/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ParametroDto;
import cr.ac.una.restuna.service.ParametroService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.image.Image;

/**@author jeez
 */
public class ParametrosStaticController extends Controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXTextField txtNombreRest;

    @FXML
    private JFXTextField txtCorreo;

    @FXML
    private JFXTextField txtImpServ;

    @FXML
    private JFXTextField txtImpVent;

    @FXML
    private JFXTextField txtDescMax;

    @FXML
    private VBox VboxImg;

    @FXML
    private HBox hboxImg;

    @FXML
    private ImageView imvImagen;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnGuardar;
    
    @FXML
    private JFXPasswordField txtMailClave;
    @FXML
    private JFXButton showPsswrd;
    @FXML
    private JFXTextField txtTelefono;
    @FXML
    private JFXTextField txtEfectivo;
    
    List<Node> requeridos = new ArrayList<>();
    
    ParametroDto dto;
    
    void bind(){
        txtNombreRest.textProperty().bindBidirectional(dto.nombreRestaurante);
        txtCorreo.textProperty().bindBidirectional(dto.correoRestaurante);
        txtDescMax.textProperty().bindBidirectional(dto.descuentoMaximo);
        txtImpServ.textProperty().bindBidirectional(dto.impuestoServicio);
        txtImpVent.textProperty().bindBidirectional(dto.impuestoVenta);
        txtMailClave.textProperty().bindBidirectional(dto.psswrdCorreo);
        txtTelefono.textProperty().bindBidirectional(dto.telefonoRestaurante);
        txtEfectivo.textProperty().bindBidirectional(dto.efectivoInicial);
        Image i;
        
        if(dto.logoRestaurante != null){
            i = new Image(new ByteArrayInputStream(dto.getLogoRestaurante()));
            imvImagen.setImage(i);
        } 
    }
    
    void Unbind(){
        txtNombreRest.textProperty().unbindBidirectional(dto.nombreRestaurante);
        txtCorreo.textProperty().unbindBidirectional(dto.correoRestaurante);
        txtDescMax.textProperty().unbindBidirectional(dto.descuentoMaximo);
        txtImpServ.textProperty().unbindBidirectional(dto.impuestoServicio);
        txtImpVent.textProperty().unbindBidirectional(dto.impuestoVenta);
        imvImagen.imageProperty().unbindBidirectional(dto.logoRestaurante);
         txtMailClave.textProperty().unbindBidirectional(dto.psswrdCorreo);
        txtTelefono.textProperty().unbindBidirectional(dto.telefonoRestaurante);
        txtEfectivo.textProperty().unbindBidirectional(dto.efectivoInicial);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombreRest , txtCorreo , txtDescMax , txtImpServ,imvImagen,txtMailClave,txtTelefono,txtEfectivo));
        
         Respuesta respuesta = new Respuesta();
        try{
            ParametroService service = new ParametroService();
            respuesta = service.getParametro(Long.valueOf(1));
            dto = (ParametroDto) respuesta.getResultado("Parametro");
            System.out.println(dto.toString());  
            bind();
        }
        catch(Exception e){
           new Mensaje().showModal(Alert.AlertType.ERROR , "Cargando Parametro" , getStage() , respuesta.getMensaje());
        }
            
        
    }

    @Override
    public void initialize() {
        //todo:
        //refrescar vista
    }

    @FXML
    void onActionBtnEditar(ActionEvent event) {
        //todo:
        //cambiar imagen

    }

    @FXML
    void onActionBtnGuardar(ActionEvent event) {
        try{
            String invalidos = validarRequeridos(); 
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar param" , getStage() , invalidos.toString());
            }else{
                ParametroService service = new ParametroService();
                Respuesta respuesta = service.guardarParametro(dto);
                if(!respuesta.getEstado()){
                    new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar param" , getStage() , respuesta.getMensaje());
                }
                else{
                    Unbind();
                    dto = (ParametroDto) respuesta.getResultado("Parametro");
                    bind();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION , "Guardar param" , getStage() , "Empleado actualizado correctamente.");
                }
            }
        }
        catch(Exception ex ){
            Logger.getLogger(EmpleadoViewController.class.getName()).log(Level.SEVERE , "Error guardando el param." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar param" , getStage() , "Ocurrio un error guardando el param: "+ex.getMessage());
        }
    }

    private String validarRequeridos() {
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
            else if(node instanceof JFXPasswordField && (((JFXPasswordField) node).getText() == null  || ((JFXPasswordField) node).getText().isBlank()))
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
    
    

}
