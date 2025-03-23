package hexlet.code;

import hexlet.code.util.NamedRoutes;

import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        var app = getApp();
        app.start(getPort());
    }

    public static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static Javalin getApp() {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
//            config.fileRenderer(new JavalinJte());
        });

        app.get(NamedRoutes.rootPath(), ctx -> {
            ctx.result("Hello, world!");
        });

        return app;
    }

}
