/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cr.ac.una.restuna.model.GrupoDto;
import cr.ac.una.restuna.model.SeccionDto;
import cr.ac.una.restuna.pojos.ItemSecciones;
import cr.ac.una.restuna.service.GrupoService;
import cr.ac.una.restuna.service.SeccionService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Respuesta;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Kenda
 */
public class GaleriaSeccionesController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private VBox root;

    @FXML
    private ScrollPane scrlPanePrincipal;

    @FXML
    private GridPane gridPanePrincipal;

    List<SeccionDto> seccionesDto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        seccionesDto = new ArrayList<>();
        cargarSecciones();

        // TODO
    }    

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }
    void cargarSecciones(){
        gridPanePrincipal.getChildren().clear();
        seccionesDto =  obtenerSecciones();
        int col = 1;
        int row = 1;

        ItemSecciones itemSeccion = new ItemSecciones();
        gridPanePrincipal.add(itemSeccion, 0, 1);
        itemSeccion.setOnMouseClicked(MouseEvent -> {
            //crear salon
            FlowController.getInstance().goView("SeccionesEditorView");
//                        cargarProducto(ip.getIdProduct());
        });
        GridPane.setMargin(itemSeccion, new Insets(10));
        if(!seccionesDto.isEmpty() || seccionesDto != null){

            for (SeccionDto seccionDto : seccionesDto) {
                itemSeccion = new ItemSecciones(seccionDto , false);

                itemSeccion.setOnMouseClicked(MouseEvent -> {
//                    AppContext.getInstance().set("",seccionDto);
                    //ver salon
//                        cargarProducto(ip.getIdProduct());
                });
                if (col == 4) {
                    col = 1;
                    row++;
                }
                gridPanePrincipal.add(itemSeccion, col++, row);
                GridPane.setMargin(itemSeccion, new Insets(10));
            }
        }


    }
    private List<SeccionDto> obtenerSecciones() {
        SeccionService service = new SeccionService();
        Respuesta respuesta = service.getSecciones();
        return (List<SeccionDto>) respuesta.getResultado("SeccionesList");
    }
}

