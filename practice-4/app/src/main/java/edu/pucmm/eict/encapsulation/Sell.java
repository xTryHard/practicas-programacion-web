
package edu.pucmm.eict.encapsulation;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
// import java.util.concurrent.atomic.AtomicInteger;

// import edu.pucmm.eict.services.SellServices;

import java.io.Serializable;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "SELLS")
public class Sell implements Serializable{

  // private static final AtomicInteger idCount= new AtomicInteger(setAtomic());
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private Date sellDate;
  private String clientName;

  @OneToMany(fetch = FetchType.EAGER)
  private List<SoldProduct> soldProducts;

  @Transient
  private String sellDateStr;

  @Transient
  private List<Product> products; 

  @Transient
  private Object[] productsObject;

  @Transient
  private Object[] amountProductList;

  @Transient
  private Object[] productTotalPrice;

  @Transient
  private BigDecimal totalPrice;

  public Sell() {

  }

  public Sell(Date sellDate, String clientName, List<SoldProduct> soldproducts, BigDecimal totalPrice){
    // this.id = idCount.incrementAndGet();
    this.sellDate =  sellDate;
    this.clientName = clientName;
    this.soldProducts = soldproducts;
    this.totalPrice = totalPrice;
    this.sellDateStr = this.getFormat(sellDate);
  }

  public Sell(Date sellDate, String clientName, List<Product> products, List<Integer> amountProductList, List<BigDecimal> productTotalPrice, BigDecimal totalPrice) {
    // this.id = idCount.incrementAndGet();
    this.sellDate = sellDate;
    this.clientName = clientName;
    this.products = products;
    this.productsObject = products.toArray();
    this.amountProductList = amountProductList.toArray();
    this.productTotalPrice = productTotalPrice.toArray();
    this.totalPrice = totalPrice;
    this.sellDateStr = this.getFormat(sellDate);
  }

  // private static int setAtomic() {
  //   SellServices sellServices = new SellServices();
  //   return sellServices.getLastId();
  // }

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
    this.sellDateStr = this.getFormat(sellDate);
  }

  public String getClientName() {
    return this.clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }


  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  } 
  
  public List<Product> getProducts() {
    return this.products;
  }

  public void setSoldProducts(List<SoldProduct> soldProducts) {
    this.soldProducts = soldProducts;
  }

  public List<SoldProduct> getSoldProducts() {
    return this.soldProducts;
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