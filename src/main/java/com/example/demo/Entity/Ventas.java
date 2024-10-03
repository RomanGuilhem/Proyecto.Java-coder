package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Ventas")

public class Ventas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String producto;
    private int cantidad;
    private double precioTotal;
    
    public Ventas(String producto, int cantidad, double precioTotal) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getprecioTotal() {
        return precioTotal;
    }

    public void setprecioTotal(double preciototal) {
        this.precioTotal=preciototal;
    }
}
