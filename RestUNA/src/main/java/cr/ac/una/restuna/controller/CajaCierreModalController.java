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
import cr.ac.una.restuna.model.CajaDto;
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
public class CajaCierreModalController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private Label lblTitle;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label lblTitle1;
    @FXML
    private JFXTextField txtSaldEf;
    @FXML
    private JFXTextField txtSaldTar;
    @FXML
    private Button btnNueva;

    List<Node> requeridos = new ArrayList<>();
    CajaDto caja;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtSaldEf.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtSaldTar.setTextFormatter(Formato.getInstance().twoDecimalFormat());

    }

    @Override
    public void initialize() {
        txtSaldEf.setText("0.00");
        txtSaldTar.setText("0.00");
        caja = (CajaDto) AppContext.getInstance().get("cajaCerrar");
    }

    @FXML
    void onActionBtnCancelar(ActionEvent event) {
        AppContext.getInstance().set("cajaCierreModal", "caceled");
        this.getStage().close();
    }

    @FXML
    void onActionBtnNueva(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (invalidos.isEmpty()) {
                if (caja.getSaldoEfectivo() > Double.valueOf(txtSaldEf.getText())) {
                    double faltante = caja.getSaldoEfectivo() - Double.valueOf(txtSaldEf.getText());
                    if (new Mensaje().showConfirmation("Cierre de Caja", getStage(), "Esta registrando ₡" + txtSaldEf.getText() + "\nSegun el sistema faltan ₡" + faltante + "\nEs esto correcto?")) {
                        cerrarCaja();
                    }
                } else if (caja.getSaldoEfectivo() < Double.valueOf(txtSaldEf.getText())) {
                    double sobrante = Double.valueOf(txtSaldEf.getText()) - caja.getSaldoEfectivo();
                    if (new Mensaje().showConfirmation("Cierre de Caja", getStage(), "Esta registrando ₡" + txtSaldEf.getText() + "\nSegun el sistema sobran ₡" + sobrante + "\nEs esto correcto?")) {
                        cerrarCaja();
                    }
                } else if (caja.getSaldoEfectivo().equals(Double.valueOf(txtSaldEf.getText()))) {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Cierre de Caja", getStage(), "El monto registrado es igual al del sistema!");
                    cerrarCaja();
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cierre de Caja", getStage(), invalidos);

            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cierre de Caja", getStage(), e.getMessage());
        }

    }

    public void cerrarCaja() {
        AppContext.getInstance().set("saldoEfectivoMarcado", txtSaldEf.getText());
        AppContext.getInstance().set("saldoTargetaMarcado", txtSaldTar.getText());

        AppContext.getInstance().set("cajaCierreModal", "ok");
        this.getStage().close();
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtSaldEf, txtSaldTar));
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
