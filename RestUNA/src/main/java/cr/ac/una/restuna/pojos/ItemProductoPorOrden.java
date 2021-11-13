/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.pojos;

import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.model.ProductoporordenDto;
import cr.ac.una.restuna.service.ProductoporordenService;
import cr.ac.una.restuna.util.Respuesta;
import java.io.ByteArrayInputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author jeez
 */
public class ItemProductoPorOrden extends HBox {

    private ProductoporordenDto productoporordenDto = new ProductoporordenDto();
    public Button btnSum = new Button();
    public Button btnRest = new Button();
    public Label lblCantidad = new Label();
    public long cantidad = 0;

    public ItemProductoPorOrden(ProductoporordenDto productoporordenDto) {
        setProductoporordenDto(productoporordenDto);
        cantidad = this.productoporordenDto.getCantidad();
        init();
    }

    
    
    public void init() {
        
        HBox contFoto = new HBox();
        contFoto.setStyle(
                "-fx-background-color: #735751;"
                + "-fx-background-radius: 10px;"
                + "-fx-pref-Width: 50px;"
                + "-fx-pref-height: 50px;"
                + "-fx-max-width: 50px;"
                + "-fx-max-height: 50px;"
                + "-fx-alignment: 'CENTER';"
        //                    + "-fx-spacing: 5px;"
        //                + "-fx-effect: dropshadow(gaussian, rgba(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
        );

        Image i = new Image(new ByteArrayInputStream(this.productoporordenDto.getIdProductoDto().getImagen()));
       
        ImageView iv = new ImageView(i);
iv.setPreserveRatio(true);
        if (i.getHeight() >= i.getWidth()) {
            iv.setFitHeight(45);
        } else {
            iv.setFitWidth(45);
        }
        contFoto.getChildren().addAll(iv);

        HBox contNombreProd = new HBox();
        contNombreProd.setStyle("-fx-pref-Width: 150px;"
                + "-fx-pref-height: 50px;"
                + "-fx-max-width: 150px;"
                + "-fx-max-height: 50px;"
                + "-fx-alignment: 'CENTER_LEFT';"
        );

        Label clblNombreProd = new Label(" " + this.productoporordenDto.getIdProductoDto().getNombre());
        clblNombreProd.setStyle("-fx-font-size: 15px;"
                + "-fx-text-fill:  #E0EEF6;");
        contNombreProd.getChildren().add(clblNombreProd);

        HBox contButtons = new HBox();
        contButtons.setStyle(
                //                "-fx-background-color: #735751;"
                //                + "-fx-background-radius: 10px;"
                "-fx-pref-Width: 75px;"
                + "-fx-pref-height: 50px;"
                + "-fx-max-width: 75px;"
                + "-fx-max-height: 50px;"
                + "-fx-alignment: 'CENTER';"
                + "-fx-spacing: 10px;"
        //                + "-fx-effect: dropshadow(gaussian, rgba(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
        );
        btnSum.setStyle("-fx-font-family: 'Fira Sans';"
                + "-fx-font-size: 15px;"
                + "-fx-text-fill:  #C9E4DB;"
                + "-fx-background-color:#0C9468;"
                + "-fx-background-radius: 5px;"
                + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.4), 5, 0.05, 0, 0 );"
        );
        btnSum.setText("+");
        btnRest.setStyle("-fx-font-family: 'Fira Sans';"
                + "-fx-font-size: 15px;"
                + "-fx-text-fill: #F2CED7;"
                + "-fx-background-color:#870000;"
                + "-fx-background-radius: 5px;"
                + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.4), 5, 0.05, 0, 0 );"
        );
        btnRest.setText("-");
        lblCantidad.setStyle("-fx-font-size: 20px;"
                + "-fx-text-fill:  #E0EEF6;");
        
        lblCantidad.setText(String.valueOf(cantidad));
        
        
        contButtons.getChildren().addAll(btnRest, lblCantidad, btnSum);

        this.getChildren().addAll(contFoto, contNombreProd, contButtons);
        this.setStyle("-fx-pref-Width: 305px;"
                + "-fx-pref-height: 50px;"
                + "-fx-max-width: 305px;"
                + "-fx-max-height: 50px;"
                + "-fx-alignment: 'CENTER_LEFT';"
                + "-fx-background-color:#4F4652;"
                + "-fx-background-radius: 10;"
                + "-fx-spacing: 5px;"
                + "-fx-effect: dropshadow(gaussian, rgba(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
        );
    }

    public ProductoporordenDto getProductoporordenDto() {
        return this.productoporordenDto;
    }

    public void setProductoporordenDto(ProductoporordenDto productoporordenDto) {
        this.productoporordenDto = productoporordenDto;
    }

//    public Button getBtnVer() {
//        return this.btnVer;
//    }
    public void ActionMinus() {
            if (cantidad > 0) {
                cantidad--;
                lblCantidad.setText(String.valueOf(cantidad));
                //txtCant.setText(Integer.toString(cantidad));
                MultPrec();
            }
            
    }

    public void ActionSum() {
      
            cantidad++;
            lblCantidad.setText(String.valueOf(cantidad));
            MultPrec();
        
    }

    private void MultPrec() {
        double nprecio = this.productoporordenDto.getIdProductoDto().getPrecio() * this.cantidad;
        this.productoporordenDto.setSubtotal(nprecio);
        this.productoporordenDto.setCantidad(Long.valueOf(this.cantidad));
        
        ProductoporordenService service = new ProductoporordenService();
        Respuesta resp = service.guardarProductopororden(productoporordenDto);
        this.productoporordenDto = (ProductoporordenDto) resp.getResultado("Productopororden");
    }

}
