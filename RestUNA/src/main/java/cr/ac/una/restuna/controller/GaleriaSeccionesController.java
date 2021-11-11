/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import cr.ac.una.restuna.model.EmpleadoDto;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cr.ac.una.restuna.model.GrupoDto;
import cr.ac.una.restuna.model.SeccionDto;
import cr.ac.una.restuna.pojos.ItemSecciones;
import cr.ac.una.restuna.service.GrupoService;
import cr.ac.una.restuna.service.SeccionService;
import cr.ac.una.restuna.util.AppContext;
import cr.ac.una.restuna.util.FlowController;
import cr.ac.una.restuna.util.Respuesta;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Kenda
 */
public class GaleriaSeccionesController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private VBox root;

    @FXML
    private ScrollPane scrlPanePrincipal;

    @FXML
    private GridPane gridPanePrincipal;

    List<SeccionDto> seccionesDto;
    SeccionDto seccionDto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        seccionesDto = new ArrayList<>();
        seccionDto = new SeccionDto();
        cargarSecciones();

        // TODO
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    void cargarSecciones() {
        gridPanePrincipal.getChildren().clear();
        seccionesDto = obtenerSecciones();

        int col = 1;
        int row = 1;

        EmpleadoDto empleadoOnline = (EmpleadoDto) AppContext.getInstance().get("Usuario");
        ItemSecciones itemSeccion = new ItemSecciones();

        if (empleadoOnline.getRol() == 1) {
            col = 1;
            row = 1;
            gridPanePrincipal.add(itemSeccion, 0, 1);
            itemSeccion.setOnMouseClicked(MouseEvent -> {
                //crear salon
                crearSeccionTemporal();
                FlowController.getInstance().goView("SeccionesEditorView");
//                        cargarProducto(ip.getIdProduct());
            });
            GridPane.setMargin(itemSeccion, new Insets(10));
        } else {
            col = 0;
            row = 1;
        }

        if (!seccionesDto.isEmpty() || seccionesDto != null) {

            for (SeccionDto dto : seccionesDto) {
                ItemSecciones itemSec = new ItemSecciones(dto, false);
                this.setEvento(itemSec);

                if (col == 3) {
                    col = 0;
                    row++;
                }
                gridPanePrincipal.add(itemSec, col++, row);
                GridPane.setMargin(itemSec, new Insets(10));
            }
        }

    }

    private void crearSeccionTemporal() {
        this.seccionDto.setNombre("No Asignado");

        File f = new File(getClass().getResource("/cr/ac/una/restuna/resources/imageEmpty.png").getFile());
        this.seccionDto.setFotoDistribucion(FileTobyte(f));

        SeccionService service = new SeccionService();
        Respuesta respuesta = service.guardarSeccion(this.seccionDto);

        this.seccionDto = (SeccionDto) respuesta.getResultado("Seccion");
        AppContext.getInstance().set("SeccionActual", this.seccionDto);
    }

    public void setEvento(ItemSecciones itemSeccion) {
        itemSeccion.setOnMouseClicked(MouseEvent -> {
            itemSeccion.setToAppContext();
            FlowController.getInstance().goView("SeccionesEditorView");
        });

    }

    private List<SeccionDto> obtenerSecciones() {
        SeccionService service = new SeccionService();
        Respuesta respuesta = service.getSecciones();
        return (List<SeccionDto>) respuesta.getResultado("SeccionesList");
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
}
