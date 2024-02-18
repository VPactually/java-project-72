package gg.jte.generated.ondemand.layout;
import gg.jte.Content;
import hexlet.code.model.BasePage;
public final class JtepageGenerated {
	public static final String JTE_NAME = "layout/page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,38,38,38,40,40,40,40,41,41,41,45,45,46,46,46,60,60,60,2,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content, BasePage page) {
		jteOutput.writeContent("\n\n<!DOCTYPE html>\n<html lang=\"en\">\n\n<head>\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n    <title>Анализатор страниц</title>\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n          integrity=\"sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65\" crossorigin=\"anonymous\">\n    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js\"\n            integrity=\"sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4\"\n            crossorigin=\"anonymous\"></script>\n</head>\n\n<body class=\"d-flex flex-column min-vh-100\">\n<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n    <div class=\"container-fluid\">\n        <a class=\"navbar-brand\" href=\"/\">Анализатор страниц</a>\n        <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNav\"\n                aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n            <span class=\"navbar-toggler-icon\"></span>\n        </button>\n        <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\n            <div class=\"navbar-nav\">\n                <a class=\"nav-link\" href=\"/\">Главная</a>\n                <a class=\"nav-link\" href=\"/urls\">Сайты</a>\n            </div>\n        </div>\n    </div>\n</nav>\n\n<main class=\"flex-grow-1\">\n    ");
		if (page != null && page.getFlash() != null) {
			jteOutput.writeContent("\n        <div class=\"rounded-0 m-0 alert alert-dismissible fade show\n        alert-");
			jteOutput.setContext("div", "class");
			jteOutput.writeUserContent(page.getFlashInfo());
			jteOutput.setContext("div", null);
			jteOutput.writeContent("\" role=\"alert\">\n            <p class=\"m-0\"> ");
			jteOutput.setContext("p", null);
			jteOutput.writeUserContent(page.getFlash().toString());
			jteOutput.writeContent("</p>\n            <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n        </div>\n\n    ");
		}
		jteOutput.writeContent("\n    ");
		jteOutput.setContext("main", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\n</main>\n\n<footer class=\"footer border-top py-3 mt-5 bg-light\">\n    <div class=\"container-xl\">\n        <div class=\"text-center\">\n            created by\n            <a href=\"https://github.com/VPactually\" target=\"_blank\">Vladislav Pomozov</a>\n        </div>\n    </div>\n</footer>\n</body>\n</html>\n\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		BasePage page = (BasePage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, content, page);
	}
}
