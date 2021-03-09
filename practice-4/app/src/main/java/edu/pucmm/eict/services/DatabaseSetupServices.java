package edu.pucmm.eict.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.tools.Server;

public class DatabaseSetupServices {


  private static Server server;
  private static DatabaseSetupServices instance = null;

  private DatabaseSetupServices() {

  }

  public static DatabaseSetupServices getInstance() {
    if (instance == null) {
      instance = new DatabaseSetupServices();
    }
    return instance;
  }

    /**
     *
    //  * @throws SQLException
    //  */
    public static void startDb() throws SQLException {
        server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-ifNotExists").start();

        String status = Server.createWebServer("-trace", "-webPort", "0").start().getStatus();
        System.out.println("Web Status: "+status);
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

}