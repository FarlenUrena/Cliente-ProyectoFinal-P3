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
//    private List<ElementoDeSeccionDto> seccionDtos;

    public SeccionDto(){
        List<EmpleadoDto> empleadoList = new ArrayList<>();
//        List<ElementoDeSeccionDto> seccionDtos = new ArrayList<>();
    }
    public SeccionDto(SimpleStringProperty idSeccion, SimpleStringProperty nombre, SimpleObjectProperty fotoDistribucion) {
        this.idSeccion = idSeccion;
        this.nombre = nombre;
        this.fotoDistribucion = fotoDistribucion;
    }

    public Long getIdSeccion() {
        if(idSeccion.get()!=null && !idSeccion.get().isEmpty())
            return Long.valueOf(idSeccion.get());
        else
            return null;
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

    public SimpleObjectProperty fotoDistribucionProperty() {
        return fotoDistribucion;
    }

    public void setFotoDistribucion(Object fotoDistribucion) {
        this.fotoDistribucion.set(fotoDistribucion);
    }

    public List<EmpleadoDto> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<EmpleadoDto> empleadoList) {
        this.empleadoList = empleadoList;
    }
//    private List<ElementoDeSeccionDto> elementoDeSeccionList;
    }
