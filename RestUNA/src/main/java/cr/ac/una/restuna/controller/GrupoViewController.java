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
import cr.ac.una.restuna.model.GrupoDto;
import cr.ac.una.restuna.service.GrupoService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.FlowController;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class GrupoViewController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnGuardar;

    GrupoDto grupo;
    List<Node> requeridos = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

       grupo = new GrupoDto();
       txtId.setTextFormatter(Formato.getInstance().integerFormat());
       txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));

        nuevoGrupo();
        indicarRequeridos();
        
    }

    @Override
    public void initialize() {
         
        
        if(!(boolean) AppContext.getInstance().get("modeViewGrupo")){
            unbindGrupo();
            grupo = (GrupoDto) AppContext.getInstance().get("grupoSelected");
            bindGrupo(false);
            validarRequeridos();
//          cargarGrupo(grupo.getIdGrupo());   
        }else{
        unbindGrupo();
        nuevoGrupo(); 
        bindGrupo(false);
        indicarRequeridos();
        }
        //Valida si se va a editar o crear un grupo
//          nuevoGrupo();
//        txtId.setTextFormatter(Formato.getInstance().integerFormat());
//        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
//
//        grupo = new GrupoDto();
//        nuevoGrupo();
//        indicarRequeridos();
//        
//        //Valida si se va a editar o crear un grupo
//        if(!(boolean) AppContext.getInstance().get("modeViewGrupo")){
//            unbindGrupo();
//            grupo = (GrupoDto) AppContext.getInstance().get("grupoSelected");
//            bindGrupo(false);
//            validarRequeridos();
////          cargarGrupo(grupo.getIdGrupo());   
//        }
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombre));
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
    private void nuevoGrupo() {
        unbindGrupo();
        grupo = new GrupoDto();
        bindGrupo(true);
        txtId.clear();
        txtId.requestFocus();
    }

    private void unbindGrupo() {
        
        txtId.textProperty().unbind();
        txtNombre.textProperty().unbindBidirectional(grupo.nombreGrupo);
    }

    private void bindGrupo(boolean nuevo) {
        if (!nuevo) {
            txtId.textProperty().bind(grupo.idGrupoDto);
        }
        txtNombre.textProperty().bindBidirectional(grupo.nombreGrupo);
    }

    @FXML
    private void onActionKeyPressedId(KeyEvent event) {
        
    }

    private void cargarGrupo(Long id) {
        GrupoService service = new GrupoService();
        Respuesta respuesta = service.getGrupo(id);

        if (respuesta.getEstado()) {
            unbindGrupo();
            grupo = (GrupoDto) respuesta.getResultado("Grupo");
            bindGrupo(false);
            validarRequeridos();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar grupo", getStage(), respuesta.getMensaje());
        }
    }

    @FXML
    private void onActionKeyPressedNombreCorto(KeyEvent event) {
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
             try{
            if(grupo.getIdGrupoDto()== null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar grupo" , getStage() , "Debe cargar el grupo que desea eliminar.");
            }
            else{
                if(new Mensaje().showConfirmation("Eliminar grupo" , getStage() , "Está seguro que desea eliminar a "+ grupo.getNombreGrupo() + " permanentemente de la lista de grupos?.")){
                    GrupoService service = new GrupoService();
                    Respuesta respuesta = service.eliminarGrupo(grupo.getIdGrupoDto());
                    if(!respuesta.getEstado()){
                        new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar grupo" , getStage() , respuesta.getMensaje());
                    }
                    else{
                        new Mensaje().showModal(Alert.AlertType.INFORMATION , "Eliminar grupo" , getStage() , "Grupo eliminado correctamente.");
                        nuevoGrupo();
                        
            AppContext.getInstance().set("grupoSelected", null);
                    }
                }
            }
        }
        catch(Exception ex)
        {
            Logger.getLogger(GrupoViewController.class.getName()).log(Level.SEVERE , "Error eliminando el grupo." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar grupo" , getStage() , "Ocurrio un error eliminando el grupo.");
        }
             this.getStage().close();
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try{
            String invalidos = validarRequeridos();
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar grupo" , getStage() , invalidos.toString());
            }else{
                GrupoService service = new GrupoService();
                Respuesta respuesta = service.guardarGrupo(grupo);
                if(!respuesta.getEstado()){
                    new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar grupo" , getStage() , respuesta.getMensaje());
                }
                else{
                    unbindGrupo();
                    grupo = (GrupoDto) respuesta.getResultado("Grupo");
                    bindGrupo(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION , "Guardar grupo" , getStage() , respuesta.getMensaje());
                }
            }
        }
        catch(Exception ex ){
            Logger.getLogger(GrupoViewController.class.getName()).log(Level.SEVERE , "Error guardando el grupo." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar grupo" , getStage() , "Ocurrio un error guardando el grupo: "+ex.getMessage());
        }
        this.getStage().close();
    }
    @FXML
    void onActionBtnCancelar(ActionEvent event) {
        this.getStage().close();

    }

}
