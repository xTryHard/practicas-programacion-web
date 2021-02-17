
package edu.pucmm.eict.encapsulation;

import java.util.List;

public class ShoppingCart {

  private String id;
  private List<Product> products;

  public ShoppingCart() {
    
  }

  public ShoppingCart(String id, List<Product> products) {
    this.id = id;
    this.products = products;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Product> getProducts() {
    return this.products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

}