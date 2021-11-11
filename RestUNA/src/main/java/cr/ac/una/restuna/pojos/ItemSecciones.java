/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.pojos;

import cr.ac.una.restuna.model.ProductoDto;
import cr.ac.una.restuna.model.SeccionDto;
import cr.ac.una.restuna.util.AppContext;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * @author Farlen
 */
public class ItemSecciones extends VBox {

    private Long idSeccion;
    private String nombre;
    private byte[] fotoDistribucion;
    private SeccionDto seccionDto;

    public ItemSecciones() {
        inicializarVBox(true);
    }

    public ItemSecciones(SeccionDto seccionDto, Boolean isAgregar) {
        this.seccionDto = seccionDto;
        if (!isAgregar) {
            this.idSeccion = seccionDto.getIdSeccion();
            this.nombre = seccionDto.getNombre();
            this.fotoDistribucion = seccionDto.getFotoDistribucion();;
            this.seccionDto = seccionDto;
            Image fotoDst = new Image(new ByteArrayInputStream(seccionDto.getFotoDistribucion()));
            agregarDatos(nombre, fotoDst);
            
        }
        inicializarVBox(isAgregar);
        
    }
    
    public SeccionDto getSeccionSeteada(){
        return this.seccionDto;
    }
    
    public void setToAppContext(){
        AppContext.getInstance().set("SeccionActual",seccionDto);
    }

//
//
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
    private void inicializarVBox(boolean isAgregar) {
        this.setStyle(
                "-fx-pref-Width: 300px;"
                + "-fx-pref-height: 200px;"
                + "-fx-alignment: 'CENTER';"
                + "-fx-spacing: 5px;"
                + "-fx-background-color:#4F4652;"
                + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(gaussian, rgb(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
        );
        if (isAgregar) {
            InputStream is = null;
            try {
                is = new FileInputStream("src/main/resources/cr/ac/una/restuna/resources/nuevoIcon.png"); // here I get FileNotFoundException
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image i = new Image(is);

            ImageView ivNuevo = new ImageView(i);
            ivNuevo.setPreserveRatio(true);
            if (ivNuevo.getFitHeight() >= ivNuevo.getFitWidth()) {
                ivNuevo.setFitWidth(100);
            } else {
                ivNuevo.setFitHeight(100);
            }
            this.getChildren().add(ivNuevo);
        }
    }

    private void agregarDatos(String nombre, Image fotoDst) {

        Label lblNombre = new Label(nombre);
        lblNombre.setStyle(
                "-fx-font-size: 30px;"
                + "-fx-text-fill:  #E0EEF6;"
        );

        ImageView ivFotoDist = new ImageView(fotoDst);
        ivFotoDist.setPreserveRatio(true);
        if (ivFotoDist.getFitHeight() >= ivFotoDist.getFitWidth()) {
            ivFotoDist.setFitWidth(100);
        } else {
            ivFotoDist.setFitHeight(100);
        }

        HBox hboxI = new HBox();
        hboxI.setStyle(
                "-fx-background-color: #735751;"
                + "-fx-background-radius: 10px;"
                + "-fx-pref-width: 175px;"
                + "-fx-max-width: 175px;"
                + "-fx-max-height:175px ;"
                + "-fx-pref-height: 175px;"
                + "-fx-alignment: 'CENTER';"
        );
        hboxI.getChildren().add(ivFotoDist);

        this.getChildren().add(lblNombre);
        this.getChildren().add(hboxI);
    }

}
