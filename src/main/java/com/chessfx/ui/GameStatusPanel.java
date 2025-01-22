package com.chessfx.ui;

import com.chessfx.model.GameState;
import com.chessfx.model.Move;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class GameStatusPanel extends VBox {
    private Label turnLabel;
    private Label gameStateLabel;
    private ListView<String> moveHistoryList;
    private int moveCount = 0;

    public GameStatusPanel() {
        setSpacing(10);
        setPadding(new Insets(10));
        setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc;");

        turnLabel = new Label("Current Turn: White");
        gameStateLabel = new Label("Game Status: Active");

        Label historyLabel = new Label("Move History:");
        moveHistoryList = new ListView<>();
        moveHistoryList.setPrefHeight(200);
        moveHistoryList.setStyle("-fx-font-family: monospace;");

        getChildren().addAll(
                turnLabel,
                gameStateLabel,
                historyLabel,
                moveHistoryList);
    }

    public void updateGameState(GameState.Status status) {
        gameStateLabel.setText("Game Status: " + status.toString());
    }

    public void updateTurn(boolean isWhiteTurn) {
        turnLabel.setText("Current Turn: " + (isWhiteTurn ? "White" : "Black"));
    }

    public void addMove(Move move) {
        moveCount++;
        String moveNotation = formatMoveNotation(move);
        int turnNumber = (moveCount + 1) / 2;

        if (moveCount % 2 == 1) {
            // White's move - create new line
            moveHistoryList.getItems().add(String.format("%d. %s", turnNumber, moveNotation));
        } else {
            // Black's move - append to previous line
            int lastIndex = moveHistoryList.getItems().size() - 1;
            if (lastIndex >= 0) {
                String currentLine = moveHistoryList.getItems().get(lastIndex);
                String withBlackMove = String.format("%-20s    %s", currentLine, moveNotation);
                moveHistoryList.getItems().set(lastIndex, withBlackMove);
            }
        }
        moveHistoryList.scrollTo(moveHistoryList.getItems().size() - 1);
    }

    private String formatMoveNotation(Move move) {
        String pieceSymbol = switch (move.getPiece().getClass().getSimpleName()) {
            case "King" -> "K";
            case "Queen" -> "Q";
            case "Rook" -> "R";
            case "Bishop" -> "B";
            case "Knight" -> "N";
            case "Pawn" -> "";
            default -> "";
        };

        String from = String.format("%c%d",
                'a' + move.getFrom().getX(),
                8 - move.getFrom().getY());
        String to = String.format("%c%d",
                'a' + move.getTo().getX(),
                8 - move.getTo().getY());

        StringBuilder notation = new StringBuilder();
        notation.append(pieceSymbol);

        if (move.getCapturedPiece() != null) {
            if (pieceSymbol.isEmpty()) {
                notation.append(from.charAt(0));
            }
            notation.append("x");
        }

        notation.append(to);

        if (move.isCheckmate()) {
            notation.append("#");
        } else if (move.isCheck()) {
            notation.append("+");
        }

        return notation.toString();
    }

    public void resetMoveHistory() {
        moveCount = 0;
        moveHistoryList.getItems().clear();
    }
}