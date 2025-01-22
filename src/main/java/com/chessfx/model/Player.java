package com.chessfx.model;

import java.util.ArrayList;
import java.util.List;

import com.chessfx.util.Color;

import com.chessfx.model.pieces.Piece;

public class Player {
    private Color color;
    private List<Piece> capturedPieces;

    public Player(Color color) {
        this.color = color;
        this.capturedPieces = new ArrayList<>();
    }

    public void addCapturedPiece(Piece piece) {
        capturedPieces.add(piece);
    }

    public Color getColor() {
        return color;
    }
}