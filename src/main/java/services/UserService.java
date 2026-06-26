package services;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import DAOs.PublisherDAO;
import DAOs.UserDAO;
import Models.Publisher;
import Models.Role;
import Models.User;
import control.UserAlreadyExistsException;
import util.Util;

public class UserService {

    private final UserDAO userDao;
	private final PublisherDAO publisherDao;

    public UserService(DataSource ds) {
        this.userDao = new UserDAO(ds);
        this.publisherDao = new PublisherDAO(ds);
    }

    public User register(String username, String email, String password)
            throws NoSuchAlgorithmException, UserAlreadyExistsException, SQLException {
        User user = new User(0);
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(Util.hashString(password));
        user.setAvatarUrl(null);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setLastLogin(null);
        user.setRoles(List.of(Role.USER));
        try {
			return userDao.create(user);
		} catch (SQLException e) {
			if(e.getErrorCode() == 1062) {
				throw new UserAlreadyExistsException();
			}
			
			throw e;
		}
    }

    public Publisher promoteToPublisher(int userId, String companyName, String description, String logoUrl)
            throws SQLException {

        User user = userDao.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (publisherDao.findById(userId) != null) {
            throw new IllegalStateException("Already publisher");
        }

        Publisher publisher = new Publisher();
        publisher.setUserId(userId);
        publisher.setCompanyName(companyName);
        publisher.setDescription(description);
        publisher.setLogoUrl(logoUrl);

        return publisherDao.create(publisher);
    }
    
    public User login(String email, String password)
            throws SQLException, NoSuchAlgorithmException {

        User user = userDao.findByEmailAndPassword(email, Util.hashString(password));

        if (user == null) {
            return null;
        }

        user.setRoles(userDao.getRolesByUser(user.getId()));

        user.setLastLogin(new Timestamp(System.currentTimeMillis()));
        userDao.updateLastLogin(user.getId(), user.getLastLogin());

        return user;
    }
}