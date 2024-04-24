package pieces;

import main.Board;
import main.Board2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pieces2 {
    public int col, row; //col row dari setiap pieces
    public int xPos, yPos; //x dan y position dari pieces

    public boolean isWhite;
    public String name;
    public int value;
    BufferedImage sheet; //dapetin image dari gambar chess
    {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("PiecesImages2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Image sprite;
    public void Piece(Board2 board) {
        /*
        this.board = board;

         */

    }
}
