import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TicTacToeUI extends JFrame {

    public static final int CELL_SIZE = 100;
    public static final int CANVAS_WIDTH = CELL_SIZE * TicTacToe.COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * TicTacToe.ROWS;
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;

    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;



    private DrawCanvas canvas;
    private JLabel statusBar;
    TicTacToe ticTacToe;
    Point computerChoice=null;


    public TicTacToeUI() {


        int n = JOptionPane.showConfirmDialog(
                this,
                "Select YES if you want two Players or NO if you want to play with the computer!",
                "Choose game option",
                JOptionPane.YES_NO_OPTION);
        if (n==0){
            TicTacToe.currentOption=TicTacToe.GameOption.TWO_PlAYERS;
        }
        else if (n==1){
            TicTacToe.currentOption=TicTacToe.GameOption.ONE_PLAYER;
        }
        else {
            System.exit(0);
        }

        ticTacToe = new TicTacToe();
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));


        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {


                int mouseX = e.getX();
                int mouseY = e.getY();

                int rowSelected = mouseY / CELL_SIZE;
                int colSelected = mouseX / CELL_SIZE;

                if (TicTacToe.currentState == TicTacToe.GameState.PLAYING) {
                    if (rowSelected >= 0 && rowSelected < TicTacToe.ROWS && colSelected >= 0
                            && colSelected < TicTacToe.COLS &&TicTacToe.board[rowSelected][colSelected] == TicTacToe.Seed.EMPTY) {

                        ticTacToe.updateGame(TicTacToe.currentPlayer, rowSelected, colSelected);

                    }
                }else {
                    new TicTacToe();
                }

                System.out.println("check clicks");
                repaint();

            }
        });


        statusBar = new JLabel("  ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(statusBar, BorderLayout.PAGE_END);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("Tic Tac Toe");
        setVisible(true);

        new TicTacToe();
    }



    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            setBackground(Color.WHITE);

            g.setColor(Color.LIGHT_GRAY);
            for (int row = 1; row < TicTacToe.ROWS; ++row) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                        CANVAS_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < TicTacToe.COLS; ++col) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                        GRID_WIDTH, CANVAS_HEIGHT-1, GRID_WIDTH, GRID_WIDTH);
            }

            Graphics2D g2d = (Graphics2D)g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));  // Graphics2D only
            for (int row = 0; row < TicTacToe.ROWS; ++row) {
                for (int col = 0; col < TicTacToe.COLS; ++col) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (TicTacToe.board[row][col] == TicTacToe.Seed.CROSS) {
                        g2d.setColor(Color.RED);
                        int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                        int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                        g2d.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    } else if (TicTacToe.board[row][col] == TicTacToe.Seed.NOUGHT) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }
                }
            }


            if (ticTacToe.currentState == TicTacToe.GameState.PLAYING) {
                statusBar.setForeground(Color.BLACK);
                if (TicTacToe.currentPlayer == TicTacToe.Seed.CROSS) {
                    if (TicTacToe.currentOption==TicTacToe.GameOption.ONE_PLAYER){
                        statusBar.setText("Computer's Turn");
                        computerChoice = ticTacToe.computerMove();
                        ticTacToe.updateGame(TicTacToe.currentPlayer, computerChoice.x, computerChoice.y);
                    }else {
                        statusBar.setText("X's Turn");
                    }
                } else {
                    statusBar.setText("O's Turn");
                }
            } else if (TicTacToe.currentState == TicTacToe.GameState.DRAW) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("It's a Draw! Click to play again.");
            } else if (TicTacToe.currentState == TicTacToe.GameState.CROSS_WON) {
                statusBar.setForeground(Color.RED);
                if (TicTacToe.currentOption==TicTacToe.GameOption.ONE_PLAYER){
                    statusBar.setText("'Computer' Won! Click to play again.");
                }else {
                    statusBar.setText("'X' Won! Click to play again.");
                }

            } else if (TicTacToe.currentState == TicTacToe.GameState.NOUGHT_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("'O' Won! Click to play again.");
            }

            repaint();
        }
    }


}