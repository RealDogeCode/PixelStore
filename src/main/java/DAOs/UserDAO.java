package DAOs;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import Models.Role;
import Models.User;
import control.UserAlreadyExistsException;

public class UserDAO {
	DataSource ds = null;
	
	public UserDAO(DataSource ds) {
		this.ds = ds;
	}
	
	public List<Role> getRolesByUser(int userId) throws SQLException {
		String sql = "SELECT r.name FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?";

		List<Role> roles = new ArrayList<>();
		
		try (Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) 
		{
			ps.setInt(1, userId);
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					roles.add(Role.valueOf(rs.getString("name").trim().toUpperCase()));
				}
			}
		}
		
		return roles;
	}
	
	// Metodo helper.
	// In un'architettura più pulita questa logica apparterrebbe a un Service layer.
	// Per semplicità viene mantenuta nel DAO.
	public void register(String username, String email, String password) throws NoSuchAlgorithmException, SQLException, UserAlreadyExistsException {
		String hashedPassword = hashString(password);
        
        this.createUser(username, email, hashedPassword, Role.USER, null);
	}
	
	// Metodo helper riutilizzabile per hashing SHA-512.
	public String hashString(String text) throws NoSuchAlgorithmException {
		// Le rainbow table possono facilmente rendere questo algoritmo inefficace, ma la sicurezza non è importante per un progettino universitario, is it?
		MessageDigest md = MessageDigest.getInstance("SHA-512"); 
        byte[] bytes = md.digest(text.getBytes(StandardCharsets.UTF_8));

        StringBuilder digest = new StringBuilder();
        for (byte b : bytes) {
            digest.append(String.format("%02x", b));
        }

        return digest.toString();
	}
	
    public void updateUser(User user) throws SQLException, UserAlreadyExistsException {
        String sql = "UPDATE users SET username = ?, email = ?, password = ?, avatar_url = ?, last_login = ? WHERE id = ?";

        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);

            try {
                if (userExist(conn, user.getUsername(), user.getEmail(), user.getId())) {
                    throw new UserAlreadyExistsException();
                }

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getPasswordHash());
                    ps.setString(4, user.getAvatarUrl());
                    ps.setTimestamp(5, user.getLastLogin());
                    ps.setInt(6, user.getId());

                    ps.executeUpdate();
                }

                conn.commit();

            } catch (SQLException | UserAlreadyExistsException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
	
	public User login(String email, String password)
	        throws SQLException, NoSuchAlgorithmException {

	    String sqlCheck =
	            "SELECT id, username, email, password, avatar_url, created_at, last_login " +
	            "FROM users WHERE email = ? AND password = ?";

	    String sqlUpdate =
	            "UPDATE users SET last_login = NOW() WHERE id = ?";

	    User user = null;

	    try (Connection conn = ds.getConnection();
	         PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {

	        psCheck.setString(1, email);
	        psCheck.setString(2, hashString(password));

	        try (ResultSet rs = psCheck.executeQuery()) {

	            if (rs.next()) {

	                int id = rs.getInt("id");

	                user = mapUser(rs);
	                user.setRoles(getRolesByUser(user.getId()));
	                user.setLastLogin(new java.sql.Timestamp(System.currentTimeMillis()));

	                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
	                    psUpdate.setInt(1, id);
	                    psUpdate.executeUpdate();
	                }
	            }
	        }
	    }

	    return user;
	}
	
	public boolean userExist(Connection conn, String username, String email) throws SQLException {
	    return userExist(conn, username, email, -1);
	}

	public boolean userExist(Connection conn, String username, String email, int excludeId) throws SQLException {
	    String sql = "SELECT 1 FROM users WHERE (username = ? OR email = ?) AND id != ?";
	    
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, username);
	        ps.setString(2, email);
	        ps.setInt(3, excludeId);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            return rs.next();
	        }
	    }
	}
	
	public void createUser(String username, String email, String password, Role role, String avatarUrl)
            throws SQLException, UserAlreadyExistsException {

        String sqlUser = "INSERT INTO users (username, email, password, avatar_url, created_at, last_login) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";

        String sqlRole = "INSERT INTO user_roles (user_id, role_id) " +
                         "VALUES (?, (SELECT id FROM roles WHERE name = ?))";

        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);

            try {
                if (userExist(conn, username, email)) {
                    throw new UserAlreadyExistsException();
                }

                try (PreparedStatement psUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {
                    psUser.setString(1, username);
                    psUser.setString(2, email);
                    psUser.setString(3, password);
                    psUser.setString(4, avatarUrl);
                    psUser.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
                    psUser.setTimestamp(6, null);

                    psUser.executeUpdate();

                    int userId;
                    try (ResultSet rs = psUser.getGeneratedKeys()) {
                        rs.next();
                        userId = rs.getInt(1);
                    }

                    try (PreparedStatement psRole = conn.prepareStatement(sqlRole)) {
                        psRole.setInt(1, userId);
                        psRole.setString(2, role.name());
                        psRole.executeUpdate();
                    }
                }

                conn.commit();

            } catch (SQLException | UserAlreadyExistsException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
	
	public User getUser(int user_id) throws SQLException {
		String sql = "SELECT id, username, email, password, avatar_url, created_at, last_login FROM users WHERE id = ?";

		User user = null;
		
		try (Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, user_id);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						user = mapUser(rs);
		            }
		        }
	    }
		
		return user;
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

}
