package hexlet.code.controllers;

import hexlet.code.NamedRoutes;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.repositories.DomainRepository;
import hexlet.code.repository.repositories.UrlRepository;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;

import java.sql.Timestamp;
import java.util.Date;


public class DomainController {
    public static void check(Context ctx) {
        int urlId = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.find(urlId).get();
        HttpResponse<String> response = Unirest.get(url.getName())
                .asString();
        var doc = Jsoup.parse(response.getBody());
        var statusCode = response.getStatus();
        var title = doc.title();
        var description = doc.select("meta[name=description]").attr("content");
        var h1 = doc.select("h1").text();
        var createdAt = new Timestamp(new Date().getTime());
        var urlCheck = new UrlCheck(urlId, statusCode, title, h1, description, createdAt);
        DomainRepository.save(urlCheck);
        ctx.sessionAttribute("flash", "Страница успешно проверена");
        ctx.sessionAttribute("flashInfo", "success");
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}
