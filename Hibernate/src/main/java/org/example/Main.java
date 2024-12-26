package org.example;

import org.hibernate.query.Query;

public class Main {

  public static void main(String[] args) {

    try(var session=HibernateConfig.getSessionFactory().openSession()){
      final var transaction = session.beginTransaction();

      // Creo un 1 registro de Departaments empleando @builder, @AllArgsConstructor
      var weightDivision = DepartmentEntity
          .builder()
          .name("Lightweight")
          .build();

      // Salvamos los cambios de la sesión
      session.save(weightDivision);

      // Creamos dos empleados y vamos a emplear @NoArgsConstructor
      var fighter = new EmployeeEntity();
      fighter.setName("Conor McGregor");
      fighter.setEmail("conor@ufc.com");
      fighter.setDepartment(weightDivision);

      // Salvamos los cambios de la sesión
      session.save(fighter);

      fighter = new EmployeeEntity();
      fighter.setName("Khabib Nurmagomedov");
      fighter.setEmail("khabib@ufc.com");
      fighter.setDepartment(weightDivision);

      // Salvamos los cambios de la sesión
      session.save(fighter);

      // Grabamos los datos a BD.
      transaction.commit();

      // Realizamos la consulta.
      Query<EmployeeEntity> query = session.createQuery(
          "FROM EmployeeEntity e JOIN FETCH e.department", EmployeeEntity.class);

      query.list().forEach(System.out::println);

    }
  }
}