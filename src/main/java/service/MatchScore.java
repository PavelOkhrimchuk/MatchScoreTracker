package service;

import lombok.Getter;
import lombok.Setter;
import service.GamePlayer;

import java.util.UUID;

@Getter
@Setter
public class MatchScore {

    private UUID matchId;
    private String player1Name;
    private String player2Name;

    private int player1Score;
    private int player2Score;

    private int player1Games;
    private int player2Games;

    private int player1Sets;
    private int player2Sets;

    private int player1TieBreakPoints;
    private int player2TieBreakPoints;

    private boolean isTieBreak;
    private boolean isMatchFinished;

    private int player1Ads;
    private int player2Ads;

    private GamePlayer winner;
    private String status;

    public void increasePlayerPoints(GamePlayer player, int points) {
        if (player == GamePlayer.PLAYER_ONE) {
            player1Score += points;
        } else if (player == GamePlayer.PLAYER_TWO) {
            player2Score += points;
        }
    }


    public void increasePlayerGames(GamePlayer player) {
        if (player == GamePlayer.PLAYER_ONE) {
            player1Games++;
        } else if (player == GamePlayer.PLAYER_TWO) {
            player2Games++;
        }
    }

    public void increasePlayerSets(GamePlayer player) {
        if (player == GamePlayer.PLAYER_ONE) {
            player1Sets++;
        } else if (player == GamePlayer.PLAYER_TWO) {
            player2Sets++;
        }
    }

    public void increasePlayerAds(GamePlayer player) {
        if (player == GamePlayer.PLAYER_ONE) {
            player1Ads++;
        } else if (player == GamePlayer.PLAYER_TWO) {
            player2Ads++;
        }
    }

    public void increasePlayerTieBreakPoints(GamePlayer player) {
        if (player == GamePlayer.PLAYER_ONE) {
            player1TieBreakPoints++;
        } else if (player == GamePlayer.PLAYER_TWO) {
            player2TieBreakPoints++;
        }
    }

    public int getPlayerPoints(GamePlayer player) {
        if (player == GamePlayer.PLAYER_ONE) {
            return player1Score;
        } else if (player == GamePlayer.PLAYER_TWO) {
            return player2Score;
        }
        return 0;
    }

    public int getPlayerGames(GamePlayer player) {
        if (player == GamePlayer.PLAYER_ONE) {
            return player1Games;
        } else if (player == GamePlayer.PLAYER_TWO) {
            return player2Games;
        }
        return 0;
    }

    public int getPlayerSets(GamePlayer player) {
        if (player == GamePlayer.PLAYER_ONE) {
            return player1Sets;
        } else if (player == GamePlayer.PLAYER_TWO) {
            return player2Sets;
        }
        return 0;
    }

    public int getPlayerTieBreakPoints(GamePlayer player) {
        if (player == GamePlayer.PLAYER_ONE) {
            return player1TieBreakPoints;
        } else if (player == GamePlayer.PLAYER_TWO) {
            return player2TieBreakPoints;
        }
        return 0;
    }

    public int getPlayerAds(GamePlayer player) {
        if (player == GamePlayer.PLAYER_ONE) {
            return player1Ads;
        } else if (player == GamePlayer.PLAYER_TWO) {
            return player2Ads;
        }
        return 0;
    }

    public void clearPoints() {
        player1Score = 0;
        player2Score = 0;
    }

    public void clearTieBreakPoints() {
      player1TieBreakPoints = 0;
      player2TieBreakPoints = 0;
    }

    public void clearGames() {
        player1Games = 0;
        player2Games = 0;
    }

    public void clearAds() {
        player1Ads = 0;
        player2Ads = 0;
    }





}
