package hexlet.code.controllers;


import hexlet.code.repository.repositories.UrlRepository;
import io.javalin.http.Context;

public class DomainController {
    public static void check(Context ctx) {
        var id = ctx.pathParamAsClass("id", Integer.class).get();
        ctx.result("url = " + id);
        var url = UrlRepository.find(id).get().getName();

    }
}
