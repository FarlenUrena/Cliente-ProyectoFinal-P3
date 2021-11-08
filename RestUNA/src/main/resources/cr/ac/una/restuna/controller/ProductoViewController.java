/*To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Kenda
 */
public class ProductoViewController implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private ImageView imvImagen;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXTextField txtNombreCorto;
    @FXML
    private JFXTextField txtPrecio;
    @FXML
    private JFXComboBox<?> cmbbxGrupo;
    @FXML
    private JFXButton btnEditarGrupo;
    @FXML
    private JFXButton btnAgregarGrupo;
    @FXML
    private JFXCheckBox cbxAccesoRapido;
    @FXML
    private JFXTextField txtCantidadVendida;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXButton btnGuardar;
  
    @FXML
    private JFXButton btnBuscarFiltro;
    @FXML
    private ScrollPane scrlPanePrincipal;
    @FXML
    private GridPane gridPanePrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionKeyPressedId(KeyEvent event) {
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
    }

    @FXML
    private void onActionKeyPressedNombre(KeyEvent event) {
    }

    @FXML
    private void onActionBtnEditar(ActionEvent event) {
    }

    @FXML
    private void onActionKeyPressedNombreCorto(KeyEvent event) {
    }

    @FXML
    private void onActionBtnEditarGrupo(ActionEvent event) {
    }

    @FXML
    private void onActionBtnAgregarGrupo(ActionEvent event) {
    }

    @FXML
    private void onActionAccesoRapido(ActionEvent event) {
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
    }


    @FXML
    private void onActionBtnBuscarFiltro(ActionEvent event) {
    }
    
}
