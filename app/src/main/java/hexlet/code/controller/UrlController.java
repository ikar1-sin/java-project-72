package hexlet.code.controller;

import hexlet.code.dto.url.UrlPage;
import hexlet.code.dto.url.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static hexlet.code.util.UrlUtils.shortenUrl;
import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public static void create(Context ctx) throws SQLException, MalformedURLException, URISyntaxException {
        var name = ctx.formParam("url");
        try {
            shortenUrl(name);
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Невалидный URL");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        if (UrlRepository.existsByName(name)) {
            ctx.sessionAttribute("flash", "Страница уже существует");
        } else {
            var url = new Url(name);
            UrlRepository.save(url);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
        }
        ctx.redirect(NamedRoutes.urlsPath());
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var lastCheck = UrlCheckRepository.getLastCheck();
        var page = new UrlsPage(urls, lastCheck);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("url/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var urlsCheck = UrlCheckRepository.getEntities(id);
        var page = new UrlPage(url, urlsCheck);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("url/show.jte", model("page", page));
    }

}
