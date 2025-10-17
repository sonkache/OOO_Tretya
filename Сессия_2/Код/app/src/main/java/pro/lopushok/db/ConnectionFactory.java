package pro.lopushok.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionFactory {
    private static final Properties props = new Properties();
    static {
        try (InputStream is = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) throw new UncheckedIOException(new IOException("db.properties not found"));
            props.load(is);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL driver not found on classpath", e);
        }
    }
    public static Connection get() {
        try {
            return DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
