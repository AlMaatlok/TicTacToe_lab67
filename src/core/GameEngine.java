package core;

public class GameEngine {
    private char[][] board;
    private char currentPlayer;

    public GameEngine() {
        board = new char[3][3];
        initializeBoard();
        currentPlayer = 'X';
    }

    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean makeMove(int row, int col) {
        if (!isMoveValid(row, col)) {
            return false;
        }
        board[row][col] = currentPlayer;
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

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void resetGame() {
        initializeBoard();
        currentPlayer = 'X';
    }

    public boolean isMoveValid(int row, int col) {
        return (row >= 0 && row < 3 && col >= 0 && col < 3) && board[row][col] == ' ';
    }

    public char[][] getBoard() {
        return board;
    }
}
