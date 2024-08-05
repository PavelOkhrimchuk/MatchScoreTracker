package service.matchScore;

import service.GamePlayer;
import service.MatchScore;

public class MatchScoreCalculationService {


    private final PointCalculationService pointCalculationService;



    public MatchScoreCalculationService(PointCalculationService pointCalculationService, GameManagementService gameManagementService) {
        this.pointCalculationService = pointCalculationService;

    }

    public boolean playerWinsPoint(GamePlayer player, MatchScore matchScore) {
        if (!matchScore.isTieBreak()) {
            pointCalculationService.calculatePlayerPointsInRegularMode(player, matchScore);
        } else {
            pointCalculationService.calculatePlayerPointsInTieBreakMode(player, matchScore);
        }


        return isMatchFinished(matchScore);
    }


    public boolean isMatchFinished(MatchScore matchScore) {
        return matchScore.isMatchFinished();
    }

}
