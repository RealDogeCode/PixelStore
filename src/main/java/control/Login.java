package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import Models.User;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
		
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    Boolean keepLogged = Boolean.parseBoolean(request.getParameter("keeplogged"));

	    try {
	        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
	        UserService us = new UserService(ds);
        	ServletContext context = getServletContext();

	        
	        User user = us.login(email, password);

	        if(user == null) {
	        	request.setAttribute("error", "Invalid Credentials...");
		    	request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		    	return;
	        }
	        	        
	        if(keepLogged) {
	        	UUID uuid = UUID.randomUUID();
	        	
	        	Cookie cookie = new Cookie("token", uuid.toString());
	        	cookie.setMaxAge(60 * 60 * 24 * 30);
	        	cookie.setHttpOnly(true);
	        	cookie.setSecure(true);
	        	cookie.setPath(request.getContextPath());

	        	response.addCookie(cookie);
	        	

	        	context.setAttribute(uuid.toString(), user.getId());
	        }
	        
	        request.getSession().setAttribute("user", user);
	        
	        response.sendRedirect(request.getContextPath() + "/Homepage");
		} catch (Exception e) {
	    	request.setAttribute("error", e.getMessage());
	    	request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	    }
	}
}
