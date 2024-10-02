package org.example.service;

import org.example.domain.Empleado;

import java.util.List;

public interface EmpleadoService {
    List<Empleado> obtenerTodosLosEmpleados();
    Empleado buscarEmpleado(String nombre, String apellido, String cargo);
    void registrarEmpleado(Empleado empleado);
    void actualizarEmpleado(Empleado empleado);
    void eliminarEmpleado(String nombre, String apellido, String cargo);
    List<Empleado> buscarEmpleadosPorDepartamento (String departamento);
    double calcularSalarioPorDepartamento(String departamento);
}
