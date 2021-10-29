/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jeez
 */
public class GrupoDto {

    private SimpleStringProperty idGrupo;
    private SimpleStringProperty nombreGrupo;
    private List<ProductoDto> productoList;

  
    public GrupoDto() {
        this.idGrupo = new SimpleStringProperty();
        this.nombreGrupo = new SimpleStringProperty();
    }

    public Long getIdGrupo() {
          if(idGrupo.get()!=null && !idGrupo.get().isEmpty())
            return Long.valueOf(idGrupo.get());
        else
            return null;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo.set(idGrupo);
    }

    public String getNombreGrupo() {
        return nombreGrupo.get();
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo.set(nombreGrupo);
    }

    public List<ProductoDto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<ProductoDto> productoList) {
        this.productoList = productoList;
    }

}
