package service;

import model.Match;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private final Map<UUID, Match> onGoingMatches;


    public OngoingMatchesService() {
        onGoingMatches = new ConcurrentHashMap<>();
    }


    public UUID add(Match match) {
        UUID uuid = UUID.randomUUID();
        while (onGoingMatches.containsKey(uuid)) {
            uuid = UUID.randomUUID();
        }
        onGoingMatches.put(uuid, match);
        return uuid;
    }

    public Optional<Match> get(UUID uuid) {
        return Optional.ofNullable(onGoingMatches.get(uuid));
    }

    public void remove(UUID uuid) {
        onGoingMatches.remove(uuid);
    }
}
