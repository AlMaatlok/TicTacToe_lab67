package server;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private String playerName;
    protected String playerToken;
    private int wins;
    private int losses;
    private int draws;

    public Player( String playerName, String playerToken) {
        this.playerName = playerName;
        this.playerToken = playerToken;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }
    public String getPlayerToken() {
        return playerToken;
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
        stats.put("Wins", getWins());
        stats.put("Losses", getLosses());
        stats.put("Draws", getDraws());

        return stats;
    }
    public String printStats(){
        Map<String, Integer> stats = getStats();
        StringBuilder sb = new StringBuilder();
        sb.append("Player: ").append(playerName).append("\n");
        for(Map.Entry<String, Integer> entry : stats.entrySet()){
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
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
