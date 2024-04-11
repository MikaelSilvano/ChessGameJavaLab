import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JFrame{
    private JPanel chessBoardPanel;
    private final int BOARD_SIZE = 8;
    private JButton[][] squares;
    private JButton menuButton;
    private JButton exitButton;

    public Board(){
        setTitle("Chess");
        setSize(800,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chessBoard();
        setVisible(true);
    }

    private void chessBoard(){
        JPanel mainPanel = new JPanel(new BorderLayout());

        //chess board panel
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


        //button yang dikanan
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); //untuk merapihkan button yang disebelah kanan

        menuButton = new JButton("Menu");
        menuButton.setPreferredSize(new Dimension(100, 50));
        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(100, 50));
        buttonPanel.add(menuButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(exitButton, gbc);

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); //menutup board
                new Menu(); //back to menu
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //exit button
            }
        });

        mainPanel.add(chessBoardPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        add(mainPanel);
    }

    public void setDifficulty(String difficulty) {
    }

    public void setSinglePlayer(boolean isSinglePlayer) {
    }
}
