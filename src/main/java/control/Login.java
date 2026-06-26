package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.UserService;

import java.io.IOException;

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

	    try {
	        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
	        UserService us = new UserService(ds);
	        User user = us.login(email, password);

	        if(user == null) {
	        	request.setAttribute("error", "Invalid Credentials...");
		    	request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		    	return;
	        }
	        request.getSession().setAttribute("user", user);
	        
	        response.sendRedirect(request.getContextPath() + "/Homepage");
		} catch (Exception e) {
	    	request.setAttribute("error", e.getMessage());
	    	request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	    }
	}
}
