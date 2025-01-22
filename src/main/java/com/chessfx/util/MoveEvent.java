package com.chessfx.util;

import com.chessfx.model.Move;
import javafx.event.Event;
import javafx.event.EventType;

public class MoveEvent extends Event {
    private static final EventType<MoveEvent> MOVE_EVENT_TYPE = new EventType<>(Event.ANY, "MOVE_EVENT");
    private final Move move;

    public MoveEvent(Move move) {
        super(MOVE_EVENT_TYPE);
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public String toString() {
        return move.toString();
    }
}