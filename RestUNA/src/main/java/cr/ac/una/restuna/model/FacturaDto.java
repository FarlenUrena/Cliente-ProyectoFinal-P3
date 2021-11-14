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
    public Boolean modificado;
    public CajaDto idCajaDto;
    public OrdenDto idOrdenDto;
  
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
        this.modificado = false;
        this.idCajaDto = new CajaDto();
        this.idOrdenDto = new OrdenDto();
    }

     public Long getIdFactura() {
        if(idFactura.get()!=null && !idFactura.get().isBlank())
            return Long.valueOf(idFactura.get());
        else
            return null;
        }
    public void setIdFactura(Long idFactura) {
        this.idFactura.set(idFactura.toString());
    }
    
    public Double getImpuestoVenta() {
        if (impuestoVenta.get() != null && !impuestoVenta.get().isEmpty()) {
            return Double.valueOf(impuestoVenta.get());
        } else {
            return 0D;
        }
    }

    public void setImpuestoVenta(String impuestoVenta) {
        this.impuestoVenta.set(String.valueOf(impuestoVenta));
    }

    public Double getImpuestoServicio() {
        if (impuestoServicio.get() != null && !impuestoServicio.get().isEmpty()) {
            return Double.valueOf(impuestoServicio.get());
        } else {
            return 0D;
        }
    }

    public void setImpuestoServicio(Double impuestoServicio) {
        this.impuestoServicio.set(String.valueOf(impuestoServicio));
    }

      public Double getDescuento() {
        if (descuento.get() != null && !descuento.get().isEmpty()) {
            return Double.valueOf(descuento.get());
        } else {
            return 0D;
        }
    }

    public void setDescuento(Double descuento) {
        this.descuento.set(String.valueOf(descuento));
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

    public Double getMontoPagado() {
        if (montoPagado.get() != null && !montoPagado.get().isEmpty()) {
            return Double.valueOf(montoPagado.get());
        } else {
            return 0D;
        }
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado.set(String.valueOf(montoPagado));
    }

    public Double getTotal() {
        if (total.get() != null && !total.get().isEmpty()) {
            return Double.valueOf(total.get());
        } else {
            return 0D;
        }
    }

    public void setTotal(Double total) {
        this.total.set(String.valueOf(total));
    }

    public Double getVuelto() {
        if (vuelto.get() != null && !vuelto.get().isEmpty()) {
            return Double.valueOf(vuelto.get());
        } else {
            return 0D;
        }
    }

    public void setVuelto(Double vuelto) {
        this.vuelto.set(String.valueOf(vuelto));
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
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
