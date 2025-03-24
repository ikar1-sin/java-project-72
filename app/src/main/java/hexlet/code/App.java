package hexlet.code;

import gg.jte.ContentType;
import hexlet.code.util.NamedRoutes;

import io.javalin.Javalin;

import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.rendering.template.JavalinJte;

public class App {
    public static void main(String[] args) {
        var app = getApp();
        app.start(getPort());
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates" ,classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static Javalin getApp() {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(NamedRoutes.rootPath(), ctx -> {
            ctx.render("index.jte");
        });

        return app;
    }

}
