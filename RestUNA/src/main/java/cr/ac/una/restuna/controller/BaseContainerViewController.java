/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.util.AppContext;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class BaseContainerViewController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private Label lblUsuario;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblTitulo1;
    @FXML
    private VBox centerVBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        EmpleadoDto empOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        lblUsuario.setText(empOnline.getNombreUsuario());
        AppContext.getInstance().set("centerBox", centerVBox);
        root.setLeft(null);
        drawerHamb();
    }    

    @Override
    public void initialize() {
        
    }
    
    private void drawerHamb(){
    try {
           VBox vbox = FXMLLoader.load(getClass().getResource("/cr/ac/una/restuna/view/MenuLateralView.fxml"));
           drawer.setSidePane(vbox);
           drawer.setMinWidth(0);
    }   catch (IOException ex) {    
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
            drawer.setMinWidth(220);
            hamburger.setAlignment(Pos.BOTTOM_RIGHT);
            });
        
            drawer.setOnDrawerClosed((event)->{
            transition.setRate(transition.getRate() * -1);
            transition.play();
            root.setLeft(null);
            drawer.setMinWidth(0);
            hamburger.setAlignment(Pos.BOTTOM_LEFT);
            });
            
       
    
    }
    
}
