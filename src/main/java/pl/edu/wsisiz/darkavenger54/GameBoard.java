package pl.edu.wsisiz.darkavenger54;

public class GameBoard
{
    private final EnFigureType[] board;

    public GameBoard() {
        board = new EnFigureType[9];
        reset();
    }

    public void reset() {
        for (int i = 0; i < 9; i++) {
            board[i] = EnFigureType.NONE;
        }
    }

    public boolean setMove(int index, EnFigureType figure) {
        if (index < 0 || index > 8) return false;
        if (board[index] != EnFigureType.NONE) return false;
        board[index] = figure;
        return true;
    }

    public EnFigureType getCell(int index) {
        if (index < 0 || index > 8) return EnFigureType.NONE;
        return board[index];
    }

    public EnFigureType checkWinner() {
        int[][] winCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] combo : winCombinations) {
            EnFigureType a = board[combo[0]];
            EnFigureType b = board[combo[1]];
            EnFigureType c = board[combo[2]];
            if (a != EnFigureType.NONE && a == b && b == c) {
                return a;
            }
        }
        return EnFigureType.NONE;
    }

    public boolean isFull() {
        for (EnFigureType cell : board) {
            if (cell == EnFigureType.NONE) return false;
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < 9; i++) {
            System.out.print(board[i].name().charAt(0) + " ");
            if ((i + 1) % 3 == 0) System.out.println();
        }
    }

}
