/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.pojos;

import cr.ac.una.restuna.model.SeccionDto;
import cr.ac.una.restuna.util.AppContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/** @author Farlen
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
            Image fotoDst = null;
            fotoDst = new Image(new ByteArrayInputStream(seccionDto.getFotoDistribucion()));
            agregarDatos(nombre, fotoDst);

        }
        inicializarVBox(isAgregar);

    }

    public SeccionDto getSeccionSeteada() {
        return this.seccionDto;
    }

    public void setToAppContext() {
        AppContext.getInstance().set("SeccionActual", seccionDto);
    }

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
                ivNuevo.setFitHeight(100);
            } else {
                ivNuevo.setFitWidth(100);

            }
            this.getChildren().add(ivNuevo);
//            this.setPadding(new Insets(20, 10, 20, 10));
        }
    }

    private void agregarDatos(String nombre, Image fotoDst) {

        Label lblNombre = new Label(nombre);
        lblNombre.setStyle(
                "-fx-font-size: 25px;"
                + "-fx-text-fill:  #E0EEF6;"
//                        + "fx-margin: 10 0 0 0"
        );

        ImageView ivFotoDist = new ImageView(fotoDst);
        ivFotoDist.setPreserveRatio(true);
        if (fotoDst.getHeight()>= fotoDst.getWidth()) {
            ivFotoDist.setFitHeight(100);
        } else {
            ivFotoDist.setFitWidth(175);
        }

        HBox hboxI = new HBox();
        hboxI.setStyle(
                "-fx-background-color: #735751;"
                + "-fx-background-radius: 10px;"
                + "-fx-pref-width: 200px;"
                + "-fx-max-width: 200px;"
                + "-fx-max-height:150px;"
                + "-fx-pref-height: 150px;"
                + "-fx-alignment: 'CENTER';"
        );
        hboxI.getChildren().add(ivFotoDist);

        this.getChildren().add(lblNombre);
        this.getChildren().add(hboxI);
    }

}
