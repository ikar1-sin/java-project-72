@import hexlet.code.dto.url.UrlPage
@import java.time.format.DateTimeFormatter
@param UrlPage page

@template.layout.page(
    page = page,
    content = @`
    @if(page.getFlash() != null)
        @if(page.getFlash().equals("Страница успешно проверена."))
            <div class="rounded-0 m-0 alert-dismissible alert fade show alert-success" role="alert">
                ${page.getFlash()}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
                </button>
            </div>
        @else
            <div class="rounded-0 m-0 alert-dismissible alert fade show alert-danger" role="alert">
                ${page.getFlash()}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
                </button>
            </div>
        @endif

    @endif
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
                    <td>Дата Создания</td>
                    <td>${page.getUrl().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
                </tr>
            </tbody>
        </table>
    <h2 class="mt-5">Проверки</h2>
    <form action="/urls/${page.getUrl().getId()}/checks" method="post">
        <button class="btn btn-primary" type="submit">Запустить проверку</button>
    </form>
    <table class="table table-bordered table-hover mt-3">
        <thead>
            <tr>
                <th class="col-1">ID</th>
                <th class="col-1">Код ответа</th>
                <th>title</th>
                <th>h1</th>
                <th>description</th>
                <th class="col-2">Дата проверки</th>
            </tr>
        </thead>
        <tbody>
            @for(var value : page.getUrls())
                <tr>
                    <td>${value.getId()}</td>
                    <td>${value.getStatusCode()}</td>
                    <td>${value.getTitle()}</td>
                    <td>${value.getH1()}</td>
                    <td>${value.getDescription()}</td>
                    <td>${value.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
                </tr>
            @endfor
        </tbody>
    </table>
    </div>
    `
)
