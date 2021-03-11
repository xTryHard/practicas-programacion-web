
package edu.pucmm.eict.encapsulation;

import java.math.BigDecimal;
// import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;

// import edu.pucmm.eict.services.ProductServices;

import java.io.Serializable;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {

  // private static AtomicInteger idCount = new AtomicInteger(setAtomic());
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private BigDecimal price;
  private String description;

  @OneToMany(fetch = FetchType.EAGER)
  private List<Photo> photos;


  public Product() {

  }
  
  public Product(String name, BigDecimal price, String description, List<Photo> photos) {

    this.name = name;
    this.price = price;
    this.description = description;
    this.photos = photos;

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

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Photo> getPhotos() {
    return this.photos;
  }

  public void setPhotos(List<Photo> photos) {
    this.photos = photos;
  }

}