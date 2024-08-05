package service;

import model.Match;
import model.Player;
import repository.MatchRepository;
import repository.PlayerRepository;

import java.util.Optional;
import java.util.UUID;

public class FinishedMatchesPersistenceService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public FinishedMatchesPersistenceService(MatchRepository matchRepository, PlayerRepository playerRepository) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    public void saveFinishedMatch(Match match) {
        matchRepository.save(match);
    }


    public Match getFinishedMatch(Integer matchId) {
        Optional<Match> matchOptional = matchRepository.findById(matchId);
        return matchOptional.orElse(null);
    }


    public Match createMatchFromScore(MatchScore matchScore) {
        Match match = new Match();
        match.setPlayer1(findPlayer(matchScore.getPlayer1Name()));
        match.setPlayer2(findPlayer(matchScore.getPlayer2Name()));
        match.setWinner(findPlayer(matchScore.getWinner() != null ? matchScore.getWinner().name() : null));
        return match;
    }


    private Player findPlayer(String playerName) {
        return playerRepository.findByName(playerName).orElseThrow(() ->
                new RuntimeException("Player not found: " + playerName));
    }
}
