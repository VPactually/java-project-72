package hexlet.code.repositoryTests;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.App;
import hexlet.code.NamedRoutes;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.repositories.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import static hexlet.code.App.readResourceFile;
import static org.assertj.core.api.Assertions.assertThat;

public class UrlRepositoryTest {
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
    public void testException() {
        JavalinTest.test(app, (server, client) -> {
            assertThat(client.get("/thisisnotrighturl").code()).isEqualTo(404);
        });
    }

    @Test
    public void testSizeDelete() {
        JavalinTest.test(app, (server, client) -> {
            var url = "https://www.example.com";
            assertThat(UrlRepository.size()).isEqualTo(0);
            client.post(NamedRoutes.urlsPath(), "url=" + url);
            assertThat(UrlRepository.size()).isEqualTo(1);
            var id = UrlRepository.getEntities().get(0).getId();
            UrlRepository.delete(id);
            assertThat(UrlRepository.size()).isEqualTo(0);
        });
    }

    @Test
    public void testNotFoundUrl() {
        JavalinTest.test(app, (server, client) -> {
            var url = "https://www.example.com";
            var instance = new Url(url, new Timestamp(new Date().getTime()));
            UrlRepository.save(instance);
            assertThat(UrlRepository.find(2)).isEmpty();
            assertThat(UrlRepository.find(1).get().getName()).isEqualTo(instance.getName());
        });
    }
}