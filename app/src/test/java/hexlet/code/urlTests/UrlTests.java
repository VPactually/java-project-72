package hexlet.code.urlTests;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.App;
import hexlet.code.NamedRoutes;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.repositories.UrlRepository;
import io.javalin.Javalin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.javalin.testtools.JavalinTest;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import static hexlet.code.App.readResourceFile;
import static org.assertj.core.api.Assertions.assertThat;

public class UrlTests {
    Javalin app;

    @BeforeAll
    public static void setDB() throws IOException, SQLException {
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
    public void setUp() throws Exception {
        app = App.getApp();

    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testEmptyUrls() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testAddUrls() {
        JavalinTest.test(app, (server, client) -> {
            var url = "https://www.example.com";
            var response = client.post(NamedRoutes.urlsPath(), "url=" + url);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains(url);
            assertThat(UrlRepository.find(url).get().getName()).isEqualTo(url);
        });
    }

    @Test
    public void testAddWrongUrl() {
        JavalinTest.test(app, (server, client) -> {
            var url = "  https:/www.example.com";
            var response2 = client.post(NamedRoutes.urlsPath(), "url=" + url);
            assertThat(response2.body().string()).contains("Некорректный URL");
        });
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var url = "https://www.example.com";
            var instance = new Url(url, new Timestamp(new Date().getTime()));
            UrlRepository.save(instance);
            var id = instance.getId();
            var response = client.get(NamedRoutes.urlPath(id));
            var body = response.body().string();
            assertThat(response.code()).isEqualTo(200);
            assertThat(body).contains(instance.getCreatedAt().toString());
            assertThat(body).contains(url);
        });
    }


}
