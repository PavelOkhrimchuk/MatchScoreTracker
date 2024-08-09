<%@ page import="model.Match" %>
<%@ page import="model.Page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Finished Matches</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/matches_list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main-container">
    <div class="filter-container">
        <form action="${pageContext.request.contextPath}/matches" method="get" class="filter-form">
            <label for="playerName">Filter by Player Name:</label>
            <input type="text" id="playerName" name="filter_by_player_name" value="${playerName}">
            <button type="submit">Search</button>
        </form>
    </div>

    <div class="matches-list">
        <h1>Finished Matches</h1>
        <table>
            <thead>
            <tr>
                <th>Match ID</th>
                <th>Player 1</th>
                <th>Player 2</th>
                <th>Winner</th>
            </tr>
            </thead>
            <tbody>

            <% if (request.getAttribute("matchPage") != null) {
                Page<Match> matchPage = (Page<Match>) request.getAttribute("matchPage");
                if (matchPage != null && matchPage.getContent() != null && !matchPage.getContent().isEmpty()) {
            %>
            <% for (Match match : matchPage.getContent()) { %>
            <tr>
                <td><%= match.getId() %></td>
                <td><%= match.getPlayer1().getName() %></td>
                <td><%= match.getPlayer2().getName() %></td>
                <td><%= match.getWinner() != null ? match.getWinner().getName() : "Unknown" %></td>
            </tr>
            <% } %>
            <% } else { %>
            <tr>
                <td colspan="4">No matches found.</td>
            </tr>
            <% } %>
            <% } %>
            </tbody>
        </table>
    </div>

    <div class="pagination">

        <%
            int pageSize = 8;
            int pageNumber = (request.getParameter("page") != null) ? Integer.parseInt(request.getParameter("page")) : 1;
            Long totalMatches = (Long) request.getAttribute("totalMatches");
            int totalPages = (int) Math.ceil((double) totalMatches / pageSize);

            if (totalPages > 1) {
                for (int i = 1; i <= totalPages; i++) {
                    String cssClass = (i == pageNumber) ? "active" : "";
        %>
        <a href="${pageContext.request.contextPath}/matches?page=<%= i %>&filter_by_player_name=<%= request.getParameter("filter_by_player_name") %>" class="<%= cssClass %>"><%= i %></a>
        <%
                }
            }
        %>
    </div>
</div>
</body>
</html>
