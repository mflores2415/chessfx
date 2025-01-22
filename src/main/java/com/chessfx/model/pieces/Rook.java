package com.chessfx.model.pieces;

import java.util.List;

import com.chessfx.util.Color;
import com.chessfx.util.Position;
import com.chessfx.model.Board;

public class Rook extends Piece {
    private static final int[][] DIRECTIONS = {{0,1}, {0,-1}, {1,0}, {-1,0}};

    public Rook(Color color, Position position) {
        super(color, position);
    }

    public List<Position> getValidMoves(Board board) {
        return getSlidingMoves(board, DIRECTIONS);
    }
}
