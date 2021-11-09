package cr.ac.una.restuna.util;

import cr.ac.una.restuna.pojos.ItemElementoDeSeccionSecundario;
import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

public class DraggableMaker {

    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeDraggable(ItemElementoDeSeccionSecundario node, AnchorPane pane) {

//        pane.setOnMousePressed(mouseEvent -> {
//            mouseAnchorX = mouseEvent.getX();
//            mouseAnchorY = mouseEvent.getY();
//        });
//
        pane.setOnMouseDragged(mouseEvent -> {
//            double left = pane.getLayoutBounds().getMinX() + 50D;
//            double top = pane.getLayoutBounds().getMinY() + 50D;
//            double right = pane.getLayoutBounds().getMaxX() - 50D;
//            double bottom = pane.getLayoutBounds().getMaxY() - 50D;
            double posx = mouseEvent.getX();
            double posy = mouseEvent.getY();

//            if (posx > pane.getLayoutBounds().getMinX() + 50D
//                    && posx < pane.getLayoutBounds().getMaxX() - 50D 
//                    && posy > pane.getLayoutBounds().getMinY() + 50D
//                    && posy < pane.getLayoutBounds().getMaxY() - 50D) {
//                node.setLayoutX(mouseEvent.getX() - 50D);
//                node.setLayoutY(mouseEvent.getY() - 50D);
//            }
//            if (posx > 0 + 50D
//                    && posx < 0 - 50D 
//                    && posy >500 + 50D
//                    && posy < 700 - 50D) {
//                node.setLayoutX(mouseEvent.getX() - 50D);
//                node.setLayoutY(mouseEvent.getY() - 50D);
//            }
        });
    }
    
//    public void makeDraggable(Node node) {
//
//        node.setOnMousePressed(mouseEvent -> {
//            mouseAnchorX = mouseEvent.getX();
//            mouseAnchorY = mouseEvent.getY();
//        });
//
//        node.setOnMouseDragged(mouseEvent -> {
//            node.setLayoutX(mouseEvent.getX() - 50);
//            node.setLayoutY(mouseEvent.getY() - 50);
//        });
//    }
//        node.setOnDragDetected(event -> {
//            Node on = (Node) event.getSource();
//            Dragboard db = on.startDragAndDrop(TransferMode.ANY);
//            event.consume();
//        });
//
//        pane.setOnDragOver(e -> {
//            e.acceptTransferModes(TransferMode.ANY);
//        });
//
//        pane.setOnDragExited(e -> {
//            node.setLayoutX(e.getSceneX() - node.getWidth() / 2d);
//            node.setLayoutY(e.getSceneY() - node.getHeight() / 2d);
//        });
//
//    }
}
