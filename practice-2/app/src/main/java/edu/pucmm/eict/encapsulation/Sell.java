
package edu.pucmm.eict.encapsulation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Sell {

  private static final AtomicInteger idCount= new AtomicInteger(0);
  private int id;
  private Date sellDate;
  private String clientName;
  private List<Product> products; 

  public Sell() {

  }

  public Sell(Date sellDate, String clientName, List<Product> products) {
    this.id = idCount.incrementAndGet();
    this.sellDate = sellDate;
    this.clientName = clientName;
    this.products = products;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getSellDate() {
    return this.sellDate;
  }

  public void setSellDate(Date sellDate) {
    this.sellDate = sellDate;
  }

  public String getClientName() {
    return this.clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public List<Product> getProducts() {
    return this.products;
  }

  public void setProducts(ArrayList<Product> products) {
    this.products = products;
  }

}