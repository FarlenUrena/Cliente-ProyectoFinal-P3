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
import cr.ac.una.restuna.model.ProductoporordenDto;
import cr.ac.una.restuna.pojos.ItemProductoPorOrden;
import cr.ac.una.restuna.service.CajaService;
import cr.ac.una.restuna.service.FacturaService;
import cr.ac.una.restuna.service.OrdenService;
import cr.ac.una.restuna.service.ProductoporordenService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.BindingUtils;
import cr.ac.una.restuna.util.Formato;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import jakarta.ws.rs.core.Response;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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

import java.util.Properties;
import javafx.geometry.Insets;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

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

    @Override
    public void initialize() {

        cargarOrden();
//        Al obtener ordenDtoActual de AppContext usar : ordenDtoActual = ...; comentar lo anterior
        inicializarVista();
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombreCliente, txtEmail, txtDescuento, txtImpuestoPorServicio,
                txtImpuestoVenta, txtFechaEmicion, txtTotal, txtMontoTarjeta, txtMontoEfectivo));
    }

    private void nuevaFactura() {
//        unbindFactura();
        factura = new FacturaDto();
//        bindFactura(true);
//        txtId.clear();
//        txtId.requestFocus();
    }

//        private String calcularVuelto(){
////           facturaDto. 
//        }
//    private void unbindFactura() {
//        txtNombreCliente.textProperty().unbindBidirectional(ordenDto.getNombreCliente());
//        txtEmail.textProperty().unbindBidirectional("");
//
//        txtFechaEmicion.textProperty().unbindBidirectional(facturaDto.fechaFacturacion);
//        BindingUtils.bindToggleGroupToProperty(MetodoPago, facturaDto.metodoDePago);
//        txtMontoPagado.textProperty().unbindBidirectional(facturaDto.montoPagado);
//        txtDescuento.textProperty().unbindBidirectional(facturaDto.descuento);
//        txtTotal.textProperty().unbindBidirectional(facturaDto.total);
//        txtVuelto.textProperty().unbindBidirectional(facturaDto.vuelto);
//        txtImpuestoVenta.textProperty().unbindBidirectional(facturaDto.impuestoVenta);
//        txtImpuestoPorServicio.textProperty().unbindBidirectional(facturaDto.impuestoServicio);
//
////ltxtMontoTarjeta.textProperty().unbindBidirectional(facturaDto.total);
////txtMontoEfectivo.textProperty().unbindBidirectional(facturaDto.total);
//    }
    private void seleccionarMetodoPago() {
        if (rdBtnEfectivo.isSelected()) {
            factura.setMetodoDePago(1L);
        } else {
            if (rdBtnTarjeta.isSelected()) {
                factura.setMetodoDePago(2L);
            }
        }
    }

    private void precargarFactura() {

        factura.setFechaFacturacion(fechaActual);
        seleccionarMetodoPago();
        factura.setMontoPagado(0D);
        factura.setDescuento(Double.valueOf(txtDescuento.getText()));
        factura.setTotal(Double.valueOf(txtTotal.getText()));
        factura.setVuelto(0D);

        factura.setImpuestoVenta(txtImpuestoVenta.getText());
        factura.setImpuestoServicio(Double.valueOf(txtImpuestoPorServicio.getText()));

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
                        refresListPxO();
                    }
                });

                iPxO.btnSum.setOnMouseClicked(MouseEvent -> {
                    iPxO.ActionSum();
                });

                gridPanePrincipal.add(iPxO, 0, row);
                GridPane.setMargin(iPxO, new Insets(10));
                row++;
            }
        }
    }

    public void cargarProdsxO() {
        try {
            ProductoporordenService service = new ProductoporordenService();
            Respuesta resp = service.getProductosPorOrdenByOrden(ordenDtoActual.getIdOrden());
            if (resp.getEstado()) {
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
                }
            }
        }
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
        precargarFactura();
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), invalidos.toString());
            } else {

                //Validacion de la caja
                seleccionarCajaActual();
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
                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Error guardando la orden");
                    }

//                    todo al generar la factura, se deben setear campos de otras entidades, ej: Caja, ?
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(FacturaViewController.class.getName()).log(Level.SEVERE, "Error guardando la factura.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar factura", getStage(), "Ocurrio un error guardando la factura: " + ex.getMessage());
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
//        todo regresar a la vista anterior
    }

    Comparator<ProductoporordenDto> comparProductoXOrden = new Comparator<ProductoporordenDto>() {
        public int compare(ProductoporordenDto p1, ProductoporordenDto p2) {
            return p1.getIdProductoPorOrden().compareTo(p2.getIdProductoPorOrden());
        }
    };

    private void EnvieEmail() {
        final String username = "unarest61@gmail.com";
        final String password = "a2c43210";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            String filename = "ordenSanitariafirmada3.pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(txtEmail.getText()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(txtEmail.getText())
            );

            String html = "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "\n"
                    + "  <meta charset=\"utf-8\">\n"
                    + "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                    + "  <title>Email Receipt</title>\n"
                    + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "  <style type=\"text/css\">\n"
                    + "  /**\n"
                    + "   * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\n"
                    + "   */\n"
                    + "  @media screen {\n"
                    + "    @font-face {\n"
                    + "      font-family: 'Source Sans Pro';\n"
                    + "      font-style: normal;\n"
                    + "      font-weight: 400;\n"
                    + "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n"
                    + "    }\n"
                    + "\n"
                    + "    @font-face {\n"
                    + "      font-family: 'Source Sans Pro';\n"
                    + "      font-style: normal;\n"
                    + "      font-weight: 700;\n"
                    + "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n"
                    + "    }\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Avoid browser level font resizing.\n"
                    + "   * 1. Windows Mobile\n"
                    + "   * 2. iOS / OSX\n"
                    + "   */\n"
                    + "  body,\n"
                    + "  table,\n"
                    + "  td,\n"
                    + "  a {\n"
                    + "    -ms-text-size-adjust: 100%; /* 1 */\n"
                    + "    -webkit-text-size-adjust: 100%; /* 2 */\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Remove extra space added to tables and cells in Outlook.\n"
                    + "   */\n"
                    + "  table,\n"
                    + "  td {\n"
                    + "    mso-table-rspace: 0pt;\n"
                    + "    mso-table-lspace: 0pt;\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Better fluid images in Internet Explorer.\n"
                    + "   */\n"
                    + "  img {\n"
                    + "    -ms-interpolation-mode: bicubic;\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Remove blue links for iOS devices.\n"
                    + "   */\n"
                    + "  a[x-apple-data-detectors] {\n"
                    + "    font-family: inherit !important;\n"
                    + "    font-size: inherit !important;\n"
                    + "    font-weight: inherit !important;\n"
                    + "    line-height: inherit !important;\n"
                    + "    color: inherit !important;\n"
                    + "    text-decoration: none !important;\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Fix centering issues in Android 4.4.\n"
                    + "   */\n"
                    + "  div[style*=\"margin: 16px 0;\"] {\n"
                    + "    margin: 0 !important;\n"
                    + "  }\n"
                    + "\n"
                    + "  body {\n"
                    + "    width: 100% !important;\n"
                    + "    height: 100% !important;\n"
                    + "    padding: 0 !important;\n"
                    + "    margin: 0 !important;\n"
                    + "  }\n"
                    + "\n"
                    + "  /**\n"
                    + "   * Collapse table borders to avoid space between cells.\n"
                    + "   */\n"
                    + "  table {\n"
                    + "    border-collapse: collapse !important;\n"
                    + "  }\n"
                    + "\n"
                    + "  a {\n"
                    + "    color: #1a82e2;\n"
                    + "  }\n"
                    + "\n"
                    + "  img {\n"
                    + "    height: auto;\n"
                    + "    line-height: 100%;\n"
                    + "    text-decoration: none;\n"
                    + "    border: 0;\n"
                    + "    outline: none;\n"
                    + "  }\n"
                    + "  </style>\n"
                    + "\n"
                    + "</head>\n"
                    + "<body style=\"background-color: #40444F;\">\n"
                    + "\n"
                    + "  <!-- start preheader -->\n"
                    + "  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\n"
                    + "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n"
                    + "  </div>\n"
                    + "  <!-- end preheader -->\n"
                    + "\n"
                    + "  <!-- start body -->\n"
                    + "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                    + "\n"
                    + "    <!-- start logo -->\n"
                    + "    <tr>\n"
                    + "      <td align=\"center\" bgcolor=\"#40444F\">\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "        <tr>\n"
                    + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                    + "        <![endif]-->\n"
                    + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "          <tr>\n"
                    + "            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n"
                    + "              <a href=\"https://sendgrid.com\" target=\"_blank\" style=\"display: inline-block;\">\n"
                    + "                <img src=\"https://drive.google.com/thumbnail?id=1gHCE65Swhj3EcOyrSaDDOwz9fxpStVrh\" alt=\"Logo\" border=\"0\" width=\"88\" style=\"display: block; width: 148px; max-width: 148px; min-width: 48px;\">\n"
                    + "              </a>\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "        </table>\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        </td>\n"
                    + "        </tr>\n"
                    + "        </table>\n"
                    + "        <![endif]-->\n"
                    + "      </td>\n"
                    + "    </tr>\n"
                    + "    <!-- end logo -->\n"
                    + "\n"
                    + "    <!-- start hero -->\n"
                    + "    <tr>\n"
                    + "      <td align=\"center\" bgcolor=\"#40444F\">\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "        <tr>\n"
                    + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                    + "        <![endif]-->\n"
                    + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "          <tr>\n"
                    + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n"
                    + "              <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Gracias por su compra, pura vida!</h1>\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "        </table>\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        </td>\n"
                    + "        </tr>\n"
                    + "        </table>\n"
                    + "        <![endif]-->\n"
                    + "      </td>\n"
                    + "    </tr>\n"
                    + "    <!-- end hero -->\n"
                    + "\n"
                    + "    <!-- start copy block -->\n"
                    + "    <tr>\n"
                    + "      <td align=\"center\" bgcolor=\"#40444F\">\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "        <tr>\n"
                    + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                    + "        <![endif]-->\n"
                    + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "\n"
                    + "          <!-- start copy -->\n"
                    + "          <tr>\n"
                    + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n"
                    + "              <p style=\"margin: 0;\">Factura generada según su orden. en caso de error puede contacte por este medio, o via telefónica al 0987654321</a>.</p>\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "          <!-- end copy -->\n"
                    + "\n"
                    + "          <!-- start receipt table -->\n"
                    + "          <tr>\n"
                    + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n"
                    + "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                    + "                <tr>\n"
                    + "                  <td align=\"left\" bgcolor=\"#40444F\" width=\"75%\" style=\"padding: 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\"><strong>ID factura</strong></td>\n"
                    + "                  <td align=\"left\" bgcolor=\"#40444F\" width=\"25%\" style=\"padding: 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\"><strong>DTO</strong></td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                  <td align=\"left\" width=\"75%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">Fecha de facturación</td>\n"
                    + "                  <td align=\"left\" width=\"25%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">DTO</td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                  <td align=\"left\" width=\"75%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">Metodo de pago</td>\n"
                    + "                  <td align=\"left\" width=\"25%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">DTO</td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                  <td align=\"left\" width=\"75%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">ID caja</td>\n"
                    + "                  <td align=\"left\" width=\"25%\" style=\"padding: 6px 12px;font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">DTO</td>\n"
                    + "                </tr>\n"
                    + "\n"
                    + "                <tr>\n"
                    + "                  <td align=\"left\" width=\"75%\" style=\"padding: 12px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-top: 2px dashed #40444F; border-bottom: 2px dashed #40444F;\"><strong>Total</strong></td>\n"
                    + "                  <td align=\"left\" width=\"25%\" style=\"padding: 12px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-top: 2px dashed #40444F; border-bottom: 2px dashed #40444F;\"><strong>DTO</strong></td>\n"
                    + "                </tr>\n"
                    + "              </table>\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "          <!-- end reeipt table -->\n"
                    + "\n"
                    + "        </table>\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        </td>\n"
                    + "        </tr>\n"
                    + "        </table>\n"
                    + "        <![endif]-->\n"
                    + "      </td>\n"
                    + "    </tr>\n"
                    + "    <!-- end copy block -->\n"
                    + "\n"
                    + "    <!-- start receipt address block -->\n"
                    + "    <tr>\n"
                    + "      <td align=\"center\" bgcolor=\"#40444F\" valign=\"top\" width=\"100%\">\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "        <tr>\n"
                    + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                    + "        <![endif]-->\n"
                    + "        <table align=\"center\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "          <tr>\n"
                    + "            <td align=\"center\" valign=\"top\" style=\"font-size: 0; border-bottom: 3px solid #d4dadf\">\n"
                    + "              <!--[if (gte mso 9)|(IE)]>\n"
                    + "              <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "              <tr>\n"
                    + "              <td align=\"left\" valign=\"top\" width=\"300\">\n"
                    + "              <![endif]-->\n"
                    + "\n"
                    + "              <!--[if (gte mso 9)|(IE)]>\n"
                    + "              </td>\n"
                    + "              <td align=\"left\" valign=\"top\" width=\"300\">\n"
                    + "              <![endif]-->\n"
                    + "              <div style=\"display: inline-block; width: 100%; max-width: 50%; min-width: 240px; vertical-align: top;\">\n"
                    + "                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 300px;\">\n"
                    + "               \n"
                    + "                </table>\n"
                    + "              </div>\n"
                    + "              <!--[if (gte mso 9)|(IE)]>\n"
                    + "              </td>\n"
                    + "              </tr>\n"
                    + "              </table>\n"
                    + "              <![endif]-->\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "        </table>\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        </td>\n"
                    + "        </tr>\n"
                    + "        </table>\n"
                    + "        <![endif]-->\n"
                    + "      </td>\n"
                    + "    </tr>\n"
                    + "    <!-- end receipt address block -->\n"
                    + "\n"
                    + "    <!-- start footer -->\n"
                    + "    <tr>\n"
                    + "      <td align=\"center\" bgcolor=\"#40444F\" style=\"padding: 24px;\">\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                    + "        <tr>\n"
                    + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                    + "        <![endif]-->\n"
                    + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                    + "\n"
                    + "          <!-- start permission -->\n"
                    + "          <tr>\n"
                    + "            <td align=\"center\" bgcolor=\"#40444f\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #fff;\">\n"
                    + "              <p style=\"margin: 0;\">Este correo fué enviado con el consentimiento expreso del cliente con datos proporcionados por el mismo, no pretente ser spam.</p>\n"
                    + "            </td>\n"
                    + "          </tr>\n"
                    + "          <!-- end permission -->\n"
                    + "\n"
                    + "          <!-- start unsubscribe -->\n"
                    + "         \n"
                    + "          <!-- end unsubscribe -->\n"
                    + "\n"
                    + "        </table>\n"
                    + "        <!--[if (gte mso 9)|(IE)]>\n"
                    + "        </td>\n"
                    + "        </tr>\n"
                    + "        </table>\n"
                    + "        <![endif]-->\n"
                    + "      </td>\n"
                    + "    </tr>\n"
                    + "    <!-- end footer -->\n"
                    + "\n"
                    + "  </table>\n"
                    + "  <!-- end body -->\n"
                    + "\n"
                    + "</body>\n"
                    + "</html>";

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(html, "text/html; charset=utf-8");

            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);

            message.setSubject("Factura RestUNA");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
