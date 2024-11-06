package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String lastName;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 12, nullable = false, unique = true)
    private String dni;

    @Column(length = 15)
    private String phone;

    public Client(UUID id, String name, String lastName, String email, String dni, String phone) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.dni = dni;
        this.phone = phone;
    }

    public Client(UUID id, String name, String email, String dni) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dni = dni;
    }
}
