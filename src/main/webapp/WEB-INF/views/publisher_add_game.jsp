<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Models.Tag" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>PixelStore | Publisher - Add Game</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>

<body>
<jsp:include page="/WEB-INF/components/navbar.jsp" />

<div class="layout">
    <jsp:include page="/WEB-INF/components/publisher_navbar.jsp" />

    <div class="content">
        <div>
            <h1>Add New Game</h1>
            <p>
                Create and publish a new game
            </p>

            <form method="post"
			      class="form-card"
			      action="<%= request.getContextPath() %>/Publisher/Dashboard?section=add_game"
			      enctype="multipart/form-data"
			      onsubmit="return this.reportValidity();">
			
			    <label>Game Title</label>
			    <input type="text"
			           name="title"
			           required
			           minlength="3"
			           maxlength="100"
			           onchange="validateFormElem(this, document.getElementById('titleError'))">
			    <span id="titleError"></span>
			
			
			    <label>Description</label>
			    <textarea name="description"
			              required
			              minlength="20"
			              maxlength="1000"
			              onchange="validateFormElem(this, document.getElementById('descriptionError'))"></textarea>
			    <span id="descriptionError"></span>
			
			
			    <label>Price</label>
			    <input type="number"
			           step="0.01"
			           name="price"
			           required
			           min="0"
			           onchange="validateFormElem(this, document.getElementById('priceError'))">
			    <span id="priceError"></span>
			
			
			    <label>Tags</label>
			    <select name="tagIds"
			            multiple
			            size="5"
			            required
			            onchange="validateFormElem(this, document.getElementById('tagsError'))">
			        <%
			            List<Tag> tags = (List<Tag>) request.getAttribute("tags");
			            if (tags != null) {
			                for (Tag t : tags) {
			        %>
			            <option value="<%= t.getId() %>">
			                <%= t.getName() %>
			            </option>
			        <%
			                }
			            }
			        %>
			    </select>
			    <span id="tagsError"></span>
			
			
			    <label>Banner Image</label>
			    <div class="file-uploader">
			        <label class="file-label">
			            Scegli file
			            <input type="file"
			                   name="cover"
			                   accept="image/*"
			                   required
			                   onchange="validateFormElem(this, document.getElementById('coverError'))">
			        </label>
			
			        <span id="coverError" class="file-error"></span>
			    </div>
			
			
			    <input type="submit" value="Publish Game">
			
			</form>

        </div>

    </div>
</div>

</body>
</html>