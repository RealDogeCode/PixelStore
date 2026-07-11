package control;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import Models.User;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Logout() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();

		ServletContext context = getServletContext();

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        if ("token".equals(cookie.getName())) {

		            context.removeAttribute(cookie.getValue());

		            Cookie deleteCookie = new Cookie("token", "");
		            deleteCookie.setMaxAge(0);

		            response.addCookie(deleteCookie);
		        }
		    }
		}

		response.sendRedirect(request.getContextPath() + "/Homepage");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
