package hexlet.code.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl(System.getenv()
                .getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project"));
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
