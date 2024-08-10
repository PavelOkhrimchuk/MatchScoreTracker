package servlet;

import exception.InvalidMatchIdException;
import exception.MatchNotFoundException;
import exception.MatchScoreNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Match;
import repository.MatchRepository;
import repository.MatchRepositoryImpl;
import repository.PlayerRepository;
import repository.PlayerRepositoryImpl;
import service.FinishedMatchesPersistenceService;
import service.GamePlayer;
import service.MatchScore;
import service.OngoingMatchesService;
import service.matchScore.GameManagementService;
import service.matchScore.MatchScoreCalculationService;
import service.matchScore.PointCalculationService;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(MatchScoreServlet.class.getName());
    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init() throws ServletException {
        super.init();

        ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
        matchScoreCalculationService = (MatchScoreCalculationService) getServletContext().getAttribute("matchScoreCalculationService");

        if (ongoingMatchesService == null) {
            ongoingMatchesService = new OngoingMatchesService();
            getServletContext().setAttribute("ongoingMatchesService", ongoingMatchesService);
            logger.info("OngoingMatchesService initialized.");
        }

        if (matchScoreCalculationService == null) {
            matchScoreCalculationService = new MatchScoreCalculationService(
                    new PointCalculationService(new GameManagementService()),
                    new GameManagementService()
            );
            getServletContext().setAttribute("matchScoreCalculationService", matchScoreCalculationService);
            logger.info("MatchScoreCalculationService initialized.");
        }

        MatchRepository matchRepository = new MatchRepositoryImpl();
        PlayerRepository playerRepository = new PlayerRepositoryImpl();
        finishedMatchesPersistenceService = new FinishedMatchesPersistenceService(matchRepository, playerRepository);
        logger.info("FinishedMatchesPersistenceService initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");

        if (idString == null || idString.isEmpty()) {
            throw new InvalidMatchIdException("Match ID is missing!");
        }

        Integer matchId;
        try {
            matchId = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new InvalidMatchIdException("Invalid match ID format!");
        }

        Optional<Match> matchOpt = ongoingMatchesService.get(matchId);

        if (matchOpt.isEmpty()) {
            throw new MatchNotFoundException("Match with ID " + matchId + " not found!");
        }

        Match match = matchOpt.get();
        MatchScore matchScore = ongoingMatchesService.getMatchScore(matchId).orElse(null);

        if (matchScore == null) {
            throw new MatchScoreNotFoundException("Match score not found for ID: " + matchId);
        }

        req.setAttribute("matchId", matchId);
        req.setAttribute("match", match);
        req.setAttribute("matchScore", matchScore);

        req.getRequestDispatcher("/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        String playerNumberParameter = req.getParameter("player");

        if (idString == null || idString.isEmpty()) {
            throw new InvalidMatchIdException("Match ID is missing!");
        }

        Integer matchId = Integer.parseInt(idString);

        Optional<Match> matchOpt = ongoingMatchesService.get(matchId);

        if (matchOpt.isEmpty()) {
            throw new MatchNotFoundException("Match with ID " + matchId + " not found!");
        }

        Match match = matchOpt.get();
        MatchScore matchScore = ongoingMatchesService.getMatchScore(matchId).orElse(null);

        if (matchScore == null) {
            throw new MatchScoreNotFoundException("Match score not found for ID: " + matchId);
        }

        GamePlayer player = "player1".equals(playerNumberParameter) ? GamePlayer.PLAYER_ONE : GamePlayer.PLAYER_TWO;

        boolean matchFinished = matchScoreCalculationService.playerWinsPoint(player, matchScore);

        if (matchFinished) {
            Match finishedMatch = finishedMatchesPersistenceService.createMatchFromScore(matchScore);
            finishedMatchesPersistenceService.saveFinishedMatch(finishedMatch);
            ongoingMatchesService.remove(matchId);

            req.setAttribute("matchId", matchId);
            req.setAttribute("match", finishedMatch);
            req.setAttribute("matchScore", matchScore);

            req.getRequestDispatcher("/final-score.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/match-score?id=" + matchId);
        }
    }

}
