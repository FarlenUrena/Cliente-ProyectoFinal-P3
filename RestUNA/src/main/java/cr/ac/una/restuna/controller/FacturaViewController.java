/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.CajaDto;
import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.FacturaDto;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.model.ParametroDto;
import cr.ac.una.restuna.model.ProductoporordenDto;
import cr.ac.una.restuna.model.ReporteDto;
import cr.ac.una.restuna.pojos.ItemProductoPorOrden;
import cr.ac.una.restuna.service.CajaService;
import cr.ac.una.restuna.service.EmpleadoService;
import cr.ac.una.restuna.service.FacturaService;
import cr.ac.una.restuna.service.OrdenService;
import cr.ac.una.restuna.service.ParametroService;
import cr.ac.una.restuna.service.ProductoporordenService;
import cr.ac.una.restuna.util.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class FacturaViewController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private JFXTextField txtNombreCliente;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtDescuento;
    @FXML
    private JFXTextField txtImpuestoPorServicio;
    @FXML
    private JFXTextField txtImpuestoVenta;
    @FXML
    private JFXTextField txtFechaEmicion;
    @FXML
    private JFXTextField txtTotal;
    @FXML
    private JFXTextField txtMontoTarjeta;
    @FXML
    private JFXTextField txtMontoEfectivo;
    @FXML
    private ScrollPane scrlPanePrincipal;
    @FXML
    private GridPane gridPanePrincipal;
    @FXML
    private ToggleGroup MetodoPago;
    @FXML
    private JFXRadioButton rdBtnEfectivo;
    @FXML
    private JFXRadioButton rdBtnTarjeta;
    @FXML
    private JFXRadioButton rdBtnAmbos;
    @FXML
    private JFXButton btnConfirmarPago;
    @FXML
    private Label lblEfectivo;
    @FXML
    private Label lblTarjeta;
    @FXML
    private JFXButton btnImprimir;
    @FXML
    private HBox elementosFactura;
    @FXML
    private JFXButton btnVolver;
    @FXML
    private JFXButton btnAgrandarOrden;

    Date fechaActual = Date.from(Instant.now());
    FacturaDto factura;
    OrdenDto ordenDtoActual;
    CajaDto caja;
    EmpleadoDto empOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
    List<CajaDto> cajasList = new ArrayList();
    List<Node> requeridos = new ArrayList<>();
    List<ProductoporordenDto> productosPXO = new ArrayList<>();
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    boolean isImprimir = false;
    ParametroDto parametro;
//    Double totalPagar = 0.0, totalPagado = 0.0, impServ = 0.0, impVent = 0.0, descMax = 0.0;
    Double totalPagar = 0.0, totalPagarSI = 0.0, totalPagado = 0.0, impServ = 0.0, impVent = 0.0, descMax = 0.0;
//    Double totalPagar = 0.0, totalPagado = 0.0, impServ = 0.0, impVent = 0.0, descMax = 0.0;
    ReporteDto reporte = new ReporteDto();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        EnvieEmail();
//        inicializarVista();

        rdBtnEfectivo.setUserData(1);
        rdBtnTarjeta.setUserData(2);

        txtFechaEmicion.setDisable(true);

        txtNombreCliente.setTextFormatter(Formato.getInstance().letrasFormat(40));
        txtEmail.setTextFormatter(Formato.getInstance().maxLengthFormat(40));
        txtDescuento.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtImpuestoPorServicio.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtImpuestoVenta.setTextFormatter(Formato.getInstance().twoDecimalFormat());

        txtTotal.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtMontoTarjeta.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtMontoEfectivo.setTextFormatter(Formato.getInstance().twoDecimalFormat());

        indicarRequeridos();

    }

    @Override
    public void initialize() {
        ParametroService parametroService = new ParametroService();
        parametro = new ParametroDto();
        Respuesta resp = parametroService.getParametro(1L);
        if (resp.getEstado()) {
            parametro = (ParametroDto) resp.getResultado("Parametro");
            impServ = parametro.getImpuestoServicio();
            impVent = parametro.getImpuestoVenta();
            descMax = parametro.getDescuentoMaximo();
        }

        nuevaFactura();
        cargarOrden();
//        Al obtener ordenDtoActual de AppContext usar : ordenDtoActual = ...; comentar lo anterior
        inicializarVista();
    }

    private void inicializarVista() {
        txtFechaEmicion.setText(formatter.format(fechaActual));

        txtImpuestoVenta.setText(String.valueOf(impVent));
        txtImpuestoVenta.setDisable(true);

        txtNombreCliente.setText(ordenDtoActual.getNombreCliente());

//
        txtImpuestoPorServicio.setText(String.valueOf(ordenDtoActual.getIdElementodeseccionDto().getImpuestoPorServicio()));
        txtImpuestoPorServicio.setDisable(true);
        isImprimir = false;

        validarImpresion();

        //TODO, impuestos etc;
    }

    void validarImpresion() {
        if (isImprimir) {
            btnImprimir.setDisable(false);
            btnConfirmarPago.setDisable(true);
        } else {
            btnImprimir.setDisable(true);
            btnConfirmarPago.setDisable(false);
        }
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombreCliente, txtEmail, txtDescuento, txtImpuestoPorServicio,
                txtImpuestoVenta, txtFechaEmicion, txtTotal, txtMontoTarjeta, txtMontoEfectivo));
    }

    private void nuevaFactura() {
        factura = new FacturaDto();
    }

    private void seleccionarMetodoPago() {
        if (rdBtnEfectivo.isSelected()) {
            factura.setMetodoDePago(1L);
        } else {
            if (rdBtnTarjeta.isSelected()) {
                factura.setMetodoDePago(2L);
            }
        }
    }

    private boolean precargarFactura() {

        txtTotal.setText(totalPagar.toString());
        factura.setNombreCliente(txtNombreCliente.getText());

        totalPagado = Double.valueOf(txtMontoEfectivo.getText()) + Double.valueOf(txtMontoTarjeta.getText());
        if (totalPagado >= totalPagar) {
            factura.setFechaFacturacion(fechaActual);
            seleccionarMetodoPago();
            if (totalPagado > totalPagar) {
                factura.setMontoPagado(totalPagado);
                Double velt = totalPagado - totalPagar;
                factura.setVuelto(velt);
            } else {
                factura.setVuelto(0D);
            }
            factura.setImpuestoVenta(impVent);
            factura.setImpuestoServicio(ordenDtoActual.getIdElementodeseccionDto().getImpuestoPorServicio());
            return true;
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Dinero Insuficiente para completar el pago");
            return false;
        }

    }

    void limpiarCampos() {

    }

    private void InicializarProductosPorOrdenGrid() {
//        cargarOrden(1L);
//        productosPorOrden = ordenDto.getProductosporordenDto();
        refresListPxO();
    }

    public void refresListPxO() {

        gridPanePrincipal.getChildren().clear();
        cargarProdsxO();
        totalPagar = 0.0;
        totalPagarSI = 0.0;
        totalPagado = 0.0;

        if (!productosPXO.isEmpty()) {
            int row = 1;
            Collections.sort(productosPXO, comparProductoXOrden);
            Collections.reverse(productosPXO);
            for (ProductoporordenDto pxo : productosPXO) {
                ItemProductoPorOrden iPxO = new ItemProductoPorOrden(pxo);

                iPxO.btnRest.setOnMouseClicked(MouseEvent -> {
                    iPxO.ActionMinus();
                    if (iPxO.cantidad == 0) {
//                        grpListPxO.getChildren().remove(iPxO);
                        ProductoporordenService service = new ProductoporordenService();
                        service.eliminarProductopororden(iPxO.getProductoporordenDto().getIdProductoPorOrden());
                        productosPXO.remove(pxo);

                        if (productosPXO.isEmpty()) {
                            OrdenService ordenService = new OrdenService();
                            Respuesta resp = ordenService.eliminarOrden(ordenDtoActual.getIdOrden());
                            if (!resp.getEstado()) {
                                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar orden", getStage(), " Error al vaciar la orden.");
                            } else {
                                nuevaOrden();
//                                cargarOrden();
                            }

                        }
                    }
                    refresListPxO();
                });

                iPxO.btnSum.setOnMouseClicked(MouseEvent -> {
                    iPxO.ActionSum();
                    refresListPxO();
                });
                if (ordenDtoActual.getEsEstado().equals(2l)) {
                    iPxO.btnRest.setVisible(false);
                    iPxO.btnSum.setVisible(false);
                }

                totalPagarSI += iPxO.getProductoporordenDto().getSubtotal();
                gridPanePrincipal.add(iPxO, 0, row);
                GridPane.setMargin(iPxO, new Insets(10));
                row++;
            }
        }
        double tImpServ = totalPagarSI * ordenDtoActual.getIdElementodeseccionDto().getImpuestoPorServicio();
        double tImpVent = totalPagarSI * impVent;

        totalPagar = totalPagarSI + tImpServ + tImpVent;
        txtDescuento.setText("0.00");
//        totalPagarSI = totalPagarSI + ((totalPagar * impServ) + (totalPagar * impVent));
        txtTotal.setText(totalPagar.toString());
//        System.out.println(totalPagar + "\n");
    }

    public void cargarProdsxO() {
        try {
            ProductoporordenService service = new ProductoporordenService();
            Respuesta resp = service.getProductosPorOrdenByOrden(ordenDtoActual.getIdOrden());
            if (resp.getEstado()) {
                productosPXO = new ArrayList<>();
                productosPXO = (List<ProductoporordenDto>) resp.getResultado("ProductosporordenFiltered");

            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar orden", getStage(), " Error al cargar la lista de productos ordenados.");
            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar orden", getStage(), e.getMessage());
        }
    }

//    private List<ProductoporordenDto> obtenerProductosPorOrden() {
//        ProductoporordenService service = new ProductoporordenService();
//        Respuesta respuesta = service.getProductosporordenByOrdenId(1L);
//        return (List<ProductoporordenDto>) respuesta.getResultado("ProductosporordenList");
//    }
    private void validarMetodoDePago() {

        if (rdBtnEfectivo.isSelected()) {
            txtMontoEfectivo.setDisable(false);
            txtMontoEfectivo.setOpacity(1);
            lblEfectivo.setDisable(false);
            lblEfectivo.setOpacity(1);

            lblTarjeta.setDisable(true);
            lblTarjeta.setOpacity(0.6);
            txtMontoTarjeta.setDisable(true);
            txtMontoTarjeta.setOpacity(0.6);
            txtMontoTarjeta.setText("0.00");
            txtMontoEfectivo.setText("0.00");
        } else {
            if (rdBtnTarjeta.isSelected()) {
                txtMontoTarjeta.setDisable(true);
                txtMontoTarjeta.setOpacity(1);
                lblTarjeta.setDisable(false);
                lblTarjeta.setOpacity(1);

                lblEfectivo.setDisable(true);
                lblEfectivo.setOpacity(0.6);
                txtMontoEfectivo.setDisable(true);
                txtMontoEfectivo.setOpacity(0.6);
                txtMontoTarjeta.setText(totalPagar.toString());
                txtMontoEfectivo.setText("0.00");
            } else {
                if (rdBtnAmbos.isSelected()) {
                    txtMontoEfectivo.setDisable(false);
                    txtMontoEfectivo.setOpacity(1);
                    lblEfectivo.setDisable(false);
                    lblEfectivo.setOpacity(1);
                    txtMontoTarjeta.setDisable(false);
                    txtMontoTarjeta.setOpacity(1);
                    lblTarjeta.setDisable(false);
                    lblTarjeta.setOpacity(1);
                    txtMontoTarjeta.setText("0.00");
                    txtMontoEfectivo.setText("0.00");
                }
            }
        }
    }

    @FXML
    void onActionBtnAgrandarOrden(ActionEvent event) {

    }

    @FXML
    private void onActionMetodoPagoEfectivo(ActionEvent event) {
        validarMetodoDePago();
    }

    @FXML
    private void onActionMetodoPagoTarjeta(ActionEvent event) {
        validarMetodoDePago();
    }

    @FXML
    private void onActionMetodoPagoAmbos(ActionEvent event) {
        validarMetodoDePago();
    }

    @FXML
    private void onActionBtnConfirmarPago(ActionEvent event) {
        if (precargarFactura()) {
            try {
                if (ordenDtoActual.getIdOrden() != null) {
                    String invalidos = validarRequeridos();
                    if (!invalidos.isBlank()) {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), invalidos.toString());
                    } else {

                        //Validacion de la caja
                        seleccionarCajaActual();

                        if (caja.getSaldoEfectivo() > factura.getVuelto()) {
                            String ModalResp = "ok";
                            if (rdBtnEfectivo.isSelected()) {
                                AppContext.getInstance().set("MontoPagar", txtMontoEfectivo.getText());
                                AppContext.getInstance().set("VueltoDar", factura.getVuelto().toString());

                                FlowController.getInstance().goViewInWindowModalUncap("CajaCambioModalView", this.getStage(), false);

                                ModalResp = (String) AppContext.getInstance().get("cajaModal");
                            } else {
                                AppContext.getInstance().set("montoSustraido", "0.00");
                            }

                            if (ModalResp.equals("ok")) {

                                double vueltin = Double.valueOf((String) AppContext.getInstance().get("montoSustraido"));

                                factura.setVuelto(vueltin);

                                caja.setSaldoEfectivo(caja.getSaldoEfectivo() - factura.getVuelto() + Double.valueOf(txtMontoEfectivo.getText()));
                                caja.setSaldoTarjeta(caja.getSaldoTarjeta() + Double.valueOf(txtMontoTarjeta.getText()));
                                CajaService serviceC = new CajaService();
                                Respuesta respuestaC = serviceC.guardarCaja(caja);
                                if (respuestaC.getEstado()) {
                                    caja = (CajaDto) respuestaC.getResultado("Caja");

                                    factura.setTotal(totalPagar);
                                    factura.setMontoPagado(totalPagado);

                                    factura.setIdCaja(caja);
                                    factura.setIdOrden(ordenDtoActual);
                                    multiplicarPorcentaje();
                                    FacturaService service = new FacturaService();
                                    Respuesta respuesta = service.guardarFactura(factura);

                                    if (!respuesta.getEstado()) {
                                        new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), respuesta.getMensaje());
                                    } else {
                                        factura = (FacturaDto) respuesta.getResultado("Factura");
                                        ordenDtoActual.setEsEstado(2L);
                                        ordenDtoActual.setNombreCliente(txtNombreCliente.getText());
                                        OrdenService ordenService = new OrdenService();
                                        Respuesta resp = ordenService.guardarOrden(ordenDtoActual);
                                        if (resp.getEstado()) {
                                            ordenDtoActual = (OrdenDto) resp.getResultado("OrdenGuardada");
                                            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar factura", getStage(), "Factura generada correctamente.");
                                            //Crear reporte
                                            reporte = new ReporteDto();
                                            reporte.setTipo(4);
                                            CrearReporte();
                                            //Mostrar Factura
                                            abrirarchivo(tempFile);
                                            //

                                            btnConfirmarPago.setDisable(true);
                                            isImprimir = true;
                                            validarImpresion();
                                            if (new Mensaje().showConfirmation("Enviar correo", this.getStage(), "Desea enviar un correo con la factura a: " + txtEmail.getText())) {
                                                if (txtEmail.getText() != null && !txtEmail.getText().isBlank()) {
                                                    EnvieEmail(tempFile);
                                                }
                                            }
                                            refresListPxO();
                                        } else {
                                            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Error guardando la orden");
                                        }
                                    }
                                } else {
                                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Hubo un error procesando el pago");
                                }
                            } else {
                                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Transaccion cancelada");
                            }
                        } else {
                            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "No hay suficiente dinero para dar cambio");
                        }

                    }
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Facturar Orden", getStage(), "No existe una orden para facturar");
                }
            } catch (Exception ex) {
                Logger.getLogger(FacturaViewController.class.getName()).log(Level.SEVERE, "Error guardando la factura.", ex);
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Ocurrio un error guardando la factura: " + ex.getMessage());
            }
        }

//        InicializarProductosPorOrdenGrid();
    }

    private void multiplicarPorcentaje(){
    factura.setDescuento(factura.getDescuento() * 100);
    factura.setImpuestoVenta(factura.getImpuestoVenta() * 100);
    factura.setImpuestoServicio(factura.getImpuestoServicio() * 100);
    }
    
    public void abrirarchivo(File file) {

        try {
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {

            System.out.println(ex);

        }

    }

    private void CrearReporte() throws java.text.ParseException, IOException {

        EmpleadoService s = new EmpleadoService();

        reporte.setNombreEmpresa(parametro.getNombreRestaurante());
        reporte.setIdFactura(factura.getIdFactura());
        reporte.setTelefono(parametro.getTelefonoRestaurante());                                      //                       REVISAR PARAMETRO

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


    private void print(PrinterJob job, Node node) {
        // Print the node
        boolean printed = job.printPage(node);

        if (printed) {
            job.endJob();
        }
    }

    private void nuevoProductopororden() {

    }

    private void cargarOrden() {

        ordenDtoActual = (OrdenDto) AppContext.getInstance().get("facturarOrden");

        if (ordenDtoActual.getIdOrden() != null) {

            OrdenService service = new OrdenService();
            Respuesta respuesta = service.getOrden(ordenDtoActual.getIdOrden());

            if (respuesta.getEstado()) {
//            unbindProducto();
                ordenDtoActual = (OrdenDto) respuesta.getResultado("Orden");
//                txtNombreCliente.setText(ordenDtoActual.getNombreCliente());
                //            bindProducto(false);
                //            validarRequeridos();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), "Orden no encontrada");
            }
            InicializarProductosPorOrdenGrid();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), "No existe una orden");
            //cargar todas las ordenes en una lista para que las pueda seleccionar luego

        }
    }

    //Selecciona la caja diaria
    private void seleccionarCajaActual() {
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
            } else {
                //No encontró caja, creela
                caja = new CajaDto();
                nuevaCaja();
                guardarCaja();
            }
            System.out.println("WEEEEYY SIIII");
        } else {
            caja = new CajaDto();
            nuevaCaja();
            guardarCaja();
        }
    }

    private List<CajaDto> obtenerTodasCajas() {
        CajaService service = new CajaService();
        Respuesta respuesta = service.getCajas();
        return (List<CajaDto>) respuesta.getResultado("CajasList");
    }
    private final double saldoInicial = 25000.00;

    public void nuevaOrden() {
        ordenDtoActual = new OrdenDto();
        Date Date = convertToDateViaInstant(java.time.LocalDateTime.now());
        ordenDtoActual.setFechaCreacion(Date);
        ordenDtoActual.setEsEstado(1L);
        ElementodeseccionDto elementoDto = (ElementodeseccionDto) AppContext.getInstance().get("elementoToOrden");
        ordenDtoActual.setIdElementodeseccionDto(elementoDto);
        EmpleadoDto empleadoOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        ordenDtoActual.setIdEmpleadoDto(empleadoOnline);
        AppContext.getInstance().set("OrdenActual", ordenDtoActual);
    }

    private void nuevaCaja() {
        caja.setEsActiva(1L);
//        caja.setFacturasDto();
//        caja.setFacturasEliminadasDto(null);
        Date fechaApertura = Date.from(Instant.now());
//        Date Date = convertToDateViaInstant(java.time.LocalDateTime.now());
        caja.setFechaApertura(fechaApertura);
//      caja.setFechaCierre(Date.from(Instant.now()));
//      caja.setIdCaja(null);
        caja.setIdEmpleadoDto(empOnline);
        caja.setModificado(false);
        caja.setSaldoEfectivo(saldoInicial);
        caja.setSaldoEfectivoCierre(0.00);
        caja.setSaldoTarjeta(saldoInicial);//REVISAR
        caja.setSaldoTarjetaCierre(0.00);

    }

    Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    private void guardarCaja() {
        CajaService service = new CajaService();
        Respuesta resp = service.guardarCaja(caja);
        caja = (CajaDto) resp.getResultado("Caja");
    }

    @FXML
    private void onActionBtnImprimirFactura(ActionEvent event) {
        abrirarchivo(tempFile);
    }

    @FXML
    private void onActionBtnVolver(ActionEvent event) {
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

            }
        } else if (ModalResp.equals("caceled")) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Cierre de caja cancelado");

        }
    }

    @FXML
    private void onActionBtnAppDesc(ActionEvent event) {
        Double descAplicado = Double.valueOf(txtDescuento.getText());
        if (descAplicado <= descMax) {

            totalPagar = totalPagarSI - (totalPagarSI * descAplicado);
            txtTotal.setText(totalPagar.toString());
            factura.setDescuento(descAplicado);

        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "El descuento maximo es de un: " + descMax);
        }
    }

    Comparator<ProductoporordenDto> comparProductoXOrden = new Comparator<ProductoporordenDto>() {
        public int compare(ProductoporordenDto p1, ProductoporordenDto p2) {
            return p1.getIdProductoPorOrden().compareTo(p2.getIdProductoPorOrden());
        }
    };
    //    public DataHandler getHandeler(String filename){
    //        DataSource source = new FileDataSource(filename);
    //        DataHandler dataHandler = new DataHandler(source);
    //        return dataHandler;
    //    }

    private void EnvieEmail(File file) throws MessagingException, MessagingException, IOException {

        final String username = parametro.getCorreoRestaurante();
        final String password = parametro.getPsswrdCorreo();
        EmailSender emailSender = new EmailSender();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Date fechaDeEmicion = convertToDateViaInstant(java.time.LocalDateTime.now());

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(username));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(txtEmail.getText()));
        message.setSubject("Factura: " + fechaDeEmicion.toString());

        //HTML
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(emailSender.getHtml(factura), "text/html");
        //FACTURA PDF

        MimeBodyPart facturaMtlp = new MimeBodyPart();
        facturaMtlp.attachFile(file);

        Multipart multipart = new MimeMultipart();

        multipart.addBodyPart(facturaMtlp);
        multipart.addBodyPart(htmlPart);

        message.setContent(multipart);

        Transport.send(message);

//
        System.out.println("Correo enviado");
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && (((JFXTextField) node).getText() == null || ((JFXTextField) node).getText().isEmpty())) {
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXPasswordField && (((JFXPasswordField) node).getText() == null || ((JFXPasswordField) node).getText().isEmpty())) {
                if (validos) {
                    invalidos += ((JFXPasswordField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXPasswordField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXDatePicker && ((JFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((JFXDatePicker) node).getAccessibleText();
                } else {
                    invalidos += "," + ((JFXDatePicker) node).getAccessibleText();
                }
                validos = false;
            } else if (node instanceof JFXComboBox && ((JFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((JFXComboBox) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXComboBox) node).getPromptText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }
}
