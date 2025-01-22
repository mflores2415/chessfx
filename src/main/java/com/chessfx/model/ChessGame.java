package com.chessfx.model;

import com.chessfx.util.Color;
import com.chessfx.util.Position;
import com.chessfx.game.DrawConditionChecker;
import com.chessfx.game.GameStateManager;
import com.chessfx.game.MoveValidator;
import com.chessfx.model.pieces.*;

public class ChessGame {
    private Board board;
    private Player currentPlayer;
    private Player otherPlayer;
    private GameStateManager gameStateManager;
    private MoveValidator moveValidator;
    private DrawConditionChecker drawChecker;

    public ChessGame() {
        initializeGame();
    }

    private void initializeGame() {
        board = new Board();
        currentPlayer = new Player(Color.WHITE);
        otherPlayer = new Player(Color.BLACK);
        gameStateManager = new GameStateManager();
        moveValidator = new MoveValidator();
        drawChecker = new DrawConditionChecker();
        gameStateManager.addBoardState(board, currentPlayer.getColor());
    }

    public void resetGame() {
        initializeGame();
    }

    public boolean movePiece(Position from, Position to) {
        if (!moveValidator.isMoveLegal(from, to, board, currentPlayer.getColor())) {
            return false;
        }

        Piece piece = board.getPiece(from);
        // Update fifty-move rule counter
        if (piece instanceof Pawn || board.getPiece(to) != null) {
            gameStateManager.resetHalfMoveClock();
        } else {
            gameStateManager.incrementHalfMoveClock();
        }

        // Execute move
        executeMoveAndUpdateState(from, to);

        return true;
    }

    private void executeMoveAndUpdateState(Position from, Position to) {
        Piece piece = board.getPiece(from);
        Piece capturedPiece = board.getPiece(to);

        // Execute move
        if (capturedPiece != null) {
            currentPlayer.addCapturedPiece(capturedPiece);
        }
        board.setPiece(to, piece);
        board.setPiece(from, null);

        // Update game state
        gameStateManager.addBoardState(board, currentPlayer.getColor());
        updateGameStatus(from, to, piece, capturedPiece);
        switchPlayer();
    }

    public void setPieceStyle(String style) {
        Piece.setStyle(style);
        board.getAllPieces().forEach(Piece::invalidateImage);
    }

    private void switchPlayer() {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }
    

    private void updateGameStatus(Position from, Position to, Piece piece, Piece capturedPiece) {
        Color oppositeColor = otherPlayer.getColor();

        boolean isCheck = moveValidator.isCheck(oppositeColor, board);
        boolean isCheckmate = moveValidator.isCheckmate(oppositeColor, board);
        boolean isStalemate = moveValidator.isStalemate(oppositeColor, board);
        boolean isDraw = drawChecker.isInsufficientMaterial(board) ||
                drawChecker.isThreefoldRepetition(gameStateManager.getBoardStates()) ||
                drawChecker.isFiftyMovesRule(gameStateManager.getHalfMoveClock());

        Move lastMove = new Move(from, to, piece, capturedPiece, isCheck, isCheckmate);
        gameStateManager.addMove(lastMove);

        if (isCheckmate) {
            gameStateManager.setStatus(GameState.Status.CHECKMATE);
        } else if (isStalemate) {
            gameStateManager.setStatus(GameState.Status.STALEMATE);
        } else if (isDraw) {
            gameStateManager.setStatus(GameState.Status.DRAW);
        } else if (isCheck) {
            gameStateManager.setStatus(GameState.Status.CHECK);
        }
    }

    public GameState.Status getGameStatus() {
        return gameStateManager.getStatus();
    }

    public boolean isWhiteTurn() {
        return currentPlayer.getColor() == Color.WHITE;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public Move getLastMove() {
        return gameStateManager.getLastMove();
    }
}