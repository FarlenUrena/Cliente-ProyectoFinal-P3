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
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.Formato;
import cr.ac.una.restuna.util.Mensaje;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author jeez
 */
public class CajaCambioModalController extends Controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private Label lblMontoPagadp;

    @FXML
    private Label lblVuelto;

    @FXML
    private JFXTextField txtMontoExtrido;

    @FXML
    private Button btnNueva;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtMontoExtrido.setTextFormatter(Formato.getInstance().twoDecimalFormat());
    }

    List<Node> requeridos = new ArrayList<>();

    @Override
    public void initialize() {

        lblMontoPagadp.setText((String) AppContext.getInstance().get("MontoPagar"));
        lblVuelto.setText((String) AppContext.getInstance().get("VueltoDar"));
        txtMontoExtrido.setText("0.00");
    }

    @FXML
    void onActionBtnCancelar(ActionEvent event) {
        AppContext.getInstance().set("cajaModal", "canceled");
        this.getStage().close();
    }

    @FXML
    void onActionBtnNueva(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (invalidos.isEmpty()) {
//                if () {
                    AppContext.getInstance().set("montoSustraido", txtMontoExtrido.getText());
                    AppContext.getInstance().set("cajaModal", "ok");
                    this.getStage().close();
//                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar producto", getStage(), invalidos);

            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar producto", getStage(), e.getMessage());

        }
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtMontoExtrido));
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

}
