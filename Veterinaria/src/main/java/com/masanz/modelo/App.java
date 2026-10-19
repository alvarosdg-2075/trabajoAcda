package com.masanz.modelo;

import com.masanz.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class App {
    Scanner sc = new Scanner(System.in);
    public void main(String[] args) {
        int opcion = 1;
        do{
            printMenu();
            switch (opcion){
                case 1:
                    altaClienteYMascotas();
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
        System.out.println("3 - Registrar mascota");
        System.out.println("4 - Registro tratamiento");
        System.out.println("5 - Consulta historial de tratamientos");
        System.out.println("6 - Salir");
    }

    /**
     *
     * @return true si ejecución correcta false si ejecución fallida y ultima acción cancelada
     */
    private boolean altaClienteYMascotas(){
        Boolean res =false;
        //TODO
        // res = altaCliente();
        if(!res){
            return res;
        }
        String leido;
        do{
            //TODO
            // res = altaMascota();
            if(!res){
                return res;
            }
            System.out.println("¿Quieres añadir otra mascota? s/N");
            leido = sc.nextLine();
        }while(leido.equalsIgnoreCase("s"));
        return res;
    }
}
