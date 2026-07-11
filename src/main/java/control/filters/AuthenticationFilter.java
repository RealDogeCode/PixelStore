package control.filters;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import Models.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.UserService;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null) {
            Cookie[] cookies = req.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        ServletContext ctx = req.getServletContext();
                        Integer userId = (Integer) ctx.getAttribute(cookie.getValue());

                        if (userId != null) {
                	        DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
                	        UserService us = new UserService(ds);

                            User user;
							try {
								user = us.login(userId);
								
	                            if (user != null) {
	                                req.getSession().setAttribute("user", user);
	                            }
							} catch (SQLException e) {
								e.printStackTrace();
							}
                        }
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }
}