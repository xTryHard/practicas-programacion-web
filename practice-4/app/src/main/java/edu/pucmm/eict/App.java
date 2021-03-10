/*
 * This Java source file was generated by the Gradle 'init' task.
 */
// Done
package edu.pucmm.eict;

import edu.pucmm.eict.controllers.AdminController;
import edu.pucmm.eict.controllers.GeneralControllers;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import edu.pucmm.eict.services.DatabaseSetupServices;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
        }).start(3000);
        
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

        DatabaseSetupServices.startDb();
        // DataBaseConnServices.getInstance().testConn();
        // DatabaseSetupServices.createTables();

        // ShoppingCartServices.getInstance().createProduct(new Product("Monitor DELL", new BigDecimal(8000)));
        // ShoppingCartServices.getInstance().createProduct(new Product("4GB RAM DDR4", new BigDecimal(4000)));

        // ProductServices productServices = new ProductServices();
        // System.out.println(productServices.getLastId());

        new AdminController(app).applyRoutes();
        new GeneralControllers(app).applyRoutes();
    }
}
