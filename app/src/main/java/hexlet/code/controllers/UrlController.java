package hexlet.code.controllers;

import hexlet.code.NamedRoutes;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.BasePage;
import hexlet.code.model.Url;
import hexlet.code.repository.repositories.DomainRepository;
import hexlet.code.repository.repositories.UrlRepository;
import io.javalin.http.Context;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;

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

    public static void add(Context ctx) {
        var url = ctx.formParamAsClass("url", String.class).get();
        try {
            var parsedUrl = new URI(url);
            if (!parsedUrl.getScheme().startsWith("http")) {
                throw new Exception();
            }
            var name = parsedUrl.getScheme() + "://" + parsedUrl.getAuthority();
            var createdAt = new Timestamp(new Date().getTime());
            Url result = new Url(name, createdAt);
            if (UrlRepository.getEntities().stream().anyMatch(u -> u.getName().equals(name))) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashInfo", "info");
            } else {
                UrlRepository.save(result);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.sessionAttribute("flashInfo", "success");
            }
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (Exception e) {
            var page = new BasePage("Некорректный URL", "danger");
            ctx.render("index.jte", Collections.singletonMap("page", page));
        }
    }

    public static void show(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.find(id).isPresent() ? UrlRepository.find(id).get() : null;
        var checks = DomainRepository.getEntitiesById(id);
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
