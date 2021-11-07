/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.SeccionDto;
import cr.ac.una.restuna.pojos.ItemElementoDeSeccion;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private AnchorPane seccion;

    List<ElementodeseccionDto> elementosDto;
    SeccionDto seccionDto;

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
        cargarElementos();
        seccionDto = (SeccionDto) AppContext.getInstance().get("SeccionActual");
        txtNombre.setText(seccionDto.getNombre());
//        seccion.getChildren().clear();
//        if(txtCant.getText().equals(""))SeteaMesas(4);
//        else SeteaMesas(Integer.parseInt(txtCant.getText()));

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
        int col = 1;
        int row = 1;

//        ItemSecciones itemSeccion = new ItemSecciones();
//        gridPanePrincipal.add(itemSeccion, 0, 1);
//        itemSeccion.setOnMouseClicked(MouseEvent -> {
//            //crear salon
//            FlowController.getInstance().goView("SeccionesEditorView");
////                        cargarProducto(ip.getIdProduct());
//        });
//        GridPane.setMargin(itemSeccion, new Insets(10));
        if (elementosDto != null) {

            for (ElementodeseccionDto elementoDto : elementosDto) {
                ItemElementoDeSeccion itemSeccion = new ItemElementoDeSeccion(elementoDto);
                //TODO: HACER DRAGABLE LA MIERDA
//                itemSeccion.setOnMouseClicked(MouseEvent -> {
//                    //ver salon

////                        cargarProducto(ip.getIdProduct());
//                });
                if (col == 1) {
                    col = 0;
                    row++;
                }
                gridPanePrincipal.add(itemSeccion, col++, row);

                GridPane.setMargin(itemSeccion, new Insets(10));
            }
        }

    }

//    private void crearSeccionTemporal() {
//        seccionDto.setNombre("NoAsignado12");
//        
//        File f = new File(getClass().getResource("/cr/ac/una/restuna/resources/imageEmpty.png").getFile());
//        seccionDto.setFotoDistribucion(FileTobyte(f));
//        
//        SeccionService service = new SeccionService();
//        Respuesta respuesta = service.guardarSeccion(seccionDto);
//        
//        seccionDto = (SeccionDto) respuesta.getResultado("Seccion");
//        AppContext.getInstance().set("SeccionActual", seccionDto);
//    }

    private List<ElementodeseccionDto> obtenerElementos() {
        ElementoService service = new ElementoService();
        Respuesta respuesta = service.getElementos();
        return (List<ElementodeseccionDto>) respuesta.getResultado("ElemetosList");
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
