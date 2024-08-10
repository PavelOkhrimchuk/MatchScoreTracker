<%@ page import="model.Match, model.Player, service.MatchScore, service.GamePlayer" %>
<%@ page import="java.util.Optional" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Match Score</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/match_score.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main-container">
    <div class="score-container">
        <h1>Match Score</h1>


        <div class="score-board">
            <table class="score-table">
                <thead>
                <tr>
                    <th>Player</th>
                    <th>Points</th>
                    <th>Games</th>
                    <th>Sets</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${matchScore.player1Name}</td>
                    <td class="points">
                        <div>${matchScore.getPlayerPoints(GamePlayer.PLAYER_ONE)}</div>
                        <div class="ads">${matchScore.getPlayerAds(GamePlayer.PLAYER_ONE)}</div>
                    </td>
                    <td class="games">
                        <div>${matchScore.getPlayerGames(GamePlayer.PLAYER_ONE)}</div>
                        <div class="tiebreak">${matchScore.getPlayerTieBreakPoints(GamePlayer.PLAYER_ONE)}</div>
                    </td>
                    <td class="sets">${matchScore.getPlayerSets(GamePlayer.PLAYER_ONE)}</td>
                </tr>
                <tr>
                    <td>${matchScore.player2Name}</td>
                    <td class="points">
                        <div>${matchScore.getPlayerPoints(GamePlayer.PLAYER_TWO)}</div>
                        <div class="ads">${matchScore.getPlayerAds(GamePlayer.PLAYER_TWO)}</div>
                    </td>
                    <td class="games">
                        <div>${matchScore.getPlayerGames(GamePlayer.PLAYER_TWO)}</div>
                        <div class="tiebreak">${matchScore.getPlayerTieBreakPoints(GamePlayer.PLAYER_TWO)}</div>
                    </td>
                    <td class="sets">${matchScore.getPlayerSets(GamePlayer.PLAYER_TWO)}</td>
                </tr>
                </tbody>
            </table>
        </div>


        <c:if test="${!matchScore.isMatchFinished()}">
            <form action="${pageContext.request.contextPath}/match-score?id=${matchId}" method="post" class="score-update-form">
                <button type="submit" name="player" value="player1">Player 1 Won Point</button>
                <button type="submit" name="player" value="player2">Player 2 Won Point</button>
            </form>
        </c:if>
    </div>
</div>
</body>
</html>
