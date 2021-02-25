
package edu.pucmm.eict.services;

import java.sql.SQLException;

import edu.pucmm.eict.encapsulation.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServices {
  
  public ProductServices() {

  }

    public boolean createProduct(Product product) {
        boolean ok =false;

        Connection conn = null;
        try {

            String query = "insert into product(id, name, price) values(?,?,?)";
            conn = DataBaseConnServices.getInstance().getConn();
            //
            PreparedStatement prepareStatement = conn.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setInt(1, product.getId());
            prepareStatement.setString(2, product.getName());
            prepareStatement.setBigDecimal(3, product.getPrice());
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

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        try {
            //
            String query = "select * from Product;";
            conn = DataBaseConnServices.getInstance().getConn(); 
            PreparedStatement prepareStatement = conn.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));

                products.add(product);
            }

        } catch (SQLException ex) {
            // Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }

        return products;
    }


    public int getLastId() {
        String query = "SELECT ID FROM PRODUCT ORDER BY ID DESC LIMIT 1;";
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