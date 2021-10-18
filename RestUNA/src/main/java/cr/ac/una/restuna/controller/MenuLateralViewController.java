/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class MenuLateralViewController extends Controller implements Initializable {

    @FXML
    private JFXButton btnEmpleados;
    @FXML
    private JFXButton btnFactura;
    @FXML
    private JFXButton btnSecciones;
    @FXML
    private JFXButton btnProductos;
    @FXML
    private JFXButton btnOrdenes;
    @FXML
    private JFXButton btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    
    }

    @FXML
    private void onActionBtnEmpleados(ActionEvent event) {
//        FlowController.getInstance().goView("");
    }

    @FXML
    private void onActionBtnFacturas(ActionEvent event) {
        //        FlowController.getInstance().goView("");
    }

    @FXML
    private void onActionBtnSecciones(ActionEvent event) {
        //        FlowController.getInstance().goView("");
    }

    @FXML
    private void onActionBtnProductos(ActionEvent event) {
        //        FlowController.getInstance().goView("");
    }

    @FXML
    private void onActionBtnOrdenes(ActionEvent event) {
        //        FlowController.getInstance().goView("");
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        //        FlowController.getInstance().goView("");
    }
    
}
