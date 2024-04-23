//piece
package pieces;

import main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {
    public int col, row; //col row dari setiap pieces
    public int xPos, yPos; //x dan y position dari pieces

    public boolean isWhite;
    public String name;
    public int value;
    public boolean isFirstMove = true;

    BufferedImage sheet; //dapetin image dari gambar chess
    {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("PiecesImages2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected int sheetScale = sheet.getWidth()/6;

    Image sprite; //ngambil bagian2 piece

    Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public boolean canMove(Board board, int newCol, int newRow, int oldCol, int oldRow, boolean isWhiteTurn) {
        // Check if the new position is within the bounds of the board
        if (newCol < 0 || newCol >= 8 || newRow < 0 || newRow >= 8) {
            return false;
        }

        // Check if the piece is actually moving
        if (newCol == oldCol && newRow == oldRow) {
            return false;
        }

        // Check if there is a piece of the same color at the target position
        Piece targetPiece = board.getPiece(newCol, newRow);
        if (targetPiece != null && targetPiece.isWhite == this.isWhite) {
            return false;
        }

        // Add specific movement rules for each type of piece
        // For now, let's return true for demonstration purposes

        // Movement rules for Pawn
        if (name.equals("Pawn")) {
            // Pawns can move forward one square, or two squares from their starting position
            // They can capture diagonally forward
            int direction = isWhite ? -1 : 1;
            if (oldCol == newCol && (newRow - oldRow == direction || (isFirstMove && newRow - oldRow == 2 * direction))) {
                // Check if there's no piece in the way
                if (board.getPiece(newCol, newRow) == null) {
                    return true;
                }
            } else if (Math.abs(newCol - oldCol) == 1 && newRow - oldRow == direction) {
                // Check if there's an enemy piece to capture
                if (targetPiece != null && targetPiece.isWhite != isWhite) {
                    return true;
                }
            }
            return false;
        }

        // Movement rules for Rook
        if (name.equals("Rook")) {
            // Rooks can move horizontally or vertically any number of squares
            if (oldCol == newCol || oldRow == newRow) {
                // Check if there's no piece in the way
                if (!isPathObstructed(oldCol, oldRow, newCol, newRow)) {
                    return true;
                }
            }
            return false;
        }

        // Movement rules for Knight
        if (name.equals("Knight")) {
            // Knights move in an L-shape: two squares in one direction and then one square perpendicular to that direction
            int deltaCol = Math.abs(newCol - oldCol);
            int deltaRow = Math.abs(newRow - oldRow);
            if ((deltaCol == 1 && deltaRow == 2) || (deltaCol == 2 && deltaRow == 1)) {
                return true;
            }
            return false;
        }

        // Movement rules for Bishop
        if (name.equals("Bishop")) {
            // Bishops move diagonally any number of squares
            if (Math.abs(newCol - oldCol) == Math.abs(newRow - oldRow)) {
                // Check if there's no piece in the way
                if (!isPathObstructed(oldCol, oldRow, newCol, newRow)) {
                    return true;
                }
            }
            return false;
        }

        // Movement rules for Queen
        if (name.equals("Queen")) {
            // Queens can move horizontally, vertically, or diagonally any number of squares
            if (oldCol == newCol || oldRow == newRow || Math.abs(newCol - oldCol) == Math.abs(newRow - oldRow)) {
                // Check if there's no piece in the way
                if (!isPathObstructed(oldCol, oldRow, newCol, newRow)) {
                    return true;
                }
            }
            return false;
        }

        // Movement rules for King
        if (name.equals("King")) {
            // Kings can move one square in any direction
            if (Math.abs(newCol - oldCol) <= 1 && Math.abs(newRow - oldRow) <= 1) {
                return true;
            }
            return false;
        }

        return true;
    }

    private boolean isPathObstructed(int oldCol, int oldRow, int newCol, int newRow) {
        int colDir = Integer.compare(newCol, oldCol);
        int rowDir = Integer.compare(newRow, oldRow);

        int col = oldCol + colDir;
        int row = oldRow + rowDir;

        while (col != newCol || row != newRow) {
            if (board.getPiece(col, row) != null) {
                return true;
            }
            col += colDir;
            row += rowDir;
        }

        return false;
    }

    public boolean isValidMovement(int col, int row) {
        return true;
    }

    public boolean moveCollidesWithPiece(int col, int row) {
        return false;
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}