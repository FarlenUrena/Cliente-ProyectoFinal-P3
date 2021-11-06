/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Kendall
 */
public class CajaDto {

    public SimpleStringProperty idCaja;
    public SimpleStringProperty saldoEfectivo;
    public SimpleStringProperty saldoTarjeta;
    public SimpleStringProperty saldoEfectivoCierre;
    public SimpleStringProperty saldoTarjetaCierre;
    public ObjectProperty<Date> fechaApertura;
    public ObjectProperty<Date> fechaCierre;
    public SimpleStringProperty esActiva;
    public Boolean modificado;
    //DTOs
    public EmpleadoDto idEmpleadoDto;
    public List<FacturaDto> facturasDto;
    public List<FacturaDto> facturasEliminadasDto;

    public CajaDto() {
        this.idCaja = new SimpleStringProperty();
        this.saldoEfectivo = new SimpleStringProperty();        
        this.saldoTarjeta = new SimpleStringProperty();
        this.saldoEfectivoCierre = new SimpleStringProperty();
        this.saldoTarjetaCierre = new SimpleStringProperty();
        this.esActiva = new SimpleStringProperty();
        this.modificado = false;
        this.idEmpleadoDto = new EmpleadoDto();
        this.facturasDto = new ArrayList<>();
        this.facturasEliminadasDto = new ArrayList<>();
    }

    public Long getIdCaja() {
        if (idCaja.get() != null && !idCaja.get().isBlank()) {
            return Long.valueOf(idCaja.get());
        } else {
            return null;
        }
    }

    public void setIdCaja(Long idCaja) {
        this.idCaja.set(idCaja.toString());
    }

    public Double getSaldoEfectivo() {
        if (saldoEfectivo.get() != null && !saldoEfectivo.get().isBlank()) {
            return Double.valueOf(saldoEfectivo.get());
        } else {
            return null;
        }
    }

    public void setSaldoEfectivo(Double saldoEfectivo) {
        this.saldoEfectivo.set(saldoEfectivo.toString());
    }

    public Double getSaldoTarjeta() {
        if (saldoTarjeta.get() != null && !saldoTarjeta.get().isBlank()) {
            return Double.valueOf(saldoTarjeta.get());
        } else {
            return null;
        }
    }

    public void setSaldoTarjeta(Double saldoTarjeta) {
        this.saldoTarjeta.set(saldoTarjeta.toString());
    }

    public Double getSaldoEfectivoCierre() {
        if (saldoEfectivoCierre.get() != null && !saldoEfectivoCierre.get().isBlank()) {
            return Double.valueOf(saldoEfectivoCierre.get());
        } else {
            return null;
        }
    }

    public void setSaldoEfectivoCierre(Double saldoEfectivoCierre) {
        this.saldoEfectivoCierre.set(saldoEfectivoCierre.toString());
    }

    public Double getSaldoTarjetaCierre() {
        if (saldoTarjetaCierre.get() != null && !saldoTarjetaCierre.get().isBlank()) {
            return Double.valueOf(saldoTarjetaCierre.get());
        } else {
            return null;
        }
    }

    public void setSaldoTarjetaCierre(Double saldoTarjetaCierre) {
        this.saldoTarjetaCierre.set(saldoTarjetaCierre.toString());
    }

    public Date getFechaApertura() {
        return fechaApertura.get();
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura.set(fechaApertura);
    }

    public Date getFechaCierre() {
        return fechaCierre.get();
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre.set(fechaCierre);
    }

    public Long getEsActiva() {
        if (esActiva.get() != null && !esActiva.get().isBlank()) {
            return Long.valueOf(esActiva.get());
        } else {
            return null;
        }
    }

    public void setEsActiva(Long esActiva) {
        this.esActiva.set(esActiva.toString());
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean Modificado) {
        this.modificado = Modificado;
    }

    public EmpleadoDto getIdEmpleadoDto() {
        return idEmpleadoDto;
    }

    public void setIdEmpleadoDto(EmpleadoDto idEmpleadoDto) {
        this.idEmpleadoDto = idEmpleadoDto;
    }

    public List<FacturaDto> getFacturasDto() {
        return facturasDto;
    }

    public void setFacturasDto(List<FacturaDto> facturasDto) {
        this.facturasDto = facturasDto;
    }

    public List<FacturaDto> getFacturasEliminadasDto() {
        return facturasEliminadasDto;
    }

    public void setFacturasEliminadasDto(List<FacturaDto> facturasEliminadasDto) {
        this.facturasEliminadasDto = facturasEliminadasDto;
    }

}
