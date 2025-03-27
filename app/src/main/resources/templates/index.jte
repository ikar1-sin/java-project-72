@import hexlet.code.dto.BasePage
@param BasePage page

@template.layout.page(
    content = @`
    @if(page.getFlash() != null)
        <div class="rounded-0 m-0 alert-dismissible fade show alert alert-danger" role="alert">
            ${page.getFlash()}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
            </button>
        </div>
    @endif
        <div class="container-fluid bg-dark p-5">
            <div class="row">
                <div class="col-md-10 col-lg-8 mx-auto text-white">
                        <h1 class="display-3 mb-0">Анализатор страниц</h1>
                        <p class="lead">Бесплатно проверяйте сайты на SEO пригодность</p>
                            <form action="/urls" method="post" class="rss-form text-body">
                                <div class="row">
                                    <div class="col">
                                        <div class="form-floating">
                                            <input type="text" autofocus required name="url" class="form-control w-100" placeholder="Ссылка" autocomplete="off">
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <button type="submit" class="h-100 btn btn-lg btn-primary px-sm-5">Проверить</button>
                                    </div>
                                </div>
                            </form>
                        <p>Пример: https://www.example.com</p>
                </div>
            </div>
        </div>
`
)