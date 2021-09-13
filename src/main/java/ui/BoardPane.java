package ui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import model.*;
import model.piece.*;
import model.state.StateDescription;
import model.state.Origin;
import model.state.QueryableBoardState;
import model.state.State;
import ui.adapter.ChessPieceAdapter;
import ui.adapter.QueenAdapter;
import ui.builder.*;
import java.util.*;

public class BoardPane extends Pane
{
    private final HashMap<Position, StackSquare<ChessPieceAdapter>> squares = new HashMap<>(64);

    private final HashMap<Position, Boolean> moved = new HashMap<>()
    {
        {
            put(new Position('A', 1), false);
            put(new Position('E', 1), false);
            put(new Position('H', 1), false);
            put(new Position('A', 8), false);
            put(new Position('E', 8), false);
            put(new Position('H', 8), false);
        }
    };

    private Origin origin = Origin.init();

    private Square<ChessPieceAdapter> queued = null;

    private final List<Square<ChessPieceAdapter>> queuedLegal = new ArrayList<>(16);

    private final ObservableList<Line> arrowComponents = FXCollections.observableArrayList();

    public BoardPane() {
        boolean isLight = true;

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                int size = 75;
                Position position = new Position(col, ((8 - row) + 1));
                Bloom bloom = new Bloom();
                bloom.setThreshold(0.8);
                StackSquare<ChessPieceAdapter> square = new SquareBuilder<ChessPieceAdapter>()
                        .setSize(75)
                        .setPosition(position)
                        .setFill(isLight ?
                                Style.primaryLinearGradient : Style.secondaryLinearGradient
                        )
                        .setStroke(Color.BLACK).setStrokeType(StrokeType.INSIDE).setStrokeRadius(1.0)
                        .setText(new TextBuilder(String.valueOf(position.getY()))
                                .setFill(isLight ? Style.secondaryColor : Style.primaryColor)
                                .setBold(true)
                                .build(), Square.X_AXIS)
                        .setText(new TextBuilder(String.valueOf(Position.from.get(position.getX())))
                                .setFill(isLight ? Style.secondaryColor : Style.primaryColor)
                                .setBold(true)
                                .build(), Square.Y_AXIS)
                        .setTextMargin(new Insets(2, 2, 2, 2), Square.X_AXIS)
                        .setTextMargin(new Insets(2, 4, 2, 2), Square.Y_AXIS)
                        .setTextAlignment(Pos.TOP_LEFT, Square.X_AXIS)
                        .setTextAlignment(Pos.BOTTOM_RIGHT, Square.Y_AXIS)
                        .showText((position.getX() == 1), Square.X_AXIS)
                        .showText((position.getY() == 1), Square.Y_AXIS)
                        .setEffect(bloom)
                        .setElement(AdapterFactory.newAdapter(position))
                        .build();
                square.setLayoutX((col - 1) * size);
                square.setLayoutY((row - 1) * size);
                getChildren().add(square);
                squares.put(position, square);
                isLight = !isLight;
            }

            isLight = !isLight;
        }

        arrowComponents.addListener((ListChangeListener<Line>) c -> {
            c.next();

            if (c.wasRemoved()) {
                getChildren().removeAll(c.getRemoved());
            }

            else if (c.wasAdded()) {
                c.getAddedSubList().forEach(item -> {
                    getChildren().add(item);
                    item.setOnDragDetected(arrowOnDragDetected());
                    item.setOnDragDropped(arrowOnDragDropped());
                    item.setOnDragOver(arrowOnDragOver());
                });
            }
        });
    }

    public Square<ChessPieceAdapter> getQueued() {
        return queued;
    }

    public boolean isQueued() {
        return queued != null;
    }

    public boolean isNotQueued()
    {
        return queued == null;
    }

    public void queue(Square<ChessPieceAdapter> square)
    {
        if (isQueued() && square.equals(queued)) {
            deque();
        }
        else {
            deque();
            square.setStroke(Style.queuedColor);
            square.setStrokeWidth(2.0);
            square.setStrokeRadius(5.0);
            queued = square;
            queueLegal();
        }
    }

    public void deque()
    {
        if (isQueued()) {
            queued.resetStroke();
            queued.resetStrokeWidth();
            queued.resetStrokeRadius();
        }
        queued = null;
        dequeLegal();
    }

    private void queueLegal() {
        if (isQueued()) {
            QueryableBoardState state = toQueryableBoardState();
            ChessPiece queuedPiece = getQueued().getElement().getChessPiece();

            queuedPiece.legalMoves(state)
                    .forEach(pos -> {
                        Square<ChessPieceAdapter> square = squares.get(pos);
                        Circle circle = new Circle(4.5, Style.circleColor);
                        circle.setEffect(new DropShadowBuilder()
                                .setOffsetX(3.0f)
                                .setOffsetY(3.0f)
                                .setColor(Style.circleDropShadowColor)
                                .build());
                        circle.setCache(true);
                        square.addCircle(circle);

                        if ((square.hasElement()) && ((square.getElement().getChessPiece().getColor() !=
                                queuedPiece.getColor())) || state.canCaptureEnPassant(queuedPiece, square.getPosition()))
                        {
                            square.setStroke(Style.capturableColor);
                            square.setStrokeWidth(2.0);
                            square.setStrokeRadius(5.0);
                        }
                        queuedLegal.add(square);
                    });
        }
    }

    private void dequeLegal()
    {
        queuedLegal.forEach(square -> {
            square.removeCircle();
            square.resetStroke();
            square.resetStrokeWidth();
            square.resetStrokeRadius();
        });
        queuedLegal.clear();
    }

    public final QueryableBoardState update(final Action action)
    {
        Square<ChessPieceAdapter> start = squares.get(action.getStartPosition());
        Square<ChessPieceAdapter> end = squares.get(action.getEndPosition());
        List<StateDescription> stateDescriptions = new ArrayList<>();
        Position passed = null;

        ChessPieceAdapter adapter = start.removeElement();
        ChessPieceAdapter captured = end.setElement(adapter);
        moved.replace(start.getPosition(), true);
        stateDescriptions.add((captured != null) ?
                StateDescription.CAPTURE :
                StateDescription.MOVE);

        if (QueryableBoardState.isCastle(adapter.getChessPiece(), action))
        {
            Action response = Rook.CASTLE_RESPONSE.get(action);
            squares.get(response.getEndPosition()).
                    addElement(squares.get(response.getStartPosition()).removeElement());
            moved.replace(response.getStartPosition(), true);
            stateDescriptions.addAll(Arrays.asList(StateDescription.MOVE, StateDescription.CASTLE));
        }

        else if (QueryableBoardState.isPawnPromotion(adapter.getChessPiece(), end.getPosition()))
        {
            end.removeElement();
            end.addElement(new QueenAdapter(new Queen(adapter.getChessPiece().getColor())));
            deque();
            stateDescriptions.add(StateDescription.PAWN_PROMOTION);
        }

        else if (QueryableBoardState.isEnPassant(adapter.getChessPiece(), action, origin))
        {
            squares.get(origin.getAction().getEndPosition()).removeElement();
            stateDescriptions.addAll(Arrays.asList(StateDescription.CAPTURE, StateDescription.EN_PASSANT));
        }

        else if (QueryableBoardState.isPawnTwoStepMove(adapter.getChessPiece(), action))
        {
            int x = action.getStartPosition().getX();
            int y = action.getEndPosition().getY() - action.getStartPosition().getY();
            y = (y == -2) ? 6 : 3;
            passed = new Position(x, y);
            stateDescriptions.add(StateDescription.PAWN_TWO_STEP_MOVE);
        }

        if ((adapter.getChessPiece() instanceof Pawn)) {
            stateDescriptions.add(StateDescription.PAWN_MOVE);
        }

        drawArrow(start, end);
        this.origin = new Origin(action, adapter.getChessPiece().getColor(),
                (captured != null) ? captured.getChessPiece() : null,
                passed, stateDescriptions);

        return toQueryableBoardState();
    }

    private void move(Square<ChessPieceAdapter> start, Square<ChessPieceAdapter> end) {
        if ((start == null) || !(start.hasElement())) {
            throw new IllegalArgumentException(
                    "start square does not have an element."
            );
        }

        end.setElement(start.removeElement());
        moved.replace(start.getPosition(), true);
    }

    private void drawArrow(Square<ChessPieceAdapter> start, Square<ChessPieceAdapter> end)
    {
        if ((start == null) || (end == null)) {
            throw new IllegalArgumentException(
                    "arrow cannot be drawn when the start and/or end square is null."
            );
        }

        arrowComponents.clear();
        arrowComponents.addAll(
                new ArrowBuilder()
                        .setStroke(Style.arrowColor)
                        .setBlendMode(Style.arrowBlendMode)
                        .setStartX(start.getCenterX())
                        .setStartY(start.getCenterY())
                        .setEndX(end.getCenterX())
                        .setEndY(end.getCenterY())
                        .build()
        );
    }

    public QueryableBoardState toQueryableBoardState() {
        HashMap<Position, ChessPiece> vSquares = new HashMap<>(this.squares.size());

        this.squares.forEach((pos, square) ->
        {
            if (square.hasElement()) {
                vSquares.put(pos, square.getElement().getChessPiece());
            }
        });

        return new State(vSquares, moved, origin);
    }

    public final void apply(QueryableBoardState state) {
        if ((state == null)) {
            return;
        }

        this.origin = state.getOrigin();
        state.getHasMoved().forEach(moved::replace);
        squares.forEach((pos, s) -> s.removeElement());
        state.getChessPieces().forEach((pos, c) -> squares.get(pos).addElement(AdapterFactory.newAdapter(c)));

        if (this.origin.getAction() != null)
        {
            drawArrow(
                    squares.get(origin.getAction().getStartPosition()),
                    squares.get(origin.getAction().getEndPosition())
            );
        }
        else {
            arrowComponents.clear();
        }
    }

    public void rotate() {
        double currentValue = getRotate();
        double newValue = (currentValue == 180) ? 0 : 180;
        squares.forEach((pos, sq) -> sq.rotate());
        setRotate(newValue);
    }

    private EventHandler<MouseEvent> arrowOnDragDetected()
    {
        return event ->
        {
            for (Square <ChessPieceAdapter> square : squares.values())
            {
                StackSquare<ChessPieceAdapter> s = (StackSquare<ChessPieceAdapter>) square;

                if (s.getBoundsInParent().contains(event.getX(), event.getY()) &&
                        s.getOnDragDetected() != null)
                {
                    s.getOnDragDetected().handle(event.copyFor(s, event.getTarget()));
                    break;
                }
            }
        };
    }

    private EventHandler<DragEvent> arrowOnDragDropped()
    {
        return event ->
        {
            for (Square <ChessPieceAdapter> square : squares.values())
            {
                StackSquare<ChessPieceAdapter> s = (StackSquare<ChessPieceAdapter>) square;

                if (s.getBoundsInParent().contains(event.getX(), event.getY()) &&
                        s.getOnDragDropped() != null)
                {
                    s.getOnDragDropped().handle(event.copyFor(s, event.getTarget()));
                    break;
                }
            }
        };
    }

    private EventHandler<DragEvent> arrowOnDragOver()
    {
        return event ->
        {
            var square = getSquareFromBoundsInParent(event.getX(), event.getY());

            if ((square != null) && (square.getOnDragOver() != null))
            {
                square.getOnDragOver().handle(event.copyFor(square, event.getTarget()));
            }
        };
    }

    public void setOnSquareDragDetected(final EventHandler<? super MouseEvent> eventHandler)
    {
        squares.forEach((pos, sq) -> ((StackPane) sq).setOnDragDetected(eventHandler));
    }

    public void setOnSquareDragOver(final EventHandler<? super DragEvent> eventHandler)
    {
        squares.forEach((pos, sq) -> ((StackPane) sq).setOnDragOver(eventHandler));
    }

    public void setOnSquareDragDropped(final EventHandler<? super DragEvent> eventHandler)
    {
        squares.forEach((pos, sq) -> ((StackPane) sq).setOnDragDropped(eventHandler));
    }

    public void setOnSquareDragDone(final EventHandler<? super DragEvent> eventHandler)
    {
        squares.forEach((pos, sq) -> ((StackPane) sq).setOnDragDone(eventHandler));
    }

    public final StackSquare<ChessPieceAdapter> getSquareFromBoundsInParent(double x, double y)
    {
        for (Square<ChessPieceAdapter> square : squares.values())
        {
            StackSquare<ChessPieceAdapter> c = (StackSquare<ChessPieceAdapter>) square;

            if (c.getBoundsInParent().contains(x, y))
            {
                return c;
            }
        }

        return null;
    }

    private static class Style
    {
        public static final Color primaryColor = Color.WHEAT;

        public static final Color secondaryColor = Color.web("#8f8890").darker();
        // #884ec7

        public static final Color linearGradientColor = Color.SILVER;

        public static final LinearGradient primaryLinearGradient =
                new LinearGradient(
                        0, 0, 5, 10,
                        false, CycleMethod.NO_CYCLE,
                        new Stop(0, Style.primaryColor),
                        new Stop(1, Style.primaryColor)
                );

        public static final LinearGradient secondaryLinearGradient =
                new LinearGradient(
                        0, 0, 5, 10,
                        false, CycleMethod.NO_CYCLE, // 0,0,10,35
                        new Stop(0, Style.secondaryColor),
                        new Stop(1, Style.secondaryColor)
                );

        public static final Color circleColor = Color.PURPLE;

        public static final Color circleDropShadowColor = Color.BLACK;

        public static final Color queuedColor = Color.GREEN;

        public static final Color capturableColor = Color.PURPLE;

        public static final Color arrowColor = Color.YELLOWGREEN;

        public static final BlendMode arrowBlendMode = BlendMode.SRC_OVER;
    }
}