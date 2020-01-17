import java.awt.*;
import java.util.Random;

public class TicTacToe {

    //game board
    public static final int ROWS = 3;
    public static final int COLS = 3;

    public enum GameState {
        PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }
    public static GameState currentState;

    public enum GameOption{
        TWO_PlAYERS, ONE_PLAYER
    }

    public static GameOption currentOption;

    public enum Seed {
        EMPTY, CROSS, NOUGHT
    }
    public static Seed currentPlayer;

    public static Seed[][] board   ;

    public TicTacToe(){

        this.board = new TicTacToe.Seed[TicTacToe.ROWS][TicTacToe.COLS];
        initGame();
    }

    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = Seed.EMPTY;
            }
        }
        currentState = GameState.PLAYING;
        currentPlayer = Seed.CROSS;
    }


    public void updateGame(Seed theSeed, int rowSelected, int colSelected) {
        board[rowSelected][colSelected] = theSeed;
        if (hasWon(theSeed, rowSelected, colSelected)) {  // check for win
            currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
        } else if (isDraw()) {  // check for draw
            currentState = GameState.DRAW;
        }

    }

    public Point computerMove(Seed[][] board){
        Point position = null;

        Random randomRow;
        Random randomCol;
        Boolean freeSeed = false;
        int row=0;
        int col=0;
        while (!freeSeed){
            randomRow = new Random();
            row=randomRow.nextInt(this.ROWS);
            System.out.println("row "+row);
            randomCol = new Random();
            col=randomCol.nextInt(this.COLS);
            System.out.println("col "+col);
            if(board[row][col]== Seed.EMPTY){
                position = new Point(row,col);
                freeSeed=true;
            }
        }
        return position;
    }


    public boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == Seed.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean hasWon(Seed theSeed, int rowSelected, int colSelected) {
        return (board[rowSelected][0] == theSeed
                && board[rowSelected][1] == theSeed
                && board[rowSelected][2] == theSeed
                || board[0][colSelected] == theSeed
                && board[1][colSelected] == theSeed
                && board[2][colSelected] == theSeed
                || rowSelected == colSelected
                && board[0][0] == theSeed
                && board[1][1] == theSeed
                && board[2][2] == theSeed
                || rowSelected + colSelected == 2
                && board[0][2] == theSeed
                && board[1][1] == theSeed
                && board[2][0] == theSeed);
    }


}
