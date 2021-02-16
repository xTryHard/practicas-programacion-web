
package edu.pucmm.eict.services;

import edu.pucmm.eict.encapsulation.Product;
import edu.pucmm.eict.encapsulation.User;
import edu.pucmm.eict.encapsulation.Sell;

import java.util.ArrayList;
import java.util.List;

// Main services class. Handles all models and application context data
public class ShoppingCartServices {

  private ShoppingCartServices instance = null;
  private List<User> users = new ArrayList<User>();
  private List<Product> products = new ArrayList<Product>();
  private List<Sell> sells = new ArratList<Sell>();

  private ShoppingCartServices() {
    users.add(new User("admin", "admin", "admin"));
  }

  public static ShoppingCartServices getInstance() {

    if (instance == null) {
      this.instance = this.instance;
    }

    return this.instance;

  }

  private Product getProductById(String id) {
    return this.products.stream().filter(e -> e.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
  }

  //Returns true if created, false if exists a product with the ID;
  public boolean createProduct(Product product) {
    if (this.getProductById(product.getId()) != null) {
      return false;
    }
    this.products.add(product);
    return true;
  }

  public List<Product> getProducts() {
    return this.products;
  }

  //Returns true if product was found and updated; false if for some reason the product was not found
  public boolean updateProduct(Product product) {

    Product foundProduct = this.getProductById(product.getId());
    if (foundProduct == null) {
      return false;
    }

    foundProduct.setId(product.getId());
    foundProduct.setName(product.getName());
    foundProduct.setPrice(product.getPrice());

    return true;
  }

  public boolean deleteProduct(Product product) {
    return products.remove(product);
  }

  public void addSell(Sell sell) {
    this.sells.add(sell);
  }

  public List<Sell> getSells() {
    return this.sells;
  }

}