
package edu.pucmm.eict.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataBaseConnServices {
  
  private static DataBaseConnServices  instance = null;
  
  private String URL = "jdbc:h2:tcp://localhost/~/dbpractice3";

  private DataBaseConnServices() {
    registerDriver();
  }

  public static DataBaseConnServices getInstance(){
    if(instance==null){
      instance = new DataBaseConnServices();
    }
    return instance;
  }

  private void registerDriver() {
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException ex) {
      // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
      System.out.println(ex.toString());
    }
  }

  public Connection getConn() {
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(URL, "sa", "");
    } catch (SQLException ex) {
        // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(ex.toString());
    }
    return conn;
}

  public void testConn() {
    try {
        getConn().close();
        System.out.println("Conexión realizado con exito...");
    } catch (SQLException ex) {
        // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(ex.toString());
    }
  }

  
}