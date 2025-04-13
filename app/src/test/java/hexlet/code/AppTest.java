package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class AppTest {
    private Javalin app;
    private static MockWebServer mockWebServer;

    @BeforeAll
    public static void beforeAll() {
        mockWebServer = new MockWebServer();
    }

    @BeforeEach
    public final void install() throws SQLException, IOException {
        app = App.getApp();
    }

    @AfterAll
    public static void stopServer() throws IOException {
        mockWebServer.shutdown();
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
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("https://ru.hexlet.io/projects/72/members/45163");
            UrlRepository.save(url);
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string().contains(url.toString()));
        });
    }

    @Test
    public void testCreateUrl() throws SQLException {
        var inputUrl = "https://github.com";
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=" + inputUrl;
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string().contains("https://github.com"));
        });
        var actualUrl = UrlRepository.findByName(inputUrl).orElse(null);

        assertThat(actualUrl).isNotNull();
        assertThat(actualUrl.getName()).isEqualTo(inputUrl);
    }

    @Test
    public void testCheckUrl() throws IOException, SQLException, URISyntaxException {
        mockWebServer.start();
        mockWebServer.enqueue(new MockResponse().setBody(App.readResourceFile("test.html")));
        var baseUrl = mockWebServer.url("/").toString().replaceAll("/$", "");
        var url = new Url(baseUrl);
        UrlRepository.save(url);
        var urlId = url.getId();

        var actualUrl = UrlRepository.findByName(baseUrl).orElse(null);

        assertThat(actualUrl).isNotNull();
        assertThat(actualUrl.getName()).isEqualTo(baseUrl);

        JavalinTest.test(app, (server, client) -> {
            var response = client.post(NamedRoutes.urlCheckPath(urlId));
            assertThat(response.code()).isEqualTo(200);
            var urlCheck = UrlCheckRepository.getEntities(urlId).getFirst();

            assertThat(urlCheck.getStatusCode()).isEqualTo(200);
            assertThat(urlCheck.getTitle()).isEqualTo("Test");
            assertThat(urlCheck.getH1()).isEqualTo("Test h1");
            assertThat(urlCheck.getDescription()).isEqualTo("description");
        });

    }


}
