/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.pojos;

import cr.ac.una.restuna.model.ProductoDto;
import cr.ac.una.restuna.service.ProductoService;
import cr.ac.una.restuna.util.Respuesta;
import java.io.ByteArrayInputStream;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author Farlen
 */
public class ItemProduct extends VBox{
    
    private Long idProduct;
    private String nameProduct;
    private String priceProduct;
    private byte[] imageProduct;
    

    public ItemProduct(ProductoDto productoDto) {
        inicializarVBox();
        this.idProduct = productoDto.getIdProducto();
        this.nameProduct = productoDto.getNombreCorto();
        this.priceProduct = String.valueOf(productoDto.getPrecio());
        this.imageProduct = productoDto.getImagen();
        Image i = new Image(new ByteArrayInputStream(productoDto.getImagen()));
        agregarDatos(nameProduct,priceProduct,i);
        
//        this.setOnMouseClicked(MouseEvent ->{
//            ProductoService service = new ProductoService();
//            Respuesta respuesta = service.getProducto(idProduct);
//            
//            System.out.println("Auch");
//    });
        
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
        this.setWidth(200);
        this.setHeight(250);
        this.setAlignment(Pos.CENTER);
        
    }
    
    private void agregarDatos(String nameProduct, String priceProduct, Image i) {
        Label nom=new Label(nameProduct);
        Label price = new Label(priceProduct);
        ImageView iv = new ImageView(i);
        
        nom.setPrefSize(200,50);
        price.setPrefSize(200, 50);
        iv.setPreserveRatio(false);
        iv.setFitWidth(200);iv.setFitHeight(150);
        
        this.getChildren().add(nom);
        this.getChildren().add(price);
        this.getChildren().add(iv);        
    }
    
    
}
