package com.masanz.modelo;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "mascotas")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nombre;

    @Column
    private String especie;

    @Column
    private String raza;

    @Column
    private Date fecha_nac;
}
