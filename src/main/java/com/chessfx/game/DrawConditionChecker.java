package com.chessfx.game;

import java.util.List;

import com.chessfx.model.Board;
import com.chessfx.model.pieces.Bishop;
import com.chessfx.model.pieces.King;
import com.chessfx.model.pieces.Knight;
import com.chessfx.model.pieces.Piece;

public class DrawConditionChecker {
    public boolean isInsufficientMaterial(Board board) {
        List<Piece> pieces = board.getAllPieces();
        if (pieces.size() == 2) { // King vs King
            return true;
        }
        if (pieces.size() == 3) { // King and Bishop/Knight vs King
            for (Piece piece : pieces) {
                if (!(piece instanceof King) &&
                        !(piece instanceof Bishop) &&
                        !(piece instanceof Knight)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isThreefoldRepetition(List<String> boardStates) {
        String currentState = boardStates.get(boardStates.size() - 1);
        int count = 0;
        for (String state : boardStates) {
            if (state.equals(currentState)) {
                count++;
                if (count >= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFiftyMovesRule(int halfMoveClock) {
        return halfMoveClock >= 100;
    }
}
