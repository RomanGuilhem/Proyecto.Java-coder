package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "clients")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Client {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 75, nullable = false)
    private String name;

    @Column(length = 75, nullable = false)
    private String lastname;

    @Column(length = 11, nullable = false, unique = true)
    private String docnumber;


}