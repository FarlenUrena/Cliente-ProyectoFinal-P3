/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.pojos;

import cr.ac.una.restuna.model.OrdenDto;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Farlen
 */
public class ItemOrdenLateral extends HBox {
    
    private OrdenDto ordenDto = new OrdenDto();
    public Button btnVer = new Button();

    public ItemOrdenLateral(OrdenDto ordenDto) {
        setOrden(ordenDto);
        init();
    }

    public void init() {
        this.setStyle("-fx-pref-Width: 200px;"
                + "-fx-pref-height: 40px;"
                + "-fx-max-width: 200px;"
                + "-fx-max-height: 40px;"
                + "-fx-alignment: 'CENTER_LEFT';"
                + "-fx-background-color:#4F4652;"
                + "-fx-background-radius: 10;"
                                    + "-fx-spacing: 10px;"
                + "-fx-effect: dropshadow(gaussian, rgba(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
        );
        HBox contIdOrden = new HBox();
        contIdOrden.setStyle("-fx-pref-Width: 50px;"
                + "-fx-pref-height: 40px;"
                + "-fx-max-width: 50px;"
                + "-fx-max-height: 40px;"
                + "-fx-alignment: 'CENTER_LEFT';"
        );
        Label lblIdOrden = new Label("\tID: " + this.ordenDto.getIdOrden().toString());
        lblIdOrden.setStyle("-fx-font-size: 13px;"
                + "-fx-text-fill:  #E0EEF6;");
        contIdOrden.getChildren().add(lblIdOrden);
        HBox contNombCliente = new HBox();
        contNombCliente.setStyle("-fx-pref-Width: 150px;"
                + "-fx-pref-height: 40px;"
                + "-fx-max-width: 150px;"
                + "-fx-max-height: 40px;"
                + "-fx-alignment: 'CENTER_LEFT';"
        );
        String nomb;
        if (this.ordenDto.getNombreCliente() != null) {
            nomb = this.ordenDto.getNombreCliente();
        } else {
            nomb = "Sin Nombre";
        }
        Label lblNombCliente = new Label("\t" + nomb);
        lblNombCliente.setStyle("-fx-font-size: 13px;"
                + "-fx-text-fill: #E0EEF6;");
        contNombCliente.getChildren().add(lblNombCliente);
        btnVer.setStyle("-fx-font-size: 12px;"
                + "-fx-text-fill:#E0EEF6;"
                + "-fx-background-color:#a78a7f;"
                + "-fx-background-radius: 5px;"
                + "-fx-pref-height: 25px;"
                + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.4), 5, 0.05, 0, 0 );");
        btnVer.setText("Ver");
      
        this.getChildren().addAll(contNombCliente, btnVer);

    }
    

    public OrdenDto getOrden() {
        return this.ordenDto;
    }

    public void setOrden(OrdenDto ordenDto) {
        this.ordenDto = ordenDto;
    }

    public Button getBtnVer() {
        return this.btnVer;
    }
}
