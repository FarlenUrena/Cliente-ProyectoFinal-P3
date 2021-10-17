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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginViewController extends Controller implements Initializable{

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    void onAction_btnConfirmar(ActionEvent event) {

    }

    @FXML
    void onAction_btnSalir(ActionEvent event) {

    }

    

    @Override
    public void initialize() {
   
    }
    
}
