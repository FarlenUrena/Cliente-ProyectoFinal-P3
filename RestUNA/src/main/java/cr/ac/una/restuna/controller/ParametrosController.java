/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ParametroDto;
import cr.ac.una.restuna.service.ParametroService;
import cr.ac.una.restuna.util.Formato;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Kendall
 */
public class ParametrosController  extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXTextField txtNombre;
  
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
       txtID.setTextFormatter(Formato.getInstance().integerFormat());
       txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(15));
    }    
     
    void GuardarParametro(){
        
          try{
            String invalidos = "";
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar par" , getStage() , invalidos.toString());
            }else{
                ParametroDto nuevo = new ParametroDto(Long.valueOf(txtID.getText()),txtNombre.getText(),4,"zzz","xxxx");
                ParametroService service = new ParametroService();
            
                Respuesta respuesta = service.guardarParametro(nuevo);
                if(!respuesta.getEstado()){
                    new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar par" , getStage() , respuesta.getMensaje());
                }
                else{
                 //   unbindParametro();
                    nuevo = (ParametroDto) respuesta.getResultado("Parametro");
                  //  bindParametro(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION , "Guardar par" , getStage() , "Parametro actualizado correctamente.");
                }
            }
        }
        catch(Exception ex ){
            Logger.getLogger(ParametrosController.class.getName()).log(Level.SEVERE , "Error guardando el par." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar par" , getStage() , "Ocurrio un error guardando el par: "+ex.getMessage());
        }
        
        
    }  
    
       @FXML
    private void Guardar(ActionEvent event) {
        GuardarParametro();
    }
}
