package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import DAOs.VideogameDAO;
import Models.*;

@WebServlet("/Publisher/Dashboard")
@MultipartConfig
public class PublisherDashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (!user.getRoles().contains(Role.PUBLISHER)) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String section = request.getParameter("section");
        if (section == null) section = "dashboard";

        DataSource ds = (DataSource) getServletContext().getAttribute("ds");
        VideogameDAO vd = new VideogameDAO(ds);

		try {
			request.setAttribute("tags", vd.findAllTags());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        switch (section) {
            case "add_game":
                request.getRequestDispatcher("/WEB-INF/views/publisher_add_game.jsp")
                        .forward(request, response);
                break;

            case "modify_game":
                try {
                    request.setAttribute("games", vd.findByPublisher(user.getId()));
                } catch (SQLException e) {
                    throw new ServletException(e);
                }

                request.getRequestDispatcher("/WEB-INF/views/publisher_modify_game.jsp")
                        .forward(request, response);
                break;

            default:
                request.getRequestDispatcher("/WEB-INF/views/publisher_add_game.jsp")
                        .forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (!user.getRoles().contains(Role.PUBLISHER)) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String section = request.getParameter("section");
        if (section == null) section = "add_game";

        switch (section) {

            case "add_game":
                try {
                    handleAddGame(request, response, user);
                } catch (Exception e) {
                    throw new ServletException(e);
                }
                break;

            case "modify_game":
                try {
                    handleModifyGame(request, response, user);
                } catch (Exception e) {
                    throw new ServletException(e);
                }
                break;
        }
    }

    private void handleAddGame(HttpServletRequest request,
                               HttpServletResponse response,
                               User user)
            throws SQLException, IOException, ServletException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        String[] tagIds = request.getParameterValues("tagIds");

        Part coverPart = request.getPart("cover");

        String coverUrl = null;

        if (coverPart != null && coverPart.getSize() > 0) {
            String contentType = coverPart.getContentType();
            if (!contentType.startsWith("image/")) {
                request.setAttribute("error", "File must be an image");
                request.getRequestDispatcher("/WEB-INF/views/publisher_add_game.jsp")
                        .forward(request, response);
                return;
            }

            String fileName = System.currentTimeMillis() + "_" + coverPart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("/images/games");

            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            coverPart.write(uploadPath + File.separator + fileName);

            coverUrl = request.getContextPath() + "/images/games/" + fileName;
        }

        DataSource ds = (DataSource) getServletContext().getAttribute("ds");
        VideogameDAO vd = new VideogameDAO(ds);

        Videogame vg = new Videogame();
        vg.setTitle(title);
        vg.setDescription(description);
        vg.setPrice(price);
        vg.setPublisherId(user.getId());
        vg.setBannerUrl(coverUrl);
        vg.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        vd.create(vg);

        if (tagIds != null) {
            for (String t : tagIds) {
                vd.addTag(vg.getId(), Integer.parseInt(t));
            }
        }

        response.sendRedirect(request.getContextPath() + "/Publisher/Dashboard?section=dashboard&success=1");
    }

    private void handleModifyGame(HttpServletRequest request,
                                  HttpServletResponse response,
                                  User user)
            throws SQLException, IOException, ServletException {

        int gameId = Integer.parseInt(request.getParameter("gameId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int discount = Integer.parseInt(request.getParameter("discountPercentage"));

        String[] tagIds = request.getParameterValues("tagIds");

        DataSource ds = (DataSource) getServletContext().getAttribute("ds");
        VideogameDAO vd = new VideogameDAO(ds);

        Videogame vg = vd.findById(gameId);

        if (vg == null || vg.getPublisherId() != user.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        vg.setTitle(title);
        vg.setDescription(description);
        vg.setPrice(price);
        vg.setDiscountPercentage(discount);

        Part coverPart = request.getPart("cover");

        if (coverPart != null && coverPart.getSize() > 0) {

            String contentType = coverPart.getContentType();

            if (!contentType.startsWith("image/")) {
                request.setAttribute("error", "File must be an image");
                request.getRequestDispatcher("/WEB-INF/views/publisher_modify_game.jsp")
                        .forward(request, response);
                return;
            }

            String fileName = System.currentTimeMillis() + "_" + coverPart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("/images/games");

            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            coverPart.write(uploadPath + File.separator + fileName);

            vg.setBannerUrl(request.getContextPath() + "/images/games/" + fileName);
        }

        vd.update(vg);

        vd.deleteTags(gameId);

        if (tagIds != null) {
            for (String t : tagIds) {
                vd.addTag(gameId, Integer.parseInt(t));
            }
        }

        response.sendRedirect(request.getContextPath() + "/Publisher/Dashboard?section=modify_game&success=1");
    }
}