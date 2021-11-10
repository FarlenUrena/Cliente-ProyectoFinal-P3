/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Kendall
 */
public class FacturaDto {
    public SimpleStringProperty idFactura;
    public ObjectProperty<Date> fechaFacturacion;
    public SimpleStringProperty metodoDePago;
    public SimpleStringProperty montoPagado;
    public SimpleStringProperty total;
    public SimpleStringProperty descuento;
    public Double impuestoVenta;
    public Double impuestoServicio;
    private CajaDto idCajaDto;
    private OrdenDto idOrdenDto;
  
    public FacturaDto() {
        this.idFactura = new SimpleStringProperty();
        this.fechaFacturacion = new SimpleObjectProperty<>();  
        this.metodoDePago = new SimpleStringProperty();
        this.metodoDePago = new SimpleStringProperty();
        this.montoPagado = new SimpleStringProperty();
        this.total = new SimpleStringProperty();
        this.descuento = new SimpleStringProperty();
        this.impuestoVenta = new Double(0);
        this.impuestoServicio = new Double(0);
        this.idCajaDto = new CajaDto();
        this.idOrdenDto = new OrdenDto();
    }

    public Double getImpuestoVenta() {
        return impuestoVenta;
    }

    public void setImpuestoVenta(double impuestoVenta) {
        this.impuestoVenta =  impuestoVenta;
    }

    public Double getImpuestoServicio() {
        return impuestoServicio;
    }

    public void setImpuestoServicio(double impuestoServicio) {
        this.impuestoServicio = impuestoServicio;
    }

      public Long getDescuento() {
        if(descuento.get()!=null && !descuento.get().isBlank())
            return Long.valueOf(descuento.get());
        else
            return null;
    }

    public void setDescuento(Long descuento) {
        this.descuento.set(descuento.toString());
    }
    
    
    public Long getIdElemento() {
        if(idFactura.get()!=null && !idFactura.get().isBlank())
            return Long.valueOf(idFactura.get());
        else
            return null;
        }
    public void setIdElemento(Long idFactura) {
        this.idFactura.set(idFactura.toString());
    }

    public Date getFechaFacturacion() {
        return fechaFacturacion.get();
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion.set(fechaFacturacion);
    }

    public String getMetodoDePago() {
        return metodoDePago.get();
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago.set(metodoDePago);
    }

    public String getMontoPagado() {
        return montoPagado.get();
    }

    public void setMontoPagado(String montoPagado) {
        this.montoPagado.set(montoPagado);
    }

    public String getTotal() {
        return total.get();
    }

    public void setTotal(String total) {
        this.total.set(total);
    }

    public CajaDto getIdCaja() {
        return idCajaDto;
    }

    public void setIdCaja(CajaDto idCajaDto) {
        this.idCajaDto = idCajaDto;
    }

    public OrdenDto getIdOrden() {
        return idOrdenDto;
    }

    public void setIdOrden(OrdenDto idOrdenDto) {
        this.idOrdenDto = idOrdenDto;
    }
    
    
}
