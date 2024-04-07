//king
package pieces;

import main.Board3;
import main.Move;

import java.awt.image.BufferedImage;

public class King extends Piece {
    public King(Board3 board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "King";

        this.sprite = sheet.getSubimage(0 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row) {
        if(Math.abs((col - this.col) * (row - this.row)) == 1 || Math.abs(col - this.col) + Math.abs(row - this.row) == 1 || canCastle(col, row)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canCastle(int col, int row) {
        if(this.row == row) {
            if(col == 6) {
                Piece rook = board.getPiece(7, row);
                if(rook != null && rook.isFirstMove && isFirstMove) {
                    if(board.getPiece(5, row) == null && board.getPiece(6, row) == null && !board.checkScanner.isKingChecked(new Move(board, this, 5, row))) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else if (col == 2) {
                Piece rook = board.getPiece(0, row);
                if(rook != null && rook.isFirstMove && isFirstMove) {
                    if(board.getPiece(3, row) == null && board.getPiece(2, row) == null && board.getPiece(1, row) == null && !board.checkScanner.isKingChecked(new Move(board, this, 3, row))) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}