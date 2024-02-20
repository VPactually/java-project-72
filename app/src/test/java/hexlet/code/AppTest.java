package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.repositories.DomainRepository;
import hexlet.code.repository.repositories.UrlRepository;
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

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");

        var dataSource = new HikariDataSource(hikariConfig);
        var sql = readResourceFile("schema.sql");

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        BaseRepository.dataSource = dataSource;
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
            requestBody = "url=http://www.mail.ru";
            client.post(NamedRoutes.urlsPath(), requestBody);
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
            var bodyString = response.body().string();
            assertThat(UrlRepository.getEntities()).hasSize(2);
            assertTrue(UrlRepository.find("http://www.rbc.ru").isPresent());
            assertEquals("http://www.rbc.ru",
                    UrlRepository.find("http://www.rbc.ru").get().getName());
            assertTrue(UrlRepository.find("http://www.mail.ru").isPresent());
            assertEquals("http://www.mail.ru",
                    UrlRepository.find("http://www.mail.ru").get().getName());
            assertThat(bodyString).contains("Сайты");
            assertThat(bodyString).contains("http://www.rbc.ru");
            assertThat(bodyString).contains("http://www.mail.ru");
        });
    }

    @Test
    void testWrongSite() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=http://www.rbc.ru";
            client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(UrlRepository.getEntities()).hasSize(1);

            requestBody = "url=hhhsp://www.rbc.ru";
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(UrlRepository.getEntities()).hasSize(1);
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
            var requestBody = "url=http://www.rbc.ru";
            client.post(NamedRoutes.urlsPath(), requestBody);
            var id = UrlRepository.find("http://www.rbc.ru").get().getId();
            var response = client.get(NamedRoutes.urlPath(id));
            assertThat(response.code()).isEqualTo(200);
            var bodyString = response.body().string();
            assertThat(bodyString).contains("Сайт:");
            assertThat(bodyString).contains("http://www.rbc.ru");
            assertThat(bodyString).contains("Запустить проверку");
        });
    }

    @Test
    void testCheckUrl() throws SQLException {
        var url = mockServer.url("/").toString();
        Url urlForCheck = new Url(url, new Timestamp(new Date().getTime()));
        UrlRepository.save(urlForCheck);
        JavalinTest.test(app, (server, client) -> {
            var response = client.post(NamedRoutes.urlsChecksPath(urlForCheck.getId()));
            try {
                assertThat(response.code()).isEqualTo(200);
                var lastCheck = DomainRepository.find(urlForCheck.getId()).orElseThrow();
                assertThat(lastCheck.getTitle()).isEqualTo("Title");
                assertThat(lastCheck.getH1()).isEqualTo("This is a header");
                assertThat(lastCheck.getDescription()).isEqualTo("description");
            } catch (final Throwable th) {
                System.out.println(th.getMessage());
            } finally {
                response.close();
            }
        });
    }
}
