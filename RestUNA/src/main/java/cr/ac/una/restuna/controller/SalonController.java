/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.util.DraggableMaker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Kenda
 */
public class SalonController extends Controller implements Initializable {

    @FXML
    private AnchorPane sala;
    @FXML
    private VBox root;
    @FXML
    private JFXTextField txtCant;
    
    JFXButton mesa;
    
    
    void SeteaMesas(int cant){     
        DraggableMaker maker = new DraggableMaker();
        double layx=0;
        
        for(int i = 0;i < cant;i++){
    
        mesa = new JFXButton();
        mesa.setStyle("-fx-background-color:black");
       
        mesa.setLayoutX(layx);
         layx=layx+55;
        mesa.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(mesa.getStyle().equals("-fx-background-color:green")) mesa.setStyle("-fx-background-color:red");
                else  mesa.setStyle("-fx-background-color:green");
            }
        });
        maker.makeDraggable(mesa);
        sala.getChildren().add(mesa);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        SeteaMesas(4);
        
    }
    
}
