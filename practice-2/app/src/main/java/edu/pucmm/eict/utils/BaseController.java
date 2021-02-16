
package edu.pucmm.eict.utils;

import io.javalin.Javalin;

public abstract class BaseController {
  
  protected Javalin app;

  abstract void applyRoutes();
}