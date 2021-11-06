/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.pojos;

import cr.ac.una.restuna.model.OrdenDto;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Farlen
 */
public class ItemOrdenLateral extends VBox {
    
    private Long idOrden;
    private String nombreElemento;
    private String nombreCliente;
    
    public ItemOrdenLateral(OrdenDto orden) {
        inicializarHBox();
        this.idOrden = orden.getIdOrden();
        this.nombreElemento=orden.getIdElemento().getNombre();
        this.nombreCliente = orden.getNombreCliente();
        
//        this.idOrden.setText(idOrden.getText() + orden.getIdOrden().toString());        
//        this.nombreElemento.setText(nombreMesa.getText() + orden.getIdElemento().getNombre());        
//        this.nombreCliente.setText(nombreCliente.getText() + orden.getNombreCliente());
        agregarDatos(idOrden, nombreElemento, nombreCliente);
    }

    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public String getNombreElemento() {
        return nombreElemento;
    }

    public void setNombreElemento(String nombreElemento) {
        this.nombreElemento = nombreElemento;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    private void inicializarHBox() {
        this.setStyle("-fx-pref-Width: 200px;"
                + "-fx-pref-height: 300px;"
                + "-fx-alignment: 'CENTER';"
                + "-fx-spacing: 5px;"
                + "-fx-background-color:#4F4652;"
                + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(gaussian, rgb(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);");
        
    }
    
    private void agregarDatos(Long idOrden, String nomMesa, String nomCliente) {
        
        
        Label labelOrden = new Label("Id Orden: "+idOrden);
        Label labelnombreElemento = new Label("Mesa: "+nomMesa);
        Label labelnombreCliente = new Label("Cliente: "+nomCliente);
        
        HBox hboxId = new HBox();
        HBox hboxMesa = new HBox();
        HBox hboxCliente = new HBox();
        
        String style = "-fx-pref-Width: 100px;"
                + "-fx-pref-height: 25px;"
                + "-fx-alignment: 'CENTER';";
        
        hboxId.setStyle(style);        
        hboxMesa.setStyle(style);
        hboxCliente.setStyle(style);
        
        String style2="-fx-font-size: 25px;"
                + "-fx-text-fill:  #E0EEF6;";
        
        labelOrden.setStyle(style2);
        labelOrden.setTextAlignment(TextAlignment.CENTER);
        
        labelnombreElemento.setStyle(style2);
        labelnombreElemento.setTextAlignment(TextAlignment.CENTER);
        
        labelnombreCliente.setStyle(style2);
        labelnombreCliente.setTextAlignment(TextAlignment.CENTER);
        
       hboxId.getChildren().add(labelOrden);
       hboxMesa.getChildren().add(labelnombreElemento);
       hboxCliente.getChildren().add(labelnombreCliente);
       
       this.getChildren().addAll(hboxId,hboxMesa,hboxCliente);
        
    }
}
