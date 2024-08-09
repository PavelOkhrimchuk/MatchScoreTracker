<%@ page import="model.Match, service.GamePlayer, service.MatchScore" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Final Score</title>
    <link rel="stylesheet" href="css/finalscore.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main-container">
    <div class="final-score-container">
        <h1>Final Score</h1>
        <div class="score-board">
            <div class="player-score">
                <h2>${matchScore.player1Name}</h2>
                <p>Sets: ${matchScore.getPlayerSets(GamePlayer.PLAYER_ONE)}</p>
            </div>
            <div class="player-score">
                <h2>${matchScore.player2Name}</h2>
                <p>Sets: ${matchScore.getPlayerSets(GamePlayer.PLAYER_TWO)}</p>
            </div>
        </div>
        <p>Winner: ${match.getWinner() != null ? match.getWinner().getName() : "Unknown"}</p>
    </div>
</div>
</body>
</html>
