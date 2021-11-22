/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.pojos;

import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.util.AppContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author Farlen
 */
public class ItemElementoDeSeccionSecundario extends VBox {

    private double posicionX;
    private double posicionY;
    Stage stage;

    public ElementodeseccionDto elementoDto;
    public EmpleadoDto empOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
    public Button btnOrdenes = new Button();
    public Button btnAgregar = new Button();
    public Label lblStatus = new Label();
//    private SeccionDto idSeccion;
    private ImageView iv;

    public ImageView getIv() {
        return iv;
    }

    public void setIv(ImageView iv) {
        this.iv = iv;
    }

    
    
    public ItemElementoDeSeccionSecundario(ElementodeseccionDto elementoDto) {
        this.elementoDto = elementoDto;
        inicializarVBox();
        Image i = new Image(new ByteArrayInputStream(elementoDto.getImagenElemento()));
        agregarDatos(i);
        this.setCursor(Cursor.HAND);

    }
    public void setElementoGenerico() {

    }

    public ElementodeseccionDto getElementoGenerico() {
        return this.elementoDto;
    }

    public double getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    public double getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(double posicionY) {
        this.posicionY = posicionY;
    }

    private void inicializarVBox() {
        List<OrdenDto> ordenesActivas = new ArrayList<>();
        for (OrdenDto ordenDto : elementoDto.getOrdenesDtoList()) {
            if (ordenDto.getEsEstado() == 1L) {
                ordenesActivas.add(ordenDto);
            }
        }
        if (!ordenesActivas.isEmpty()) {
            elementoDto.setEsOcupada(2L);
        } else {
            elementoDto.setEsOcupada(1L);
        }

        this.initStyle();

        this.setLayoutX(this.elementoDto.getPosicionX());
        this.setLayoutY(this.elementoDto.getPosicionY());

    }

    private void agregarDatos(Image i) {
        Label nombre = new Label(this.elementoDto.getNombre());
        nombre.setStyle("-fx-font-size: 15px;"
                + "-fx-text-fill:  #E0EEF6;"
                + "-fx-max-width: 75px;"
                + "-fx-text-alignment: 'CENTER';"
        );
        HBox hboxI = new HBox();
        hboxI.setStyle(
                "-fx-background-color:#4F4652;"
                + "-fx-background-radius: 10px;"
                + "-fx-pref-width: 75px;"
                + "-fx-max-width: 75px;"
                + "-fx-max-height: 75px ;"
                + "-fx-pref-height: 75px;"
                + "-fx-alignment: 'CENTER';"
        );

        iv = new ImageView(i);
        iv.setPreserveRatio(true);

        if (iv.getFitHeight() >= iv.getFitWidth()) {
            iv.setFitHeight(50);

        } else {
            iv.setFitWidth(50);

        }

        HBox btnCont = new HBox();
        btnCont.setStyle(
                "-fx-pref-width: 100px;"
                + "-fx-pref-height: 25px;"
                + "-fx-max-width: 100px;"
                + "-fx-max-height:25px ;"
                + "-fx-alignment: 'CENTER';"
                + "-fx-spacing: 10px;"
        );

        btnOrdenes.setId("btnOrdenes");
        btnOrdenes.setText("Ordenes");
        btnOrdenes.setStyle(
                "-fx-font-size: 12px;"
                + "-fx-text-fill:#E0EEF6;"
                + "-fx-background-color:#a78a7f;"
                + "-fx-background-radius: 5px;"
                + "-fx-pref-height: 25px;"
                + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.4), 5, 0.05, 0, 0 );");

        hboxI.getChildren().add(iv);
        btnCont.getChildren().addAll(btnOrdenes);

        //lo que va en vbox final
        this.getChildren().add(nombre);
        this.getChildren().add(hboxI);
        this.getChildren().add(btnCont);
    }

    public void initStyle() {
        if (this.elementoDto.getEsOcupada().equals(1L)) {
            this.setStyle("-fx-pref-Width: 100px;"
                    + "-fx-pref-height: 150px;"
                    + "-fx-max-width: 100px;"
                    + "-fx-max-height:150px;"
                    + "-fx-alignment: 'CENTER';"
                    + "-fx-background-color: #0C9468;"
                    + " -fx-background-radius: 10;"
                    + "-fx-spacing: 5px;"
                    + "-fx-effect: dropshadow(gaussian, rgba(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
            );
        } else if (this.elementoDto.getEsOcupada().equals(2L)) {
            this.setStyle("-fx-pref-Width: 100px;"
                    + "-fx-pref-height: 150px;"
                    + "-fx-max-width: 100px;"
                    + "-fx-max-height: 150px;"
                    + "-fx-alignment: 'CENTER';"
                    + "-fx-background-color: #870000;"
                    + " -fx-background-radius: 10;"
                    + "-fx-spacing: 5px;"
                    + "-fx-effect: dropshadow(gaussian, rgba(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
            );
        }
    }

    public void toggleOcupada() {
        if (this.elementoDto.getEsOcupada().equals(1L)) {
            elementoDto.setEsOcupada(2L);
            this.setStyle("-fx-pref-Width: 100px;"
                    + "-fx-pref-height: 150px;"
                    + "-fx-max-width: 100px;"
                    + "-fx-max-height:150px;"
                    + "-fx-alignment: 'CENTER';"
                    + "-fx-background-color: #0C9468;"
                    + " -fx-background-radius: 10;"
                    + "-fx-spacing: 5px;"
                    + "-fx-effect: dropshadow(gaussian, rgba(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
            );
        } else if (this.elementoDto.getEsOcupada().equals(2L)) {
            elementoDto.setEsOcupada(1L);
            this.setStyle("-fx-pref-Width: 100px;"
                    + "-fx-pref-height: 150px;"
                    + "-fx-max-width: 100px;"
                    + "-fx-max-height: 150px;"
                    + "-fx-alignment: 'CENTER';"
                    + "-fx-background-color: #870000;"
                    + " -fx-background-radius: 10;"
                    + "-fx-spacing: 5px;"
                    + "-fx-effect: dropshadow(gaussian, rgba(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
            );
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private double stDragX = 0;
    private double stDragY = 0;

}
