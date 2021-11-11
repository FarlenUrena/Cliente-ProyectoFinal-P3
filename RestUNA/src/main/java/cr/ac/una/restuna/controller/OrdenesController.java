/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.model.ProductoDto;
import cr.ac.una.restuna.model.ProductoporordenDto;
import cr.ac.una.restuna.pojos.HorizontalGrid;
import cr.ac.una.restuna.pojos.ItemProductCarrito;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import cr.ac.una.restuna.service.ProductoService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author Kendall
 */
public class OrdenesController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private ScrollPane scrlPanePrincipal;
    @FXML
    private GridPane gridPanePrincipal;
    @FXML
    private JFXTextField txtINombre;
    @FXML
    private JFXTextField txtCant;
    @FXML
    private JFXTextField txtPrecio;
    @FXML
    private JFXButton btnOrdenar;
    @FXML
    private Label lblNombreSeccion;
    @FXML
    private Label lblMesa;
    @FXML
    private Label lblSubTotal;

    //VARIABLES
    List<ProductoDto> productos = new ArrayList<>();
    ProductoDto productoDtoActual;
    OrdenDto ordeneDto;
    ProductoporordenDto pxo;
    int cantidad = 1;

    //-----------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarProductos();
        

    }

    @Override
    public void initialize() {
        ordeneDto = new OrdenDto();
        ordeneDto = (OrdenDto) AppContext.getInstance().get("OrdenActual");
        lblNombreSeccion.setText(ordeneDto.getIdElementodeseccionDto().getIdSeccionDto().getNombre());
        lblMesa.setText(ordeneDto.getIdElementodeseccionDto().getNombre());
//       inicializarGrid(productos);
        cargarGrids(productos);
        // txtCantidad.setTextFormatter(Formato.getInstance().integerFormat());

    }

    private void MultPrec() {
        double nprecio;
        nprecio = Double.parseDouble(productoDtoActual.precio.get());
        nprecio = nprecio * cantidad;
        pxo.subtotal.set(Double.toString(nprecio));

        pxo.precioProducto.set(Double.toString(nprecio));
        // txtPrecio.setText(Double.toString(nprecio));
    }

    private void bind() {
        // txtId.textProperty().bind(productoDtoActual.idProducto);
        txtINombre.textProperty().bindBidirectional(productoDtoActual.nombre);
        txtCant.textProperty().bindBidirectional(pxo.cantidad);
        txtPrecio.textProperty().bindBidirectional(pxo.precioProducto);
        lblSubTotal.textProperty().bindBidirectional(pxo.subtotal);
        MultPrec();

    }

    private void Unbind() {
//        txtId.textProperty().unbind();
        txtINombre.textProperty().unbind();
        txtPrecio.textProperty().unbind();
    }

    private void cargarProductos() {
        ProductoService service = new ProductoService();
        Respuesta respuesta = service.getProductos();

        if (respuesta.getEstado()) {

            productos = (List<ProductoDto>) respuesta.getResultado("ProductosList");

        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), respuesta.getMensaje());
        }
    }

    private void cargarProducto(ProductoDto ip2) {
//        ProductoService service = new ProductoService();
//        Respuesta respuesta = service.getProducto(id);

        if (ip2 != null) {
            productoDtoActual = ip2;
            System.out.println(productoDtoActual.toString());
            Unbind();
            bind();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), "Error cargando el producto seleccionado");
        }
    }

//    private void inicializarGrid(){
//      if (productos != null && !productos.isEmpty()) {
//          gridPanePrincipal.getChildren().clear();
//          if (productos != null) {
//              int col = 0;
//              int row = 1;
//
//              for (ProductoDto pd : productos) {
//                  ItemProduct ip = new ItemProduct(pd);
//
//                  ip.setOnMouseClicked(MouseEvent -> {
//                      cargarProducto(ip.getIdProduct());        
//                  });
//                  if (col == 3) {
//                      col = 0;
//                      row++;
//                  }
//                  gridPanePrincipal.add(ip, col++, row);
//                  GridPane.setMargin(ip, new Insets(10));
//              }
//
//          }
//      }
//    } 
    private void cargarGrids(List<ProductoDto> productos) {

        Collections.sort(productos, comparProductoGrupoDtoId);

        Long idGrurpoIdentifier = 0L;
        boolean esGrupoNuevo = true;
        if (productos != null || !productos.isEmpty()) {

            gridPanePrincipal.getChildren().clear();
//            gridPaneAccesosRapidos.getChildren().clear();

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

//                if (esGrupoNuevo) {
//                    //TODOS LOS PRODUCTOS-----------------------------
//                    Label lbl = new Label();
//                    lbl.setText(pd.grupoDto.getNombreGrupo());
//                    lbl.setWrapText(true);
//                    lbl.setTextAlignment(TextAlignment.JUSTIFY);
//                    //Setting the maximum width of the label
//                    lbl.setMaxWidth(400);
//                    lbl.setStyle("-fx-font-size: 30px;"
//                            + "-fx-text-fill:  #E0EEF6;"
//                    );
//
//                    gridPanePrincipal.add(lbl, 0, rowP++);
//                    GridPane.setMargin(lbl, new Insets(10));
//                    //HORIZONTAL
//                    hGridPrincipal = new HorizontalGrid();
//                    gridPanePrincipal.add(hGridPrincipal, 0, rowP++);
//                    colP = 0;
//
//                }
//                ItemProductCarrito ip = new ItemProductCarrito(pd);
//                hGridPrincipal.addToGrid(ip, colP++, 0);
                if (pd.getEsAccesoRapido().equals(1L)) {
                    if (esGrupoNuevo) {
                        //ACCESOS RAPIDOS-----------------------------
                        JFXButton lbl2 = new JFXButton();
                        lbl2.setText(pd.grupoDto.getNombreGrupo() + "ˇ");
                        lbl2.setWrapText(true);
                        lbl2.setTextAlignment(TextAlignment.JUSTIFY);
                        //Setting the maximum width of the label
                        lbl2.setMaxHeight(50);
                        lbl2.setStyle("-fx-font-size: 30px;"
                                + "-fx-text-fill:  #E0EEF6;"
                        );
                        gridPanePrincipal.add(lbl2, 0, rowA++);
                        GridPane.setMargin(lbl2, new Insets(10));
                        //HORIZONTAL
                        hGridRapidos = new HorizontalGrid();
                        gridPanePrincipal.add(hGridRapidos, 0, rowA++);
                        hGridRapidos.setStyle("-fx-max-height:0px;"
                                + "-fx-max-width: 550px;");
                        colA = 0;
                        setClickGrupos(lbl2, hGridRapidos);
                    }
                    ItemProductCarrito ip2 = new ItemProductCarrito(pd);
                    ip2.btnAgregar.setOnMouseClicked(MouseEvent -> {
                        cargarProducto(ip2.getProductoDto());
                    });
                    hGridRapidos.addToGrid(ip2, colA++, 0);
                }
            }
        }
    }

    public void setClickGrupos(JFXButton lbl, HorizontalGrid hGrid) {
        lbl.setOnAction(MouseEvent -> {
            hGrid.toogleVisible();
        });
    }

    Comparator<ProductoDto> comparProductoGrupoDtoId = new Comparator<ProductoDto>() {
        public int compare(ProductoDto p1, ProductoDto p2) {
            return p1.getGrupoDto().getIdGrupoDto().compareTo(p2.getGrupoDto().getIdGrupoDto());
        }
    };

    @FXML
    private void onActionKeyPressedId(KeyEvent event) {
    }

    @FXML
    private void OnActionMinus(ActionEvent event) {
        if (cantidad > 0) {
            cantidad--;
            pxo.cantidad.set(Integer.toString(cantidad));
            //txtCant.setText(Integer.toString(cantidad));
            MultPrec();
        }
    }

    @FXML
    private void OnActionSum(ActionEvent event) {
        cantidad++;
        txtCant.setText(Integer.toString(cantidad));
        MultPrec();
    }

    @FXML
    private void onActionBtnOrdenar(ActionEvent event) {
//        pxo = new ProductoporordenDto();
//
//        ProductoDto productoDtoActual;
//        OrdenDto ordeneDto;
    }

}
