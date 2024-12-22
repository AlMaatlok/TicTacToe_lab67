public class GameEngine {
    char[][] board;
    char currentPlayer;

    public GameEngine() {
        initializeBoard();
        currentPlayer = 'X';
    }

    public void initializeBoard(){

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = ' ';
            }
        }

        currentPlayer = 'X';
    }

    public boolean makeMove(int row, int col, char player){
        if(!isMoveValid(row, col)){
            return false;
        }

        board[row][col] = player;
        return true;
    }
    public char checkWinner(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return player;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return player;
            }
        }

        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return player;
        }

        return ' ';
    }
    public boolean isBoardFull(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public void resetGame(char currentPlayer){
        initializeBoard();
    }
    public boolean isMoveValid(int row, int col){
        return ((row < 3 && row >= 0) && (col < 3 && col >= 0)) && board[row][col] == ' ';
    }
    public char[][] getBoard() {
        return board;
    }
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public void setBoard(char[][] board) {
        this.board = board;
    }
}
