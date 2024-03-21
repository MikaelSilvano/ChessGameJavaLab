package com.chess.engine.board;
import com.chess.engine.pieces.Piece;
//import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile { //abstract = we cannot instantiate this class (new)
    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles(); //declare new member field

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return null;//ImmutableMap.copyOf(emptyTileMap); //map is a container. after construct the empty tile map, jangan ada yang ganti. every possible tile is created at front so we dont need to make it anymore later
    }

    private Tile(final int tileCoordinate) { //constructor
        this.tileCoordinate = tileCoordinate; //constructor untuk assign tile coor apa yang di pass
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece) { //method untuk dapat membuat tile
        if (piece != null) {
            return new OccupiedTile(tileCoordinate, piece);
        }
        else {
            return EMPTY_TILE_CACHE.get(tileCoordinate);
        }
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece(); //retrieve piece di tile

    public static final class EmptyTile extends Tile { //child class untuk buat tile yang empty
        private EmptyTile(final int coordinate) {
            super(coordinate); //panggil superclass constructor
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile { //child class jika ada tile yang occupied di boardnya
        private final Piece pieceOnTile; //harus di call get piece dulu baru bisa access
        private OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
