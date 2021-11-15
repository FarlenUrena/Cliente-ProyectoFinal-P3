/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.SeccionDto;
import cr.ac.una.restuna.pojos.ItemElementoDeSeccion;
import cr.ac.una.restuna.pojos.ItemElementoDeSeccionSecundario;
import cr.ac.una.restuna.service.ElementoService;
import cr.ac.una.restuna.service.SeccionService;
import cr.ac.una.restuna.util.AppContext;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Kenda
 */
public class MantenimientoSeccionesController extends Controller implements Initializable {
    
    @FXML
    private VBox root;
    @FXML
    private HBox hbContainer;
    @FXML
    private JFXButton btnAgregar;
    
    @FXML
    private ScrollPane scrlPanePrincipal1;
    
    @FXML
    private GridPane gridPanePrincipal;
    
    @FXML
    private JFXButton btnEliminar;
    
    @FXML
    private JFXTextField txtNombre;
    
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblDefault;
    @FXML
    private AnchorPane seccion;
    @FXML
    private VBox vbFacturar;
    @FXML
    private VBox vbEditorElementos;
    
    List<ElementodeseccionDto> elementosDto;
    List<ItemElementoDeSeccionSecundario> elementosInterfazSeccionSecundario;
    SeccionDto seccionDto;
    EmpleadoDto empleadoOnline;
    @FXML
    private VBox vbSalon;
    @FXML
    private JFXCheckBox chkBoxHabilitarEdicion;
    @FXML
    private ImageView ivCaja;
    
    @FXML
    void onActionBtnEliminar(ActionEvent event) {
        
    }
    
    @FXML
    void onActionBtnGuardar(ActionEvent event) throws IOException {
        
        try {
            ElementoService service = new ElementoService();
            List<ElementodeseccionDto> temp = new ArrayList<>();
            
            for (ItemElementoDeSeccionSecundario it : elementosInterfazSeccionSecundario) {
                temp.add(it.getElementoGenerico());
            }
            
            Respuesta respuesta = service.guardarElementos(temp);
            if (!respuesta.getEstado()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar elementos", getStage(), respuesta.getMensaje());
                
            } else {
                elementosDto = (List<ElementodeseccionDto>) respuesta.getResultado("ElementosActualizados");
                seccionDto.setElementosdeseccionDto(elementosDto);
                
                seccionDto.setNombre(txtNombre.getText());
                seccionDto.setFotoDistribucion(screenshot());
                
                SeccionService serviceSecc = new SeccionService();
                Respuesta respuestaSecc = serviceSecc.guardarSeccion(seccionDto);
                if (!respuestaSecc.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar seccion", getStage(), respuesta.getMensaje());
                    
                } else {
                    
                    seccionDto = (SeccionDto) respuestaSecc.getResultado("Seccion");
                    cargarElementos(obtenerElementos());
                    validarDraggableAdmin();
                }
            }
        } catch (IOException e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar elemento", getStage(), e.getMessage());
        }
    }
    
    @FXML
    void onAction_btnAgregar(ActionEvent event) {
        AppContext.getInstance().set("elementoGenerico", new ElementodeseccionDto());
        FlowController.getInstance().goViewInWindowModalUncap("EditarElementosSeccionView", this.getStage(), false);
        cargarElementos(obtenerElementos());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        empleadoOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        
        if (empleadoOnline.getRol() == 2) {
            hbContainer.getChildren().remove(vbEditorElementos);
            btnGuardar.setVisible(false);
            //Edicion mesas
            chkBoxHabilitarEdicion.setDisable(true);
            chkBoxHabilitarEdicion.setVisible(false);
            
        } else if (empleadoOnline.getRol() == 3) {
            hbContainer.getChildren().remove(vbEditorElementos);
            btnGuardar.setVisible(false);
            vbFacturar.setVisible(false);

            //Edicion mesas
            chkBoxHabilitarEdicion.setDisable(true);
            chkBoxHabilitarEdicion.setVisible(false);
        }
    }
    
    @Override
    public void initialize() {
        chkBoxHabilitarEdicion.setSelected(false);
        elementosDto = new ArrayList<>();
        elementosInterfazSeccionSecundario = new ArrayList<>();
        
        seccionDto = (SeccionDto) AppContext.getInstance().get("SeccionActual");
        
        txtNombre.setText(seccionDto.getNombre());
        
        elementosDto = seccionDto.getElementosdeseccionDto();
        cargarElementos(elementosDto);
    }
    
    private void cargarElementos(List<ElementodeseccionDto> elementosDto) {
        seccion.getChildren().clear();
        gridPanePrincipal.getChildren().clear();
        elementosInterfazSeccionSecundario.clear();
        
        Collections.sort(elementosDto, comparElementosPorId);
        
        int row = 1;
        
        if (elementosDto != null) {
            for (ElementodeseccionDto elementoDto : elementosDto) {
                
                if (elementoDto.getIdSeccionDto().getIdSeccion().equals(seccionDto.getIdSeccion())) {
                    if (elementoDto.getPosicionX() == 30000D && elementoDto.getPosicionY() == 30000D) {
                        
                        ItemElementoDeSeccion itemSeccion = new ItemElementoDeSeccion(elementoDto);
                        itemSeccion.btnAgregar.setOnMouseClicked(MouseEvent -> {
                            AppContext.getInstance().set("elementoGenerico", itemSeccion.getElementoGenerico());
                            FlowController.getInstance().goViewInWindowModalUncap("EditarElementosSeccionSecView", this.getStage(), false);
                            cargarElementos(obtenerElementos());
                        });
                        
                        itemSeccion.btnEditar.setOnMouseClicked(MouseEvent -> {
                            AppContext.getInstance().set("elementoGenerico", itemSeccion.getElementoGenerico());
                            FlowController.getInstance().goViewInWindowModalUncap("EditarElementosSeccionView", this.getStage(), false);
                            cargarElementos(obtenerElementos());
                        });
                        
                        gridPanePrincipal.add(itemSeccion, 0, row);
                        row++;
                        GridPane.setMargin(itemSeccion, new Insets(10));
                        
                    } else {
                        
                        ItemElementoDeSeccionSecundario itemSeccionDragg = new ItemElementoDeSeccionSecundario(elementoDto);
                        itemSeccionDragg.setStage(this.getStage());
//El elemento que se cargue en el lienzo, debe contener propiedades según el tipo de usuario que hace uso de la aplicación
                        //En caso de que sea un admin
                        if (empleadoOnline.getRol() == 1) {
                            itemSeccionDragg.MakeDraggableCajero(ivCaja);
                            itemSeccionDragg.MakePressedSalonero();
                        } else { //En caso de que sea un cajero 
                            if (empleadoOnline.getRol() == 2) {
                                itemSeccionDragg.MakeDraggableCajero(ivCaja);
                                itemSeccionDragg.MakePressedSalonero();
                            } else { //En caso de que sea un salonero
                                if (empleadoOnline.getRol() == 3) {
                                    itemSeccionDragg.MakePressedSalonero();
                                }
                            }
                        }
                        
                        setOpenModal(itemSeccionDragg);
                        seccion.getChildren().add(itemSeccionDragg);
                        elementosInterfazSeccionSecundario.add(itemSeccionDragg);
                    }
                }
            }
        }
    }
    
    public void setOpenModal(ItemElementoDeSeccionSecundario itemSeccionDragg) {
        itemSeccionDragg.btnOrdenes.setOnMouseClicked(MouseEvent -> {
            AppContext.getInstance().set("elementoToOrden", itemSeccionDragg.getElementoGenerico());
            if (empleadoOnline.getIdEmpleado().equals(3L)) {
                FlowController.getInstance().goViewInWindowModalUncap("OrdenesListView", this.getStage(), Boolean.FALSE);
            }else {
                FlowController.getInstance().goViewInWindowModalUncap("AllOrdenesListView", this.getStage(), false);
            }
        });
    }
    
    private void validarDraggableAdmin() {
        if (chkBoxHabilitarEdicion.isSelected()) {
            for (ItemElementoDeSeccionSecundario it : elementosInterfazSeccionSecundario) {
                it.MakeDraggableAdmin(seccion);
            }
        } else {
            for (ItemElementoDeSeccionSecundario it : elementosInterfazSeccionSecundario) {
                it.MakeDraggableCajero(ivCaja);
                it.MakePressedSalonero();
            }
        }
    }
    
    private List<ElementodeseccionDto> obtenerElementos() {
        ElementoService service = new ElementoService();
        Respuesta respuesta = service.getElementos();
        return (List<ElementodeseccionDto>) respuesta.getResultado("ElementosList");
    }
    Comparator<ElementodeseccionDto> comparElementosPorId = new Comparator<ElementodeseccionDto>() {
        public int compare(ElementodeseccionDto e1, ElementodeseccionDto e2) {
            return e1.getIdElemento().compareTo(e2.getIdElemento());
        }
    };
    
    @FXML
    private void onActionButtonHabilitarEdicion(ActionEvent event) {
        validarDraggableAdmin();
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
    
    public byte[] screenshot() throws IOException {
        WritableImage snapshot = seccion.snapshot(null, null);
        File file = new File("snapshot.png");
        ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        byte[] data = FileTobyte(file);
        return data;
    }
    
}
