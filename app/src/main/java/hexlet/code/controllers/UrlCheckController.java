package hexlet.code.controllers;

import hexlet.code.NamedRoutes;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;

public class UrlCheckController {
    public static void check(Context ctx) {
        int urlId = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.find(urlId).get();
        try {
            HttpResponse<String> response = Unirest.get(url.getName())
                    .asString();
            var doc = Jsoup.parse(response.getBody());
            var statusCode = response.getStatus();
            var title = doc.title();
            var description = doc.select("meta[name=description]").attr("content");
            var h1 = doc.select("h1").text();
            var urlCheck = new UrlCheck(urlId, statusCode, title, h1, description);
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashInfo", "success");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.sessionAttribute("flashInfo", "danger");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        }
    }
}
