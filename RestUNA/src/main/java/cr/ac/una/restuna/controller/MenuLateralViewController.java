/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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
        transitionView("EmpleadoView");
//        FlowController.getInstance().goView();
    }

    @FXML
    private void onActionBtnFacturas(ActionEvent event) {
        //        FlowController.getInstance().goView("");
    }

    @FXML
    private void onActionBtnSecciones(ActionEvent event) {
        //        FlowController.getInstance().goView("");
          transitionView("Salon");
    }

    @FXML
    private void onActionBtnProductos(ActionEvent event) {
        //        FlowController.getInstance().goView("");
        transitionView("ProductoView");
    }

    @FXML
    private void onActionBtnOrdenes(ActionEvent event) {
//                FlowController.getInstance().goView("");
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        //        FlowController.getInstance().goView("");
    }
    
    
    private void transitionView(String view){
        VBox centerVBox = (VBox) AppContext.getInstance().get("centerBox");
        
    final double alturaInicio = centerVBox.getHeight();
        final Animation hideCenter = new Transition() {
            { setCycleDuration(Duration.millis(200)); }
            protected void interpolate(double frac) {
                final double curWidth = alturaInicio * (1.0 - frac);
                centerVBox.setTranslateY(-alturaInicio + curWidth);
            }
        };
        final Animation showCenter = new Transition() {
            { setCycleDuration(Duration.millis(200)); }
            protected void interpolate(double frac) {
                final double curWidth = alturaInicio * (2.0 - frac);
                centerVBox.setTranslateY((alturaInicio-(alturaInicio*2)) + curWidth);
            }
        };
        hideCenter.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                centerVBox.setVisible(false);
                showCenter.play();
                FlowController.getInstance().goView(view);
                centerVBox.setVisible(true);
                showCenter.play();
            }
        });
        hideCenter.play();
    }
}
