package control;

import DAOs.OrderDAO;
import Models.OrderItem;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/OrderDetails")
public class OrderDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int orderId = Integer.parseInt(request.getParameter("id"));

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        OrderDAO dao = new OrderDAO(ds);

        try {
            List<OrderItem> items = dao.findItemsByOrderId(orderId);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < items.size(); i++) {
                OrderItem it = items.get(i);

                json.append("{")
                    .append("\"title\":\"").append(it.getTitle()).append("\",")
                    .append("\"price\":").append(it.getPrice()).append(",")
                    .append("\"quantity\":").append(it.getQuantity())
                    .append("}");

                if (i < items.size() - 1) json.append(",");
            }
            json.append("]");

            out.print(json.toString());

        } catch (SQLException e) {
            response.sendError(500);
        }
    }
}