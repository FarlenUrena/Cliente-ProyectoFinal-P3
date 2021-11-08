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
import cr.ac.una.restuna.model.GrupoDto;
import cr.ac.una.restuna.model.ProductoDto;
import cr.ac.una.restuna.pojos.HorizontalGrid;
import cr.ac.una.restuna.pojos.ItemProduct;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        cargarGrids(todosProductos);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
//    //FUNCIONAL NO TOCAR
//    private void cargarGrids(List<ProductoDto> productos) {
//
//        Collections.sort(productos, comparProductoGrupoDtoId);
//
//        Long idGrurpoIdentifier = 0L;
//        boolean esGrupoNuevo = true;
//        if (productos != null || !productos.isEmpty()) {
////            List<ProductoDto> productos2 = new ArrayList<>();
////            productos2 = obtenerProductos();
////            if (productos.equals(productos2)) {
////                productos = productos2;
////            }
//            gridPanePrincipal.getChildren().clear();
//            gridPaneAccesosRapidos.getChildren().clear();
//
//            if (productos != null) {
//                int col = 0;
//                int row = 1;
//                int colA = 0;
//                int rowA = 1;
////Si queda tiempo
////                GridPane hG = new GridPane();
////                ScrollPane jsp = new ScrollPane();
////                ItemProductCarrito ip2;
//                for (ProductoDto pd : productos) {
//
//                    if (idGrurpoIdentifier.equals(pd.grupoDto.getIdGrupoDto())) {
//                        esGrupoNuevo = false;
//                    } else {
//                        idGrurpoIdentifier = pd.grupoDto.getIdGrupoDto();
//                        esGrupoNuevo = true;
//                    }
//                    if (esGrupoNuevo) {
////                        hG = new GridPane();
////                        jsp = new ScrollPane();
////                        hG.setStyle("");
//
//                        Label lbl = new Label();
//                        lbl.setText(pd.grupoDto.getNombreGrupo());
//                        lbl.setWrapText(true);
//                        lbl.setTextAlignment(TextAlignment.JUSTIFY);
//                        //Setting the maximum width of the label
//                        lbl.setMaxWidth(150);
//                        lbl.setStyle("-fx-font-size: 20px;"
//                                + "-fx-text-fill:  #E0EEF6;"
//                        );
//                        col = 0;
//                        row++;
//                        gridPanePrincipal.add(lbl, col, row++);
//                        GridPane.setMargin(lbl, new Insets(10));
//                    }
//                    if (col == 3) {
//                        col = 0;
//                        row++;
//                    }
//                    if (colA == 3) {
//                        colA = 0;
//                        rowA++;
//                    }
//
////                    ip.setOnMouseClicked(MouseEvent -> {
////                        //agregar al carrito//
//////                        cargarProducto(ip.getIdProduct());
////                    });=
//                    if (pd.getEsAccesoRapido().equals(1L)) {
//                        if (esGrupoNuevo) {
//                            Label lbl2 = new Label();
//
//                            lbl2.setText(pd.grupoDto.getNombreGrupo());
//                            colA = 0;
//                            rowA++;
//
//                            lbl2.setWrapText(true);
//                            lbl2.setTextAlignment(TextAlignment.JUSTIFY);
//                            //Setting the maximum width of the label
//                            lbl2.setMaxWidth(150);
//                            lbl2.setStyle("-fx-font-size: 20px;"
//                                    + "-fx-text-fill:  #E0EEF6;"
//                            );
//                            gridPaneAccesosRapidos.add(lbl2, colA, rowA++);
//                            GridPane.setMargin(lbl2, new Insets(10));
//                        }
//                        ItemProductCarrito ip2 = new ItemProductCarrito(pd);
////                        setEvent(ip2.btnAgregar);
//                        gridPaneAccesosRapidos.add(ip2, colA++, rowA);
//
//                        GridPane.setMargin(ip2, new Insets(10));
//                    }
//                    ItemProductCarrito ip = new ItemProductCarrito(pd);
////                    setEvent(ip.btnAgregar);
//                    gridPanePrincipal.add(ip, col++, row);
//                    GridPane.setMargin(ip, new Insets(10));
//                }
//
//            }
//        }
//    }
   
//      private void cargarGrids(List<ProductoDto> productos) {
//
//        Collections.sort(productos, comparProductoGrupoDtoId);
//
//        Long idGrurpoIdentifier = 0L;
//        boolean esGrupoNuevo = true;
//        if (productos != null || !productos.isEmpty()) {
////            List<ProductoDto> productos2 = new ArrayList<>();
////            productos2 = obtenerProductos();
////            if (productos.equals(productos2)) {
////                productos = productos2;
////            }
//            gridPanePrincipal.getChildren().clear();
//            gridPaneAccesosRapidos.getChildren().clear();
//            int idPcont = 0;
//            int idAcont = 0;
//
//            if (productos != null) {
//                int col = 0;
//                int row = 1;
//                int col_H = 1;
//                int colA = 0;
//                int rowA = 1;
//                int colA_H = 1;
////Si queda tiempo
//
//                GridPane hG_P = new GridPane();
//
//                ScrollPane jsp_P = new ScrollPane();
//                GridPane hG_AR = new GridPane();
//                ScrollPane jsp_AR = new ScrollPane();
//
//                for (ProductoDto pd : productos) {
//
//                    if (idGrurpoIdentifier.equals(pd.grupoDto.getIdGrupoDto())) {
//                        esGrupoNuevo = false;
//                    } else {
//                        idGrurpoIdentifier = pd.grupoDto.getIdGrupoDto();
//                        esGrupoNuevo = true;
//                    }
//
//                    if (esGrupoNuevo) {
//                        //TODOS LOS PRODUCTOS-----------------------------
//                        Label lbl = new Label();
//                        lbl.setText(pd.grupoDto.getNombreGrupo());
//                        lbl.setWrapText(true);
//                        lbl.setTextAlignment(TextAlignment.JUSTIFY);
//                        //Setting the maximum width of the label
//                        lbl.setMaxWidth(150);
//                        lbl.setStyle("-fx-font-size: 20px;"
//                                + "-fx-text-fill:  #E0EEF6;"
//                        );
//                        col = 0;
//                        row++;
//                        gridPanePrincipal.add(lbl, col, row++);
//                        GridPane.setMargin(lbl, new Insets(10));
//                        //HORIZONTAL
//                        hG_P = new GridPane();
//                        jsp_P = new ScrollPane();
//                        jsp_P.setStyle("-fx-pref-width: 400px;"
//                                + "-fx-max-width: 270px;"
//                                + "-fx-max-height:400px;"
//                                + "-fx-pref-height: 270px;"
//                        );
//                        
//                        jsp_P.setId(String.valueOf(idPcont));
//                        idPcont++;
//                        gridPanePrincipal.add(jsp_P, 0, rowA++);
//                        col_H = 0;
//
//                    }
//                    ItemProductCarrito ip = new ItemProductCarrito(pd);
//                    hG_P.add(ip, col_H++, 0);
//                    GridPane.setMargin(ip, new Insets(10));
////                    if (col == 3) {
////                        col = 0;
////                        row++;
////                    }
////                    if (colA == 3) {
////                        colA = 0;
////                        rowA++;
////                    }
//
////                    ip.setOnMouseClicked(MouseEvent -> {
////                        //agregar al carrito//
//////                        cargarProducto(ip.getIdProduct());
////                    });=
//                    if (pd.getEsAccesoRapido().equals(1L)) {
//                        if (esGrupoNuevo) {
//                            //ACCESOS RAPIDOS-----------------------------
//                            Label lbl2 = new Label();
//                            lbl2.setText(pd.grupoDto.getNombreGrupo());
//                            lbl2.setWrapText(true);
//                            lbl2.setTextAlignment(TextAlignment.JUSTIFY);
//                            //Setting the maximum width of the label
//                            lbl2.setMaxWidth(150);
//                            lbl2.setStyle("-fx-font-size: 20px;"
//                                    + "-fx-text-fill:  #E0EEF6;"
//                            );
//                            colA = 0;
//                            rowA++;
//                            gridPaneAccesosRapidos.add(lbl2, 0, rowA++);
//                            GridPane.setMargin(lbl2, new Insets(10));
//                            //HORIZONTAL
//                            hG_AR = new GridPane();
//                            jsp_P = new ScrollPane();
//                            jsp_AR.setStyle("-fx-pref-width: 400px;"
//                                    + "-fx-max-width: 270px;"
//                                    + "-fx-max-height:400px;"
//                                    + "-fx-pref-height: 270px;"
//                            );
//                            jsp_P.setId(String.valueOf(idAcont));
//                            idAcont++;
//                            gridPaneAccesosRapidos.add(jsp_AR, 0, rowA++);
//                            colA_H = 0;
//                        }
//
//                        ItemProductCarrito ip2 = new ItemProductCarrito(pd);
////                        setEvent(ip2.btnAgregar);
//                        hG_AR.add(ip2, colA_H++, 0);
//
//                        GridPane.setMargin(ip2, new Insets(10));
//                    }
//                    
//                }
//
//            }
//        }
//    }
    
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
                        //Setting the maximum width of the label
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
                            //Setting the maximum width of the label
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
                        }

                        ItemProductCarrito ip2 = new ItemProductCarrito(pd);
                        hGridRapidos.addToGrid(ip2, colA++, 0);
                        
                    }
                    

            }
        }
    }

//    public void setEvent(Button b) {
//        b.setOnMouseClicked(MouseEvent -> {
//            //agregar al carrito//
//            
//            
//        });
//    }
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
