package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck urlCheck) throws SQLException {
        String sql = "INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at)"
                + "VALUES(?,?,?,?,?,?)";
        try (var conn = dataSource.getConnection();
             var preparedStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStmt.setLong(1, urlCheck.getUrlId());
            preparedStmt.setInt(2, urlCheck.getStatusCode());
            preparedStmt.setString(3, urlCheck.getH1());
            preparedStmt.setString(4, urlCheck.getTitle());
            preparedStmt.setString(5, urlCheck.getDescription());
            preparedStmt.setTimestamp(6, Timestamp.valueOf(urlCheck.getCreatedAt()));
            preparedStmt.executeUpdate();
            var generatedKeys = preparedStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
                urlCheck.setCreatedAt(urlCheck.getCreatedAt());
            }
        }
    }

    public static HashMap<Long, UrlCheck> getLastCheck() throws SQLException {
        String sql = "SELECT DISTINCT ON (url_id) * FROM url_checks ORDER BY url_id, created_at DESC";
        var result = new HashMap<Long, UrlCheck>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()
        ) {
            var rs = stmt.executeQuery(sql);

            while (rs.next()) {
                var urlCheck = new UrlCheck(
                        rs.getInt("status_code"),
                        rs.getString("title"),
                        rs.getString("h1"),
                        rs.getString("description")
                );
                var urlId = rs.getLong("url_id");
                urlCheck.setUrlId(urlId);
                urlCheck.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                urlCheck.setId(rs.getLong("id"));
                result.put(urlId, urlCheck);
            }
        }
        return result;
    }


    public static List<UrlCheck> getEntities(Long id) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id=?";
        var result = new ArrayList<UrlCheck>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var urlCheck = new UrlCheck(
                        rs.getInt("status_code"),
                        rs.getString("h1"),
                        rs.getString("title"),
                        rs.getString("description")
                );
                urlCheck.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                urlCheck.setUrlId(rs.getLong("url_id"));
                urlCheck.setId(rs.getLong("id"));
                result.add(urlCheck);
            }
        }
        return result;
    }

}
