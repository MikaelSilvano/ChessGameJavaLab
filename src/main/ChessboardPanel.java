package main;

import javax.swing.*;
import java.awt.*;

public class ChessboardPanel extends JPanel {
    private static final int SQUARE_SIZE = 105;
    private AIBoard aiBoard;

    public ChessboardPanel(AIBoard aiBoard) {
        this.aiBoard = aiBoard;
        this.setPreferredSize(new Dimension(SQUARE_SIZE * 8, SQUARE_SIZE * 8));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 64; i += 2) {
            g.setColor(new Color(255, 200, 100));
            g.fillRect((i % 8 + (i / 8) % 2) * SQUARE_SIZE, (i / 8) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            g.setColor(new Color(150, 50, 30));
            g.fillRect(((i + 1) % 8 - ((i + 1) / 8) % 2) * SQUARE_SIZE, ((i + 1) / 8) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }

        Image chessPiecesImage = new ImageIcon("src/res/ChessPieces.png").getImage();
        int x, y, x1, y1;

        for (int i = 0; i < 64; i++) {
            x = -1;
            y = -1;
            switch (aiBoard.chessBoard[i / 8][i % 8]) {
                case "P":
                    x = 5;
                    y = 0;
                    break;
                case "p":
                    x = 5;
                    y = 1;
                    break;
                case "R":
                    x = 2;
                    y = 0;
                    break;
                case "r":
                    x = 2;
                    y = 1;
                    break;
                case "K":
                    x = 4;
                    y = 0;
                    break;
                case "k":
                    x = 4;
                    y = 1;
                    break;
                case "B":
                    x = 3;
                    y = 0;
                    break;
                case "b":
                    x = 3;
                    y = 1;
                    break;
                case "Q":
                    x = 1;
                    y = 0;
                    break;
                case "q":
                    x = 1;
                    y = 1;
                    break;
                case "A":
                    x = 0;
                    y = 0;
                    break;
                case "a":
                    x = 0;
                    y = 1;
                    break;
            }
            if (x != -1 && y != -1)
                g.drawImage(chessPiecesImage, (i % 8) * SQUARE_SIZE, (i / 8) * SQUARE_SIZE, (i % 8 + 1) * SQUARE_SIZE, (i / 8 + 1) * SQUARE_SIZE, x * 64, y * 64, (x + 1) * 64, (y + 1) * 64, this);
        }
    }
}
