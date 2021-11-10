/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import java.io.Serializable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Farlen
 */
public class ProductoDto implements Serializable{

    public SimpleStringProperty idProducto;
    public SimpleStringProperty nombre;
    public SimpleStringProperty nombreCorto;
    public SimpleStringProperty precio;
    public SimpleStringProperty esAccesoRapido;
    public SimpleStringProperty ventasTotales;
    public SimpleObjectProperty imagen;
    public BooleanProperty modificado;
    //DTOs
    public GrupoDto grupoDto;
//    public List<ProductoporordenDto> productosporordenDto;
//    public List<ProductoporordenDto> productosporordenEliminadosDto;

//    public ProductoDto() {
//        this.idProducto = new SimpleStringProperty();
//        this.nombre = new SimpleStringProperty();
//        this.nombreCorto = new SimpleStringProperty();
//        this.precio = new SimpleStringProperty();
//        this.esAccesoRapido = new SimpleStringProperty();
//        this.ventasTotales = new SimpleStringProperty();
//        this.imagen = new SimpleObjectProperty();
//        this.modificado = new Bool
//        this.grupoDto = new GrupoDto();
////        this.productosporordenDto = new ArrayList<>();
////        this.productosporordenEliminadosDto = new ArrayList<>();
//    }

    public void setIdProducto(Long idProducto) {
        this.idProducto.set(idProducto.toString());
    }

    public byte[] getImagen() {
        return (byte[]) imagen.getValue();
    }

    public void setImagen(byte[] imagen) {
        this.imagen.set(imagen);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getNombreCorto() {
        return nombreCorto.get();
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto.set(nombreCorto);
    }

    public Double getPrecio() {
        if (precio.get() != null && !precio.get().isEmpty()) {
            return Double.valueOf(precio.get());
        } else {
            return 0D;
        }
    }

    public void setPrecio(double precio) {
        this.precio.set(String.valueOf(precio));
    }

    public Long getEsAccesoRapido() {
        if (esAccesoRapido.get() != null && !esAccesoRapido.get().isBlank()) {
            return Long.valueOf(esAccesoRapido.get());
        } else {
            return null;
        }
    }

    public void setEsAccesoRapido(Long esAccesoRapido) {
        this.esAccesoRapido.set(esAccesoRapido.toString());
    }

    public Long getVentasTotales() {
        if (ventasTotales.get() != null && !ventasTotales.get().isEmpty()) {
            return Long.valueOf(ventasTotales.get());
        } else {
            return null;
        }
    }

    public void setVentasTotales(Long ventasTotales) {
        this.ventasTotales.set(ventasTotales.toString());
    }

    public Boolean getModificado() {
        return modificado.get();
    }

    public void setModificado(Boolean modificado) {
        this.modificado.set(modificado);
    }

    public GrupoDto getGrupoDto() {
        return grupoDto;
    }

    public void setGrupoDto(GrupoDto grupoDto) {
        this.grupoDto = grupoDto;
    }

//    public List<ProductoporordenDto> getProductosporordenDto() {
//        return productosporordenDto;
//    }
//
//    public void setProductosporordenDto(List<ProductoporordenDto> productosporordenDto) {
//        this.productosporordenDto = productosporordenDto;
//    }

    public Long getIdProducto() {
        if (idProducto.get() != null && !idProducto.get().isEmpty()) {
            return Long.valueOf(idProducto.get());
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "ProductoDto{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", nombreCorto=" + nombreCorto + ", precio=" + precio + ", esAccesoRapido=" + esAccesoRapido + ", ventasTotales=" + ventasTotales + ", imagen=" + imagen + ", grupo=" + grupoDto + '}';
    }

}
