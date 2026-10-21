package com.masanz.modelo;

import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mascota> mascotas;

    public boolean addMascota(Mascota mascota){
        return mascotas.add(mascota);
    }


}
