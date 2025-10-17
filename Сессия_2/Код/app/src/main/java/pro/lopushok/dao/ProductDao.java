package pro.lopushok.dao;

import pro.lopushok.db.ConnectionFactory;
import pro.lopushok.model.Material;
import pro.lopushok.model.Product;
import pro.lopushok.model.ProductMaterial;
import pro.lopushok.model.ProductType;

import java.sql.*;
import java.util.*;

public class ProductDao {

    public List<Product> page(String search, Long productTypeId, String sortKey, boolean asc, int pageIndex, int pageSize) {
        String base =
                "SELECT p.product_id, p.product_type_id, p.article, p.name, pt.name AS type_name, w.number AS workshop_number, " +
                        "p.workshop_id, p.min_agent_price, p.people_required, p.width, p.length, p.description, p.image_path, " +
                        "COALESCE(SUM(pm.quantity * m.cost / NULLIF(m.package_quantity,0)),0) AS material_cost, " +
                        "GROUP_CONCAT(DISTINCT m.name ORDER BY m.name SEPARATOR ', ') AS materials_summary, " +
                        "CASE WHEN EXISTS (SELECT 1 FROM AgentProductSale s WHERE s.product_id=p.product_id AND s.sale_date >= NOW() - INTERVAL 1 MONTH) THEN 0 ELSE 1 END AS not_sold_last_month " +
                        "FROM Product p " +
                        "LEFT JOIN ProductType pt ON pt.product_type_id=p.product_type_id " +
                        "LEFT JOIN Workshop w ON w.workshop_id=p.workshop_id " +
                        "LEFT JOIN ProductMaterial pm ON pm.product_id=p.product_id " +
                        "LEFT JOIN Material m ON m.material_id=pm.material_id " +
                        "WHERE 1=1 ";

        List<Object> params = new ArrayList<>();
        if (search != null && !search.isBlank()) {
            base += "AND (p.name LIKE ? OR p.description LIKE ?) ";
            String q = "%" + search.trim() + "%";
            params.add(q);
            params.add(q);
        }
        if (productTypeId != null && productTypeId > 0) {
            base += "AND p.product_type_id=? ";
            params.add(productTypeId);
        }

        base += "GROUP BY p.product_id ";
        String orderBy = switch (sortKey) {
            case "workshop" -> "w.number";
            case "price" -> "p.min_agent_price";
            default -> "p.name";
        };
        base += "ORDER BY " + orderBy + (asc ? " ASC " : " DESC ") + "LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add(pageIndex * pageSize);

        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(base)) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                List<Product> out = new ArrayList<>();
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getLong("product_id"));
                    p.setProductTypeId(rs.getLong("product_type_id"));
                    p.setArticle(rs.getString("article"));
                    p.setName(rs.getString("name"));
                    p.setTypeName(rs.getString("type_name"));
                    p.setWorkshopNumber((Integer) rs.getObject("workshop_number"));
                    Object wid = rs.getObject("workshop_id");
                    p.setWorkshopId(wid == null ? null : ((Number) wid).longValue());
                    p.setMinAgentPrice(rs.getDouble("min_agent_price"));
                    p.setPeopleRequired((Integer) rs.getObject("people_required"));
                    p.setWidth((Integer) rs.getObject("width"));
                    p.setLength((Integer) rs.getObject("length"));
                    p.setDescription(rs.getString("description"));
                    p.setImagePath(rs.getString("image_path"));
                    p.setMaterialCost(rs.getDouble("material_cost"));
                    p.setMaterialsSummary(rs.getString("materials_summary"));
                    p.setNotSoldLastMonth(rs.getInt("not_sold_last_month") == 1);
                    out.add(p);
                }
                return out;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int totalCount(String search, Long productTypeId) {
        String sql = "SELECT COUNT(*) FROM Product p WHERE 1=1 ";
        List<Object> params = new ArrayList<>();
        if (search != null && !search.isBlank()) {
            sql += "AND (p.name LIKE ? OR p.description LIKE ?) ";
            String q = "%" + search.trim() + "%";
            params.add(q);
            params.add(q);
        }
        if (productTypeId != null && !Objects.equals(productTypeId, 0L)) {
            sql += "AND p.product_type_id=? ";
            params.add(productTypeId);
        }
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductType> typesForFilter() {
        String sql = "SELECT product_type_id, name FROM ProductType ORDER BY name";
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<ProductType> out = new ArrayList<>();
            out.add(new ProductType(0, "Все типы"));
            while (rs.next()) out.add(new ProductType(rs.getLong(1), rs.getString(2)));
            return out;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductType> allTypes() {
        String sql = "SELECT product_type_id, name FROM ProductType ORDER BY name";
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<ProductType> out = new ArrayList<>();
            while (rs.next()) out.add(new ProductType(rs.getLong(1), rs.getString(2)));
            return out;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product get(long id) {
        String sql = "SELECT p.product_id, p.product_type_id, p.article, p.name, p.min_agent_price, p.width, p.length, p.people_required, p.description, p.image_path, p.workshop_id, w.number " +
                "FROM Product p LEFT JOIN Workshop w ON w.workshop_id=p.workshop_id WHERE p.product_id=?";
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getLong(1));
                    p.setProductTypeId(rs.getLong(2));
                    p.setArticle(rs.getString(3));
                    p.setName(rs.getString(4));
                    p.setMinAgentPrice(rs.getDouble(5));
                    p.setWidth((Integer) rs.getObject(6));
                    p.setLength((Integer) rs.getObject(7));
                    p.setPeopleRequired((Integer) rs.getObject(8));
                    p.setDescription(rs.getString(9));
                    p.setImagePath(rs.getString(10));
                    Object wid = rs.getObject("workshop_id");
                    p.setWorkshopId(wid == null ? null : ((Number) wid).longValue());
                    p.setWorkshopNumber((Integer) rs.getObject(12));
                    return p;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long insert(Product p) {
        String sql = "INSERT INTO Product(product_type_id, article, name, min_agent_price, width, length, people_required, description, image_path, workshop_id) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, p.getProductTypeId());
            ps.setString(2, p.getArticle());
            ps.setString(3, p.getName());
            ps.setDouble(4, p.getMinAgentPrice());
            if (p.getWidth() == null) ps.setNull(5, Types.INTEGER);
            else ps.setInt(5, p.getWidth());
            if (p.getLength() == null) ps.setNull(6, Types.INTEGER);
            else ps.setInt(6, p.getLength());
            int ppl = p.getPeopleRequired() == null ? 0 : p.getPeopleRequired();
            ps.setInt(7, ppl);
            ps.setString(8, p.getDescription());
            ps.setString(9, p.getImagePath());
            if (p.getWorkshopId() == null) ps.setNull(10, Types.BIGINT);
            else ps.setLong(10, p.getWorkshopId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Product p) {
        String sql = "UPDATE Product SET product_type_id=?, article=?, name=?, min_agent_price=?, width=?, length=?, people_required=?, description=?, image_path=?, workshop_id=? WHERE product_id=?";
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, p.getProductTypeId());
            ps.setString(2, p.getArticle());
            ps.setString(3, p.getName());
            ps.setDouble(4, p.getMinAgentPrice());
            if (p.getWidth() == null) ps.setNull(5, Types.INTEGER);
            else ps.setInt(5, p.getWidth());
            if (p.getLength() == null) ps.setNull(6, Types.INTEGER);
            else ps.setInt(6, p.getLength());
            int ppl = p.getPeopleRequired()==null ? 0 : p.getPeopleRequired();
            ps.setInt(7, ppl);
            ps.setString(8, p.getDescription());
            ps.setString(9, p.getImagePath());
            if (p.getWorkshopId() == null) ps.setNull(10, Types.BIGINT);
            else ps.setLong(10, p.getWorkshopId());
            ps.setLong(11, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean articleExists(String article, Long exceptId) {
        String sql = "SELECT 1 FROM Product WHERE article=? " + (exceptId == null ? "" : "AND product_id<>?");
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, article);
            if (exceptId != null) ps.setLong(2, exceptId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Material> findMaterials(String query, int limit) {
        String sql = "SELECT material_id, name FROM Material WHERE name LIKE ? ORDER BY name LIMIT ?";
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + (query == null ? "" : query.trim()) + "%");
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                List<Material> out = new ArrayList<>();
                while (rs.next()) out.add(new Material(rs.getLong(1), rs.getString(2)));
                return out;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductMaterial> productMaterials(long productId) {
        String sql = "SELECT pm.material_id, m.name, pm.quantity FROM ProductMaterial pm JOIN Material m ON m.material_id=pm.material_id WHERE pm.product_id=? ORDER BY m.name";
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                List<ProductMaterial> out = new ArrayList<>();
                while (rs.next()) out.add(new ProductMaterial(rs.getLong(1), rs.getString(2), rs.getDouble(3)));
                return out;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void replaceProductMaterials(long productId, List<ProductMaterial> list) {
        String del = "DELETE FROM ProductMaterial WHERE product_id=?";
        String ins = "INSERT INTO ProductMaterial(product_id, material_id, quantity) VALUES(?,?,?)";
        try (Connection c = ConnectionFactory.get()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps1 = c.prepareStatement(del)) {
                ps1.setLong(1, productId);
                ps1.executeUpdate();
            }
            if (list != null && !list.isEmpty()) {
                try (PreparedStatement ps2 = c.prepareStatement(ins)) {
                    for (ProductMaterial pm : list) {
                        ps2.setLong(1, productId);
                        ps2.setLong(2, pm.getMaterialId());
                        ps2.setDouble(3, pm.getQuantity());
                        ps2.addBatch();
                    }
                    ps2.executeBatch();
                }
            }
            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasSales(long productId) {
        String sql = "SELECT 1 FROM AgentProductSale WHERE product_id=? LIMIT 1";
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(long productId) {
        String sql = "DELETE FROM Product WHERE product_id=?";
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int bulkIncreaseMinAgentPriceValue(Collection<Long> ids, double delta) {
        if (ids == null || ids.isEmpty()) return 0;
        StringBuilder sb = new StringBuilder("UPDATE Product SET min_agent_price = min_agent_price + ? WHERE product_id IN (");
        for (int i = 0; i < ids.size(); i++) {
            if (i > 0) sb.append(',');
            sb.append('?');
        }
        sb.append(')');
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sb.toString())) {
            int idx = 1;
            ps.setDouble(idx++, delta);
            for (Long id : ids) ps.setLong(idx++, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double averageMinAgentPriceForIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) return 0d;
        StringBuilder sb = new StringBuilder("SELECT AVG(min_agent_price) FROM Product WHERE product_id IN (");
        for (int i = 0; i < ids.size(); i++) {
            if (i > 0) sb.append(',');
            sb.append('?');
        }
        sb.append(')');
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sb.toString())) {
            int idx = 1;
            for (Long id : ids) ps.setLong(idx++, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
                return 0d;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long findWorkshopIdByNumber(Integer number) {
        if (number == null) return null;
        String sql = "SELECT workshop_id FROM Workshop WHERE number=?";
        try (Connection c = ConnectionFactory.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, number);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
