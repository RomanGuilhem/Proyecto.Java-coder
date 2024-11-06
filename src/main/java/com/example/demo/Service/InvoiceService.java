package com.example.demo.Service;

import com.example.demo.Entity.Invoice;
import com.example.demo.Entity.InvoiceItems;
import com.example.demo.Entity.Product;
import com.example.demo.Exception.InsufficientStockException;
import com.example.demo.Repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ApiService apiService;

    public Invoice save(Invoice invoice) {
        if (invoice.getDate() == null) {
            invoice.setDate(apiService.getCurrentDateTime());
        }
        return repository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return repository.findAll();
    }

    public Optional<Invoice> getById(UUID id) {
        return repository.findById(id);
    }

    public List<Invoice> saveAll(Iterable<Invoice> invoices) {
        return repository.saveAll(invoices);
    }

    public void deleteById(UUID id) {
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
        repository.deleteById(id);
    }

    @Transactional
    public void addItemToInvoice(Invoice invoice, InvoiceItems item) {
        Product product = item.getProduct();

        if (item.getQuantity() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        if (product.getStock() >= item.getQuantity()) {
            product.setStock(product.getStock() - item.getQuantity());
            productService.updateProductStock(product);

            invoice.addItem(item);
            save(invoice);
            System.out.println("Item agregado correctamente, stock actualizado: " + product.getStock());
        } else {
            throw new InsufficientStockException("No hay suficiente stock para el producto: " + product.getId());
        }
    }
}
