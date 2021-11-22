/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.service.EmpleadoService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginViewController extends Controller implements Initializable {

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
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtID.requestFocus();
            }
        });
        txtID.requestFocus();
//        txtID.setFocusTraversable(true);
    }

      private void CambioLang(String lang){
	 FlowController.getInstance().initialize();
        FlowController.getInstance().setLang(lang);
        ((Stage) btnSalir.getScene().getWindow()).close();
        FlowController.getInstance().goViewInWindow("LoginView");
    }

     @FXML
    private void OnActENG(ActionEvent event) {
         CambioLang("ENG");
    }

    @FXML
    private void OnActESP(ActionEvent event) {
         CambioLang("ESP");
    }
//    
    @FXML
    void onAction_btnConfirmar(ActionEvent event) {
        try {
            if (txtID.getText() == null || txtID.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) btnConfirmar.getScene().getWindow(), "Es necesario digitar un usuario para ingresar al sistema.");
            } else if (txtContra.getText() == null || txtContra.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) btnConfirmar.getScene().getWindow(), "Es necesario digitar la clave para ingresar al sistema.");
            } else {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.getUsuario(txtID.getText(), txtContra.getText());
                if (respuesta.getEstado()) {
                    EmpleadoDto empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                    AppContext.getInstance().set("Usuario", empleadoDto);
                    AppContext.getInstance().set("Token", empleadoDto.getToken());
                    if (getStage().getOwner() == null) {

                        if (empleadoDto.getRol() == 1L) {
                            FlowController.getInstance().goMainAdmin();
                        } else {
                            FlowController.getInstance().goMain();
                        }
                    }
                    AppContext.getInstance().set("authorEstatus", "ok");

                    getStage().close();
                } else {
                    AppContext.getInstance().set("authorEstatus", "error");
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Ingreso", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, "Error ingresando.", ex);
        }
    }

    @FXML
    void onAction_btnSalir(ActionEvent event) {
        AppContext.getInstance().set("authorEstatus", "error");
        ((Stage) btnSalir.getScene().getWindow()).close();
    }

    @Override
    public void initialize() {

        txtContra.setText("");

        txtID.requestFocus();
    }

}
