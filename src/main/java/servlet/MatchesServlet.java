package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Match;
import model.Page;
import repository.MatchRepositoryImpl;
import repository.PlayerRepositoryImpl;
import service.FinishedMatchesPersistenceService;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(MatchesServlet.class.getName());
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init() throws ServletException {
        super.init();


        this.finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) getServletContext().getAttribute("finishedMatchesPersistenceService");

        if (finishedMatchesPersistenceService == null) {

            this.finishedMatchesPersistenceService = new FinishedMatchesPersistenceService(
                    new MatchRepositoryImpl(),
                    new PlayerRepositoryImpl()
            );
            getServletContext().setAttribute("finishedMatchesPersistenceService", finishedMatchesPersistenceService);
            logger.info("FinishedMatchesPersistenceService initialized.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("filter_by_player_name");
        String pageParam = req.getParameter("page");

        int pageNumber = pageParam != null ? Integer.parseInt(pageParam) : 1;
        int pageSize = 10;

        Page<Match> matchPage = finishedMatchesPersistenceService.getFinishedMatchesWithPaginationAndFilter(pageNumber, pageSize, playerName);
        Long totalMatches = finishedMatchesPersistenceService.countTotalMatches(playerName);

        req.setAttribute("matchPage", matchPage);
        req.setAttribute("totalMatches", totalMatches);
        req.setAttribute("playerName", playerName);

        req.getRequestDispatcher("/matches.jsp").forward(req, resp);
    }
}
