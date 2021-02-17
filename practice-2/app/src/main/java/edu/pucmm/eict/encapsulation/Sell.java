
package edu.pucmm.eict.encapsulation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Sell {

  private String id;
  private Date sellDate;
  private String clientName;
  private List<Product> products; 

  public Sell() {

  }

  public Sell(String id, Date sellDate, String clientName, List<Product> products) {
    this.id = id;
    this.sellDate = sellDate;
    this.clientName = clientName;
    this.products = products;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
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