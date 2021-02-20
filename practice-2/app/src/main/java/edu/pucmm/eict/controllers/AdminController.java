
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.encapsulation.Product;
import edu.pucmm.eict.services.ShoppingCartServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;
import edu.pucmm.eict.encapsulation.Sell;
import edu.pucmm.eict.encapsulation.ShoppingCart;

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

      before("/", ctx -> {
        //Validations
        String admin = ctx.sessionAttribute("admin");
        System.out.println("Admin: "+admin);
        if (admin == null  && ShoppingCartServices.getInstance().getAdminMode() == false) {
          
          ctx.redirect("/shop");
        }

      });

      before("/sells", ctx -> {
        //Validations
        String admin = ctx.sessionAttribute("admin");
        System.out.println("Admin: "+admin);
        if (admin == null) {
          
          ctx.redirect("/shop");
        }
      });


      get("/", ctx ->{
        ShoppingCartServices.getInstance().setAdminMode(false);
        List<Product> products = ShoppingCartServices.getInstance().getProducts();
        HashMap<String, Object> model = new HashMap<>();
        model.put("products", products);

        ShoppingCart shoppingCart = ctx.sessionAttribute("shopping-cart");
        
        int amount = shoppingCart.getTotalAmount();
        model.put("cartAmount", amount);
        model.put("activeAdmin", "active");

        ctx.render("/templates/admin-products.html", model);
        
      });

      get("/create-product", ctx-> {
        HashMap<String, Object> model = new HashMap<>();
        ShoppingCartServices.getInstance().setAdminMode(true);
        model.put("createEdit", "Crear Producto");
        model.put("createOrEdit", "create");
        model.put("crearEditar", "Crear");
        model.put("productId","");
        ctx.render("/templates/create-edit.html", model);
      });

      post("/create-product", ctx -> {
        ShoppingCartServices.getInstance().setAdminMode(true);

        String name = ctx.formParam("productName");
        BigDecimal price = new BigDecimal(ctx.formParam("productPrice"));

        boolean done = ShoppingCartServices.getInstance().createProduct(new Product(name, price));

        if (done) {
        //Rediret to list
          ctx.redirect("/admin");
        } else {
          //Error
        }
      });

      get("/update-product/:product-id", ctx -> {
        ShoppingCartServices.getInstance().setAdminMode(true);
        int id = Integer.parseInt(ctx.pathParam("product-id"));
        Product product = ShoppingCartServices.getInstance().getProductById(id);
        HashMap<String, Object> model = new HashMap<>();

        model.put("createOrEdit", "update");
        model.put("productId", "/"+String.valueOf(id));
        model.put("product", product);
        model.put("edit", true);
        model.put("crearEditar", "Editar");
        model.put("createEdit", "Editar Producto");
        ctx.render("/templates/create-edit.html", model);
      });
      post("/update-product/:product-id", ctx -> {

        int id = Integer.parseInt(ctx.pathParam("product-id"));
        String name = ctx.formParam("productName");
        BigDecimal price = new BigDecimal(ctx.formParam("productPrice"));
        ShoppingCartServices.getInstance().setAdminMode(true);

        Product productToUpdate = new Product();
        productToUpdate.setId(id);
        productToUpdate.setName(name);
        productToUpdate.setPrice(price);

        boolean done = ShoppingCartServices.getInstance().updateProduct(productToUpdate);

        if (done) {
          //Redirect to list
          ctx.redirect("/admin");
        } else {
          //Error
        }  

      });

      get("/delete-product/:product-id", ctx -> {

        ShoppingCartServices.getInstance().setAdminMode(true);
        int id = Integer.parseInt(ctx.pathParam("product-id"));

        boolean done = ShoppingCartServices.getInstance().deleteProduct(id);

        if (done) {
          //Redirect list
          ctx.redirect("/admin");
        } else {
          //error
        }

      });
    
      get("/sells", ctx -> {

        List<Sell> sells = ShoppingCartServices.getInstance().getSells();
        //Return sell;
        Map<String, Object> model = new HashMap<String, Object>();
      
        ShoppingCart shoppingCart = ctx.sessionAttribute("shopping-cart");
        model.put("sells", sells);
        model.put("activeSells", "active");
        model.put("cartAmount", shoppingCart.getTotalAmount());

        ctx.render("/templates/sells.html", model);
      });

      });

    });

  }

}