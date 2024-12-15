import java.util.ArrayList;

public class Player {
    String playerName;
    int wins;
    int losses;
    int draws;

    public Player(String playerName, int wins, int losses, int draws) {
        this.playerName = playerName;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }
    public String getPlayerName() {
        return playerName;
    }
    public int getWins() {
        return wins;
    }
    public int getLosses() {
        return losses;
    }
    public int getDraws() {
        return draws;
    }
    public void incrementWins() {
        wins++;
    }
    public void incrementLosses() {
        losses++;
    }
    public void incrementDraws() {
        draws++;
    }
    public void getStats(){

    }
}
