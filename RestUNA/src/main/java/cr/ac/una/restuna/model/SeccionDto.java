package cr.ac.una.restuna.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class SeccionDto {

    public SimpleStringProperty idSeccion;
    public SimpleStringProperty nombre;
    public SimpleObjectProperty fotoDistribucion;
    public Boolean modificado;
    //DTOs
    public List<ElementodeseccionDto> elementosdeseccionDto;
    public List<ElementodeseccionDto> elementosdeseccionEliminadosDto;

    public SeccionDto() {
        this.idSeccion = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.fotoDistribucion = new SimpleObjectProperty();
        this.modificado = false;
        this.elementosdeseccionDto = new ArrayList<>();
        this.elementosdeseccionEliminadosDto = new ArrayList<>();
    }

    public Long getIdSeccion() {
        if (idSeccion.get() != null && !idSeccion.get().isEmpty()) {
            return Long.valueOf(idSeccion.get());
        } else {
            return null;
        }
    }

    public SimpleStringProperty idSeccionProperty() {
        return idSeccion;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion.set(idSeccion);
    }

    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public byte[] getFotoDistribucion() {
        return (byte[]) fotoDistribucion.getValue();

    }

    public byte[] fotoDistribucionProperty() {
        return (byte[]) fotoDistribucion.getValue();
    }

    public void setFotoDistribucion(byte[] fotoDistribucion) {
        this.fotoDistribucion.set(fotoDistribucion);
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public List<ElementodeseccionDto> getElementosdeseccionDto() {
        return elementosdeseccionDto;
    }

    public void setElementosdeseccionDto(List<ElementodeseccionDto> elementosdeseccionDto) {
        this.elementosdeseccionDto = elementosdeseccionDto;
    }

    public List<ElementodeseccionDto> getElementosdeseccionEliminadosDto() {
        return elementosdeseccionEliminadosDto;
    }

    public void setElementosdeseccionEliminadosDto(List<ElementodeseccionDto> elementosdeseccionEliminadosDto) {
        this.elementosdeseccionEliminadosDto = elementosdeseccionEliminadosDto;
    }
}
