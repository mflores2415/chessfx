package com.chessfx.model.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chessfx.util.Color;
import com.chessfx.util.Position;
import com.chessfx.model.Board;

public class King extends Piece {

    public King(Color color, Position position) {
        super(color, position);
    }

    public List<Position> getValidMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {
                { -1, -1 }, { -1, 0 }, { -1, 1 },
                { 0, -1 }, { 0, 1 },
                { 1, -1 }, { 1, 0 }, { 1, 1 }
        };

        for (int[] dir : directions) {
            Position newPos = new Position(
                    position.getX() + dir[0],
                    position.getY() + dir[1]);

            if (board.isValidPosition(newPos)) {
                Piece piece = board.getPiece(newPos);
                if (piece == null || piece.getColor() != color) {
                    moves.add(newPos);
                }
            }
        }

        // TODO: Add castling logic
        return moves;
    }
}
