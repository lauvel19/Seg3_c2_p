package org.example.infraestructure.repository;


import org.example.domain.Empleado;
import org.example.domain.Tarea;
import org.example.infraestructure.repository.Recursos.DatabaseConnection;
import org.example.interfaces.repository.TareaInterfazRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TareaRepositoryImpl implements TareaInterfazRepository {

    @Override
    public List<Tarea> cargar() {
        List<Tarea> tareas = new ArrayList<>();
        String query = "SELECT * FROM tareas";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {

                int empleadoId = resultSet.getInt("empleado_id");
                Empleado empleado = obtenerEmpleadoPorId(empleadoId);

                Tarea tarea = new Tarea(
                        resultSet.getString("titulo"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("fechaInicio"),
                        resultSet.getString("fechaFin"),
                        resultSet.getString("estado"),
                        empleado // Asociar el empleado a la tarea
                );
                tareas.add(tarea);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tareas;
    }

    @Override
    public Tarea buscarTarea(String titulo, Empleado empleado) {
        Tarea tarea = null;
        String query = "SELECT * FROM tareas WHERE titulo = ? AND empleado_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, titulo);
            preparedStatement.setInt(2, empleado.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                tarea = new Tarea(
                        resultSet.getString("titulo"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("fechaInicio"),
                        resultSet.getString("fechaFin"),
                        resultSet.getString("estado"),
                        empleado // Asociar el empleado
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarea;
    }

    @Override
    public void guardar(Tarea tarea) {
        String query = "INSERT INTO tareas (titulo, descripcion, fechaInicio, fechaFin, estado, empleado_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, tarea.getTitulo());
            preparedStatement.setString(2, tarea.getDescripcion());
            preparedStatement.setString(3, tarea.getFechaInicio());
            preparedStatement.setString(4, tarea.getFechaFin());
            preparedStatement.setString(5, tarea.getEstado());
            preparedStatement.setInt(6, tarea.getEmpleado().getId()); // Asegúrate de manejar el ID del empleado

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Tarea tarea) {
        String query = "UPDATE tareas SET descripcion = ?, fechaInicio = ?, fechaFin = ?, estado = ? WHERE titulo = ? AND empleado_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, tarea.getDescripcion());
            preparedStatement.setString(2, tarea.getFechaInicio());
            preparedStatement.setString(3, tarea.getFechaFin());
            preparedStatement.setString(4, tarea.getEstado());
            preparedStatement.setString(5, tarea.getTitulo());
            preparedStatement.setInt(6, tarea.getEmpleado().getId()); // Usa el ID del empleado

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(String titulo, Empleado empleado) {
        String query = "DELETE FROM tareas WHERE titulo = ? AND empleado_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, titulo);
            preparedStatement.setInt(2, empleado.getId()); // Usa el ID del empleado

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para obtener el empleado por su ID
    private Empleado obtenerEmpleadoPorId(int empleadoId) {
        Empleado empleado = null;
        String query = "SELECT * FROM empleados WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, empleadoId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                empleado = new Empleado(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getInt("edad"),
                        resultSet.getString("cargo"),
                        resultSet.getString("departamento"),
                        resultSet.getDouble("salario")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleado;
    }
}

/*
import org.example.domain.Empleado;
import org.example.domain.Tarea;
import org.example.interfaces.repository.TareaInterfazRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TareaRepositoryImpl implements TareaInterfazRepository {
    private static final String FILE_NAME = "tareas.txt";

    @Override
    public List<Tarea> cargar() {
        List<Tarea> tareas = new ArrayList<>();
        try (ObjectInputStream oIs = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            tareas = (List<Tarea>) oIs.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado, se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tareas;
    }

    @Override
    public Tarea buscarTarea(String titulo, Empleado empleado) {
        List<Tarea> tareas = cargar();
        for (Tarea tarea : tareas) {
            if (tarea.getTitulo().equals(titulo) && tarea.getEmpleado().equals(empleado)) {
                return tarea;
            }
        }
        return null;
    }

    @Override
    public void guardar(Tarea tarea) {
        List<Tarea> tareas = cargar();
        tareas.add(tarea);
        guardarLista(tareas);
    }

    @Override
    public void update(Tarea tarea) {
        List<Tarea> tareas = cargar();
        for (int i = 0; i < tareas.size(); i++) {  // Corrección del índice
            if (tareas.get(i).getTitulo().equals(tarea.getTitulo()) && tareas.get(i).getEmpleado().equals(tarea.getEmpleado())) {
                tareas.set(i, tarea); // Actualiza la tarea
                break;
            }
        }
        guardarLista(tareas);
    }

    @Override
    public void eliminar(String titulo, Empleado empleado) {
        List<Tarea> tareas = cargar();
        tareas.removeIf(tarea -> tarea.getTitulo().equals(titulo) && tarea.getEmpleado().equals(empleado)); // Corregido
        guardarLista(tareas);
    }

    // Método privado para guardar la lista completa de tareas en el archivo
    private void guardarLista(List<Tarea> tareas) {
        try (ObjectOutputStream oOs = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {  // Corregido
            oOs.writeObject(tareas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 */
