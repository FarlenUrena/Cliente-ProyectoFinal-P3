package cr.ac.una.restuna.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ElementoDto {

    public SimpleStringProperty idElemento;
    public SimpleStringProperty tipo;
    public SimpleStringProperty nombre;
    public SimpleStringProperty esOcupada;
    public SimpleStringProperty posicionX;
    public SimpleStringProperty posicionY;
    public SimpleStringProperty impuestoPorServicio;
    public SimpleObjectProperty imagenElemento;
    public SeccionDto idSeccion;

    public ElementoDto() {
        this.idElemento = new SimpleStringProperty();
        this.tipo = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.esOcupada = new SimpleStringProperty();
        this.posicionX = new SimpleStringProperty();
        this.posicionY = new SimpleStringProperty();
        this.impuestoPorServicio = new SimpleStringProperty();
        this.imagenElemento = new SimpleObjectProperty();
        this.idSeccion = new SeccionDto();
    }
    
    public Long getIdElemento() {
        if(idElemento.get()!=null && !idElemento.get().isBlank())
            return Long.valueOf(idElemento.get());
        else
            return null;
    }
    public void setIdElemento(Long idElemento) {
        this.idElemento.set(idElemento.toString());
    }
    public Long getTipo() {
        if(tipo.get()!=null && !tipo.get().isBlank())
            return Long.valueOf(tipo.get());
        else
            return null;
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
        if(esOcupada.get()!=null && !esOcupada.get().isEmpty())
            return Long.valueOf(esOcupada.get());
        else
            return null;
    }
    public void setEsOcupada(Long esOcupada) {
        this.esOcupada.set(esOcupada.toString());
    }

    public Long getPosicionX() {
        if(posicionX.get()!=null && !posicionX.get().isEmpty())
            return Long.valueOf(posicionX.get());
        else
            return null;
    }
    public void setPosicionX(Long posicionX) {
        this.posicionX.set(posicionX.toString());
    }

    public Long getPosicionY() {
        if(posicionY.get()!=null && !posicionY.get().isEmpty())
            return Long.valueOf(posicionY.get());
        else
            return null;
    }
    public void setPosicionY(Long posicionY) {
        this.posicionY.set(posicionY.toString());
    }

    public Long getImpuestoPorServicio() {
        if(impuestoPorServicio.get()!=null && !impuestoPorServicio.get().isEmpty())
            return Long.valueOf(impuestoPorServicio.get());
        else
            return null;
    }
    public void setImpuestoPorServicio(Long impuestoPorServicio) {
        this.impuestoPorServicio.set(impuestoPorServicio.toString());
    }

    public byte[] getImagenElemento() {
        return (byte[] )imagenElemento.get();
    }


    public void setImagenElemento(byte[] imagenElemento) {
        this.imagenElemento.set(imagenElemento);
    }

    public SeccionDto getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(SeccionDto idSeccion) {
        this.idSeccion = idSeccion;
    }



}
