package cr.ac.una.restuna.util;

import cr.ac.una.restuna.pojos.ItemElementoDeSeccionSecundario;
import javafx.scene.layout.AnchorPane;

public class DraggableMaker {

    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeDraggable(ItemElementoDeSeccionSecundario node, AnchorPane pane) {

        pane.setOnMouseDragged(mouseEvent -> {
            double posx = mouseEvent.getX();
            double posy = mouseEvent.getY();
        });
    }
    
}
