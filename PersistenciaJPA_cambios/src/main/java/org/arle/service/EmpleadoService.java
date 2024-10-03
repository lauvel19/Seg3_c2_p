package org.arle.service;

import org.arle.entity.Empleado;
import org.arle.repository.EmpleadoRepository;

import java.util.List;

public class EmpleadoService {

    private final EmpleadoRepository repository;

    public EmpleadoService() {
        this.repository = new EmpleadoRepository();
    }

    public void crearProducto(Empleado empleado) {
        repository.crear(empleado);
    }

    public Empleado obtenerProducto(Long id) {
        return repository.leer(id);
    }

    public List<Empleado> obtenerTodosLosProductos() {
        return repository.leerTodos();
    }

    public void actualizarProducto(Empleado empleado) {
        repository.actualizar(empleado);
    }

    public void eliminarProducto(Long id) {
        repository.eliminar(id);
    }

    public void cerrar() {
        repository.cerrar();
    }
}
