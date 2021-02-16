
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;


public class ProductController extends BaseController{

  public ProductController(Javalin app) {
    super(app);
  }

  @Override
  public void ApplyRoutes() {
    app.routes(() -> {
      path("/admin", () -> {


        
      });
    })
  }
}