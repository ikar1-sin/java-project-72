package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;

import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.sql.Timestamp;

public class UrlCheckController {
    public static void index(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id).get();

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            Document doc = Jsoup.parse(response.getBody());

            var statusCode = response.getStatus();
            var h1 = doc.selectFirst("h1") != null ? doc.selectFirst("h1").text() : "";
            var title = doc.title();
            var description = doc.selectFirst("meta[name=description]") != null
                    ? doc.selectFirst("meta[name=description]").attr("content") : "";
            var timeStamp = new Timestamp(System.currentTimeMillis());
            var urlCheck = new UrlCheck(
                    statusCode,
                    title,
                    description,
                    h1
            );
            urlCheck.setUrlId(id);
            urlCheck.setCreatedAt(timeStamp);
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("flash", "Страница успешно проверена.");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL.");
        }
        ctx.redirect(NamedRoutes.urlPath(id));
    }

}
