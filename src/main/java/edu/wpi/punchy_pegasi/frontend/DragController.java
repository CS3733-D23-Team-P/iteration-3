package edu.wpi.punchy_pegasi.frontend;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import lombok.Setter;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DragController {
    private final Node target;
    private final int ACTIVE = 1;
    private final int INACTIVE = 0;
    private final int cycleStatus = INACTIVE;
    private double anchorX;
    private double anchorY;
    private double layoutX;
    private double layoutY;
    private EventHandler<MouseEvent> setAnchor;
    private EventHandler<MouseEvent> updatePositionOnDrag;
    private EventHandler<MouseEvent> commitPositionOnRelease;
    private BooleanProperty isDraggable;
    @Setter
    private Consumer<Node> onMove;
    @Setter
    private Consumer<Node> onStart;
    @Setter
    private Consumer<Node> onEnd;
    @Setter
    private Supplier<Double> scaleSupplier = () -> 1.0;
    @Setter
    private Predicate<MouseEvent> filterMouseEvents = event -> true;

    public DragController(Node target) {
        this(target, false);
    }

    public DragController(Node target, boolean isDraggable) {
        this.target = target;
        createHandlers();
        createDraggableProperty();
        this.isDraggable.set(isDraggable);
    }

    private double getScale() {
        if (scaleSupplier == null)
            return 1;
        var scale = scaleSupplier.get();
        return scale == null ? 1 : scale;
    }

    private void createHandlers() {
        setAnchor = event -> {
            if (!filterMouseEvents.test(event)) return;
            if (event.isPrimaryButtonDown()) {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                layoutX = target.getLayoutX();
                layoutY = target.getLayoutY();
                target.setCursor(Cursor.CLOSED_HAND);
                if (onStart != null) onStart.accept(target);
            }
        };
        updatePositionOnDrag = event -> {
            if (!filterMouseEvents.test(event)) return;
            target.setLayoutX(layoutX + (event.getSceneX() - anchorX) / getScale());
            target.setLayoutY(layoutY + (event.getSceneY() - anchorY) / getScale());
        };
        commitPositionOnRelease = event -> {
            if (!filterMouseEvents.test(event)) return;
            if (onEnd != null) onEnd.accept(target);
            if (onMove != null) onMove.accept(target);
            target.setCursor(Cursor.OPEN_HAND);
        };
    }

    public void createDraggableProperty() {
        isDraggable = new SimpleBooleanProperty();
        isDraggable.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                target.setCursor(Cursor.OPEN_HAND);
                target.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                target.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            } else {
                target.setCursor(Cursor.DEFAULT);
                target.removeEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                target.removeEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.removeEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            }
        });
    }

    public boolean isIsDraggable() {
        return isDraggable.get();
    }

    public BooleanProperty isDraggableProperty() {
        return isDraggable;
    }
}