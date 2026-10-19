package com.masanz.modelo;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Date fecha;

    @Column
    private String descripcion;

    @Column
    private float coste;

    @Column
    private String raza;



}
