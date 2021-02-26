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
    public static void createTables() throws  SQLException{
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
      createSellTable();
      createUserTable();
      createSellProductTable();
      // alterSellProductTable();
  } 
    /**
     * @throws SQLException
     */
  private static void createProductTable() throws  SQLException {
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

    /**
     * @throws SQLException
     */
  private static void createUserTable() throws  SQLException {
    String sqlQuery = "CREATE TABLE IF NOT EXISTS User\n" + 
                  "(\n" +
                  " username VARCHAR(30) PRIMARY KEY NOT NULL,\n" +
                  " name VARCHAR(30) NOT NULL,\n" +
                  " password VARCHAR(20) NOT NULL\n" + 
                  ");";
    
    Connection conn = DataBaseConnServices.getInstance().getConn();
    Statement statement = conn.createStatement();
    statement.execute(sqlQuery);
    statement.close();
    conn.close();
  }

    /**
     * @throws SQLException
     */
  private static void createSellTable() throws  SQLException {
    String sqlQuery = "CREATE TABLE IF NOT EXISTS Sell\n" + 
                  "(\n" +
                  " id INTEGER PRIMARY KEY NOT NULL,\n" +
                  " sellDate DATE NOT NULL,\n" +
                  " clientName VARCHAR(30) NOT NULL,\n" + 
                  " totalPrice DECIMAL NOT NULL\n" +
                  ");";
    
    Connection conn = DataBaseConnServices.getInstance().getConn();
    Statement statement = conn.createStatement();
    statement.execute(sqlQuery);
    statement.close();
    conn.close();
  }

  private static void createSellProductTable()  throws  SQLException {
    String sqlQuery = "CREATE TABLE IF NOT EXISTS SellProduct\n" + 
                  "(\n" +
                  " sellId INTEGER NOT NULL,\n" +
                  " productId INTEGER NOT NULL,\n" +
                  " productName VARCHAR(60) NOT NULL,\n" +
                  " amount INTEGER NOT NULL,\n" + 
                  " price DECIMAL NOT NULL,\n" +
                  " totalPrice DECIMAL NOT NULL,\n" +
                  " PRIMARY KEY (sellId, productId),\n" + 
                  " FOREIGN KEY (sellID) REFERENCES Sell(id)\n" +
                  ");";
    
    Connection conn = DataBaseConnServices.getInstance().getConn();
    Statement statement = conn.createStatement();
    statement.execute(sqlQuery);
    statement.close();
    conn.close();
  }

  //   /**
  //    * @throws SQLException
  //    */
  // private static void alterSellProductTable() throws  SQLException {
  //   String sqlQuery = "ALTER TABLE SellProduct\n" + 
  //                 " ADD CONSTRAINT SellProduct_sellId FOREIGN KEY sellId REFERENCES Sell (id),\n" +
  //                 "ADD CONSTRAINT SellProduct_productId FOREIGN KEY productId REFERENCES Product (id)\n"+";";
                  
  //   Connection conn = DataBaseConnServices.getInstance().getConn();
  //   Statement statement = conn.createStatement();
  //   statement.execute(sqlQuery);
  //   statement.close();
  //   conn.close();
  // }

}