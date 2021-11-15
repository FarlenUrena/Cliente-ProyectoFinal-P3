/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.restuna.controller.Controller;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;

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
        if(dpINI.getValue() == null || dpFin == null){
             new Mensaje().showModal(Alert.AlertType.ERROR , "Datos incompletos" , getStage() , "Debe añadir fecha inicial y fecha final para el reporte de facturas entre fechas.");
        }else{
            try {
                reporte.setTipo(1);
                CrearReporte();
                abrirarchivo(urlFinal);
                new Mensaje().showModal(Alert.AlertType.INFORMATION , "Reportes" , getStage() , "Reporte generado correctamente en la ruta:"+urlFinal);
            } catch (ParseException ex) {
                Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }

    @FXML
    private void btncaja(ActionEvent event) {
         if(dpCierre.getValue() == null || txtId.getText().isBlank()){
            new Mensaje().showModal(Alert.AlertType.ERROR , "Datos incompletos" , getStage() , "Debe añadir fecha inicial, fecha final y el id del empleado, para reporte de caja.");
         }
         else{
             try {
                 reporte.setTipo(2);
                 CrearReporte();
                 abrirarchivo(urlFinal);
                 new Mensaje().showModal(Alert.AlertType.INFORMATION , "Reportes" , getStage() , "Reporte generado correctamente en la ruta:"+urlFinal);
             } catch (ParseException ex) {
                 Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
                 Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
             }
        
         }
    }

    @FXML
    private void btnproduc(ActionEvent event) {
         if(dpINI.getValue() == null || dpFin == null){
            new Mensaje().showModal(Alert.AlertType.ERROR , "Datos incompletos" , getStage() , "Debe añadir fecha inicial y fecha final del reporte, para el reporte de cantidades de productos.");
         }
         else{
             try {
                 reporte.setTipo(3);
                 CrearReporte();
                 new Mensaje().showModal(Alert.AlertType.INFORMATION , "Reportes" , getStage() , "Reporte generado correctamente en la ruta:"+urlFinal);
             
                 abrirarchivo(urlFinal);
             } catch (ParseException ex) {
                 Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
                 Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
             }
        
         }
    }
    
    public void abrirarchivo(String archivo){

     try {

            File objetofile = new File (archivo);
            Desktop.getDesktop().open(objetofile);

     }catch (IOException ex) {

            System.out.println(ex);

     }

}       
    
    private void CrearReporte() throws ParseException, IOException{
 
EmpleadoService s = new EmpleadoService();

        String pal = "RESTAURANTEUNA";
reporte.setNombreEmpresa(pal);
if(dpINI.getValue() != null){

reporte.setDateInicio(convertLocaDateToDate(dpINI.getValue()));
}
if(dpFin.getValue() != null){
reporte.setDateFinal(convertLocaDateToDate(dpFin.getValue()));
}
if(dpCierre.getValue() != null){
reporte.setFechaCierreCaja(convertLocaDateToDate(dpCierre.getValue()));
}
//if(txtId.getValue() != null){
reporte.setIdEmpleado(Long.parseLong(txtId.getText()));
//}
                
                Respuesta respuesta = s.getReporte(reporte);
                if(!respuesta.getEstado()){
                    new Mensaje().showModal(Alert.AlertType.ERROR , "Obteniendo reporte" , getStage() , respuesta.getMensaje());
                }else{
                    ReporteDto report = (ReporteDto) respuesta.getResultado("Reporte");
                    if(crearReportePdf(report.getPrintReport(), "ReporteTemporal")){
                        
                        new Mensaje().showModal(Alert.AlertType.CONFIRMATION , "Crear reporte" , getStage() , "Reporte generado correctamente.");
                    }
                }
    }
    
    private Date convertLocaDateToDate(LocalDate ld) {
        
        return Date.from(ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    
    
    
    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//    Date Date = convertToDateViaInstant(java.time.LocalDateTime.now());
    Date convertToDateViaInstant(LocalDate localDate) {
        if(localDate != null){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return devolverFecha(Date.from(localDate.atStartOfDay(defaultZoneId).toInstant()));
    }else{
    return null;
    }}
    
      public Date devolverFecha(Date fechaEntrada) { 
        Date miFecha=null;
        try {
            SimpleDateFormat formato = format1;
            String fechaString = fechaEntrada.toString(); // Convierte Date a String
             miFecha = formato.parse(fechaString); // convierte String a Date
            
        } catch (ParseException ex) {
            Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    return miFecha;
  }
    String urlFinal = "";
        public boolean crearReportePdf(byte[] bytes, String nombre) throws IOException{
        File home = FileSystemView.getFileSystemView().getHomeDirectory();
       
        desktop = home.getAbsolutePath();
        System.out.println(desktop);
        urlFinal = home+"/"+nombre+".pdf";
        try (OutputStream out = new FileOutputStream(urlFinal)) {
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
