package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.matchScore.GameManagementService;
import service.matchScore.PointCalculationService;

import static org.junit.jupiter.api.Assertions.*;

class MatchScoreTest {

    private GameManagementService gameManagementService;
    private PointCalculationService pointCalculationService;
    private MatchScore matchScore;

    @BeforeEach
    void setUp() {
        gameManagementService = new GameManagementService();
        pointCalculationService = new PointCalculationService(gameManagementService);
        matchScore = new MatchScore();
    }

    @Test
    void testPlayerWinsGameWhenScoreIs40_40() {
        matchScore.increasePlayerPoints(GamePlayer.PLAYER_ONE, 40);
        matchScore.increasePlayerPoints(GamePlayer.PLAYER_TWO, 40);

        pointCalculationService.calculatePlayerPointsInRegularMode(GamePlayer.PLAYER_ONE, matchScore);
        assertEquals(1, matchScore.getPlayer1Ads());
        assertEquals(0, matchScore.getPlayer2Ads());

        pointCalculationService.calculatePlayerPointsInRegularMode(GamePlayer.PLAYER_ONE, matchScore);
        assertEquals(1, matchScore.getPlayer1Games());
        assertEquals(0, matchScore.getPlayer2Games());

        assertEquals(0, matchScore.getPlayer1Ads());
        assertEquals(0, matchScore.getPlayer2Ads());
    }

    @Test
    void testPlayerWinsGameWhenScoreIs40_0() {
        matchScore.increasePlayerPoints(GamePlayer.PLAYER_ONE, 40);
        matchScore.increasePlayerPoints(GamePlayer.PLAYER_TWO, 0);

        pointCalculationService.calculatePlayerPointsInRegularMode(GamePlayer.PLAYER_ONE, matchScore);
        assertEquals(1, matchScore.getPlayer1Games());
        assertEquals(0, matchScore.getPlayer2Games());

    }



    @Test
    void testPlayerWinsSet() {
        matchScore.setPlayer1Games(5);
        matchScore.setPlayer2Games(4);

        gameManagementService.playerWinsGame(GamePlayer.PLAYER_ONE, matchScore);
        assertEquals(1, matchScore.getPlayer1Sets());
        assertEquals(0, matchScore.getPlayer2Sets());
        assertEquals(0, matchScore.getPlayer1Games());
        assertEquals(0, matchScore.getPlayer2Games());
    }

    @Test
    void testPlayerWinsMatch() {
        matchScore.setPlayer1Sets(1);
        matchScore.setPlayer2Sets(0);
        matchScore.setPlayer1Games(5);
        matchScore.setPlayer2Games(4);

        gameManagementService.playerWinsGame(GamePlayer.PLAYER_ONE, matchScore);
        assertEquals(2, matchScore.getPlayer1Sets());
        assertEquals(true, matchScore.isMatchFinished());
        assertEquals(GamePlayer.PLAYER_ONE, matchScore.getWinner());
    }

    @Test
    void testTieBreakPointWin() {
        matchScore.setTieBreak(true);
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_ONE);
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_ONE);
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_ONE);
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_ONE);
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_ONE);
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_ONE); // 6 очков у игрока 1
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_TWO);
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_TWO);
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_TWO);
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_TWO);
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_TWO);
        matchScore.increasePlayerTieBreakPoints(GamePlayer.PLAYER_TWO); // 6 очков у игрока 2



        assertEquals(6, matchScore.getPlayer1TieBreakPoints());
        assertEquals(6, matchScore.getPlayer2TieBreakPoints());


        pointCalculationService.calculatePlayerPointsInTieBreakMode(GamePlayer.PLAYER_ONE, matchScore);
        assertEquals(7, matchScore.getPlayer1TieBreakPoints());
        assertEquals(6, matchScore.getPlayer2TieBreakPoints());


        pointCalculationService.calculatePlayerPointsInTieBreakMode(GamePlayer.PLAYER_ONE, matchScore);



        assertEquals(1, matchScore.getPlayer1Sets());
        assertEquals(0, matchScore.getPlayer2Sets());


        assertEquals(false, matchScore.isTieBreak());
        assertEquals(0, matchScore.getPlayer1TieBreakPoints());
        assertEquals(0, matchScore.getPlayer2TieBreakPoints());
    }
}