package edu.pucmm.eict.encapsulation;

import java.io.Serializable;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "PHOTOS")
public class Photo implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String mimeType;
  @Lob
  private String base64;

  public Photo() {

  }

  public Photo(String name, String mimeType, String base64) {
    this.name = name;
    this.mimeType = mimeType;
    this.base64 = base64;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMimeType() {
    return this.mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  } 

  public String getBase64() {
    return this.base64;
  }

  public void setBase64(String base64) {
    this.base64 = base64;
  }

}