package hexlet.code.controllers;

import hexlet.code.NamedRoutes;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

public class UrlController {
    public static void index(Context ctx) {
        var flash = ctx.consumeSessionAttribute("flash");
        var flashInfo = ctx.consumeSessionAttribute("flashInfo");
        var page = new UrlsPage(UrlRepository.getEntities());

        if (flash != null && flashInfo != null) {
            page.setFlash(flash);
            page.setFlashInfo(flashInfo.toString());
        }
        ctx.render("urls/index.jte", Collections.singletonMap("page", page));
    }

    public static void add(Context ctx) throws URISyntaxException {
        var url = ctx.formParamAsClass("url", String.class).get();
        try {
            var parsedUrl = new URI(url);
        } catch (URISyntaxException e) {
            var page = new BasePage("Некорректный URL", "danger");
            ctx.render("index.jte", Collections.singletonMap("page", page));
        }
        var parsedUrl = new URI(url);
        var name = parsedUrl.getScheme() + "://" + parsedUrl.getAuthority();
        Url result = new Url(name);
        if (UrlRepository.find(result.getName()).isPresent()) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flashInfo", "info");
        } else {
            UrlRepository.save(result);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flashInfo", "success");
        }
        ctx.redirect(NamedRoutes.urlsPath());

    }

    public static void show(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.find(id).isPresent() ? UrlRepository.find(id).get() : null;
        var checks = UrlCheckRepository.getEntitiesById(id);
        var flash = ctx.consumeSessionAttribute("flash");
        var flashInfo = ctx.consumeSessionAttribute("flashInfo");
        var page = new UrlPage(url, checks);
        if (flash != null && flashInfo != null) {
            page.setFlash(flash);
            page.setFlashInfo(flashInfo.toString());
        }
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }


}
