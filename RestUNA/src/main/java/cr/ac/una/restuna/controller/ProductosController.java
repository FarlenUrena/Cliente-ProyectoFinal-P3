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
import cr.ac.una.restuna.pojos.HorizontalGrid;
import cr.ac.una.restuna.pojos.ItemProductCarrito;
import cr.ac.una.restuna.service.ProductoService;
import cr.ac.una.restuna.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

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

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void cargarGrids(List<ProductoDto> productos) {

        Collections.sort(productos, comparProductoGrupoDtoId);

        Long idGrurpoIdentifier = 0L;
        boolean esGrupoNuevo = true;
        if (productos != null || !productos.isEmpty()) {

            gridPanePrincipal.getChildren().clear();
            gridPaneAccesosRapidos.getChildren().clear();

            int rowP = 1;
            int rowA = 1;

            int colP = 0;
            int colA = 0;

            HorizontalGrid hGridPrincipal = new HorizontalGrid();
            HorizontalGrid hGridRapidos = new HorizontalGrid();
            for (ProductoDto pd : productos) {

                if (idGrurpoIdentifier.equals(pd.grupoDto.getIdGrupoDto())) {
                    esGrupoNuevo = false;
                } else {
                    idGrurpoIdentifier = pd.grupoDto.getIdGrupoDto();
                    esGrupoNuevo = true;
                }

                if (esGrupoNuevo) {
                    //TODOS LOS PRODUCTOS-----------------------------
                    Label lbl = new Label();
                    lbl.setText(pd.grupoDto.getNombreGrupo());
                    lbl.setWrapText(true);
                    lbl.setTextAlignment(TextAlignment.JUSTIFY);
                    //Setea el maximo ancho del label
                    lbl.setMaxWidth(400);
                    lbl.setStyle("-fx-font-size: 30px;"
                            + "-fx-text-fill:  #E0EEF6;"
                    );

                    gridPanePrincipal.add(lbl, 0, rowP++);
                    GridPane.setMargin(lbl, new Insets(10));
                    //HORIZONTAL
                    hGridPrincipal = new HorizontalGrid();
                    gridPanePrincipal.add(hGridPrincipal, 0, rowP++);
                    colP = 0;

                }
                ItemProductCarrito ip = new ItemProductCarrito(pd);
                hGridPrincipal.addToGrid(ip, colP++, 0);

                if (pd.getEsAccesoRapido().equals(1L)) {
                    if (esGrupoNuevo) {
                        //ACCESOS RAPIDOS-----------------------------
                        Label lbl2 = new Label();
                        lbl2.setText(pd.grupoDto.getNombreGrupo());
                        lbl2.setWrapText(true);
                        lbl2.setTextAlignment(TextAlignment.JUSTIFY);
                         //Setea el maximo ancho del label
                        lbl2.setMaxWidth(400);
                        lbl2.setStyle("-fx-font-size: 30px;"
                                + "-fx-text-fill:  #E0EEF6;"
                        );
                        gridPaneAccesosRapidos.add(lbl2, 0, rowA++);
                        GridPane.setMargin(lbl2, new Insets(10));
                        //HORIZONTAL
                        hGridRapidos = new HorizontalGrid();
                        gridPaneAccesosRapidos.add(hGridRapidos, 0, rowA++);
                        colA = 0;

                        setClickGrupos(lbl2, hGridRapidos);
                    }

                    ItemProductCarrito ip2 = new ItemProductCarrito(pd);
                    hGridRapidos.addToGrid(ip2, colA++, 0);

                }

            }
        }
    }

    public void setClickGrupos(Label lbl, HorizontalGrid hGrid) {
        lbl.setOnMouseClicked(MouseEvent -> {
            hGrid.toogleVisible();
        });
    }

    Comparator<ProductoDto> comparProductoGrupoDtoId = new Comparator<ProductoDto>() {
        public int compare(ProductoDto p1, ProductoDto p2) {
            return p1.getGrupoDto().getIdGrupoDto().compareTo(p2.getGrupoDto().getIdGrupoDto());
        }
    };

    public List<ProductoDto> obtenerProductos() {
        ProductoService service = new ProductoService();
        Respuesta respuesta = service.getProductos();
        return (List<ProductoDto>) respuesta.getResultado("ProductosList");
    }

}
