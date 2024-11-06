package com.example.demo.Controllers;

import com.example.demo.Entity.Invoice;
import com.example.demo.Entity.InvoiceItems;
import com.example.demo.Entity.Product;
import com.example.demo.Service.InvoiceService;
import com.example.demo.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final ProductService productService;

    public InvoiceController(InvoiceService invoiceService, ProductService productService) {
        this.invoiceService = invoiceService;
        this.productService = productService;
    }

    @Operation(summary = "Create Invoice", description = "Create a new invoice")
    @ApiResponse(responseCode = "201", description = "Invoice created successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
        try {
            Invoice newInvoice = invoiceService.save(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(newInvoice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get all invoices", description = "Get all invoices")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Iterable<Invoice>> getAll() {
        Iterable<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Get invoice by ID", description = "Get an invoice by its ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Invoice not found")
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Invoice> getById(@PathVariable UUID id) {
        Optional<Invoice> invoice = invoiceService.getById(id);
        return invoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Add items to invoice", description = "Add items to an existing invoice")
    @ApiResponse(responseCode = "200", description = "Items added successfully")
    @ApiResponse(responseCode = "404", description = "Invoice or product not found")
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @PostMapping("/{invoiceId}/items")
    public ResponseEntity<?> addItemsToInvoice(
            @PathVariable UUID invoiceId,
            @RequestBody List<InvoiceItems> items) {

        Optional<Invoice> invoiceOpt = invoiceService.getById(invoiceId);
        if (!invoiceOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found");
        }

        Invoice invoice = invoiceOpt.get();
        invoice.clearItems();

        try {
            for (InvoiceItems item : items) {
                if (item.getProduct() == null || item.getProduct().getId() == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product ID is required");
                }

                Optional<Product> productOpt = productService.getById(item.getProduct().getId());
                if (!productOpt.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Product not found with ID: " + item.getProduct().getId());
                }

                item.setProduct(productOpt.get());
                item.setInvoice(invoice);

                item.setSubtotal();

                invoiceService.addItemToInvoice(invoice, item);
            }

            invoice.calculateTotal();
            Invoice updatedInvoice = invoiceService.save(invoice);

            return ResponseEntity.status(HttpStatus.CREATED).body(updatedInvoice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding items to the invoice");
        }
    }

    @Operation(summary = "Update invoice", description = "Update an existing invoice by ID")
    @ApiResponse(responseCode = "200", description = "Invoice updated successfully")
    @ApiResponse(responseCode = "404", description = "Invoice not found")
    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Invoice> updateInvoice(@PathVariable UUID id, @RequestBody Invoice invoice) {
        Optional<Invoice> existingInvoice = invoiceService.getById(id);
        if (!existingInvoice.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        invoice.setId(id);
        Invoice updatedInvoice = invoiceService.save(invoice);
        return ResponseEntity.ok(updatedInvoice);
    }

    @Operation(summary = "Delete invoice by ID", description = "Delete a specific invoice by its ID")
    @ApiResponse(responseCode = "204", description = "Invoice deleted successfully")
    @ApiResponse(responseCode = "404", description = "Invoice not found")
    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Void> deleteInvoice(@PathVariable UUID id) {
        try {
            invoiceService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
