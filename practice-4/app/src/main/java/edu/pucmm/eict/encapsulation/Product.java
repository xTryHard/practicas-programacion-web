
package edu.pucmm.eict.encapsulation;

import java.math.BigDecimal;
import java.util.HashSet;
// import java.util.concurrent.atomic.AtomicInteger;
import java.util.Set;

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
  // @OrderColumn(name="id")
  private Set<Photo> photos;

  @OneToMany(fetch = FetchType.EAGER)
  // @OrderColumn(name="id")
  private Set<Comment> comments;

  public Product() {

  }
  
  public Product(String name, BigDecimal price, String description, Set<Photo> photos) {

    this.name = name;
    this.price = price;
    this.description = description;
    this.photos = photos;
    this.comments = new HashSet<>();
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

  public Set<Photo> getPhotos() {
    return this.photos;
  }

  public void setPhotos(Set<Photo> photos) {
    this.photos = photos;
  }

  public Set<Comment> getComments() {
    return this.comments;
  }

  public void setComments(Set<Comment> comments) {
    this.comments = comments;
  }

}