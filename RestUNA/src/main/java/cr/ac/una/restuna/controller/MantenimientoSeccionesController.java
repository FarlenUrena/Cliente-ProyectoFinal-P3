/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ElementoDto;
import cr.ac.una.restuna.model.SeccionDto;
import cr.ac.una.restuna.pojos.ItemElementoDeSeccion;
import cr.ac.una.restuna.pojos.ItemSecciones;
import cr.ac.una.restuna.service.ElementoService;
import cr.ac.una.restuna.service.SeccionService;
import cr.ac.una.restuna.util.DraggableMaker;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Respuesta;
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

    @FXML
    void onActionBtnEliminar(ActionEvent event) {

    }

    @FXML
    void onActionBtnGuardar(ActionEvent event) {

    }
    
//    JFXButton mesa;
     @FXML
    void onAction_btnAgregar(ActionEvent event) {
         FlowController.getInstance().goViewInWindowModalUncap("EditarElementosSeccionView",this.getStage(), false);
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

    List<ElementoDto> elementosDto;
    
    void SeteaMesas(int cant){     
        DraggableMaker maker = new DraggableMaker();
        double layx=0;
        
        for(int i = 0;i < cant;i++){
    
        JFXButton mesa = new JFXButton();
        mesa.setStyle("-fx-background-color:black");
       
        mesa.setLayoutX(layx);
        layx=layx+55;
        mesa.setOnMouseClicked((MouseEvent e) -> {
            if(mesa.getStyle().equals("-fx-background-color:green")) mesa.setStyle("-fx-background-color:red");
            else  mesa.setStyle("-fx-background-color:green");
        });
        
        maker.makeDraggable(mesa);
        seccion.getChildren().add(mesa);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
//        seccion.getChildren().clear();
//        if(txtCant.getText().equals(""))SeteaMesas(4);
//        else SeteaMesas(Integer.parseInt(txtCant.getText()));
        
    }
    void cargarElementos(){
        gridPanePrincipal.getChildren().clear();
        elementosDto =  obtenerElementos();
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
        if(!elementosDto.isEmpty() || elementosDto != null){

            for (ElementoDto elementoDto : elementosDto) {
                ItemElementoDeSeccion itemSeccion = new ItemElementoDeSeccion(elementoDto);
                //TODO: HACER DRAGABLE LA MIERDA
//                itemSeccion.setOnMouseClicked(MouseEvent -> {
//                    //ver salon
////                        cargarProducto(ip.getIdProduct());
//                });
                if (col == 4) {
                    col = 1;
                    row++;
                }
                gridPanePrincipal.add(itemSeccion, col++, row);
                GridPane.setMargin(itemSeccion, new Insets(10));
            }
        }


    }
    private List<ElementoDto> obtenerElementos() {
        ElementoService service = new ElementoService();
        Respuesta respuesta = service.getElementos();
        return (List<ElementoDto>) respuesta.getResultado("ElemetosList");
    }
    
}
