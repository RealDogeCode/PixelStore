<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Models.Videogame" %>
<%@ page import="Models.Tag" %>
<%@ page import="java.util.List" %>

<%
    List<Videogame> games = (List<Videogame>) request.getAttribute("games");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Publisher - Modify Games</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
<script src="${pageContext.request.contextPath}/scripts/publisher_modify_game.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />

<div class="layout">
    <jsp:include page="/WEB-INF/components/publisher_navbar.jsp" />

    <div class="content">
        <h1>Games List</h1>
        <p>Select a game to modify.</p>

        <% if (games != null && !games.isEmpty()) { %>
            <ul class="list padded">
                <% for (Videogame game : games) { %>
                    <li style="width: 100%; margin-bottom: 10px;">
                        <span><%= game.getTitle() %></span>

						<button class="btn" type="button"
						onclick="editGame(
						'<%= game.getId() %>',
						'<%= game.getTitle().replace("'", "\\'") %>',
						'<%= game.getDescription().replace("'", "\\'").replace("\n", " ") %>',
						'<%= game.getPrice() %>',
						[
						<%
						    if (game.getTags() != null) {
						        for (int i = 0; i < game.getTags().size(); i++) {
						            out.print(game.getTags().get(i).getId());
						            if (i < game.getTags().size() - 1) out.print(",");
						        }
						    }
						%>
						]
						)">       
							Modify
                        </button>
                    </li>
                <% } %>
            </ul>
        <% } else { %>
            <p>No games found.</p>
        <% } %>

        <div id="editFormContainer">
            <h2>Edit Game</h2>

            <form id="hidden_form" class="form-card hidden" method="post" action="<%= request.getContextPath() %>/Publisher/Dashboard?section=modify_game" enctype="multipart/form-data">
                <input type="hidden" name="gameId" id="gameId">

                <label>Game Title</label>
                <input type="text" name="title" id="title" required>

                <label>Description</label>
                <textarea name="description" id="description" required></textarea>

                <label>Price</label>
                <input type="number" step="0.01" name="price" id="price" required>

				<label>Tags</label>
				<select name="tagIds" id="tagIds" multiple size="5">
				    <% 
				        List<Tag> allTags = (List<Tag>) request.getAttribute("tags");
				        if (allTags != null) {
				            for (Tag t : allTags) {
				    %>
				        <option value="<%= t.getId() %>">
				            <%= t.getName() %>
				        </option>
				    <%
				            }
				        }
				    %>
				</select>

                <label>New Banner Image</label>
                <input type="file" name="cover">

                <input type="submit" value="Save Changes">
            </form>
        </div>
    </div>
</div>

</body>
</html>