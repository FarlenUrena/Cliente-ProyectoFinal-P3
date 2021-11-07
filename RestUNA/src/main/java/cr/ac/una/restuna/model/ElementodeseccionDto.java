package cr.ac.una.restuna.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ElementodeseccionDto {

    public SimpleStringProperty idElemento;
    public SimpleStringProperty tipo;
    public SimpleStringProperty nombre;
    public SimpleStringProperty esOcupada;
    public SimpleStringProperty posicionX;
    public SimpleStringProperty posicionY;
    public SimpleStringProperty impuestoPorServicio;
    public SimpleObjectProperty imagenElemento;
    public Boolean modificado;
    //DTOs
    public SeccionDto idSeccionDto;
//    public List<OrdenDto> ordenesDtoList;
//    public List<OrdenDto> ordenesEliminadasDtoList;

    public ElementodeseccionDto() {
        this.idElemento = new SimpleStringProperty();
        this.tipo = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.esOcupada = new SimpleStringProperty();
        this.posicionX = new SimpleStringProperty();
        this.posicionY = new SimpleStringProperty();
        this.impuestoPorServicio = new SimpleStringProperty();
        this.imagenElemento = new SimpleObjectProperty();
        this.idSeccionDto = new SeccionDto();
//        this.ordenesDtoList = new ArrayList<>();
//        this.ordenesEliminadasDtoList = new ArrayList<>();
    }

    public Long getIdElemento() {
        if (idElemento.get() != null && !idElemento.get().isBlank()) {
            return Long.valueOf(idElemento.get());
        } else {
            return null;
        }
    }

    public void setIdElemento(Long idElemento) {
        this.idElemento.set(idElemento.toString());
    }

    public Long getTipo() {
        if (tipo.get() != null && !tipo.get().isBlank()) {
            return Long.valueOf(tipo.get());
        } else {
            return null;
        }
    }

    public void setTipo(Long tipo) {
        this.tipo.set(tipo.toString());
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public Long getEsOcupada() {
        if (esOcupada.get() != null && !esOcupada.get().isEmpty()) {
            return Long.valueOf(esOcupada.get());
        } else {
            return null;
        }
    }

    public void setEsOcupada(Long esOcupada) {
        this.esOcupada.set(esOcupada.toString());
    }

    public Double getPosicionX() {
        if (posicionX.get() != null && !posicionX.get().isEmpty()) {
            return Double.valueOf(posicionX.get());
        } else {
            return null;
        }
    }

    public void setPosicionX(Double posicionX) {
        this.posicionX.set(posicionX.toString());
    }

    public Double getPosicionY() {
        if (posicionY.get() != null && !posicionY.get().isEmpty()) {
            return Double.valueOf(posicionY.get());
        } else {
            return null;
        }
    }

    public void setPosicionY(Double posicionY) {
        this.posicionY.set(posicionY.toString());
    }

    public Double getImpuestoPorServicio() {
        if (impuestoPorServicio.get() != null && !impuestoPorServicio.get().isEmpty()) {
            return Double.valueOf(impuestoPorServicio.get());
        } else {
            return null;
        }
    }

    public void setImpuestoPorServicio(Double impuestoPorServicio) {
        this.impuestoPorServicio.set(impuestoPorServicio.toString());
    }

    public byte[] getImagenElemento() {
        return (byte[]) imagenElemento.get();
    }

    public void setImagenElemento(byte[] imagenElemento) {
        this.imagenElemento.set(imagenElemento);
    }

    public Boolean getModificado() {
            return modificado ;
    }

    public void setModificado(Boolean modificado) {
        this.modificado= modificado;
    }

    public SeccionDto getIdSeccionDto() {
        return idSeccionDto;
    }

    public void setIdSeccionDto(SeccionDto idSeccionDto) {
        this.idSeccionDto = idSeccionDto;
    }

//    public List<OrdenDto> getOrdenesDtoList() {
//        return ordenesDtoList;
//    }
//
//    public void setOrdenesDtoList(List<OrdenDto> ordenesDtoList) {
//        this.ordenesDtoList = ordenesDtoList;
//    }
//
//    public List<OrdenDto> getOrdenesEliminadasDtoList() {
//        return ordenesEliminadasDtoList;
//    }
//
//    public void setOrdenesEliminadasDtoList(List<OrdenDto> ordenesEliminadasDtoList) {
//        this.ordenesEliminadasDtoList = ordenesEliminadasDtoList;
//    }

}
