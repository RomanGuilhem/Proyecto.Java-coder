package com.example.demo.Controllers;

import com.example.demo.Entity.InvoiceItems;
import com.example.demo.Service.InvoiceItemsService;
import com.example.demo.Service.InvoiceService;
import com.example.demo.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceItemsController {

    private final InvoiceItemsService invoiceItemsService;
    private final InvoiceService invoiceService;
    private final ProductService productService;

    // Constructor explícito para inyección de dependencias
    public InvoiceItemsController(InvoiceItemsService invoiceItemsService, InvoiceService invoiceService, ProductService productService) {
        this.invoiceItemsService = invoiceItemsService;
        this.invoiceService = invoiceService;
        this.productService = productService;
    }

    @Operation(summary = "Get items by invoice ID", description = "Retrieve all items associated with a specific invoice ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Invoice not found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @GetMapping(value = "/{invoiceId}/items", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<InvoiceItems>> getItemsByInvoiceId(@PathVariable UUID invoiceId) {

        // Verificar si existe la factura antes de obtener los items
        Optional<?> invoiceOpt = invoiceService.getById(invoiceId);
        if (!invoiceOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // 404 si la factura no existe
        }

        try {
            List<InvoiceItems> items = invoiceItemsService.findByInvoiceId(invoiceId);
            return ResponseEntity.ok(items);  // 200 OK con la lista de items
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // 500 en caso de error
        }
    }
}
