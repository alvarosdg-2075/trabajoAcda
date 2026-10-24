package com.masanz.modelo;

import org.hibernate.*;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name="ID_Cliente")
    private Cliente cliente;


    @OneToMany(mappedBy = "mascota_factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Factura> facturas;

    public Mascota(){}
    public Mascota(String nombre,String especie,String raza,Date fecha_nac,Cliente cliente){
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fecha_nac = fecha_nac;
        this.cliente = cliente;
    }

    public Cliente getCliente() {return cliente;}

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public boolean addFactura(Factura factura){ return facturas.add(factura);}

    /**
     *
     * @param nombre nombre de la mascota
     * @param especie Especie de la mascota
     * @param raza Raza de la mascota
     * @param fecha_nac Fecha de nacimiento de la mascota
     * @return true si se ha efectuado sin problemas la inserción false si no.
     */
    public static boolean insertar(String nombre, String especie, String raza, Date fecha_nac,Cliente cliente ,Session session){
        boolean res = true;
        Transaction tr = null;
        try {
            tr=session.beginTransaction();
            cliente = (Cliente) session.merge(cliente);
            Mascota mascota = new Mascota(nombre, especie, raza, fecha_nac, cliente);
            cliente.addMascota(mascota);
            session.persist(mascota);
            tr.commit();
        }catch(HibernateException he){
            if (tr != null){
                tr.rollback();
            }
            res = false;
        }
            return res;
    }
    public static Mascota getMascota(int id,Session session){
        List<Mascota> mascotas = session.createQuery("FROM Mascota m where m.id = :id",Mascota.class).setParameter("id",id).getResultList();
        return mascotas.getFirst();
    }

}
