package cr.ac.una.restuna.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class SeccionDto {

    private SimpleStringProperty idSeccion;
    private SimpleStringProperty nombre;
    private SimpleObjectProperty fotoDistribucion;
    private List<EmpleadoDto> empleadoList;
    private List<ElementodeseccionDto> seccionDtos;

    public SeccionDto() {
        this.idSeccion = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.fotoDistribucion = new SimpleObjectProperty();
        this.empleadoList = new ArrayList<>();
        this.seccionDtos = new ArrayList<>();
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

    public List<EmpleadoDto> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<EmpleadoDto> empleadoList) {
        this.empleadoList = empleadoList;
    }
<<<<<<< Updated upstream
//    private List<ElementoDeSeccionDto> elementoDeSeccionList;
    }
=======

    public List<ElementodeseccionDto> getSeccionDtos() {
        return seccionDtos;
    }

    public void setSeccionDtos(List<ElementodeseccionDto> seccionDtos) {
        this.seccionDtos = seccionDtos;
    }
}
>>>>>>> Stashed changes
