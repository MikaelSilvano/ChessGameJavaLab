package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Board extends JPanel {
    public int tileSize = 90;
    int cols = 8;
    int rows = 8;
    ArrayList<Piece> pieceList = new ArrayList<>();
    public Piece selectedPiece;
    Input input = new Input(this);
    public CheckScanner checkScanner = new CheckScanner(this);
    public int enPassantTile = -1;
    public boolean isWhiteTurn = true;
    InputAudio promotionSound;
    InputAudio eatSound;
    private ChessPage chessPage;
   class Position {
       int col;
       int row;

       Position(int col, int row) {
           this.col = col;
           this.row = row;
       }
   }

    public Board(ChessPage chessPage) {
        this.chessPage = chessPage;
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
                return piece;
            }
        }
        return null;
    }

    public void makeMove(Move move) {
        if (move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else if (move.piece.name.equals("King")) {
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
        chessPage.switchTurn();
    }

    private void moveKing(Move move) {
        if (Math.abs(move.piece.col - move.newCol) == 2) {
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
        int colorIndex = move.piece.isWhite ? 1 : -1;
        if (getTileNum(move.newCol, move.newRow) == enPassantTile) {
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
        }
        if (Math.abs(move.piece.row - move.newRow) == 2) {
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
        } else {
            enPassantTile = -1;
        }

        //promosi
        colorIndex = move.piece.isWhite ? 0 : 7;
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
        pieceList.remove(piece);
    }

    public boolean isValidMove(Move move) {
        if (sameTeam(move.piece, move.capture)) {
            return false;
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
        return piece1.isWhite == piece2.isWhite;
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
    public boolean moveValid(Piece piece, int newCol, int newRow) {
        if (sameTeam(piece, getPiece(newCol, newRow))) {
            return false;
        }

        Move move = new Move(this, piece, newCol, newRow);

        if (!piece.isValidMovement(newCol, newRow)) {
            return false;
        }

        if (piece.moveCollidesWithPiece(newCol, newRow)) {
            return false;
        }

        return !checkScanner.isKingChecked(move);
    }
    public boolean isCheck(boolean isWhite) {
        Piece king = findKing(isWhite);
        if (king != null) {
            Move kingMove = new Move(this, king, king.col, king.row);
            return checkScanner.isKingChecked(kingMove);
        }
        return false;
    }

    public boolean isCheckmate(boolean isWhite) {
        Piece king = findKing(isWhite);

        // Check if the king is in check
        if (checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
            // Check if the king can escape from check
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int newX = king.col + dx;
                    int newY = king.row + dy;
                    if (isValidMove(new Move(this, king, newX, newY))) {
                        // If the king can escape from check, it's not checkmate
                        return false;
                    }
                }
            }

            // Check if any friendly piece can capture the attacking piece
            ArrayList<Piece> attackingPieces = getAttackingPieces(king, isWhite);
            for (Piece attackingPiece : attackingPieces) {
                for (Piece piece : pieceList) {
                    if (piece.isWhite == isWhite && isValidMove(new Move(this, piece, attackingPiece.col, attackingPiece.row))) {
                        // If a friendly piece can capture the attacking piece, it's not checkmate
                        return false;
                    }
                }
            }

            // Check if any friendly piece can block the check
            ArrayList<Piece> defendingPieces = getDefendingPieces(king, isWhite);
            for (Piece defendingPiece : defendingPieces) {
                ArrayList<Position> pathToKing = getPathToKing(defendingPiece, king);
                for (Position pos : pathToKing) {
                    for (Piece piece : pieceList) {
                        if (piece.isWhite == isWhite && isValidMove(new Move(this, piece, pos.col, pos.row))) {
                            // If a friendly piece can move in between the attacking piece and the king, it's not checkmate
                            return false;
                        }
                    }
                }
            }

            // If none of the above conditions are met, it's checkmate
            return true;
        }

        // If the king is not in check, it's not checkmate
        return false;
    }

    private ArrayList<Piece> getDefendingPieces(Piece targetPiece, boolean isTargetPieceWhite) {
        ArrayList<Piece> defendingPieces = new ArrayList<>();

        for (Piece piece : pieceList) {
            if (piece.isWhite == isTargetPieceWhite && piece.isValidMovement(targetPiece.col, targetPiece.row)) {
                // Check if the piece's movement can block the attack
                if (piece.moveCollidesWithPiece(targetPiece.col, targetPiece.row)) {
                    defendingPieces.add(piece);
                }
            }
        }

        return defendingPieces;
    }
    public ArrayList<Piece> getAttackingPieces(Piece targetPiece, boolean isTargetPieceWhite) {
        ArrayList<Piece> attackingPieces = new ArrayList<>();

        for (Piece piece : pieceList) {
            if (piece.isWhite != isTargetPieceWhite && piece.isValidMovement(targetPiece.col, targetPiece.row)) {
                // Check if the piece's movement can capture the target piece
                if (piece.moveCollidesWithPiece(targetPiece.col, targetPiece.row)) {
                    attackingPieces.add(piece);
                }
            }
        }

        return attackingPieces;
    }

    private ArrayList<Position> getPathToKing(Piece attackingPiece, Piece king) {
        ArrayList<Position> pathToKing = new ArrayList<>();
        int kingCol = king.col;
        int kingRow = king.row;
        int deltaX = Math.abs(attackingPiece.col - kingCol);
        int deltaY = Math.abs(attackingPiece.row - kingRow);

        int colDirection = Integer.compare(attackingPiece.col, kingCol);
        int rowDirection = Integer.compare(attackingPiece.row, kingRow);

        int col = attackingPiece.col;
        int row = attackingPiece.row;

        // Check if the king is in the same row or column as the attacking piece
        if (deltaX == 0) {
            for (int i = 1; i < deltaY; i++) {
                pathToKing.add(new Position(col, row + i * rowDirection));
            }
        } else if (deltaY == 0) {
            for (int i = 1; i < deltaX; i++) {
                pathToKing.add(new Position(col + i * colDirection, row));
            }
        } else if (deltaX == deltaY) { // Check if the king is in one of the diagonals of the attacking piece
            for (int i = 1; i < deltaX; i++) {
                pathToKing.add(new Position(col + i * colDirection, row + i * rowDirection));
            }
        }

        return pathToKing;
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

        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, i, 1, false));
            pieceList.add(new Pawn(this, i, 6, true));
        }

        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Queen(this, 3, 7, true));
        pieceList.add(new King(this, 4, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));
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

        // Highlight checked king
        Piece king = findKing(isWhiteTurn);
        if (king != null && checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
            g2d.setColor(Color.RED);
            g2d.fillRect(king.col * tileSize, king.row * tileSize, tileSize, tileSize);
        }

        // Highlight valid moves for the selected piece
        if (selectedPiece != null) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Move move = new Move(this, selectedPiece, i, j);
                    if (isValidMove(move)) {
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
        boolean isPlayer1InCheck = isCheck(true);
        boolean isPlayer2InCheck = isCheck(false);

        if (isPlayer1InCheck) {
            chessPage.updateCheckStatusLabel(1);
        } else {
            chessPage.clearCheckStatusLabel(1);
        }

        if (isPlayer2InCheck) {
            chessPage.updateCheckStatusLabel(2);
        } else {
            chessPage.clearCheckStatusLabel(2);
        }

        boolean isPlayer1InCheckmate = isCheckmate(true);
        boolean isPlayer2InCheckmate = isCheckmate(false);

        if (isPlayer1InCheckmate) {
            chessPage.updateCheckmateStatusLabel(1);
        } else {
            chessPage.clearCheckmateStatusLabel(1);
        }

        if (isPlayer2InCheckmate) {
            chessPage.updateCheckmateStatusLabel(2);
        } else {
            chessPage.clearCheckmateStatusLabel(2);
        }
    }
}