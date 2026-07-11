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
<title>PixelStore | Publisher - Modify Games</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
<script src="${pageContext.request.contextPath}/scripts/publisher_modify_game.js"></script>
<script src="${pageContext.request.contextPath}/scripts/validation.js"></script>

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

						<div class="request-actions">
							<button class="btn" type="button"
							    onclick="editGame(
							        '<%= game.getId() %>',
							        '<%= game.getTitle().replace("'", "\\'") %>',
							        '<%= game.getDescription().replace("'", "\\'").replace("\n", " ") %>',
							        '<%= game.getPrice() %>',
							        '<%= game.getDiscountPercentage() %>',
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
							<form action="RemoveGame" method="post">
							    <input type="hidden" name="gameId" value="<%= game.getId() %>">
							    <button class="btn" type="submit">Remove</button>
							</form>
						</div>
                    </li>
                <% } %>
            </ul>
        <% } else { %>
            <p class="alert alert-info">No games found.</p>
        <% } %>

        <div id="editFormContainer">
            <h2>Edit Game</h2>

			<form id="hidden_form"
			      class="form-card hidden"
			      method="post"
			      action="<%= request.getContextPath() %>/Publisher/Dashboard?section=modify_game"
			      enctype="multipart/form-data"
			      onsubmit="return this.reportValidity();">
			
			    <input type="hidden"
			           name="gameId"
			           id="gameId">
			
			
			    <label>Game Title</label>
			    <input type="text"
			           name="title"
			           id="title"
			           required
			           minlength="3"
			           maxlength="100"
			           onchange="validateFormElem(this, document.getElementById('modifyTitleError'))">
			    <span id="modifyTitleError"></span>
			
			
			    <label>Description</label>
			    <textarea name="description"
			              id="description"
			              required
			              minlength="20"
			              maxlength="1000"
			              onchange="validateFormElem(this, document.getElementById('modifyDescriptionError'))"></textarea>
			    <span id="modifyDescriptionError"></span>
			
			
			    <label>Price</label>
			    <input type="number"
			           step="0.01"
			           name="price"
			           id="price"
			           required
			           min="0"
			           onchange="validateFormElem(this, document.getElementById('modifyPriceError'))">
			    <span id="modifyPriceError"></span>
			
			<label>Discount</label>
			<div>
			    <input type="range"
			           name="discountPercentage"
			           id="discountPercentage"
			           min="0"
			           max="100"
			           value="0"
			           oninput="document.getElementById('discountValue').innerText = this.value + '%'">
			
			    <span id="discountValue">0%</span>
			</div>
			
			    <label>Tags</label>
			    <select name="tagIds"
			            id="tagIds"
			            multiple
			            size="5"
			            required
			            onchange="validateFormElem(this, document.getElementById('modifyTagsError'))">
			
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
			    <span id="modifyTagsError"></span>
			
			
			    <label>New Banner Image</label>
			    <div class="file-uploader">
			        <label class="file-label">
			            Scegli file
			            <input type="file"
			                   name="cover"
			                   accept="image/*"
			                   onchange="validateFormElem(this, document.getElementById('modifyCoverError'))">
			        </label>
			
			        <span id="modifyCoverError" class="file-error"></span>
			    </div>
			
			
			    <input type="submit" value="Save Changes">
			
			</form>
        </div>
    </div>
</div>

</body>
</html>