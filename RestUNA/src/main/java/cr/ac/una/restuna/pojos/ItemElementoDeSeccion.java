/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.pojos;

import cr.ac.una.restuna.model.ElementodeseccionDto;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import javafx.scene.control.Button;

/**
 *
 * @author Farlen
 */
public class ItemElementoDeSeccion extends VBox {

    private Long idElemento;
    private Long tipo;
    private String nombre;
    private Long esOcupada;
    private double posicionX;
    private double posicionY;
    private double impuestoPorServicio;
    private byte[] imagenElemento;

    public Button btnEditar = new Button();
    public Button btnAgregar = new Button();
    ElementodeseccionDto elementoDto = new ElementodeseccionDto();
//    private SeccionDto idSeccion;

    public ItemElementoDeSeccion(ElementodeseccionDto elementoDto) {
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
        this.setStyle("-fx-pref-Width: 150px;"
                + "-fx-pref-height: 200px;"
                + "-fx-alignment: 'TOP_CENTER';"
                + "-fx-spacing: 5px;"
                + "-fx-background-color:#735751;"
                + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(gaussian, rgb(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
        );
    }

    private void agregarDatos(Image i) {
        Label nombre = new Label(this.nombre);
        nombre.setStyle("-fx-font-size: 20px;"
                + "-fx-text-fill:  #E0EEF6;"
                + "-fx-text-alignment: 'CENTER';");
        nombre.setWrapText(true);

        HBox hboxI = new HBox();
        hboxI.setStyle("-fx-background-color:#4F4652;"
                + "-fx-background-radius: 10px;"
                + "-fx-pref-width: 125px;"
                + "-fx-max-width: 125px;"
                + "-fx-max-height:125px ;"
                + "-fx-pref-height: 125px;"
                + "-fx-alignment: 'CENTER';"
        );

        ImageView iv = new ImageView(i);
        iv.setPreserveRatio(true);

        if (iv.getFitHeight() >= iv.getFitWidth()) {
            iv.setFitWidth(75);
        } else {
            iv.setFitHeight(75);
        }
        HBox btnCont = new HBox();
        btnCont.setStyle(
                "-fx-pref-width: 150px;"
                + "-fx-max-width: 150px;"
                + "-fx-max-height:25px ;"
                + "-fx-pref-height: 25px;"
                + "-fx-alignment: 'CENTER';"
                + "-fx-spacing: 10px;"
        );

        btnEditar.setId("btnEditar");
        btnEditar.setText("Editar");
        btnEditar.setStyle(
                "-fx-font-size: 12px;"
                + "-fx-text-fill:#E0EEF6;"
                + "-fx-background-color:#a78a7f;"
                + "-fx-background-radius: 5px;"
                + "-fx-pref-height: 25px;"
                + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 1.0, 1.0 );");

        btnAgregar.setId("btnAgregar");
        btnAgregar.setText("Agregar");
        btnAgregar.setStyle(
                "-fx-font-size: 12px;"
                + "-fx-text-fill: #C9E4DB;"
                + "-fx-background-color: #0C9468;"
                + "-fx-background-radius: 5px;"
                + "-fx-pref-height: 25px;"
                + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 1.0, 1.0 );");

        btnCont.getChildren().addAll(btnEditar, btnAgregar);

        //lo que va en vbox final
        this.getChildren().add(nombre);
        hboxI.getChildren().add(iv);
        this.getChildren().add(hboxI);
        this.getChildren().add(btnCont);
    }

}
