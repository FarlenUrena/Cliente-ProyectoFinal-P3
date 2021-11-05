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
    private SimpleStringProperty idProductoPorOrden;
    private SimpleStringProperty cantidad;
    private SimpleStringProperty precioProducto;
    private OrdenDto idOrden;
    private ProductoDto idProducto;

    public ProductoporordenDto() {
        this.idProductoPorOrden = new SimpleStringProperty();
        this.cantidad  = new SimpleStringProperty();
        this.precioProducto = new SimpleStringProperty();
        this.idOrden  = new OrdenDto();
        this.idProducto = new ProductoDto();
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

    public String getPrecioProducto() {
        return precioProducto.get();
    }

    public void setPrecioProducto(String precioProducto) {
        this.precioProducto.set(precioProducto);
    }

    public OrdenDto getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(OrdenDto idOrden) {
        this.idOrden = idOrden;
    }

    public ProductoDto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(ProductoDto idProducto) {
        this.idProducto = idProducto;
    }
    
    
    
    
}
