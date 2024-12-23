public class Game {
    String gameID;
    Player playerX;
    Player playerO;
    GameEngine engine;
    Boolean isGameOver;

    public Game(String gameID, Player playerX, Player playerO) {
        this.gameID = gameID;
        this.playerX = playerX;
        this.playerO = playerO;
        this.engine = new GameEngine();
        this.isGameOver = false;
    }
     public void startGame(){
        isGameOver = false;
        engine.initializeBoard();
        System.out.println("Game started! Player X goes first.");
     }

     public boolean processMove(char player, int row, int col){
        if(isGameOver){
            System.out.println("The game is already over.");
            return false;
        }

        boolean makeMove = engine.makeMove(row, col, player);

        if(!makeMove){
            System.out.println("Invalid move!. Try again.");
            return false;
        }

        char winner = engine.checkWinner(player);
        if(winner != ' '){
            endGame("Player " + player + " wins!");
            isGameOver = true;
        }
        if (engine.isBoardFull()) {
            endGame("It's a draw!");
            return true;
        }
        engine.setCurrentPlayer(player == 'X' ? 'O' : 'X');
        return true;
     }

     public void endGame(String result){
        isGameOver = true;
        System.out.println(result);

        if(result.contains("Player X wins!")){
            playerO.incrementLosses();
            playerX.incrementWins();
        }

        if(result.contains("Player O wins!")){
            playerX.incrementLosses();
            playerO.incrementWins();
        }
        if(result.contains("It's a draw!")){
            playerX.incrementDraws();
            playerO.incrementDraws();
        }
     }
     public char[][] getCurrentState(){
        return engine.getBoard();
     }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
    public String getGameID() {
        return gameID;
    }
    public Player getPlayerX() {
        return playerX;
    }
    public Player getPlayerO() {
        return playerO;
    }
    public Boolean isGameOver() {
        return isGameOver;
    }
}
