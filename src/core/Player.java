package core;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private String playerName;
    private int wins;
    private int losses;
    private int draws;

    public Player(String playerName) {
        this.playerName = playerName;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
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
    public Map<String, Integer> getStats(){
        Map<String, Integer> stats = new HashMap<>();
        stats.put("wins", wins);
        stats.put("losses", losses);
        stats.put("draws", draws);

        return stats;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerName.equals(player.playerName);
    }

    @Override
    public int hashCode() {
        return playerName.hashCode();
    }

}
