package cr.ac.una.restuna.controller;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarElementosDeSeccionController extends Controller implements Initializable {
    @FXML
    private VBox root;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXComboBox<?> cmbxTipo;

    @FXML
    private JFXCheckBox chkImpuesto;

    @FXML
    private ImageView ivImagenElemento;

    @FXML
    private Button btnCambiarImagen;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;

    @FXML
    void onActionBtnCambiarImagen(ActionEvent event) {
        //TODO: CAMBIAR IMAGEN
    }

    @FXML
    void onActionBtnCancelar(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    void onActionBtnGuardar(ActionEvent event) {
        //TODO: GUARDAR
        this.getStage().close();
    }
    @FXML
    void onActionBtnEliminar(ActionEvent event) {
        //TODO: ELIMINAR
        this.getStage().close();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
