package service.matchScore;

import service.GamePlayer;
import service.MatchScore;

public class GameManagementService {

    private static final int SETS_TO_WIN = 2;

    public void playerWinsGame(GamePlayer player, MatchScore matchScore) {
        GamePlayer otherPlayer = getOpponent(player);

        if (playerGamesLessThan5(player, matchScore)) {
            matchScore.increasePlayerGames(player);
        } else if (playerGamesEqualsTo5(player, matchScore)) {
            if (playerGamesLessThan5(otherPlayer, matchScore)) {
                playerWinsSet(player, matchScore);
                matchScore.clearGames();
            } else if (playerGamesEqualsTo6(otherPlayer, matchScore)) {
                matchScore.increasePlayerGames(player);
                setTieBreakMode(matchScore);
            } else {
                matchScore.increasePlayerGames(player);
            }
        } else if (playerGamesEqualsTo6(player, matchScore) && playerGamesEqualsTo5(otherPlayer, matchScore)) {
            playerWinsSet(player, matchScore);
            matchScore.clearGames();
        }
    }

    public void playerWinsSet(GamePlayer player, MatchScore matchScore) {
        matchScore.increasePlayerSets(player);
        matchScore.clearGames();
        if (playerWinsMatch(player, matchScore)) {
            setWinner(player, matchScore);
            setMatchFinished(matchScore);
        }
    }

    public boolean playerWinsMatch(GamePlayer player, MatchScore matchScore) {
        return matchScore.getPlayerSets(player) == SETS_TO_WIN;
    }

    public void setTieBreakMode(MatchScore matchScore) {
        matchScore.setTieBreak(true);
    }

    public void setRegularMode(MatchScore matchScore) {
        matchScore.setTieBreak(false);
    }

    private GamePlayer getOpponent(GamePlayer player) {
        return (player == GamePlayer.PLAYER_ONE) ? GamePlayer.PLAYER_TWO : GamePlayer.PLAYER_ONE;
    }

    private boolean playerGamesLessThan5(GamePlayer player, MatchScore matchScore) {
        return getPlayerGames(player, matchScore) < 5;
    }

    private boolean playerGamesEqualsTo5(GamePlayer player, MatchScore matchScore) {
        return getPlayerGames(player, matchScore) == 5;
    }

    private boolean playerGamesEqualsTo6(GamePlayer player, MatchScore matchScore) {
        return getPlayerGames(player, matchScore) == 6;
    }

    private void setWinner(GamePlayer player, MatchScore matchScore) {
        matchScore.setWinner(player);
    }

    private void setMatchFinished(MatchScore matchScore) {
        matchScore.setMatchFinished(true);
    }

    private int getPlayerGames(GamePlayer player, MatchScore matchScore) {
        return player == GamePlayer.PLAYER_ONE ? matchScore.getPlayer1Games() : matchScore.getPlayer2Games();
    }
}
