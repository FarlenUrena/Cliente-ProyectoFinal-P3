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
    public ObjectProperty<Long> metodoDePago;
    public SimpleStringProperty montoPagado;
    public SimpleStringProperty total;
    public SimpleStringProperty vuelto;
    public SimpleStringProperty descuento;
    public SimpleStringProperty impuestoVenta;
    public SimpleStringProperty impuestoServicio;
    private CajaDto idCajaDto;
    private OrdenDto idOrdenDto;
  
    public FacturaDto() {
        this.idFactura = new SimpleStringProperty();
        this.fechaFacturacion = new SimpleObjectProperty<>();  
        this.metodoDePago = new SimpleObjectProperty(1);
        this.montoPagado = new SimpleStringProperty();
        this.total = new SimpleStringProperty();
        this.vuelto = new SimpleStringProperty();
        this.descuento = new SimpleStringProperty();
        this.impuestoVenta = new SimpleStringProperty();
        this.impuestoServicio = new SimpleStringProperty();
        this.idCajaDto = new CajaDto();
        this.idOrdenDto = new OrdenDto();
    }

    public Double getImpuestoVenta() {
        return Double.valueOf(impuestoVenta.getValue());
    }

    public void setImpuestoVenta(String impuestoVenta) {
        this.impuestoVenta.set(Double.valueOf(impuestoVenta).toString());
    }

    public Double getImpuestoServicio() {
        return Double.valueOf(impuestoServicio.getValue());
    }

    public void setImpuestoServicio(String impuestoServicio) {
        this.impuestoServicio.set(Double.valueOf(impuestoServicio).toString());
    }

      public Double getDescuento() {
        return Double.valueOf(descuento.getValue());
    }

    public void setDescuento(String descuento) {
        this.descuento.set(Double.valueOf(descuento).toString());
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

    public Long getMetodoDePago() {
        return metodoDePago.get();
    }

    public void setMetodoDePago(Long metodoDePago) {
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

    public String getVuelto() {
        return vuelto.get();
    }

    public void setVuelto(String vuelto) {
        this.vuelto.set(vuelto);
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
