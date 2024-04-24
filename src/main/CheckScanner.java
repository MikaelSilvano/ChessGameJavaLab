
//CheckScanner
package main;
import pieces.Piece;

import java.util.ArrayList;

public class CheckScanner {
    Board board;
    public CheckScanner(Board board) {
        this.board = board;
    }
    public boolean isCheck(boolean isWhite) {
        Piece king = board.findKing(isWhite);
        if (king != null) {
            Move kingMove = new Move(board, king, king.col, king.row);
            return isKingChecked(kingMove);
        }
        return false;
    }

    public boolean isCheckmate(boolean isWhite) {
        Piece king = board.findKing(isWhite);

        // Check if the king is in check
        if (isCheck(isWhite)) {
            // Generate possible moves for the king
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int newX = king.col + dx;
                    int newY = king.row + dy;
                    if (board.isValidMove(new Move(board, king, newX, newY))) {
                        // If the king can escape from check, it's not checkmate
                        return false;
                    }
                }
            }

            // Check if any piece can block the check
            for (Piece piece : board.pieceList) {
                if (piece.isWhite == isWhite && board.moveValid(piece, king.col, king.row)) {
                    // If a piece can block the check, it's not checkmate
                    return false;
                }
            }

            // Check if any piece can capture the attacking piece
            ArrayList<Piece> attackingPieces = board.getAttackingPieces(king, isWhite);
            for (Piece attackingPiece : attackingPieces) {
                for (Piece piece : board.pieceList) {
                    if (piece.isWhite == isWhite && board.isValidMove(new Move(board, piece, attackingPiece.col, attackingPiece.row))) {
                        // If a piece can capture the attacking piece, it's not checkmate
                        return false;
                    }
                }
            }

            // If none of the above conditions are met, it's checkmate
            return true;
        }

        // If the king is not in check, it's not checkmate
        return false;
    }

    public boolean isKingChecked(Move move) {
        Piece king = board.findKing(move.piece.isWhite);
        assert king != null; //cek apakah king itu tidak null

        int kingCol = king.col;
        int kingRow = king.row;

        if(board.selectedPiece != null && board.selectedPiece.name.equals("King")) {
            kingCol = move.newCol;
            kingRow = move.newRow;
        }
        return  hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 0, 1) || //up
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 1, 0) || //right
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 0, -1) || //down
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, -1, 0) || //left

                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, -1) || //up left
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, -1) || //up right
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, 1) || //down right
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, 1) || //down left

                hitByKnight(move.newCol, move.newRow, king, kingCol, kingRow) ||
                hitByPawn(move.newCol, move.newRow, king, kingCol, kingRow) ||
                hitByKing(king, kingCol, kingRow);
    }

    private boolean hitByRook(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal) {
        for(int i = 1; i < 8; i++) {
            if(kingCol + (i * colVal) == col && kingRow + (i * rowVal) == row) {
                break;
            }
            Piece piece = board.getPiece(kingCol + (i * colVal), kingRow + (i * rowVal));
            if(piece != null && piece != board.selectedPiece) {
                if(!board.sameTeam(piece, king) && (piece.name.equals("Rook") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByBishop(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal) {
        for(int i = 1; i < 8; i++) {
            if(kingCol - (i * colVal) == col && kingRow - (i * rowVal) == row) {
                break;
            }
            Piece piece = board.getPiece(kingCol - (i * colVal), kingRow - (i * rowVal));
            if(piece != null && piece != board.selectedPiece) {
                if(!board.sameTeam(piece, king) && (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByKnight(int col, int row, Piece king, int kingCol, int kingRow) {
        return checkKnight(board.getPiece(kingCol - 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow - 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow - 1), king, col, row);
    }

    private boolean checkKnight(Piece p, Piece k, int col, int row) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
    }
    private boolean hitByKing(Piece king, int kingCol, int kingRow) {
        return checkKing(board.getPiece(kingCol - 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol - 1, kingRow), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow), king) ||
                checkKing(board.getPiece(kingCol - 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol, kingRow + 1), king);
    }

    private boolean checkKing(Piece p, Piece k) {
        if(p != null && !board.sameTeam(p, k) && p.name.equals("King")) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean hitByPawn(int col, int row, Piece king, int kingCol, int kingRow) {
        int colorVal;
        if(king.isWhite) {
            colorVal = -1;
        } else {
            colorVal = 1;
        }
        if(checkPawn(board.getPiece(kingCol + 1, kingRow + colorVal), king, col, row) || checkPawn(board.getPiece(kingCol - 1, kingRow + colorVal), king, col, row)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPawn(Piece p, Piece k, int col, int row) {
        if(p != null && !board.sameTeam(p, k) && p.name.equals("Pawn") && !(p.col == col && p.row == row)) {
            return true;
        } else {
            return false;
        }
    }
}
