package org.example;

import lombok.Data;

@Data
public class EmployeeDTO {

  // Mapeamos los campos de la tabla Employees y Departaments.
  // Esta clase se emplea como salida de datos.
  private Integer id;
  private String name;
  private String email;
  private Integer departmentId;
  private String departmentName;

}
