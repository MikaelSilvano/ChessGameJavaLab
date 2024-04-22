package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ChessBoardPanel extends JPanel implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;
    private static String userPossibleMoves;
    private static int oldMouseX, oldMouseY, newMouseX, newMouseY;
    private static int squareSize = 105;
    private static AIBoard aiBoard;
    private InputAudio promotionSound;
    private InputAudio eatSound;
    private InputAudio putSound;
    private InputAudio pickSound;

    public ChessBoardPanel(AIBoard aiBoard) {
        promotionSound = new InputAudio("src/res/PawnPromotion.wav");
        eatSound = new InputAudio("src/res/EatPieces.wav");
        pickSound = new InputAudio("src/res/PickUpPieces.wav");
        putSound = new InputAudio("src/res/PutPieces.wav");
        ChessBoardPanel.aiBoard = new AIBoard(); // Assuming AIBoard is already instantiated
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 64; i += 2) {
            g.setColor(new Color(255, 200, 100));
            g.fillRect((i % 8 + (i / 8) % 2) * squareSize, (i / 8) * squareSize, squareSize, squareSize);
            g.setColor(new Color(150, 50, 30));
            g.fillRect(((i + 1) % 8 - ((i + 1) / 8) % 2) * squareSize, ((i + 1) / 8) * squareSize, squareSize,
                    squareSize);
        }
        Image chessPiecesImage;
        chessPiecesImage = new ImageIcon("src/res/ChessPieces.png").getImage();
        for (int i = 0; i < 64; i++) {
            int x = -1;
            int y = -1;
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
                g.drawImage(chessPiecesImage, (i % 8) * squareSize, (i / 8) * squareSize, (i % 8 + 1) * squareSize,
                        (i / 8 + 1) * squareSize, x * 64, y * 64, (x + 1) * 64, (y + 1) * 64, this);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
            newMouseX = e.getX();
            newMouseY = e.getY();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
            oldMouseX = e.getX() / squareSize;
            oldMouseY = e.getY() / squareSize;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
            newMouseX = e.getX() / squareSize;
            newMouseY = e.getY() / squareSize;
            String move;
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (newMouseY == 0 && oldMouseY == 1 && "P".equals(aiBoard.chessBoard[oldMouseY][oldMouseX])) {
                    // Pawn promotion
                    move = "" + oldMouseX + newMouseX + aiBoard.chessBoard[newMouseY][newMouseX] + "QP";
                } else {
                    // Regular move
                    move = "" + oldMouseY + oldMouseX + newMouseY + newMouseX + aiBoard.chessBoard[newMouseY][newMouseX];
                }
                userPossibleMoves = aiBoard.possibleMoves();
                if (userPossibleMoves.replaceAll(move, "").length() < userPossibleMoves.length()) {
                    aiBoard.makeMove(move);
                    aiBoard.flipBoard();
                    aiBoard.makeMove(aiBoard.alphaBeta(aiBoard.globalDepth, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0));
                    aiBoard.flipBoard();
                    repaint();
                    putSound.PutPieceSound();
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not implemented
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not implemented
    }
}
