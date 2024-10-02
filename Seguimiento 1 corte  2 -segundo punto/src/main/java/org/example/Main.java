package org.example;

/*

import org.example.domain.Empleado;
import org.example.domain.Tarea;
import org.example.infraestructure.repository.EmpleadoRepositoryImp;
import org.example.infraestructure.repository.TareaRepositoryImpl;
import org.example.service.EmpleadoService;
import org.example.service.EmpleadoServiceImpl;
import org.example.service.TareaService;
import org.example.service.TareaServiceImpl;

import javax.swing.*;
import java.util.List;

public class Main {
    private static final EmpleadoService empleadoService = new EmpleadoServiceImpl(new EmpleadoRepositoryImp());
    private static final TareaService tareaService = new TareaServiceImpl(new TareaRepositoryImpl());

    public static void main(String[] args) {
        String menu = "1. Registrar nuevo empleado\n" +
                "2. Actualizar datos del empleado\n" +
                "3. Asignar tarea a un empleado\n" +
                "4. Eliminar tarea asignada\n" +
                "5. Mostrar lista de empleados\n" +
                "6. Mostrar tareas de un empleado\n" +
                "7. Actualizar estado de una tarea\n" +
                "8. Buscar empleados por departamento\n" +
                "9. Calcular salario total por departamento\n" +
                "10. Salir";

        while (true) {
            String opcion = JOptionPane.showInputDialog(menu);
            if (opcion == null || opcion.equals("10")) {
                break; // Salir del programa
            }

            try {
                switch (opcion) {
                    case "1":
                        registrarEmpleado();
                        break;
                    case "2":
                        actualizarEmpleado();
                        break;
                    case "3":
                        asignarTarea();
                        break;
                    case "4":
                        eliminarTarea();
                        break;
                    case "5":
                        mostrarEmpleados();
                        break;
                    case "6":
                        mostrarTareasDeEmpleado();
                        break;
                    case "7":
                        actualizarEstadoTarea();
                        break;
                    case "8":
                        buscarEmpleadosPorDepartamento();
                        break;
                    case "9":
                        calcularSalarioPorDepartamento();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente nuevamente.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage());
            }
        }
    }

    private static void registrarEmpleado() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del empleado:");
        String apellido = JOptionPane.showInputDialog("Ingrese el apellido del empleado:");
        int edad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del empleado:"));
        String cargo = JOptionPane.showInputDialog("Ingrese el cargo del empleado:");
        String departamento = JOptionPane.showInputDialog("Ingrese el departamento del empleado:");
        double salario = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el salario del empleado:"));

        Empleado empleado = new Empleado(nombre, apellido, edad, cargo, departamento, salario);
        empleadoService.registrarEmpleado(empleado);
        JOptionPane.showMessageDialog(null, "Empleado registrado exitosamente.");
    }

    private static void actualizarEmpleado() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del empleado a actualizar:");
        String apellido = JOptionPane.showInputDialog("Ingrese el apellido del empleado a actualizar:");
        String cargo = JOptionPane.showInputDialog("Ingrese el cargo del empleado a actualizar:");

        Empleado empleado = empleadoService.buscarEmpleado(nombre, apellido, cargo);
        if (empleado != null) {
            String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre (actual: " + empleado.getNombre() + "):", empleado.getNombre());
            String nuevoApellido = JOptionPane.showInputDialog("Ingrese el nuevo apellido (actual: " + empleado.getApellido() + "):", empleado.getApellido());
            int nuevaEdad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva edad (actual: " + empleado.getEdad() + "):", empleado.getEdad()));
            String nuevoCargo = JOptionPane.showInputDialog("Ingrese el nuevo cargo (actual: " + empleado.getCargo() + "):", empleado.getCargo());
            String nuevoDepartamento = JOptionPane.showInputDialog("Ingrese el nuevo departamento (actual: " + empleado.getDepartamento() + "):", empleado.getDepartamento());
            double nuevoSalario = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el nuevo salario (actual: " + empleado.getSalario() + "):", empleado.getSalario()));

            empleado.setNombre(nuevoNombre);
            empleado.setApellido(nuevoApellido);
            empleado.setEdad(nuevaEdad);
            empleado.setCargo(nuevoCargo);
            empleado.setDepartamento(nuevoDepartamento);
            empleado.setSalario(nuevoSalario);

            empleadoService.actualizarEmpleado(empleado);
            JOptionPane.showMessageDialog(null, "Empleado actualizado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Empleado no encontrado.");
        }
    }

    private static void asignarTarea() {
        String titulo = JOptionPane.showInputDialog("Ingrese el título de la tarea:");
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción de la tarea:");
        String fechaInicio = JOptionPane.showInputDialog("Ingrese la fecha de inicio (yyyy-MM-dd):");
        String fechaFin = JOptionPane.showInputDialog("Ingrese la fecha de fin (yyyy-MM-dd):");
        String estado = JOptionPane.showInputDialog("Ingrese el estado de la tarea:");
        String nombreEmpleado = JOptionPane.showInputDialog("Ingrese el nombre del empleado asignado:");
        String apellidoEmpleado = JOptionPane.showInputDialog("Ingrese el apellido del empleado asignado:");
        String cargoEmpleado = JOptionPane.showInputDialog("Ingrese el cargo del empleado asignado:");

        Empleado empleado = empleadoService.buscarEmpleado(nombreEmpleado, apellidoEmpleado, cargoEmpleado);
        if (empleado != null) {
            Tarea tarea = new Tarea(titulo, descripcion, fechaInicio, fechaFin, estado, empleado);
            tareaService.asignarTarea(tarea);
            JOptionPane.showMessageDialog(null, "Tarea asignada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Empleado no encontrado.");
        }
    }

    private static void eliminarTarea() {
        String titulo = JOptionPane.showInputDialog("Ingrese el título de la tarea a eliminar:");
        String nombreEmpleado = JOptionPane.showInputDialog("Ingrese el nombre del empleado asignado:");
        String apellidoEmpleado = JOptionPane.showInputDialog("Ingrese el apellido del empleado asignado:");

        Empleado empleado = empleadoService.buscarEmpleado(nombreEmpleado, apellidoEmpleado, null);
        if (empleado != null) {
            tareaService.eliminarTarea(titulo, empleado);
            JOptionPane.showMessageDialog(null, "Tarea eliminada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Empleado no encontrado.");
        }
    }

    private static void mostrarEmpleados() {
        List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
        StringBuilder sb = new StringBuilder("Empleados registrados:\n");
        for (Empleado emp : empleados) {
            sb.append(emp.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void mostrarTareasDeEmpleado() {
        String nombreEmpleado = JOptionPane.showInputDialog("Ingrese el nombre del empleado:");
        String apellidoEmpleado = JOptionPane.showInputDialog("Ingrese el apellido del empleado:");

        Empleado empleado = empleadoService.buscarEmpleado(nombreEmpleado, apellidoEmpleado, null);
        if (empleado != null) {
            List<Tarea> tareas = tareaService.obtenerTodasLasTareas();
            StringBuilder sb = new StringBuilder("Tareas asignadas a " + empleado.getNombre() + " " + empleado.getApellido() + ":\n");
            for (Tarea tarea : tareas) {
                if (tarea.getEmpleado().equals(empleado)) {
                    sb.append(tarea.toString()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Empleado no encontrado.");
        }
    }

    private static void actualizarEstadoTarea() {
        String titulo = JOptionPane.showInputDialog("Ingrese el título de la tarea:");
        String nombreEmpleado = JOptionPane.showInputDialog("Ingrese el nombre del empleado:");
        String apellidoEmpleado = JOptionPane.showInputDialog("Ingrese el apellido del empleado:");

        Empleado empleado = empleadoService.buscarEmpleado(nombreEmpleado, apellidoEmpleado, null);
        if (empleado != null) {
            Tarea tarea = tareaService.buscarTarea(titulo, empleado);
            if (tarea != null) {
                String nuevoEstado = JOptionPane.showInputDialog("Ingrese el nuevo estado de la tarea (actual: " + tarea.getEstado() + "):", tarea.getEstado());
                tarea.setEstado(nuevoEstado);
                tareaService.actualizarTarea(tarea);
                JOptionPane.showMessageDialog(null, "Estado de la tarea actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Tarea no encontrada.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Empleado no encontrado.");
        }
    }

    private static void buscarEmpleadosPorDepartamento() {
        String departamento = JOptionPane.showInputDialog("Ingrese el nombre del departamento:");
        List<Empleado> empleados = empleadoService.buscarEmpleadosPorDepartamento(departamento);

        StringBuilder sb = new StringBuilder("Empleados en el departamento " + departamento + ":\n");
        for (Empleado emp : empleados) {
            sb.append(emp.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void calcularSalarioPorDepartamento() {
        String departamento = JOptionPane.showInputDialog("Ingrese el nombre del departamento:");
        double totalSalario = empleadoService.calcularSalarioPorDepartamento(departamento);
        JOptionPane.showMessageDialog(null, "El salario total en el departamento " + departamento + " es: " + totalSalario);
    }
}

 */
