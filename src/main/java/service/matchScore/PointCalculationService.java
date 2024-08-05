package service.matchScore;

import service.GamePlayer;
import service.MatchScore;

public class PointCalculationService {

    private final GameManagementService gameManagementService;

    public PointCalculationService(GameManagementService gameManagementService) {
        this.gameManagementService = gameManagementService;
    }

    public void calculatePlayerPointsInRegularMode(GamePlayer player, MatchScore matchScore) {
        GamePlayer otherPlayer = getOpponent(player);

        if (playerPointsLessThan30(player, matchScore)) {
            matchScore.increasePlayerPoints(player, 15);
        } else if (playerPointsLessThan40(player, matchScore)) {
            matchScore.increasePlayerPoints(player, 10);
        } else if (playerPointsEqualsTo40(player, matchScore)) {
            if (playerPointsLessThan40(otherPlayer, matchScore)) {
                gameManagementService.playerWinsGame(player, matchScore);
                matchScore.clearPoints();
            } else if (playerPointsEqualsTo40(otherPlayer, matchScore)) {
                matchScore.increasePlayerAds(player);
                if (playerWinsPointWithAdvantage(player, matchScore)) {
                    gameManagementService.playerWinsGame(player, matchScore);
                    matchScore.clearPoints();
                    matchScore.clearAds();
                } else if (playerLostPointWithAdvantage(player, matchScore)) {
                    matchScore.clearAds();
                }
            }
        }
    }

    public void calculatePlayerPointsInTieBreakMode(GamePlayer player, MatchScore matchScore) {
        GamePlayer otherPlayer = getOpponent(player);

        if (playerTieBreakPointsLessThan6(player, matchScore)) {
            matchScore.increasePlayerTieBreakPoints(player);
        } else if (playerTieBreakPointsEqualsTo6(player, matchScore)) {
            if (playerTieBreakPointsLessThan6(otherPlayer, matchScore)) {
                gameManagementService.playerWinsSet(player, matchScore);
                matchScore.clearTieBreakPoints();
                gameManagementService.setRegularMode(matchScore);
            } else {
                matchScore.increasePlayerTieBreakPoints(player);
            }
        } else {
            matchScore.increasePlayerTieBreakPoints(player);
            if (playerWinsTieBreakPointWithAdvantage(player, matchScore)) {
                gameManagementService.playerWinsSet(player, matchScore);
                matchScore.clearTieBreakPoints();
                gameManagementService.setRegularMode(matchScore);
            }
        }
    }

    private GamePlayer getOpponent(GamePlayer player) {
        return (player == GamePlayer.PLAYER_ONE) ? GamePlayer.PLAYER_TWO : GamePlayer.PLAYER_ONE;
    }

    // Прочие вспомогательные методы для проверки условий
    private boolean playerPointsLessThan30(GamePlayer player, MatchScore matchScore) {
        return matchScore.getPlayerPoints(player) < 30;
    }

    private boolean playerPointsLessThan40(GamePlayer player, MatchScore matchScore) {
        return matchScore.getPlayerPoints(player) < 40;
    }

    private boolean playerPointsEqualsTo40(GamePlayer player, MatchScore matchScore) {
        return matchScore.getPlayerPoints(player) == 40;
    }

    private boolean playerTieBreakPointsLessThan6(GamePlayer player, MatchScore matchScore) {
        return matchScore.getPlayerTieBreakPoints(player) < 6;
    }

    private boolean playerTieBreakPointsEqualsTo6(GamePlayer player, MatchScore matchScore) {
        return matchScore.getPlayerTieBreakPoints(player) == 6;
    }

    private boolean playerWinsTieBreakPointWithAdvantage(GamePlayer player, MatchScore matchScore) {
        GamePlayer otherPlayer = getOpponent(player);
        return matchScore.getPlayerTieBreakPoints(player) - matchScore.getPlayerTieBreakPoints(otherPlayer) == 2;
    }

    private boolean playerWinsPointWithAdvantage(GamePlayer player, MatchScore matchScore) {
        GamePlayer otherPlayer = getOpponent(player);
        return matchScore.getPlayerAds(player) - matchScore.getPlayerAds(otherPlayer) == 2;
    }

    private boolean playerLostPointWithAdvantage(GamePlayer player, MatchScore matchScore) {
        GamePlayer otherPlayer = getOpponent(player);
        return matchScore.getPlayerAds(player) == 1 && matchScore.getPlayerAds(otherPlayer) == 1;
    }



}
