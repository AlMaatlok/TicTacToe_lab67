package room;

import connection.ObserverClient;
import core.GameEngine;
import core.Player;

import java.util.HashMap;
import java.util.List;

public class Room {
    String roomToken;
    String roomName;
    String password;
    Player playersTurn;
    GameEngine gameEngine;
    boolean isGameInProgress;
    List<ObserverClient> observers;
    private HashMap<String, Player> players;

    public Room(String roomName, String password, String roomToken) {
       this.setRoomName(roomName);
       this.setPassword(password);
       this.setRoomToken(roomToken);
       players = new HashMap();

    }

    public boolean joinRoom(Player player, String password) {
        if (password.equals(this.password)) {
            if (players.size() < 2) {
                String token = (players.size() == 0) ? "X" : "O";
                players.put(token, player);
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean leaveRoom(Player player) {
        if (player == null) {
            return false;
        }

        players.values().remove(player);
        return true;
    }

    public void resetRoom(){
        isGameInProgress = false;
        gameEngine.resetGame();
        notifyObservers("reset");
    }
    public String getBoardInfo(){
        char[][] board = gameEngine.getBoard();

        return  " ┌───┬───┬───┐\n" +
                " | " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + " |\n "
                + " ├───┼───┼───┤\n     " +
                " | " + board[1][0] + " | " + board[1][1] + " | " + board[1][2] + " |\n"
                + " ├───┼───┼───┤\n     " +
                " | " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + " |\n"
                + " └───┴───┴───┘   ";

    }

    public String getPlayerWhosTurn(){
        return (gameEngine.getCurrentPlayer() == 'X') ? "X" : "O";
    }

    public boolean isYourTurn(Player player){
        String currentPlayerToken = getPlayerWhosTurn();
        if (players.get(currentPlayerToken) == player) {
            return true;
        }
        return false;
    }
    public boolean makeMove(Player player, int row, int col) {
        String playerToken = (players.get("X") == player) ? "X" : "O";
        if (gameEngine.makeMove(row, col)) {
            String winnerStatus = checkWinner();
            if (winnerStatus.equals("Game ongoing.")) {
                notifyObservers("move");
            }
            return true;
        }
        return false;
    }

    public String checkWinner() {
        char winner = gameEngine.checkWinner();

        if (winner == 'X') {
            notifyObservers("winX");
            players.get("X").incrementWins();
            players.get("O").incrementLosses();
            return "Player X won!";
        } else if (winner == 'O') {
            notifyObservers("winO");
            players.get("O").incrementWins();
            players.get("X").incrementLosses();
            return "Player O won!";
        } else if (gameEngine.isBoardFull()) {
            notifyObservers("draw");
            players.get("O").incrementDraws();
            players.get("X").incrementDraws();
            return "It's a draw!";
        }
        return "Game ongoing.";
    }
    public int getPlayersNumber(){
        return players.size();
    }
    public boolean isFull() {
        return players.size() == 2;
    }


    public void addObserver(ObserverClient observer) {
        observers.add(observer);
        getBoardInfo();
    }

    public void notifyObservers(String update) {
        String boardInfo = getBoardInfo();
        for(ObserverClient observer : observers) {
            observer.update(update, boardInfo);
        }
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
    public boolean isGameInProgress() {
        return isGameInProgress;
    }
    public void setGameInProgress(boolean gameInProgress) {
        isGameInProgress = gameInProgress;
    }
    public List<ObserverClient> getObservers() {
        return observers;
    }
    public void setObservers(List<ObserverClient> observers) {
        this.observers = observers;
    }
    public Player getPlayersTurn(){
        return playersTurn;
    }
    public void setPlayersTurn(Player player){
        playersTurn = player;
    }

}