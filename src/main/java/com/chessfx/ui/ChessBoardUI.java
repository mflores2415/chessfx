package com.chessfx.ui;

import com.chessfx.util.MoveEvent;
import com.chessfx.util.Position;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import com.chessfx.model.Board;
import com.chessfx.model.ChessGame;
import com.chessfx.model.Move;
import com.chessfx.model.pieces.Piece;
import com.chessfx.model.Square;

public class ChessBoardUI extends GridPane {
    private ChessGame game;
    private Board board;
    private SquareView[][] squareViews;
    private Position selectedPosition;
    private EventHandler<MoveEvent> onMoveMade;
    private GameStatusPanel gameStatusPanel;
    private String currentLightColor = "#FFFFFF";
    private String currentDarkColor = "#E8E8E8";

    public ChessBoardUI(ChessGame game, GameStatusPanel gameStatusPanel) {
        initialize(game, gameStatusPanel);
    }

    private void initialize(ChessGame game, GameStatusPanel gameStatusPanel) {
        this.game = game;
        this.board = game.getBoard();
        this.gameStatusPanel = gameStatusPanel;
        this.selectedPosition = null;
        initializeUI();
    }

    public void resetBoard() {
        // Store current colors
        String lightColor = currentLightColor;
        String darkColor = currentDarkColor;

        // Reset game state and UI
        initialize(game, gameStatusPanel);

        // Restore colors if they're not default
        if (!lightColor.equals("#FFFFFF") || !darkColor.equals("#E8E8E8")) {
            updateBoardColors(lightColor, darkColor);
        }
    }

    public void updatePieceStyle(String style) {
        game.setPieceStyle(style);
        updateBoard();
    }

    public void setOnMoveMade(EventHandler<MoveEvent> handler) {
        this.onMoveMade = handler;
    }

    private void initializeUI() {
        squareViews = new SquareView[8][8];

        setHgap(2);
        setVgap(2);

        // Add rank numbers (1-8)
        for (int i = 0; i < 8; i++) {
            Label rankLabel = new Label(String.valueOf(8 - i));
            rankLabel.setMinWidth(20);
            rankLabel.setAlignment(Pos.CENTER);
            rankLabel.setStyle("-fx-padding: 5;");
            add(rankLabel, 0, i);
        }

        // Add file letters (a-h)
        for (int i = 0; i < 8; i++) {
            Label fileLabel = new Label(String.valueOf((char) ('A' + i)));
            fileLabel.setMinHeight(20);
            fileLabel.setAlignment(Pos.CENTER);
            fileLabel.setStyle("-fx-padding: 5;");
            add(fileLabel, i + 1, 8);
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position pos = new Position(i, j);
                SquareView squareView = new SquareView(new Square(pos, null));
                squareViews[i][j] = squareView;
                squareView.setColors(currentLightColor, currentDarkColor);
                squareView.setOnMouseClicked(_ -> handleSquareClick(pos));
                add(squareView, i + 1, j); // Offset by 1 to account for rank labels
            }
        }
        updateBoard();
    }

    private void handleSquareClick(Position pos) {
        if (selectedPosition == null) {
            Piece piece = board.getPiece(pos);
            if (piece != null && piece.getColor() == game.getCurrentPlayer().getColor()) {
                selectedPosition = pos;
                highlightValidMoves(piece);
            }
        } else {
            if (game.movePiece(selectedPosition, pos)) {
                updateBoard();
                Move lastMove = game.getLastMove();
                if (onMoveMade != null) {
                    onMoveMade.handle(new MoveEvent(lastMove));
                }
            }
            clearHighlights();
            selectedPosition = null;
        }
    }

    private void highlightValidMoves(Piece piece) {
        for (Position pos : piece.getValidMoves(board)) {
            squareViews[pos.getX()][pos.getY()].highlight();
        }
    }

    private void clearHighlights() {
        for (SquareView[] row : squareViews) {
            for (SquareView square : row) {
                square.unhighlight();
            }
        }
    }

    public void updateBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position pos = new Position(i, j);
                Piece piece = board.getPiece(pos);
                squareViews[i][j].updatePieceDisplay(piece);
            }
        }
    }

    public void updateBoardColors(String lightColor, String darkColor) {
        this.currentLightColor = lightColor;
        this.currentDarkColor = darkColor;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squareViews[i][j].setColors(lightColor, darkColor);
            }
        }
    }

    public GameStatusPanel getGameStatusPanel() {
        return gameStatusPanel;
    }
}