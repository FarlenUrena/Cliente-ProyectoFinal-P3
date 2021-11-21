/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import java.util.Date;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Farlen
 */
public class ReporteDto {
    private int tipo;
    private String NombreEmpresa;
    private Date DateInicio;
    private Date DateFinal;
    private Date FechaCierreCaja;
    private Long IdEmpleado;
    private Long IdFactura;
    private String telefono;
    private byte[] printReport;

    public ReporteDto(){}
    
    public ReporteDto(int tipo, String NombreEmpresa, Date DateInicio, Date DateFinal, Date FechaCierreCaja, Long IdEmpleado, Long IdFactura,String telefono,byte[] jp) {
        this.tipo = tipo;
        this.NombreEmpresa = NombreEmpresa;
        this.DateInicio = DateInicio;
        this.DateFinal = DateFinal;
        this.FechaCierreCaja = FechaCierreCaja;
        this.IdEmpleado = IdEmpleado;
        this.IdFactura = IdFactura;
        this.telefono = telefono;
        this.printReport = jp;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNombreEmpresa() {
        return NombreEmpresa;
    }

    public void setNombreEmpresa(String NombreEmpresa) {
        this.NombreEmpresa = NombreEmpresa;
    }

    public Date getDateInicio() {
        return DateInicio;
    }

    public void setDateInicio(Date DateInicio) {
        this.DateInicio = DateInicio;
    }

    public Date getDateFinal() {
        return DateFinal;
    }

    public void setDateFinal(Date DateFinal) {
        this.DateFinal = DateFinal;
    }

    public Date getFechaCierreCaja() {
        return FechaCierreCaja;
    }

    public void setFechaCierreCaja(Date FechaCierreCaja) {
        this.FechaCierreCaja = FechaCierreCaja;
    }

    public Long getIdEmpleado() {
        return IdEmpleado;
    }

    public void setIdEmpleado(Long IdEmpleado) {
        this.IdEmpleado = IdEmpleado;
    }

    public Long getIdFactura() {
        return IdFactura;
    }

    public void setIdFactura(Long IdFactura) {
        this.IdFactura = IdFactura;
    }  

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public byte[] getPrintReport() {
        return printReport;
    }

    public void setPrintReport(byte[] printReport) {
        this.printReport = printReport;
    }
    
    
}
