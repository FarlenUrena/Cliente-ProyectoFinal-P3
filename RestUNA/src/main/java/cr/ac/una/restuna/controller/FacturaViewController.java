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
import cr.ac.una.restuna.model.FacturaDto;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.model.ProductoporordenDto;
import cr.ac.una.restuna.service.FacturaService;
import cr.ac.una.restuna.service.OrdenService;
import cr.ac.una.restuna.service.ProductoporordenService;
import cr.ac.una.restuna.util.BindingUtils;
import cr.ac.una.restuna.util.Formato;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
    private JFXTextField txtVuelto;
    @FXML
    private JFXTextField txtTotal;
    @FXML
    private JFXTextField txtMontoTarjeta;
    @FXML
    private JFXTextField txtMontoEfectivo;
    @FXML
    private JFXTextField txtMontoPagado;
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

    Date fechaActual = Date.from(Instant.now());
    FacturaDto facturaDto;
    OrdenDto ordenDto;
//    List<ProductoporordenDto> productosPorOrden = new ArrayList();
    List<Node> requeridos = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        rdBtnEfectivo.setUserData(1);
        rdBtnTarjeta.setUserData(2);

        
        txtFechaEmicion.setDisable(true);
        
        txtNombreCliente.setTextFormatter(Formato.getInstance().letrasFormat(40));
        txtEmail.setTextFormatter(Formato.getInstance().maxLengthFormat(40));
        txtDescuento.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtImpuestoPorServicio.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtImpuestoVenta.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtFechaEmicion.setTextFormatter(Formato.getInstance().maxLengthFormat(15));
        txtVuelto.setTextFormatter(Formato.getInstance().twoDecimalFormat());

        txtTotal.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtMontoPagado.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtMontoTarjeta.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtMontoEfectivo.setTextFormatter(Formato.getInstance().twoDecimalFormat());

        facturaDto = new FacturaDto();
        nuevaFactura();
        indicarRequeridos();

//        orden = new OrdenDto();
//        nuevaOrden()
//        InicializarProductosPorOrdenGrid();

//        nuevoProductopororden();
    }

    @Override
    public void initialize() {

    }
    public void indicarRequeridos(){
     requeridos.clear();
     requeridos.addAll(Arrays.asList(txtNombreCliente,txtEmail,txtDescuento,txtImpuestoPorServicio,
     txtImpuestoVenta,txtFechaEmicion,txtVuelto,txtTotal,txtMontoPagado,txtMontoTarjeta,txtMontoEfectivo));
    }
    private void nuevaFactura() {
//        unbindFactura();
        facturaDto = new FacturaDto();
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
////txtMontoTarjeta.textProperty().unbindBidirectional(facturaDto.total);
////txtMontoEfectivo.textProperty().unbindBidirectional(facturaDto.total);
//    }

    private void seleccionarMetodoPago(){
    if(rdBtnEfectivo.isSelected()){
        facturaDto.setMetodoDePago(1L);
    }else{
    if(rdBtnTarjeta.isSelected()){
        facturaDto.setMetodoDePago(2L);
    }}}
    
    private void precargarFactura() {
        
        facturaDto.setFechaFacturacion(fechaActual);
        seleccionarMetodoPago();
        facturaDto.setMontoPagado(txtMontoPagado.getText());
        facturaDto.setDescuento(txtDescuento.getText());
        facturaDto.setTotal(txtTotal.getText());
        facturaDto.setVuelto(txtDescuento.getText());
        
        facturaDto.setImpuestoVenta(txtImpuestoVenta.getText());
        facturaDto.setImpuestoServicio(txtImpuestoPorServicio.getText());

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
    
    void limpiarCampos(){
    
    }

    private void InicializarProductosPorOrdenGrid() {
        cargarOrden(1L);
//        productosPorOrden = ordenDto.getProductosporordenDto();
    }

//    private List<ProductoporordenDto> obtenerProductosPorOrden() {
//        ProductoporordenService service = new ProductoporordenService();
//        Respuesta respuesta = service.getProductosporordenByOrdenId(1L);
//        return (List<ProductoporordenDto>) respuesta.getResultado("ProductosporordenList");
//    }

    @FXML
    private void onActionMetodoPagoEfectivo(ActionEvent event) {
    }

    @FXML
    private void onActionMetodoPagoTarjeta(ActionEvent event) {
    }

    @FXML
    private void onActionMetodoPagoAmbos(ActionEvent event) {
    }

    @FXML
    private void onActionBtnConfirmarPago(ActionEvent event) {
        precargarFactura();
        try{
            String invalidos = validarRequeridos();
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar factura" , getStage() , invalidos.toString());
            }else{
                FacturaService service = new FacturaService();
                Respuesta respuesta = service.guardarFactura(facturaDto);
                if(!respuesta.getEstado()){
                    new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar empleado" , getStage() , respuesta.getMensaje());
                }
                else{
//                    unbindFactura();
                    facturaDto = (FacturaDto) respuesta.getResultado("Factura");
//                    bindFactura(false);

                    if(new Mensaje().showConfirmation("Enviar correo", this.getStage(), "Desea enviar un correo con la factura a: "+txtEmail.getText())){
                        //to do send email
                    }
                    new Mensaje().showModal(Alert.AlertType.INFORMATION , "Guardar factura" , getStage() , "Factura generada correctamente.");
//                    todo al generar la factura, se deben setear campos de otras entidades, ej: Caja, estado orden, estado mesa...+?
                }
            }
        }
        catch(Exception ex ){
            Logger.getLogger(FacturaViewController.class.getName()).log(Level.SEVERE , "Error guardando la factura." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar factura" , getStage() , "Ocurrio un error guardando la factura: "+ex.getMessage());
        }
        
//        InicializarProductosPorOrdenGrid();
    }

    private void nuevoProductopororden() {

    }

    private void cargarOrden(Long id) {
        OrdenService service = new OrdenService();
        Respuesta respuesta = service.getOrden(id);

        if (respuesta.getEstado()) {
//            unbindProducto();
            ordenDto = (OrdenDto) respuesta.getResultado("OrdenGuardada");
//            bindProducto(false);
//            validarRequeridos();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), respuesta.getMensaje());
        }
    }

}
