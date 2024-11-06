package com.example.demo.Repository;

import com.example.demo.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}