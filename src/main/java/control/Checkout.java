package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import DAOs.OrderDAO;
import Models.CartModel;
import Models.Order;
import Models.User;

@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Checkout() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
	    HttpSession session = request.getSession();

		if(session.getAttribute("user") == null) {
        	request.setAttribute("error", "You need to login first...");
	    	request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
			return;
		}
		
	    CartModel cart = (CartModel) session.getAttribute("cart");

	    if (cart == null || cart.isEmpty()) {
	    	request.setAttribute("status", "error");
        	request.setAttribute("error", "What are you trying to do with an empty cart? You can't pay €0...");
	        request.getRequestDispatcher("/WEB-INF/views/cart.jsp")
	    	.forward(request, response);
	        return;
	    }
	    
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp")
    	.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    CartModel cart = (CartModel) session.getAttribute("cart");

	    try {
	        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

	        OrderDAO orderDAO = new OrderDAO(ds);

	        Order order = new Order();
		    User user = (User) session.getAttribute("user");
	        order.setUserId(user.getId());
	        order.setTotal(cart.getTotal());

	        orderDAO.create(order);
	        orderDAO.insertOrderItems(order.getId(), cart);
	        cart.getItems().clear();

	        session.setAttribute("cart", cart);

	        response.sendRedirect("Cart?status=success");
	    } catch (SQLException e) {
	        throw new ServletException("Errore durante checkout", e);
	    }

	}

}
