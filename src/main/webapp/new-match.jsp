<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New Match</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
</head>
<body>
<div class="main-container">
    <div class="form-container">
        <h1>Create New Match</h1>
        <form action="${pageContext.request.contextPath}/new-match" method="post" class="new-match-form">
            <label for="player1">Player 1:</label>
            <input type="text" id="player1" name="player1" required>

            <label for="player2">Player 2:</label>
            <input type="text" id="player2" name="player2" required>

            <button type="submit">Start Match</button>
        </form>
    </div>
</div>
</body>
</html>