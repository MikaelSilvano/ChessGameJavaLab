// WinConditionChecker.java
package main;

import pieces.Piece;

public class WinConditionChecker {

    public static boolean isWin(Board3 board, boolean isWhiteTurn) {
        CheckScanner scanner = new CheckScanner(board); // Create local variable scanner
        return scanner.isKingChecked(isWhiteTurn) || isKingStuck(board, isWhiteTurn);
    }

    private static boolean isKingStuck(Board3 board, boolean isWhiteTurn) {
        CheckScanner scanner = new CheckScanner(board); // Create local variable scanner
        Piece king = board.findKing(isWhiteTurn);
        if (king == null) {
            return false; // King not found, game continues
        }

        int kingX = king.col;
        int kingY = king.row;

        // Check if the king cannot move
        for (Move move : king.isValidMovement(board, kingX, kingY)) {
            Board3 testBoard = new Board3(board);
            testBoard.makeMove(move);
            if (!scanner.isKingChecked(isWhiteTurn)) {
                return false; // King has a valid move, game continues
            }
        }

        // King cannot move, it's a win for the opponent
        return true;
    }
}
