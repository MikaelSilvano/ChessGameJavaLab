package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Board extends JPanel {
    public int tileSize = 80;
    int cols = 8;
    int rows = 8;
    ArrayList<Piece> pieceList = new ArrayList<>();
    public Piece selectedPiece;
    Input input = new Input(this);
    public CheckScanner checkScanner = new CheckScanner(this);
    public int enPassantTile = -1;
    public boolean isWhiteToMove = true;
    public boolean isGameOver = false;
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

    public Board() {
        //this.chessPage = chessPage;
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 5));
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
        isWhiteToMove = !isWhiteToMove;
        updateGameState();
        //chessPage.switchTurn();
    }
    private void updateGameState() {
        Piece king = findKing(isWhiteToMove);
        if(checkScanner.isGameOver(king)) {
            if(checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
                System.out.println(isWhiteToMove ? "Black wins" : "white wins");
            }
            else {
                System.out.println("stalemate");
            }
        }
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
    private boolean isCheckmate() {
        Piece king = findKing(isWhiteTurn);
        if (kingCanMove(king)) {
            System.out.println("king can move");
            return false;
        } else if(!kingCanMove(king)){
            System.out.println("king cannot move");
            for (Piece piece : pieceList) {
                System.out.println("piece di board");
                if (piece.isWhite == isWhiteTurn) {
                    System.out.println("same team");
                    for (int col = 0; col < cols; col++) {
                        for (int row = 0; row < rows; row++) {
                            if (moveValid(piece, col, row)) {
                                System.out.println("move valid");
                                int prevCol = piece.col;
                                int prevRow = piece.row;
                                piece.col = col;
                                piece.row = row;
                                if (!checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
                                    piece.col = prevCol;
                                    piece.row = prevRow;
                                    System.out.println("kingnya masih bisa gerak lagi");
                                    return false;
                                }
                                piece.col = prevCol;
                                piece.row = prevRow;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("checkmate false");
        return true;
    }
    private boolean kingCanMove(Piece king) {
        for (int col = king.col - 1; col <= king.col + 1; col++) {
            for (int row = king.row - 1; row <= king.row + 1; row++) {
                if (isValidMove(new Move(this, king, col, row)) && !checkScanner.isKingChecked(new Move(this, king, col, row))) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
        private boolean isCheckmate() {
            Piece king = getKing(true);
            if(kingCanMove(king)) {
                return false;
            }
            else {
                //check if you can block the attack with your piece
                //check the position of the checking piece and the king in check
                int colDiff = Math.abs(checkingP.col-king,col);
                int rowDiff = Math.abs(checkingP.row-king,row);
                if(colDiff = 0) {
                    //the checking piece is attacking vertically
                    if(checkingP.row < king.row) {
                        //the checking piece is above the king
                        for(int row = checkingP.row; row < king.row; row++) {
                            for(Piece piece : simPieces) {
                                if(piece != king && piece.color != currentColor && piece.canMove(checkingP.col, row)) {
                                    return false;
                                }
                            }
                        }
                    }
                    if(checkingP.row > king.row) {
                        //the checking piece is above the king
                        //the checking piece is attacking horizontaly
                        for(int row = checkingP.row; row > king.row; row--) {
                            for(Piece piece : simPieces) {
                                if(piece != king && piece.color != currentColor && piece.canMove(checkingP.col, row)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                else if(rowDiff == 0) {
                    //the checking piece is attacking horizontally
                    if(checkingP.col < king.col) {
                        //the checking piece is to the left
                        for(int col = checkingP.col; col < king.col; col++) {
                            for(Piece piece : simPieces) {
                                if(piece != king && piece.color != currentColor && piece.canMove(col, checkingP.row)) {
                                    return false;
                                }
                            }
                        }
                    }
                    if(checkingP.col > king.col) {
                        //the checking piece is to the right
                        if(checkingP.col < king.col) {
                            for(int col = checkingP.col; col > king.col; col--) {
                                for(Piece piece : simPieces) {
                                    if(piece != king && piece.color != currentColor && piece.canMove(col, checkingP.row)) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
                else if(colDiff == rowDiff) {
                    //the checking piece is attacking diagonally
                    if(checkingP.row < king.row) {
                        //the checking piece is above the king
                        if(checkingP.col < king.col) {
                            //the checking piece is in the upper left
                            for(int col = checkingP.col, row = checkingP.row; col < king.col; col++, row++) {
                                for(Piece piece : simPieces) {
                                    if(piece != king && piece.color != currentColor && piece.canMove(col, row)) {
                                        return false;
                                    }
                                }
                            }
                        }
                        if(checkingP.col > king.col) {
                            //the checking piece is in the upper left
                            for(int col = checkingP.col, row = checkingP.row; col > king.col; col--, row++) {
                                for(Piece piece : simPieces) {
                                    if(piece != king && piece.color != currentColor && piece.canMove(col, row)) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                    if(checkingP.row > king.row) {
                        //the checking piece is above the king
                        if(checkingP.col < king.col) {
                            //the checking piece is in the lower left
                            for(int col = checkingP.col, row = checkingP.row; col < king.col; col++, row--) {
                                for(Piece piece : simPieces) {
                                    if(piece != king && piece.color != currentColor && piece.canMove(col, row)) {
                                        return false;
                                    }
                                }
                            }
                        }
                        if(checkingP.col > king.col) {
                            //the checking piece is in the lower right
                            for(int col = checkingP.col, row = checkingP.row; col > king.col; col--, row--) {
                                for(Piece piece : simPieces) {
                                    if(piece != king && piece.color != currentColor && piece.canMove(col, row)) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        private boolean kingCanMove(Piece king) {
            if(isValidMove(king, -1, -1)) {
                return true;
            }
            if(isValidMove(king, 0, -1)) {
                return true;
            }
            if(isValidMove(king, 1, -1)) {
                return true;
            }
            if(isValidMove(king, -1, 0)) {
                return true;
            }
            if(isValidMove(king, 1, 0)) {
                return true;
            }
            if(isValidMove(king, -1, 1)) {
                return true;
            }
            if(isValidMove(king, 0, 1)) {
                return true;
            }
            if(isValidMove(king, 1, 1)) {
                return true;
            }
            return false;
        }


     */
    public void CheckmateChecker() {
        boolean test = isCheckmate();
        if(test) {
            System.out.println("Checkmate");
        }
        else {
            System.out.println("No");
        }
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

        /*
        // Highlight checked king
        Piece king = findKing(isWhiteToMove);
        if (king != null && checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
            g2d.setColor(Color.RED);
            g2d.fillRect(king.col * tileSize, king.row * tileSize, tileSize, tileSize);
        }

         */

        // Highlight valid moves for the selected piece
        if (selectedPiece != null) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Move move = new Move(this, selectedPiece, i, j);
                    if (isValidMove(move)) {
                        g2d.setColor(new Color(39, 215, 34, 171));
                        g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                        //chessPage.clearCheckStatus();
                    }
                }
            }
        }

        for (int i = 0; i < pieceList.size(); i++) {
            Piece piece = pieceList.get(i);
            piece.paint(g2d);
        }
    }
}