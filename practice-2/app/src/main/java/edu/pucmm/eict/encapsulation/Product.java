
package edu.pucmm.eict.encapsulation;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Product {

  private static final AtomicInteger idCount = new AtomicInteger(0);
  private int id;
  private String name;
  private BigDecimal price;


  public Product() {

  }

  public Product(String name, BigDecimal price) {
    this.id = idCount.incrementAndGet();
    this.name = name;
    this.price = price;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return this.price;
  }

  public void setPrice (BigDecimal price) {
    this.price = price;
  }

}