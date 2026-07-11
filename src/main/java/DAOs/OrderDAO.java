package DAOs;

import Models.CartModel;
import Models.Order;
import Models.OrderItem;
import Models.OrdersReport;
import Models.Videogame;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements GenericDAO<Order, Integer> {

    private final DataSource ds;

    public OrderDAO(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Order create(Order order) throws SQLException {
        String sql = "INSERT INTO orders (user_id, total) VALUES (?, ?)";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotal());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                order.setId(rs.getInt(1));
            }
        }

        return order;
    }

    @Override
    public Order update(Order order) throws SQLException {
        String sql = "UPDATE orders SET total=? WHERE id=?";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, order.getTotal());
            ps.setInt(2, order.getId());

            ps.executeUpdate();
        }

        return order;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id=?";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Order findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE id=?";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setTotal(rs.getDouble("total"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                return o;
            }
        }

        return null;
    }

    @Override
    public List<Order> findAll() throws SQLException {
        List<Order> list = new ArrayList<>();

        String sql = "SELECT * FROM orders";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setTotal(rs.getDouble("total"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(o);
            }
        }

        return list;
    }

    public void insertOrderItems(int orderId, CartModel cart) throws SQLException {
        String sql = """
            INSERT INTO order_items 
            (order_id, videogame_id, title, publisher, price, quantity)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (Videogame v : cart.getItems()) {

                int quantity = cart.getQuantity(v.getId());

                ps.setInt(1, orderId);
                ps.setInt(2, v.getId());
                ps.setString(3, v.getTitle());
                ps.setString(4, v.getPublisherName());
                ps.setDouble(5, v.getDiscountedPrice());
                ps.setInt(6, quantity);

                ps.addBatch();
            }

            ps.executeBatch();
        }
    }
    
    public List<OrderItem> findItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();

        String sql = """
            SELECT id, order_id, videogame_id, title, publisher, price, quantity
            FROM order_items
            WHERE order_id = ?
        """;

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem it = new OrderItem();

                it.setId(rs.getInt("id"));
                it.setOrderId(rs.getInt("order_id"));
                it.setVideogameId(rs.getInt("videogame_id"));
                it.setTitle(rs.getString("title"));
                it.setPublisher(rs.getString("publisher"));
                it.setPrice(rs.getDouble("price"));
                it.setQuantity(rs.getInt("quantity"));

                items.add(it);
            }
        }

        return items;
    }
    
    public List<OrdersReport> getOrdersByCustomer(int publisherId, Date from, Date to) throws SQLException {
        List<OrdersReport> result = new ArrayList<>();

        String sql = """
            SELECT u.username,
                   COUNT(DISTINCT o.id) AS total_orders
            FROM orders o
            JOIN users u ON o.user_id = u.id
            JOIN order_items oi ON o.id = oi.order_id
            JOIN videogames v ON oi.videogame_id = v.id
            WHERE v.publisher_id = ?
            AND DATE(o.created_at) BETWEEN ? AND ?
            GROUP BY u.id, u.username
            ORDER BY total_orders DESC
        """;

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, publisherId);
            ps.setDate(2, from);
            ps.setDate(3, to);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                OrdersReport report = new OrdersReport();

                report.setCustomerName(rs.getString("username"));
                report.setTotalOrders(rs.getInt("total_orders"));

                result.add(report);
            }
        }

        return result;
    }
    
    public List<Order> findByUserId(int userId) throws SQLException {
        List<Order> list = new ArrayList<>();

        String sql = "SELECT * FROM orders WHERE user_id=?";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setTotal(rs.getDouble("total"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(o);
            }
        }

        return list;
    }
}