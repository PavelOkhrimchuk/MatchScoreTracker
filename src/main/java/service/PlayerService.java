package service;

import model.Player;
import repository.PlayerRepository;

import java.util.Optional;

public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getPlayer(String playerName) {

        Optional<Player> playerOptional = playerRepository.findByName(playerName);
        if (playerOptional.isPresent()) {
            return playerOptional.get();
        } else {
            Player newPlayer = new Player();
            newPlayer.setName(playerName);
            playerRepository.save(newPlayer);
            return newPlayer;
        }

    }
}
