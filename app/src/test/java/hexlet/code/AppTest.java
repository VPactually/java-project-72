package hexlet.code;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import hexlet.code.model.Url;

import static hexlet.code.App.readResourceFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {
    private static Javalin app;
    private static MockWebServer mockServer;

    @BeforeAll
    public static void beforeAll() throws IOException, SQLException {
        mockServer = new MockWebServer();
        var mockResponse = new MockResponse().setBody(readResourceFile("fixtures/example.html"));
        mockServer.enqueue(mockResponse);
    }

    @BeforeEach
    public final void beforeEach() {
        app = App.getApp();
    }

    @AfterAll
    public static void afterAll() throws IOException {
        mockServer.shutdown();
    }

    @Test
    void testRoot() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });

    }

    @Test
    void testRegisterNewSites() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=http://www.rbc.ru";
            client.post(NamedRoutes.urlsPath(), requestBody);
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
            var bodyString = response.body().string();

            assertEquals("http://www.rbc.ru", UrlRepository.find("http://www.rbc.ru").get().getName());
            assertThat(bodyString).contains("Сайты");
            assertThat(bodyString).contains("http://www.rbc.ru");
        });
    }

    @Test
    void testDoubleSite() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=http://www.rbc.ru";
            client.post(NamedRoutes.urlsPath(), requestBody);
            client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(UrlRepository.getEntities()).hasSize(1);
        });
    }

    @Test
    void testShowUrl() {
        JavalinTest.test(app, (server, client) -> {
            var name = "http://www.rbc.ru";
            var url = new Url(name);
            UrlRepository.save(url);

            assertTrue(UrlRepository.find(url.getId()).isPresent());
            var id = url.getId();
            var response = client.get(NamedRoutes.urlPath(id));
            assertThat(response.code()).isEqualTo(200);
            var bodyString = response.body().string();
            assertThat(bodyString).contains("Сайт:");
            assertThat(bodyString).contains(name);
            assertThat(bodyString).contains("Запустить проверку");
        });
    }

    @Test
    void testCheckUrl() {
        var url = mockServer.url("/").toString();
        Url urlForCheck = new Url(url);
        UrlRepository.save(urlForCheck);
        JavalinTest.test(app, (server, client) -> {
            var response = client.post(NamedRoutes.urlsChecksPath(urlForCheck.getId()));
            assertThat(response.code()).isEqualTo(200);
            var lastCheck = UrlCheckRepository.find(urlForCheck.getId()).orElseThrow();
            assertThat(lastCheck.getTitle()).isEqualTo("Example Title");
            assertThat(lastCheck.getH1()).isEqualTo("Example Domain");
            assertThat(lastCheck.getDescription()).isEqualTo("");
        });
    }
}
