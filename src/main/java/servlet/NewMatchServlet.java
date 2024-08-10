package servlet;


import exception.DuplicatePlayerException;
import exception.PlayerNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Match;
import model.Player;
import repository.PlayerRepositoryImpl;
import service.OngoingMatchesService;
import service.PlayerService;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/new-match")

public class NewMatchServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(NewMatchServlet.class.getName());
    private OngoingMatchesService ongoingMatchesService;
    private PlayerService playerService;

    @Override
    public void init() throws ServletException {
        super.init();

        ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
        if (ongoingMatchesService == null) {
            ongoingMatchesService = new OngoingMatchesService();
            getServletContext().setAttribute("ongoingMatchesService", ongoingMatchesService);
            logger.info("OngoingMatchesService initialized.");
        }

        playerService = new PlayerService(new PlayerRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String player1Name = req.getParameter("player1");
        String player2Name = req.getParameter("player2");

        try {
            playerService.validatePlayerName(player1Name);
            playerService.validatePlayerName(player2Name);



            if (player1Name.equals(player2Name)) {
                throw new DuplicatePlayerException("Players must have different names!");
            }

            Player player1 = playerService.getPlayer(player1Name);
            Player player2 = playerService.getPlayer(player2Name);

            if (player1 == null || player2 == null) {
                throw new PlayerNotFoundException("One or both players not found!");
            }

            Match match = new Match();
            match.setPlayer1(player1);
            match.setPlayer2(player2);

            Integer matchId = ongoingMatchesService.add(match);
            if (matchId == null) {
                throw new ServletException("Failed to create match.");
            }

            logger.info("New match created with ID: " + matchId);
            resp.sendRedirect(req.getContextPath() + "/match-score?id=" + matchId);

        } catch (Exception e) {
            logger.severe("Error in NewMatchServlet: " + e.getMessage());
            req.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            req.getRequestDispatcher("/error-page.jsp").forward(req, resp);
        }
    }


}
