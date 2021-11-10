/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import cr.ac.una.restuna.util.FlowController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class BaseContainerSecondViewController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblTitulo1;
    @FXML
    private JFXButton btnContraer;
    @FXML
    private JFXButton btnMaxMin;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private HBox hbButtonContainer;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private VBox centerVBox;
    @FXML
    private JFXButton btnHome;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        root.setLeft(null);
        drawerHamb();
        
        
        
        
    }    

    
        private void drawerHamb(){
        try {
            VBox vbox = FXMLLoader.load(getClass().getResource("/cr/ac/una/restuna/view/MenuLateralOrdenes.fxml"));
            drawer.setSidePane(vbox);
            drawer.setMinWidth(0);
        }catch (IOException ex) {    
            Logger.getLogger(BaseContainerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        HamburgerSlideCloseTransition  transition = new HamburgerSlideCloseTransition (hamburger);
        transition.setRate(-1);
        
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            drawer.toggle();
        });
        drawer.setOnDrawerOpening((event)->{
            transition.setRate(transition.getRate() * -1);
            transition.play();
            root.setLeft(drawer);
            drawer.setMinWidth(250);
//            hamburger.setAlignment(Pos.CENTER_RIGHT);
        });

        drawer.setOnDrawerClosed((event)->{
            transition.setRate(transition.getRate() * -1);
            transition.play();
            root.setLeft(null);
            drawer.setMinWidth(0);
//            hamburger.setAlignment(Pos.CENTER_LEFT);
        });
    }
    
    @FXML
    private void onAction_btnContraer(ActionEvent event) {
        ((Stage) root.getScene().getWindow() ).setIconified(true);
    }

    @FXML
    private void onAction_btnMaxMin(ActionEvent event) {
        if(((Stage) root.getScene().getWindow() ).isMaximized()){
            ((Stage) root.getScene().getWindow() ).setMaximized(false);
        }else{
            ((Stage) root.getScene().getWindow() ).setMaximized(true);
        }
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        ((Stage) root.getScene().getWindow() ).close();
    }

    @FXML
    private void onActionBtnHome(ActionEvent event) {
        
        FlowController.getInstance().goView("SeccionesGalleryView");
    }

    @Override
    public void initialize() {
    
    }
    
}
