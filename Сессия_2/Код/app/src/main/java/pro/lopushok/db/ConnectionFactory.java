package pro.lopushok.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionFactory {
    private static final HikariDataSource ds;

    static {
        try (InputStream is = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) throw new UncheckedIOException(new IOException("db.properties not found"));

            Properties props = new Properties();
            props.load(is);

            // Настройка HikariCP через конфигурацию
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("url"));
            config.setUsername(props.getProperty("user"));
            config.setPassword(props.getProperty("password"));
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("pool.size", "10")));
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            ds = new HikariDataSource(config);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private ConnectionFactory() {}

    public static Connection get() throws SQLException {
        return ds.getConnection();
    }
}
