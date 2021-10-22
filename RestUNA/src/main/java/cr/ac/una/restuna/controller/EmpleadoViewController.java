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
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.service.EmpleadoService;
import cr.ac.una.restuna.util.Formato;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class EmpleadoViewController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private JFXComboBox<String> cmbbxRol;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXButton btnGuardar;

    EmpleadoDto empleado;
    List<Node> requeridos = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarRoles();
        txtId.setTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
        txtApellido.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(40));
        txtUsuario.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtPassword.setTextFormatter(Formato.getInstance().maxLengthFormat(8));
        
        empleado=new EmpleadoDto();
        nuevoEmpleado();
        indicarRequeridos();
    }

        public void indicarRequeridos(){
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombre , txtCedula , txtApellido , txtUsuario, txtPassword, cmbbxRol));
    }
    
        public String validarRequeridos(){
        Boolean validos = true;
        String invalidos = "";
        for(Node node : requeridos)
        {
            if(node instanceof JFXTextField && (((JFXTextField) node).getText() == null || ((JFXTextField) node).getText().isEmpty() ))
            {
                if(validos)
                {
                    invalidos += ((JFXTextField) node).getPromptText();
                }
                else
                {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            }
            else if(node instanceof JFXPasswordField && (((JFXPasswordField) node).getText() == null  || ((JFXPasswordField) node).getText().isEmpty()))
            {
                if(validos)
                {
                    invalidos += ((JFXPasswordField) node).getPromptText();
                }
                else
                {
                    invalidos += "," + ((JFXPasswordField) node).getPromptText();
                }
                validos = false;
            }
            else if(node instanceof JFXDatePicker && ((JFXDatePicker) node).getValue() == null)
            {
                if(validos)
                {
                    invalidos += ((JFXDatePicker) node).getAccessibleText();
                }
                else
                {
                    invalidos += "," + ((JFXDatePicker) node).getAccessibleText();
                }
                validos = false;
            }
            else if(node instanceof JFXComboBox && ((JFXComboBox) node).getSelectionModel().getSelectedIndex() < 0)
            {
                if(validos)
                {
                    invalidos += ((JFXComboBox) node).getPromptText();
                }
                else
                {
                    invalidos += "," + ((JFXComboBox) node).getPromptText();
                }
                validos = false;
            }
        }
        if(validos)
        {
            return "";
        }
        else
        {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }
        
    @Override
    public void initialize() {
        
    }
    
        private void bindEmpleado(Boolean nuevo){
        if(!nuevo){
        txtId.textProperty().bind(empleado.idEmpleado);
        cmbbxRol.setValue(intToRol());

        }
        txtCedula.textProperty().bindBidirectional(empleado.cedula);
        txtNombre.textProperty().bindBidirectional(empleado.nombre);
        txtApellido.textProperty().bindBidirectional(empleado.apellido);
        txtUsuario.textProperty().bindBidirectional(empleado.nombreUsuario);
        txtPassword.textProperty().bindBidirectional(empleado.password);
        
    }
    
    private void unbindEmpleado(){
        txtId.textProperty().unbind();
        txtCedula.textProperty().unbindBidirectional(empleado.cedula);
        txtNombre.textProperty().unbindBidirectional(empleado.nombre);
        txtApellido.textProperty().unbindBidirectional(empleado.apellido);
        txtUsuario.textProperty().unbindBidirectional(empleado.nombreUsuario);
        txtPassword.textProperty().unbindBidirectional(empleado.password);
        cmbbxRol.setValue(null);
    }
    
        private void nuevoEmpleado(){
        unbindEmpleado();
        empleado = new EmpleadoDto();
        bindEmpleado(true);
        txtId.clear();
        txtId.requestFocus();
    }
        
            private void cargarEmpleado(Long id){
        EmpleadoService service = new EmpleadoService();
        Respuesta respuesta = service.getEmpleado(id);

        if(respuesta.getEstado()){
            unbindEmpleado();
            empleado = (EmpleadoDto) respuesta.getResultado("Empleado");
            bindEmpleado(false);
            validarRequeridos();
        }
        else{
            new Mensaje().showModal(Alert.AlertType.ERROR , "Cargar empleado" , getStage() , respuesta.getMensaje());
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        if(txtId.getText() != null && !txtId.getText().isBlank()){
            cargarEmpleado(Long.valueOf(txtId.getText()));
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
     try{
            if(empleado.getIdEmpleado() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar empleado" , getStage() , "Debe cargar el empleado que desea eliminar.");
            }
            else{
                if(new Mensaje().showConfirmation("Eliminar empleado" , getStage() , "Está seguro que desea eliminar a "+ empleado.getNombre() +" "+ empleado.getApellido() +" permanentemente de la lista de empleados?.")){
                EmpleadoService service = new EmpleadoService();
                Respuesta respuesta = service.eliminarEmpleado(empleado.getIdEmpleado());
                if(!respuesta.getEstado()){
                    new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar empleado" , getStage() , respuesta.getMensaje());
                }
                else{
                    new Mensaje().showModal(Alert.AlertType.INFORMATION , "Eliminar empleado" , getStage() , "Empleado eliminado correctamente.");
                    nuevoEmpleado();
                }
                }
            }
        }
        catch(Exception ex)
        {
            Logger.getLogger(EmpleadoViewController.class.getName()).log(Level.SEVERE , "Error eliminando el empleado." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar empleado" , getStage() , "Ocurrio un error eliminando el empleado.");
        }   
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if(new Mensaje().showConfirmation("Limpiar empleado" , getStage() , "¿Esta seguro que desea limpiar el registro?")){
            nuevoEmpleado();
//            btnGuardar.setDisable(false);
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try{
            String invalidos = validarRequeridos();
            if(!invalidos.isEmpty()){
                new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar empleado" , getStage() , invalidos.toString());
            }else{
                EmpleadoService service = new EmpleadoService();
                empleado.setRol(rolToInt());
                Respuesta respuesta = service.guardarEmpleado(empleado);
                if(!respuesta.getEstado()){
                    new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar empleado" , getStage() , respuesta.getMensaje());
                }
                else{
                    unbindEmpleado();
                    empleado = (EmpleadoDto) respuesta.getResultado("Empleado");
                    bindEmpleado(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION , "Guardar empleado" , getStage() , "Empleado actualizado correctamente.");
                }
            }
        }
        catch(Exception ex ){
            Logger.getLogger(EmpleadoViewController.class.getName()).log(Level.SEVERE , "Error guardando el empleado." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar empleado" , getStage() , "Ocurrio un error guardando el empleado: "+ex.getMessage());
        }
    }

    private void cargarRoles() {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Administrador", "Cajero", "Salonero");

        cmbbxRol.setItems(items);
    }

    private String intToRol() {
        String eleccion = "";
        int e = Math.toIntExact(empleado.getRol());
            switch (e) {
                case 1:
                    eleccion = "Administrador";
                    break;
                case 2:
                    eleccion = "Cajero";
                    break;
                case 3:
                    eleccion = "Salonero";
                    break;
                default:
//                    new Mensaje().showModal(Alert.AlertType.ERROR, "Seleccionar rol", getStage(), "Debe seleccionar un rol.");
                    break;
            }
        return eleccion;
    }
    
    private Long rolToInt() {
        int eleccion = 0;
        if (cmbbxRol.getValue() != null) {

            switch (cmbbxRol.getValue()) {
                case "Administrador":
                    eleccion = 1;
                    break;
                case "Cajero":
                    eleccion = 2;
                    break;
                case "Salonero":
                    eleccion = 3;
                    break;
                default:
                    break;
            }} else {
//            new Mensaje().showModal(Alert.AlertType.ERROR, "Seleccionar rol", getStage(), "Debe seleccionar un rol.");
            return Long.valueOf(0);
        }
        return Long.valueOf(eleccion);
    }

}
