/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Farlen
 */
public class ProductoDto{
    public SimpleStringProperty idProducto;
    public SimpleStringProperty nombre;
    public SimpleStringProperty nombreCorto;
    public SimpleStringProperty precio;
//    public SimpleStringProperty grupo;
    public SimpleStringProperty esAccesoRapido;
    public SimpleStringProperty ventasTotales;
    public SimpleObjectProperty imagen;
    public GrupoDto grupo;

//    private static final long serialVersionUID = 1L;
    
    public ProductoDto() {
        this.idProducto = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.nombreCorto = new SimpleStringProperty();
        this.precio = new SimpleStringProperty();
        this.esAccesoRapido = new SimpleStringProperty();
        this.ventasTotales = new SimpleStringProperty();
        this.imagen = new SimpleObjectProperty();
        this.grupo = new GrupoDto();
    }   

    public Long getIdProducto() {
        if(idProducto.get()!=null && !idProducto.get().isEmpty())
            return Long.valueOf(idProducto.get());
        else
            return null;
    }

    public void setIdProducto(Long idProducto) {this.idProducto.set(idProducto.toString());}

    public byte[] getImagen() {return (byte[]) imagen.getValue();}
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

    public float getPrecio() {
       if(precio.get()!=null && !precio.get().isEmpty())
            return Float.valueOf(precio.get());
        else
            return 0;
    }

    public void setPrecio(float precio) {
        this.precio.set(String.valueOf(precio));
    }

//    public Long getGrupo() {
//        if(grupo.get()!=null && !grupo.get().isEmpty())
//            return Long.valueOf(grupo.get());
//        else
//            return null;
//    }

//    public void setGrupo(Long grupo) {
//        this.grupo.set(grupo.toString());
//    }

    public Long getEsAccesoRapido() {
         if(esAccesoRapido.get()!=null && !esAccesoRapido.get().isEmpty())
            return Long.valueOf(esAccesoRapido.get());
        else
            return null;
    }

    public void setEsAccesoRapido(Long esAccesoRapido) {
        this.esAccesoRapido.set(esAccesoRapido.toString());
    }

    public Long getVentasTotales() {
        if(ventasTotales.get()!=null && !ventasTotales.get().isEmpty())
            return Long.valueOf(ventasTotales.get());
        else
            return null;
    }

    public void setVentasTotales(Long ventasTotales) {
        this.ventasTotales.set(ventasTotales.toString());
    }
    
    
    public GrupoDto getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoDto grupo) {
        this.grupo = grupo;
    }
    
}
