/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ParametroDto;
import cr.ac.una.restuna.service.ParametroService;
import cr.ac.una.restuna.util.Formato;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.awt.image.BufferedImage;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/** FXML Controller class
 * @author Kendall
 */
public class ParametrosController  extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXTextField txtValNum;
    @FXML
    private JFXTextField txtValText;  
    @FXML
    private JFXTextArea TxtDescrip;
    @FXML
    private ImageView imvImagen;
    @FXML
    private JFXButton btnEditar;
    
    ParametroDto nuevo;
    List<Node> requeridos = new ArrayList<>();
   
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        nuevo = new ParametroDto();
        txtValNum.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        txtId.setTextFormatter(Formato.getInstance().integerFormat());
        txtValText.setTextFormatter(Formato.getInstance().letrasFormat(35));
        txtNombre.setTextFormatter(Formato.getInstance().maxLengthFormat(35));
        indicarRequeridos();
        nuevoParametro();  
    }   
    @Override
    public void initialize() {
       
    }
    
    @FXML
    private void onActionBtnEliminar(ActionEvent event) {    
          try{
            if(nuevo.getIdParametro() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar parametro" , getStage() , "No existe el parametro que desea eliminar.");
            }
            else{
                if(new Mensaje().showConfirmation("Eliminar parametro" , getStage() , "Está seguro que desea eliminar "+ nuevo.getNombre()  +" permanentemente de la lista de parametros?.")){
                    ParametroService service = new ParametroService();
                    Respuesta respuesta = service.eliminarParametro(nuevo.getIdParametro());
                    if(!respuesta.getEstado()){
                        new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar parametro" , getStage() , respuesta.getMensaje());
                    }
                    else{
                        new Mensaje().showModal(Alert.AlertType.INFORMATION , "Eliminar parametro" , getStage() , "Parametro eliminado correctamente.");
                        nuevoParametro();
                    }
                }
            }
        }
        catch(Exception ex)
        {
            Logger.getLogger(ParametrosController.class.getName()).log(Level.SEVERE , "Error eliminando el parametro." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Eliminar parametro" , getStage() , "Ocurrio un error eliminando el parametro.");
        }      
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {nuevoParametro();}

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {GuardarParametro();}

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
         Respuesta respuesta = null;
        if(!txtId.getText().isBlank()){
            System.out.println(Long.valueOf(txtId.getText()));
            ParametroService service = new ParametroService();
            respuesta = service.getParametro(Long.valueOf(txtId.getText()));
        }
        else if(!txtNombre.getText().isBlank()){
            System.out.println(txtNombre.getText());
            ParametroService service = new ParametroService();
            respuesta = service.getParametro(txtNombre.getText());
        }
        if(respuesta.getEstado() && respuesta!=null){
            unbind();
            nuevo = (ParametroDto) respuesta.getResultado("Parametro");
            System.out.println(nuevo.toString());
            bind(false);
            validarRequeridos();
        }
        else{
            new Mensaje().showModal(Alert.AlertType.ERROR , "Cargar parametro" , getStage() ,respuesta.getMensaje());
        }
    }
    
    Image image;
    
    private byte[] FileTobyte(File f) {
        try {
            BufferedImage bufferimage;
            bufferimage = ImageIO.read(f);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferimage, "png", output);
            byte[] data = output.toByteArray();
            System.out.println(Arrays.toString(data));
            return data;
        } catch (IOException ex) {
            Logger.getLogger(MantenimientoProductosViewController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 
    
    @FXML
    private void onActionBtnEditar (ActionEvent event) {
    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File imgFile = fileChooser.showOpenDialog(this.getStage());
        if (imgFile != null) {image = new Image(imgFile.toURI().toString());

        nuevo.setImagen(FileTobyte(imgFile));

        imvImagen.setImage(image);

        }
    
    }
    
    public void indicarRequeridos(){
     requeridos.clear();
     requeridos.addAll(Arrays.asList(txtNombre));
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
    private void bind(Boolean esNuevo){ 
        if(!esNuevo) txtId.textProperty().bind(nuevo.idParametro);
        txtValText.textProperty().bindBidirectional(nuevo.valorTexto);
        txtNombre.textProperty().bindBidirectional(nuevo.nombre);
        txtValNum.textProperty().bindBidirectional(nuevo.valorNumerico);
        txtValText.textProperty().bindBidirectional(nuevo.valorTexto);
        TxtDescrip.textProperty().bindBidirectional(nuevo.descripcion); 
        if(nuevo.getImagen() != null){ imvImagen.setImage(new Image(new ByteArrayInputStream(nuevo.getImagen()))); System.out.println("HOLA");}
      //  else imvImagen.setImage(new Image("/resources/imageEmpty.png"));
    }
    
    private void unbind(){    
        txtId.textProperty().unbind();
        txtValText.textProperty().unbindBidirectional(nuevo.valorTexto);
        txtNombre.textProperty().unbindBidirectional(nuevo.nombre); 
        txtValNum.textProperty().unbindBidirectional(nuevo.valorNumerico);
        txtValText.textProperty().unbindBidirectional(nuevo.valorTexto);
        TxtDescrip.textProperty().unbindBidirectional(nuevo.descripcion);
       
    }
   
    private void nuevoParametro(){
     unbind();
     nuevo = new ParametroDto();
     bind(true);
     txtId.clear();
     txtId.requestFocus();
    }
    
    void GuardarParametro(){
        try{
          String invalidos = validarRequeridos();
          if(!invalidos.isBlank()){
              new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar par" , getStage() , invalidos);
          }else{
              ParametroService service = new ParametroService();
            
              Respuesta respuesta = service.guardarParametro(nuevo);
              if(!respuesta.getEstado()){
                  new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar par" , getStage() , respuesta.getMensaje());
              }
              else{
                  unbind();
                  nuevo = (ParametroDto) respuesta.getResultado("Parametro");
                  bind(false);
                  new Mensaje().showModal(Alert.AlertType.INFORMATION , "Guardar par" , getStage() , "Parametro guardado correctamente.");
              }
          }
        }
        catch(Exception ex ){
            Logger.getLogger(ParametrosController.class.getName()).log(Level.SEVERE , "Error guardando el par." , ex);
            new Mensaje().showModal(Alert.AlertType.ERROR , "Guardar par" , getStage() , "Ocurrio un error guardando el par: "+ex.getMessage());
        }
               
    }    
    
}
