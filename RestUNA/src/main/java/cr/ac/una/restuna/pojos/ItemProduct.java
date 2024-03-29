/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.pojos;

import cr.ac.una.restuna.model.ProductoDto;
import java.io.ByteArrayInputStream;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**@author Farlen
 */
public class ItemProduct extends VBox{
    
    private Long idProduct;
    private String nameProduct;
    private String nameCortoProduct;
    private String priceProduct;
    private byte[] imageProduct;
    

    public ItemProduct(ProductoDto productoDto) {
        inicializarVBox();
        this.idProduct = productoDto.getIdProducto();
        this.nameProduct = productoDto.getNombre();
        this.nameCortoProduct = productoDto.getNombreCorto();
        this.priceProduct = String.valueOf(productoDto.getPrecio());
        this.imageProduct = productoDto.getImagen();
        Image i = new Image(new ByteArrayInputStream(productoDto.getImagen()));
        agregarDatos(nameProduct, nameCortoProduct,priceProduct,i);
        
    }
    
    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public byte[] getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(byte[] imageProduct) {
        this.imageProduct = imageProduct;
    }
    
    private void inicializarVBox(){
        this.setStyle("-fx-pref-Width: 200px;"
                + "-fx-pref-height: 300px;"
                + "-fx-alignment: 'CENTER';"
                + "-fx-spacing: 5px;"
                +"-fx-background-color:#4F4652;"
                + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(gaussian, rgb(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);");
    }
    
    
    private void agregarDatos(String nameProduct, String nameCortoProduct, String priceProduct, Image i) {
        Label nom=new Label(nameProduct);
        Label nomC=new Label("COD: "+ nameCortoProduct);
        Label price = new Label("Precio: ₡"+priceProduct);
        ImageView iv = new ImageView(i);
        HBox hboxN = new HBox();
        HBox hboxP = new HBox();
        HBox hboxI = new HBox();
        
        hboxN.setStyle("-fx-pref-Width: 100px;"
                + "-fx-pref-height: 25px;"
                + "-fx-alignment: 'CENTER';" 
//                + "-fx-margin: 0 10 10 0;"
        );
        
        hboxP.setStyle("-fx-pref-Width: 100px;"
                + "-fx-pref-height: 25px;"
                + "-fx-alignment: 'CENTER';"
//                + "-fx-padding: 0 10 10 0;"
        );

        hboxI.setStyle("-fx-background-color: #735751;"
                + "-fx-background-radius: 10px;"
                + "-fx-pref-width: 175px;"
                +"-fx-max-width: 175px;"
                +"-fx-max-height:175px ;"
                + "-fx-pref-height: 175px;"
                + "-fx-alignment: 'CENTER';" 
        );
        
        nom.setStyle("-fx-font-size: 25px;"
                + "-fx-text-fill:  #E0EEF6;");
        
        nom.setTextAlignment(TextAlignment.CENTER);
//        nom.setWrapText(true);
        nomC.setStyle("-fx-font-size: 20px;"
                + "-fx-text-fill:  #E0EEF6;");
        price.setStyle("-fx-font-size: 20px;"
                + "-fx-text-fill:  #E0EEF6;");
        
        iv.setPreserveRatio(true);
        
        if(iv.getFitHeight()>=iv.getFitWidth()){
            iv.setFitWidth(100);
        }else{
            iv.setFitHeight(100);
        }
        
        hboxN.getChildren().add(nomC);
        hboxP.getChildren().add(price);
        hboxI.getChildren().add(iv);
        
        this.getChildren().add(nom);
        this.getChildren().add(hboxN);
        this.getChildren().add(hboxI);
        this.getChildren().add(hboxP);
        
    }
    
    
}
