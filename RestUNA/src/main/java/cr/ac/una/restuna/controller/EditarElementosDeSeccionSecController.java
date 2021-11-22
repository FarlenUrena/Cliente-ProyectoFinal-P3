package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.SeccionDto;
import cr.ac.una.restuna.service.ElementoService;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
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
    public void initialize(URL location, ResourceBundle resources) {
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(12));
        indicarRequeridos();
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombre));
    }

    @Override
    public void initialize() {
        elemento = new ElementodeseccionDto();
        elemento = (ElementodeseccionDto) AppContext.getInstance().get("elementoGenerico");
        elemento.setIdElemento(0L);
        elemento.setNombre("");
        ivImagenElemento.setImage(new Image(new ByteArrayInputStream(elemento.getImagenElemento())));
    }

    @FXML
    void onActionBtnCancelar(ActionEvent event) {
        AppContext.getInstance().set("elementoGenerico", new ElementodeseccionDto());
        this.getStage().close();
    }

    @FXML
    void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            
            if (invalidos.isEmpty()) {
                
                elemento.setPosicionX(350D);
                elemento.setPosicionY(250D);
                elemento.setEsOcupada(1L);
                ElementoService service = new ElementoService();
                elemento.setNombre(txtNombre.getText());
                Respuesta respuesta = service.guardarElemento(elemento);
                if (respuesta.getEstado()) {
                    AppContext.getInstance().set("elementoGenerico", new ElementodeseccionDto());
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar elemento", getStage(), "Elemento actualizado correctamente.");
                    this.getStage().close();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar elemento", getStage(), respuesta.getMensaje());
                }

            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar elemento", getStage(), invalidos.toString());
            }
        } catch (Exception ex) {
            Logger.getLogger(EditarElementosDeSeccionSecController.class.getName()).log(Level.SEVERE, "Error guardando el elemento.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar elemento", getStage(), "Ocurrio un error guardando el elemento: " + ex.getMessage());
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
            Logger.getLogger(EditarElementosDeSeccionSecController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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

}
