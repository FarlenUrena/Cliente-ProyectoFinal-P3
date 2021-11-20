/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author jeez
 */
public class ParametrosStaticController extends Controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXTextField txtNombreRest;

    @FXML
    private JFXTextField txtCorreo;

    @FXML
    private JFXTextField txtImpServ;

    @FXML
    private JFXTextField txtImpVent;

    @FXML
    private JFXTextField txtDescMax;

    @FXML
    private VBox VboxImg;

    @FXML
    private HBox hboxImg;

    @FXML
    private ImageView imvImagen;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //todo:
        //jalar los lados desde la base
        //bindearlos
        //
    }

    @Override
    public void initialize() {
        //todo:
        //refrescar vista
    }

    @FXML
    void onActionBtnEditar(ActionEvent event) {
        //todo:
        //cambiar imagen

    }

    @FXML
    void onActionBtnGuardar(ActionEvent event) {
        //todo:
        //actualizar parametros

    }
    
    

}
