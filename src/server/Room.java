package server;

import java.util.ArrayList;
import java.util.HashMap;


public class Room {
    String roomToken;
    String roomName;
    String password;
    GameEngine gameEngine;
    boolean isGameInProgress;
    private HashMap<String, Player> players;
    private ArrayList<Player> allPlayers;
    private boolean isStatsUpdated = false;

    public Room(String roomName, String password, String roomToken) {
        this.setRoomName(roomName);
        this.setPassword(password);
        this.setRoomToken(roomToken);
        this.players = new HashMap();
        this.allPlayers = new ArrayList();
        this.gameEngine = new GameEngine(new String[3][3]);
        this.gameEngine.initializeBoard();

    }

    public synchronized int joinRoom(String playerToken, String password) {
        if(!password.equals(this.password)) {
            return -1;
        }
        if(getPlayersNumber() >= 2) return -2;

        String playerName = playerToken.split("@")[1];
        Player newPlayer = new Player(playerName, playerToken);
        allPlayers.add(newPlayer);
        if(players.isEmpty()){
            players.put("X", newPlayer);
            System.out.println("Player " + playerName + " joined the game as player X");
            System.out.println("Number of players: " + players.size());
            return 1;
        }
        else{
            players.put("O", newPlayer);
            System.out.println("Player " + playerName + " joined the game as player O");
            System.out.println("Number of players: " + players.size());
            return 2;
        }

    }



    public void resetRoom(){
        isGameInProgress = false;
        gameEngine.resetGame();
    }
    public String getBoardInfo(){
        String[][] board = gameEngine.getBoard();

        return  " ┌───┬───┬───┐\n" +
                " | " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + " |\n" +
                " ├───┼───┼───┤\n" +
                " | " + board[1][0] + " | " + board[1][1] + " | " + board[1][2] + " |\n" +
                " ├───┼───┼───┤\n" +
                " | " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + " |\n" +
                " └───┴───┴───┘";

    }

    public String getPlayerWhosTurn(){
        char currentSymbol = gameEngine.getCurrentPlayer();
        Player currentPlayer = players.get(String.valueOf(currentSymbol));
        return (currentPlayer != null) ? currentPlayer.getPlayerToken() : "No player";
    }

    public boolean isYourTurn(String playerToken){
        char currentSymbol = gameEngine.getCurrentPlayer();
        Player currentPlayer = players.get(String.valueOf(currentSymbol));
        return currentPlayer != null && currentPlayer.getPlayerToken().equals(playerToken);
    }
    public boolean makeMove(int row, int col) {
        char currentSymbol = gameEngine.getCurrentPlayer();
        String symbol = String.valueOf(currentSymbol);

        if(gameEngine.isMoveValid(row, col)){
            gameEngine.makeMove(symbol, row, col);
            gameEngine.switchPlayer();
            return true;
        }
        else {
            System.out.println("Invalid move. Try again");
            return false;

        }

    }

    public String checkWinner() {
        String winner = gameEngine.checkWinner();

        if (!isStatsUpdated) {
            if (winner.equals("X")) {
                players.get("X").incrementWins();
                players.get("O").incrementLosses();
                System.out.println(players.get("O").printStats());
                System.out.println(players.get("X").printStats());
                isStatsUpdated = true;
                System.out.println("Game ended. Player X wins!");
            } else if (winner.equals("O")) {
                players.get("O").incrementWins();
                players.get("X").incrementLosses();
                System.out.println(players.get("O").printStats());
                System.out.println(players.get("X").printStats());
                isStatsUpdated = true;
                System.out.println("Game ended. Player O wins!");
            } else if (gameEngine.isBoardFull()) {
                players.get("O").incrementDraws();
                players.get("X").incrementDraws();
                System.out.println(players.get("O").printStats());
                System.out.println(players.get("X").printStats());
                isStatsUpdated = true;
                System.out.println("Game ended. It's a draw.");
            }
        }

        if (winner.equals("X")) {
            return "X";
        } else if (winner.equals("O")) {
            return "O";
        } else if (gameEngine.isBoardFull()) {
            return "D";
        }
        return "Game ongoing.";
    }

    public String getStatistics(String playerToken){
        for(Player player : allPlayers){
            if(player.getPlayerToken().equals(playerToken)){
                return player.printStats();
            }
        }
        return "No statistics";
    }
    public int getPlayersNumber(){
        return players.size();
    }

    public String getRoomToken() {
        return roomToken;
    }
    public void setRoomToken(String roomToken) {
        this.roomToken = roomToken;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Player getPlayer(String playerToken) {
        return players.get(playerToken);
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

}