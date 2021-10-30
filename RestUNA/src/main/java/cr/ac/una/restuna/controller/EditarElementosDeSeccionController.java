package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ElementoDto;
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

public class EditarElementosDeSeccionController extends Controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXComboBox<String> cmbxTipo;

    @FXML
    private ImageView ivImagenElemento;

    @FXML
    private Button btnCambiarImagen;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private JFXCheckBox cbxImpuesto;
    @FXML
    private JFXTextField txtMontoImpuesto;
    @FXML
    private JFXComboBox<String> cmbxSeccion;

    ElementoDto elemento;
    List<Node> requeridos = new ArrayList<>();
    private List<SeccionDto> secciones = new ArrayList<>();
    SeccionDto seccionDto;
    Image image;

    @Override
    public void initialize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        image = new Image(ivImagenElemento.getImage().getUrl());
        AppContext.getInstance().set("imageEmpty", image);
        txtID.setTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
        txtMontoImpuesto.setTextFormatter(Formato.getInstance().twoDecimalFormat());
        elemento = new ElementoDto();

        nuevoElemento();
        indicarRequeridos();
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombre, txtMontoImpuesto, cbxImpuesto));
    }

    private void nuevoElemento() {
        unbindElemento();
        elemento = new ElementoDto();
        bindElemento(true);
        txtID.clear();
        txtID.requestFocus();
        ivImagenElemento.setImage((Image) AppContext.getInstance().get("imageEmpty"));
        File f = new File(getClass().getResource("/cr/ac/una/restuna/resources/imageEmpty.png").getFile());
        elemento.setImagenElemento(FileTobyte(f));
    }

    private void unbindElemento() {
        ivImagenElemento.setImage(image);
        txtID.textProperty().unbind();
        txtNombre.textProperty().unbindBidirectional(elemento.nombre);
        txtMontoImpuesto.textProperty().unbindBidirectional(elemento.impuestoPorServicio);
        cbxImpuesto.setSelected(false);
        cmbxTipo.setValue(null);
        if (elemento.getImagenElemento() != null) {
//            Image image2 = new Image(new ByteArrayInputStream(elemento.getFoto()));
            ivImagenElemento.setImage(null);
        }

    }

    private void obtenerImpuesto() {

        if (elemento.impuestoPorServicio.equals(Long.valueOf(0))) {
            cbxImpuesto.setSelected(false);
        } else {
            cbxImpuesto.setSelected(true);
//            consultar tabla de paramentros para obtener el impuesto
//            elemento.setImpuestoPorServicio(parametros.getImpuesto());
            txtMontoImpuesto.setText(elemento.getImpuestoPorServicio().toString());
        }
    }

    private void bindElemento(boolean nuevo) {
        if (!nuevo) {
            txtID.textProperty().bind(elemento.idElemento);
            seccionDto = elemento.getIdSeccion();
            cmbxSeccion.setValue(seccionDto.getNombre());
            obtenerImpuesto();
        }
        txtNombre.textProperty().bindBidirectional(elemento.nombre);

        if (elemento.getImagenElemento() != null) {

            Image image2 = new Image(new ByteArrayInputStream(elemento.getImagenElemento()));
            ivImagenElemento.setImage(image2);
        }

    }

    private byte[] FileTobyte(File f) {
        try {
            BufferedImage bufferimage;
            bufferimage = ImageIO.read(f);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferimage, "png", output);
            byte[] data = output.toByteArray();
            return data;
        } catch (IOException ex) {
            Logger.getLogger(EditarElementosDeSeccionController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void cargarElemento(Long id) {
        ElementoService service = new ElementoService();
        Respuesta respuesta = service.getElemento(id);

        if (respuesta.getEstado()) {
            unbindElemento();
            elemento = (ElementoDto) respuesta.getResultado("Elemento");
            bindElemento(false);
            validarRequeridos();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar elemento", getStage(), respuesta.getMensaje());
        }
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

    @FXML
    void onActionBtnCambiarImagen(ActionEvent event) {
        //TODO: CAMBIAR IMAGEN
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File imgFile = fileChooser.showOpenDialog(this.getStage());
        if (imgFile != null) {
            image = new Image(imgFile.toURI().toString());

            elemento.setImagenElemento(FileTobyte(imgFile));

            ivImagenElemento.setImage(image);

        }
    }

    @FXML
    void onActionBtnCancelar(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    void onActionBtnGuardar(ActionEvent event) {
        //TODO: GUARDAR

        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar elemento", getStage(), invalidos.toString());
            } else {
                ElementoService service = new ElementoService();
//                GrupoDto grupo = (GrupoDto) grupos.stream().filter(g->(g.getNombreGrupo() == cmbbxGrupo.getValue()));
//                System.out.println(grupo.getIdGrupo());
//                
//                elemento.setGrupo(grupo.getIdGrupo());
                obtenerImpuesto();

//                secciones.stream().filter(g -> (g.getNombreGrupo().equals(cmbxTipo.getValue()))).forEachOrdered(g -> {
//                    seccionDto = g;
//                });
//                elemento.setIdSeccion(seccionDto);
                Respuesta respuesta = service.guardarElemento(elemento);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar elemento", getStage(), respuesta.getMensaje());
                } else {
                    unbindElemento();
                    elemento = (ElementoDto) respuesta.getResultado("Elemento");
                    bindElemento(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar elemento", getStage(), "Elemento actualizado correctamente.");
                    this.getStage().close();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EditarElementosDeSeccionController.class.getName()).log(Level.SEVERE, "Error guardando el elemento.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar elemento", getStage(), "Ocurrio un error guardando el elemento: " + ex.getMessage());
        }

    }

    @FXML
    void onActionBtnEliminar(ActionEvent event) {
        //TODO: ELIMINAR

        try {
            if (elemento.getIdElemento() == null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar elemento", getStage(), "Debe cargar el elemento que desea eliminar.");
            } else {
                if (new Mensaje().showConfirmation("Eliminar elemento", getStage(), "Está seguro que desea eliminar a " + elemento.getNombre() + " permanentemente de la lista de elementos?.")) {
                    ElementoService service = new ElementoService();
                    Respuesta respuesta = service.eliminarElemento(elemento.getIdElemento());
                    if (!respuesta.getEstado()) {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar elemento", getStage(), respuesta.getMensaje());
                    } else {
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar elemento", getStage(), "Elemento eliminado correctamente.");
                        nuevoElemento();
                        this.getStage().close();
//                        inicializarGrid();
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EditarElementosDeSeccionController.class.getName()).log(Level.SEVERE, "Error eliminando el elemento.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar elemento", getStage(), "Ocurrio un error eliminando el elemento.");
        }

    }

    @FXML
    private void onActioncbxImpuesto(ActionEvent event) {
        obtenerImpuesto();
    }

    private List<SeccionDto> obtenerSecciones() {
        SeccionService service = new SeccionService();
        Respuesta respuesta = service.getSecciones();
        return (List<SeccionDto>) respuesta.getResultado("SeccionesList");
    }

    private void cargarSecciones() {
        ObservableList<String> items = FXCollections.observableArrayList();

        secciones = obtenerSecciones();

        if (!secciones.isEmpty() || secciones != null) {
            secciones.stream().forEach(g -> {

                items.add(g.getNombre());

            });
        } else {
//            System.out.println("TAN MAMANDO LOS GRUPOS");
        }

//        items.addAll("Entradas", "Platos fuertes", "Bebidas",
//                "Postres", "Ensaladas", "Comidas rápidas");
        cmbxSeccion.setItems(items);
    }

}
