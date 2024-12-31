package server;

import java.util.HashMap;


public class Room {
    String roomToken;
    String roomName;
    String password;
    Player playersTurn;
    GameEngine gameEngine;
    boolean isGameInProgress;
    private HashMap<String, Player> players;

    public Room(String roomName, String password, String roomToken) {
        this.setRoomName(roomName);
        this.setPassword(password);
        this.setRoomToken(roomToken);
        this.players = new HashMap();
        this.gameEngine = new GameEngine(new String[3][3]);
        this.gameEngine.initializeBoard();

    }
    public int joinRoom(String playerToken, String password) {
        if(!password.equals(this.password)) {
            return -1;
        }
        if(getPlayersNumber() >= 2) return -2;

        if(players.isEmpty()){
            players.put("X", new Player(playerToken));
            System.out.println("Player " + playerToken + " joined the game as player X");
            System.out.println("Number of players: " + players.size());
            return 1;
        }
        else{
            players.put("O", new Player(playerToken));
            System.out.println("Player " + playerToken + " joined the game as player O");
            System.out.println("Number of players: " + players.size());
            return 2;
        }

    }

    public boolean leaveRoom(Player player) {
        if (player == null) {
            return false;
        }

        players.values().remove(player);
        System.out.println("Player " + player.getPlayerToken() + " left the game");
        return true;
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
    public void initilizeBoard(){
        gameEngine.initializeBoard();
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

        if (winner == "X") {
            players.get("X").incrementWins();
            players.get("O").incrementLosses();
            return "Player X won!";
        } else if (winner == "O") {
            players.get("O").incrementWins();
            players.get("X").incrementLosses();
            return "Player O won!";
        } else if (gameEngine.isBoardFull()) {
            players.get("O").incrementDraws();
            players.get("X").incrementDraws();
            return "It's a draw!";
        }
        return "Game ongoing.";
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
    public boolean isGameInProgress() {
        return isGameInProgress;
    }
    public void setGameInProgress(boolean gameInProgress) {
        isGameInProgress = gameInProgress;
    }
    public Player getPlayersTurn(){
        return playersTurn;
    }
    public void setPlayersTurn(Player player){
        playersTurn = player;
    }
    public HashMap<String, Player> getPlayers() {
        return players;
    }
    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }

}