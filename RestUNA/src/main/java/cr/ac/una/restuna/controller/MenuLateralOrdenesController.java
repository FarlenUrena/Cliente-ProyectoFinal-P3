/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.pojos.ItemOrdenLateral;
import cr.ac.una.restuna.service.OrdenService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
    private VBox root;
    @FXML
    private ScrollPane scrlPanePrincipal;
    @FXML
    private GridPane gridPanePrincipal;
    @FXML
    private JFXButton btnFacturacion;

    private List<OrdenDto> ordenesDto = new ArrayList<>();
    private EmpleadoDto empleadoOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
    OrdenDto ordenDto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (empleadoOnline.getRol() == 3) {
            btnFacturacion.setVisible(false);
        }
        
        ordenesDto.clear();
        if (empleadoOnline.getRol().equals(3L)) {

            for (OrdenDto o : obtenerOrdenes()) {
                if (o.getIdEmpleadoDto().getIdEmpleado().equals(empleadoOnline.getIdEmpleado())
                        && o.getEsEstado().equals(1L)) {
                    ordenesDto.add(o);
                }
            }
        } else {
            for (OrdenDto o : obtenerOrdenes()) {
                if ( o.getEsEstado().equals(1L)) {
                    ordenesDto.add(o);
                }
            }
        }

        cargarOrdenes();
    }

    @Override
    public void initialize() {
        
    }

    @FXML
    void onActionBtbFacturacion(ActionEvent event) {

    }

    private void iniciarParaSalonero() {
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

    void cargarOrdenes() {
        gridPanePrincipal.getChildren().clear();
        int row = 1;

        if (ordenesDto != null && !ordenesDto.isEmpty()) {
            for (OrdenDto orden : ordenesDto) {

                ItemOrdenLateral itemOrden = new ItemOrdenLateral(orden);
                itemOrden.getBtnVer().setOnMouseClicked(MouseEvent -> {
                    AppContext.getInstance().set("OrdenActual", itemOrden.getOrden());
                    FlowController.getInstance().goView("Ordenes");
//                    this.getStage().close();
                });
                gridPanePrincipal.add(itemOrden, 0, row);
                row++;
                GridPane.setMargin(itemOrden, new Insets(10));

            }
        }

    }

    private List<OrdenDto> obtenerOrdenes() {
        OrdenService service = new OrdenService();
        Respuesta respuesta = service.getOrdenes();
        return (List<OrdenDto>) respuesta.getResultado("OrdenesList");
    }

}
