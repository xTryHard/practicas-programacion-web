package edu.pucmm.eict.services;

import edu.pucmm.eict.encapsulation.Photo;

public class PhotoServices extends DatabaseOrmHandler<Photo> {

  private static PhotoServices instance = null;

  private PhotoServices() {
    super(Photo.class);
  }

  public static PhotoServices getInstance() {
    
    if (instance == null) {
      instance = new PhotoServices();
    }
    return instance;
  }

  
}