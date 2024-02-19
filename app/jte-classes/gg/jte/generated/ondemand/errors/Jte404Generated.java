package gg.jte.generated.ondemand.errors;
public final class Jte404Generated {
	public static final String JTE_NAME = "errors/404.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,1,1,7,7,7,7,7,7,7,7};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div class=\"position-absolute top-50 start-50 translate-middle\">\n        <h2>Упс, 404</h2>\n        <h3>Такая страница не существует</h3>\n    </div>\n\n");
			}
		}, null);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
