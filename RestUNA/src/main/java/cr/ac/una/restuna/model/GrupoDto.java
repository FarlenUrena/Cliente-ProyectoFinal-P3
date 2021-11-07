/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jeez
 */
public class GrupoDto{
    
    

    public SimpleStringProperty idGrupoDto;
    public SimpleStringProperty nombreGrupo;
    public Boolean modificado;
    public List<ProductoDto> productosDto;
//    public List<ProductoDto> productosEliminadosDto;

    public GrupoDto() {
        this.idGrupoDto = new SimpleStringProperty();
        this.nombreGrupo = new SimpleStringProperty();
        this.modificado = false;
        this.productosDto = new ArrayList<>();
//        this.productosEliminadosDto = new ArrayList<>();
    }

    public Long getIdGrupoDto() {
        if (idGrupoDto.get() != null && !idGrupoDto.get().isEmpty()) {
            return Long.valueOf(idGrupoDto.get());
        } else {
            return null;
        }
    }

    public void setIdGrupoDto(Long idGrupo) {
        this.idGrupoDto.set(idGrupo.toString());
    }

    public String getNombreGrupo() {
        return nombreGrupo.get();
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo.set(nombreGrupo);
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public List<ProductoDto> getProductosDto() {
        return productosDto;
    }

    public void setProductosDto(List<ProductoDto> productosDto) {
        this.productosDto = productosDto;
    }

//    public List<ProductoDto> getProductosEliminadosDto() {
//        return productosEliminadosDto;
//    }
//
//    public void setProductosEliminadosDto(List<ProductoDto> productosEliminadosDto) {
//        this.productosEliminadosDto = productosEliminadosDto;
//    }

}
