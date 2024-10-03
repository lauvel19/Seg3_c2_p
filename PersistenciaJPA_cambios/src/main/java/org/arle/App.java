package org.arle;

import org.arle.entity.Empleado;
import org.arle.service.EmpleadoService;


import java.util.List;
import java.util.Scanner;

public class App {
    private static final EmpleadoService productoService = new EmpleadoService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- CRUD de Empleado ---");
            System.out.println("1. Crear empleado");
            System.out.println("2. Leer empleado");
            System.out.println("3. Actualizar empleado");
            System.out.println("4. Eliminar empleado");
            System.out.println("5. Listar todos los empleados");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1 -> crearProducto();
                case 2 -> leerProducto();
                case 3 -> actualizarProducto();
                case 4 -> eliminarProducto();
                case 5 -> listarProductos();
                case 6 -> salir = true;
                default -> System.out.println("Opción no válida");
            }
        }
        productoService.cerrar();
        scanner.close();
    }

    private static void crearProducto() {
        System.out.print("Nombre del empleado: ");
        String nombre = scanner.nextLine();
        System.out.print("Edad del empleado: ");
        int edad = scanner.nextInt();

        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setEdad(edad);

        productoService.crearProducto(empleado);
        System.out.println("Empleado creado con éxito");
    }

    private static void leerProducto() {
        System.out.print("ID del empleado: ");
        Long id = scanner.nextLong();
        Empleado empleado = productoService.obtenerProducto(id);
        if (empleado != null) {
            System.out.println(empleado);
        } else {
            System.out.println("Empleado no encontrado");
        }
    }

    private static void actualizarProducto() {
        System.out.print("ID del empleado a actualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea

        Empleado empleado = productoService.obtenerProducto(id);
        if (empleado != null) {
            System.out.print("Nuevo nombre (deje en blanco para mantener el actual): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) {
                empleado.setNombre(nombre);
            }

            System.out.print("Nueva edad (0 para mantener el actual): ");
            int edad = scanner.nextInt();
            if (edad != 0) {
                empleado.setEdad(edad);
            }

            productoService.actualizarProducto(empleado);
            System.out.println("Empleado actualizado con éxito");
        } else {
            System.out.println("Empleado no encontrado");
        }
    }

    private static void eliminarProducto() {
        System.out.print("ID del Empleado a eliminar: ");
        Long id = scanner.nextLong();
        productoService.eliminarProducto(id);
        System.out.println("Empleado eliminado con éxito");
    }

    private static void listarProductos() {
        List<Empleado> empleados = productoService.obtenerTodosLosProductos();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleado registrados");
        } else {
            for (Empleado empleado : empleados) {
                System.out.println(empleado);
            }
        }
    }
}