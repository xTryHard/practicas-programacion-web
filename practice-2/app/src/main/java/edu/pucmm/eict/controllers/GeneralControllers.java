
package edu.pucmm.eict.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pucmm.eict.encapsulation.Product;
import edu.pucmm.eict.encapsulation.Sell;
import edu.pucmm.eict.encapsulation.ShoppingCart;
import edu.pucmm.eict.services.ShoppingCartServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;

public class GeneralControllers extends BaseController {

  public GeneralControllers(Javalin app) {
    super(app);
  }

  public void applyRoutes() {

    app.before(ctx -> {

      String admin = ctx.sessionAttribute("admin");
      String user = ctx.sessionAttribute("user");
      
      //invalidate session if admin leaves admin block
      if (admin == "admin") {
        ctx.req.getSession().invalidate();
      }

      if (user == null) {
        ctx.sessionAttribute("user", "user");
        ctx.sessionAttribute("shopping-cart", new ShoppingCart());
      }

    });
    

    app.get("/", ctx -> {
      ctx.redirect("/shop");
    });

    app.get("/shopping-cart", ctx -> {
      //Vista del carrito;
      ShoppingCart shoppingCart = ctx.sessionAttribute("shopping-cart");
      //Inject shopping cart to view;
      ctx.render("/templates/shopping-cart.html");

    });

    //Process purchase of the items in the cart
    app.post("/purchase-cart", ctx -> {

      String user = ctx.formParam("user");
      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");

      ShoppingCartServices.getInstance().addSell(new Sell(new Date(), user,userShoppingCart.getProducts()));

      ctx.req.getSession().invalidate();
      ctx.redirect("/shop");
    });

    app.get("/clear-cart", ctx -> {
      //Clear cart
      ctx.redirect("/shop");
    });

    app.get("/login/:mode", ctx -> {
      String mode = ctx.pathParam("mode");

      if (mode.equalsIgnoreCase("sells")) {
        ctx.redirect("/admin/sells");

      } else if (mode.equalsIgnoreCase("admin")) {
        ctx.redirect("/admin");
      }
    });

    app.get("/shop", ctx -> {
      List<Product> products = ShoppingCartServices.getInstance().getProducts();
      //Inject products to template
      Map<String, Object> model = new HashMap<String, Object>();

      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");

      model.put("products", products);
      model.put("cartAmount", userShoppingCart.getTotalAmount());

      ctx.render("/templates/shop.html", model);
    });

    app.post("/shop/add/:product-id", ctx -> {
      int id = Integer.parseInt(ctx.pathParam("product-id"));
      int amount = Integer.parseInt(ctx.formParam("product-amount"));
      System.out.println(String.format("ID: %d | amount: %d", id, amount));

      Product product = ShoppingCartServices.getInstance().getProductById(id);
      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");
      userShoppingCart.addProduct(product, amount);
      ctx.sessionAttribute("shopping-cart", userShoppingCart);
      ctx.redirect("/shop");
    });


  }


}