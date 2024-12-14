package org.example;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) {
    // Crear la conexión a BD.  Se llamará test.
    final var jdbcUrl = "jdbc:h2:~/test;DB_CLOSE_DELAY=-1";

    // Indicaremos usuario y contraseña
    final var userDB = "sa";
    final var password = "";

    // Indicamos el script SQL para generar datos.
    final var scriptPath = "src/main/resources/schema.sql";
    final var initDB = "RUNSCRIPT FROM '" + scriptPath + "'";

    // Query para traernos datos de la BD.
    final String query = "SELECT e.id, e.name, e.email, e.department_id, d.name AS department_name "
        + "FROM employees e "
        + "JOIN departments d ON e.department_id = d.id";

    //Realizamos la conexión y lanzamos la query.
    try(var connection = DriverManager.getConnection(jdbcUrl, userDB, password)){

      // Activamos la conexión a la BD
      var statement = connection.createStatement();

      // Lanzamos el script schema.sql (crea la BD).
      statement.execute(initDB);

      // Ejecutamos nuestra query.
      var rs = statement.executeQuery(query);
      var employess = new ArrayList<EmployeeDTO>();

      while (rs.next()){
        var employee = new EmployeeDTO();
        // Nos traemos los datos de Employee
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setEmail(rs.getString("email"));

        // Nos traemos los datos Departaments
        employee.setDepartmentId(rs.getInt("department_id"));
        employee.setDepartmentName(rs.getString("department_name"));

        // Cada iteración la guardamos en el objeto employess que es un objeto EmployeeDTO.
        employess.add(employee);

      }

      // Imprimimos el employess que es un objeto EmployeeDTO.
      employess.forEach(System.out::println);


    }catch(SQLException e) {
      System.err.println(e.getMessage());
    }


  }
}