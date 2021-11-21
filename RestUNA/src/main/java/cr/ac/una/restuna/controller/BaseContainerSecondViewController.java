/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import cr.ac.una.restuna.App;
import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.pojos.ItemOrdenLateral;
import cr.ac.una.restuna.service.ElementoService;
import cr.ac.una.restuna.service.OrdenService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class BaseContainerSecondViewController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private HBox hbHeader;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblTitulo1;
    @FXML
    private JFXButton btnContraer;
    @FXML
    private JFXButton btnMaxMin;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private HBox hbButtonContainer;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private VBox centerVBox;
    @FXML
    private VBox root1;
    @FXML
    private JFXButton btnHome;
    @FXML
    private ScrollPane scrlPanePrincipal;
    @FXML
    private GridPane gridPanePrincipal;
    @FXML
    private JFXButton btnFacturacion;
    @FXML
    private JFXButton btnNuevaOrden;

    private List<OrdenDto> ordenesDto = new ArrayList<>();
    private EmpleadoDto empleadoOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
    OrdenDto ordenDto;
    boolean isContracted = false;
    Controller controller;
    Double xOffset = 0D, yOffset = 0D;
    VBox vboxLateral = new VBox();
    ElementodeseccionDto elementoDto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        FlowController.getInstance().makeDragable(hbHeader);

        //TODO: CREAR ELEMETO GENERICO
        root1.setVisible(isContracted);
        drawerHamb();

    }

    private void drawerHamb() {
        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(hamburger);
        transition.setRate(-1);

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            drawer.toggle();
            drawer.setMaxWidth(0);
        });
        drawer.setOnDrawerOpening((event) -> {
            cargarOrdenes();
            root1.setVisible(isContracted);
            isContracted = false;
            transition.setRate(transition.getRate() * -1);
            transition.play();
            root.setLeft(drawer);
            drawer.setMaxWidth(250);

//            hamburger.setAlignment(Pos.CENTER_RIGHT);
        });

        drawer.setOnDrawerClosed((event) -> {

            root1.setVisible(isContracted);
            isContracted = true;

            transition.setRate(transition.getRate() * -1);
            transition.play();
            root.setLeft(null);
            drawer.setMaxWidth(0);
//            hamburger.setAlignment(Pos.CENTER_LEFT);
        });
    }

    @FXML
    private void onAction_btnContraer(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void onAction_btnMaxMin(ActionEvent event) {
        if (((Stage) root.getScene().getWindow()).isMaximized()) {
            ((Stage) root.getScene().getWindow()).setMaximized(false);
        } else {
            ((Stage) root.getScene().getWindow()).setMaximized(true);
        }
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnHome(ActionEvent event) {

        FlowController.getInstance().goView("SeccionesGalleryView");
    }

    @Override
    public void initialize() {
        if (empleadoOnline.getRol() == 3) {
            btnNuevaOrden.setVisible(false);
        } else {
            btnNuevaOrden.setVisible(true);
        }

    }

    @FXML
    void onActionBtbFacturacion(ActionEvent event) {
        AppContext.getInstance().set("ultimaVentana", "Facturacion");
        FlowController.getInstance().goViewInWindowModalUncap("AllOrdenesListView", this.getStage(), false);
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
                if (o.getEsEstado().equals(1L)) {
                    ordenesDto.add(o);
                }
            }
        }

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

    @FXML
    void onAction_btnNuevaOrden(ActionEvent event) {
        ElementoService service = new ElementoService();
        Respuesta respuesta = service.getElemento(11L);

        if (respuesta.getEstado()) {
            elementoDto = (ElementodeseccionDto) respuesta.getResultado("Elemento");

            ordenDto = new OrdenDto();
            Date Date = convertToDateViaInstant(java.time.LocalDateTime.now());
            ordenDto.setFechaCreacion(Date);

            ordenDto.setIdElementodeseccionDto(elementoDto);
            ordenDto.setEsEstado(1L);
            ordenDto.setIdEmpleadoDto(empleadoOnline);
            AppContext.getInstance().set("OrdenActual", ordenDto);

            FlowController.getInstance().goView("Ordenes");
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), "Erro creando la orden desde la caja");

        }
    }

    Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

}
