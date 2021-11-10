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
import javax.swing.text.AbstractDocument;

/**
 *
 * @author jeez
 */
public class ItemOrden extends HBox {

    private OrdenDto ordenDto = new OrdenDto();
    public Button btnVer = new Button();

    public ItemOrden(OrdenDto ordenDto) {
        setOrden(ordenDto);
        init();
    }

    public void init() {
        this.setStyle("-fx-pref-Width: 450px;"
                + "-fx-pref-height: 50px;"
                + "-fx-max-width: 450px;"
                + "-fx-max-height: 50px;"
                + "-fx-alignment: 'CENTER_LEFT';"
                + "-fx-background-color:#4F4652;"
                + "-fx-background-radius: 10;"
                //                    + "-fx-spacing: 5px;"
                + "-fx-effect: dropshadow(gaussian, rgba(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
        );
        HBox contIdOrden = new HBox();
        contIdOrden.setStyle("-fx-pref-Width: 150px;"
                + "-fx-pref-height: 50px;"
                + "-fx-max-width: 150px;"
                + "-fx-max-height: 50px;"
                + "-fx-alignment: 'CENTER_LEFT';"
        );
        Label lblIdOrden = new Label(this.ordenDto.getIdOrden().toString());
        lblIdOrden.setStyle("-fx-font-size: 15px;"
                + "-fx-text-fill:  #E0EEF6;");

        HBox contNombCliente = new HBox();
        contNombCliente.setStyle("-fx-pref-Width: 150px;"
                + "-fx-pref-height: 50px;"
                + "-fx-max-width: 150px;"
                + "-fx-max-height: 50px;"
                + "-fx-alignment: 'CENTER_LEFT';"
        );
        Label lblNombCliente = new Label("| " + this.ordenDto.getNombreCliente());
        lblNombCliente.setStyle("-fx-font-size: 15px;"
                + "-fx-text-fill:  #E0EEF6;");
        
        btnVer.setStyle("-fx-font-size: 12px;"
                + "-fx-text-fill:#E0EEF6;"
                + "-fx-background-color:#a78a7f;"
                + "-fx-background-radius: 5px;"
                + "-fx-pref-height: 25px;"
                + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.4), 5, 0.05, 0, 0 );");
        
        this.getChildren().addAll(contIdOrden, contNombCliente, btnVer);

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
