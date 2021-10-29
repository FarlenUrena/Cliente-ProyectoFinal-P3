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
 * @author Kendall
 */
public class ParametroDto {
    public SimpleStringProperty idParametro;
    public SimpleStringProperty nombre;
    public SimpleStringProperty valorNumerico;
    public SimpleStringProperty valorTexto;
    public SimpleStringProperty descripcion;
    private final SimpleObjectProperty imagen;

    public ParametroDto() {
        this.idParametro = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.valorNumerico = new SimpleStringProperty();
        this.valorTexto = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
         this.imagen = new SimpleObjectProperty();
    } 
    
     public Long getIdParametro() {
        if(idParametro.get()!=null && !idParametro.get().isBlank()) return Long.valueOf(idParametro.get());
        else return null;
    }

    public void setIdParametro(Long idParametro) {
        this.idParametro.set(idParametro.toString());
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.get();
    }

    public String getValorNumerico() {
        return valorNumerico.get();
    }

    public void setValorNumerico(String valorNumerico) {
        this.valorNumerico.set(valorNumerico);
    }

    public String getValorTexto() {
        return valorTexto.get();
    }

    public void setValorTexto(String valorTexto) {
        this.valorTexto.set(valorTexto);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }
    
    
  
}
