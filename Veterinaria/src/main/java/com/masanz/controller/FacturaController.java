package com.masanz.controller;

import com.masanz.modelo.Factura;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FacturaController {
    public static void generarFactura(Factura factura){
        String ruta = "facturas/Factura_"+factura.getId()+"_"+factura.getMascota().getCliente().getDni()+".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))){
            bw.write("Factura: "+ factura.getId());
            bw.newLine();
            bw.write("Fecha: "+ factura.getFecha().toString());
            bw.newLine();
            bw.write("Mascota :"+ factura.getMascota().getNombre());
            bw.newLine();
            bw.write("Descripcion: ");
            bw.newLine();
            bw.write(factura.getDescripcion());
            bw.newLine();
            bw.write("Coste: "+ factura.getCoste());
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
