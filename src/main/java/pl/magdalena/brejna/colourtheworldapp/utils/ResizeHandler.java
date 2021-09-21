package pl.magdalena.brejna.colourtheworldapp.utils;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResizeHandler implements EventHandler<MouseEvent> {

    private static final int BORDER = 10;
    private final Stage stage;
    private Cursor cursor = Cursor.DEFAULT;
    private double startX = 0;
    private double startY = 0;

    public ResizeHandler(Stage stageTmp) {
        stage = stageTmp;
    }

    @Override
    public void handle(final MouseEvent event) {
        final EventType<? extends MouseEvent> eventType = event.getEventType();
        final Scene scene = stage.getScene();
        final double mouseEventX = event.getSceneX();
        final double mouseEventY = event.getSceneY();
        final double sceneWidth = scene.getWidth();
        final double sceneHeight = scene.getHeight();

        if (MouseEvent.MOUSE_MOVED.equals(eventType)) {
            setCursor(mouseEventX, mouseEventY, sceneWidth, sceneHeight);
            scene.setCursor(cursor);

        } else if (MouseEvent.MOUSE_PRESSED.equals(eventType)) {
            startX = stage.getWidth() - mouseEventX;
            startY = stage.getHeight() - mouseEventY;
            consumeEventIfNotDefaultCursor(event);

        } else if (MouseEvent.MOUSE_DRAGGED.equals(eventType) && !Cursor.DEFAULT.equals(cursor)) {
            consumeEventIfNotDefaultCursor(event);
            if (!Cursor.W_RESIZE.equals(cursor) && !Cursor.E_RESIZE.equals(cursor))
                processVerticalDrag(event);

            if (!Cursor.N_RESIZE.equals(cursor) && !Cursor.S_RESIZE.equals(cursor))
                processHorizontalDrag(event);
        }
    }

    private void consumeEventIfNotDefaultCursor(final MouseEvent event) {
        if (!cursor.equals(Cursor.DEFAULT))
            event.consume();
    }

    private void processHorizontalDrag(final MouseEvent event) {
        final double minWidth = stage.getMinWidth() > BORDER * 2 ? stage.getMinWidth() : BORDER * 2;
        final double mouseEventX = event.getSceneX();
        if (Cursor.NW_RESIZE.equals(cursor)
                || Cursor.W_RESIZE.equals(cursor)
                || Cursor.SW_RESIZE.equals(cursor)) {
            if (stage.getWidth() > minWidth || mouseEventX < 0) {
                stage.setWidth(stage.getX() - event.getScreenX() + stage.getWidth());
                stage.setX(event.getScreenX());
            }
        } else if (stage.getWidth() > minWidth || mouseEventX + startX - stage.getWidth() > 0) {
            stage.setWidth(mouseEventX + startX);
        }
    }

    private void processVerticalDrag(final MouseEvent event) {
        final double minHeight = stage.getMinHeight() > BORDER * 2 ? stage.getMinHeight() : BORDER * 2;
        final double mouseEventY = event.getSceneY();
        if (Cursor.NW_RESIZE.equals(cursor)
                || Cursor.N_RESIZE.equals(cursor)
                || Cursor.NE_RESIZE.equals(cursor)) {
            if (stage.getHeight() > minHeight || mouseEventY < 0) {
                stage.setHeight(stage.getY() - event.getScreenY() + stage.getHeight());
                stage.setY(event.getScreenY());
            }
        } else if (stage.getHeight() > minHeight || mouseEventY + startY - stage.getHeight() > 0) {
            stage.setHeight(mouseEventY + startY);
        }
    }

    private void setCursor(final double mouseEventX, final double mouseEventY,
                           final double sceneWidth, final double sceneHeight) {
        final Cursor cursor1;
        if (mouseEventX < BORDER && mouseEventY < BORDER) {
            cursor1 = Cursor.NW_RESIZE;
        } else if (mouseEventX < BORDER && mouseEventY > sceneHeight - BORDER) {
            cursor1 = Cursor.SW_RESIZE;
        } else if (mouseEventX > sceneWidth - BORDER && mouseEventY < BORDER) {
            cursor1 = Cursor.NE_RESIZE;
        } else if (mouseEventX > sceneWidth - BORDER && mouseEventY > sceneHeight - BORDER) {
            cursor1 = Cursor.SE_RESIZE;
        } else if (mouseEventX < BORDER) {
            cursor1 = Cursor.W_RESIZE;
        } else if (mouseEventX > sceneWidth - BORDER) {
            cursor1 = Cursor.E_RESIZE;
        } else if (mouseEventY < BORDER) {
            cursor1 = Cursor.N_RESIZE;
        } else if (mouseEventY > sceneHeight - BORDER) {
            cursor1 = Cursor.S_RESIZE;
        } else {
            cursor1 = Cursor.DEFAULT;
        }
        cursor = cursor1;
    }
}
