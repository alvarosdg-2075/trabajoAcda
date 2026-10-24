package com.masanz.modelo;

import jakarta.persistence.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

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


    public Cliente(){}
    public Cliente(String dni, String nombre, String telefono, String email){
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public boolean addMascota(Mascota mascota){
        return mascotas.add(mascota);
    }

    public static Cliente insertar(String dni, String nombre, String telefono, String email, Session session){
        Transaction tr = null;
        Cliente cliente;
        try{
            tr = session.beginTransaction();
            cliente = new Cliente(dni,nombre,telefono,email);
            session.persist(cliente);
            tr.commit();
        }catch(HibernateException he){
            if (tr != null){
                tr.rollback();
            }
            cliente = null;
        }
        return cliente;
    }
    public static Cliente getCliente(String dni,Session session){
        List<Cliente> clientes = session.createQuery("FROM Cliente c where c.dni = :dni",Cliente.class).setParameter("dni",dni).getResultList();
        return clientes.getFirst();
    }
}
