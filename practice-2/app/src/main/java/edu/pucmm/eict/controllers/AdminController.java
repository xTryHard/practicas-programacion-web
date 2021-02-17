
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.encapsulation.Product;
import edu.pucmm.eict.services.ShoppingCartServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.math.BigDecimal;


public class AdminController extends BaseController{

  public AdminController(Javalin app) {
    super(app);
  }

  public void applyRoutes() {
    app.routes(() -> {
      path("/admin", () -> {

      before(ctx -> {
        //Validations
      });

      get("/", ctx -> {

        String products = "";
        
        for (Product product : ShoppingCartServices.getInstance().getProducts()) {
          products += String.format("Nombre: [%s] | Precio: [%.2f]\n", product.getName(), product.getPrice());
        }
        ctx.result(products);
      });

      post("/create-product", ctx -> {
        
        String name = ctx.formParam("product-name");
        BigDecimal price = new BigDecimal(ctx.formParam("product-price"));

        boolean done = ShoppingCartServices.getInstance().createProduct(new Product(name, price));

        if (done) {
        //Rediret to list

        } else {
          //Error
        }
      });

      put("/update-product/:product-id", ctx -> {

        int id = Integer.parseInt(ctx.pathParam("product-id"));
        String name = ctx.formParam("product-name");
        BigDecimal price = new BigDecimal(ctx.formParam("product-price"));

        Product productToUpdate = new Product();
        productToUpdate.setId(id);
        productToUpdate.setName(name);
        productToUpdate.setPrice(price);

        boolean done = ShoppingCartServices.getInstance().updateProduct(productToUpdate);

        if (done) {
          //Redirect to list
        } else {
          //Error
        }  

      });

      delete("/delete-product/:product-id", ctx -> {

        int id = Integer.parseInt(ctx.pathParam("product-id"));

        boolean done = ShoppingCartServices.getInstance().deleteProduct(id);

        if (done) {
          //Redirect list
        } else {
          //error
        }

      });
    
      });

    });

  }

}