package com.masanz.modelo;

import com.masanz.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class App {
    public static void main(String[] args) {
        int opcion = 1;
        do{
            printMenu();
            switch (opcion){
                case 1:
                    //TODO
                    // altaClienteYMascotas();
                    break;
                case 2:
                    //TODO
                    // altaCliente();
                    break;
                case 3:
                    //TODO
                    // altaMascota();
                    break;
                case 4:
                    //TODO
                    // registroTratamiento();
                    break;
                case 5:
                    //TODO
                    // getFacturas();
                    break;
                case 6:
                    break;
                default:
                    opcion=0;
                    System.out.println("Introduzca una opción válida");
                    break;
            }

        }while(opcion!= 6);
    }

     private static void printMenu(){
        System.out.println("_______Menu______");
        System.out.println("1 - Alta cliente c/Registro mascotas");
        System.out.println("2 - Alta cliente");
        System.out.println("3 - Registro mascotas");
        System.out.println("4 - Registro tratamiento");
        System.out.println("5 - Consulta historial de tratamientos");
        System.out.println("6 - Salir");
    }
}
