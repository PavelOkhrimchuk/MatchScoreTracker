package service;

import exception.InvalidPlayerNameException;
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

    public void validatePlayerName(String playerName) throws InvalidPlayerNameException {
        if (playerName == null || playerName.trim().isEmpty()) {
            throw new InvalidPlayerNameException("Player name cannot be empty or contain only whitespace.");
        }
        if (playerName.length() < 2) {
            throw new InvalidPlayerNameException("Player name must be at least 2 characters long.");
        }
        if (playerName.length() > 30) {
            throw new InvalidPlayerNameException("Player name cannot be longer than 30 characters.");
        }
        if (!playerName.matches("[a-zA-Zа-яА-Я0-9_\\- ]+")) {
            throw new InvalidPlayerNameException("Player name contains invalid characters.");
        }
    }
}
