/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.pojos.ItemOrdenLateral;
import cr.ac.una.restuna.service.OrdenService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class MenuLateralOrdenesController extends Controller implements Initializable {


    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ScrollPane scrlPanePrincipal;
    @FXML
    private GridPane gridPanePrincipal;
    
    private List<OrdenDto> ordenes = new ArrayList<>();
    
    private EmpleadoDto empleadoOnline=(EmpleadoDto)AppContext.getInstance().get("Usuario");
    
    OrdenDto ordenDto;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if( empleadoOnline.getRol() == 3){
            
        }else{
        
        }
        
    }    

    @Override
    public void initialize() {
    
    }
    
    private void iniciarParaSalonero(){
//        TODO visualizar las ordenes en curso del salonero que ingresó al sistema
//        ordenes = empleadoOnline.get
        
        
    }
    
        private void cargarOrden(Long id) {
        OrdenService service = new OrdenService();
        Respuesta respuesta = service.getOrden(id);

        if (respuesta.getEstado()) {
//            unbindOrden();
            ordenDto = (OrdenDto) respuesta.getResultado("Orden");
//            bindOrden(false);
//            validarRequeridos();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), respuesta.getMensaje());
        }
    }
    
        private List<OrdenDto> obtenerOrdenes() {
        OrdenService service = new OrdenService();
        Respuesta respuesta = service.getOrdenes();
        return (List<OrdenDto>) respuesta.getResultado("OrdenesList");
    }
        
        private void iniciarParaCajeroAdmin() {
        ordenes = obtenerOrdenes();
        
        if (ordenes != null || !ordenes.isEmpty()) {
//            List<ProductoDto> productos2 = new ArrayList<>();
//            productos2 = obtenerProductos();
//            if (productos.equals(productos2)) {
//                productos = productos2;
//            }
            gridPanePrincipal.getChildren().clear();
            if (ordenes != null) {
                int col = 0;
                int row = 1;

                for (OrdenDto od : ordenes) {
                    ItemOrdenLateral io = new ItemOrdenLateral(od);

//                    io.setOnMouseClicked(MouseEvent -> {
//                        cargarOrden(io.getIdOrden());
//                    });
                    if (col == 1) {
                        col = 0;
                        row++;
                    }
                    gridPanePrincipal.add(io, col++, row);
                    GridPane.setMargin(io, new Insets(10));
                }

            }
        }

    }
    
}
