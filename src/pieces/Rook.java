//rook
package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Rook extends Piece {
    public Rook(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Rook";

        this.sprite = sheet.getSubimage(4 * sheetScale, yPos, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row) {
        if(this.col == col || this.row == row) {
            return true;
        } else {
            return false;
        }
    }

    public boolean moveCollidesWithPiece(int col, int row) {
        //left
        if(this.col > col) {
            for(int i = this.col - 1; i > col; i--) {
                if(board.getPiece(i, this.row) != null) {
                    return true;
                }
            }
        }

        //right
        if(this.col < col) {
            for(int i = this.col + 1; i < col; i++) {
                if(board.getPiece(i, this.row) != null) {
                    return true;
                }
            }
        }

        //up
        if(this.row > row) {
            for(int j = this.row - 1; j > row; j--) {
                if(board.getPiece(this.col, j) != null) {
                    return true;
                }
            }
        }

        //down
        if(this.row < row) {
            for(int j = this.row + 1; j < row; j++) {
                if(board.getPiece(this.col, j) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}