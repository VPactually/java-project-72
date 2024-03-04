@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.NamedRoutes
@import hexlet.code.model.UrlCheck
@import java.time.format.DateTimeFormatter
@import java.util.Comparator
@param UrlsPage page

@template.layout.page(page = page,
content = @`
    <div class="container-lg mt-5">
        <h1>Сайты</h1>

        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th class="col-1">ID</th>
                <th>Имя</th>
                <th class="col-2">Последняя проверка</th>
                <th class="col-1">Код ответа</th>
            </tr>
            </thead>
            <tbody>

            @for(var url : page.getUrls())
                <tr>
                    <td>
                        ${url.getId()}
                    </td>
                    <td>
                        <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                    </td>
                    @if(page.getChecks().stream().anyMatch(e -> e.getUrlId() == url.getId()))
                    <td>
                            ${page.getChecks().stream().filter(check -> check.getUrlId() == url.getId())
                                                    .max(Comparator.comparing(UrlCheck::getCreatedAt))
                                                    .get()
                                                    .getCreatedAt().toLocalDateTime()
                                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}
                    </td>
                    <td>
                            ${page.getChecks().stream().filter(check -> check.getUrlId() == url.getId())
                                                    .max(Comparator.comparing(UrlCheck::getCreatedAt))
                                                    .get()
                                                    .getStatusCode()}
                    </td>
                    @endif
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`)