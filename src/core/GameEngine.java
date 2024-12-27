package core;

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

    public char checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }

        if (isBoardFull()) {
            return 'D';
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

    public void resetGame(){
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
}
