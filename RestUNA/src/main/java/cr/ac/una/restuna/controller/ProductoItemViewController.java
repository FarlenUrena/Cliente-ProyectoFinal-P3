/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import cr.ac.una.restuna.model.ProductoDto;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class ProductoItemViewController implements Initializable {

    @FXML
    private Label lblNombre;
    @FXML
    private Label lblPrecio;
    @FXML
    private ImageView imgvImagenProducto;

    /**
     * Initializes the controller class.
     */
    
    private ProductoDto productoDto;
    
    public void setData(ProductoDto productoDto){
    this.productoDto=productoDto;
    lblNombre.setText(productoDto.getNombreCorto());
    lblPrecio.setText("₡"+productoDto.getPrecio());
    imgvImagenProducto.setImage(new Image(new ByteArrayInputStream(productoDto.getImagen())));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
