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
import cr.ac.una.restuna.util.FlowController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.ByteArrayInputStream;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
    public Button btnOrdenes = new Button();
    public Button btnAgregar = new Button();
    public Label lblStatus = new Label();
//    private SeccionDto idSeccion;
    ImageView iv;

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
        this.setCursor(Cursor.HAND);
//        if(empOnline.getRol() == 1){
//            //Hace el draggable para el admin
//        MakeDraggable();
//        }

    }

//    public ItemElementoDeSeccionSecundario(ItemElementoDeSeccionSecundario bv) {
//    this = bv;
//    }
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
        this.setStyle(
                "-fx-pref-Width: 150px;"
                + "-fx-pref-height: 200px;"
                + "-fx-alignment: 'TOP_CENTER';"
                + "-fx-spacing: 5px;"
                + "-fx-background-color:#4F4652;"
                + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(gaussian, rgb(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
        //                "-fx-pref-Width: 100px;"
        //                + "-fx-pref-height: 100px;"
        //                + "-fx-max-width: 100px;"
        //                + "-fx-max-height:100px;"
        //                + "-fx-alignment: 'TOP_CENTER';"
        //                + "-fx-spacing: 5px;"
        //                + "-fx-background-color:#735751;"
        //                + "-fx-background-radius: 10;"

        );
        this.setLayoutX(this.elementoDto.getPosicionX()+350);
        this.setLayoutY(this.elementoDto.getPosicionY()+250);
        this.toggleOcupada();
    }

    private void agregarDatos(Image i) {
        Label nombre = new Label(this.nombre);
        nombre.setStyle("-fx-font-size: 15px;"
                + "-fx-text-fill:  #E0EEF6;"
                + "-fx-max-width: 75px;"
                + "-fx-text-alignment: 'CENTER';"
        );
//        nombre.setWrapText(true);
//        nombre.setMaxWidth(75D);
        HBox hboxI = new HBox();
        hboxI.setStyle(
                "-fx-background-color:#4F4652;"
                + "-fx-background-radius: 10px;"
                + "-fx-pref-width: 75px;"
                + "-fx-max-width: 75px;"
                + "-fx-max-height: 75px ;"
                + "-fx-pref-height: 75px;"
                + "-fx-alignment: 'CENTER';"
        //                "-fx-background-color:#4F4652;"
        //                + "-fx-background-radius: 10px;"
        //                "-fx-pref-width: 75px;"
        //                + "-fx-pref-height: 75px;"
        //                + "-fx-max-width: 75px;"
        //                + "-fx-max-height:75px;"
        //                + "-fx-alignment: 'CENTER';"
        //                + "-fx-effect: dropshadow(gaussian, rgba(0.0, 0.0, 0.0, 0.15), 10.0, 0.7, 0.0,1.5);"
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
//        lblStatus.setStyle("-fx-font-size: 15px;"
//                + "-fx-text-alignment: 'CENTER';"
//        );
        hboxI.getChildren().add(iv);
        btnCont.getChildren().addAll(btnOrdenes);

        //lo que va en vbox final
        this.getChildren().add(nombre);
        this.getChildren().add(hboxI);
        this.getChildren().add(btnCont);
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
    private double stDragX = 0;
    private double stDragY = 0;

    public void MakePressedSalonero() {
        //Todo Generar Orden
        this.setOnMouseClicked(onDragDetectedSALONERO);

        this.setOnMousePressed(null);
        this.setOnMouseDragged(null);

    }

    EventHandler<MouseEvent> onDragDetectedSALONERO
            = (MouseEvent t) -> {
                System.out.println("Me clickeaste");

            };

    public void MakeDraggableCajero(Object toAcceptTransfer) {
        // mover a la caja
        this.setOnDragDetected(onDragDetectedCAJERO);
        ((ImageView) toAcceptTransfer).setOnDragOver(event -> dragDetected(event, toAcceptTransfer));
        ((ImageView) toAcceptTransfer).setOnDragDropped(event -> dragDropped(event, toAcceptTransfer));

        this.setOnMousePressed(null);
        this.setOnMouseDragged(null);

    }

    public void dragDetected(DragEvent event, Object i) {
        if (event.getGestureSource() != i
                && event.getDragboard().hasImage()) {
           
            event.acceptTransferModes(TransferMode.MOVE);
            System.out.println("Sobre la caja...");
        }
    }

    public void dragDropped(DragEvent event, Object i) {
        Dragboard db = event.getDragboard();
        if (db.hasImage() && i != null) {
//       TODO abrir vista de facturas

            event.setDropCompleted(true);
            System.out.println("Pegado...");
        }
    }

    EventHandler<MouseEvent> onDragDetectedCAJERO
            = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            Dragboard db;
            db = iv.startDragAndDrop(TransferMode.MOVE);
      
            ClipboardContent content = new ClipboardContent();
            content.putImage(iv.getImage());
            
            content.getImage();
            db.setContent(content);
            t.consume();
        }
    };

    public void MakeDraggableAdmin() {

        this.setOnMousePressed(OnMousePressedEventHandlerADMIN);
        this.setOnMouseDragged(OnMouseDraggedEventHandlerADMIN);

        this.setOnDragDetected(null);
        this.setOnMouseClicked(null);

    }

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    EventHandler<MouseEvent> OnMousePressedEventHandlerADMIN
            = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((VBox) (t.getSource())).getTranslateX();
            orgTranslateY = ((VBox) (t.getSource())).getTranslateY();
        }
    };

    EventHandler<MouseEvent> OnMouseDraggedEventHandlerADMIN
            = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            //Validacion de tamaños para 
            if (newTranslateX >= -350 && newTranslateX <= 250 && newTranslateY >= -250 && newTranslateY <= 150) {
                ((VBox) (t.getSource())).setTranslateX(newTranslateX);
                ((VBox) (t.getSource())).setTranslateY(newTranslateY);
            }
            elementoDto.setPosicionX(newTranslateX);
            elementoDto.setPosicionY(newTranslateY);
            
            System.out.println("X : " + newTranslateX); //-350 a 250
            System.out.println("Y : " + newTranslateY); //-250 a  150
            
        }
    };

}
