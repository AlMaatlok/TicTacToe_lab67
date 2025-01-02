package server;

public class GameEngine {
    private String[][] board;
    private char currentPlayer;

    public GameEngine(String[][] board) {
        this.board = board;
        currentPlayer = 'X';
    }

    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
            }
        }
    }

    public boolean makeMove(String symbol, int row, int col) {
        if (!isMoveValid(row, col)) {
            return false;
        }
        board[row][col] = symbol;
        return true;
    }

    public String checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].equals(" ") && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
                return board[i][0];
            }
        }
        for (int i = 0; i < 3; i++) {
            if (!board[0][i].equals(" ") && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i])) {
                return board[0][i];
            }
        }

        if (!board[0][0].equals(" ") && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            return board[0][0];
        }
        else if (!board[0][2].equals(" ") && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
            return board[0][2];
        }

        else if (isBoardFull()) {
            return "D";
        }

        return "Game ongoing.";
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(" ")) {
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
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            System.out.println("Move is out of bounds!");
            return false;
        }
        if (!board[row][col].equals(" ")) {
            System.out.println("Cell is already occupied!");
            return false;
        }
        return true;
    }


    public String[][] getBoard() {
        return board;
    }
}
