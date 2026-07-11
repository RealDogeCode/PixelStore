package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import DAOs.VideogameDAO;
import Models.Tag;
import Models.Videogame;


@WebServlet("/SearchApi")
public class SearchAPI extends HttpServlet {

    private static final long serialVersionUID = 1L;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {


        String query = request.getParameter("q");


        if (query == null) {
            query = "";
        }


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        try {

            DataSource ds =
                (DataSource) request.getServletContext().getAttribute("ds");


            VideogameDAO dao = new VideogameDAO(ds);


            List<Videogame> games = dao.search(query);


            StringBuilder json = new StringBuilder();

            json.append("[");


            for (int i = 0; i < games.size(); i++) {

                Videogame g = games.get(i);


                json.append("{");


                json.append("\"id\":")
                    .append(g.getId())
                    .append(",");


                json.append("\"title\":\"")
                    .append(g.getTitle().replace("\"", "\\\""))
                    .append("\",");


                json.append("\"bannerUrl\":\"")
                    .append(g.getBannerUrl())
                    .append("\",");


                json.append("\"publisherName\":\"")
                    .append(g.getPublisherName())
                    .append("\",");


                json.append("\"price\":")
                    .append(g.getPrice())
                    .append(",");


                json.append("\"discountPercentage\":")
                    .append(g.getDiscountPercentage())
                    .append(",");


                json.append("\"discountedPrice\":")
                    .append(g.getDiscountedPrice())
                    .append(",");


                json.append("\"tags\":[");


                for (int j = 0; j < g.getTags().size(); j++) {

                    Tag tag = g.getTags().get(j);


                    json.append("{");

                    json.append("\"name\":\"")
                        .append(tag.getName().replace("\"", "\\\""))
                        .append("\"");

                    json.append("}");


                    if (j < g.getTags().size() - 1) {
                        json.append(",");
                    }
                }


                json.append("]");


                json.append("}");


                if (i < games.size() - 1) {
                    json.append(",");
                }
            }


            json.append("]");


            response.getWriter().write(json.toString());


        } catch (SQLException e) {

            response.setStatus(500);

            response.getWriter()
                    .write("{\"error\":\"Database error\"}");
        }
    }
}