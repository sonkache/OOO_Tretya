package pro.lopushok;

import pro.lopushok.db.ConnectionFactory;
import java.sql.*;

public class PingDb {
    public static void main(String[] args) throws Exception {
        try (Connection c = ConnectionFactory.get()) {
            try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM product")) {
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    System.out.println("OK, products: " + rs.getInt(1));
                }
            }
        }
    }
}
