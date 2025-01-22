package com.chessfx.model;

import com.chessfx.util.Color;
import com.chessfx.util.Position;

import java.util.ArrayList;
import java.util.List;

import com.chessfx.model.pieces.*;

public class Board {
    private Square[][] squares;
    
    public Board() {
        squares = new Square[8][8];
        initializeBoard();
    }
    
    public Piece getPiece(Position pos) {
        return squares[pos.getX()][pos.getY()].getPiece();
    }

    public List<Piece> getAllPieces() {
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = getPiece(new Position(i, j));
                if (piece != null) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    public void setPiece(Position pos, Piece piece) {
        if (isValidPosition(pos)) {
        squares[pos.getX()][pos.getY()].setPiece(piece);
        if (piece != null) {
            piece.setPosition(pos);
        }
    }
    }

    public boolean isValidPosition(Position pos) {
        return pos != null && 
            pos.getX() >= 0 && pos.getX() < 8 && 
            pos.getY() >= 0 && pos.getY() < 8;
    }

    private void initializeBoard() {
        // Initialize squares
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square(new Position(i, j), null);
            }
        }

        // Place pieces
        Color black = Color.BLACK;
        Color white = Color.WHITE;

        // Black pieces
        squares[0][0].setPiece(new Rook(black, new Position(0, 0)));
        squares[1][0].setPiece(new Knight(black, new Position(1, 0)));
        squares[2][0].setPiece(new Bishop(black, new Position(2, 0)));
        squares[3][0].setPiece(new Queen(black, new Position(3, 0)));
        squares[4][0].setPiece(new King(black, new Position(4, 0)));
        squares[5][0].setPiece(new Bishop(black, new Position(5, 0)));
        squares[6][0].setPiece(new Knight(black, new Position(6, 0)));
        squares[7][0].setPiece(new Rook(black, new Position(7, 0)));

        // Black pawns
        for (int i = 0; i < 8; i++) {
            squares[i][1].setPiece(new Pawn(black, new Position(i, 1)));
        }

        // White pieces
        squares[0][7].setPiece(new Rook(white, new Position(0, 7)));
        squares[1][7].setPiece(new Knight(white, new Position(1, 7)));
        squares[2][7].setPiece(new Bishop(white, new Position(2, 7)));
        squares[3][7].setPiece(new Queen(white, new Position(3, 7)));
        squares[4][7].setPiece(new King(white, new Position(4, 7)));
        squares[5][7].setPiece(new Bishop(white, new Position(5, 7)));
        squares[6][7].setPiece(new Knight(white, new Position(6, 7)));
        squares[7][7].setPiece(new Rook(white, new Position(7, 7)));

        // White pawns
        for (int i = 0; i < 8; i++) {
            squares[i][6].setPiece(new Pawn(white, new Position(i, 6)));
        }
    } 
}
