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