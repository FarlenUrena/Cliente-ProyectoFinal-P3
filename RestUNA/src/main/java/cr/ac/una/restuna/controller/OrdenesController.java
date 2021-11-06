/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ProductoDto;
import cr.ac.una.restuna.pojos.ItemProduct;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import cr.ac.una.restuna.service.ProductoService;
import cr.ac.una.restuna.util.Formato;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Respuesta;
import java.util.Iterator;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Kendall
 */
public class OrdenesController  extends Controller implements Initializable{

    @FXML
    private VBox root;
    @FXML
    private ScrollPane scrlPanePrincipal;
    @FXML
    private GridPane gridPanePrincipal;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtINombre;
    @FXML
    private JFXTextField txtCant;
    @FXML
    private JFXTextField txtPrecio;
    @FXML
    private JFXButton btnOrdenar;
    List<ProductoDto> productos;
    ProductoDto ordena;
    int cantidad = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @Override
    public void initialize() {
       cargarProductos();
       inicializarGrid();
       // txtCantidad.setTextFormatter(Formato.getInstance().integerFormat());
       
    }
    private void MultPrec(){
       double nprecio;
        nprecio = Double.parseDouble(ordena.precio.get());
       if(cantidad>0) nprecio = nprecio * cantidad;
       txtPrecio.setText(Double.toString(nprecio));
    }
    private void bind(){
       txtId.textProperty().bind(ordena.idProducto);
       txtINombre.textProperty().bind(ordena.nombre);
       MultPrec();
    }
    private void Unbind(){
       txtId.textProperty().unbind();
       txtINombre.textProperty().unbind();
       txtPrecio.textProperty().unbind();
    }
    private void cargarProductos() {
        ProductoService service = new ProductoService();
        Respuesta respuesta = service.getProductos();

        if (respuesta.getEstado()) {
          //  unbindProducto();
            productos =  (List<ProductoDto>) respuesta.getResultado("ProductosList");
           // bindProducto(false);
            //validarRequeridos();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), respuesta.getMensaje());
        }
    }
    
    private void cargarProducto(Long id) {
    ProductoService service = new ProductoService();
    Respuesta respuesta = service.getProducto(id);

    if (respuesta.getEstado()) {
        ordena = (ProductoDto) respuesta.getResultado("Producto");
        System.out.println(ordena.toString());
        Unbind();
        bind();
    } else {
        new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), respuesta.getMensaje());
    }
    } 
    
    private void inicializarGrid(){
      if (productos != null && !productos.isEmpty()) {
          gridPanePrincipal.getChildren().clear();
          if (productos != null) {
              int col = 0;
              int row = 1;

              for (ProductoDto pd : productos) {
                  ItemProduct ip = new ItemProduct(pd);

                  ip.setOnMouseClicked(MouseEvent -> {
                      cargarProducto(ip.getIdProduct());
                     
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
    private void onActionKeyPressedId(KeyEvent event) {
    }
    @FXML
    private void OnActionMinus(ActionEvent event) {
         cantidad--;
        txtCant.setText(Integer.toString(cantidad));
         MultPrec();
    }

    @FXML
    private void OnActionSum(ActionEvent event) {
        cantidad++;
        txtCant.setText(Integer.toString(cantidad));
         MultPrec();
    }

    @FXML
    private void onActionBtn(ActionEvent event) {
    }
    
    
}
