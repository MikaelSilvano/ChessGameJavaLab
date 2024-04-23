/*
package main;

import main.AIBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static main.AIUserInterface.*;

public class ChessBoardPanel extends JPanel implements MouseListener, MouseMotionListener {
    private static final int SQUARE_SIZE = 90;
    private AIBoard aiBoard;
    private int selectedSquare = -1; // Stores the index of the currently selected square (-1 if none)
    private static String userPossibleMoves;
    InputAudio putSound;

    public ChessBoardPanel(AIBoard aiBoard) {
        this.aiBoard = aiBoard;
        this.setPreferredSize(new Dimension(SQUARE_SIZE * 8, SQUARE_SIZE * 8));

        // Add mouse listener to capture clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / SQUARE_SIZE;
                int y = e.getY() / SQUARE_SIZE;
                int squareIndex = y * 8 + x;

                // Handle square selection logic here
                handleSquareSelection(squareIndex);
            }
        });
    }

    // Method to handle square selection logic
    private void handleSquareSelection(int squareIndex) {
        if (selectedSquare == -1) {
            // No square selected yet, select the clicked square if it contains a piece
            if (isValidSquare(squareIndex) && aiBoard.chessBoard[squareIndex / 8][squareIndex % 8].trim().length() > 0) {
                selectedSquare = squareIndex;
                repaint(); // Repaint to highlight the selected square
            }
        } else {
            // A square is already selected, check if the clicked square is a valid destination
            int sourceSquare = selectedSquare;
            int destSquare = squareIndex;

            // Convert square indices to chess coordinates
            int sourceX = sourceSquare % 8;
            int sourceY = sourceSquare / 8;
            int destX = destSquare % 8;
            int destY = destSquare / 8;

            // Check if the move is valid
            boolean validMove = aiBoard.isMoveValid(sourceX, sourceY, destX, destY);

            if (validMove) {
                // Make the move
                handleMove(sourceSquare, destSquare);
            }

            // Reset selected square after the move or if the move is invalid
            selectedSquare = -1;
            repaint(); // Repaint to remove highlighting
        }
    }

    // Method to check if the square index is valid
    private boolean isValidSquare(int squareIndex) {
        return squareIndex >= 0 && squareIndex < 64;
    }

    // Method to handle moving a piece from source square to destination square
    private void handleMove(int sourceSquare, int destSquare) {
        // Convert square indices to chess coordinates
        int sourceX = sourceSquare % 8;
        int sourceY = sourceSquare / 8;
        int destX = destSquare % 8;
        int destY = destSquare / 8;

        // Get the piece at the source square
        String piece = aiBoard.chessBoard[sourceY][sourceX].trim();

        // Check if the move is valid
        boolean validMove = aiBoard.isMoveValid(sourceX, sourceY, destX, destY);

        if (validMove) {
            // Make the move
            aiBoard.makeMove(sourceX, sourceY, destX, destY);
            // Flip the board to reflect the move
            aiBoard.flipBoard();
            // Let the AI make its move
            aiBoard.makeAIMove();
            // Flip the board back
            aiBoard.flipBoard();
        } else {
            // The move is invalid, handle this case if needed
            System.out.println("Invalid move!");
        }

        // Repaint the panel
        repaint();
    }

    // Override the paintComponent method to draw the chessboard and pieces
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 64; i += 2) {
            g.setColor(new Color(210, 81, 66));
            g.fillRect((i % 8 + (i / 8) % 2) * SQUARE_SIZE, (i / 8) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            g.setColor(new Color(57, 47, 79));
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
    @Override
    public void mouseClicked(MouseEvent e) {
        // Not used in this implementation
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX()<8*squareSize && e.getY()<8*squareSize){//if mouse is pressed inside the chess board
            oldMouseX=e.getX()/squareSize;
            oldMouseY=e.getY()/squareSize;
            //pickSound.PickPieceSound();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getX()<8*squareSize && e.getY()<8*squareSize){//if mouse is released inside the chess board
            newMouseX=e.getX()/squareSize;
            newMouseY=e.getY()/squareSize;
            String move;
            if(e.getButton()==MouseEvent.BUTTON1){
                if(newMouseY==0 && oldMouseY==1 && "P".equals(AIBoard.chessBoard[oldMouseY][oldMouseX])){
                    //if pawn promotion
                    move=""+oldMouseX+newMouseX+ AIBoard.chessBoard[newMouseY][newMouseX]+"QP";
                }
                else{	//if a regular move
                    move=""+oldMouseY+oldMouseX+newMouseY+newMouseX+ AIBoard.chessBoard[newMouseY][newMouseX];
                }
                userPossibleMoves= AIBoard.possibleMoves();
                if(userPossibleMoves.replaceAll(move, "").length()<userPossibleMoves.length()){
                    AIBoard.makeMove(move);
                    AIBoard.flipBoard();
                    AIBoard.makeMove(AIBoard.alphaBeta(AIBoard.globalDepth, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0));
                    AIBoard.flipBoard();
                    repaint();
                    putSound.PutPieceSound();
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not used in this implementation
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not used in this implementation
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(e.getX()<8*squareSize && e.getY()<8*squareSize){//if mouse is dragged inside the chess board
            newMouseX=e.getX();
            newMouseY=e.getY();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

 */
