package com.chessfx.ui;

import com.chessfx.model.ChessGame;
import com.chessfx.model.GameState;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SettingsPanel extends VBox {
    private final String[] PIECE_STYLES = {"chessnut", "cooke", "spatial"};
    private final String[] BOARD_COLORS = {
        "#FFFFFF,#E8E8E8", // Default (White/Light Gray)
        "#FFFFFF,#769656", // Green
        "#FFFFFF,#8B4513", // Brown
        "#FFFFFF,#4682B4"  // Blue
    };
    
    public SettingsPanel(ChessBoardUI boardUI, ChessGame game) {
        setSpacing(15);
        setPadding(new Insets(10));
        
        ComboBox<String> pieceStyleCombo = new ComboBox<>();
        pieceStyleCombo.getItems().addAll(PIECE_STYLES);
        pieceStyleCombo.setValue(PIECE_STYLES[0]);
        pieceStyleCombo.setOnAction(_ -> boardUI.updatePieceStyle(pieceStyleCombo.getValue()));
        
        ComboBox<String> boardColorCombo = new ComboBox<>();
        boardColorCombo.getItems().addAll("Default", "Green", "Brown", "Blue");
        boardColorCombo.setValue("Default");
        boardColorCombo.setOnAction(_ -> {
            String colors = BOARD_COLORS[boardColorCombo.getSelectionModel().getSelectedIndex()];
            boardUI.updateBoardColors(colors.split(",")[0], colors.split(",")[1]);
        });

        Button resetButton = new Button("Reset Game");
        resetButton.setOnAction(_ -> {
            game.resetGame();
            boardUI.resetBoard();
            // Reset the game status panel
            GameStatusPanel statusPanel = boardUI.getGameStatusPanel();
            if (statusPanel != null) {
                statusPanel.resetMoveHistory();
                statusPanel.updateTurn(true);
                statusPanel.updateGameState(GameState.Status.ONGOING);
            }
        });

        getChildren().addAll(
            new VBox(5, new Label("Piece Style:"), pieceStyleCombo),
            new VBox(5, new Label("Board Color:"), boardColorCombo),
            new VBox(5, resetButton)
        );
    }
}
