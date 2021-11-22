/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import cr.ac.una.restuna.model.CajaDto;
import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.service.CajaService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class BaseContainerViewController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private Label lblUsuario;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblTitulo1;
    @FXML
    private VBox centerVBox;
    @FXML
    private JFXButton btnContraer;

    @FXML
    private JFXButton btnMaxMin;

    @FXML
    private JFXButton btnSalir;
    @FXML
    private HBox hbHeader;
    @FXML
    private HBox hbButtonContainer;

    @FXML
    private JFXButton btnEmpleados;

    @FXML
    private JFXButton btnFactura;

    @FXML
    private JFXButton btnSecciones;

    @FXML
    private JFXButton btnProductos;

    @FXML
    private JFXButton btnOrdenes;

    @FXML
    private JFXButton btnParametros;

    @FXML
    private JFXButton btnCierreCaja;
//      @FXML
//    private HBox leftBox;
    @FXML
    private JFXButton btnReporte;

    List<CajaDto> cajasList = new ArrayList();
    CajaDto caja;
    EmpleadoDto empOnline;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        FlowController.getInstance().makeDragable(hbHeader);
        // TODO
        empOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        if (empOnline != null) {
            lblUsuario.setText(empOnline.getNombreUsuario());
        }
//        AppContext.getInstance().set("centerBox", centerVBox);
//        root.setLeft(null);
//        drawerHamb();
//    ((Stage) root.getScene().getWindow()).initStyle(StageStyle.UNDECORATED);
    }

    @Override
    public void initialize() {

    }

    private void drawerHamb() {
        try {
            VBox vbox = FXMLLoader.load(getClass().getResource("/cr/ac/una/restuna/view/MenuLateralView.fxml"));
            drawer.setSidePane(vbox);
            drawer.setMinWidth(0);
        } catch (IOException ex) {
            Logger.getLogger(BaseContainerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(hamburger);
        transition.setRate(-1);

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            drawer.toggle();
        });
        drawer.setOnDrawerOpening((event) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            root.setLeft(drawer);
            drawer.setMinWidth(250);
            hamburger.setAlignment(Pos.CENTER_RIGHT);
        });

        drawer.setOnDrawerClosed((event) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            root.setLeft(null);
            drawer.setMinWidth(0);
            hamburger.setAlignment(Pos.CENTER_LEFT);
        });
    }

    @FXML
    private void onActionBtnEmpleados(ActionEvent event) {
        FlowController.getInstance().goView("EmpleadoView");
//        FlowController.getInstance().goView();
    }

    @FXML
    private void onActionBtnFacturas(ActionEvent event) {

        AppContext.getInstance().set("ultimaVentana", "Facturacion");
        FlowController.getInstance().goViewInWindowModalUncap("AllOrdenesListView", this.getStage(), false);
    }

    @FXML
    private void onActionBtnSecciones(ActionEvent event) {
        //        FlowController.getInstance().goView("");
        FlowController.getInstance().goView("SeccionesGalleryView");
    }

    @FXML
    private void onActionBtnProductos(ActionEvent event) {
        //        FlowController.getInstance().goView("");
        FlowController.getInstance().goView("MantenimientoProductosView");
    }

    @FXML
    private void onActionBtnOrdenes(ActionEvent event) {
        AppContext.getInstance().set("elementoToOrden", new ElementodeseccionDto());
        AppContext.getInstance().set("ultimaVentana", "Ordenes");
        FlowController.getInstance().goViewInWindowModalUncap("AllOrdenesListView", this.getStage(), false);
    }

    @FXML
    private void onActionBtnParametros(ActionEvent event) {
        FlowController.getInstance().goView("ParametrosStaticosView");
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    void onAction_btnContraer(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).setIconified(true);

    }

    @FXML
    void onAction_btnMaxMin(ActionEvent event) {
        if (((Stage) root.getScene().getWindow()).isMaximized()) {
            ((Stage) root.getScene().getWindow()).setMaximized(false);
        } else {
            ((Stage) root.getScene().getWindow()).setMaximized(true);
        }
    }

//    private void transitionView(String view){
////        VBox centerVBox = (VBox) AppContext.getInstance().get("centerBox");
////        
////        final double alturaInicio = centerVBox.getHeight();
////        
////        final Animation hideCenter = new Transition() {
////            { setCycleDuration(Duration.millis(200)); }
////            protected void interpolate(double frac) {
////                final double curWidth = alturaInicio * (1.0 - frac);
////                centerVBox.setTranslateY(-alturaInicio + curWidth);
////            }
////            
////        };
////        final Animation showCenter = new Transition() {
////            { setCycleDuration(Duration.millis(200)); }
////            protected void interpolate(double frac) {
////                final double curWidth = alturaInicio * (2.0 - frac);
////                centerVBox.setTranslateY((alturaInicio-(alturaInicio*2)) + curWidth);
////            }
////        };
////        hideCenter.onFinishedProperty().set(new EventHandler<ActionEvent>() {
////            @Override public void handle(ActionEvent actionEvent) {
////                centerVBox.setVisible(false);
////                showCenter.play();
////                FlowController.getInstance().goView(view);
////                centerVBox.setVisible(true);
////                showCenter.play();
////            }
////        });
////        hideCenter.play();
//         FlowController.getInstance().goView(view);
//    }
    @FXML
    private void onActionBtnReporte(ActionEvent event) {
        FlowController.getInstance().goView("ReportesView");
    }

    @FXML
    void onActionBtnCierreCaja(ActionEvent event) {
        if (seleccionarCajaActual()) {
            String ModalResp = "";
            FlowController.getInstance().goViewInWindowModalUncap("CajaCierreModalView", this.getStage(), false);
            ModalResp = (String) AppContext.getInstance().get("cajaCierreModal");
            if (ModalResp.equals("ok")) {
                double se = Double.valueOf((String) AppContext.getInstance().get("saldoEfectivoMarcado"));
                double st = Double.valueOf((String) AppContext.getInstance().get("saldoTargetaMarcado"));

                caja.setSaldoEfectivoCierre(se);
                caja.setSaldoTarjetaCierre(st);
                Date fechaCierre = Date.from(Instant.now());
//        Date Date = convertToDateViaInstant(java.time.LocalDateTime.now());
                caja.setFechaCierre(fechaCierre);
                caja.setEsActiva(2L);
                CajaService serviceC = new CajaService();
                Respuesta respuestaC = serviceC.guardarCaja(caja);
                if (!respuestaC.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Cierre de caja", getStage(), "No se pudo completar el cierrre de caja");
                } else {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Cargar producto", getStage(), "Caja Cerrada Correctamente.");
                    //TODO:
                    //REPORTE

                }
            } else if (ModalResp.equals("caceled")) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Cierre de caja cancelado");

            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), "No hay una caja abierta en este momento.\nFactura algo primero para abrir una nueva.");
        }
    }

   private boolean seleccionarCajaActual() {
        boolean hayCaja = true;
        cajasList.clear();
        cajasList = obtenerTodasCajas();
        caja = new CajaDto();
        CajaDto cajaPrueba = new CajaDto();

        if (cajasList != null && !cajasList.isEmpty()) {
            for (CajaDto x : cajasList) {
                if (x.getEsActiva().equals(1L) && x.idEmpleadoDto.getIdEmpleado().equals(empOnline.getIdEmpleado())) {
                    cajaPrueba = x;
                    break;
                } else {
                    cajaPrueba = null;
                }
            }
            if (cajaPrueba != null) {
                caja = cajaPrueba;
                   hayCaja =  true;
            } else {
                //No encontró caja, creela
                hayCaja =  false;
            }
        }
        return hayCaja;
    }

    private List<CajaDto> obtenerTodasCajas() {
        CajaService service = new CajaService();
        Respuesta respuesta = service.getCajas();
        return (List<CajaDto>) respuesta.getResultado("CajasList");
    }

}
