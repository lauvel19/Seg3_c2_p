package org.example.service;

import org.example.domain.Empleado;
import org.example.interfaces.repository.EmpleadoInterfazRepository;
import org.example.service.EmpleadoService;

import java.util.List;

public class EmpleadoServiceImpl implements EmpleadoService {
    private final EmpleadoInterfazRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoInterfazRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.cargar();
    }

    @Override
    public Empleado buscarEmpleado(String nombre, String apellido, String cargo) {
        return empleadoRepository.buscarEmpleado(nombre, apellido, cargo);
    }

    @Override
    public void registrarEmpleado(Empleado empleado) {
        empleadoRepository.guardar(empleado);
    }

    @Override
    public void actualizarEmpleado(Empleado empleado) {
        empleadoRepository.update(empleado);
    }

    @Override
    public void eliminarEmpleado(String nombre, String apellido, String cargo) {
        empleadoRepository.eliminar(nombre, apellido, cargo);
    }

    @Override
    public List<Empleado> buscarEmpleadosPorDepartamento(String departamento) {
        List<Empleado> empleados = empleadoRepository.cargar();
        return empleados.stream()
                .filter(emp -> emp.getDepartamento().equalsIgnoreCase(departamento))
                .toList();
    }

    @Override
    public double calcularSalarioPorDepartamento(String departamento) {
        List<Empleado> empleados = buscarEmpleadosPorDepartamento(departamento);
        return empleados.stream()
                .mapToDouble(Empleado::getSalario)
                .sum();
    }
}
