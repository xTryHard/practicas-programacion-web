
package edu.pucmm.eict.encapsulation;

import java.util.List;

public class ShoppingCart {

  String id;
  List<Product> products;

  public ShoppinCart() {
    
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

  public void setProducts(List<Products> products) {
    this.products = products;
  }

}