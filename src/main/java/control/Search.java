package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import DAOs.VideogameDAO;
import Models.Videogame;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.sql.DataSource;


@WebServlet("/Search")
public class Search extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("q");

        try {

            DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
            VideogameDAO dao = new VideogameDAO(ds);

            List<Videogame> games;


            if (query == null || query.trim().isEmpty()) {
                games = dao.findAll();
            } else {
                games = dao.search(query);
            }


            request.setAttribute("games", games);
            request.setAttribute("query", query);


            request.getRequestDispatcher("/WEB-INF/views/search.jsp")
                   .forward(request, response);


        } catch (SQLException e) {

            throw new ServletException(e);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}