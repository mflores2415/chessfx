package com.chessfx.model;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    public enum Status {
        ONGOING,
        CHECK,
        CHECKMATE,
        STALEMATE,
        DRAW
    }

    private List<Move> moveHistory;
    private Status status;
    private Move lastMove;

    public GameState() {
        moveHistory = new ArrayList<>();
        status = Status.ONGOING;
    }

    public void addMove(Move move) {
        moveHistory.add(move);
        lastMove = move;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public List<Move> getMoveHistory() {
        return new ArrayList<>(moveHistory);
    }

    public boolean canUndo() {
        return !moveHistory.isEmpty();
    }

    public Move undoLastMove() {
        if (!moveHistory.isEmpty()) {
            return moveHistory.remove(moveHistory.size() - 1);
        }
        return null;
    }
}