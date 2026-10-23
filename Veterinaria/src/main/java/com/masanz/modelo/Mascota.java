package com.masanz.modelo;

import org.hibernate.*;
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

    @ManyToOne
    @JoinColumn(name="ID_Cliente")
    private Cliente cliente;

    public Mascota(){}
    public Mascota(String nombre,String especie,String raza,Date fecha_nac,Cliente cliente){
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fecha_nac = fecha_nac;
        this.cliente = cliente;
    }


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
}
