/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

/**
 *
 * @author jeez
 */
import cr.ac.una.restuna.model.ProductoDto;
import cr.ac.una.restuna.service.ProductoService;
import cr.ac.una.restuna.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ProductosController extends Controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private ScrollPane scrlPanePrincipal1;

    @FXML
    private GridPane gridPaneAccesosRapidos;

    @FXML
    private ScrollPane scrlPanePrincipal;

    @FXML
    private GridPane gridPanePrincipal;

    List<ProductoDto> todosProductos = new ArrayList<>();
    List<ProductoDto> accesoProductos = new ArrayList<>();

    @Override
    public void initialize() {
        todosProductos = obtenerProductos();

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void cargarGrids() {

    }

    public List<ProductoDto> obtenerProductos() {
        ProductoService service = new ProductoService();
        Respuesta respuesta = service.getProductos();
        return (List<ProductoDto>) respuesta.getResultado("ProductosList");
    }

}
