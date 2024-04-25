package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public int tileSize = 85;

    int cols = 8;
    int rows = 8;

    ArrayList<Piece> pieceList = new ArrayList<>(); //buat list semua pieces

    public Piece selectedPiece; //piece yang mau digerakan

    Input input = new Input(this);

    public CheckScanner checkScanner = new CheckScanner(this);

    public int enPassantTile = -1;
    public boolean isWhiteToMove = true;
    public boolean isGameOver = false;
    public boolean isWhiteTurn = true;
    InputAudio promotionSound;
    InputAudio eatSound;
    private ChessPage chessPage;


    public Board() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 5));
        promotionSound = new InputAudio("src/res/PawnPromotion.wav");
        eatSound = new InputAudio("src/res/EatPieces.wav");
        addPieces();
    }

    public Piece getPiece(int col, int row) {
        for(Piece piece: pieceList) {
            if(piece.col == col && piece.row == row) {
                return piece; //digunakan untuk dapetin piece di board dan input
            }
        }
        return null;
    }
    public void makeMove(Move move) {
        if(move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else if(move.piece.name.equals("King")) {
            moveKing(move);
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

        isWhiteToMove = !isWhiteToMove;
        updateGameState();

    }
    public void updateGameState() {
        Piece king = findKing(isWhiteToMove);
        if(checkScanner.isGameOver(king)) {
            if(checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
                System.out.println("win");
            }
            else {
                System.out.println("Stalemate");
            }
        }
    }

    private void moveKing(Move move) {
        if(Math.abs(move.piece.col - move.newCol) == 2) {
            Piece rook;
            if(move.piece.col < move.newCol) {
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

        int colorIndex = move.piece.isWhite ? 1 : -1;
        if(getTileNum(move.newCol, move.newRow) == enPassantTile) {
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
        }
        if(Math.abs(move.piece.row - move.newRow) == 2) {
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
        }
        else {
            enPassantTile = -1;
        }

        //promotions
        colorIndex = move.piece.isWhite ? 0 : 7;
        if(move.newRow == colorIndex) {
            promotePawn(move);
        }
    }

    private void promotePawn(Move move) {
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece) {
        pieceList.remove(piece);
    }
    public boolean isValidMove(Move move) {
        if(sameTeam(move.piece, move.capture)) {
            return false; //tidak bisa capture team yang sama
        }

        if(!move.piece.isValidMovement(move.newCol, move.newRow)) {
            return false;
        }

        if(move.piece.moveCollidesWithPiece(move.newCol, move.newRow)) {
            return false;
        }
        if(checkScanner.isKingChecked(move)) {
            return false;
        }

        return true;
    }

    public boolean sameTeam(Piece piece1, Piece piece2) {
        if(piece1 == null || piece2 == null) {
            return false;
        }
        return piece1.isWhite == piece2.isWhite;
    }
    public int getTileNum(int col, int row) {
        return row * rows + col + cols;
    }

    Piece findKing(boolean isWhite) {
        for(Piece piece : pieceList) {
            if(isWhite == piece.isWhite && piece.name.equals("King")) {
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
        Graphics2D g2d = (Graphics2D) g; //cast g to g2d

        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((i + j) % 2 == 0) {
                    g2d.setColor(new Color(210, 81, 66));
                    g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                } else {
                    g2d.setColor(new Color(57, 47, 79));
                    g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                }
            }
        }
        Piece king = findKing(isWhiteToMove);
        if (king != null && checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
            g2d.setColor(Color.RED);
            g2d.fillRect(king.col * tileSize, king.row * tileSize, tileSize, tileSize);
        }

        if(selectedPiece != null)
            for(int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (isValidMove(new Move(this, selectedPiece, i, j))) {
                        g2d.setColor(new Color(39, 215, 34, 171)); //ngecek apakah piece itu bisa bergerak (valid) (di loop)
                        g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                    }
                }
            }

        for(Piece piece : pieceList) { //untuk semua piece yang ada di piece list, kita tambahkan ke board (paint)
            piece.paint(g2d);
        }
    }
}
