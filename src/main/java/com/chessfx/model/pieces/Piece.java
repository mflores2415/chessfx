package com.chessfx.model.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chessfx.util.Color;
import com.chessfx.util.Position;
import com.chessfx.model.Board;

import javafx.scene.image.Image;

public abstract class Piece {
    protected Color color;
    protected Position position;
    private static String currentStyle = "chessnut";
    private Image image;

    public Piece(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    public void setPosition(Position pos) {
        this.position = pos;
    }

    public static void setStyle(String style) {
        currentStyle = style;
    }

    public void refreshImage() {
        this.image = null;
        this.image = loadImage();
    }

    public Image loadImage() {
        String colorPrefix = color == Color.WHITE ? "w" : "b";
        String piecePrefix = switch (this.getClass().getSimpleName()) {
            case "King" -> "K";
            case "Queen" -> "Q";
            case "Rook" -> "R";
            case "Bishop" -> "B";
            case "Knight" -> "N";
            case "Pawn" -> "P";
            default -> "";
        };
        String path = getClass().getResource("/pieces/" + currentStyle + "/" + colorPrefix + piecePrefix + ".png")
                .toExternalForm();
        return new Image(path);
    }

    public Image getImage() {
        if(image == null) {
            image = loadImage();
        }
        return image;
    }

    public void invalidateImage() {
        this.image = null;
    }

    public abstract List<Position> getValidMoves(Board board);

    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    protected List<Position> getSlidingMoves(Board board, int[][] directions) {
        List<Position> moves = new ArrayList<>();

        for (int[] dir : directions) {
            Position current = position;
            while (true) {
                current = new Position(current.getX() + dir[0], current.getY() + dir[1]);
                if (!board.isValidPosition(current))
                    break;

                Piece piece = board.getPiece(current);
                if (piece == null) {
                    moves.add(current);
                    continue;
                }
                if (piece.getColor() != color) {
                    moves.add(current);
                }
                break;
            }
        }
        return moves;
    }
}
