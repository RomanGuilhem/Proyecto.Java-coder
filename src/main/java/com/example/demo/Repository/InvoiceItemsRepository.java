package com.example.demo.Repository;

import com.example.demo.Entity.InvoiceItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceItemsRepository extends JpaRepository<InvoiceItems, UUID> {
    List<InvoiceItems> findByInvoiceId(UUID invoiceId);

}