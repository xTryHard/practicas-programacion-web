
package edu.pucmm.eict.encapsulation;

import java.math.BigDecimal;

public class Product {

  String id;
  String name;
  BigDecimal price;

  public Product() {

  }

  public Product(String id, String name, BigDecimal price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
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