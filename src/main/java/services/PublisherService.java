package services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import DAOs.PublisherDAO;
import DAOs.UserDAO;
import Models.Publisher;
import Models.Role;

public class PublisherService {

    private final PublisherDAO dao;
    private final DataSource ds;

    public PublisherService(DataSource ds) {
    	this.ds = ds;
        this.dao = new PublisherDAO(ds);
    }

    public Publisher requestPublisher(int userId, String name, String desc, String logourl)
            throws SQLException {

        Publisher p = new Publisher();

        p.setUserId(userId);
        p.setCompanyName(name);
        p.setDescription(desc);
        p.setLogoUrl(logourl);

        p.setStatus("PENDING");
        p.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return dao.create(p);
    }

    public Publisher approve(int userId) throws SQLException {

        Publisher p = dao.findById(userId);

        if (p == null) {
            throw new IllegalArgumentException("Publisher not found");
        }

        if (!p.getStatus().equals("PENDING")) {
            throw new IllegalStateException("Already processed");
        }

        UserService us = new UserService(this.ds);
        
        us.addRole(userId, Role.PUBLISHER);
        p.setStatus("ACTIVE");

        return dao.update(p);
    }

    public Publisher reject(int userId) throws SQLException {

        Publisher p = dao.findById(userId);

        if (p == null) {
            throw new IllegalArgumentException("Publisher not found");
        }

        if (!p.getStatus().equals("PENDING")) {
            throw new IllegalStateException("Already processed");
        }

        p.setStatus("REJECTED");

        return dao.update(p);
    }
    
    public List<Publisher> getPendingPublishers() {
        try {
			return dao.findPending();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
}