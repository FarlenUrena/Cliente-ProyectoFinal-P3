/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.pojos.ItemOrden;
import cr.ac.una.restuna.service.ElementoService;
import cr.ac.una.restuna.service.OrdenService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Respuesta;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author jeez
 */
public class OrdenesListModalController extends Controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private ScrollPane scrlPanePrincipal;

    @FXML
    private GridPane gridPanePrincipal;

    @FXML
    private Button btnGuardar;
    OrdenDto ordeneDto;
    List<OrdenDto> ordenesDto = new ArrayList<>();
    ElementodeseccionDto elementoDto;
    EmpleadoDto empleadoOnline;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {
        elementoDto = (ElementodeseccionDto) AppContext.getInstance().get("elementoToOrden");
        empleadoOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        for(OrdenDto o : obtenerOrdenes() ){
            if(o.getIdEmpleadoDto().getIdEmpleado().equals(empleadoOnline.getIdEmpleado()) 
                    && o.getIdElementodeseccionDto().getIdElemento().equals(elementoDto.getIdElemento())
                    && o.getEsEstado().equals(1L))
            ordenesDto.add(o);
        }
        
        cargarOrdenes();
    }

    @FXML
    void onActionBtnCancelar(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    void onActionBtnNueva(ActionEvent event) {

        ordeneDto = new OrdenDto();
        Date Date = convertToDateViaInstant(java.time.LocalDateTime.now());

        ordeneDto.setFechaCreacion(Date);

        ordeneDto.setIdElementodeseccionDto(elementoDto);
        ordeneDto.setEsEstado(1L);
        ordeneDto.setIdEmpleadoDto(empleadoOnline);
        OrdenService ordenService = new OrdenService();
        Respuesta resp = ordenService.guardarOrden(ordeneDto);
        ordeneDto = (OrdenDto) resp.getResultado("OrdenGuardada");
        AppContext.getInstance().set("OrdenActual", ordeneDto);
        FlowController.getInstance().goView("Ordenes");
        this.getStage().close();

    }

    void cargarOrdenes() {
        gridPanePrincipal.getChildren().clear();
        int row = 1;
//        DraggableMaker maker = new DraggableMaker();

        if (ordenesDto != null || ordenesDto.isEmpty()) {
            for (OrdenDto orden : ordenesDto) {
                if (orden.getEsEstado().equals(1l)) {
                    ItemOrden itemOrden = new ItemOrden(orden);
                    itemOrden.getBtnVer().setOnMouseClicked(MouseEvent -> {
                        AppContext.getInstance().set("ordenSeleccionada", itemOrden.getOrden());
                        FlowController.getInstance().goViewInWindowModalUncap("OrdenesView", this.getStage(), false);
                    });
                    gridPanePrincipal.add(itemOrden, 0, row);
                    row++;
                    GridPane.setMargin(itemOrden, new Insets(10));

                }

            }
        }

    }

    private List<OrdenDto> obtenerOrdenes() {
        OrdenService service = new OrdenService();
        Respuesta respuesta = service.getOrdenes();
        return (List<OrdenDto>) respuesta.getResultado("OrdenesList");
    }

    Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
