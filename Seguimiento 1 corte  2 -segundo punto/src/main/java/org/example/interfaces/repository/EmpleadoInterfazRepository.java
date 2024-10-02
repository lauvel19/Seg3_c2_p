package org.example.interfaces.repository;

import org.example.domain.Empleado;

import java.util.List;

public interface EmpleadoInterfazRepository {
        List<Empleado> cargar();
        Empleado buscarEmpleado(String nombre, String apellido, String cargo);
        void guardar(Empleado empleado);
        void update(Empleado empleado);
        void eliminar(String nombre, String apellido, String cargo);


}
