package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import services.PublisherService;
import services.UserService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import DAOs.OrderDAO;
import DAOs.PublisherDAO;
import DAOs.UserDAO;
import Models.Order;
import Models.Publisher;
import Models.Role;
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
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
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
		    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
		    	OrderDAO dao = new OrderDAO(ds);
				List<Order> orders;
				try {
					orders = dao.findByUserId(user.getId());
				} catch (SQLException e) {
					e.printStackTrace();
					return;
				}
		    	request.setAttribute("orders", orders);
		        request.getRequestDispatcher("/WEB-INF/views/account_my_orders.jsp").forward(request, response);
		        break;

		    case "publisher":
		    	if(((User)request.getSession().getAttribute("user")).getRoles().contains(Role.PUBLISHER)) {
			        request.getRequestDispatcher("/WEB-INF/views/account_info.jsp").forward(request, response);
			        break;
		    	}
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
		    	handlePublisher(request, response);
		        break;
		}
	}

	private void handlePublisher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    User user = (User) session.getAttribute("user");

	    if (user == null) {
	        response.sendRedirect(request.getContextPath() + "/Login");
	        return;
	    }

	    DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
	    PublisherService pservice = new PublisherService(ds);

	    try {
	        String name = request.getParameter("name");
	        String desc = request.getParameter("desc");

	        Part logoPart = request.getPart("logo");

	        String logoUrl = null;

	        if (logoPart != null && logoPart.getSize() > 0) {
	            String contentType = logoPart.getContentType();
	            if (!contentType.startsWith("image/")) {
				    request.setAttribute("error", "Il file deve essere un'immagine.");
	            }

	            String fileName = System.currentTimeMillis() + "_" + logoPart.getSubmittedFileName();

	            String uploadPath = getServletContext().getRealPath("/images/avatars");

	            File dir = new java.io.File(uploadPath);
	            if (!dir.exists()) dir.mkdirs();

	            logoPart.write(uploadPath + java.io.File.separator + fileName);

	            logoUrl = request.getContextPath() + "/images/avatars/" + fileName;
	        }

	        pservice.requestPublisher(user.getId(), name, desc, logoUrl);
	        
	        response.sendRedirect("Account?section=publisher&status=success");

	    } catch (SQLException e) {
	    	if(e.getErrorCode() == 1062) {
	    		request.setAttribute("error", "La richiesta è già stata mandata, per favore, attendi che un amministratore ti accetti.");
	    		request.getRequestDispatcher("/WEB-INF/views/account_become_publisher.jsp")
	    		       .forward(request, response);
	    		return;
	    	}
	        response.sendRedirect(request.getContextPath() + "/Account?section=publisher");
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
		    ud.update(updatedUser);
		} catch (SQLException e) {
			if(e.getErrorCode() == 1062) {
			    request.setAttribute("error", "Le informazioni che hai tentato di modificare appartengono ad un altro utente.");
			    request.getRequestDispatcher("/WEB-INF/views/account_info.jsp").forward(request, response);
			}
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
