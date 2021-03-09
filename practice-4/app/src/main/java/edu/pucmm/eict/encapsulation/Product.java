
package edu.pucmm.eict.encapsulation;

import java.math.BigDecimal;
// import java.util.concurrent.atomic.AtomicInteger;

// import edu.pucmm.eict.services.ProductServices;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "PRODUCTS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Product implements Serializable {

  // private static AtomicInteger idCount = new AtomicInteger(setAtomic());
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private BigDecimal price;

  public Product() {

  }

  public Product(String name, BigDecimal price) {
    // this.id = idCount.incrementAndGet();
    this.name = name;
    this.price = price;
  }

  // private static int setAtomic() {
  //   ProductServices pServices = new ProductServices();
  //   return pServices.getLastId();
  // }

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