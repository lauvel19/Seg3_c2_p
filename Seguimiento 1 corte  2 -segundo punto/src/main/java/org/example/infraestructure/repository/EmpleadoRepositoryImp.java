package org.example.infraestructure.repository;


import org.example.domain.Empleado;
import org.example.infraestructure.repository.Recursos.DatabaseConnection;
import org.example.interfaces.repository.EmpleadoInterfazRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepositoryImp implements EmpleadoInterfazRepository {

    @Override
    public List<Empleado> cargar() {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT * FROM empleados";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Empleado empleado = new Empleado(
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getInt("edad"),
                        resultSet.getString("cargo"),
                        resultSet.getString("departamento"),
                        resultSet.getDouble("salario")
                );
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleados;
    }

    @Override
    public Empleado buscarEmpleado(String nombre, String apellido, String cargo) {
        Empleado empleado = null;
        String query = "SELECT * FROM empleados WHERE nombre = ? AND apellido = ? AND cargo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, cargo);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                empleado = new Empleado(
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

    @Override
    public void guardar(Empleado empleado) {
        String query = "INSERT INTO empleados (nombre, apellido, edad, cargo, departamento, salario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, empleado.getNombre());
            preparedStatement.setString(2, empleado.getApellido());
            preparedStatement.setInt(3, empleado.getEdad());
            preparedStatement.setString(4, empleado.getCargo());
            preparedStatement.setString(5, empleado.getDepartamento());
            preparedStatement.setDouble(6, empleado.getSalario());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Empleado empleado) {
        String query = "UPDATE empleados SET edad = ?, cargo = ?, departamento = ?, salario = ? WHERE nombre = ? AND apellido = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, empleado.getEdad());
            preparedStatement.setString(2, empleado.getCargo());
            preparedStatement.setString(3, empleado.getDepartamento());
            preparedStatement.setDouble(4, empleado.getSalario());
            preparedStatement.setString(5, empleado.getNombre());
            preparedStatement.setString(6, empleado.getApellido());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(String nombre, String apellido, String cargo) {
        String query = "DELETE FROM empleados WHERE nombre = ? AND apellido = ? AND cargo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, cargo);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


/*
import org.example.domain.Empleado;
import org.example.interfaces.repository.EmpleadoInterfazRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepositoryImp implements EmpleadoInterfazRepository {
    private static final String FILE_NAME = "empleados.txt";

    @Override
    public List<Empleado> cargar() {
        List<Empleado> empleados = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            empleados = (List<Empleado>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado, se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public Empleado buscarEmpleado(String nombre, String apellido, String cargo) {
        List<Empleado> empleados = cargar();
        for (Empleado empleado : empleados) {
            if (empleado.getNombre().equals(nombre) && empleado.getApellido().equals(apellido) && empleado.getCargo().equals(cargo)) {
                return empleado;
            }
        }
        return null;
    }

    @Override
    public void guardar(Empleado empleado) {
        List<Empleado> empleados = cargar();
        empleados.add(empleado);
        guardarLista(empleados);
    }

    @Override
    public void update(Empleado empleado) {
        List<Empleado> empleados = cargar();
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getNombre().equals(empleado.getNombre()) &&
                    empleados.get(i).getApellido().equals(empleado.getApellido()) &&
                    empleados.get(i).getCargo().equals(empleado.getCargo())) {
                empleados.set(i, empleado);
                break;
            }
        }
        guardarLista(empleados);
    }

    @Override
    public void eliminar(String nombre, String apellido, String cargo) {
        List<Empleado> empleados = cargar();
        empleados.removeIf(emp -> emp.getNombre().equals(nombre) && emp.getApellido().equals(apellido) && emp.getCargo().equals(cargo));
        guardarLista(empleados);
    }

    // Método privado para guardar la lista completa de empleados en el archivo
    private void guardarLista(List<Empleado> empleados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(empleados);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

 */


