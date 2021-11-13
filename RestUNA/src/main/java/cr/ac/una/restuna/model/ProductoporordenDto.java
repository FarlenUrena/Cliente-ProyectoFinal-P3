/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import javafx.beans.property.SimpleStringProperty;

/** @author Kendall
 */
public class ProductoporordenDto {

    public SimpleStringProperty idProductoPorOrden;
    public SimpleStringProperty cantidad;
    public SimpleStringProperty precioProducto;
    public SimpleStringProperty subtotal;
    public Boolean modificado;
    public OrdenDto idOrdenDto;
    public ProductoDto idProductoDto;

    public ProductoporordenDto() {
        this.modificado = false;
        this.subtotal = new SimpleStringProperty();
        this.idProductoPorOrden = new SimpleStringProperty();
        this.cantidad = new SimpleStringProperty();
        this.precioProducto = new SimpleStringProperty();
        this.subtotal = new SimpleStringProperty();
        this.idOrdenDto = new OrdenDto();
        this.idProductoDto = new ProductoDto();
    }
    
    public Long getIdProductoPorOrden() {
        if (idProductoPorOrden.get() != null && !idProductoPorOrden.get().isBlank()) {
            return Long.valueOf(idProductoPorOrden.get());
        } else {
            return null;
        }
    }
    
    public void setIdProductoPorOrden(Long idProductoPorOrden) {
        this.idProductoPorOrden.set(idProductoPorOrden.toString());
    }
    
    public Long getCantidad() {
        if (cantidad.get() != null && !cantidad.get().isBlank()) {
            return Long.valueOf(cantidad.get());
        } else {
            return null;
        }
    }

    public void setCantidad(Long cantidad) {
        this.cantidad.set(cantidad.toString());
    }

    public Double getPrecioProducto() {
        if (precioProducto.get() != null && !precioProducto.get().isEmpty()) {
            return Double.valueOf(precioProducto.get());
        } else {
            return 0D;
        }
    }

    public void setPrecioProducto(Double precioProducto) {
        this.precioProducto.set(String.valueOf(precioProducto));
    }

    public Double getSubtotal() {
        if (subtotal.get() != null && !subtotal.get().isEmpty()) {
            return Double.valueOf(subtotal.get());
        } else {
            return 0D;
        }
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal.set(String.valueOf(subtotal));
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public OrdenDto getIdOrdenDto() {
        return idOrdenDto;
    }

    public void setIdOrdenDto(OrdenDto idOrdenDto) {
        this.idOrdenDto = idOrdenDto;
    }

    public ProductoDto getIdProductoDto() {
        return idProductoDto;
    }

    public void setIdProductoDto(ProductoDto idProductoDto) {
        this.idProductoDto = idProductoDto;
    }

    @Override
    public String toString() {
        return "ProductoporordenDto{" + "idProductoPorOrden=" + idProductoPorOrden + ", cantidad=" + cantidad + ", precioProducto=" + precioProducto + ", subtotal=" + subtotal + ", modificado=" + modificado + ", idOrdenDto=" + idOrdenDto + ", idProductoDto=" + idProductoDto + '}';
    }  
    
}
