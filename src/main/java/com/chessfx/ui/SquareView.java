package com.chessfx.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.chessfx.model.Square;
import com.chessfx.model.pieces.Piece;

public class SquareView extends StackPane {
    private Square square;
    private Rectangle background;
    private ImageView pieceView;
    private Color lightColor;
    private Color darkColor;
    private Color highlightColor = Color.YELLOW;

    public SquareView(Square square) {
        this.square = square;
        background = new Rectangle(60, 60);
        pieceView = new ImageView();
        pieceView.setFitWidth(50);
        pieceView.setFitHeight(50);

        getChildren().addAll(background, pieceView);
    }

    public void setColors(String light, String dark) {
        this.lightColor = Color.web(light);
        this.darkColor = Color.web(dark);
        if (!background.getFill().equals(highlightColor)) {  // Only update if not highlighted
            background.setFill(isLightSquare() ? lightColor : darkColor);
        }
    }

    private boolean isLightSquare() {
        return (square.getPosition().getX() + square.getPosition().getY()) % 2 == 0;
    }
    
    public void updatePieceDisplay(Piece piece) {
        if (piece != null) {
            Image image = piece.getImage();
            if (image != null && !image.isError()) {
                pieceView.setImage(image);
                pieceView.setVisible(true);
            } else {
                pieceView.setVisible(false);
            }
        } else {
            pieceView.setImage(null);
            pieceView.setVisible(false);
        }
    }

    public void highlight() {
        background.setFill(highlightColor);
    }

    public void unhighlight() {
        background.setFill(isLightSquare() ? lightColor : darkColor);
    }
}