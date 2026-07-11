package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import DAOs.VideogameDAO;

@WebServlet("/Publisher/RemoveGame")
public class RemoveGame extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String gameId = request.getParameter("gameId");

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        VideogameDAO dao = new VideogameDAO(ds);
        try {
			dao.delete(Integer.parseInt(gameId));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        response.sendRedirect(request.getContextPath() + "/Publisher/Dashboard");
    }
}
