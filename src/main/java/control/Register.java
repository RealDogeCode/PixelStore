package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;

import javax.sql.DataSource;
import DAOs.UserDAO;
import Models.Role;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Register() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String username = request.getParameter("username");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");

	    try {
	        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
	        UserDAO ud = new UserDAO(ds);

	        ud.register(username, email, password);

	        response.sendRedirect(request.getContextPath() + "/Login");

	    } catch (UserAlreadyExistsException e) {
	    	request.setAttribute("error", e.getMessage());
	    	request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
		} catch (Exception e) {
	    	request.setAttribute("error", "Something Went Wrong...");
	    	request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
	    }
	}
}
