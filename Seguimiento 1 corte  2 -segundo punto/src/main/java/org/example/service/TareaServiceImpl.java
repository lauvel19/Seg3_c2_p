package org.example.service;

import org.example.domain.Empleado;
import org.example.domain.Tarea;
import org.example.interfaces.repository.TareaInterfazRepository;
import org.example.service.TareaService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TareaServiceImpl implements TareaService {
    private final TareaInterfazRepository tareaRepository;

    public TareaServiceImpl(TareaInterfazRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    @Override
    public List<Tarea> obtenerTodasLasTareas() {
        return tareaRepository.cargar();
    }

    @Override
    public Tarea buscarTarea(String titulo, Empleado empleado) {
        return tareaRepository.buscarTarea(titulo, empleado);
    }

    @Override
    public void asignarTarea(Tarea tarea) {
        tareaRepository.guardar(tarea);
    }

    @Override
    public void actualizarTarea(Tarea tarea) {
        tareaRepository.update(tarea);
    }

    @Override
    public void eliminarTarea(String titulo, Empleado empleado) {
        tareaRepository.eliminar(titulo, empleado);
    }

    @Override
    public int generarReporteProductividad(Empleado empleado, String fechaInicio, String fechaFin) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int tareasCompletadas = 0;

        try {
            Date inicio = sdf.parse(fechaInicio);
            Date fin = sdf.parse(fechaFin);
            List<Tarea> tareas = tareaRepository.cargar();

            for (Tarea tarea : tareas) {
                if (tarea.getEmpleado().equals(empleado) && tarea.getEstado().equalsIgnoreCase("completada")) {
                    Date fechaFinTarea = sdf.parse(tarea.getFechaFin());
                    if (!fechaFinTarea.before(inicio) && !fechaFinTarea.after(fin)) {
                        tareasCompletadas++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tareasCompletadas;
    }
}
