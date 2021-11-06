/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import java.time.LocalDate;
import java.util.Date;
import javafx.beans.InvalidationListener;
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
    public Long descuento;
    //falta los impuestos
    //DTOs
    private CajaDto idCajaDto;
    private OrdenDto idOrdenDto;
    public Long getDescuento() {
        return descuento;
    }

    public void setDescuento(Long descuento) {
        this.descuento = descuento;
    }

    public FacturaDto() {
        this.idFactura = new SimpleStringProperty();
        this.fechaFacturacion = new SimpleObjectProperty<>();  
        this.metodoDePago = new SimpleStringProperty();
        this.metodoDePago = new SimpleStringProperty();
        this.montoPagado = new SimpleStringProperty();
        this.total = new SimpleStringProperty();
        //falta los impuestos y descuento
        this.idCajaDto = new CajaDto();
        this.idOrdenDto = new OrdenDto();
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

    public SimpleStringProperty getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(SimpleStringProperty montoPagado) {
        this.montoPagado = montoPagado;
    }

    public SimpleStringProperty getTotal() {
        return total;
    }

    public void setTotal(SimpleStringProperty total) {
        this.total = total;
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
