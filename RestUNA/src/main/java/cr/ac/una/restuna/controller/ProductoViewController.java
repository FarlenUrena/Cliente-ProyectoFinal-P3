/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.App;
import cr.ac.una.restuna.model.ProductoDto;
import cr.ac.una.restuna.pojos.ItemProduct;
import cr.ac.una.restuna.service.ProductoService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.Formato;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class ProductoViewController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXTextField txtNombreCorto;
    @FXML
    private JFXTextField txtPrecio;
    @FXML
    private JFXComboBox<String> cmbbxGrupo;
    @FXML
    private JFXCheckBox cbxAccesoRapido;
    @FXML
    private JFXTextField txtCantidadVendida;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private ScrollPane scrlPanePrincipal;
    @FXML
    private GridPane gridPanePrincipal;
    @FXML
    private ImageView imvImagen;
    private List<ProductoDto> productos = new ArrayList<>();
    ProductoDto producto;
    List<Node> requeridos = new ArrayList<>();

    Image image;
    @FXML
    private JFXButton btnEditar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarGrid();        
        image = new Image(imvImagen.getImage().getUrl());
        AppContext.getInstance().set("imageEmpty", image);
        cargarRoles();
        txtId.setTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
        txtNombreCorto.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtPrecio.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtCantidadVendida.setTextFormatter(Formato.getInstance().integerFormat());
        producto = new ProductoDto();
        nuevoProducto();
        indicarRequeridos();
//        imvImagen.setImage(null);
    }

    @Override
    public void initialize() {
//        inicializarGrid();        
//        image = new Image(imvImagen.getImage().getUrl());
//        AppContext.getInstance().set("imageEmpty", image);
//        cargarRoles();
//        txtId.setTextFormatter(Formato.getInstance().integerFormat());
//        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
//        txtNombreCorto.setTextFormatter(Formato.getInstance().letrasFormat(15));
//        txtPrecio.setTextFormatter(Formato.getInstance().twoDecimalFormat());
//        txtCantidadVendida.setTextFormatter(Formato.getInstance().integerFormat());
//        unbindProducto();
//        producto = new ProductoDto();
//          if(new Mensaje().showConfirmation("Cargar producto", getStage(), "Desea Limpiar los datos?")){
//              nuevoProducto();
//          }

//        
//        indicarRequeridos();
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombre, txtNombreCorto, txtPrecio, txtCantidadVendida, cbxAccesoRapido, cmbbxGrupo));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && (((JFXTextField) node).getText() == null || ((JFXTextField) node).getText().isEmpty())) {
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXPasswordField && (((JFXPasswordField) node).getText() == null || ((JFXPasswordField) node).getText().isEmpty())) {
                if (validos) {
                    invalidos += ((JFXPasswordField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXPasswordField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXDatePicker && ((JFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((JFXDatePicker) node).getAccessibleText();
                } else {
                    invalidos += "," + ((JFXDatePicker) node).getAccessibleText();
                }
                validos = false;
            } else if (node instanceof JFXComboBox && ((JFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((JFXComboBox) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXComboBox) node).getPromptText();
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

    private void bindProducto(Boolean nuevo) {
        if (!nuevo) {
            txtId.textProperty().bind(producto.idProducto);
            cmbbxGrupo.setValue(intToRol());
            obtenerAccesoRapido();
        }
        txtNombre.textProperty().bindBidirectional(producto.nombre);
        txtNombreCorto.textProperty().bindBidirectional(producto.nombreCorto);
        txtPrecio.textProperty().bindBidirectional(producto.precio);
        txtCantidadVendida.textProperty().bindBidirectional(producto.ventasTotales);
        if (producto.getImagen() != null) {
            
            Image image2 = new Image(new ByteArrayInputStream(producto.getImagen()));
            imvImagen.setImage(image2);
        }

    }

    private void unbindProducto() {
        imvImagen.setImage(image);
        txtId.textProperty().unbind();
        txtNombre.textProperty().unbindBidirectional(producto.nombre);
        txtNombreCorto.textProperty().unbindBidirectional(producto.nombreCorto);
        txtPrecio.textProperty().unbindBidirectional(producto.precio);
        txtCantidadVendida.textProperty().unbindBidirectional(producto.ventasTotales);
        cmbbxGrupo.setValue(null);
        cbxAccesoRapido.setSelected(false);
        if (producto.getImagen() != null) {
//            Image image2 = new Image(new ByteArrayInputStream(producto.getFoto()));
            imvImagen.setImage(null);
        }
    }

    private void obtenerAccesoRapido() {
        if (producto.getEsAccesoRapido().equals(Long.valueOf(1))) {
            cbxAccesoRapido.setSelected(true);
        } else {
            cbxAccesoRapido.setSelected(false);
        }
    }

    private void bindAccesoRapido() {
        if (cbxAccesoRapido.isSelected()) {
            producto.setEsAccesoRapido(Long.valueOf(1));
        } else {
            producto.setEsAccesoRapido(Long.valueOf(0));
        }
//         
//        if(producto.getEsAccesoRapido().equals(1)){
//            cbxAccesoRapido.setSelected(true);
//        } else{
//            cbxAccesoRapido.setSelected(false);
//        }

    }

    private void nuevoProducto() {
        unbindProducto();
        producto = new ProductoDto();
        bindProducto(true);
        txtId.clear();
        txtId.requestFocus();
        imvImagen.setImage((Image) AppContext.getInstance().get("imageEmpty"));
        File f = new File(getClass().getResource("/cr/ac/una/restuna/resources/imageEmpty.png").getFile());
        producto.setImagen(FileTobyte(f));
    }

    private void cargarProducto(Long id) {
        ProductoService service = new ProductoService();
        Respuesta respuesta = service.getProducto(id);

        if (respuesta.getEstado()) {
            unbindProducto();
            producto = (ProductoDto) respuesta.getResultado("Producto");
            bindProducto(false);
            validarRequeridos();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar producto", getStage(), respuesta.getMensaje());
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        if (txtId.getText() != null && !txtId.getText().isBlank()) {
            cargarProducto(Long.valueOf(txtId.getText()));
        }
    }

    @FXML
    private void onActionAccesoRapido(ActionEvent event) {
        bindAccesoRapido();
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (producto.getIdProducto() == null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar producto", getStage(), "Debe cargar el producto que desea eliminar.");
            } else {
                if (new Mensaje().showConfirmation("Eliminar producto", getStage(), "Está seguro que desea eliminar a " + producto.getNombre() + " permanentemente de la lista de productos?.")) {
                    ProductoService service = new ProductoService();
                    Respuesta respuesta = service.eliminarProducto(producto.getIdProducto());
                    if (!respuesta.getEstado()) {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar producto", getStage(), respuesta.getMensaje());
                    } else {
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar producto", getStage(), "Producto eliminado correctamente.");
                        nuevoProducto();
                        inicializarGrid();
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductoViewController.class.getName()).log(Level.SEVERE, "Error eliminando el producto.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar producto", getStage(), "Ocurrio un error eliminando el producto.");
        }
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar producto", getStage(), "¿Esta seguro que desea limpiar el registro?")) {
            nuevoProducto();
            btnGuardar.setDisable(false);
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar producto", getStage(), invalidos.toString());
            } else {
                ProductoService service = new ProductoService();
                producto.setGrupo(grupoToInt());
                bindAccesoRapido();
                Respuesta respuesta = service.guardarProducto(producto);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar producto", getStage(), respuesta.getMensaje());
                } else {
                    unbindProducto();
                    producto = (ProductoDto) respuesta.getResultado("Producto");
                    bindProducto(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar producto", getStage(), "Producto actualizado correctamente.");
                    inicializarGrid();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductoViewController.class.getName()).log(Level.SEVERE, "Error guardando el producto.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar producto", getStage(), "Ocurrio un error guardando el producto: " + ex.getMessage());
        }
    }

    @FXML
    private void onActionKeyPressedId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtId.getText().isEmpty()) {
            cargarProducto(Long.valueOf(txtId.getText()));
        }
    }

    @FXML
    private void onActionKeyPressedNombre(KeyEvent event) {
    }

    @FXML
    private void onActionKeyPressedNombreCorto(KeyEvent event) {
    }

    private void cargarRoles() {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Entradas", "Platos fuertes", "Bebidas",
                "Postres", "Ensaladas", "Comidas rápidas");

        cmbbxGrupo.setItems(items);
    }

    private String intToRol() {
        String eleccion = "";
        int e = Math.toIntExact(producto.getGrupo());
        switch (e) {
            case 1:
                eleccion = "Entradas";
                break;
            case 2:
                eleccion = "Platos fuertes";
                break;
            case 3:
                eleccion = "Bebidas";
                break;
            case 4:
                eleccion = "Postres";
                break;
            case 5:
                eleccion = "Ensaladas";
                break;
            case 6:
                eleccion = "Comidas rápidas";
                break;
            default:
                break;
        }
        return eleccion;
    }

    private Long grupoToInt() {
        int eleccion = 0;
        if (cmbbxGrupo.getValue() != null) {

            switch (cmbbxGrupo.getValue()) {
                case "Entradas":
                    eleccion = 1;
                    break;
                case "Platos fuertes":
                    eleccion = 2;
                    break;
                case "Bebidas":
                    eleccion = 3;
                    break;
                case "Postres":
                    eleccion = 4;
                    break;
                case "Ensaladas":
                    eleccion = 5;
                    break;
                case "Comidas rápidas":
                    eleccion = 6;
                    break;
                default:
                    break;
            }
        } else {
            return Long.valueOf(0);
        }
        return Long.valueOf(eleccion);
    }

    @FXML
    private void onActionBtnEditar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        
        File imgFile = fileChooser.showOpenDialog(this.getStage());
        if(imgFile != null){
        image = new Image(imgFile.toURI().toString());
        
                producto.setImagen(FileTobyte(imgFile));
            
        
        imvImagen.setImage(image);
        
    }
    }
    
    private byte[] FileTobyte(File f){
        try {
            BufferedImage bufferimage;
            bufferimage = ImageIO.read(f);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferimage , "png" , output);
            byte[] data = output.toByteArray();
            return data;
        } catch (IOException ex) {
            Logger.getLogger(ProductoViewController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    
    
    private void inicializarGrid(){
//        if(productos == null || productos.isEmpty()){
            productos = obtenerProductos();
//        }else{
//            List<ProductoDto> productos2 = new ArrayList<>();
//            productos2 = obtenerProductos();
//            if(productos.equals(productos2)){
//                productos = productos2;
//            }
//        }
    gridPanePrincipal.getChildren().clear();
    if(productos != null){
        int col=0;
        int row=1;

            for(ProductoDto pd : productos){
                ItemProduct ip = new ItemProduct(pd);

                ip.setOnMouseClicked(MouseEvent ->{
                cargarProducto(ip.getIdProduct());
                });
                if(col==3){
                    col=0;
                    row++;
                }
                gridPanePrincipal.add(ip,col++,row);
                GridPane.setMargin(ip,new Insets(10));
            }
            

        }
    }

    private List<ProductoDto> obtenerProductos() {
        ProductoService service = new ProductoService();
        Respuesta respuesta = service.getProductos();
        return (List<ProductoDto>) respuesta.getResultado("ProductosList");
    }
    
    
    
    
    
    
}
