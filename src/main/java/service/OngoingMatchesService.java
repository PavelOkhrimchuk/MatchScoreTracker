package service;

import model.Match;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class OngoingMatchesService {

    private final Map<Integer, Match> onGoingMatches;
    private final Map<Integer, MatchScore> matchScores;
    private final AtomicInteger idGenerator;

    public OngoingMatchesService() {
        onGoingMatches = new ConcurrentHashMap<>();
        matchScores = new ConcurrentHashMap<>();
        idGenerator = new AtomicInteger(0);
    }

    public Integer add(Match match) {
        Integer id = idGenerator.incrementAndGet();
        onGoingMatches.put(id, match);
        matchScores.put(id, new MatchScore(match.getPlayer1().getName(), match.getPlayer2().getName()));
        return id;
    }

    public Optional<Match> get(Integer id) {
        return Optional.ofNullable(onGoingMatches.get(id));
    }

    public Optional<MatchScore> getMatchScore(Integer id) {
        return Optional.ofNullable(matchScores.get(id));
    }

    public void remove(Integer id) {
        onGoingMatches.remove(id);
        matchScores.remove(id);
    }
}
