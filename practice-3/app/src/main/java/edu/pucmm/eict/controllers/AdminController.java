
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.encapsulation.Product;
import edu.pucmm.eict.services.ProductServices;
import edu.pucmm.eict.services.ShoppingCartServices;
import edu.pucmm.eict.services.UserServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;
import edu.pucmm.eict.encapsulation.Sell;
import edu.pucmm.eict.encapsulation.ShoppingCart;
import edu.pucmm.eict.encapsulation.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminController extends BaseController{

  private ProductServices productServices;
  private UserServices userServices;

  public AdminController(Javalin app) {
    super(app);
    productServices = new ProductServices();
    userServices = new UserServices();
  }

  public void applyRoutes() {
    app.before("/admin", ctx -> {
      String admin = ctx.sessionAttribute("admin");
      System.out.println("Admin: "+admin);
      if (admin == null  && ShoppingCartServices.getInstance().getAdminMode() == false) {
        
        ctx.redirect("/shop");
      }
    });

    app.routes(() -> {
      path("/admin", () -> {

      before(ctx -> {
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
        ShoppingCart shoppingCart = ctx.sessionAttribute("shopping-cart");
        int amount = shoppingCart.getTotalAmount();

        model.put("cartAmount", amount);
        model.put("createEdit", "Crear Producto");
        model.put("createOrEdit", "create");
        model.put("crearEditar", "Crear");
        model.put("productId","");

        ctx.render("/templates/create-edit.html", model);
      });

      post("/create-product", ctx -> {
        String name = ctx.formParam("productName");
        BigDecimal price = new BigDecimal(ctx.formParam("productPrice"));
        Product product = new Product(name, price);
        boolean done = productServices.createProduct(product);
        
        if (done) {
        //Rediret to 
          ShoppingCartServices.getInstance().createProduct(product);
          HashMap<String, Object> model = new HashMap<>();
          model.put("code", "201");
          model.put("codeText", "Producto creado satisfactoriamente.");
          model.put("codeHref", "/admin");
          ctx.render("/templates/error.html", model);
        } else {
          //Error
        }
      });

      get("/update-product/:product-id", ctx -> {
        int id = Integer.parseInt(ctx.pathParam("product-id"));
        Product product = ShoppingCartServices.getInstance().getProductById(id);
        HashMap<String, Object> model = new HashMap<>();

        ShoppingCart shoppingCart = ctx.sessionAttribute("shopping-cart");
        int amount = shoppingCart.getTotalAmount();

        model.put("cartAmount", amount);
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

        Product productToUpdate = new Product();
        productToUpdate.setId(id);
        productToUpdate.setName(name);
        productToUpdate.setPrice(price);

        boolean done = productServices.editProduct(productToUpdate);

        if (done) {
          //Redirect to list
          ShoppingCartServices.getInstance().updateProduct(productToUpdate);
          HashMap<String, Object> model = new HashMap<>();
          model.put("code", "204");
          model.put("codeText", "Producto actualizado satisfactoriamente.");
          model.put("codeHref", "/admin");
          ctx.render("/templates/error.html", model);
        } else {
          //Error
        }  

      });

      get("/delete-product/:product-id", ctx -> {

        int id = Integer.parseInt(ctx.pathParam("product-id"));

        boolean done = productServices.deleteProduct(id);

        if (done) {
          //Redirect list
          ShoppingCartServices.getInstance().deleteProduct(id);
          HashMap<String, Object> model = new HashMap<>();
          model.put("code", "204");
          model.put("codeText", "Producto eliminado satisfactoriamente.");
          model.put("codeHref", "/admin");
          ctx.render("/templates/error.html", model);
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


      get("/create-user", ctx -> {

        Map<String, Object> model = new HashMap<String, Object>();
        ShoppingCart shoppingCart = ctx.sessionAttribute("shopping-cart");
        model.put("activeUsers", "active");
        model.put("cartAmount", shoppingCart.getTotalAmount());
        ctx.render("/templates/create-user.html", model);
      });

      post("/create-user", ctx -> {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        String name = ctx.formParam("name");
        User user = new User(username.toLowerCase(), password, name);

        if (!userServices.existsUser(username)) {
    
          boolean done = userServices.createUser(user);
          HashMap<String, Object> model = new HashMap<>();
  
          if (done) {
          //Rediret to list
            ShoppingCartServices.getInstance().createUser(user);
            model.put("code", "201");
            model.put("codeText", "Usuario creado correctamente");
            ctx.render("/templates/error.html", model);
          } else {
            //Error
            model.put("code", "400");
            model.put("codeText", "Oh, no... algo salió mal :(");
            ctx.render("/templates/error.html", model);
          }

        } else {
          HashMap<String, Object> model = new HashMap<>();
          model.put("code", "409");
          model.put("codeText", "El usuario ya existe");
          ctx.render("/templates/error.html", model);
        }

      });

      });

    });

  }

}