package room;

import core.GameEngine;
import core.Player;

import java.util.ArrayList;
import java.util.List;

public class Room {
    String roomToken;
    String roomName;
    String password;
    Player playerX;
    Player playerO;
    GameEngine gameEngine;
    boolean isGameInProgress;
    List<ObserverClient> observers;

    public Room(String roomName, String password, Player playerX, Player playerO, GameEngine gameEngine) {
        this.roomName = roomName;
        this.password = password;
        this.playerX = playerX;
        this.playerO = playerO;
        this.gameEngine = gameEngine;
        this.isGameInProgress = false;
        this.observers = new ArrayList<ObserverClient>();
    }

    boolean joinRoom(Player player, String password) {
        if(password.equals(this.password)) {
            if(playerO == null) {
                playerO = player;
            }
            else if(playerX == null) {
                playerX = player;
            }
            else{
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    boolean leaveRoom(Player player) {
        if(player == null){
            return false;
        }
        if(playerX.equals(player)) {
            playerX = null;
            return true;
        }
        else if(playerO.equals(player)) {
            playerO = null;
            return true;
        }
        return false;
    }

    public void resetRoom(){
        isGameInProgress = false;
        gameEngine.resetGame();
        notifyObservers("reset");
    }
    public String getBoardInfo(){
        char[][] board = gameEngine.getBoard();

        return  " ┌───┬───┬───┐\\n" +
                " | " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + " |\\n "
                + " ├───┼───┼───┤\n     " +
                " | " + board[1][0] + " | " + board[1][1] + " | " + board[1][2] + " |\\n"
                + " ├───┼───┼───┤\n     " +
                " | " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + " |\\n"
                + " └───┴───┴───┘   ";

    }

    public String getPlayerWhosTurn(){
        char currentPlayer = gameEngine.getCurrentPlayer();
        if(currentPlayer == 'X') {
            return "X";
        }
        else if(currentPlayer == 'O') {
            return "O";
        }
        else {
            return "";
        }
    }

    public boolean isYourTurn(Player player){
        gameEngine.getCurrentPlayer();
        if(player == playerX && getPlayerWhosTurn().equals("X")) {
            return true;
        }
        else if(player == playerO && getPlayerWhosTurn().equals("O")) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean makeMove(Player player, int row, int col) {
        char playerChar = ' ';
        if(isYourTurn(player)) {
            playerChar = (player.equals(playerX)) ? 'X' : 'O';
            if (gameEngine.makeMove(row, col, playerChar)) {
                notifyObservers("move");
                return true;
            }
        }
        return false;
    }

    public String checkWinner() {
        char winner = gameEngine.checkWinner();

        if (winner == 'X') {
            notifyObservers("winX");
            return "Core.Player X won!";
        } else if (winner == 'O') {
            notifyObservers("winO");
            return "Core.Player O won!";
        } else if (gameEngine.isBoardFull()) {
            notifyObservers("draw");
            return "It's a draw!";
        }
        return "Game ongoing.";
    }
    public int getPlayersNumber(){
        int number = 0;
        if(playerX != null) {
            number++;
        }
        else if(playerO != null) {
            number++;
        }
        return number;
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
    public Player getPlayerX() {
        return playerX;
    }
    public void setPlayerX(Player playerX) {
        this.playerX = playerX;
    }
    public Player getPlayerO() {
        return playerO;
    }
    public void setPlayerO(Player playerO) {
        this.playerO = playerO;
    }
    public GameEngine getGameEngine() {
        return gameEngine;
    }
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
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
}