package DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import Models.Publisher;

public class PublisherDAO implements GenericDAO<Publisher, Integer> {
    private final DataSource ds;

    public PublisherDAO(DataSource ds) {
        this.ds = ds;
    }

    private Publisher map(ResultSet rs) throws SQLException {
        Publisher p = new Publisher();

        p.setUserId(rs.getInt("user_id"));
        p.setCompanyName(rs.getString("company_name"));
        p.setDescription(rs.getString("description"));
        p.setLogoUrl(rs.getString("logo_url"));

        p.setStatus(rs.getString("status"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        p.setUpdatedAt(rs.getTimestamp("updated_at"));

        return p;
    }

    public Publisher create(Publisher p) throws SQLException {
        String sql = "INSERT INTO publishers (user_id, company_name, description, logo_url, status, created_at) VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getUserId());
            ps.setString(2, p.getCompanyName());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getLogoUrl());
            ps.setString(5, p.getStatus());

            ps.executeUpdate();
        }

        return findById(p.getUserId());
    }

    public List<Publisher> findAll() throws SQLException {
        List<Publisher> list = new ArrayList<>();

        String sql = "SELECT * FROM publishers";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }

    public Publisher update(Publisher p) throws SQLException {
        String sql = "UPDATE publishers SET company_name=?, description=?, logo_url=?, status=?, updated_at=NOW() WHERE user_id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getCompanyName());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getLogoUrl());
            ps.setString(4, p.getStatus());
            ps.setInt(5, p.getUserId());

            ps.executeUpdate();
        }

        return p;
    }

    public List<Publisher> findPending() throws SQLException {
        String sql = "SELECT * FROM publishers WHERE status = 'PENDING'";

        List<Publisher> list = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }

	@Override
    public boolean delete(Integer userId) throws SQLException {
        String sql = "DELETE FROM publishers WHERE user_id = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            return ps.executeUpdate() > 0;
        }
    }

	@Override
    public Publisher findById(Integer userId) throws SQLException {
        String sql = "SELECT * FROM publishers WHERE user_id = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }

        return null;
    }
	
	public Publisher getPublisherByGameId(int gameId) throws SQLException {
	    String sql = "SELECT p.* FROM publishers p JOIN videogames v ON v.publisher_id = p.user_id WHERE v.id = ?";

	    try (Connection conn = ds.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, gameId);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return map(rs);
	            }
	        }
	    }

	    return null;
	}
}