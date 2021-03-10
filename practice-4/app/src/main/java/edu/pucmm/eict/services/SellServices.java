
package edu.pucmm.eict.services;

import java.sql.PreparedStatement;

import edu.pucmm.eict.encapsulation.Sell;
import edu.pucmm.eict.encapsulation.SoldProduct;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellServices extends DatabaseOrmHandler<Sell>{

    private static SellServices instance = null;

    private SellServices() {
        super(Sell.class);
    }
    
    public static SellServices getInstance() {
        if (instance == null) {
            instance = new SellServices();
        }
        return instance;
    }

    // public boolean createSell(Sell sell) {
    //     boolean ok = false;

    //     Connection conn = null;
    //     try {

    //         String query = "insert into sell(id, selldate, clientname, totalprice) values(?,?,?, ?)";
    //         conn = DataBaseConnServices.getInstance().getConn();
    //         //
    //         PreparedStatement prepareStatement = conn.prepareStatement(query);
    //         // Antes de ejecutar seteo los parametros.
    //         prepareStatement.setInt(1, sell.getId());
    //         prepareStatement.setDate(2, new java.sql.Date(sell.getSellDate().getTime()));
    //         prepareStatement.setString(3, sell.getClientName());
    //         prepareStatement.setBigDecimal(4, sell.getTotalPrice());
    //         int fila = prepareStatement.executeUpdate();
    //         ok = fila > 0;

    //     } catch (SQLException ex) {
    //         // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null,
    //         // ex);
    //         System.out.println(ex.toString());
    //     } finally {
    //         try {
    //             conn.close();
    //         } catch (SQLException ex) {
    //             // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null,
    //             // ex);
    //             System.out.println(ex.toString());
    //         }
    //     }

    //     return ok;
    // }

    // public boolean createSellProduct(Sell sell) {
    //     boolean ok = false;

    //     Connection conn = null;
    //     for (SoldProduct product : sell.getSoldProducts()) {
    //         try {

    //             String query = "insert into sellproduct(sellid, productid, productname, amount, price, totalprice) values(?,?,?,?,?,?)";
    //             conn = DataBaseConnServices.getInstance().getConn();
    //             //
    //             PreparedStatement prepareStatement = conn.prepareStatement(query);
    //             // Antes de ejecutar seteo los parametros.
    //             prepareStatement.setInt(1, sell.getId());
    //             prepareStatement.setInt(2, product.getId());
    //             prepareStatement.setString(3, product.getName());
    //             prepareStatement.setInt(4, product.getAmount());
    //             prepareStatement.setBigDecimal(5, product.getPrice());
    //             prepareStatement.setBigDecimal(6, product.getTotalPrice());

    //             int fila = prepareStatement.executeUpdate();
    //             ok = fila > 0;
    //         } catch (SQLException ex) {
    //             // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null,
    //             // ex);
    //             System.out.println(ex.toString());
    //         } finally {
    //             try {
    //                 conn.close();
    //             } catch (SQLException ex) {
    //                 // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null,
    //                 // ex);
    //                 System.out.println(ex.toString());
    //             }
    //         }

    //     }
    //     return ok;
    // }

    // public List<Sell> getAllSells() {
    //     List<Sell> sells = new ArrayList<>();

    //     Connection conn = null;
    //     try {
    //         //
    //         String query = "select * from Sell;";
    //         conn = DataBaseConnServices.getInstance().getConn();
    //         PreparedStatement prepareStatement = conn.prepareStatement(query);
    //         ResultSet rs = prepareStatement.executeQuery();
    //         while (rs.next()) {
    //             Sell sell = new Sell();
    //             sell.setId(rs.getInt("id"));
    //             sell.setSellDate(new Date(rs.getDate("selldate").getTime()));
    //             sell.setClientName(rs.getString("clientname"));
    //             sell.setTotalPrice(rs.getBigDecimal("totalprice"));
    //             sells.add(sell);
    //             System.out.println(new Date(rs.getDate("selldate").getTime()));
    //         }

    //     } catch (SQLException ex) {
    //         // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null,
    //         // ex);
    //         System.out.println(ex.toString());
    //     } finally {
    //         try {
    //             conn.close();
    //         } catch (SQLException ex) {
    //             System.out.println(ex.toString());
    //         }
    //     }

    //     return sells;
    // }

    // public List<SoldProduct> getSellProducts(Sell sell) {
    //     int sellId = sell.getId();

    //     List<SoldProduct> soldProducts = new ArrayList<>();

    //     Connection conn = null;
    //     try {
    //         //
    //         String query = "select * from SellProduct where sellid = ?;";
    //         conn = DataBaseConnServices.getInstance().getConn();
    //         PreparedStatement prepareStatement = conn.prepareStatement(query);
    //         prepareStatement.setInt(1, sellId);
    //         ResultSet rs = prepareStatement.executeQuery();
    //         while (rs.next()) {
    //             SoldProduct soldProduct = new SoldProduct();
    //             soldProduct.setId(rs.getInt("productid"));
    //             soldProduct.setName(rs.getString("productname"));
    //             soldProduct.setAmount(rs.getInt("amount"));
    //             soldProduct.setPrice(rs.getBigDecimal("price"));
    //             soldProduct.setTotalPrice(rs.getBigDecimal("totalPrice"));
    //             soldProducts.add(soldProduct);
    //         }

    //     } catch (SQLException ex) {
    //         // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null,
    //         // ex);
    //         System.out.println(ex.toString());
    //     } finally {
    //         try {
    //             conn.close();
    //         } catch (SQLException ex) {
    //             System.out.println(ex.toString());
    //         }
    //     }

    //     return soldProducts;
    // }

    // public int getLastId() {
    //     String query = "SELECT ID FROM SELL ORDER BY ID DESC LIMIT 1;";
    //     Connection conn = null;
    //     int result = 0;

    //     try {
    //         conn = DataBaseConnServices.getInstance().getConn();
    //         Statement statement = conn.createStatement();
    //         ResultSet resultSet = statement.executeQuery(query);

    //         while (resultSet.next()) {
    //             result = resultSet.getInt(1);
    //         }
    //     } catch (SQLException ex) {
    //         System.out.println(ex.toString());
    //     } finally {
    //         try {
    //             conn.close();
    //         } catch (SQLException ex) {
    //             System.out.println(ex.toString());
    //         }
    //     }
    //     return result;
    // }
}