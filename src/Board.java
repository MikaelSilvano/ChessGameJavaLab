import javax.swing.*;
import java.awt.*;

public class Board extends JFrame{
    private JPanel chessBoardPanel;
    private final int BOARD_SIZE = 8;
    private JButton[][] squares;

    public Board(){
        setTitle("Chess");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chessBoard();
        setVisible(true);
    }

    private void chessBoard(){
        chessBoardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        squares = new JButton[BOARD_SIZE][BOARD_SIZE];
        Color lightColor = new Color(240, 217, 181);
        Color darkColor = new Color(181, 136, 99);

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                JButton square = new JButton();
                square.setPreferredSize(new Dimension(75, 75));
                if ((i + j) % 2 == 0) {
                    square.setBackground(lightColor);
                }
                else {
                    square.setBackground(darkColor);
                }
                squares[i][j] = square;
                chessBoardPanel.add(square);
            }
        }
        add(chessBoardPanel);
    }
}