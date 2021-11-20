/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

iimport com.jfoenix.controls.JFXButton;
mport java.net.URL;
import java.util.ResourceBundle;
iimport javafx.event.ActionEvent;
import javafx.fxml.FXML;
mport javafx.fxml.Initializable;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author Kenda
 */
public class LoginViewController implements Initializable {


    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtID;
    @FXML
    private PasswordField txtContra;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnConfirmar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
   

    @FXML
    private void onAction_btnSalir(ActionEvent event) {
    }

    @FXML
    private void onAction_btnConfirmar(ActionEvent event) {
    }

}
