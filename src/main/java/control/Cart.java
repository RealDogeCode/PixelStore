package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.io.IOException;
import java.sql.SQLException;

import DAOs.VideogameDAO;
import Models.CartModel;
import Models.Videogame;

@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Cart() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartModel();
            session.setAttribute("cart", cart);
        }

        String addIdParam = request.getParameter("add_id");
        String removeIdParam = request.getParameter("remove_id");

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        VideogameDAO dao = new VideogameDAO(ds);

        try {

            if (addIdParam != null) {
                int id = Integer.parseInt(addIdParam);

                Videogame game = dao.findById(id);
                cart.addItem(game);
            }

            if (removeIdParam != null) {
                int id = Integer.parseInt(removeIdParam);

                cart.removeItem(id);
            }

        } catch (NumberFormatException e) {
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("cart", cart.getItems());

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp")
               .forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
