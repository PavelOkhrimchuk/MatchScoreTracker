package repository;

import model.Match;
import model.Page;

import java.util.List;

public interface MatchRepository extends Repository<Match,Integer>{

    List<Match> findByPlayerName(String playerName);

    Page<Match> findMatchesWithPaginationAndFilter(int pageNumber, int pageSize, String playerName);

    Long countMatchesByPlayerName(String playerName);
}
