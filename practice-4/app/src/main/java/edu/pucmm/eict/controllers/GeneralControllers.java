
package edu.pucmm.eict.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pucmm.eict.encapsulation.Product;
import edu.pucmm.eict.encapsulation.Sell;
import edu.pucmm.eict.encapsulation.ShoppingCart;
import edu.pucmm.eict.encapsulation.SoldProduct;
import edu.pucmm.eict.services.SellServices;
import edu.pucmm.eict.services.ShoppingCartServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class GeneralControllers extends BaseController {

  private String adminMode = "";
  private SellServices sellServices;
  StrongPasswordEncryptor passwordEncryptor;

  public GeneralControllers(Javalin app) {
    super(app);
    sellServices = new SellServices();
    passwordEncryptor = new StrongPasswordEncryptor();
  }

  public void applyRoutes() {

    app.before(ctx -> {

      // String admin = ctx.sessionAttribute("admin");
      String user = ctx.sessionAttribute("user");
      String cookieAuth = ctx.cookie("rememberMe");
      if (cookieAuth != null) {
        try {
          if (passwordEncryptor.checkPassword("autoAuth", cookieAuth)) {
            ShoppingCartServices.getInstance().setAdminMode(true);
            ctx.sessionAttribute("admin", "admin");
            ctx.sessionAttribute("rememberMe", "autoAuth");
          }
        } catch (EncryptionOperationNotPossibleException e) {
          System.out.println(e.toString());
        }
      } else {
        ctx.sessionAttribute("autoAuth", null);
      }

      if (user == null) {
        ctx.sessionAttribute("user", "user");
        ctx.sessionAttribute("shopping-cart", new ShoppingCart());
      }

    });

    app.before("/shop", ctx -> {
      String admin = ctx.sessionAttribute("admin");
      String autoAuthCookie = ctx.sessionAttribute("rememberMe");

      // invalidate session if admin leaves admin block
      if (admin != null && autoAuthCookie == null) {
        ctx.sessionAttribute("admin", null);
        System.out.println("Si");
        ShoppingCartServices.getInstance().setAdminMode(false);
      }
    });

    app.before("/cart", ctx -> {
      String admin = ctx.sessionAttribute("admin");
      String autoAuthCookie = ctx.sessionAttribute("rememberMe");

      // invalidate session if admin leaves admin block
      if (admin != null && autoAuthCookie == null) {
        ctx.sessionAttribute("admin", null);
        System.out.println("Si");
        ShoppingCartServices.getInstance().setAdminMode(false);
      }
    });

    app.get("/", ctx -> {
      ctx.redirect("/shop");
    });

    app.get("/cart", ctx -> {
      // Vista del carrito;

      // Inject products to template
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

      // Inject shopping cart to view;
      ctx.render("/templates/cart.html", model);

    });

    app.get("/cart/delete/:product-id", ctx -> {
      int productId = Integer.parseInt(ctx.pathParam("product-id"));
      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");
      userShoppingCart.deleteProduct(userShoppingCart.getProductById(productId));
      ctx.sessionAttribute("shopping-cart", userShoppingCart);

      ctx.redirect("/cart");
      ;

    });

    // Process purchase of the items in the cart
    app.post("/cart/purchase-cart", ctx -> {

      String user = ctx.formParam("clientName");
      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");
      // Sell sell = new Sell(new Date(), user,userShoppingCart.getProducts(),
      // userShoppingCart.getProductAmountList(),
      // userShoppingCart.getProductTotalPrice(),
      // userShoppingCart.getCartTotalPrice());

      List<Integer> amountList = userShoppingCart.getProductAmountList();

      List<BigDecimal> productTotalPrices = userShoppingCart.getProductTotalPrice();

      BigDecimal sellTotalPrice = userShoppingCart.getCartTotalPrice();

      List<SoldProduct> soldProducts = new ArrayList<>();
      int i = 0;
      for (Product product : userShoppingCart.getProducts()) {
        SoldProduct soldProduct = new SoldProduct(product.getId(), product.getName(), product.getPrice(),
            amountList.get(i), productTotalPrices.get(i));
        soldProducts.add(soldProduct);
        i++;
      }

      Sell sell = new Sell(new Date(), user, soldProducts, sellTotalPrice);
      sellServices.createSell(sell);
      sellServices.createSellProduct(sell);

      ShoppingCartServices.getInstance().addSell(sell);

      ctx.req.getSession().invalidate();
      HashMap<String, Object> model = new HashMap<>();
      model.put("code", "201");
      model.put("codeText", "Compra realizada correctamente");
      model.put("codeHref", "/shop");
      ctx.render("/templates/error.html", model);

      System.out.println("Compra realizada: " + ShoppingCartServices.getInstance().getSells().get(0));
    });

    app.get("/cart/clear-cart", ctx -> {
      // Clear cart
      ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");
      userShoppingCart.clearProductList();
      ctx.redirect("/cart");
    });

    app.post("/login-validate", ctx -> {
      String username = ctx.formParam("username");
      String password = ctx.formParam("password");
      String rememberMe = ctx.formParam("rememberMe");

      System.out.println(username + " " + password);
      boolean isAdmin = ShoppingCartServices.getInstance().validateAdmin(username, password);

      if (!isAdmin) {
        ctx.redirect("/login/" + this.adminMode);

      } else {

        if (rememberMe != null) {
          ctx.cookie("rememberMe", passwordEncryptor.encryptPassword("autoAuth"), 604800);
          ctx.sessionAttribute("rememberMe", "autoAuth");
        }

        ctx.sessionAttribute("admin", "admin");
        ShoppingCartServices.getInstance().setAdminMode(true);

        if (this.adminMode.equals("sells"))
          ctx.redirect("/admin/sells");
        else if (this.adminMode.equals("admin"))
          ctx.redirect("/admin");
        else if (this.adminMode.equals("users"))
          ctx.redirect("/admin/create-user");
      }
    });

    app.before("/login/:mode", ctx -> {
      String mode = ctx.pathParam("mode");
      String admin = ctx.sessionAttribute("admin");

      if (admin != null) {
        if (mode.equals("sells"))
          ctx.redirect("/admin/sells");
        else if (mode.equals("admin"))
          ctx.redirect("/admin");
        else if (mode.equals("users"))
          ctx.redirect("/admin/create-user");
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
      // Inject products to template
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

    app.get("/logout", ctx -> {

    
      if (ctx.sessionAttribute("rememberMe") != null) {
        ctx.sessionAttribute("rememberMe", null);
        ctx.sessionAttribute("admin", null);
        ctx.removeCookie("rememberMe");
        HashMap<String, Object> model = new HashMap<>();
        model.put("code", "205");
        model.put("codeText", "Has cerrado sesión correctamente");
        model.put("codeHref", "/shop");
        ctx.render("/templates/error.html", model);

      } else if(ctx.sessionAttribute("admin") != null) {
          ctx.sessionAttribute("admin", null);
          HashMap<String, Object> model = new HashMap<>();
          model.put("code", "205");
          model.put("codeText", "Has cerrado sesión correctamente");
          model.put("codeHref", "/shop");
          ctx.render("/templates/error.html", model);
      } else {
        HashMap<String, Object> model = new HashMap<>();
        model.put("code", "404");
        model.put("codeText", "Oops, ¡no has iniciado sesión!");
        model.put("codeHref", "/shop");
        ctx.render("/templates/error.html", model);
      }
    }); 
  }

}