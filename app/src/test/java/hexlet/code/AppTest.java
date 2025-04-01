package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class AppTest {
    private Javalin app;
    @BeforeEach
    public void install() throws SQLException, IOException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlsPage() throws MalformedURLException, SQLException, URISyntaxException {
        var url = new Url("https://google.com");
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string().contains(url.toString()));
        });
    }

    @Test
    public void testUrlPage() throws MalformedURLException, SQLException, URISyntaxException {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("https://ru.hexlet.io/projects/72/members/45163");
            UrlRepository.save(url);
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string().contains(url.toString()));
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://github.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string().contains("https://github.com"));
        });
    }


}
