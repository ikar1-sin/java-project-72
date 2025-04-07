@import hexlet.code.dto.url.UrlsPage
@import java.time.format.DateTimeFormatter
@param UrlsPage page

@template.layout.page(
    content = @`
    <div class="container-lg mb-5">
        @if(page.getFlash() != null)
            @if(page.getFlash().equals("Страница уже существует"))
                <div class="rounded-0 m-0 alert-dismissible fade show alert alert-info" role="alert">
                    ${page.getFlash()}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
                    </button>
                </div>
            @else
                <div class="rounded-0 m-0 alert-dismissible alert fade show alert-success" role="alert">
                    ${page.getFlash()}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
                    </button>
                </div>
            @endif
        @endif
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
            @for(var url: page.getUrls())
                <tr>
                    <td>${url.getId()}</td>
                    <td><a href="urls/${url.getId()}">${url.getName()}</a></td>
                    <td>
                        ${!page.getLastCheckMap().isEmpty() && page.getLastCheckMap().get(url.getId()) != null
                        ? page.getLastCheckMap().get(url.getId()).getCreatedAt().toString() : null}
                    </td>
                    <td>${!page.getLastCheckMap().isEmpty() && page.getLastCheckMap().get(url.getId()) != null
                    ? page.getLastCheckMap().get(url.getId()).getStatusCode() : null}</td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
    `
)