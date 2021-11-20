/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.model.ProductoDto;
import cr.ac.una.restuna.model.ProductoporordenDto;
import cr.ac.una.restuna.pojos.HorizontalGrid;
import cr.ac.una.restuna.pojos.ItemProductCarrito;
import cr.ac.una.restuna.pojos.ItemProductoPorOrden;
import cr.ac.una.restuna.service.OrdenService;
import cr.ac.una.restuna.pojos.ItemProduct;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import cr.ac.una.restuna.service.ProductoService;
import cr.ac.una.restuna.service.ProductoporordenService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import cr.ac.una.restuna.model.EmpleadoDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private HBox hboxAZ;
    @FXML
    private ToggleGroup tggAZ;

    private ScrollPane scrlPanePrincipal;
    @FXML
    private ScrollPane scrlpListPxO;
    @FXML
    private GridPane gridPanePrincipal;

    @FXML
    private GridPane grpListPxO;
    @FXML
    private JFXTextField txtINombre;
    @FXML
    private Label lblNombreSeccion;
    @FXML
    private Label lblMesa;

    @FXML
    private JFXTextField txtNombreCliente;
    @FXML
    private JFXButton btnFacturarOrden;
    @FXML
    private JFXButton btnBA;

    //VARIABLES
    List<ProductoDto> productos = new ArrayList<>();
    ProductoDto productoDtoActual;
    OrdenDto ordenDto;
    ProductoporordenDto pxo;
    List<ProductoporordenDto> productosPXO = new ArrayList<>();
    boolean isAvansada = false;
    int cantidad = 1;
    List<Node> requeridos = new ArrayList<>();

    //-----------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        EmpleadoDto empleadoOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        if (empleadoOnline.getIdEmpleado().equals(3L)) {
            btnFacturarOrden.setVisible(false);
        }

        cargarProductos();
        cargarGrids(productos);

        tggAZ.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) -> {
            RadioButton selectedRadioButton = (RadioButton) tggAZ.getSelectedToggle();
            String toogleGroupValue = selectedRadioButton.getText();
            if (toogleGroupValue.equals("A-Z")) {
                Collections.sort(productos, ProductoAlfabetico);
                BusquedaAvanzada();
            } else {
                Collections.sort(productos, ProductoAlfabetico);
                Collections.reverse(productos);
                BusquedaAvanzada();
            }
        });

    }

    @Override
    public void initialize() {

        requeridos.clear();

        requeridos.add(txtINombre);
//        requeridos.add(txtPrecio);
//        requeridos.add(txtCant);

        pxo = new ProductoporordenDto();
        pxo.setModificado(Boolean.FALSE);
        ordenDto = new OrdenDto();
        ordenDto = (OrdenDto) AppContext.getInstance().get("OrdenActual");

        lblNombreSeccion.setText(ordenDto.getIdElementodeseccionDto().getIdSeccionDto().getNombre());
        lblMesa.setText(ordenDto.getIdElementodeseccionDto().getNombre());
        if (ordenDto.getIdOrden() != null) {
            txtNombreCliente.setText(ordenDto.getNombreCliente());

        }

        refresListPxO();
        hboxAZ.setVisible(false);
        cargarGrids(productos);

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

    private void cargarProducto(ProductoDto ip2) {
//        ProductoService service = new ProductoService();
//        Respuesta respuesta = service.getProducto(id);

        if (ip2 != null) {
            productoDtoActual = ip2;

            if (ordenDto.getIdOrden() == null) {
                try {
                    OrdenService ordenService = new OrdenService();
                    Respuesta resp = ordenService.guardarOrden(ordenDto);
                    if (resp.getEstado()) {
                        ordenDto = (OrdenDto) resp.getResultado("OrdenGuardada");
                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), "Error crando la orden");
                    }
                } catch (Exception e) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar orden", getStage(), e.getMessage());
                }

            }

            pxo = new ProductoporordenDto();
            for (ProductoporordenDto px : productosPXO) {
                if (px.getIdProductoDto().getIdProducto().equals(productoDtoActual.getIdProducto())) {
                    pxo = px;
                }
            }

            if (pxo.getIdProductoPorOrden() == null) {
                pxo.setIdOrdenDto(ordenDto);
                pxo.setIdProductoDto(productoDtoActual);
                pxo.setPrecioProducto(productoDtoActual.getPrecio());
                pxo.setCantidad(1L);
                pxo.setModificado(Boolean.FALSE);
                pxo.setSubtotal(productoDtoActual.getPrecio());

            } else {
                pxo.setCantidad(pxo.getCantidad() + 1L);
                pxo.setModificado(Boolean.FALSE);
                pxo.setSubtotal(pxo.getSubtotal() + productoDtoActual.getPrecio());
            }
            try {
                ProductoporordenService service = new ProductoporordenService();
                Respuesta resp = service.guardarProductopororden(pxo);
                if (resp.getEstado()) {
                    pxo = (ProductoporordenDto) resp.getResultado("Productopororden");
                    refresListPxO();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), "Error agregado el producto al carrito");
                }
            } catch (Exception e) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), e.getMessage());

            }
//            Unbind();
//            bind();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), "Error cargando el producto seleccionado");
        }
    }

    public void refresListPxO() {
        grpListPxO.getChildren().clear();
        cargarProdsxO();

        if (!productosPXO.isEmpty()) {
            int row = 1;
            Collections.sort(productosPXO, comparProductoXOrden);
            Collections.reverse(productosPXO);
            for (ProductoporordenDto pxo : productosPXO) {
                ItemProductoPorOrden iPxO = new ItemProductoPorOrden(pxo);

                iPxO.btnRest.setOnMouseClicked(MouseEvent -> {
                    iPxO.ActionMinus();
                    if (iPxO.cantidad == 0) {
//                        grpListPxO.getChildren().remove(iPxO);
                        ProductoporordenService service = new ProductoporordenService();
                        service.eliminarProductopororden(iPxO.getProductoporordenDto().getIdProductoPorOrden());
                        productosPXO.remove(pxo);

                        if (productosPXO.isEmpty()) {
                            OrdenService ordenService = new OrdenService();
                            Respuesta resp = ordenService.eliminarOrden(ordenDto.getIdOrden());
                            if (resp.getEstado()) {
                                nuevaOrden();
                            } else {
                                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar orden", getStage(), " Error al vaciar la orden.");
                            }
                        }
                        refresListPxO();
                    }
                });

                iPxO.btnSum.setOnMouseClicked(MouseEvent -> {
                    iPxO.ActionSum();
                });

                grpListPxO.add(iPxO, 0, row);
                GridPane.setMargin(iPxO, new Insets(10));
                row++;
            }
        }
    }

    public void cargarProdsxO() {
        try {
            if (ordenDto.getIdOrden() != null) {
                ProductoporordenService service = new ProductoporordenService();
                Respuesta resp = service.getProductosPorOrdenByOrden(ordenDto.getIdOrden());
                if (resp.getEstado()) {
                    productosPXO = (List<ProductoporordenDto>) resp.getResultado("ProductosporordenFiltered");
//                refresListPxO();
//                productosPXO = ordenDto.getProductosporordenDto();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar orden", getStage(), " Error al cargar la lista de productos ordenados.");
                }
            }else{
            productosPXO.clear();}
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar orden", getStage(), e.getMessage());
        }
    }

    public void setClickGrupos(JFXButton lbl, HorizontalGrid hGrid) {
        lbl.setOnAction(MouseEvent -> {
            hGrid.toogleVisible();
        });
    }
    Comparator<ProductoporordenDto> comparProductoXOrden = new Comparator<ProductoporordenDto>() {
        public int compare(ProductoporordenDto p1, ProductoporordenDto p2) {
            return p1.getIdProductoPorOrden().compareTo(p2.getIdProductoPorOrden());
        }
    };
    Comparator<ProductoDto> comparProductoGrupoDtoId = new Comparator<ProductoDto>() {
        public int compare(ProductoDto p1, ProductoDto p2) {
            return p1.getGrupoDto().getNombreGrupo().compareTo(p2.getGrupoDto().getNombreGrupo());
        }
    };
    Comparator<ProductoDto> ProductoAlfabetico = new Comparator<ProductoDto>() {
        public int compare(ProductoDto p1, ProductoDto p2) {
            return p1.nombreCorto.get().compareTo(p2.nombreCorto.get());
        }
    };

    @FXML
    private void onActionKeyPressedId(KeyEvent event) {
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && (((JFXTextField) node).getText() == null || ((JFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }

    }

    @FXML
    void onActionBtnFacturarOrden(ActionEvent event) {

        if (ordenDto.getIdOrden() != null) {
            if (new Mensaje().showConfirmation("Cargar orden", getStage(), "Seguro que deaseas facturar esta orden?")) {
                AppContext.getInstance().set("facturarOrden", ordenDto);
                FlowController.getInstance().goView("FacturaView");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Cargar orden", getStage(), "Agrega productos para crear una orden");
        }

    }

//    @FXML
//    private void onActionBtnOrdenar(ActionEvent event) {
//
//        try {
//            String invalidos = validarRequeridos();
//            if (!invalidos.isBlank()) {
//                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar orden", getStage(), invalidos);
//            } else {
//                ProductoporordenService service = new ProductoporordenService();
//
//                pxo.setIdOrdenDto(ordenDto);
//                pxo.setIdProductoDto(productoDtoActual);
//
//                Respuesta respuesta = service.guardarProductopororden(pxo);
//
//                if (!respuesta.getEstado()) {
//                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar orden", getStage(), respuesta.getMensaje());
//                } else {
//                    Unbind();
//                    pxo = (ProductoporordenDto) respuesta.getResultado("Parametro");
//                    bind();
//                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar par", getStage(), "Producto cargado existosamente");
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(ParametrosController.class.getName()).log(Level.SEVERE, "Error guardando el parametro.", ex);
//            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar par", getStage(), "Ocurrio un error guardando el par: " + ex.getMessage());
//        }
//        System.out.println(pxo.toString());
//        System.out.println(ordenDto.toString());
//
//    }
    void BusquedaAvanzada() {
        if (productos != null || !productos.isEmpty()) {
//            List<ProductoDto> productos2 = new ArrayList<>();
//            productos2 = obtenerProductos();
//            if (productos.equals(productos2)) {
//                productos = productos2;
//            }
            gridPanePrincipal.getChildren().clear();
            if (productos != null) {
                int col = 0;
                int row = 1;

                for (ProductoDto pd : productos) {
                    ItemProductCarrito ip = new ItemProductCarrito(pd);

                    ip.btnAgregar.setOnMouseClicked(MouseEvent -> {
                        cargarProducto(ip.getProductoDto());
                    });
                    if (col == 3) {
                        col = 0;
                        row++;
                    }
                    gridPanePrincipal.add(ip, col++, row);
                    GridPane.setMargin(ip, new Insets(10));
                }

            }
        }
    }

    @FXML
    private void btnBusqAvan(ActionEvent event) {
        if (!isAvansada) {
            hboxAZ.setVisible(true);
            BusquedaAvanzada();
//            btnBA.setText("btn.fstSearch");
            isAvansada = true;
        } else {
            hboxAZ.setVisible(false);
//            btnBA.setText("btn.busqAvan");
            cargarGrids(productos);
            isAvansada = false;
        }

    }

    Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public void nuevaOrden() {
        ordenDto = new OrdenDto();
        Date Date = convertToDateViaInstant(java.time.LocalDateTime.now());
        ordenDto.setFechaCreacion(Date);
        ordenDto.setEsEstado(1L);
        ElementodeseccionDto elementoDto = (ElementodeseccionDto) AppContext.getInstance().get("elementoToOrden");
        ordenDto.setIdElementodeseccionDto(elementoDto);
        EmpleadoDto empleadoOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        ordenDto.setIdEmpleadoDto(empleadoOnline);
        AppContext.getInstance().set("OrdenActual", ordenDto);
    }

}
