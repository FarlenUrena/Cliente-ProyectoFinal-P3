/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.pojos;

import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.ProductoDto;
import cr.ac.una.restuna.util.AppContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.ByteArrayInputStream;
import javafx.scene.control.Button;

/**
 *
 * @author Farlen
 */
public class ItemElementoDeSeccionSecundario extends VBox {

    private Long idElemento;
    private Long tipo;
    private String nombre;
    private Long esOcupada;
    private double posicionX;
    private double posicionY;
    private double impuestoPorServicio;
    private byte[] imagenElemento;
    public ElementodeseccionDto elementoDto = new ElementodeseccionDto();
    public EmpleadoDto empOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
    public Button btnEditar = new Button();
    public Button btnAgregar = new Button();
    public Label lblStatus = new Label();
//    private SeccionDto idSeccion;

    public ItemElementoDeSeccionSecundario(ElementodeseccionDto elementoDto) {
        this.elementoDto = elementoDto;
        inicializarVBox();
        this.idElemento = elementoDto.getIdElemento();
        this.tipo = elementoDto.getTipo();
        this.nombre = elementoDto.getNombre();
        this.esOcupada = elementoDto.getEsOcupada();
        this.posicionX = elementoDto.getPosicionX();
        this.posicionY = elementoDto.getPosicionX();
        this.impuestoPorServicio = elementoDto.getImpuestoPorServicio();
        Image i = new Image(new ByteArrayInputStream(elementoDto.getImagenElemento()));
        agregarDatos(i);

    }
     public ElementodeseccionDto getElementoGenerico() {
        return this.elementoDto;
    }

//
//    public Long getIdProduct() {
//        return idProduct;
//    }
//
//    public void setIdProduct(Long idProduct) {
//        this.idProduct = idProduct;
//    }
//
//    public String getNameProduct() {
//        return nameProduct;
//    }
//
//    public void setNameProduct(String nameProduct) {
//        this.nameProduct = nameProduct;
//    }
//
//    public String getPriceProduct() {
//        return priceProduct;
//    }
//
//    public void setPriceProduct(String priceProduct) {
//        this.priceProduct = priceProduct;
//    }
//
//    public byte[] getImageProduct() {
//        return imageProduct;
//    }
//
//    public void setImageProduct(byte[] imageProduct) {
//        this.imageProduct = imageProduct;
//    }
    private void inicializarVBox() {
        this.setStyle("-fx-pref-Width: 100px;"
                + "-fx-pref-height: 100px;"
                + "-fx-alignment: 'CENTER';"
                + "-fx-spacing: 5px;"
                //                + "-fx-background-color:#735751;"
                //                + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(gaussian, rgb(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
        );
        this.setLayoutX(this.elementoDto.getPosicionX());
        this.setLayoutY(this.elementoDto.getPosicionY());
    }

    private void agregarDatos(Image i) {
        Label nombre = new Label(this.nombre);
        nombre.setStyle("-fx-font-size: 15px;"
                + "-fx-text-fill:  #E0EEF6;"
                + "-fx-text-alignment: 'CENTER';");
        nombre.setWrapText(true);

        HBox hboxI = new HBox();
        hboxI.setStyle(
                //                "-fx-background-color:#4F4652;"
                //                + "-fx-background-radius: 10px;"
                "-fx-pref-width: 75px;"
                + "-fx-max-width: 75px;"
                + "-fx-max-height:75px ;"
                + "-fx-pref-height: 75px;"
                + "-fx-alignment: 'CENTER';"
        );

        ImageView iv = new ImageView(i);
        iv.setPreserveRatio(true);

        if (iv.getFitHeight() >= iv.getFitWidth()) {
            iv.setFitWidth(75);
        } else {
            iv.setFitHeight(75);
        }
//        HBox btnCont = new HBox();
//        btnCont.setStyle(
//                "-fx-pref-width: 150px;"
//                + "-fx-max-width: 150px;"
//                + "-fx-max-height:25px ;"
//                + "-fx-pref-height: 25px;"
//                + "-fx-alignment: 'CENTER';"
//                + "-fx-spacing: 10px;"
//        );
//
//        btnEditar.setId("btnEditar");
//        btnEditar.setText("Editar");
//        btnEditar.setStyle(
//                "-fx-font-size: 12px;"
//                + "-fx-text-fill:#E0EEF6;"
//                + "-fx-background-color:#a78a7f;"
//                + "-fx-background-radius: 5px;"
//                + "-fx-pref-height: 25px;"
//                + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 1.0, 1.0 );");
//
//        btnAgregar.setId("btnAgregar");
//        btnAgregar.setText("Agregar");
//        btnAgregar.setStyle(
//                "-fx-font-size: 12px;"
//                + "-fx-text-fill: #C9E4DB;"
//                + "-fx-background-color: #0C9468;"
//                + "-fx-background-radius: 5px;"
//                + "-fx-pref-height: 25px;"
//                + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 1.0, 1.0 );");
//
//        btnCont.getChildren().addAll(btnEditar, btnAgregar);
        lblStatus.setStyle("-fx-font-size: 25px;"
                + "-fx-text-alignment: 'CENTER';");
        toggleOcupada();
        //lo que va en vbox final
        this.getChildren().add(lblStatus);
        hboxI.getChildren().add(iv);
        this.getChildren().add(hboxI);
        this.getChildren().add(nombre);
//        this.getChildren().add(btnCont);
    }

    public void toggleOcupada() {
        if (this.elementoDto.getEsOcupada().equals(1L)) {
            lblStatus.setText("Desocupada");
            lblStatus.setStyle("-fx-font-size: 25px;"
                    + "-fx-text-fill: #C9E4DB;");
        } else if (this.elementoDto.getEsOcupada().equals(2L)) {
            lblStatus.setText("Ocupada");
            lblStatus.setStyle("-fx-font-size: 25px;"
                    + "-fx-text-fill: #C9E4DB;");
        }
    }

}
