package servlet;


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
import util.HibernateUtil;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")

public class NewMatchServlet extends HttpServlet {

    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private PlayerService playerService;

    @Override
    public void init() throws ServletException {
        super.init();
        playerService = new PlayerService(new PlayerRepositoryImpl(HibernateUtil.getSessionFactory().openSession()));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String player1Name = req.getParameter("player1");
        String player2Name = req.getParameter("player2");

        if (player1Name.equals(player2Name)) {
            resp.sendRedirect(req.getContextPath() + "/new-match?error=same_player");
            return;
        }

        Player player1 = playerService.getPlayer(player1Name);
        Player player2 = playerService.getPlayer(player2Name);

        Match match = new Match();
        match.setPlayer1(player1);
        match.setPlayer2(player2);

        UUID matchId = ongoingMatchesService.add(match);

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchId.toString());
    }
}
