
package edu.pucmm.eict.encapsulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class ShoppingCart {

  private static final AtomicInteger idCount = new AtomicInteger(0);
  private int id;
  private List<Product> products;
  private HashMap<String, Integer> productAmount;

  public ShoppingCart() {
    this.products = new ArrayList<Product>();
    this.id = idCount.incrementAndGet();
    this.productAmount = new HashMap<>();
  }

  public ShoppingCart(List<Product> products) {
    this.id = idCount.incrementAndGet();
    this.products = products;
    this.productAmount = new HashMap<>();
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

  public void addProduct(Product product, int amount) {

    if (isInShoppingCart(product)) {
      int newAmount = getAmount(product) + amount;
      this.productAmount.put(String.valueOf(product.getId()), newAmount);
    } else {
      this.products.add(product);
      this.productAmount.put(String.valueOf(product.getId()), amount);
    }
  }

  private boolean isInShoppingCart(Product product) {
    String id = String.valueOf(product.getId());
    if (productAmount.get(id) == null) return false;
    return true;
  }

  private int getAmount(Product product) {
    String id = String.valueOf(product.getId());
    return productAmount.get(id);
  }

  public int getTotalAmount() {
    int totalElements = 0;
    for (Entry<String, Integer> entry : productAmount.entrySet()) {
      totalElements += entry.getValue();
    }
    return totalElements;
  }

}