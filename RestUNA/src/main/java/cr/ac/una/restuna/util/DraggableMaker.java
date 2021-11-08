package cr.ac.una.restuna.util;

import javafx.scene.Node;

public class DraggableMaker {

    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeDraggable(Node node, Node pane) {

        pane.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            double left = pane.getLayoutBounds().getMinX() + 50D;
            double top = pane.getLayoutBounds().getMinY() + 50D;
            double right = pane.getLayoutBounds().getMaxX() - 50D;
            double bottom = pane.getLayoutBounds().getMaxY() - 50D;
            double posx = mouseEvent.getX();
            double posy = mouseEvent.getY();

            if (posx > left && posx < right && posy > top && posy < bottom) {
                node.setLayoutX(mouseEvent.getX() - 50);
                node.setLayoutY(mouseEvent.getY() - 50);
            }
        });
    }

    public void makeDraggable(Node node) {

        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getX() - 50);
            node.setLayoutY(mouseEvent.getY() - 50);
        });
    }
}
