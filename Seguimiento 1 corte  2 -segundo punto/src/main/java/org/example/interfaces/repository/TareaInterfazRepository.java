package org.example.interfaces.repository;

import org.example.domain.Empleado;
import org.example.domain.Tarea;

import java.util.List;

public interface TareaInterfazRepository {
        List<Tarea> cargar();
        Tarea buscarTarea(String titulo, Empleado empleado);
        void guardar(Tarea tarea);
        void update(Tarea tarea);
        void eliminar(String titulo, Empleado empleado);


}
