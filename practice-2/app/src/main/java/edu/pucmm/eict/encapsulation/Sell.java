
package edu.pucmm.eict.encapsulation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
  private Object[] productsObject;
  private Object[] amountProductList;
  private Object[] productTotalPrice;
  private BigDecimal totalPrice;
  private String sellDateStr;
  public Sell() {

  }

  public Sell(Date sellDate, String clientName, List<Product> products, List<Integer> amountProductList, List<BigDecimal> productTotalPrice, BigDecimal totalPrice) {
    this.id = idCount.incrementAndGet();
    this.sellDate = sellDate;
    this.clientName = clientName;
    this.products = products;
    this.productsObject = products.toArray();
    this.amountProductList = amountProductList.toArray();
    this.productTotalPrice = productTotalPrice.toArray();
    this.totalPrice = totalPrice;
    this.sellDateStr = this.getFormat(sellDate);
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

  public Object[] getProductObjects() {
    return this.productsObject;
  }

  public Object[] getAmountProductList() {
    return this.amountProductList;
  }

  public Object[] getProductTotalPrice() {
    return this.productTotalPrice;
  }
  
  public BigDecimal getTotalPrice() {
    return this.totalPrice;
  }

  public String getSellDateStr() {
    return this.sellDateStr;
  }

  private String getFormat(Date date) {

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
    return format.format(date);
    
  }

}