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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

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
    @FXML
    private ToggleGroup tggLang;
    
//   private ValidationSupport valspp;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tggLang.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
                RadioButton selectedRadioButton = (RadioButton) tggLang.getSelectedToggle();
                String toogleGroupValue = selectedRadioButton.getText();
               FlowController.getInstance().initialize();
              //Cerrar();
               FlowController.getInstance().setLang(toogleGroupValue);
               ((Stage) btnSalir.getScene().getWindow()).close();
              FlowController.getInstance().goViewInWindow("LoginView");
            }

        });
    }   
    
//    private void Cerrar(){
//        // FlowController.getInstance().salir();
//        ((Stage) btnSalir.getScene().getWindow()).close();
//        FlowController.getInstance().InitializeFlow(this.getStage(),null);
//    }
//    
    @FXML
    void onAction_btnConfirmar(ActionEvent event) {
        try
        {
            if(txtID.getText() == null || txtID.getText().isEmpty())
            {
                new Mensaje().showModal(Alert.AlertType.ERROR , "Validación de usuario" , (Stage) btnConfirmar.getScene().getWindow() , "Es necesario digitar un usuario para ingresar al sistema.");
            }
            else if(txtContra.getText() == null || txtContra.getText().isEmpty())
            {
                new Mensaje().showModal(Alert.AlertType.ERROR , "Validación de usuario" , (Stage) btnConfirmar.getScene().getWindow() , "Es necesario digitar la clave para ingresar al sistema.");
            }
            else
            {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.getUsuario(txtID.getText() , txtContra.getText());
                if(respuesta.getEstado()){
                    EmpleadoDto empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                    AppContext.getInstance().set("Usuario" , empleadoDto);
                    AppContext.getInstance().set("Token" , empleadoDto.getToken());
                    if(getStage().getOwner() == null)
                    {
                        
                        
                        FlowController.getInstance().goMain();
                    }
                    getStage().close();
                }
                else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR , "Ingreso" , getStage() , respuesta.getMensaje());
                }
            }
        }
        catch(Exception ex)
        {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE , "Error ingresando." , ex);
        }
    }

    @FXML
    void onAction_btnSalir(ActionEvent event) {
    ((Stage) btnSalir.getScene().getWindow()).close();
    }

    

    @Override
    public void initialize() {
        txtContra.setText("");
    }
    
}
