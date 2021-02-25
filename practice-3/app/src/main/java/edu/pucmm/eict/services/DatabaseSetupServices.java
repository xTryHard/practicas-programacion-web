package edu.pucmm.eict.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.tools.Server;

public class DatabaseSetupServices {


  private static Server server;

    /**
     *
    //  * @throws SQLException
    //  */
    public static void startDb() throws SQLException {
        server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-ifNotExists").start();
    }

    /**
     *
     * @throws SQLException
     */
    public static void stopDb() throws SQLException {
        server.shutdown();
    }

        /**
     * Metodo para recrear las tablas necesarios
     * @throws SQLException
     */
    public static void crearTablas() throws  SQLException{
      // String sql = "CREATE TABLE IF NOT EXISTS ESTUDIANTE\n" +
      //         "(\n" +
      //         "  MATRICULA INTEGER PRIMARY KEY NOT NULL,\n" +
      //         "  NOMBRE VARCHAR(100) NOT NULL,\n" +
      //         "  APELLIDO VARCHAR(100) NOT NULL,\n" +
      //         "  TELEFONO VARCHAR(25) NOT NULL,\n" +
      //         "  CARRERA VARCHAR(50) NOT NULL\n" +
      //         ");";
      // Connection con = DataBaseServices.getInstancia().getConexion();
      // Statement statement = con.createStatement();
      // statement.execute(sql);
      // statement.close();
      // con.close();
      createProductTable();
  }

  private static void createProductTable() {
    String sqlQuery = "CREATE TABLE IF NOT EXISTS Product\n" + 
                  "(\n" +
                  " id INTEGER PRIMARY KEY NOT NULL,\n" +
                  "name VARCHAR(60) NOT NULL,\n" +
                  "price DECIMAL NOT NULL\n" + 
                  ");";
    
    Connection conn = DataBaseConnServices.getInstance().getConn();
    Statement statement = conn.createStatement();
    statement.execute(sqlQuery);
    statement.close();
    conn.close();
  }

  private static void createUserTable() {
    String sqlQuery = "CREATE TABLE IF NOT EXISTS User\n" + 
                  "(\n" +
                  " username VARCHAR(30) PRIMARY KEY NOT NULL,\n" +
                  "name VARCHAR(30) NOT NULL,\n" +
                  "password VARCHAR(20) NOT NULL,\n" + 
                  ");";
    
    Connection conn = DataBaseConnServices.getInstance().getConn();
    Statement statement = conn.createStatement();
    statement.execute(sqlQuery);
    statement.close();
    conn.close();
  }

  private static void createSellTable() {
    String sqlQuery = "CREATE TABLE IF NOT EXISTS Sell\n" + 
                  "(\n" +
                  " id INTEGER PRIMARY KEY NOT NULL,\n" +
                  " sellDate DATE NOT NULL,\n" +
                  " clientName VARCHAR(30) NOT NULL,\n" + 
                  ");";
    
    Connection conn = DataBaseConnServices.getInstance().getConn();
    Statement statement = conn.createStatement();
    statement.execute(sqlQuery);
    statement.close();
    conn.close();
  }

  private static void createSellProductTable() {
    String sqlQuery = "CREATE TABLE IF NOT EXISTS SellProduct\n" + 
                  "(\n" +
                  " sellId INTEGER PRIMARY KEY NOT NULL,\n" +
                  " productId INTEGER NOT NULL,\n" +
                  "amount INTEGER NOT NULL,\n" + 
                  ");";
    
    Connection conn = DataBaseConnServices.getInstance().getConn();
    Statement statement = conn.createStatement();
    statement.execute(sqlQuery);
    statement.close();
    conn.close();
  }

  private static void alterSellProductTable() {
    String sqlQuery = "ALTER TABLE SellProduct\n" + 
                  "\n" +
                  "ADD CONSTRAINT SellProduct_sellId FOREIGN KEY sellId REFERENCES Sell (id),\n" +
                  "ADD CONSTRAINT SellProduct_productId FOREIGN KEY productId REFERENCES Product (id);";
                  
    Connection conn = DataBaseConnServices.getInstance().getConn();
    Statement statement = conn.createStatement();
    statement.execute(sqlQuery);
    statement.close();
    conn.close();
  }



  


}