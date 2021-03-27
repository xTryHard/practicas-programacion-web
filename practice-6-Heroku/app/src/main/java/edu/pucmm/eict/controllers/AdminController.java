
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.encapsulation.Product;
import edu.pucmm.eict.services.CommentServices;
import edu.pucmm.eict.services.PhotoServices;
import edu.pucmm.eict.services.ProductServices;
import edu.pucmm.eict.services.SellServices;
import edu.pucmm.eict.services.ShoppingCartServices;
import edu.pucmm.eict.services.UserServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;
import edu.pucmm.eict.encapsulation.Sell;
import edu.pucmm.eict.encapsulation.ShoppingCart;
import edu.pucmm.eict.encapsulation.User;
import edu.pucmm.eict.encapsulation.Comment;
import edu.pucmm.eict.encapsulation.Photo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Base64;


public class AdminController extends BaseController{

  private boolean isCreating = true;
  public AdminController(Javalin app) {
    super(app);
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

        List<Product> products = ProductServices.getInstance().findAll();
        HashMap<String, Object> model = new HashMap<>();
        model.put("products", products);

        ShoppingCart shoppingCart = ctx.sessionAttribute("shopping-cart");
        
        int amount = shoppingCart.getTotalAmount();
        model.put("cartAmount", amount);
        model.put("activeAdmin", "active");

        ctx.render("/templates/admin-products.html", model);
        
      });

      post("/photos/:productId", ctx -> {

        String productId = ctx.pathParam("productId");
        String productName = ctx.formParam("productName");
        String productPrice = ctx.formParam("productPrice");
        String productDescription = ctx.formParam("productDescription");
        ctx.sessionAttribute("productName", productName);
        ctx.sessionAttribute("productPrice", productPrice);
        ctx.sessionAttribute("productDescription", productDescription);
        ctx.redirect("/admin/photos/"+productId);
      });

      get("/photos/:productId", ctx -> {
        List<Photo> photos = null;
        Map<String, Object> model = new HashMap<>();

        //if (isCreating) {
          photos = new ArrayList<>(ctx.sessionAttribute("photos"));
          model.put("isCreating", isCreating);
        //} else {
          // int productId = Integer.parseInt(ctx.pathParam("productId"));
          // Product product = ProductServices.getInstance().find(productId);
          // photos = product.getPhotos();
          // model.put("productId", productId);
          // model.put("isCreating", false);

          if (!isCreating) {
            int productId = Integer.parseInt(ctx.pathParam("productId"));
            model.put("productId", productId);
            ctx.sessionAttribute("productId", productId);

          }
     //   }
        model.put("photos", photos);
        
        ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");

        model.put("cartAmount", userShoppingCart.getTotalAmount());
        ctx.render("/templates/photos.html", model);
      });

      get("/photos/visualize/:id", ctx ->{
        HashMap<String, Object> model = new HashMap<String, Object>();

        int id = Integer.parseInt(ctx.pathParam("id"));

        List<Photo> photos = new ArrayList<Photo>(ctx.sessionAttribute("photos"));
        Photo photo = photos.get(id);
        model.put("photo", photo);

        ShoppingCart userShoppingCart = ctx.sessionAttribute("shopping-cart");
        model.put("cartAmount", userShoppingCart.getTotalAmount());

        ctx.render("/templates/photosVisualize.html", model);
      });

      get("/photos/delete/:id", ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        //if (isCreating) {
          List<Photo> photos = new ArrayList<Photo>(ctx.sessionAttribute("photos"));
          Photo photo = photos.remove(id);
          
          if (!isCreating) {
            if (ctx.sessionAttribute("deletedPhotos") == null) {
              List<Photo> deletedPhotos = new ArrayList<Photo>();
              ctx.sessionAttribute("deletedPhotos", deletedPhotos);
            }

            List<Photo> deletedPhotos = ctx.sessionAttribute("deletedPhotos");
            deletedPhotos.add(photo);
            ctx.sessionAttribute("deletedPhotos", deletedPhotos);
          }
          ctx.sessionAttribute("photos", photos);

          if (isCreating) {
            ctx.redirect("/admin/photos/-1");
          } else {
            int productId = ctx.sessionAttribute("productId");
            ctx.redirect("/admin/photos/"+productId);

          }
        //} else {
          //Photo photo = PhotoServices.getInstance().find(id);
          
        //}
      });

      
      post("/photos-upload", ctx -> {

        List<Photo> photosSession = new ArrayList<Photo>(ctx.sessionAttribute("photos"));
        ctx.uploadedFiles("photo").forEach(uploadedFile -> {
            try {
                byte[] bytes = uploadedFile.getContent().readAllBytes();
                String encodedString = Base64.getEncoder().encodeToString(bytes);
                Photo photo = new Photo(uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                photosSession.add(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }
          });
          ctx.sessionAttribute("photos", photosSession);

          if (isCreating) {
            ctx.redirect("/admin/photos/-1");
          } else {
            int productId = ctx.sessionAttribute("productId");

            ctx.redirect("/admin/photos/"+productId);

          }
    });

      get("/create-product", ctx-> {
        HashMap<String, Object> model = new HashMap<>();
        ShoppingCartServices.getInstance().setAdminMode(true);
        ShoppingCart shoppingCart = ctx.sessionAttribute("shopping-cart");
        int amount = shoppingCart.getTotalAmount();

        ctx.sessionAttribute("photos", new ArrayList<Photo>());

        isCreating = true;
        model.put("cartAmount", amount);
        model.put("createEdit", "Crear Producto");
        model.put("createOrEdit", "create");
        model.put("crearEditar", "Crear");
        model.put("productId","");
        model.put("edit", false);


        ctx.render("/templates/create-edit.html", model);
      });

      post("/create-product", ctx -> {
        String name = ctx.sessionAttribute("productName");
        String priceStr = ctx.sessionAttribute("productPrice");
        BigDecimal price = new BigDecimal(priceStr);
        String description = ctx.sessionAttribute("productDescription");

        ctx.sessionAttribute("productName", null);
        ctx.sessionAttribute("productPrice", null);
        ctx.sessionAttribute("productDescription", null);
        List<Photo> photos = ctx.sessionAttribute("photos");
        
        for (Photo photo : photos) {
          PhotoServices.getInstance().create(photo);
        }

        Product product = new Product(name, price, description, new HashSet<Photo>(photos));
        Product done = ProductServices.getInstance().create(product);
        ctx.sessionAttribute("photos", null);

        if (done != null) {
        //Rediret to 
          // ShoppingCartServices.getInstance().createProduct(product);
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
        Product product = ProductServices.getInstance().find(id);
        HashMap<String, Object> model = new HashMap<>();
        ctx.sessionAttribute("photos", product.getPhotos());
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
        isCreating = false;
      });
      post("/update-product/:productId", ctx -> {

        int id = Integer.parseInt(ctx.pathParam("productId"));

        Product productToUpdate = ProductServices.getInstance().find(id);

        String name = ctx.sessionAttribute("productName");
        String priceStr = ctx.sessionAttribute("productPrice");
        BigDecimal price = new BigDecimal(priceStr);
        String description = ctx.sessionAttribute("productDescription");
        List<Photo> photos = new ArrayList<Photo>(ctx.sessionAttribute("photos"));
        productToUpdate.setName(name);
        productToUpdate.setPrice(price);
        productToUpdate.setDescription(description);
        productToUpdate.setPhotos(new HashSet<Photo>(photos));

        for (Photo photo : photos) {
          if (PhotoServices.getInstance().find(photo.getId()) == null ){
            PhotoServices.getInstance().create(photo);
          }
        }
        ctx.sessionAttribute("productName", null);
        ctx.sessionAttribute("productPrice", null);
        ctx.sessionAttribute("productDescription", null);
        ctx.sessionAttribute("photos", null);
        Product done = ProductServices.getInstance().update(productToUpdate);

        List<Photo> deletedPhotos = ctx.sessionAttribute("deletedPhotos");
        
        if (deletedPhotos != null) {
          for (Photo photo : deletedPhotos) {
            PhotoServices.getInstance().delete(photo.getId());
          }
          ctx.sessionAttribute("deletedPhotos", null);
        }
        
        if (done != null) {
          //Redirect to list
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

        List<Photo> photos = new ArrayList<Photo>(ProductServices.getInstance().find(id).getPhotos());
        boolean done = ProductServices.getInstance().delete(id);
        for (Photo photo : photos) {
          PhotoServices.getInstance().delete(photo.getId());
        }
        if (done) {
          //Redirect list
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

        List<Sell> sells = SellServices.getInstance().findAll();
        
        for (Sell sell : sells) {
          System.out.println("date: "+sell.getSellDateStr());
          System.out.println("total: "+sell.getTotalPrice());
        }
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

        if (UserServices.getInstance().find(username) == null) {
    
          User done = UserServices.getInstance().create(user);
          HashMap<String, Object> model = new HashMap<>();
  
          if (done != null) {
          //Rediret to list
            ShoppingCartServices.getInstance().createUser(user);
            model.put("code", "201");
            model.put("codeText", "Usuario creado correctamente");
            model.put("codeHref", "/shop");
            ctx.render("/templates/error.html", model);
          } else {
            //Error
            model.put("code", "400");
            model.put("codeText", "Oh, no... algo salió mal :(. Inténtalo de nuevo");
            model.put("codeHref", "/admin/create-user");
            ctx.render("/templates/error.html", model);
          }

        } else {
          HashMap<String, Object> model = new HashMap<>();
          model.put("code", "409");
          model.put("codeText", "El usuario ya existe. Inténtalo de nuevo");
          ctx.render("/templates/error.html", model);
        }

      });

      get("/delete-comment/:productId/:commentId", ctx -> {
        int commentId = Integer.parseInt(ctx.pathParam("commentId"));
        int productId = Integer.parseInt(ctx.pathParam("productId"));

        Product product = ProductServices.getInstance().find(productId);
        Comment comment = CommentServices.getInstance().find(commentId);
        
        for (Comment newComment : product.getComments()) {
          if (newComment.getId() == comment.getId()) {
            comment = newComment;
            break;
          }
        }
        product.getComments().remove(comment);
        ProductServices.getInstance().update(product);
        CommentServices.getInstance().delete(commentId);
        ctx.redirect("/review/"+productId);
      });

      });

    });

  }

}