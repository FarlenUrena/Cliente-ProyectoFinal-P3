/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXDatePicker;
import cr.ac.una.restuna.controller.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import cr.ac.una.restuna.util.Mensaje;

/**
 * FXML Controller class
 *
 * @author Kenda
 */
public class ReportesController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private VBox boxView;
    @FXML
    private JFXDatePicker dpCierre;
    @FXML
    private JFXDatePicker dpINI;
    @FXML
    private JFXDatePicker dpFin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnfacturas(ActionEvent event) {
        if(dpINI.getValue() == null || dpFin == null){
             new Mensaje().showModal(Alert.AlertType.ERROR , "Datos incompletos" , getStage() , "Fecha inicial y final deben ser llenadas");
        }else{
            
        }
    }

    @FXML
    private void btncaja(ActionEvent event) {
         if(dpCierre.getValue() == null){
            new Mensaje().showModal(Alert.AlertType.ERROR , "Datos incompletos" , getStage() , "Fecha de cierre debe ser llenada");
         }
         else{
             
         }
    }

    @FXML
    private void btnproduc(ActionEvent event) {
         if(dpINI.getValue() == null || dpFin == null){
            new Mensaje().showModal(Alert.AlertType.ERROR , "Datos incompletos" , getStage() , "Fecha inicial y final deben ser llenadas");
         }
         else{
             
         }
    }

    @Override
    public void initialize() {
        
    }
    
}
