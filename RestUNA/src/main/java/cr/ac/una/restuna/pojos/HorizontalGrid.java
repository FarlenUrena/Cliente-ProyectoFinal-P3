/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.pojos;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

/**
 *@author jeez
 */
public class HorizontalGrid extends ScrollPane {

    GridPane gridpane;
    boolean contracted = false;

    public HorizontalGrid() {
//HORIZONTAL
        gridpane = new GridPane();
        this.setStyle("-fx-pref-width: 550px;"
                + "-fx-pref-height: 400px;"
                + "-fx-max-width: 550px;"
                + "-fx-max-height:400px;"
        );
        this.setContent(gridpane);
        this.setVisible(false);
    }

    public GridPane getGrid() {
        return this.gridpane;
    }

    public void addToGrid(ItemProductCarrito p, int col, int row) {
        this.gridpane.add(p, col, row);
        GridPane.setMargin(p, new Insets(10));
    }

    public void toogleVisible() {
        if (this.isVisible()) {
            this.setVisible(false);
            this.setStyle("-fx-max-height:0px;"
                                + "-fx-max-width: 550px;");
        } else {
            this.setVisible(true);
            this.setStyle("-fx-max-height:400px;"
                                + "-fx-max-width: 550px;");
        }

    }

    public boolean isContractected() {
        return this.contracted;
    }

    public void setContractected(boolean contracted) {
        this.contracted = contracted;
    }

}
