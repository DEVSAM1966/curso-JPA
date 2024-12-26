package org.example;

import lombok.Getter;
import org.hibernate.SessionFactory;
import java.util.Properties;
import java.io.FileInputStream;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
  // Defino CONFIG_PATH para indicar la ruta y el fichero de properties de Hibernate.
  private static final String CONFIG_PATH = "src/main/resources/hibernate.properties";

  // Defino sessionFactory para crear una SessionFactory.
  @Getter
  private static final SessionFactory sessionFactory = buildSessionFactory();

  private static SessionFactory buildSessionFactory() {
    var properties = new Properties();
    try {
      properties.load(new FileInputStream(CONFIG_PATH));

      return new Configuration()
          .mergeProperties(properties)
          .addAnnotatedClass(EmployeeEntity.class)
          .addAnnotatedClass(DepartmentEntity.class)
          .buildSessionFactory();

    }catch (Throwable throwable){
        System.err.println(throwable.getMessage());
        throw new ExceptionInInitializerError(throwable);
      }
  }

  public static void shutdown() {
    getSessionFactory().close();
  }

}
