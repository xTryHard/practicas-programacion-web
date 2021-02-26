
package edu.pucmm.eict.services;

import java.sql.PreparedStatement;

import edu.pucmm.eict.encapsulation.Product;
import edu.pucmm.eict.encapsulation.Sell;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellServices {

  public SellServices() {

  }

  public boolean createSell(Sell sell) {
    boolean ok =false;

    Connection conn = null;
    try {

        String query = "insert into sell(id, selldate, clientname) values(?,?,?)";
        conn = DataBaseConnServices.getInstance().getConn();
        //
        PreparedStatement prepareStatement = conn.prepareStatement(query);
        //Antes de ejecutar seteo los parametros.
        prepareStatement.setInt(1, sell.getId());
        prepareStatement.setDate(2, new Date(sell.getSellDate().getTime()));
        prepareStatement.setString(3, sell.getClientName());

        int fila = prepareStatement.executeUpdate();
        ok = fila > 0 ;

    } catch (SQLException ex) {
        // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(ex.toString());
    } finally{
        try {
            conn.close();
        } catch (SQLException ex) {
            // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
    }

    return ok;
  }

  public boolean createSellProduct(Sell sell, List<Integer> amountList){
    boolean ok = false;

    Connection conn = null;
    int i = 0;
    for (Product product : sell.getProducts()) {
      try {

        String query = "insert into sellproduct(sellid, productid, amount) values(?,?,?)";
        conn = DataBaseConnServices.getInstance().getConn();
        //
        PreparedStatement prepareStatement = conn.prepareStatement(query);
        //Antes de ejecutar seteo los parametros.
        prepareStatement.setInt(1, sell.getId());
        prepareStatement.setInt(2, product.getId());
        prepareStatement.setInt(3, amountList.get(i));

        int fila = prepareStatement.executeUpdate();
        ok = fila > 0 ;
        i++;
    } catch (SQLException ex) {
        // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println(ex.toString());
      } finally{
          try {
            conn.close();
          } catch (SQLException ex) {
            // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
          }
      }

    }
    return ok;      
  }

  // public List<Sell> getAllSells() {
  //   List<Sell> sells = new ArrayList<>();
  //   Connection conn = null;
  //   try {
  //       //
  //       String query = "select * from Product;";
  //       conn = DataBaseConnServices.getInstance().getConn(); 
  //       PreparedStatement prepareStatement = conn.prepareStatement(query);
  //       ResultSet rs = prepareStatement.executeQuery();
  //       while(rs.next()){
  //           Product product = new Product();
  //           product.setId(rs.getInt("id"));
  //           product.setName(rs.getString("name"));
  //           product.setPrice(rs.getBigDecimal("price"));

  //           products.add(product);
  //       }

  //   } catch (SQLException ex) {
  //       // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
  //       System.out.println(ex.toString());
  //   } finally{
  //       try {
  //           conn.close();
  //       } catch (SQLException ex) {
  //           System.out.println(ex.toString());
  //       }
  //   }

  //   return products;
  // }

  public int getLastId() {
    String query = "SELECT ID FROM SELL ORDER BY ID DESC LIMIT 1;";
    Connection conn = null;
    int result = 0;

    try {
        conn = DataBaseConnServices.getInstance().getConn();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()) {
            result = resultSet.getInt(1);
        }
    } catch (SQLException ex) {
        System.out.println(ex.toString());
    } finally {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    return result;
  }
}