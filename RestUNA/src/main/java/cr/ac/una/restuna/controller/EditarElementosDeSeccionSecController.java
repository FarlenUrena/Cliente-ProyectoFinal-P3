package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.SeccionDto;
import cr.ac.una.restuna.service.ElementoService;
import cr.ac.una.restuna.service.SeccionService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.Formato;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class EditarElementosDeSeccionSecController extends Controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private ImageView ivImagenElemento;

    @FXML
    private Button btnGuardar;

    ElementodeseccionDto elemento;
    SeccionDto seccionDto;
    List<Node> requeridos = new ArrayList<>();
    Image image;
    Long tipo = 1L;

    @Override
    public void initialize() {
        seccionDto = new SeccionDto();
        seccionDto = (SeccionDto) AppContext.getInstance().get("SeccionActual");
        elemento = (ElementodeseccionDto) AppContext.getInstance().get("elementoGenerico");
        elemento.setIdElemento(0L);
        elemento.setNombre("");
//        tipo= elemento.getTipo();
//        
//        
//        image = new Image(ivImagenElemento.getImage().getUrl());
//        AppContext.getInstance().set("imagenElementoSeccion", image);
//        ivImagenElemento.setImage((Image) AppContext.getInstance().get("imagenElementoSeccion"));
//        File f = new File(getClass().getResource("/cr/ac/una/restuna/resources/imageEmpty.png").getFile());
//        elemento.setImagenElemento(FileTobyte(f));
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
//        elemento = new ElementodeseccionDto();
//        cargarTipos();
//        nuevoElemento();
        indicarRequeridos();
        //TODO:
        //      LIMPIAR CAMPOS
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombre));
    }

//    private void nuevoElemento() {
//        unbindElemento();
////        elemento = new ElementodeseccionDto();
////        elemento = (ElementodeseccionDto)AppContext.getInstance().get("elementoGenerico");
//        bindElemento(true);
//        ivImagenElemento.setImage((Image) AppContext.getInstance().get("imageEmpty"));
//        File f = new File(getClass().getResource("/cr/ac/una/restuna/resources/imageEmpty.png").getFile());
//        elemento.setImagenElemento(FileTobyte(f));
//
//    }
//    private void unbindElemento() {
//        ivImagenElemento.setImage(image);
//        txtNombre.textProperty().unbindBidirectional(elemento.nombre);
//        if (elemento.getImagenElemento() != null) {
////            Image image2 = new Image(new ByteArrayInputStream(elemento.getFoto()));
//            ivImagenElemento.setImage(null);
//        }
//
//    }
//    private void bindElemento(boolean nuevo) {
//        if (!nuevo) {
//            //TODO:
//            //      BINDEAR CHECKBOX
////            obtenerImpuesto();//REVISAR METODO DA ERROR
//        }
//        txtNombre.textProperty().bindBidirectional(elemento.nombre);
//
//        if (elemento.getImagenElemento() != null) {
//
//            Image image2 = new Image(new ByteArrayInputStream(elemento.getImagenElemento()));
//            ivImagenElemento.setImage(image2);
//        }
//
//    }
    private byte[] FileTobyte(File f) {
        try {
            BufferedImage bufferimage;
            bufferimage = ImageIO.read(f);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferimage, "png", output);
            byte[] data = output.toByteArray();
            return data;
        } catch (IOException ex) {
            Logger.getLogger(EditarElementosDeSeccionSecController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

//    @FXML
//    void onActionBtnCambiarImagen(ActionEvent event) {
//
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Seleccionar imagen");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All Images", "*.*"),
//                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
//                new FileChooser.ExtensionFilter("PNG", "*.png")
//        );
//
//        File imgFile = fileChooser.showOpenDialog(this.getStage());
//        if (imgFile != null) {
//            image = new Image(imgFile.toURI().toString());
//            elemento.setImagenElemento(FileTobyte(imgFile));
//            ivImagenElemento.setImage(image);
//        }
//    }
    @FXML
    void onActionBtnCancelar(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar elemento", getStage(), invalidos.toString());
            } else {
                ElementoService service = new ElementoService();
//             TODO: 
//                      SETEAR SECCION ACTUAL TRAIDA DESDE APPCONTEXT(LINEA 83 INITIALIZE)
//                elemento.setIdSeccion(seccionDto);

                elemento.setPosicionX(0D);//VALOR POR DEFECTO PARA INDICAR QUE EL ELEMENTO PERTENESE A LA BARRA LATERAL
                elemento.setPosicionY(0D);//VALOR POR DEFECTO PARA INDICAR QUE EL ELEMENTO PERTENESE A LA BARRA LATERAL
                elemento.setEsOcupada(1L);
                elemento.setNombre(txtNombre.getText());
//                elemento.setImpuestoPorServicio(0D);
//                elemento.setTipo(tipo);
//                elemento.setIdSeccionDto(seccionDto);
//                elemento.setImagenElemento(FileTobyte(image));
                Respuesta respuesta = service.guardarElemento(elemento);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar elemento", getStage(), respuesta.getMensaje());
                } else {
//                    unbindElemento();
                    elemento = (ElementodeseccionDto) respuesta.getResultado("Elemento");
//                    bindElemento(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar elemento", getStage(), "Elemento actualizado correctamente.");
                    this.getStage().close();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EditarElementosDeSeccionSecController.class.getName()).log(Level.SEVERE, "Error guardando el elemento.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar elemento", getStage(), "Ocurrio un error guardando el elemento: " + ex.getMessage());
        }

    }

//    @FXML
//    void onActionBtnEliminar(ActionEvent event) {
//        try {
//            if (elemento.getIdElemento() == null) {
//                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar elemento", getStage(), "Debe cargar el elemento que desea eliminar.");
//            } else {
//                if (new Mensaje().showConfirmation("Eliminar elemento", getStage(), "Está seguro que desea eliminar a " + elemento.getNombre() + " permanentemente de la lista de elementos?.")) {
//                    ElementoService service = new ElementoService();
//                    Respuesta respuesta = service.eliminarElemento(elemento.getIdElemento());
//                    if (!respuesta.getEstado()) {
//                        new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar elemento", getStage(), respuesta.getMensaje());
//                    } else {
//                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar elemento", getStage(), "Elemento eliminado correctamente.");
//                        nuevoElemento();
//                        this.getStage().close();
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(EditarElementosDeSeccionSecController.class.getName()).log(Level.SEVERE, "Error eliminando el elemento.", ex);
//            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar elemento", getStage(), "Ocurrio un error eliminando el elemento.");
//        }
//
//    }
//    @FXML
//    private void onActioncbxImpuesto(ActionEvent event) {
////        obtenerImpuesto();
//    }
    //    private void obtenerImpuesto() {//DA ERROR GRAFICO
//
//        if (elemento.impuestoPorServicio.equals(Long.valueOf(0))) {
//            cbxImpuesto.setSelected(false);
//        } else {
//            cbxImpuesto.setSelected(true);
////            consultar tabla de paramentros para obtener el impuesto
////            elemento.setImpuestoPorServicio(parametros.getImpuesto());
//            txtMontoImpuesto.setText(elemento.getImpuestoPorServicio().toString());
//        }
//    }
//    private void cargarTipos() {
//        ObservableList<String> items = FXCollections.observableArrayList();
//
//        items.addAll("Mesa", "Barra");
//        cmbxTipo.setItems(items);
//    }
//    private void cargarElemento(Long id) {
//        ElementoService service = new ElementoService();
//        Respuesta respuesta = service.getElemento(id);
//
//        if (respuesta.getEstado()) {
//            unbindElemento();
//            elemento = (ElementodeseccionDto) respuesta.getResultado("Elemento");
//            bindElemento(false);
//            validarRequeridos();
//        } else {
//            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar elemento", getStage(), respuesta.getMensaje());
//        }
//    }
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

}
