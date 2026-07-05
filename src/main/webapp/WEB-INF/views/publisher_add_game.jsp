<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Models.Tag" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Publisher - Add Game</title>
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

            <form method="post" class="form-card" action="<%= request.getContextPath() %>/Publisher/Dashboard?section=add_game" enctype="multipart/form-data">

                <label>Game Title</label>
                <input type="text" name="title" required>

                <label>Description</label>
                <textarea name="description" required></textarea>

                <label>Price</label>
                <input type="number" step="0.01" name="price" required>

                <label>Tags</label>
                <select name="tagIds" multiple size="5">
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

                <label>Banner Image</label>
                <input type="file" name="cover">

                <input type="submit" value="Publish Game">
            </form>

        </div>

    </div>
</div>

</body>
</html>