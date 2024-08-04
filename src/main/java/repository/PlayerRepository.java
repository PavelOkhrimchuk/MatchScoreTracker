package repository;

import model.Player;

import java.util.Optional;

public interface PlayerRepository extends Repository<Player, Integer> {



    Optional<Player> findByName(String name);
}
