package edu.pucmm.eict.encapsulation;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMMENTS")
public class Comment implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String author;
  private String message;

  public Comment() {

  }

  public Comment(String author, String message) {
    this.author = author;
    this.message = message;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getMessage( ) {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}