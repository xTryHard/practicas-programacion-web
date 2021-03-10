package edu.pucmm.eict.services;

import edu.pucmm.eict.encapsulation.SoldProduct;

public class SoldProductServices extends DatabaseOrmHandler<SoldProduct>{
  
  private static SoldProductServices instance = null;

  private SoldProductServices() {
    super(SoldProduct.class);
  }

  public static SoldProductServices getInstance() {
    if (instance == null) {
      instance = new SoldProductServices();
    }
    return instance;
  }

  
}