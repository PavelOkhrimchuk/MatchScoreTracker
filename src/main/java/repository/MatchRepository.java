package repository;

import model.Match;

import java.util.List;

public interface MatchRepository extends Repository<Match,Integer>{

    List<Match> findByPlayerName(String playerName);

    List<Match> findMatchesWithPaginationAndFilter(int offset, int limit, String playerName);

    Long countMatchesByPlayerName(String playerName);
}
