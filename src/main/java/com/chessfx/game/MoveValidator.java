package com.chessfx.game;

import com.chessfx.util.Color;

import java.util.List;

import com.chessfx.model.Board;
import com.chessfx.model.pieces.King;
import com.chessfx.model.pieces.Piece;
import com.chessfx.util.Position;

public class MoveValidator {
    public boolean isMoveLegal(Position from, Position to, Board board, Color playerColor) {
        Piece piece = board.getPiece(from);

        if (piece == null || piece.getColor() != playerColor) {
            return false;
        }

        if (!piece.getValidMoves(board).contains(to)) {
            return false;
        }

        return !wouldResultInCheck(from, to, board, playerColor);
    }

    private boolean wouldResultInCheck(Position from, Position to, Board board, Color playerColor) {
        // Make temporary move
        Piece piece = board.getPiece(from);
        Piece capturedPiece = board.getPiece(to);
        board.setPiece(to, piece);
        board.setPiece(from, null);

        boolean resultsInCheck = isCheck(playerColor, board);

        // Undo move
        board.setPiece(from, piece);
        board.setPiece(to, capturedPiece);

        return resultsInCheck;
    }

    public boolean isCheck(Color color, Board board) {
        Position kingPosition = findKing(color, board);
        return kingPosition != null && isPositionUnderAttack(kingPosition, color.opposite(), board);
    }

    private Position findKing(Color color, Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position pos = new Position(i, j);
                Piece piece = board.getPiece(pos);
                if (piece instanceof King && piece.getColor() == color) {
                    return pos;
                }
            }
        }
        return null;
    }

    private boolean hasLegalMoves(Color color, Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position from = new Position(i, j);
                Piece piece = board.getPiece(from);
                if (piece != null && piece.getColor() == color) {
                    List<Position> moves = piece.getValidMoves(board);
                    for (Position to : moves) {
                        if (isMoveLegal(from, to, board, color)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(Color color, Board board) {
        if (!isCheck(color, board))
            return false;
        return !hasLegalMoves(color, board);
    }

    public boolean isStalemate(Color color, Board board) {
        if (isCheck(color, board))
            return false;
        return !hasLegalMoves(color, board);
    }

    private boolean isPositionUnderAttack(Position pos, Color attackingColor, Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position from = new Position(i, j);
                Piece piece = board.getPiece(from);
                if (piece != null && piece.getColor() == attackingColor) {
                    List<Position> moves = piece.getValidMoves(board);
                    if (moves.contains(pos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}