package com.masanz.modelo;

import jakarta.persistence.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name="ID_mascota")
    private Mascota mascota_factura;

    public Factura(){}

    public Factura(Date fecha, String descripcion,float coste,Mascota mascota){
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.coste = coste;
        this.mascota_factura = mascota;
    }

    public static ArrayList<Factura> getFacturasCliente(String dni, Session session){
        List<Factura> facturas = session.createQuery("FROM Factura f where f.mascota_factura.cliente.dni = :dni",Factura.class).setParameter("dni",dni).getResultList();
        return new ArrayList<>(facturas);
    }
    public static ArrayList<Factura> getFacturasMascota(String id_mascota, Session session){
        List<Factura> facturas = session.createQuery("FROM Factura f where f.mascota_factura.id = :id",Factura.class).setParameter("id",id_mascota).getResultList();
        return new ArrayList<>(facturas);
    }

    public static boolean insertar(Date fecha,String descripcion,float coste,Mascota mascota ,Session session){
        boolean res = true;
        Transaction tr = null;
        try {
            tr=session.beginTransaction();
            mascota = (Mascota) session.merge(mascota);
            Factura factura = new Factura(fecha,descripcion,coste,mascota);
            mascota.addFactura(factura);
            session.persist(factura);
            tr.commit();
        }catch(HibernateException he){
            if (tr != null){
                tr.rollback();
            }
            res = false;
        }
        return res;
    }
}
