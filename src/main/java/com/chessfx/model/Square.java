package com.chessfx.model;

import com.chessfx.model.pieces.Piece;
import com.chessfx.util.Position;
import com.chessfx.util.Color;

public class Square {
    private Position position;
    private Piece piece;
    private Color color;
    
    public Square(Position position, Color color) {
        this.position = position;
        this.color = color;
    }
    
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
    public Position getPosition() { return position; }
    public Color getColor() { return color; }
}