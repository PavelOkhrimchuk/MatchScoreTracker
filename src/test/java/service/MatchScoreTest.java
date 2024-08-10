package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.matchScore.GameManagementService;
import service.matchScore.MatchScoreCalculationService;
import service.matchScore.PointCalculationService;

import static org.junit.jupiter.api.Assertions.*;

class MatchScoreTest {

    private GameManagementService gameManagementService;
    private PointCalculationService pointCalculationService;
    private MatchScore matchScore;
    private MatchScoreCalculationService matchScoreCalculationService;

    @BeforeEach
    void setUp() {
        gameManagementService = new GameManagementService();
        pointCalculationService = new PointCalculationService(gameManagementService);
        matchScore = new MatchScore();
        matchScoreCalculationService = new MatchScoreCalculationService(pointCalculationService);
    }


    @Test
    void testInitialValues() {
        assertEquals(0, matchScore.getPlayer1Games());
        assertEquals(0, matchScore.getPlayer2Games());
        assertEquals(0, matchScore.getPlayer1Sets());
        assertEquals(0, matchScore.getPlayer2Sets());
        assertEquals(0, matchScore.getPlayer1TieBreakPoints());
        assertEquals(0, matchScore.getPlayer2TieBreakPoints());
        assertFalse(matchScore.isTieBreak());
        assertFalse(matchScore.isMatchFinished());
    }

    @Test
    void testIncreasePlayerPointsInRegularMode() {
        pointCalculationService.calculatePlayerPointsInRegularMode(GamePlayer.PLAYER_ONE, matchScore);
        assertEquals(15, matchScore.getPlayerPoints(GamePlayer.PLAYER_ONE));
        pointCalculationService.calculatePlayerPointsInRegularMode(GamePlayer.PLAYER_ONE, matchScore);
        assertEquals(30, matchScore.getPlayerPoints(GamePlayer.PLAYER_ONE));
        pointCalculationService.calculatePlayerPointsInRegularMode(GamePlayer.PLAYER_ONE, matchScore);
        assertEquals(40, matchScore.getPlayerPoints(GamePlayer.PLAYER_ONE));
    }

    @Test
    void testWinGameFrom40() {
        matchScore.increasePlayerPoints(GamePlayer.PLAYER_ONE, 40);
        pointCalculationService.calculatePlayerPointsInRegularMode(GamePlayer.PLAYER_ONE, matchScore);
        assertEquals(0, matchScore.getPlayerPoints(GamePlayer.PLAYER_ONE));
        assertEquals(1, matchScore.getPlayer1Games());
    }

    @Test
    void testDeuceScenario() {
        matchScore.increasePlayerPoints(GamePlayer.PLAYER_ONE, 40);
        matchScore.increasePlayerPoints(GamePlayer.PLAYER_TWO, 40);

        pointCalculationService.calculatePlayerPointsInRegularMode(GamePlayer.PLAYER_ONE, matchScore);
        assertEquals(1, matchScore.getPlayerAds(GamePlayer.PLAYER_ONE));

        pointCalculationService.calculatePlayerPointsInRegularMode(GamePlayer.PLAYER_TWO, matchScore);
        assertEquals(0, matchScore.getPlayerAds(GamePlayer.PLAYER_ONE));
        assertEquals(0, matchScore.getPlayerAds(GamePlayer.PLAYER_TWO));
    }

    @Test
    void testWinGameWithAdvantage() {
        matchScore.increasePlayerPoints(GamePlayer.PLAYER_ONE, 40);
        matchScore.increasePlayerPoints(GamePlayer.PLAYER_TWO, 40);

        pointCalculationService.calculatePlayerPointsInRegularMode(GamePlayer.PLAYER_ONE, matchScore);
        pointCalculationService.calculatePlayerPointsInRegularMode(GamePlayer.PLAYER_ONE, matchScore);

        assertEquals(1, matchScore.getPlayer1Games());
        assertEquals(0, matchScore.getPlayerPoints(GamePlayer.PLAYER_ONE));
        assertEquals(0, matchScore.getPlayerAds(GamePlayer.PLAYER_ONE));
    }

    @Test
    void testWinMatchWithoutTieBreak() {

        for (int i = 0; i < 6; i++) {
            gameManagementService.playerWinsGame(GamePlayer.PLAYER_ONE, matchScore);
        }

        assertEquals(1, matchScore.getPlayer1Sets());

        for (int i = 0; i < 6; i++) {
            gameManagementService.playerWinsGame(GamePlayer.PLAYER_TWO, matchScore);
        }

        assertEquals(1, matchScore.getPlayer2Sets());


        for (int i = 0; i < 6; i++) {
            gameManagementService.playerWinsGame(GamePlayer.PLAYER_ONE, matchScore);
        }

        assertEquals(2, matchScore.getPlayer1Sets());
        assertTrue(matchScoreCalculationService.isMatchFinished(matchScore));
        assertEquals(GamePlayer.PLAYER_ONE, matchScore.getWinner());
    }

    @Test
    void testTieBreakActivationAt6_6() {

        for (int i = 0; i < 5; i++) {
            gameManagementService.playerWinsGame(GamePlayer.PLAYER_ONE, matchScore);
        }
        for (int i = 0; i < 5; i++) {
            gameManagementService.playerWinsGame(GamePlayer.PLAYER_TWO, matchScore);
        }

        assertEquals(5, matchScore.getPlayer1Games());
        assertEquals(5, matchScore.getPlayer2Games());
        assertFalse(matchScore.isTieBreak());

        gameManagementService.playerWinsGame(GamePlayer.PLAYER_TWO, matchScore);
        gameManagementService.playerWinsGame(GamePlayer.PLAYER_ONE, matchScore);


        assertEquals(6, matchScore.getPlayer1Games());
        assertEquals(6, matchScore.getPlayer2Games());
        assertEquals(0, matchScore.getPlayer1Sets());
        assertEquals(0, matchScore.getPlayer2Sets());
        assertTrue(matchScore.isTieBreak());

        for (int i = 0; i < 7; i++) {
            pointCalculationService.calculatePlayerPointsInTieBreakMode(GamePlayer.PLAYER_ONE, matchScore);
        }

        assertFalse(matchScore.isTieBreak());


        assertEquals(1, matchScore.getPlayer1Sets());
        assertEquals(0, matchScore.getPlayer2Sets());


    }



}