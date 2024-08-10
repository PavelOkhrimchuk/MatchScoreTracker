package service;

import model.Match;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class OngoingMatchesService {

    private final Map<Integer, MatchDetail> ongoingMatches;
    private final AtomicInteger idGenerator;

    public OngoingMatchesService() {
        ongoingMatches = new ConcurrentHashMap<>();
        idGenerator = new AtomicInteger(0);
    }

    public Integer add(Match match) {
        Integer id = idGenerator.incrementAndGet();
        ongoingMatches.put(id, new MatchDetail(match, new MatchScore(match.getPlayer1().getName(), match.getPlayer2().getName())));
        return id;
    }

    public Optional<Match> get(Integer id) {
        return Optional.ofNullable(ongoingMatches.get(id))
                .map(MatchDetail::match);
    }

    public Optional<MatchScore> getMatchScore(Integer id) {
        return Optional.ofNullable(ongoingMatches.get(id))
                .map(MatchDetail::matchScore);
    }

    public void remove(Integer id) {
        ongoingMatches.remove(id);
    }


    private record MatchDetail(Match match, MatchScore matchScore) {

    }
}