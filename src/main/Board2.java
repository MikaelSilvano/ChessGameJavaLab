package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Board2 extends JPanel {
    public int tileSize = 80;
    int cols = 8;
    int rows = 8;
    public Board2() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.setBackground(Color.green);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw the chessboard
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((i + j) % 2 == 0) {
                    g2d.setColor(new Color(210, 81, 66));
                } else {
                    g2d.setColor(new Color(57, 47, 79));
                }
                g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
            }
        }

        /*
        // Highlight checked king
        Piece king = findKing(isWhiteToMove);
        if (king != null && checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
            g2d.setColor(Color.RED);
            g2d.fillRect(king.col * tileSize, king.row * tileSize, tileSize, tileSize);
        }

        // Highlight valid moves for the selected piece
        if (selectedPiece != null) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (isValidMove(new Move(this, selectedPiece, i, j))) {
                        g2d.setColor(new Color(39, 215, 34, 171));
                        g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                    }
                }
            }
        }

        for (int i = 0; i < pieceList.size(); i++) {
            Piece piece = pieceList.get(i);
            piece.paint(g2d);
        }


         */
    }
}