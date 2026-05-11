package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import DAOs.UserDAO;
import Models.User;

@WebServlet("/Account")
@MultipartConfig
public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Account() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String section = request.getParameter("section");

		if(request.getSession().getAttribute("user") == null) {
			response.sendRedirect("Login");
			return;
		}
		
		if (section == null) section = "info";

		switch (section) {
		    case "info":
		        request.getRequestDispatcher("/WEB-INF/views/account_info.jsp").forward(request, response);
		        break;

		    case "payment":
		        request.getRequestDispatcher("/WEB-INF/views/account_payment_method.jsp").forward(request, response);
		        break;

		    case "orders":
		        request.getRequestDispatcher("/WEB-INF/views/account_my_orders.jsp").forward(request, response);
		        break;

		    case "publisher":
		        request.getRequestDispatcher("/WEB-INF/views/account_become_publisher.jsp").forward(request, response);
		        break;
		    default:
		        request.getRequestDispatcher("/WEB-INF/views/account_info.jsp").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String section = request.getParameter("section");

		if (section == null) section = "info";

		switch (section) {
		    case "info":
			try {
				handleInfo(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		    case "payment":
		        break;

		    case "orders":
		        break;

		    case "publisher":
		        break;
		}
	}

	private void handleInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		
		Part filePart = request.getPart("avatar");
		
		UserDAO ud = new UserDAO((DataSource) request.getServletContext().getAttribute("ds"));
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		User updatedUser = new User(user);
		updatedUser.setUsername(username);
		updatedUser.setEmail(email);	
			
		if (filePart != null && filePart.getSize() > 0) {
			String contentType = filePart.getContentType();
			if (!contentType.startsWith("image/")) {
			    request.setAttribute("error", "Il file deve essere un'immagine.");
			    request.getRequestDispatcher("/WEB-INF/views/account_info.jsp").forward(request, response);
			    return;
			}
			String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
			String uploadDir = getServletContext().getRealPath("/images/avatars");
			filePart.write(uploadDir + "/" + fileName);
			updatedUser.setAvatarUrl("/images/avatars/" + fileName);
			
			System.out.println("Saving to: " + uploadDir);
		}
		
		try {
		    ud.updateUser(updatedUser);
		} catch (UserAlreadyExistsException e) {
		    request.setAttribute("error", e.getMessage());
		    request.getRequestDispatcher("/WEB-INF/views/account_info.jsp").forward(request, response);
		    return;
		} catch (Exception e) {
		    request.setAttribute("error", "Something went wrong...");
		    request.getRequestDispatcher("/WEB-INF/views/account_info.jsp").forward(request, response);
		    return;
		}
		
		session.setAttribute("user", updatedUser);
		response.sendRedirect(request.getContextPath() + "/Account?section=info&success=1");
	}
}
