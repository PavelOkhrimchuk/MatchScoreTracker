<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/welcome.css">
</head>
<body>
<div class="container">
    <h1>Welcome to MatchScoreTracker</h1>
    <div class="menu">
        <a href="${pageContext.request.contextPath}/matches" class="menu-item">View Match History</a>
        <a href="${pageContext.request.contextPath}/new-match" class="menu-item">Create New Match</a>
    </div>
</div>
</body>
</html>
