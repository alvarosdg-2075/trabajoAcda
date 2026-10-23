package com.masanz.modelo;

import com.masanz.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class App {
    Scanner sc = new Scanner(System.in);
    public void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Sesion abierta");
            int opcion = 1;
            do {
                printMenu();
                switch (opcion) {
                    case 1:
                        altaClienteYMascotas(session);
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
                        opcion = 0;
                        System.out.println("Introduzca una opción válida");
                        break;
                }

            } while (opcion != 6);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            HibernateUtil.shutdown();
        }
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
    private boolean altaClienteYMascotas(Session session){
        Boolean res =false;
        res = altaCliente(session);
        if(!res){
            return res;
        }
        String leido;
        do{
            res = altaMascota(session);
            if(!res){
                return res;
            }
            System.out.println("¿Quieres añadir otra mascota? s/N");
            leido = sc.nextLine();
        }while(leido.equalsIgnoreCase("s"));
        return res;
    }
    private Cliente altaCliente(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce tu dni");
        String dni = scan.nextLine();
        if (dni.isEmpty()) {
            return false;
        }
        System.out.println("Introduce tu nombre");
        String nombre = scan.nextLine();
        System.out.println("Introduce tu telefono");
        String telefono = scan.nextLine();
        System.out.println("Introduce tu email");
        String email = scan.nextLine();

        return Cliente.insertar(dni, nombre, telefono, email, session);

    }
    private boolean altaMascota(Session session){
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce el nombre de tu mascota");
        String nombre = scan.nextLine();
        if (nombre.isEmpty()) {
            return false;
        }
        System.out.println("Introduce su especie");
        String especie = scan.nextLine();
        System.out.println("Introduce su raza");
        String raza = scan.nextLine();
        System.out.println("Introduce su fecha de nacimiento");
        String fecha_nac = scan.nextLine();
        System.out.println("Introduce el DNI del cliente dueño");
        String dni_Cliente = scan.nextLine();
        Cliente duenio = Cliente.getCliente(dni_Cliente);
        return Mascota.insertar(nombre, especie, raza, fecha_nac,duenio,session);

    }