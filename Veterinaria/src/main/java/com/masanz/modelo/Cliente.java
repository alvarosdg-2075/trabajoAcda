package com.masanz.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String dni;

    @Column
    private String nombre;

    @Column
    private String telefono;

    @Column
    private String email;
}
