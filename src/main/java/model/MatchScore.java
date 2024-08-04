package model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MatchScore {

    private UUID matchId;
    private String player1Name;
    private String player2Name;
    private int player1Score;
    private int player2Score;
    private String status;
}
