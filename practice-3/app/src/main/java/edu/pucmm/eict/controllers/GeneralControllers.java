
package edu.pucmm.eict.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pucmm.eict.encapsulation.Product;
import edu.pucmm.eict.encapsulation.Sell;
import edu.pucmm.eict.encapsulation.ShoppingCart;
import edu.pucmm.eict.encapsulation.User;
import edu.pucmm.eict.services.SellServices;
import edu.pucmm.eict.services.ShoppingCartServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;

public class GeneralControllers extends BaseController {

  private String adminMode = "";
  private SellServices sellServices;
  public GeneralControllers(Javalin app) {
    super(app);
    sellServices = new SellServices();
  }

  public void applyRoutes() {

    app.before(ctx -> {

      // String admin = ctx.sessionAttribute("admin");
      String user = ctx.sessionAttribute("user");
      
      if (user == null) {
        ctx.sessionAttribute("user", "user");
        ctx.sessionAttribute("shopping-cart", new ShoppingCart());
      }

    });
    
    app.before("/shop", ctx -> {
      String admin = ctx.sessionAttribute("admin"); 
            //invalidate session if admin leaves admin block
            if (admin != null) {
              ctx.sessionAttribute("admin", null);
              System.out.println("Si");
              ShoppingCartServices.getInstance().setAdminMode(false);
            }      
    });

    app.before("/cart", ctx -> {
      String admin = ctx.sessionAttribute("admin"); 
            //invalidate session if admin leaves admin block
            if (admin != null) {
              ctx.sessionAttribute("admin", null);
              System.out.println("Si");
              ShoppingCartServices.getInstance().setAdminMode(false);
            }      
    });

    app.get("/", ctx -> {
      ctx.redirect("/shop");
    });

    app.get("/cart", ctx -> {
      //Vista del carrito;
    
      //Inject products to template
      Map<String, Object> model = new HashMap<String, Object>();

      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");
      List<Product> products = userShoppingCart.getProducts();
      List<Integer> productAmounts = userShoppingCart.getProductAmountList();
      List<BigDecimal> productTotalPrice = userShoppingCart.getProductTotalPrice();
      BigDecimal total = userShoppingCart.getCartTotalPrice();

      model.put("products", products);
      model.put("cartAmount", userShoppingCart.getTotalAmount());
      model.put("activeCart", "active");
      model.put("productAmounts", productAmounts.toArray());
      model.put("productTotalPrice", productTotalPrice.toArray());
      model.put("totalPrice", total);
    

      //Inject shopping cart to view;
      ctx.render("/templates/cart.html", model);

    });

    app.get("/cart/delete/:product-id", ctx -> {
      int productId = Integer.parseInt(ctx.pathParam("product-id"));
      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");
      userShoppingCart.deleteProduct(userShoppingCart.getProductById(productId));
      ctx.sessionAttribute("shopping-cart", userShoppingCart);

      ctx.redirect("/cart");;

    });

    //Process purchase of the items in the cart
    app.post("/cart/purchase-cart", ctx -> {

      String user = ctx.formParam("clientName");
      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");
      Sell sell = new Sell(new Date(), user,userShoppingCart.getProducts(), userShoppingCart.getProductAmountList(), userShoppingCart.getProductTotalPrice(), userShoppingCart.getCartTotalPrice());
      sellServices.createSell(sell);
      sellServices.createSellProduct(sell, userShoppingCart.getProductAmountList());
      ShoppingCartServices.getInstance().addSell(sell);

      ctx.req.getSession().invalidate();
      ctx.redirect("/shop");
      System.out.println("Compra realizada: "+ShoppingCartServices.getInstance().getSells().get(0));
    });

    app.get("/cart/clear-cart", ctx -> {
      //Clear cart
      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");
      userShoppingCart.clearProductList();
      ctx.redirect("/cart");
    });

    app.post("/login-validate", ctx -> {
      String username = ctx.formParam("username");
      String password = ctx.formParam("password");

      System.out.println(username+" "+password);
      boolean isAdmin = ShoppingCartServices.getInstance().validateAdmin(username, password);

      if (!isAdmin) {
        ctx.redirect("/login/"+this.adminMode);

      } else {

        ctx.sessionAttribute("admin", "admin");
        ShoppingCartServices.getInstance().setAdminMode(true);

        if (this.adminMode.equals("sells")) ctx.redirect("/admin/sells");
        else if (this.adminMode.equals("admin")) ctx.redirect("/admin");
        else if (this.adminMode.equals("users")) ctx.redirect("/admin/create-user");
      }
    });

    app.before("/login/:mode", ctx-> {
      String mode = ctx.pathParam("mode");
      String admin = ctx.sessionAttribute("admin");

      if (admin != null) {
        if (mode.equals("sells")) ctx.redirect("/admin/sells");
        else if (mode.equals("admin")) ctx.redirect("/admin");
        else if (mode.equals("users")) ctx.redirect("/admin/create-user");
      }
      
    });

    app.get("/login/:mode", ctx -> {
      String mode = ctx.pathParam("mode");
      this.adminMode = mode;

      HashMap<String, Object> model = new HashMap<>();
      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");

      model.put("cartAmount", userShoppingCart.getTotalAmount());
    
      ctx.render("/templates/login.html", model);
    });

    app.get("/shop", ctx -> {
      List<Product> products = ShoppingCartServices.getInstance().getProducts();
      //Inject products to template
      Map<String, Object> model = new HashMap<String, Object>();

      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");

      model.put("products", products);
      model.put("cartAmount", userShoppingCart.getTotalAmount());
      model.put("activeShop", "active");

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


    app.error(404, ctx -> {
      HashMap<String, Object> model = new HashMap<>();
      model.put("code", "404");
      model.put("codeText", "Oops, el recurso que estás buscando no pudo ser encontrado :(");
      model.put("codeHref", "/shop");
      ctx.render("/templates/error.html", model);
    });

    
  }


}