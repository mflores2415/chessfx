package com.chessfx.model;

import com.chessfx.util.Position;

import com.chessfx.model.pieces.Piece;

public class Move {
    private final Position from;
    private final Position to;
    private final Piece piece;
    private final Piece capturedPiece;
    private final boolean isCheck;
    private final boolean isCheckmate;

    public Move(Position from, Position to, Piece piece, Piece capturedPiece,
            boolean isCheck, boolean isCheckmate) {
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.isCheck = isCheck;
        this.isCheckmate = isCheckmate;
    }

    // Getters
    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public Piece getPiece() {
        return piece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public boolean isCheckmate() {
        return isCheckmate;
    }

    // Move.java
    @Override
    public String toString() {
        String from = String.format("%c%d", 'a' + this.from.getX(), 8 - this.from.getY());
        String to = String.format("%c%d", 'a' + this.to.getX(), 8 - this.to.getY());
        return piece.getClass().getSimpleName() + ": " + from + " → " + to;
    }
}