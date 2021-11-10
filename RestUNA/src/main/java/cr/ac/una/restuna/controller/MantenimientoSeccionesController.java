/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.SeccionDto;
import cr.ac.una.restuna.pojos.ItemElementoDeSeccion;
import cr.ac.una.restuna.pojos.ItemElementoDeSeccionSecundario;
import cr.ac.una.restuna.pojos.ItemSecciones;
import cr.ac.una.restuna.service.ElementoService;
import cr.ac.una.restuna.service.SeccionService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.DraggableMaker;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Respuesta;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Kenda
 */
public class MantenimientoSeccionesController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private HBox hbContainer;
    @FXML
    private JFXButton btnAgregar;

    @FXML
    private ScrollPane scrlPanePrincipal1;

    @FXML
    private GridPane gridPanePrincipal;

    @FXML
    private JFXButton btnEliminar;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblDefault;
    @FXML
    private AnchorPane seccion;
    @FXML
    private VBox vbFacturar;
    @FXML
    private VBox vbEditorElementos;

    List<ElementodeseccionDto> elementosDto;
    List<ItemElementoDeSeccionSecundario> elementosInterfazSeccionSecundario;
    SeccionDto seccionDto;
    EmpleadoDto empleadoOnline;
    @FXML
    private VBox vbSalon;
    @FXML
    private JFXCheckBox chkBoxHabilitarEdicion;
    @FXML
    private ImageView ivCaja;

    @FXML
    void onActionBtnEliminar(ActionEvent event) {

    }

    @FXML
    void onActionBtnGuardar(ActionEvent event) {

    }

//    JFXButton mesa;
    @FXML
    void onAction_btnAgregar(ActionEvent event) {

        FlowController.getInstance().goViewInWindowModalUncap("EditarElementosSeccionView", this.getStage(), false);
        //TODO: REFRESCAR LA LISTA CON EL NUEVO ELEMENTO CREADO
//        DraggableMaker maker = new DraggableMaker();
//        double layx=0;
//
////        for(int i = 0;i < cant;i++){
//
//        JFXButton mesa = new JFXButton();
//        mesa.setStyle("-fx-background-color:black");
//
//        mesa.setLayoutX(layx);
//        layx=layx+55;
//        mesa.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//                if(mesa.getStyle().equals("-fx-background-color:green")) mesa.setStyle("-fx-background-color:red");
//                else  mesa.setStyle("-fx-background-color:green");
//            }
//        });
//        maker.makeDraggable(mesa);
//        seccion.getChildren().add(mesa);
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        elementosDto = new ArrayList<>();
        elementosInterfazSeccionSecundario= new ArrayList<>();
//        seccionDto = (SeccionDto) AppContext.getInstance().get("SeccionActual");
//        txtNombre.setText(seccionDto.getNombre());
//        crearSeccionTemporal();

        // 
    }

    @Override
    public void initialize() {

        seccionDto = (SeccionDto) AppContext.getInstance().get("SeccionActual");
        seccion.getChildren().clear();
        elementosDto.clear();
//        elementosDto = seccionDto.getElementosdeseccionDto();
        cargarElementos();
        txtNombre.setText(seccionDto.getNombre());

//        seccion.getChildren().add(lblDefault);
        empleadoOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        
        if (empleadoOnline.getRol() == 2) {

            hbContainer.getChildren().remove(vbEditorElementos);
//            vbEditorElementos.setVisible(false);
            btnGuardar.setVisible(false);
            
            //Edicion mesas
            chkBoxHabilitarEdicion.setDisable(true);
            chkBoxHabilitarEdicion.setVisible(false);
            
        } else if (empleadoOnline.getRol() == 3) {
            hbContainer.getChildren().remove(vbEditorElementos);
//            vbEditorElementos.resize(0, 0);
//            vbEditorElementos.setVisible(false);
            btnGuardar.setVisible(false);
            vbFacturar.setVisible(false);
            
            //Edicion mesas
            chkBoxHabilitarEdicion.setDisable(true);
            chkBoxHabilitarEdicion.setVisible(false);
        }
//        if(txtCant.getText().equals(""))SeteaMesas(4);
        //        else SeteaMesas(Integer.parseInt(txtCant.getText()));
        {

        }
    }

    void SeteaMesas(int cant, double layx, double layy) {
//        double layx = 0;

        for (int i = 0; i < cant; i++) {

            JFXButton mesa = new JFXButton();
            mesa.setStyle("-fx-background-color:black");

            mesa.setLayoutX(layx);
            mesa.setLayoutX(layy);
            mesa.setOnMouseClicked((MouseEvent e) -> {
                if (mesa.getStyle().equals("-fx-background-color:green")) {
                    mesa.setStyle("-fx-background-color:red");
                } else {
                    mesa.setStyle("-fx-background-color:green");
                }
            });

//            maker.makeDraggable(mesa);
            seccion.getChildren().add(mesa);
        }
    }
    double left;
    double top;
    double right;
    double bottom;
    double posx;
    double posy;

    private double stDragX;
    private double stDragY;
    
    void cargarElementos() {
        gridPanePrincipal.getChildren().clear();
        elementosDto = obtenerElementos();
        left = seccion.getLayoutBounds().getMinX() + 50;
        top = seccion.getLayoutBounds().getMinY() + 50;
        right = seccion.getLayoutBounds().getMaxX() - 50;
        bottom = seccion.getLayoutBounds().getMaxY() - 50;
        DraggableMaker maker = new DraggableMaker();
        int row = 1;
//        DraggableMaker maker = new DraggableMaker();

        if (elementosDto != null) {
            for (ElementodeseccionDto elementoDto : elementosDto) {

                if (elementoDto.getIdSeccionDto().getIdSeccion().equals(seccionDto.getIdSeccion())) {
                    if (elementoDto.getPosicionX() == 0D && elementoDto.getPosicionY() == 0D) {
                        
                        ItemElementoDeSeccion itemSeccion = new ItemElementoDeSeccion(elementoDto);
                        itemSeccion.btnAgregar.setOnMouseClicked(MouseEvent -> {
                            AppContext.getInstance().set("elementoGenerico", itemSeccion.getElementoGenerico());
                            FlowController.getInstance().goViewInWindowModalUncap("EditarElementosSeccionSecView", this.getStage(), false);
                        });
                        gridPanePrincipal.add(itemSeccion, 0, row);
                        row++;
                        GridPane.setMargin(itemSeccion, new Insets(10));
                        
                    } else {
                    ItemElementoDeSeccionSecundario itemSeccionDragg = new ItemElementoDeSeccionSecundario(elementoDto);
                    EmpleadoDto empOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
                       //El elemento que se cargue en el lienzo, debe contener propiedades según el tipo de usuario que hace uso de la aplicación
                    //En caso de que sea un admin
                    if(empOnline.getRol() == 1){
                            itemSeccionDragg.MakeDraggableCajero(ivCaja);
                            itemSeccionDragg.MakePressedSalonero();
                    }else{ //En caso de que sea un cajero 
                        if(empOnline.getRol() == 2){
                            itemSeccionDragg.MakeDraggableCajero(ivCaja);
                            itemSeccionDragg.MakePressedSalonero();
                    }else{ //En caso de que sea un salonero
                        if(empOnline.getRol() == 3){
                            itemSeccionDragg.MakePressedSalonero();
                    }
                        
                    }
                    
                    }
                        
                    seccion.getChildren().add(itemSeccionDragg);
                    elementosInterfazSeccionSecundario.add(itemSeccionDragg);
                    }
                }
            }
        }

    }

    
    private void validarDraggableAdmin(){
        if(chkBoxHabilitarEdicion.isSelected()){
            for(ItemElementoDeSeccionSecundario it : elementosInterfazSeccionSecundario){
                it.MakeDraggableAdmin();
            }
        }else{
            for(ItemElementoDeSeccionSecundario it : elementosInterfazSeccionSecundario){
            it.MakeDraggableCajero(ivCaja);
            it.MakePressedSalonero();
            }
        }
    }
    
    private List<ElementodeseccionDto> obtenerElementos() {
        ElementoService service = new ElementoService();
        Respuesta respuesta = service.getElementos();
        return (List<ElementodeseccionDto>) respuesta.getResultado("ElementosList");
    }

    private byte[] FileTobyte(File f) {
        try {
            BufferedImage bufferimage;
            bufferimage = ImageIO.read(f);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferimage, "png", output);
            byte[] data = output.toByteArray();
            return data;
        } catch (IOException ex) {
            Logger.getLogger(EditarElementosDeSeccionController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @FXML
    private void onActionButtonHabilitarEdicion(ActionEvent event) {
        validarDraggableAdmin();
    }

}
