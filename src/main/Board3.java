//board
package main;

import pieces.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Board3 extends JPanel {
    public int tileSize = 100;
    int cols = 8;
    int rows = 8;
    ArrayList<Piece> pieceList = new ArrayList<>(); //buat list yang berisi semua pieces
    public Piece selectedPiece; //piece yang mau digerakan
    Input input = new Input(this);
    public CheckScanner checkScanner = new CheckScanner(this);
    public int enPassantTile = -1;
    public boolean isWhiteTurn = true;
    InputAudio promotionSound;
    InputAudio eatSound;

    public Board3() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        promotionSound = new InputAudio("src/res/PawnPromotion.wav");
        eatSound = new InputAudio("src/res/EatPieces.wav");
        addPieces();
    }

    public Piece getPiece(int col, int row) {
        for (int i = 0; i < pieceList.size(); i++) {
            Piece piece = pieceList.get(i);
            if (piece.col == col && piece.row == row) {
                return piece; //digunakan untuk dapetin suatu piece di board dan input
            }
        }
        return null;
    }

    public void makeMove(Move move) {
        if (move.piece.name.equals("Pawn")) {
            movePawn(move); //khusus movepawn
        } else if (move.piece.name.equals("King")) {
            moveKing(move); //khusus moveking
        }
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newCol * tileSize;
        move.piece.yPos = move.newRow * tileSize;
        move.piece.isFirstMove = false;

        if (move.capture != null) {
            eatSound.EatPieceSound();
        }

        capture(move.capture);
    }

    private void moveKing(Move move) {
        if (Math.abs(move.piece.col - move.newCol) == 2) { //handling castling
            Piece rook;
            if (move.piece.col < move.newCol) {
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            } else {
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }
            rook.xPos = rook.col * tileSize;
        }
    }

    private void movePawn(Move move) {
        //en passant
        int colorIndex;
        if (move.piece.isWhite) {
            colorIndex = 1;
        } else {
            colorIndex = -1;
        }
        if (getTileNum(move.newCol, move.newRow) == enPassantTile) {
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
        }
        if (Math.abs(move.piece.row - move.newRow) == 2) {
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
        } else {
            enPassantTile = -1;
        }

        //promotions
        if (move.piece.isWhite) {
            colorIndex = 0;
        } else {
            colorIndex = 7;
        }
        if (move.newRow == colorIndex) {
            promotePawn(move);
            promotionSound.PawnPromotionSound();
        }
    }

    private void promotePawn(Move move) {
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece) {
        pieceList.remove(piece); //kalau ada yang ke capture, yang piece tersebut di remove dari pieceList
    }

    public boolean isValidMove(Move move) {
        if (sameTeam(move.piece, move.capture)) {
            return false; //tidak bisa capture dari team yang sama
        }

        if (!move.piece.isValidMovement(move.newCol, move.newRow)) {
            return false;
        }

        if (move.piece.moveCollidesWithPiece(move.newCol, move.newRow)) {
            return false;
        }
        if (checkScanner.isKingChecked(move)) {
            return false;
        }

        return true;
    }

    public boolean sameTeam(Piece piece1, Piece piece2) {
        if (piece1 == null || piece2 == null) {
            return false;
        }
        if (piece1.isWhite == piece2.isWhite) {
            return true;
        } else {
            return false;
        }
    }

    public int getTileNum(int col, int row) {
        return row * rows + col + cols;
    }

    Piece findKing(boolean isWhite) {
        for (int i = 0; i < pieceList.size(); i++) {
            Piece piece = pieceList.get(i);
            if (isWhite == piece.isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }

    public void addPieces() {
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Queen(this, 3, 0, false));
        pieceList.add(new King(this, 4, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));

        pieceList.add(new Pawn(this, 0, 1, false));
        pieceList.add(new Pawn(this, 1, 1, false));
        pieceList.add(new Pawn(this, 2, 1, false));
        pieceList.add(new Pawn(this, 3, 1, false));
        pieceList.add(new Pawn(this, 4, 1, false));
        pieceList.add(new Pawn(this, 5, 1, false));
        pieceList.add(new Pawn(this, 6, 1, false));
        pieceList.add(new Pawn(this, 7, 1, false));

        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Queen(this, 3, 7, true));
        pieceList.add(new King(this, 4, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));

        pieceList.add(new Pawn(this, 0, 6, true));
        pieceList.add(new Pawn(this, 1, 6, true));
        pieceList.add(new Pawn(this, 2, 6, true));
        pieceList.add(new Pawn(this, 3, 6, true));
        pieceList.add(new Pawn(this, 4, 6, true));
        pieceList.add(new Pawn(this, 5, 6, true));
        pieceList.add(new Pawn(this, 6, 6, true));
        pieceList.add(new Pawn(this, 7, 6, true));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; //cast g ke g2d
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((i + j) % 2 == 0) {
                    g2d.setColor(new Color(240, 217, 181));
                    g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                } else {
                    g2d.setColor(new Color(181, 136, 99));
                    g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                }
            }
        }

        Piece king = findKing(isWhiteTurn); //kondisi jika king kena hit maka board display akan berwarna merah
        if (king != null && checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
            int kingX = king.col * tileSize;
            int kingY = king.row * tileSize;
            g2d.setColor(new Color(255, 0, 0));
            g2d.fillRect(kingX, kingY, tileSize, tileSize);
        }


        if (selectedPiece != null)
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (isValidMove(new Move(this, selectedPiece, i, j))) {
                        g2d.setColor(new Color(39, 215, 34, 171)); //ngecek apakah piece itu bisa bergerak (valid) (di loop)
                        g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                    }
                }
            }

        for (int i = 0; i < pieceList.size(); i++) {
            Piece piece = pieceList.get(i);
            piece.paint(g2d); //untuk semua piece yang ada di piece list, kita tambahkan ke board (paint)
        }
    }

    public void setDifficulty(String difficulty) {
    }

    public void setSinglePlayer(boolean isSinglePlayer) {
    }
}