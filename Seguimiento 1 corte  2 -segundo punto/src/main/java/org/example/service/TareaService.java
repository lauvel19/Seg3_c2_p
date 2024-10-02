package org.example.service;

import org.example.domain.Empleado;
import org.example.domain.Tarea;

import java.util.List;

public interface TareaService {
    List<Tarea> obtenerTodasLasTareas();
    Tarea buscarTarea(String titulo, Empleado empleado);
    void asignarTarea(Tarea tarea);
    void actualizarTarea(Tarea tarea);
    void eliminarTarea(String titulo, Empleado empleado);
    int generarReporteProductividad(Empleado empleado, String fechaInicio, String fechaFin);
}
