package service;

import model.Match;
import model.Page;
import model.Player;
import repository.MatchRepository;
import repository.PlayerRepository;

import java.util.Optional;
import java.util.logging.Logger;

public class FinishedMatchesPersistenceService {


    private static final Logger logger = Logger.getLogger(FinishedMatchesPersistenceService.class.getName());

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public FinishedMatchesPersistenceService(MatchRepository matchRepository, PlayerRepository playerRepository) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    public void saveFinishedMatch(Match match) {
        logger.info("Saving finished match: " + match);
        try {
            matchRepository.save(match);
            logger.info("Match saved successfully: " + match);
        } catch (Exception e) {
            logger.severe("Error saving finished match: " + e.getMessage());
            throw e;
        }
    }

    public Match createMatchFromScore(MatchScore matchScore) {
        logger.info("Creating match from score: " + matchScore);
        try {
            Match match = new Match();
            match.setPlayer1(findPlayer(matchScore.getPlayer1Name()));
            match.setPlayer2(findPlayer(matchScore.getPlayer2Name()));

            String winnerName = matchScore.getMatchWinner();
            match.setWinner(findPlayer(winnerName));


            logger.info("Match created from score: " + match);
            return match;
        } catch (Exception e) {
            logger.severe("Error creating match from score: " + e.getMessage());
            throw new RuntimeException("Error creating match from score", e);
        }
    }

    private Player findPlayer(String playerName) {
        logger.info("Looking for player with name: " + playerName);
        if (playerName == null || playerName.isEmpty()) {
            logger.severe("Player name cannot be null or empty");
            throw new RuntimeException("Player name cannot be null or empty");
        }

        Optional<Player> player = playerRepository.findByName(playerName);
        if (player.isEmpty()) {
            String errorMessage = "Player not found: " + playerName;
            logger.severe(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        return player.get();
    }

    public Page<Match> getFinishedMatchesWithPaginationAndFilter(int pageNumber, int pageSize, String playerName) {
        logger.info("Retrieving matches with pagination - Page: " + pageNumber + ", Size: " + pageSize + ", Filter by Player: " + playerName);
        try {
            return matchRepository.findMatchesWithPaginationAndFilter(pageNumber, pageSize, playerName);
        } catch (Exception e) {
            logger.severe("Error retrieving matches: " + e.getMessage());
            throw e;
        }
    }


    public Long countTotalMatches(String playerName) {
        logger.info("Counting total matches with filter by Player: " + playerName);
        try {
            return matchRepository.countMatchesByPlayerName(playerName);
        } catch (Exception e) {
            logger.severe("Error counting matches: " + e.getMessage());
            throw e;
        }
    }


}
