package com.chessfx.game;

import java.util.ArrayList;
import java.util.List;

import com.chessfx.model.Board;
import com.chessfx.model.GameState;
import com.chessfx.model.Move;
import com.chessfx.model.pieces.Piece;
import com.chessfx.util.Color;
import com.chessfx.util.Position;

public class GameStateManager {
    private GameState gameState;
    private List<String> boardStates;
    private int halfMoveClock;

    public GameStateManager() {
        this.gameState = new GameState();
        this.boardStates = new ArrayList<>();
        this.halfMoveClock = 0;
    }

    public void addBoardState(Board board, Color currentPlayerColor) {
        boardStates.add(getBoardStateString(board, currentPlayerColor));
    }

    public List<String> getBoardStates() {
        return new ArrayList<>(boardStates);
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameState.Status getStatus() {
        return gameState.getStatus();
    }

    public Move getLastMove() {
        return gameState.getLastMove();
    }

    public List<Move> getMoveHistory() {
        return gameState.getMoveHistory();
    }

    public void incrementHalfMoveClock() {
        halfMoveClock++;
    }

    public void resetHalfMoveClock() {
        halfMoveClock = 0;
    }

    public int getHalfMoveClock() {
        return this.halfMoveClock;
    }

    private String getBoardStateString(Board board, Color currentPlayerColor) {
        StringBuilder state = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(new Position(i, j));
                if (piece == null) {
                    state.append("-");
                } else {
                    state.append(piece.getColor().toString().charAt(0))
                            .append(piece.getClass().getSimpleName().charAt(0));
                }
            }
        }
        state.append(currentPlayerColor == Color.WHITE ? "w" : "b");
        return state.toString();
    }

    // Getters and state management methods
    public void addMove(Move move) {
        gameState.addMove(move);
    }

    public void setStatus(GameState.Status status) {
        gameState.setStatus(status);
    }

    public void reset() {
        gameState = new GameState();
        boardStates.clear();
        halfMoveClock = 0;
    }
}
