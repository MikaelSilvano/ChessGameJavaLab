//knight
package pieces;

import main.Board3;

import java.awt.image.BufferedImage;

public class Knight extends Piece {
    public Knight(Board3 board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Knight";

        int yPos;
        if (isWhite) {
            yPos = 0;
        } else {
            yPos = sheetScale;
        }
        this.sprite = sheet.getSubimage(3 * sheetScale, yPos, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row) {
        if(Math.abs(col - this.col) * Math.abs(row - this.row) == 2) {
            return true;
        } else {
            return false;
        }
    }
}