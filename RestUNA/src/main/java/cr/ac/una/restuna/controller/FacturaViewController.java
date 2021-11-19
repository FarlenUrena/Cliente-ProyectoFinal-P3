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
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.FacturaDto;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.model.ParametroDto;
import cr.ac.una.restuna.model.ProductoporordenDto;
import cr.ac.una.restuna.pojos.ItemProductoPorOrden;
import cr.ac.una.restuna.service.CajaService;
import cr.ac.una.restuna.service.FacturaService;
import cr.ac.una.restuna.service.OrdenService;
import cr.ac.una.restuna.service.ParametroService;
import cr.ac.una.restuna.service.ProductoporordenService;
import cr.ac.una.restuna.util.*;
import java.io.File;
import java.io.IOException;
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
    Double totalPagar = 0.0, totalPagado = 0.0, impServ = 0.0, impVent = 0.0, descMax = 0.0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        EnvieEmail();
        inicializarVista();

        rdBtnEfectivo.setUserData(1);
        rdBtnTarjeta.setUserData(2);

        txtFechaEmicion.setDisable(true);

        txtNombreCliente.setTextFormatter(Formato.getInstance().letrasFormat(40));
        txtEmail.setTextFormatter(Formato.getInstance().maxLengthFormat(40));
        txtDescuento.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtImpuestoPorServicio.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtImpuestoVenta.setTextFormatter(Formato.getInstance().twoDecimalFormat());

//        txtVuelto.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtTotal.setTextFormatter(Formato.getInstance().twoDecimalFormat());
//        txtMontoPagado.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtMontoTarjeta.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtMontoEfectivo.setTextFormatter(Formato.getInstance().twoDecimalFormat());

//        factura = new FacturaDto();
        nuevaFactura();
        indicarRequeridos();
//        ordenDtoActual = new OrdenDto();
//        nuevaOrden()
//        InicializarProductosPorOrdenGrid();

//        nuevoProductopororden();
    }
    
    @Override
    public void initialize() {
        ParametroService parametroService = new ParametroService();
        ParametroDto parametro = new ParametroDto();

        Respuesta resp1 = parametroService.getParametro("Impuesto por Servicio");
        if (resp1.getEstado()) {
            parametro = (ParametroDto) resp1.getResultado("Parametro");
            impServ = parametro.getValorNumerico();
            txtImpuestoPorServicio.setText(impServ.toString());
        }

        Respuesta resp2 = parametroService.getParametro("Impuesto de Venta");
        if (resp1.getEstado()) {
            parametro = (ParametroDto) resp2.getResultado("Parametro");
            impVent = parametro.getValorNumerico();
            txtImpuestoVenta.setText(impVent.toString());
        }

        Respuesta resp3 = parametroService.getParametro("Descuento Maximo");
        if (resp1.getEstado()) {
            parametro = (ParametroDto) resp3.getResultado("Parametro");
            descMax = parametro.getValorNumerico();
        }

        cargarOrden();
//        Al obtener ordenDtoActual de AppContext usar : ordenDtoActual = ...; comentar lo anterior
        inicializarVista();
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

    private void inicializarVista() {
        txtFechaEmicion.setText(formatter.format(fechaActual));
        isImprimir = false;
        validarImpresion();

        //TODO, impuestos etc;
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
            factura.setImpuestoServicio(impServ);
            return true;
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Dinero Insuficiente para completar el pago");
            return false;
        }

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
                                ordenDtoActual = new OrdenDto();
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

                totalPagar += iPxO.getProductoporordenDto().getSubtotal();
                gridPanePrincipal.add(iPxO, 0, row);
                GridPane.setMargin(iPxO, new Insets(10));
                row++;
            }
        }
        totalPagar = totalPagar + ((totalPagar * impServ) + (totalPagar * impVent));
        txtTotal.setText(totalPagar.toString());
        System.out.println(totalPagar + "\n");
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
                txtMontoTarjeta.setDisable(false);
                txtMontoTarjeta.setOpacity(1);
                lblTarjeta.setDisable(false);
                lblTarjeta.setOpacity(1);

                lblEfectivo.setDisable(true);
                lblEfectivo.setOpacity(0.6);
                txtMontoEfectivo.setDisable(true);
                txtMontoEfectivo.setOpacity(0.6);
                txtMontoTarjeta.setText("0.00");
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
                String invalidos = validarRequeridos();
                if (!invalidos.isBlank()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), invalidos.toString());
                } else {

                    //Validacion de la caja
                    seleccionarCajaActual();
                    if (caja.getSaldoEfectivo() > factura.getVuelto()) {
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
                            FacturaService service = new FacturaService();
                            Respuesta respuesta = service.guardarFactura(factura);
                            if (!respuesta.getEstado()) {
                                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), respuesta.getMensaje());
                            } else {
//                    unbindFactura();
                                factura = (FacturaDto) respuesta.getResultado("Factura");
//                    bindFactura(false);
                                ordenDtoActual.setEsEstado(2L);
                                ordenDtoActual.setNombreCliente(txtNombreCliente.getText());
                                OrdenService ordenService = new OrdenService();
                                Respuesta resp = ordenService.guardarOrden(ordenDtoActual);
                                if (resp.getEstado()) {
                                    ordenDtoActual = (OrdenDto) resp.getResultado("OrdenGuardada");
                                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar factura", getStage(), "Factura generada correctamente.");
                                    btnConfirmarPago.setDisable(true);
                                    isImprimir = true;
                                    validarImpresion();
                                    if (new Mensaje().showConfirmation("Enviar correo", this.getStage(), "Desea enviar un correo con la factura a: " + txtEmail.getText())) {
                                        if (txtEmail.getText() != null && !txtEmail.getText().isBlank()) {
                                            EnvieEmail();
                                        }
                                    }
                                    refresListPxO();
                                } else {
                                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Error guardando la orden");
                                }

//                    todo al generar la factura, se deben setear campos de otras entidades, ej: Caja, ?
                            }
                        } else {
                            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Hubo un error procesando el pago");
                        }
                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "No hay suficiente dinero para dar cambio");
                    }

                }
            } catch (Exception ex) {
                Logger.getLogger(FacturaViewController.class.getName()).log(Level.SEVERE, "Error guardando la factura.", ex);
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Ocurrio un error guardando la factura: " + ex.getMessage());
            }
        }

//        InicializarProductosPorOrdenGrid();
    }

    private void intentoDeImprimir() {
        // Create the PrinterJob
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null) {
            return;
        }

// Show the page setup dialog
        boolean proceed = job.showPageSetupDialog(this.getStage().getOwner());

        if (proceed) {
            print(job, elementosFactura);
        }
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
                txtNombreCliente.setText(ordenDtoActual.getNombreCliente());
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

    private void nuevaCaja() {
        caja.setEsActiva(1L);
//        caja.setFacturasDto();
//        caja.setFacturasEliminadasDto(null);

        Date Date = convertToDateViaInstant(java.time.LocalDateTime.now());
        caja.setFechaApertura(Date);
//      caja.setFechaCierre(Date.from(Instant.now()));
//      caja.setIdCaja(null);
        caja.setIdEmpleadoDto(empOnline);
        caja.setModificado(false);
        caja.setSaldoEfectivo(saldoInicial);
        caja.setSaldoEfectivoCierre(0.00);
        caja.setSaldoTarjeta(saldoInicial);
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
        intentoDeImprimir();
    }

    @FXML
    private void onActionBtnVolver(ActionEvent event) {

        caja.setSaldoEfectivoCierre(caja.getSaldoEfectivo());
        caja.setSaldoTarjetaCierre(caja.getSaldoTarjeta());
        caja.setEsActiva(2L);
        CajaService serviceC = new CajaService();
        Respuesta respuestaC = serviceC.guardarCaja(caja);
        if(!respuestaC.getEstado()){
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Cierre de caja", getStage(), "No se pudo completar el cierrre de caja");

        }
    }

    @FXML
    private void onActionBtnAppDesc(ActionEvent event) {
        Double descAplicado = Double.valueOf(txtDescuento.getText());
        if (descAplicado <= descMax) {

            totalPagar = totalPagar - (totalPagar * descAplicado);
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

    private void EnvieEmail() throws MessagingException, MessagingException, IOException {
        final String username = "unarest61@gmail.com";
        final String password = "a2c43210";
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
        message.setSubject("Factura" + fechaDeEmicion.toString());

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(emailSender.getHtml(factura), "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(htmlPart);
        
        Multipart facturaMtlp = new MimeMultipart();
        facturaMtlp.addBodyPart(htmlPart);

        message.setContent(facturaMtlp);

        Transport.send(message);

//
        System.out.println("Correo enviado");
    }
}
