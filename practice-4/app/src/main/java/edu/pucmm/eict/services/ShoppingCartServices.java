
package edu.pucmm.eict.services;

import edu.pucmm.eict.encapsulation.Product;
import edu.pucmm.eict.encapsulation.User;
import edu.pucmm.eict.encapsulation.Sell;


import java.util.ArrayList;
import java.util.List;

// Main services class. Handles all models and application context data
public class ShoppingCartServices {

  private static ShoppingCartServices instance = null;
  private List<User> users = new ArrayList<User>();
  private List<Product> products = new ArrayList<Product>();
  private List<Sell> sells = new ArrayList<Sell>();
  private boolean adminMode = false;
  private ProductServices productServices = new ProductServices();
  private UserServices userServices = new UserServices();
  private SellServices sellServices = new SellServices();

  private ShoppingCartServices() {

    //Arreglar esto y que solo cargue cuando 
    this.products = productServices.getAllProducts();
    this.users = userServices.getAllUsers();
    this.sells = sellServices.getAllSells();
    this.setSellProducts();
    createDefaultUser();
  }

  public static ShoppingCartServices getInstance() {

    if (instance == null) {
      instance = new ShoppingCartServices();
    }

    return instance;

  }

  private void createDefaultUser() {
    
    if (!userServices.existsUser("admin")) {
      User user = new User("admin", "admin", "admin");
      boolean done = userServices.createUser(user);
  
      if (done) {
        users.add(user);
      } else {
        //
      }
    } else {
      System.out.println("Usuario existente!");
    }
  }

  private void setSellProducts() {

    for (Sell sell : this.sells) {
    
      sell.setSoldProducts(sellServices.getSellProducts(sell));

      // ShoppingCart shoppingCart = new Shopp
    }
  }


  public Product getProductById(int id) {
    return this.products.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
  }

  public User existsUser(User user) {
    String username = user.getUserName();
    return this.users.stream().filter(e -> e.getUserName().equals(username)).findFirst().orElse(null);

  } 

  //Returns true if created, false if exists a product with the ID;
  public boolean createProduct(Product product) {
    if (this.getProductById(product.getId()) != null) {
      return false;
    }
    this.products.add(product);
    return true;
  }

  public boolean createUser(User user) {

    if (this.existsUser(user) != null) {
      return false;
    }
    
    this.users.add(user);
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

    // foundProduct.setId(product.getId());
    foundProduct.setName(product.getName());
    foundProduct.setPrice(product.getPrice());

    return true;
  }

  public boolean deleteProduct(int id) {
    return products.remove(this.getProductById(id));
  }

  public void addSell(Sell sell) {
    this.sells.add(sell);
  }

  public List<Sell> getSells() {
    return this.sells;
  }

  public List<User> getUsers() {
    return this.users;
  }

  public boolean validateAdmin(String username, String password){
    for (User user : users) {
      if (user.getUserName().equalsIgnoreCase(username) && user.getPassword().equals(password)) return true;
    }
    return false;
  }

  public boolean getAdminMode() {
    return this.adminMode;
  }

  public void setAdminMode(boolean mode) {
    this.adminMode = mode;
  }

}