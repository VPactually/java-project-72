package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.NamedRoutes;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.repositories.DomainRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,4,5,6,7,8,8,8,10,10,11,11,26,26,29,29,29,32,32,32,32,32,32,32,32,32,32,32,32,35,35,36,39,39,39,39,39,40,40,43,43,44,46,46,46,46,47,47,50,50,54,54,54,54,54,8,8,8,8};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"container-lg mt-5\">\n        <h1>Сайты</h1>\n\n        <table class=\"table table-bordered table-hover mt-3\">\n            <thead>\n            <tr>\n                <th class=\"col-1\">ID</th>\n                <th>Имя</th>\n                <th class=\"col-2\">Последняя проверка</th>\n                <th class=\"col-1\">Код ответа</th>\n            </tr>\n            </thead>\n            <tbody>\n\n            ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent("\n                <tr>\n                    <td>\n                        ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("\n                    </td>\n                    <td>\n                        <a");
					var __jte_html_attribute_0 = NamedRoutes.urlPath(url.getId());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" href=\"");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a>\n                    </td>\n                    <td>\n                        ");
					if (DomainRepository.find(url.getId()).isPresent()) {
						jteOutput.writeContent("\n                            ");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(DomainRepository.getEntitiesById(url.getId())
                            .get(DomainRepository.getEntitiesById(url.getId()).size() - 1)
                            .getCreatedAt().toLocalDateTime()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
						jteOutput.writeContent("\n                        ");
					}
					jteOutput.writeContent("\n                    </td>\n                    <td>\n                        ");
					if (DomainRepository.find(url.getId()).isPresent()) {
						jteOutput.writeContent("\n                            ");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(DomainRepository.getEntitiesById(url.getId())
                            .get(DomainRepository.getEntitiesById(url.getId()).size() - 1)
                            .getStatusCode());
						jteOutput.writeContent("\n                        ");
					}
					jteOutput.writeContent("\n                    </td>\n                </tr>\n            ");
				}
				jteOutput.writeContent("\n            </tbody>\n        </table>\n    </div>\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
