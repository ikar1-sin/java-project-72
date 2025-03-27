package gg.jte.generated.ondemand;
import hexlet.code.dto.BasePage;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,3,3,5,5,6,6,8,8,8,12,12,34,34,34,34,34,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, BasePage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    ");
				if (page.getFlash() != null) {
					jteOutput.writeContent("\n        <div class=\"rounded-0 m-0 alert-dismissible fade show alert alert-danger\" role=\"alert\">\n            ");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(page.getFlash());
					jteOutput.writeContent("\n            <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\">\n            </button>\n        </div>\n    ");
				}
				jteOutput.writeContent("\n        <div class=\"container-fluid bg-dark p-5\">\n            <div class=\"row\">\n                <div class=\"col-md-10 col-lg-8 mx-auto text-white\">\n                        <h1 class=\"display-3 mb-0\">Анализатор страниц</h1>\n                        <p class=\"lead\">Бесплатно проверяйте сайты на SEO пригодность</p>\n                            <form action=\"/urls\" method=\"post\" class=\"rss-form text-body\">\n                                <div class=\"row\">\n                                    <div class=\"col\">\n                                        <div class=\"form-floating\">\n                                            <input type=\"text\" autofocus required name=\"url\" class=\"form-control w-100\" placeholder=\"Ссылка\" autocomplete=\"off\">\n                                        </div>\n                                    </div>\n                                    <div class=\"col-auto\">\n                                        <button type=\"submit\" class=\"h-100 btn btn-lg btn-primary px-sm-5\">Проверить</button>\n                                    </div>\n                                </div>\n                            </form>\n                        <p>Пример: https://www.example.com</p>\n                </div>\n            </div>\n        </div>\n");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		BasePage page = (BasePage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
