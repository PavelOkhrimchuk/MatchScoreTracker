package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import util.HibernateUtil;

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
        finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) getServletContext().getAttribute("finishedMatchesPersistenceService");

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

        if (finishedMatchesPersistenceService == null) {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();

            MatchRepository matchRepository = new MatchRepositoryImpl(session);
            PlayerRepository playerRepository = new PlayerRepositoryImpl(session);
            finishedMatchesPersistenceService = new FinishedMatchesPersistenceService(matchRepository, playerRepository);

            getServletContext().setAttribute("finishedMatchesPersistenceService", finishedMatchesPersistenceService);
            session.close();
            logger.info("FinishedMatchesPersistenceService initialized.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");

        if (idString == null || idString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Match ID is missing!");
            logger.warning("Match ID is missing in GET request.");
            return;
        }

        Integer matchId;
        try {
            matchId = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid match ID format!");
            logger.warning("Invalid match ID format: " + idString);
            return;
        }

        logger.info("Received match ID: " + matchId);

        Optional<Match> matchOpt = ongoingMatchesService.get(matchId);

        if (matchOpt.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match with ID " + matchId + " not found!");
            logger.warning("Match with ID " + matchId + " not found.");
            return;
        }

        Match match = matchOpt.get();
        MatchScore matchScore = ongoingMatchesService.getMatchScore(matchId).orElse(null);

        if (matchScore == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match score not found!");
            logger.warning("Match score not found for ID: " + matchId);
            return;
        }

        req.setAttribute("matchId", matchId); // Передача matchId в JSP
        req.setAttribute("match", match);
        req.setAttribute("matchScore", matchScore);
        req.getRequestDispatcher("/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        String playerNumberParameter = req.getParameter("player");

        // Логирование получения параметров
        logger.info("Received POST request with parameters - id: " + idString + ", player: " + playerNumberParameter);

        if (idString == null || idString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Match ID is missing!");
            logger.warning("Match ID is missing in POST request.");
            return;
        }

        Integer matchId = Integer.parseInt(idString);

        logger.info("Parsed match ID: " + matchId);

        Optional<Match> matchOpt = ongoingMatchesService.get(matchId);

        if (matchOpt.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match with ID " + matchId + " not found!");
            logger.warning("Match with ID " + matchId + " not found.");
            return;
        }

        Match match = matchOpt.get();
        logger.info("Match found: " + match);

        MatchScore matchScore = ongoingMatchesService.getMatchScore(matchId).orElse(null);

        if (matchScore == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match score not found!");
            logger.warning("Match score not found for ID: " + matchId);
            return;
        }

        logger.info("Match score found: " + matchScore);

        GamePlayer player = "player1".equals(playerNumberParameter) ? GamePlayer.PLAYER_ONE : GamePlayer.PLAYER_TWO;
        logger.info("Player selected: " + player);

        boolean matchFinished = matchScoreCalculationService.playerWinsPoint(player, matchScore);
        logger.info("Match finished: " + matchFinished);

        if (matchFinished) {
            Match finishedMatch = finishedMatchesPersistenceService.createMatchFromScore(matchScore);
            finishedMatchesPersistenceService.saveFinishedMatch(finishedMatch);
            ongoingMatchesService.remove(matchId);

            logger.info("Match finished, saving and removing. Winner: " + match.getWinner());

            req.setAttribute("matchId", matchId); // Передача matchId в JSP
            req.setAttribute("match", match);
            req.getRequestDispatcher("/final-score.jsp").forward(req, resp);
        } else {
            logger.info("Redirecting to match-score with ID: " + matchId);
            resp.sendRedirect(req.getContextPath() + "/match-score?id=" + matchId);
        }
    }


}
