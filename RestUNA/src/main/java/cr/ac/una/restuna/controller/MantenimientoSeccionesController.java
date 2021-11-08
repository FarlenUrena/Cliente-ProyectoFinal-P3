/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    SeccionDto seccionDto;
    EmpleadoDto empleadoOnline;

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
//        seccionDto = (SeccionDto) AppContext.getInstance().get("SeccionActual");
//        txtNombre.setText(seccionDto.getNombre());
//        crearSeccionTemporal();

        // 
    }

    @Override
    public void initialize() {

        seccionDto = (SeccionDto) AppContext.getInstance().get("SeccionActual");
        elementosDto.clear();
//        elementosDto = seccionDto.getElementosdeseccionDto();
        cargarElementos();
        txtNombre.setText(seccionDto.getNombre());
        seccion.getChildren().clear();
        seccion.getChildren().add(lblDefault);
        empleadoOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        if (empleadoOnline.getRol() == 2) {
            
            hbContainer.getChildren().remove(vbEditorElementos);
//            vbEditorElementos.setVisible(false);
            btnGuardar.setVisible(false);
        } else if (empleadoOnline.getRol() == 3) {
            hbContainer.getChildren().remove(vbEditorElementos);
//            vbEditorElementos.resize(0, 0);
//            vbEditorElementos.setVisible(false);
            btnGuardar.setVisible(false);
            vbFacturar.setVisible(false);
        }
//        if(txtCant.getText().equals(""))SeteaMesas(4);
        //        else SeteaMesas(Integer.parseInt(txtCant.getText()));
        {

        }
    }

    void SeteaMesas(int cant) {
        DraggableMaker maker = new DraggableMaker();
        double layx = 0;

        for (int i = 0; i < cant; i++) {

            JFXButton mesa = new JFXButton();
            mesa.setStyle("-fx-background-color:black");

            mesa.setLayoutX(layx);
            layx = layx + 55;
            mesa.setOnMouseClicked((MouseEvent e) -> {
                if (mesa.getStyle().equals("-fx-background-color:green")) {
                    mesa.setStyle("-fx-background-color:red");
                } else {
                    mesa.setStyle("-fx-background-color:green");
                }
            });

            maker.makeDraggable(mesa);
            seccion.getChildren().add(mesa);
        }
    }

    void cargarElementos() {
        gridPanePrincipal.getChildren().clear();
        elementosDto = obtenerElementos();
        int row = 1;

        if (elementosDto != null) {
            for (ElementodeseccionDto elementoDto : elementosDto) {
                
                if (elementoDto.getIdSeccionDto().getIdSeccion().equals(seccionDto.getIdSeccion())) {
                    System.out.println("1 - "+elementoDto.getPosicionX().toString() +" - "+ elementoDto.getPosicionY().toString());
                    
                    if (elementoDto.getPosicionX() == 0D && elementoDto.getPosicionY() == 0D) {
                        
                        System.out.println("2 - "+elementoDto.getPosicionX().toString() +" - "+ elementoDto.getPosicionY().toString());
                        ItemElementoDeSeccion itemSeccion = new ItemElementoDeSeccion(elementoDto);
                        itemSeccion.btnAgregar.setOnMouseClicked(MouseEvent -> {
                            AppContext.getInstance().set("elementoGenerico", itemSeccion.getElementoGenerico());
                            FlowController.getInstance().goViewInWindowModalUncap("EditarElementosSeccionSecView", this.getStage(), false);
//                            SeteaMesas(1);
                            
                        });
                        gridPanePrincipal.add(itemSeccion, 0, row);
                        row++;
                        GridPane.setMargin(itemSeccion, new Insets(10));
                    }
                } else {
                    ItemElementoDeSeccionSecundario itemSeccion = new ItemElementoDeSeccionSecundario(elementoDto);
//                        itemSeccion.btnAgregar.setOnMouseClicked(MouseEvent -> {
////                            AppContext.getInstance().set("elementoGenerico", itemSeccion.getElementoGenerico());
////                            FlowController.getInstance().goViewInWindowModalUncap("EditarElementosSeccionSecView", this.getStage(), false);
////                            SeteaMesas(1);
//                            
//                        });
//                        itemSeccion.setLayoutX(row);
                        seccion.getChildren().add(itemSeccion);
//                        row++;
//                        GridPane.setMargin(itemSeccion, new Insets(10));
//                    ItemElementoDeSeccionSecundario
                    //crear los que van el anchor pane
                }
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

}
