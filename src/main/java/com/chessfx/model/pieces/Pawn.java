package com.chessfx.model.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chessfx.util.Color;
import com.chessfx.util.Position;
import com.chessfx.model.Board;

public class Pawn extends Piece {
    private boolean hasMoved = false;

    public Pawn(Color color, Position position) {
        super(color, position);
    }

    public List<Position> getValidMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int direction = (color == Color.WHITE) ? -1 : 1;
        Position forward = new Position(position.getX(), position.getY() + direction);

        // Forward move
        if (board.isValidPosition(forward) && board.getPiece(forward) == null) {
            moves.add(forward);
            // Initial two-square move
            if (!hasMoved) {
                Position twoForward = new Position(position.getX(), position.getY() + 2 * direction);
                if (board.getPiece(twoForward) == null) {
                    moves.add(twoForward);
                }
            }
        }

        // Captures
        Position[] captures = {
                new Position(position.getX() - 1, position.getY() + direction),
                new Position(position.getX() + 1, position.getY() + direction)
        };

        for (Position capture : captures) {
            if (board.isValidPosition(capture)) {
                Piece target = board.getPiece(capture);
                if (target != null && target.getColor() != color) {
                    moves.add(capture);
                }
            }
        }

        return moves;
    }
}
