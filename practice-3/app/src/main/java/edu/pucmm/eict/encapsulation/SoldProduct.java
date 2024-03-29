
package edu.pucmm.eict.encapsulation;

import java.math.BigDecimal;

public class SoldProduct extends Product {

  private int amount;
  private BigDecimal totalPrice;

  public SoldProduct() {

  }

  public SoldProduct(int id, String name, BigDecimal price, int amount, BigDecimal totalPrice) {
    this.setId(id);
    this.setName(name);
    this.setPrice(price);
    this.amount = amount;
    this.totalPrice = totalPrice;
  }

  public int getAmount() {
    return this.amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public BigDecimal getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

}