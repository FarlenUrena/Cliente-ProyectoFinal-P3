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
    private SimpleStringProperty idFactura;
    private ObjectProperty<LocalDate> fechaFacturacion;
    private SimpleStringProperty metodoDePago;
    private SimpleStringProperty montoPagado;
    private SimpleStringProperty total;
    private CajaDto idCaja;
    private OrdenDto idOrden;

    public FacturaDto() {
        this.idFactura = new SimpleStringProperty();
        this.metodoDePago = new SimpleStringProperty();
        this.metodoDePago = new SimpleStringProperty();
        this.montoPagado = new SimpleStringProperty();
        this.total = new SimpleStringProperty();
        this.fechaFacturacion = new SimpleObjectProperty<>();       
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

    public LocalDate getFechaFacturacion() {
        return fechaFacturacion.get();
    }

    public void setFechaFacturacion(LocalDate fechaFacturacion) {
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
        return idCaja;
    }

    public void setIdCaja(CajaDto idCaja) {
        this.idCaja = idCaja;
    }

    public OrdenDto getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(OrdenDto idOrden) {
        this.idOrden = idOrden;
    }

    
    
    
    
    
}
