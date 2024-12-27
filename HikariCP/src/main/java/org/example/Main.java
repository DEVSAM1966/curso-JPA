package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    // Pasamos los datos de conexión como jdbc, usuario, contraseña
    var config = new HikariConfig();
    config.setJdbcUrl("jdbc:h2:mem:test");
    config.setUsername("sa");
    config.setPassword("");

    // Ejecutamos los métodos executeSqlScript y selectAllEmployees.
    try (HikariDataSource ds = new HikariDataSource(config)) {
      executeSqlScript(ds);
      var employees = selectAllEmployees(ds);

      employees.forEach(System.out::println);
    } catch (SQLException sqlE) {
      System.err.println(sqlE.getMessage());
    }
  }

  // Metodo para generar la BD mediante schema.sql
  private static void executeSqlScript(HikariDataSource ds) throws SQLException {
    // Indicamos la ruta del fichero schema.sql para crear la BD.
    final var path = "src/main/resources/schema.sql";

    try (var connection = ds.getConnection(); var statement = connection.createStatement()) {
      // Lanzamos el script schema.sql
      var fis = new FileInputStream(path);
      var script = new Scanner(fis, StandardCharsets.UTF_8).useDelimiter("\\A").next();
      statement.execute(script);

    } catch (FileNotFoundException e){
      // En caso de error lanzamos la excepción y mostramos el error.
      throw new RuntimeException(e);
    }
  }

  // Método para sealizar una SELECT a la BD.
  private static List<EmployeeDTO> selectAllEmployees(HikariDataSource ds) throws SQLException {
    var employees = new ArrayList<EmployeeDTO>();

    try (var connection = ds.getConnection(); var statement = connection.createStatement()) {

      ResultSet rs = statement.executeQuery("SELECT " +
          "e.id, e.name, e.email, e.department_id, d.name AS department_name " +
          "FROM employees e " +
          "JOIN departments d ON e.department_id = d.id");

      while (rs.next()) {
        EmployeeDTO employee = new EmployeeDTO();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setEmail(rs.getString("email"));
        employee.setDepartmentId(rs.getInt("department_id"));
        employee.setDepartmentName(rs.getString("department_name"));
        employees.add(employee);
      }
    }

    return employees;
  }
}