package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.PublisherService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import Models.Publisher;

@WebServlet("/Admin/Dashboard")
public class AdminDashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        loadApplications(request);

        request.getRequestDispatcher("/WEB-INF/views/admin_applications.jsp")
               .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String action = request.getParameter("action");
        int userId = Integer.parseInt(request.getParameter("userId"));

        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        PublisherService ps = new PublisherService(ds);

        try {
            if ("approve".equals(action)) {
                ps.approve(userId);
            } else if ("reject".equals(action)) {
                ps.reject(userId);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/Admin/Dashboard?section=application");
    }

    private void loadApplications(HttpServletRequest request) {
        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
        PublisherService ps = new PublisherService(ds);

        List<Publisher> publishers = ps.getPendingPublishers();
        request.setAttribute("applications", publishers);
    }
}