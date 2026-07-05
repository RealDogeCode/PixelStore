package control.filters;

import java.io.IOException;

import javax.sql.DataSource;

import DAOs.UserDAO;
import Models.Role;
import Models.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/Publisher/*")
public class PublisherFilter implements Filter {

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            try {
                UserDAO userDao = new UserDAO(
                    (DataSource) req.getServletContext().getAttribute("ds")
                );

                user.setRoles(userDao.getRolesByUser(user.getId()));

                session.setAttribute("user", user);

            } catch (Exception e) {
                throw new ServletException(e);
            }
        }

        if (user.getRoles().contains(Role.PUBLISHER)) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/Login");
        }
    }
}