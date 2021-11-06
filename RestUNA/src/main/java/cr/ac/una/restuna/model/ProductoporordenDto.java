/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Kenda
 */
public class ProductoporordenDto {
    public SimpleStringProperty idProductoPorOrden;
    public SimpleStringProperty cantidad;
    public SimpleStringProperty precioProducto;
    //falta subtotal
    public OrdenDto idOrdenDto;
    public ProductoDto idProductoDto;

    public ProductoporordenDto() {
        this.idProductoPorOrden = new SimpleStringProperty();
        this.cantidad  = new SimpleStringProperty();
        this.precioProducto = new SimpleStringProperty();
        this.idOrdenDto  = new OrdenDto();
        this.idProductoDto = new ProductoDto();
    }

      public Long getIdElemento() {
        if(idProductoPorOrden.get()!=null && !idProductoPorOrden.get().isBlank())
            return Long.valueOf(idProductoPorOrden.get());
        else
            return null;
        }

    public void setIdProductoPorOrden(String idProductoPorOrden) {
        this.idProductoPorOrden.set(idProductoPorOrden);
    }

    public String getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(String cantidad) {
        this.cantidad.set(cantidad);
    }

    public Double getPrecioProducto() {
        if (precioProducto.get() != null && !precioProducto.get().isEmpty()) {
            return Double.valueOf(precioProducto.get());
        } else {
            return 0D;
        }
    }

    public void setPrecioProducto(Double precioProducto) {
        this.precioProducto.set(precioProducto.toString());
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
    
}
