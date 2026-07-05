package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import Models.Role;
import Models.User;

public class UserDAO implements GenericDAO<User, Integer> {
	DataSource ds = null;
	
	public UserDAO(DataSource ds) {
		this.ds = ds;
	}

	
    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password"));
        user.setAvatarUrl(rs.getString("avatar_url"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setLastLogin(rs.getTimestamp("last_login"));
        user.setRoles(new ArrayList<>());
        return user;
    }

    @Override
    public User create(User entity) throws SQLException {
        String sql = "INSERT INTO users (username, email, password, avatar_url, created_at, last_login) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, entity.getUsername());
                ps.setString(2, entity.getEmail());
                ps.setString(3, entity.getPasswordHash());
                ps.setString(4, entity.getAvatarUrl());
                ps.setTimestamp(5, entity.getCreatedAt());
                ps.setTimestamp(6, entity.getLastLogin());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getInt(1));
                    }
                }

                try (PreparedStatement psRole = conn.prepareStatement(
                        "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)")) {

                    psRole.setInt(1, entity.getId());
                    psRole.setInt(2, Role.USER.getValue());
                    psRole.executeUpdate();
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }

        return entity;
    }

    @Override
    public User update(User entity) throws SQLException {
        String sql = "UPDATE users SET username=?, email=?, password=?, avatar_url=?, last_login=? WHERE id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getPasswordHash());
            ps.setString(4, entity.getAvatarUrl());
            ps.setTimestamp(5, entity.getLastLogin());
            ps.setInt(6, entity.getId());

            ps.executeUpdate();
        }

        return entity;
    }
    
    public void updateLastLogin(int id, Timestamp ts) throws SQLException {
        String sql = "UPDATE users SET last_login = ? WHERE id = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, ts);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean userExists(String username, String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ? OR email = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    @Override
    public User findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        }

        return null;
    }

    public User findByEmailAndPassword(String email, String passwordHash) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, passwordHash);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        }

        return null;
    }
    
    @Override
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }
        }

        return users;
    }

    public void addRole(int userId, Role role) throws SQLException {
        String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, role.getValue());

            ps.executeUpdate();
        }
    }

    public List<Role> getRolesByUser(int userId) throws SQLException {
        String sql =
            "SELECT r.name " +
            "FROM roles r " +
            "JOIN user_roles ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = ?";

        List<Role> roles = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                	roles.add(Role.valueOf(rs.getString("name")));
                }
            }
        }

        return roles;
    }
}
