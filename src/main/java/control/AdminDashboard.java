package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.PublisherService;
import services.UserService;

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
            response.sendRedirect("Login");
            return;
        }

        loadApplications(request);

        request.getRequestDispatcher("/WEB-INF/views/admin_applications.jsp")
               .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("Login");
            return;
        }

        String action = request.getParameter("action");
        String userIdStr = request.getParameter("userId");

        if (action == null || userIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/Admin/Dashboard?section=application");
            return;
        }

        int userId = Integer.parseInt(userIdStr);

        DataSource ds = (DataSource) getServletContext().getAttribute("ds");
        PublisherService ps = new PublisherService(ds);
        
        try {
            switch (action) {
                case "approve":
                    ps.approve(userId);
                    break;

                case "reject":
                    ps.reject(userId);
                    break;
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