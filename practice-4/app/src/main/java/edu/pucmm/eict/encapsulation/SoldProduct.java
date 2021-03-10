
package edu.pucmm.eict.encapsulation;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "SOLDPRODUCTS")
public class SoldProduct implements Serializable {

  
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private int id;
  private String name;
  private BigDecimal price;
  private int amount;
  private BigDecimal totalPrice;

  public SoldProduct() {

  }

  public SoldProduct(int id, String name, BigDecimal price, int amount, BigDecimal totalPrice) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.amount = amount;
    this.totalPrice = totalPrice;
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
  
  public void setPrice(BigDecimal price) {
    this.price = price;
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