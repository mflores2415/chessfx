package com.chessfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.chessfx.model.ChessGame;
import com.chessfx.ui.ChessBoardUI;
import com.chessfx.ui.GameStatusPanel;
import com.chessfx.ui.SettingsPanel;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ChessGame game = new ChessGame();
        GameStatusPanel statusPanel = new GameStatusPanel();
        ChessBoardUI boardUI = new ChessBoardUI(game, statusPanel); // Pass statusPanel here
        SettingsPanel settingsPanel = new SettingsPanel(boardUI, game);

        ScrollPane scrollPane = new ScrollPane(statusPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(400);

        HBox layout = new HBox(10);
        VBox leftSide = new VBox(10, scrollPane);
        VBox rightSide = new VBox(10, boardUI, settingsPanel);
        layout.getChildren().addAll(leftSide, rightSide);

        boardUI.setOnMoveMade((moveEvent) -> {
            statusPanel.updateTurn(game.isWhiteTurn());
            statusPanel.addMove(moveEvent.getMove()); // Changed to use addMove
            statusPanel.updateGameState(game.getGameStatus());
        });

        Scene scene = new Scene(layout);
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}