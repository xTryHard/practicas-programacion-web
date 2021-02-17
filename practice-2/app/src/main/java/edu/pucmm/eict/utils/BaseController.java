
package edu.pucmm.eict.utils;

import io.javalin.Javalin;

public abstract class BaseController {
  
  protected Javalin app;

  public BaseController(Javalin app) {
    this.app = app;
  }
  
  public abstract void applyRoutes();
}