/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ParametroDto;
import cr.ac.una.restuna.service.ParametroService;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Kendall
 */
public class ParametrosController  extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXTextField txtValNum;
    @FXML
    private JFXTextField txtValText;  
    @FXML
    private JFXTextArea TxtDescrip;
    
    ParametroDto nuevo;
    List<Node> requeridos = new ArrayList<>();
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        nuevo = new ParametroDto();
        nuevoParametro();  
        txtValNum.setTextFormatter(Formato.getInstance().twoDecimalFormat());
      //  txtId.setTextFormatter(Formato.getInstance().integerFormat());
        txtValText.setTextFormatter(Formato.getInstance().letrasFormat(35));
        txtNombre.setTextFormatter(Formato.getInstance().maxLengthFormat(35));
        indicarRequeridos();
    }   
    @Override
    public void initialize() {
       
    }
    
    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        
          try{
            if(nuevo.getIdParametro() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar parametro" , getStage() , "No existe el parametro que desea eliminar.");
            }
            else{
                if(new Mensaje().showConfirmation("Eliminar parametro" , getStage() , "Está seguro que desea eliminar "+ nuevo.getNombre()  +" permanentemente de la lista de parametros?.")){
                    ParametroService service = new ParametroService();
                    Respuesta respuesta = service.eliminarParametro(nuevo.getIdParametro());
                    if(!respuesta.getEstado()){
                        new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar parametro" , getStage() , respuesta.getMensaje());
                    }
                    else{
                        new Mensaje().showModal(Alert.AlertType.INFORMATION , "Eliminar parametro" , getStage() , "Parametro eliminado correctamente.");
                        nuevoParametro();
                    }
                }
            }
        }
        catch(Exception ex)
        {
            Logger.getLogger(ParametrosController.class.getName()).log(Level.SEVERE , "Error eliminando el parametro." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar parametro" , getStage() , "Ocurrio un error eliminando el parametro.");
        }      
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {GuardarParametro();}

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
    
        ParametroService service = new ParametroService();
        Respuesta respuesta = service.getParametro(Long.valueOf(txtId.getText()));

        if(respuesta.getEstado()){
            unbind();
            nuevo = (ParametroDto) respuesta.getResultado("Parametro");
            bind(false);
            validarRequeridos();
        }
        else{
            new Mensaje().showModal(Alert.AlertType.ERROR , "Cargar parametro" , getStage() , respuesta.getMensaje());
        }
    }
    
    @FXML
    private void onActionBtnEditar (ActionEvent event) {}
    
    public void indicarRequeridos(){
     requeridos.clear();
     requeridos.addAll(Arrays.asList(txtNombre,txtId));
    }
    
    public String validarRequeridos(){
    Boolean validos = true;
    String invalidos = "";
    for(Node node : requeridos)
    {
        if(node instanceof JFXTextField && (((JFXTextField) node).getText() == null || ((JFXTextField) node).getText().isBlank()))
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
    }
    if(validos)
    {
        return "";
    }
    else
    { return "Campos requeridos o con problemas de formato [" + invalidos + "].";}
}
    
    private void bind(Boolean esNuevo){ 
        txtId.textProperty().bindBidirectional(nuevo.idParametro);
        txtValText.textProperty().bindBidirectional(nuevo.valorTexto);
        txtNombre.textProperty().bindBidirectional(nuevo.nombre);
        txtValNum.textProperty().bindBidirectional(nuevo.valorNumerico);
        txtValText.textProperty().bindBidirectional(nuevo.valorTexto);
        TxtDescrip.textProperty().bindBidirectional(nuevo.descripcion);  
    }
    
    private void unbind(){    
        txtId.textProperty().unbind();
        txtValText.textProperty().bindBidirectional(nuevo.valorTexto);
        txtNombre.textProperty().unbindBidirectional(nuevo.nombre);
        txtValNum.textProperty().bindBidirectional(nuevo.valorNumerico);
        txtValText.textProperty().bindBidirectional(nuevo.valorTexto);
        TxtDescrip.textProperty().bindBidirectional(nuevo.descripcion);
    }
   
    private void nuevoParametro(){
     unbind();
     nuevo = new ParametroDto();
     bind(true);
     txtId.clear();
     txtId.requestFocus();
    }
    
    void GuardarParametro(){
        try{
          String invalidos = validarRequeridos();
          if(!invalidos.isBlank()){
              new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar par" , getStage() , invalidos);
          }else{
              ParametroService service = new ParametroService();
              Respuesta respuesta = service.guardarParametro(nuevo);
              if(!respuesta.getEstado()){
                  new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar par" , getStage() , respuesta.getMensaje());
              }
              else{
                  unbind();
                  nuevo = (ParametroDto) respuesta.getResultado("Parametro");
                  bind(false);
                  new Mensaje().showModal(Alert.AlertType.INFORMATION , "Guardar par" , getStage() , "Parametro guardado correctamente.");
              }
          }
        }
        catch(Exception ex ){
            Logger.getLogger(ParametrosController.class.getName()).log(Level.SEVERE , "Error guardando el par." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar par" , getStage() , "Ocurrio un error guardando el par: "+ex.getMessage());
        }
               
    }    
    
}
