package com.chess.engine.pieces;
import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance pieceAlliance; //alliance pakai enum. berguna untuk pieces dan player
    Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
    }
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }
    public abstract Collection<Move> calculateLegalMoves(final Board board); //return collection (unspecified list) of legal moves. all of the pieces going to be created going to override with their own behavior
}
