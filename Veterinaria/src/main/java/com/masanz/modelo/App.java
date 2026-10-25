package com.masanz.modelo;

import com.masanz.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class App {
    Scanner sc = new Scanner(System.in);

    public void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Sesion abierta");
            int opcion = 0;
            do {
                printMenu();
                opcion = sc.nextInt();
                switch (opcion) {
                    case 1:
                        altaClienteYMascotas(session);
                        break;
                    case 2:
                        altaCliente(session);
                        break;
                    case 3:
                        altaMascota(session);
                        break;
                    case 4:
                        registroTratamiento(session);
                        break;
                    case 5:
                        getFacturas(session);
                        break;
                    case 6:
                        break;
                    default:
                        opcion = 0;
                        System.out.println("Introduzca una opción válida");
                        break;
                }

            } while (opcion != 6);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }

    private static void printMenu() {
        System.out.println("_______Menu______");
        System.out.println("1 - Alta cliente c/Registro mascotas");
        System.out.println("2 - Alta cliente");
        System.out.println("3 - Registrar mascota");
        System.out.println("4 - Registro tratamiento");
        System.out.println("5 - Consulta historial de tratamientos");
        System.out.println("6 - Salir");
    }

    /**
     * @return true si ejecución correcta false si ejecución fallida y ultima acción cancelada
     */
    private boolean altaClienteYMascotas(Session session) {
        Boolean res = false;
        Cliente clienteInsert;
        clienteInsert = altaCliente(session);
        res = clienteInsert != null;
        if (!res) {
            return res;
        }
        String leido;
        do {
            res = altaMascota(session);
            if (!res) {
                return res;
            }
            System.out.println("¿Quieres añadir otra mascota? s/N");
            leido = sc.nextLine();
        } while (leido.equalsIgnoreCase("s"));
        return res;
    }

    /**
     * @param session session de hibernate
     * @return Cliente insertado si se ha hecho satisfactoriamente, cliente null si no.
     */
    private Cliente altaCliente(Session session) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce tu dni");
        String dni = scan.nextLine();
        if (dni.isBlank()) {
            return null;
        }
        System.out.println("Introduce tu nombre");
        String nombre = scan.nextLine();
        System.out.println("Introduce tu telefono");
        String telefono = scan.nextLine();
        System.out.println("Introduce tu email");
        String email = scan.nextLine();

        return Cliente.insertar(dni, nombre, telefono, email, session);

    }

    private boolean altaMascota(Session session) {
        Scanner scan = new Scanner(System.in);
        String nombre;
        String especie;
        String raza;
        String fecha_nac;
        boolean fechaValida;
        do {
            System.out.println("Introduce el nombre de tu mascota");
            nombre = scan.nextLine();
        } while (nombre.isBlank());
        do {
            System.out.println("Introduce su especie");
            especie = scan.nextLine();
        } while (especie.isBlank());
        do {
            System.out.println("Introduce su raza");
            raza = scan.nextLine();
        } while (raza.isBlank());
        do {
            System.out.println("Introduce su fecha de nacimiento (YYYY-MM-DD)");
            fecha_nac = scan.nextLine();
            String[] fechaSegment = fecha_nac.split("-");
            fechaValida = (fechaSegment.length == 3 && fechaSegment[0].length() == 4 && fechaSegment[1].length() <= 2 && fechaSegment[2].length() <= 2);
        } while (!fechaValida);
        Date DateNac = Date.valueOf(fecha_nac);
        System.out.println("Introduce el DNI del cliente dueño");
        String dni_Cliente = scan.nextLine();
        Cliente duenio = Cliente.getCliente(dni_Cliente, session);
        return Mascota.insertar(nombre, especie, raza, DateNac, duenio, session);
    }

    private boolean registroTratamiento(Session session) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Introduce el ID de la mascota");
        int idMascota;
        try {
            idMascota = Integer.parseInt(scan.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID de mascota no válido");
            return false;
        }
        Mascota mascota = Mascota.getMascota(idMascota,session);
        String descripcion;
        do {
            System.out.println("Introduce la descripción del tratamiento");
            descripcion = scan.nextLine();
        } while (descripcion.isBlank());
        float coste;
        try {
            System.out.println("Introduce el coste (ej. 45.50)");
            coste = Float.parseFloat(scan.nextLine().trim().replace(',', '.'));
            if (coste < 0) {
                System.out.println("El coste no puede ser negativo");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Coste no válido");
            return false;
        }
        Date fecha = Date.valueOf(LocalDate.now());
        return Factura.insertar(fecha,descripcion,coste,mascota,session);
    }
    private ArrayList<Factura> getFacturas(Session session) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Consultar historial por: 1 - Cliente (DNI), 2 - Mascota (ID)");
        String opcion = scan.nextLine().trim();
        ArrayList<Factura> filas;
        try {
            if (opcion.equals("1")) {
                System.out.println("Introduce el DNI del cliente");
                String dni = scan.nextLine().trim();
                filas = Factura.getFacturasCliente(dni,session);
            } else if (opcion.equals("2")) {
                System.out.println("Introduce el ID de la mascota");
                int id = Integer.parseInt(scan.nextLine().trim());
                filas = Factura.getFacturasMascota(id,session);
            } else {
                System.out.println("Opción no válida");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID no válido");
            return null;
        }
        if (filas.isEmpty()) {
            System.out.println("No hay tratamientos registrados");
            return filas;
        }
        double total = 0;
        int i = 1;
        for (Factura f : filas) {
            double coste = f.getCoste();
            total += coste;
            System.out.println("Factura " + i);
            System.out.println(f);
            i++;
        }
        System.out.printf("Total: %.2f EUR\n", total);
        return filas;
    }
}

