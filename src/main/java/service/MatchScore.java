package service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchScore {


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

    public MatchScore(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Score = 0;
        this.player2Score = 0;
        this.player1Games = 0;
        this.player2Games = 0;
        this.player1Sets = 0;
        this.player2Sets = 0;
        this.player1TieBreakPoints = 0;
        this.player2TieBreakPoints = 0;
        this.isTieBreak = false;
        this.isMatchFinished = false;
        this.player1Ads = 0;
        this.player2Ads = 0;
        this.winner = null;
        this.status = "In Progress";
    }

    public MatchScore() {

    }

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
            checkSetWinner();
        } else if (player == GamePlayer.PLAYER_TWO) {
            player2Games++;
            checkSetWinner();
        }
    }

    private void checkSetWinner() {
        if (player1Games >= 6 && (player1Games - player2Games) >= 2) {
            player1Sets++;
            clearGames();
        } else if (player2Games >= 6 && (player2Games - player1Games) >= 2) {
            player2Sets++;
            clearGames();
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
        checkTieBreakWinner();
    }

    private void checkTieBreakWinner() {

        if (player1TieBreakPoints >= 7 && (player1TieBreakPoints - player2TieBreakPoints) >= 2) {

            System.out.println("Player 1 wins the tie-break with " + player1TieBreakPoints + " points.");
            player1Sets++;
            clearTieBreakPoints();
        } else if (player2TieBreakPoints >= 7 && (player2TieBreakPoints - player1TieBreakPoints) >= 2) {

            System.out.println("Player 2 wins the tie-break with " + player2TieBreakPoints + " points.");
            player2Sets++;
            clearTieBreakPoints();
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
      setTieBreak(false);
    }

    public void clearGames() {
        player1Games = 0;
        player2Games = 0;
    }

    public void clearAds() {
        player1Ads = 0;
        player2Ads = 0;
    }

    public String getMatchScore() {
        return String.format("Player 1: %d sets, %d games, %d points\nPlayer 2: %d sets, %d games, %d points",
                player1Sets, player1Games, player1Score,
                player2Sets, player2Games, player2Score);
    }

    public boolean isMatchFinished() {
        int setsToWin = 2;
        return player1Sets >= setsToWin || player2Sets >= setsToWin;
    }

    public String getMatchWinner() {
        if (winner == null) {
            return "No winner yet";
        }
        return winner == GamePlayer.PLAYER_ONE ? player1Name : player2Name;
    }





}
