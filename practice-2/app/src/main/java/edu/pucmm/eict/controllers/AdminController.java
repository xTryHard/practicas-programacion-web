
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.encapsulation.Product;
import edu.pucmm.eict.services.ShoppingCartServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;
import edu.pucmm.eict.encapsulation.Sell;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminController extends BaseController{

  public AdminController(Javalin app) {
    super(app);
  }

  public void applyRoutes() {
    app.routes(() -> {
      path("/admin", () -> {

      before(ctx -> {
        //Validations
        String admin = ctx.sessionAttribute("admin");

        if (admin == null) {
          //redirect to 401
        }

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
    
      get("/sells", ctx -> {

        List<Sell> sells = ShoppingCartServices.getInstance().getSells();
        //Return sell;
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("sells", sells);
        model.put("activeSells", "active");
        
        ctx.render("/templates/sells.html", model);
      });

      });

    });

  }

}