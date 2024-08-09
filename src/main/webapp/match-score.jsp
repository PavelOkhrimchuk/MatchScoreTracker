<%@ page import="model.Match, model.Player, service.MatchScore, service.GamePlayer" %>
<%@ page import="java.util.Optional" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Match Score</title>
    <link rel="stylesheet" href="css/matchscore.css">
</head>
<body>
<div class="main-container">
    <div class="score-container">
        <h1>Match Score</h1>
        <div class="score-board">
            <div class="player-score">
                <h2 id="player1-name">${matchScore.player1Name}</h2>
                <div class="score-details">
                    <p>Points: <span id="player1-points">${matchScore.getPlayerPoints(GamePlayer.PLAYER_ONE)}</span></p>
                    <p>Games: <span id="player1-games">${matchScore.getPlayerGames(GamePlayer.PLAYER_ONE)}</span></p>
                    <p>Sets: <span id="player1-sets">${matchScore.getPlayerSets(GamePlayer.PLAYER_ONE)}</span></p>
                </div>
            </div>
            <div class="player-score">
                <h2 id="player2-name">${matchScore.player2Name}</h2>
                <div class="score-details">
                    <p>Points: <span id="player2-points">${matchScore.getPlayerPoints(GamePlayer.PLAYER_TWO)}</span></p>
                    <p>Games: <span id="player2-games">${matchScore.getPlayerGames(GamePlayer.PLAYER_TWO)}</span></p>
                    <p>Sets: <span id="player2-sets">${matchScore.getPlayerSets(GamePlayer.PLAYER_TWO)}</span></p>
                </div>
            </div>
        </div>

        <!-- Форма для обновления счета (если матч не завершён) -->
        <c:if test="${!matchScore.isMatchFinished()}">
            <form action="match-score?id=${matchId}" method="post" class="score-update-form">
                <button type="submit" name="player" value="player1">Player 1 Won Point</button>
                <button type="submit" name="player" value="player2">Player 2 Won Point</button>
            </form>
        </c:if>
    </div>
</div>
</body>
</html>
