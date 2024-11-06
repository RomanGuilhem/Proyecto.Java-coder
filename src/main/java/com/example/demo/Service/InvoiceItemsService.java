package com.example.demo.Service;

import com.example.demo.Entity.InvoiceItems;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.InvoiceItemsRepository;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceItemsService {

    @Autowired
    private InvoiceItemsRepository repository;

    @Autowired
    private ProductRepository productRepository;

    public InvoiceItems save(InvoiceItems item) {
        Optional<Product> product = productRepository.findById(item.getProduct().getId());
        if (product.isPresent()) {
            return repository.save(item);
        } else {
            throw new RuntimeException("Producto no encontrado con el ID: " + item.getProduct().getId());
        }
    }

    public List<InvoiceItems> getAllItems() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<InvoiceItems> findByInvoiceId(UUID invoiceId) {
        List<InvoiceItems> items = repository.findByInvoiceId(invoiceId);
        if (items.isEmpty()) {
            throw new RuntimeException("No se encontraron items para la factura con ID: " + invoiceId);
        }
        return items;
    }

    public void deleteById(UUID id) {
        InvoiceItems item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de factura no encontrado con el ID: " + id));
        repository.delete(item);
    }
}
