//input 
package main;

import pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input extends MouseAdapter {
    Board3 board;
    InputAudio putSound;
    InputAudio pickSound;
    public Input(Board3 board) {
        this.board = board;
        pickSound = new InputAudio("src/res/PickUpPieces.wav");
        putSound = new InputAudio("src/res/PutPieces.wav");
    }
    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / board.tileSize; //untuk dapetin col di mana mouse itu di klik
        int row = e.getY() / board.tileSize;

        Piece pieceXY = board.getPiece(col, row); //to get piece at that location
        if(pieceXY != null) {
            board.selectedPiece = pieceXY; //kita mendapatkan piecenya
            pickSound.PickPieceSound();
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if(board.selectedPiece != null) {
            board.selectedPiece.xPos = e.getX() - board.tileSize / 2; //agar posisi piece center di mousenya
            board.selectedPiece.yPos = e.getY() - board.tileSize / 2;
            board.repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;
        if(board.selectedPiece != null) {
            Move move = new Move(board, board.selectedPiece, col, row);
            if(board.isValidMove(move)) {
                board.makeMove(move);
            }
            else {
                board.selectedPiece.xPos = board.selectedPiece.col * board.tileSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;
            }
        }
        board.selectedPiece = null;
        board.repaint();
        putSound.PutPieceSound();
    }
}