public class Game {
    String gameID;
    Player playerX;
    Player playerO;
    GameEngine engine;
    Boolean isGameOver;

    public Game(String gameID, Player playerX, Player playerO, GameEngine engine) {
        this.gameID = gameID;
        this.playerX = playerX;
        this.playerO = playerO;
        this.engine = engine;
        this.isGameOver = false;
        
    }
}
