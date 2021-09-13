package controller.game;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import media.AudioManager;
import model.Action;
import model.player.LocalPlayer;
import model.player.Player;
import model.Color;
import model.state.StateDescription;
import model.state.QueryableBoardState;
import struct.FirstInFirstOutStack;
import struct.LastInFirstOutStack;
import ui.Square;
import ui.adapter.ChessPieceAdapter;
import ui.builder.DialogBuilder;
import ui.BoardPane;

import java.util.*;

/*
Todo: everything but the EventHandlers need to be reimplemented alongside the BoardPane.
 */
public abstract class GameController
{
    protected static final MouseButton GAME_BUTTON = MouseButton.PRIMARY;

    protected final FirstInFirstOutStack<Player> players = new FirstInFirstOutStack<Player>();

    protected final LastInFirstOutStack<QueryableBoardState> states = new LastInFirstOutStack<>();

    @FXML
    protected BoardPane board;

    @FXML
    protected Pane root;

    public void initialize() {
        board.setOnSquareDragDetected(this::onDragDetected);
        board.setOnSquareDragDone(this::onDragDone);
        board.setOnSquareDragDropped(this::onDragDropped);
        board.setOnSquareDragOver(this::onDragOver);
        states.push(board.toQueryableBoardState());
    }

    private void onDragDetected(final MouseEvent event) {
        if ((event == null) || (event.getButton() != GAME_BUTTON) ||
                (players.peek() == null) || (players.peek().getClass() != LocalPlayer.class)) {
            return;
        }

        @SuppressWarnings("unchecked")
        Square<ChessPieceAdapter> square = (Square<ChessPieceAdapter>) event.getSource();

        if (square.hasElement() &&
                square.getElement().getChessPiece().getColor() == players.peek().getColor()) {

            if ((board.isNotQueued()) || (board.getQueued().equals(square))) {
                board.queue(square);
            }

            Dragboard dragboard = square.getElement().startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            dragboard.setDragView(square.getElement().getImage());
            content.putString(square.getElement().getChessPiece().toString());
            dragboard.setContent(content);
        }
        event.consume();
    }

    private void onDragOver(final DragEvent event) {
        @SuppressWarnings("unchecked")
        Square<ChessPieceAdapter> square = (Square<ChessPieceAdapter>) event.getSource();

        if (event.getGestureSource() != square && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    private void onDragDropped(final DragEvent event) {
        @SuppressWarnings("unchecked")
        Square<ChessPieceAdapter> square = (Square<ChessPieceAdapter>) event.getSource();

        if (event.getGestureSource() != square && event.getDragboard().hasString() && board.isQueued() &&
                board.getQueued().getElement().getChessPiece().legalMoves(board.toQueryableBoardState())
                        .contains(square.getPosition())) {
            updateBoard(new Action(board.getQueued().getPosition(), square.getPosition()));
        }

        event.setDropCompleted(true);
        event.consume();
    }

    private void onDragDone(final DragEvent event) {
        board.deque();
        event.consume();
    }

    /*
    Todo: needs to be reimplemented
     */
    protected boolean updateBoard(Action action) {
        var state = board.update(action);
        updatePlayers();
        states.push(state);
        addDescriptions(states);
        AudioManager.playChessAudio(state.getOrigin().getStateDescriptions());
        return shouldShowTerminalDialog();
    }

    private boolean shouldShowTerminalDialog() {
        return states.peek().getOrigin().getStateDescriptions()
                .contains(StateDescription.END_STATE);
    }

    protected void showTerminalStateDialog() {
        Dialog<ButtonType> dialog = new DialogBuilder<ButtonType>()
                .setTitle("END")
                .setContentText("GAME OVER")
                .addButtonType(ButtonType.CLOSE)
                .build();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(root.getScene().getWindow());
        dialog.showAndWait().ifPresent(buttonType -> System.exit(0));
    }

    private boolean addDescriptions(final LastInFirstOutStack<QueryableBoardState> states) {
        if ((states == null) || (states.isEmpty())) {
            return true;
        }

        QueryableBoardState state = states.peek();
        List<StateDescription> descriptions = state.getOrigin().getStateDescriptions();

        if (state.checkmate(state.getOrigin().getCreator())) {
            descriptions.addAll(Arrays.asList(
                    StateDescription.CHECKMATE,
                    StateDescription.END_STATE)
            );
            return false;
        }

        if (state.stalemate(Color.invert(state.getOrigin().getCreator()))) {
            descriptions.addAll(Arrays.asList(
                    StateDescription.DRAW_BY_STALEMATE,
                    StateDescription.END_STATE)
            );
            return false;
        }

        if (QueryableBoardState.threefoldRepetition(states)) {
            descriptions.addAll(Arrays.asList(
                    StateDescription.DRAW_BY_THREEFOLD_REPETITION,
                    StateDescription.END_STATE)
            );
            return false;
        }

        if (QueryableBoardState.fiftyMovesWithoutPawnMoveOrCapture(states)) {
            descriptions.addAll(Arrays.asList(
                    StateDescription.DRAW_BY_FIFTY_MOVES_RULE,
                    StateDescription.END_STATE)
            );
            return false;
        }

        if (state.insufficientMaterial()) {
            descriptions.addAll(Arrays.asList(
                    StateDescription.DRAW_BY_INSUFFICIENT_MATERIAL,
                    StateDescription.END_STATE)
            );
            return false;
        }

        if (state.check(state.getOrigin().getCreator())) {
            descriptions.add(StateDescription.CHECK);
            return true;
        }

        return true;
    }

    public final void setPlayers(final Collection<Player> players) {
        if ((players == null) || !(players.size() == 2)) {
            throw new IllegalArgumentException(
                    "players must be non-null and must consist of two elements."
            );
        }

        this.players.clear();
        this.players.push(players.stream()
                .filter(player -> player.getColor() == Color.WHITE).findFirst().orElseThrow());
        this.players.push(players.stream()
                .filter(player -> player.getColor() == Color.BLACK).findFirst().orElseThrow());
    }

    private void updatePlayers() {
        players.push(players.pop());
    }

    protected final FirstInFirstOutStack<Player> getPlayers() {
        return players;
    }
}
