@import hexlet.code.NamedRoutes
@import hexlet.code.dto.urls.UrlPage
@import java.time.format.DateTimeFormatter
@param UrlPage page

@template.layout.page(page = page, content = @`
    <section>

        <div class="container-lg mt-5">
            <h1>Сайт: ${page.getUrl().getName()}</h1>

            <table class="table table-bordered table-hover mt-3">
                <tbody>
                <tr>
                    <td>ID</td>
                    <td>${page.getUrl().getId()}</td>
                </tr>
                <tr>
                    <td>Имя</td>
                    <td>${page.getUrl().getName()}</td>
                </tr>
                <tr>
                    <td>Дата создания</td>
                    <td>${page.getUrl().getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
                </tr>
                </tbody>
            </table>

            <h2 class="mt-5">Проверки</h2>
            <form method="post" action="${NamedRoutes.urlsChecksPath(page.getUrl().getId())}">
                <button type="submit" class="btn btn-primary">Запустить проверку</button>
            </form>

            <table class="table table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <th class="col-1">ID</th>
                    <th class="col-1">Код ответа</th>
                    <th>Заголовок</th>
                    <th>Заголовок первого уровня</th>
                    <th>Описание</th>
                    <th class="col-2">Дата проверки</th>
                </tr>
                </thead>
                <tbody>

                @if (!page.getUrlChecks().isEmpty())
                    @for(var check : page.getUrlChecks())
                        <tr>
                            <td>
                                ${check.getId()}
                            </td>
                            <td>
                                ${check.getStatusCode()}
                            </td>
                            <td>
                                ${check.getTitle()}
                            </td>
                            <td>
                                ${check.getH1()}
                            </td>
                            <td>
                                ${check.getDescription()}
                            </td>
                            <td>
                                ${check.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}
                            </td>
                        </tr>
                    @endfor
                @endif
                </tbody>
            </table>
        </div>

    </section>
` )
