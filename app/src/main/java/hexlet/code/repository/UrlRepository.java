package hexlet.code.repository;

import hexlet.code.model.Url;

import static hexlet.code.util.UrlUtils.shortenUrl;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UrlRepository extends BaseRepository {
    public static void save(Url url) throws SQLException, MalformedURLException, URISyntaxException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStmt.setString(1, shortenUrl(url.getName()));
            var createdAt = LocalDateTime.now();
            preparedStmt.setTimestamp(2, Timestamp.valueOf(createdAt));

            preparedStmt.executeUpdate();

            var generatedKeys = preparedStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
                url.setCreatedAt(createdAt);
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<Url> find(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id=?";
        try (var conn = dataSource.getConnection();
             var preparedStmt = conn.prepareStatement(sql);
        ) {
            preparedStmt.setLong(1, id);
            var rs = preparedStmt.executeQuery();
            if (rs.next()) {
                var name = rs.getString("name");
                var createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                var url = new Url(name);
                url.setId(id);
                url.setCreatedAt(createdAt);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static boolean existsByName(String name) throws SQLException, MalformedURLException, URISyntaxException {
        String sql = "SELECT COUNT(*) FROM urls WHERE name=?";
        try (var conn = dataSource.getConnection();
             var preparedStmt = conn.prepareStatement(sql)
        ) {
            preparedStmt.setString(1, shortenUrl(name));
            var rs = preparedStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls ORDER BY name ASC";
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)
        ) {
            var result = new ArrayList<Url>();

            while (rs.next()) {
                var id = rs.getLong("id");
                var name = rs.getString("name");
                var createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                var url = new Url(name);
                url.setId(id);
                url.setCreatedAt(createdAt);
                result.add(url);
            }
            return result;
        }
    }


}
