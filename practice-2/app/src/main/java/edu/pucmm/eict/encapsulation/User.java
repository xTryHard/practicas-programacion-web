
package edu.pucmm.eict.encapsulation;

public class User {
  
  private String username;
  private String password;
  private String name;

  public User() {

  }

  public User(String username, String password, String name) {
    this.username = username;
    this.password = password;
    this.name = name;
  } 

  public String getUserName() {
    return this.username;
  }

  public void setUserName(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}