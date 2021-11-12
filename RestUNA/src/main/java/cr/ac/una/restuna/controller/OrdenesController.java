/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.OrdenDto;
import cr.ac.una.restuna.model. ProductoporordenDto;
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
import cr.ac.una.restuna.service.ProductoporordenService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

/**FXML Controller class
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
    OrdenDto ordenDto;
    ProductoporordenDto pxo;
    int cantidad = 1;
    List<Node> requeridos = new ArrayList<>();

    //-----------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() { 
         cargarProductos();
        requeridos.clear();
        
        requeridos.add(txtINombre);
        requeridos.add(txtPrecio);
        requeridos.add(txtCant);
        
        pxo = new ProductoporordenDto();
        pxo.setModificado(Boolean.FALSE);
        ordenDto = (OrdenDto) AppContext.getInstance().get("OrdenActual");
        if(ordenDto == null) new Mensaje().showModal(Alert.AlertType.ERROR , "Cargar orden" , getStage() , " Error obteniendo la orden.");
        else {
        lblNombreSeccion.setText(ordenDto.getIdElementodeseccionDto().getIdSeccionDto().getNombre());
        lblMesa.setText(ordenDto.getIdElementodeseccionDto().getNombre());
//       inicializarGrid(productos);
        cargarGrids(productos);
        }
        
       //txtCantidad.setTextFormatter(Formato.getInstance().integerFormat());

    }

    private void MultPrec() {
        double nprecio = productoDtoActual.getPrecio() * cantidad;
        pxo.setSubtotal(nprecio);
        pxo.setCantidad(Long.valueOf(cantidad));
    }

    private void bind() {
        txtINombre.textProperty().bindBidirectional(productoDtoActual.nombre);
        txtCant.textProperty().bindBidirectional(pxo.cantidad);
        txtPrecio.textProperty().bindBidirectional(pxo.idProductoDto.precio);
        lblSubTotal.textProperty().bindBidirectional(pxo.subtotal);
        MultPrec();
    }

    private void Unbind() {       
        txtINombre.textProperty().unbind();
        txtPrecio.textProperty().unbind();
        txtCant.textProperty().unbind();
         lblSubTotal.textProperty().unbind();
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
            
//            pxo = new ProductoporordenDto();
            for(ProductoporordenDto px :ordenDto.getProductosporordenDto()){
                if(px.getIdProductoDto().getIdProducto().equals(productoDtoActual.getIdProducto())){
                    pxo = px;
                }
                
            }
            if(pxo.getCantidad()==null)pxo.setCantidad(0L);
            
            if(pxo.getSubtotal()==null)pxo.setSubtotal(0D);
            
            pxo.setIdProductoDto(productoDtoActual);
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
            return p1.getGrupoDto().getNombreGrupo().compareTo(p2.getGrupoDto().getNombreGrupo());
        }
    };

    @FXML
    private void onActionKeyPressedId(KeyEvent event) {
    }

    @FXML
    private void OnActionMinus(ActionEvent event) {
        if (cantidad > 0) {
            cantidad--;
            txtCant.setText(String.valueOf(cantidad));
            //txtCant.setText(Integer.toString(cantidad));
            MultPrec();
        }
    }

    @FXML
    private void OnActionSum(ActionEvent event) {
        cantidad++;
        txtCant.setText(String.valueOf(cantidad));
        MultPrec();
    }

    
        public String validarRequeridos(){
    Boolean validos = true;
    String invalidos = "";
    for(Node node : requeridos)
    {
        if(node instanceof JFXTextField && (((JFXTextField) node).getText() == null || ((JFXTextField) node).getText().isBlank()))
        {
            if(validos)
            {
                invalidos += ((JFXTextField) node).getPromptText();
            }
            else
            {
                invalidos += "," + ((JFXTextField) node).getPromptText();
            }
            validos = false;
        }    
    }
        if(validos)
        {
            return "";
        }
        else
        { return "Campos requeridos o con problemas de formato [" + invalidos + "].";}
    
    }
    
    @FXML
    private void onActionBtnOrdenar(ActionEvent event) {

       try{
          String invalidos = validarRequeridos();
          if(!invalidos.isBlank()){
              new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar orden" , getStage() , invalidos);
          }else{
               ProductoporordenService service = new ProductoporordenService();
            
               pxo.setIdOrdenDto(ordenDto);
               pxo.setIdProductoDto(productoDtoActual);
               
              Respuesta respuesta = service.guardarProductopororden(pxo);
                 
              if(!respuesta.getEstado()){
                  new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar orden" , getStage() , respuesta.getMensaje());
              }
              else{
                  Unbind();
                  pxo = ( ProductoporordenDto) respuesta.getResultado("Parametro");
                  bind();
                  new Mensaje().showModal(Alert.AlertType.INFORMATION , "Guardar par" , getStage() , "Producto cargado existosamente");
              }
          }
        }
        catch(Exception ex ){
            Logger.getLogger(ParametrosController.class.getName()).log(Level.SEVERE , "Error guardando el parametro." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar par" , getStage() , "Ocurrio un error guardando el par: "+ex.getMessage());
        }
          System.out.println(pxo.toString());
          System.out.println(ordenDto.toString());
        
    }

}
