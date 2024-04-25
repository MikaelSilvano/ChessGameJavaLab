//pawn
package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Pawn extends Piece {
    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Pawn";

        this.sprite = sheet.getSubimage(5 * sheetScale, yPos, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row) {
        int colorIndex;
        if(isWhite) {
            colorIndex = 1;
        } else {
            colorIndex = -1;
        }

        //pawn maju 1
        if(this.col == col && row == this.row - colorIndex && board.getPiece(col, row) == null) { //garis lurus
            return true;
        }

        //pawn maju 2
        if(isFirstMove && this.col == col && row == this.row - colorIndex * 2 && board.getPiece(col, row) == null &&  board.getPiece(col, row + colorIndex) == null) { //garis lurus
            return true;
        }

        //capture left
        if(col == this.col - 1 && row == this.row - colorIndex && board.getPiece(col, row) != null) {
            return true;
        }

        //capture right
        if(col == this.col + 1 && row == this.row - colorIndex && board.getPiece(col, row) != null) {
            return true;
        }

        //en passant left
        if(board.getTileNum(col, row) == board.enPassantTile && col == this.col - 1  && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null) {
            return true;
        }

        //en passant right
        if(board.getTileNum(col, row) == board.enPassantTile && col == this.col + 1  && row == this.row - colorIndex && board.getPiece(col, row + colorIndex) != null) {
            return true;
        }
        return false;
    }
}