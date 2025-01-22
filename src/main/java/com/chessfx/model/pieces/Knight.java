package com.chessfx.model.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chessfx.util.Color;
import com.chessfx.util.Position;
import com.chessfx.model.Board;

public class Knight extends Piece {
    private static final int[][] MOVES = {
        {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
        {-1, -2}, {1, -2}, {-1, 2}, {1, 2}
    };

    public Knight(Color color, Position position) {
        super(color, position);
    }

    public List<Position> getValidMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        
        for (int[] offset : MOVES) {
            Position newPos = new Position(
                position.getX() + offset[0],
                position.getY() + offset[1]
            );
            
            if (board.isValidPosition(newPos)) {
                Piece target = board.getPiece(newPos);
                if (target == null || target.getColor() != color) {
                    moves.add(newPos);
                }
            }
        }
        return moves;
    }
}
