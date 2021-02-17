
package edu.pucmm.eict.encapsulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShoppingCart {

  private static final AtomicInteger idCount = new AtomicInteger(0);
  private int id;
  private List<Product> products;

  public ShoppingCart() {
    this.products = new ArrayList<Product>();
  }

  public ShoppingCart(List<Product> products) {
    this.id = idCount.incrementAndGet();
    this.products = products;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Product> getProducts() {
    return this.products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public void addProduct(Product product) {
    this.products.add(product);
  }

}