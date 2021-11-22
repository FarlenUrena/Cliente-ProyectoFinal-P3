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
import cr.ac.una.restuna.model.CajaDto;
import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.model.ParametroDto;
import cr.ac.una.restuna.model.ReporteDto;
import cr.ac.una.restuna.pojos.ItemOrdenLateral;
import cr.ac.una.restuna.service.CajaService;
import cr.ac.una.restuna.service.ElementoService;
import cr.ac.una.restuna.service.EmpleadoService;
import cr.ac.una.restuna.service.OrdenService;
import cr.ac.una.restuna.service.ParametroService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

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
    @FXML
    private JFXButton btnCierreCaja;

    private List<OrdenDto> ordenesDto = new ArrayList<>();
    OrdenDto ordenDto;
    boolean isContracted = false;
    Controller controller;
    Double xOffset = 0D, yOffset = 0D;
    VBox vboxLateral = new VBox();
    ElementodeseccionDto elementoDto;
    List<CajaDto> cajasList = new ArrayList();
    CajaDto caja;
    EmpleadoDto empOnline;
    ReporteDto reporte = new ReporteDto();
    ParametroDto parametro;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarParametros();
        FlowController.getInstance().makeDragable(hbHeader);
        empOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        //TODO: CREAR ELEMETO GENERICO
        root1.setVisible(isContracted);

        if (empOnline.getRol().equals(3L)) {
            btnNuevaOrden.setVisible(false);
            root.getChildren().remove(hamburger);
            root.getChildren().remove(drawer);
            hamburger.setVisible(false);

        } else {
            btnNuevaOrden.setVisible(true);
            drawerHamb();
        }

    }

    @Override
    public void initialize() {
        cargarParametros();
        empOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
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
            ordenDto = (OrdenDto) respuesta.getResultado("Orden");
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), respuesta.getMensaje());
        }
    }

    void cargarOrdenes() {

        if (empOnline.getRol() == 3) {
            btnFacturacion.setVisible(false);
        }

        ordenesDto.clear();
        if (empOnline.getRol().equals(3L)) {

            for (OrdenDto o : obtenerOrdenes()) {
                if (o.getIdEmpleadoDto().getIdEmpleado().equals(empOnline.getIdEmpleado())
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
        Respuesta respuesta = service.getElemento(1L);

        if (respuesta.getEstado()) {
            elementoDto = (ElementodeseccionDto) respuesta.getResultado("Elemento");

            ordenDto = new OrdenDto();
            Date Date = convertToDateViaInstant(java.time.LocalDateTime.now());
            ordenDto.setFechaCreacion(Date);

            ordenDto.setIdElementodeseccionDto(elementoDto);
            ordenDto.setEsEstado(1L);
            ordenDto.setIdEmpleadoDto(empOnline);
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

    @FXML
    void onActionBtnCierreCaja(ActionEvent event) {
        if (seleccionarCajaActual()) {
            String ModalResp = "";
            AppContext.getInstance().set("cajaCerrar", caja);
            FlowController.getInstance().goViewInWindowModalUncap("CajaCierreModalView", this.getStage(), false);
            ModalResp = (String) AppContext.getInstance().get("cajaCierreModal");
            if (ModalResp.equals("ok")) {
                double se = Double.valueOf((String) AppContext.getInstance().get("saldoEfectivoMarcado"));
                double st = Double.valueOf((String) AppContext.getInstance().get("saldoTargetaMarcado"));

                caja.setSaldoEfectivoCierre(se);
                caja.setSaldoTarjetaCierre(st);
                Date fechaCierre = Date.from(Instant.now());
                caja.setFechaCierre(DateWithoutTime(fechaCierre));
                caja.setEsActiva(2L);
                CajaService serviceC = new CajaService();
                Respuesta respuestaC = serviceC.guardarCaja(caja);
                if (!respuestaC.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Cierre de caja", getStage(), "No se pudo completar el cierrre de caja");
                } else {
                    try {
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Cargar producto", getStage(), "Caja Cerrada Correctamente.");
                        //TODO:
                        //REPORTE
                        //REPORTE
                        reporte = new ReporteDto();
                        reporte.setTipo(5);
                        CrearReporte();
                        //Mostrar Factura
                        abrirarchivo(tempFile);
                    } catch (ParseException | IOException ex) {
                        Logger.getLogger(BaseContainerSecondViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    

                }
            } else if (ModalResp.equals("caceled")) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Cierre de caja cancelado");

            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), "No hay una caja abierta en este momento.\nFactura algo primero para abrir una nueva.");
        }
    }
    
    private Date DateWithoutTime(Date sinHora) {
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        sinHora = cal.getTime();
        return sinHora;
    }
    public void abrirarchivo(File file) {

        try {

            Desktop.getDesktop().open(file);

        } catch (IOException ex) {

            System.out.println(ex);

        }

    }
    
    private void cargarParametros(){
    ParametroService parametroService = new ParametroService();
        parametro = new ParametroDto();
        Respuesta resp = parametroService.getParametro(1L);
        if (resp.getEstado()) {
            parametro = (ParametroDto) resp.getResultado("Parametro");
            
        }
    }
    private void CrearReporte() throws java.text.ParseException, IOException {
        EmpleadoService s = new EmpleadoService();

        reporte.setNombreEmpresa(parametro.getNombreRestaurante());
        reporte.setIdCaja(caja.getIdCaja());
        reporte.setTelefono(parametro.getTelefonoRestaurante());    //                                                         REVISAR PARAMETRO

        Respuesta respuesta = s.getReporte(reporte);
        if (!respuesta.getEstado()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Obteniendo reporte", getStage(), respuesta.getMensaje());
        } else {
            ReporteDto report = (ReporteDto) respuesta.getResultado("Reporte");
            if (crearReportePdf(report.getPrintReport(), "ReporteTemporal")) {

                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Crear reporte", getStage(), "Reporte generado correctamente.");
            }
        }
    }
    File tempFile;

    public boolean crearReportePdf(byte[] bytes, String nombre) throws IOException {
        tempFile = File.createTempFile("tempFile", ".pdf", null);
        tempFile.deleteOnExit();
        try (OutputStream out = new FileOutputStream(tempFile)) {
            out.write(bytes);
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BaseContainerViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseContainerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
                hayCaja = true;
            } else {
                //No encontró caja, creela
                hayCaja = false;
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
