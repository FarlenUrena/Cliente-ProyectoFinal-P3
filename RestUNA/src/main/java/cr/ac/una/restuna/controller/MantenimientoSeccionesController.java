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
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

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
    private VBox vbEliminar;
    @FXML
    private ImageView ivEliminar;
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
    @FXML
    private VBox vbSalon;
    @FXML
    private JFXCheckBox chkBoxHabilitarEdicion;
    @FXML
    private ImageView ivCaja;

    List<ElementodeseccionDto> elementosDto;
    List<ItemElementoDeSeccionSecundario> elementosInterfazSeccionSecundario;
    SeccionDto seccionDto;
    EmpleadoDto empleadoOnline;
    boolean seVen = false;

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
            txtNombre.setDisable(true);

            //Edicion mesas
            chkBoxHabilitarEdicion.setDisable(true);
            chkBoxHabilitarEdicion.setVisible(false);
        }
    }

    @Override
    public void initialize() {
        seVen = false;
        btnGuardar.setVisible(seVen);
        btnEliminar.setVisible(seVen);
        vbEliminar.setVisible(seVen);

        chkBoxHabilitarEdicion.setSelected(false);
        elementosDto = new ArrayList<>();
        elementosInterfazSeccionSecundario = new ArrayList<>();

        seccionDto = (SeccionDto) AppContext.getInstance().get("SeccionActual");

        txtNombre.setText(seccionDto.getNombre());

        elementosDto = seccionDto.getElementosdeseccionDto();
        cargarElementos(elementosDto);
    }

    @FXML
    void onActionBtnEliminar(ActionEvent event) {

        SeccionService service = new SeccionService();
        Respuesta respuesta = service.eliminarSeccion(seccionDto.getIdSeccion());
        if (!respuesta.getEstado()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "EliminarSeccion", getStage(), respuesta.getMensaje());
        } else {
            FlowController.getInstance().goView("SeccionesGalleryView");
        }

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

    private void cargarElementos(List<ElementodeseccionDto> elementosDto) {
        seccion.getChildren().clear();
        gridPanePrincipal.getChildren().clear();
        elementosInterfazSeccionSecundario.clear();

        Collections.sort(elementosDto, comparElementosPorId);

        int row = 1;

        if (elementosDto != null) {
            for (ElementodeseccionDto elementoDto : elementosDto) {
                if (!elementoDto.getTipo().equals(3L)) {//SI ES LA CAJA(ELEMENTO GENERICO)
                    if (elementoDto.getIdSeccionDto().getIdSeccion().equals(seccionDto.getIdSeccion())) {
                        if (elementoDto.getPosicionX() == 30000D && elementoDto.getPosicionY() == 30000D) {

                            ItemElementoDeSeccion itemSeccion = new ItemElementoDeSeccion(elementoDto);
                            itemSeccion.btnAgregar.setOnMouseClicked(MouseEvent -> {
                                AppContext.getInstance().set("elementoGenerico", itemSeccion.getElementoGenerico());
                                FlowController.getInstance().goViewInWindowModalUncap("EditarElementosSeccionSecView", this.getStage(), false);
                                validarDraggableAdmin();
                                cargarElementos(obtenerElementos());
                            });

                            itemSeccion.btnEditar.setOnMouseClicked(MouseEvent -> {
                                AppContext.getInstance().set("elementoGenerico", itemSeccion.getElementoGenerico());
                                FlowController.getInstance().goViewInWindowModalUncap("EditarElementosSeccionView", this.getStage(), false);
                                validarDraggableAdmin();
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
                                MakeDraggableCajero(itemSeccionDragg);
                                MakePressedSalonero(itemSeccionDragg);
                            } else { //En caso de que sea un cajero 
                                if (empleadoOnline.getRol() == 2) {
                                    MakeDraggableCajero(itemSeccionDragg);
                                    MakePressedSalonero(itemSeccionDragg);
                                } else { //En caso de que sea un salonero
                                    if (empleadoOnline.getRol() == 3) {
                                        MakePressedSalonero(itemSeccionDragg);
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
    }

    private ItemElementoDeSeccionSecundario getElementoSeleccionado(Long id) {
        ItemElementoDeSeccionSecundario e = null;
        for (ItemElementoDeSeccionSecundario elemento : elementosInterfazSeccionSecundario) {
            if (elemento.elementoDto.getIdElemento().equals(id)) {
                e = elemento;
                break;
            }
        }
        return e;
    }

    //DRAGABLE MOVE TEST//
    public void MakePressedSalonero(ItemElementoDeSeccionSecundario itemSeccionDragg) {
        //Todo Generar Orden

        itemSeccionDragg.setOnMouseClicked(onClickedSALONERO);

        itemSeccionDragg.setOnMousePressed(null);
        itemSeccionDragg.setOnMouseDragged(null);

    }

    void openModal(ElementodeseccionDto eDto) {

        AppContext.getInstance().set("elementoDroped", eDto);
        AppContext.getInstance().set("ultimaVentana", "Facturacion2");
        FlowController.getInstance().goViewInWindowModalUncap("AllOrdenesListView", this.getStage(), false);
    }
    EventHandler<MouseEvent> onClickedSALONERO
            = (MouseEvent t) -> {
                System.out.println("Me clickeaste");
//                System.out.println("elementoDto x: " + elementoDto.getPosicionX());
//                System.out.println("elementoDto y: " + elementoDto.getPosicionY());
                System.out.println("----------------------------------------------");
//                System.out.println(" x: " + this.posicionX);
//                System.out.println(" y: " + this.posicionY);
            };

    //CAJERO
    public void MakeDraggableCajero(ItemElementoDeSeccionSecundario itemSeccionDragg) {
        // mover a la caja, inicia el evento en el elemento del lienzo
        itemSeccionDragg.setOnDragDetected(event -> dragDetectedCajero(event,itemSeccionDragg));

        // Se activan los escuchas de la caja, para detectar cuando el elemento en movimiento pasa sobre la caja
        ivCaja.setOnDragOver(event -> dragOver(event, itemSeccionDragg));
        ivCaja.setOnDragDropped(event -> dragDropped(event,itemSeccionDragg));

        // termina el evento
        itemSeccionDragg.setOnDragDone(event -> dragDone(event));

        itemSeccionDragg.setOnMousePressed(null);
        itemSeccionDragg.setOnMouseDragged(null);

    }
    private boolean acceptMove = false;

    private ElementodeseccionDto eDto;
    
    public void dragDetectedCajero(MouseEvent event,ItemElementoDeSeccionSecundario itemSeccionDragg) {
        eDto = itemSeccionDragg.elementoDto;
        Dragboard db;
        db = itemSeccionDragg.getIv().startDragAndDrop(TransferMode.COPY);
        System.out.println("--------------------------------------" + itemSeccionDragg.elementoDto.getIdElemento());

        ClipboardContent content = new ClipboardContent();
        content.putImage(itemSeccionDragg.getIv().getImage());

        content.getImage();
        db.setContent(content);
        event.consume();
    }

    public void dragOver(DragEvent event,ItemElementoDeSeccionSecundario itemSeccionDragg) {
        if (event.getGestureSource() != ivCaja
                && event.getDragboard().hasImage()) {
            
            if(eDto.getIdElemento().equals(itemSeccionDragg.elementoDto.getIdElemento())){
            event.acceptTransferModes(TransferMode.COPY);
            System.out.println("Sobre la caja...");
            }else{
            for(ItemElementoDeSeccionSecundario i : elementosInterfazSeccionSecundario){
                
                if(i.elementoDto.getIdElemento().equals(eDto.getIdElemento())){
                    itemSeccionDragg = i;
                    event.acceptTransferModes(TransferMode.COPY);
                    System.out.println("Sobre la caja...");
                }
            
            }
            }

        }
    }

    public void dragDropped(DragEvent event,ItemElementoDeSeccionSecundario itemSeccionDragg) {

        Dragboard db = event.getDragboard();
        if (db.hasImage() && ivCaja != null) {
            
            if(eDto.getIdElemento().equals(itemSeccionDragg.elementoDto.getIdElemento())){
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++" + itemSeccionDragg.elementoDto.getIdElemento());
//       TODO abrir vista de facturas
//        acceptMove = true;
            event.setDropCompleted(true);
            openModal(eDto);
            event.consume();
            }else{
            for(ItemElementoDeSeccionSecundario i : elementosInterfazSeccionSecundario){
                
                if(i.elementoDto.getIdElemento().equals(eDto.getIdElemento())){
                    itemSeccionDragg = i;
                    break;
                }
            }
             System.out.println("+++++++++++++++++++++++++++++++++++++++++++" + itemSeccionDragg.elementoDto.getIdElemento());
//       TODO abrir vista de facturas
//        acceptMove = true;
            event.setDropCompleted(true);
            openModal(eDto);
            event.consume();
//            FlowController.getInstance().goViewInWindowModalUncap("OrdenesListView", stage, false);
        }

    }
    }

    public void dragDone(DragEvent event) {
        if (acceptMove) {
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++"+this.elementoDto.getIdElemento());

        }

    }

    
        private void MakeDraggableAdminDelete(ItemElementoDeSeccionSecundario it) {
        ivEliminar.setOnDragOver(event -> dragOverEliminar(event, it));
        ivEliminar.setOnDragDropped(event -> dragDroppedEliminar(event,it));
   }
    private void dragOverEliminar(DragEvent event, ItemElementoDeSeccionSecundario it) {
        if (event.getGestureSource() != ivEliminar
                && event.getDragboard().hasImage()) {
            
            if(eDto.getIdElemento().equals(it.elementoDto.getIdElemento())){
            event.acceptTransferModes(TransferMode.COPY);
            System.out.println("Sobre el basurero...");
            }else{
            for(ItemElementoDeSeccionSecundario i : elementosInterfazSeccionSecundario){
                
                if(i.elementoDto.getIdElemento().equals(eDto.getIdElemento())){
                    it = i;
                    event.acceptTransferModes(TransferMode.COPY);
                    System.out.println("Sobre la caja...");
                }
            
            }
            }

        }
    }
//    EventHandler<MouseEvent> onDragDetectedCAJERO
//            = new EventHandler<MouseEvent>() {
//
//        @Override
//        public void handle(MouseEvent t) {
//            
////            System.out.println("-------------------------------------"+this..getIdElemento());
//            Dragboard db;
//            db = iv.startDragAndDrop(TransferMode.MOVE);
//
//            ClipboardContent content = new ClipboardContent();
//            content.putImage(iv.getImage());
//
//            content.getImage();
//            db.setContent(content);
//            t.consume();
//        }
//    };
    //ADMIN
    public void MakeDraggableAdmin(Object o,ItemElementoDeSeccionSecundario itemSeccionDragg) {

        itemSeccionDragg.setOnMousePressed(e -> PressItemToMove(e, o));
        itemSeccionDragg.setOnMouseDragged(e -> MoverSobreLienzo(e, itemSeccionDragg));

        itemSeccionDragg.setOnDragDetected(null);
        itemSeccionDragg.setOnMouseClicked(null);

    }

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    EventHandler<MouseEvent> OnMousePressedEventHandlerADMIN
            = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {

        }
    };

    EventHandler<MouseEvent> OnMouseDraggedEventHandlerADMIN
            = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {

        }
    };

    private void MoverSobreLienzo(MouseEvent t, ItemElementoDeSeccionSecundario itemSeccionDragg) {
        double offsetX = t.getSceneX() - orgSceneX;
        double offsetY = t.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;
        
        
        ((VBox) (t.getSource())).setTranslateX(newTranslateX);
        ((VBox) (t.getSource())).setTranslateY(newTranslateY);
        itemSeccionDragg.setPosicionX(newTranslateX + 350);
        itemSeccionDragg.setPosicionY(newTranslateY + 250);
        itemSeccionDragg.getElementoGenerico().setPosicionX(newTranslateX + 350);
        itemSeccionDragg.getElementoGenerico().setPosicionX(newTranslateY + 250);
    }

    private void PressItemToMove(MouseEvent t, Object o) {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        orgTranslateX = ((VBox) (t.getSource())).getTranslateX();
        orgTranslateY = ((VBox) (t.getSource())).getTranslateY();
    }

    //DRAGABLE MOVE TEST//
    public void setOpenModal(ItemElementoDeSeccionSecundario itemSeccionDragg) {
        itemSeccionDragg.btnOrdenes.setOnMouseClicked(MouseEvent -> {
            AppContext.getInstance().set("elementoToOrden", itemSeccionDragg.getElementoGenerico());
            if (empleadoOnline.getIdEmpleado().equals(3L)) {
                FlowController.getInstance().goViewInWindowModal("LoginView", this.getStage(), false);
                String stat = (String) AppContext.getInstance().get("authorEstatus");
                if (stat.equals("ok")) {
                    FlowController.getInstance().goViewInWindowModalUncap("OrdenesListView", this.getStage(), Boolean.FALSE);
                }
            } else {
                FlowController.getInstance().goViewInWindowModalUncap("OrdenesListView", this.getStage(), false);
            }
        });
    }

    private void validarDraggableAdmin() {
        if (chkBoxHabilitarEdicion.isSelected()) {
            for (ItemElementoDeSeccionSecundario it : elementosInterfazSeccionSecundario) {
                MakeDraggableAdmin(seccion,it);
            }
        } else {
            for (ItemElementoDeSeccionSecundario it : elementosInterfazSeccionSecundario) {
                MakeDraggableCajero(it);
                MakePressedSalonero(it);
                MakeDraggableAdminDelete(it);
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

        if (seVen) {
            seVen = false;
        } else {
            seVen = true;
        }
        btnGuardar.setVisible(seVen);
        btnEliminar.setVisible(seVen);
        vbEliminar.setVisible(seVen);
        vbFacturar.setVisible(!seVen);
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
            Logger.getLogger(EditarElementosDeSeccionController.class
                    .getName()).log(Level.SEVERE, null, ex);
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
 private void eliminarElemento(Long id){
     
     ElementoService service = new ElementoService();
     Respuesta respuesta = service.eliminarElemento(id);
     if(respuesta.getEstado()){
      new Mensaje().showModal(Alert.AlertType.INFORMATION, "EliminarElementoSeccion", getStage(), "Elemento eliminado correctamente.");
        } else {
        elementosDto = seccionDto.getElementosdeseccionDto();
        cargarElementos(elementosDto);
 }
 }
    private void dragDroppedEliminar(DragEvent event, ItemElementoDeSeccionSecundario it) {
        Dragboard db = event.getDragboard();
        if (db.hasImage() && ivCaja != null) {
            
            if(eDto.getIdElemento().equals(it.elementoDto.getIdElemento())){
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++" + it.elementoDto.getIdElemento());
//       TODO abrir vista de facturas
//        acceptMove = true;
            event.setDropCompleted(true);
            openModal(eDto);
            event.consume();
            }else{
            for(ItemElementoDeSeccionSecundario i : elementosInterfazSeccionSecundario){
                
                if(i.elementoDto.getIdElemento().equals(eDto.getIdElemento())){
                    it = i;
                    break;
                }
            }
             System.out.println("+++++++++++++++++++++++++++++++++++++++++++" + it.elementoDto.getIdElemento());
//       TODO abrir vista de facturas
//        acceptMove = true;
            event.setDropCompleted(true);
            eliminarElemento(eDto.getIdElemento());
            event.consume();
//            FlowController.getInstance().goViewInWindowModalUncap("OrdenesListView", stage, false);
        }
        }
    }

}
