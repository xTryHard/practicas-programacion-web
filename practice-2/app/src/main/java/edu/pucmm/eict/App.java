/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.pucmm.eict;

import edu.pucmm.eict.controllers.AdminController;
import edu.pucmm.eict.controllers.GeneralControllers;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
        }).start(3000);
        
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
        new AdminController(app).applyRoutes();
        new GeneralControllers(app).applyRoutes();

    }
}
