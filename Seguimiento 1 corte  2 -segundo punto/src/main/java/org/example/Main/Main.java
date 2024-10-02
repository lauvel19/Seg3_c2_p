package org.example.Main;

import org.example.domain.Empleado;
import org.example.domain.Tarea;
import org.example.infraestructure.repository.EmpleadoRepositoryImp;
import org.example.infraestructure.repository.TareaRepositoryImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EmpleadoRepositoryImp empleadoRepository = new EmpleadoRepositoryImp();
    private static final TareaRepositoryImpl tareaRepository = new TareaRepositoryImpl();

    public static void main(String[] args) {
        int opcion;

        do {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Cargar empleados");
            System.out.println("2. Buscar empleado");
            System.out.println("3. Guardar empleado");
            System.out.println("4. Actualizar empleado");
            System.out.println("5. Eliminar empleado");
            System.out.println("6. Cargar tareas");
            System.out.println("7. Buscar tarea");
            System.out.println("8. Guardar tarea");
            System.out.println("9. Actualizar tarea");
            System.out.println("10. Eliminar tarea");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    cargarEmpleados();
                    break;
                case 2:
                    buscarEmpleado();
                    break;
                case 3:
                    guardarEmpleado();
                    break;
                case 4:
                    actualizarEmpleado();
                    break;
                case 5:
                    eliminarEmpleado();
                    break;
                case 6:
                    cargarTareas();
                    break;
                case 7:
                    buscarTarea();
                    break;
                case 8:
                    guardarTarea();
                    break;
                case 9:
                    actualizarTarea();
                    break;
                case 10:
                    eliminarTarea();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void cargarEmpleados() {
        List<Empleado> empleados = empleadoRepository.cargar();
        for (Empleado empleado : empleados) {
            System.out.println(empleado);
        }
    }

    private static void buscarEmpleado() {
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese cargo: ");
        String cargo = scanner.nextLine();

        Empleado empleado = empleadoRepository.buscarEmpleado(nombre, apellido, cargo);
        if (empleado != null) {
            System.out.println("Empleado encontrado: " + empleado);
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    private static void guardarEmpleado() {
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese cargo: ");
        String cargo = scanner.nextLine();
        System.out.print("Ingrese departamento: ");
        String departamento = scanner.nextLine();
        System.out.print("Ingrese salario: ");
        double salario = scanner.nextDouble();
        scanner.nextLine(); // Limpiar el buffer

        Empleado nuevoEmpleado = new Empleado(nombre, apellido, edad, cargo, departamento, salario);
        empleadoRepository.guardar(nuevoEmpleado);
        System.out.println("Empleado guardado.");
    }

    private static void actualizarEmpleado() {
        System.out.print("Ingrese nombre del empleado a actualizar: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellido del empleado a actualizar: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese cargo del empleado a actualizar: ");
        String cargo = scanner.nextLine();

        Empleado empleado = empleadoRepository.buscarEmpleado(nombre, apellido, cargo);
        if (empleado != null) {
            System.out.print("Ingrese nueva edad: ");
            empleado.setEdad(scanner.nextInt());
            scanner.nextLine(); // Limpiar el buffer
            System.out.print("Ingrese nuevo cargo: ");
            empleado.setCargo(scanner.nextLine());
            System.out.print("Ingrese nuevo departamento: ");
            empleado.setDepartamento(scanner.nextLine());
            System.out.print("Ingrese nuevo salario: ");
            empleado.setSalario(scanner.nextDouble());
            scanner.nextLine(); // Limpiar el buffer

            empleadoRepository.update(empleado);
            System.out.println("Empleado actualizado.");
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    private static void eliminarEmpleado() {
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese cargo: ");
        String cargo = scanner.nextLine();

        empleadoRepository.eliminar(nombre, apellido, cargo);
        System.out.println("Empleado eliminado.");
    }

    private static void cargarTareas() {
        List<Tarea> tareas = tareaRepository.cargar();
        for (Tarea tarea : tareas) {
            System.out.println(tarea);
        }
    }

    private static void buscarTarea() {
        System.out.print("Ingrese título de la tarea: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese nombre del empleado: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellido del empleado: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese cargo del empleado: ");
        String cargo = scanner.nextLine();

        Empleado empleado = empleadoRepository.buscarEmpleado(nombre, apellido, cargo);
        if (empleado != null) {
            Tarea tarea = tareaRepository.buscarTarea(titulo, empleado);
            if (tarea != null) {
                System.out.println("Tarea encontrada: " + tarea);
            } else {
                System.out.println("Tarea no encontrada.");
            }
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    private static void guardarTarea() {
        System.out.print("Ingrese título de la tarea: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese descripción de la tarea: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese fecha de inicio: ");
        String fechaInicio = scanner.nextLine();
        System.out.print("Ingrese fecha de fin: ");
        String fechaFin = scanner.nextLine();
        System.out.print("Ingrese estado: ");
        String estado = scanner.nextLine();
        System.out.print("Ingrese nombre del empleado: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellido del empleado: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese cargo del empleado: ");
        String cargo = scanner.nextLine();

        Empleado empleado = empleadoRepository.buscarEmpleado(nombre, apellido, cargo);
        if (empleado != null) {
            Tarea nuevaTarea = new Tarea(titulo, descripcion, fechaInicio, fechaFin, estado, empleado);
            tareaRepository.guardar(nuevaTarea);
            System.out.println("Tarea guardada.");
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    private static void actualizarTarea() {
        System.out.print("Ingrese título de la tarea a actualizar: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese nombre del empleado: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellido del empleado: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese cargo del empleado: ");
        String cargo = scanner.nextLine();

        Empleado empleado = empleadoRepository.buscarEmpleado(nombre, apellido, cargo);
        if (empleado != null) {
            Tarea tarea = tareaRepository.buscarTarea(titulo, empleado);
            if (tarea != null) {
                System.out.print("Ingrese nueva descripción: ");
                tarea.setDescripcion(scanner.nextLine());
                System.out.print("Ingrese nueva fecha de inicio: ");
                tarea.setFechaInicio(scanner.nextLine());
                System.out.print("Ingrese nueva fecha de fin: ");
                tarea.setFechaFin(scanner.nextLine());
                System.out.print("Ingrese nuevo estado: ");
                tarea.setEstado(scanner.nextLine());

                tareaRepository.update(tarea);
                System.out.println("Tarea actualizada.");
            } else {
                System.out.println("Tarea no encontrada.");
            }
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    private static void eliminarTarea() {
        System.out.print("Ingrese título de la tarea a eliminar: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese nombre del empleado: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellido del empleado: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese cargo del empleado: ");
        String cargo = scanner.nextLine();

        Empleado empleado = empleadoRepository.buscarEmpleado(nombre, apellido, cargo);
        if (empleado != null) {
            tareaRepository.eliminar(titulo, empleado);
            System.out.println("Tarea eliminada.");
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }
}
