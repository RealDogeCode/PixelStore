package Models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private List<Role> roles; 
    // USER, PUBLISHER, ADMIN

    private String avatarUrl;
	private java.sql.Timestamp createdAt;
	private java.sql.Timestamp lastLogin;


    public User(int id) {
		super();
		this.id = id;
	}
    
    // Copy Constructor
    public User(User user) {
	    this.id = user.id;
	    this.username = user.username;
	    this.email = user.email;
	    this.passwordHash = user.passwordHash;
	    this.roles = user.roles;
	    this.avatarUrl = user.avatarUrl;
	    this.createdAt = user.createdAt;
	    this.lastLogin = user.lastLogin;
    }
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> role) {
		this.roles = role;
	}
	public String getAvatarUrl() {
	    return (avatarUrl == null || avatarUrl.isEmpty())
	            ? "/images/avatars/default_profile_picture.jpg"
	            : avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public java.sql.Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(java.sql.Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public java.sql.Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(java.sql.Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
}
