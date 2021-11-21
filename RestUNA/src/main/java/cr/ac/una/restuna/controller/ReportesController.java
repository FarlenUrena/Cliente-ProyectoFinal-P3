/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.model.ReporteDto;
import cr.ac.una.restuna.service.EmpleadoService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import cr.ac.una.restuna.util.Mensaje;
import cr.ac.una.restuna.util.Respuesta;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Kenda
 */
public class ReportesController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private JFXDatePicker dpCierre;
    @FXML
    private JFXDatePicker dpINI;
    @FXML
    private JFXDatePicker dpFin;

    @FXML
    private JFXTextField txtId;
    String desktop;
    ReporteDto reporte = new ReporteDto();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnfacturas(ActionEvent event) {
        if (dpINI.getValue() == null || dpFin == null) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Datos incompletos", getStage(), "Debe añadir la fecha inicial y la fecha final para generar el reporte de facturas entre fechas.");
        } else {
            try {
                reporte.setTipo(1);
                CrearReporte();
                abrirarchivo(tempFile);
            } catch (ParseException | IOException ex) {

                new Mensaje().showModal(Alert.AlertType.ERROR, "Crear reporte", getStage(), "Ocurrió un error al realizar el reporte.");
                Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void btncaja(ActionEvent event) {
        if (dpCierre.getValue() == null || txtId.getText().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Datos incompletos", getStage(), "Debe añadir la fecha de cierre y el id del empleado, para generar el reporte de caja.");
        } else {
            try {
                reporte.setTipo(2);
                CrearReporte();
                abrirarchivo(tempFile);
            } catch (ParseException | IOException ex) {

                new Mensaje().showModal(Alert.AlertType.ERROR, "Crear reporte", getStage(), "Ocurrió un error al realizar el reporte.");
                Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void btnproduc(ActionEvent event) {
        if (dpINI.getValue() == null || dpFin == null) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Datos incompletos", getStage(), "Debe añadir la fecha inicial y la fecha final para generar el reporte de ventas de productos entre fechas.");
        } else {
            try {
                reporte.setTipo(3);
                CrearReporte();
                abrirarchivo(tempFile);
            } catch (ParseException | IOException ex) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Crear reporte", getStage(), "Ocurrió un error al realizar el reporte.");
                Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void abrirarchivo(File file) {

        try {

            Desktop.getDesktop().open(file);

        } catch (IOException ex) {

            System.out.println(ex);

        }

    }

    private void CrearReporte() throws ParseException, IOException {

        EmpleadoService s = new EmpleadoService();

        String pal = "RESTAURANTEUNA";
        reporte.setNombreEmpresa(pal);
        if (dpINI.getValue() != null) {

            reporte.setDateInicio(convertLocaDateToDate(dpINI.getValue()));
        }
        if (dpFin.getValue() != null) {
            reporte.setDateFinal(convertLocaDateToDate(dpFin.getValue()));
        }
        if (dpCierre.getValue() != null) {
            reporte.setFechaCierreCaja(convertLocaDateToDate(dpCierre.getValue()));
        }
        reporte.setIdEmpleado(Long.parseLong(txtId.getText()));

        Respuesta respuesta = s.getReporte(reporte);
        if (!respuesta.getEstado()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Obteniendo reporte", getStage(), respuesta.getMensaje());
        } else {
            ReporteDto report = (ReporteDto) respuesta.getResultado("Reporte");
            if (crearReportePdf(report.getPrintReport(), "ReporteTemporal")) {

                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Crear reporte", getStage(), "Reporte generado correctamente.");
            }
        }
    }

    private Date convertLocaDateToDate(LocalDate ld) {

        return Date.from(ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    File tempFile;

    public boolean crearReportePdf(byte[] bytes, String nombre) throws IOException {
        tempFile = File.createTempFile("tempFile", ".pdf", null);
        tempFile.deleteOnExit();
        try (OutputStream out = new FileOutputStream(tempFile)) {
            out.write(bytes);
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BaseContainerViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BaseContainerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void initialize() {

    }

}
