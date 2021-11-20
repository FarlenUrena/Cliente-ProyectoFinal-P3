/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Kendall
 */
public class ParametroDto {

    private SimpleStringProperty idParametro;
    private SimpleStringProperty nombreRestaurante;
    private SimpleStringProperty correoRestaurante;//ARREGLAR EN EL SCRIPT Y MODEL NOMBRE MAL ESCRITO
    private SimpleStringProperty impuestoServicio;
    private SimpleStringProperty impuestoVenta;
    private SimpleStringProperty descuentoMaximo;
    private SimpleObjectProperty logoRestaurante;

    public ParametroDto() {
        this.idParametro = new SimpleStringProperty();
        this.nombreRestaurante = new SimpleStringProperty();
        this.correoRestaurante = new SimpleStringProperty();
        this.impuestoServicio = new SimpleStringProperty();
        this.impuestoVenta = new SimpleStringProperty();
        this.descuentoMaximo = new SimpleStringProperty();
        this.logoRestaurante = new SimpleObjectProperty();
    }

    public Long getIdParametro() {
        if (idParametro.get() != null && !idParametro.get().isBlank()) {
            return Long.valueOf(idParametro.get());
        } else {
            return null;
        }
    }

    public void setIdParametro(Long idParametro) {
        this.idParametro.set(idParametro.toString());
    }

    public String getNombreRestaurante() {
        return nombreRestaurante.get();
    }

    public void setNombreRestaurante(String nombreRestaurante) {
        this.nombreRestaurante.set(nombreRestaurante);
    }

    public String getCorreoRestaurante() {
        return correoRestaurante.get();//ARREGLAR EN EL SCRIPT Y MODEL NOMBRE MAL ESCRITO
    }

    public void setCorreoRestaurante(String correoRestaurante) {
        this.correoRestaurante.set(correoRestaurante);//ARREGLAR EN EL SCRIPT Y MODEL NOMBRE MAL ESCRITO
    }

    public Double getImpuestoServicio() {
        if (impuestoServicio.get() != null && !impuestoServicio.get().isBlank()) {
            return Double.valueOf(impuestoServicio.get());
        } else {
            return null;
        }
    }

    public void setImpuestoServicio(Double impuestoServicio) {
        
        this.impuestoServicio.set(impuestoServicio.toString());
    }

    public Double getImpuestoVenta() {
        if (impuestoVenta.get() != null && !impuestoVenta.get().isBlank()) {
            return Double.valueOf(impuestoVenta.get());
        } else {
            return null;
        }
    }

    public void setImpuestoVenta(Double impuestoVenta) {
        this.impuestoVenta.set(impuestoVenta.toString());
    }

    public Double getDescuentoMaximo() {
        if (descuentoMaximo.get() != null && !descuentoMaximo.get().isBlank()) {
            return Double.valueOf(descuentoMaximo.get());
        } else {
            return null;
        }
    }

    public void setDescuentoMaximo(Double descuentoMaximo) {
        this.descuentoMaximo.set(descuentoMaximo.toString());
    }

    public byte[] getLogoRestaurante() {
        return (byte[]) logoRestaurante.getValue();
    }

    public void setLogoRestaurante(byte[] logoRestaurante) {
        this.logoRestaurante.set(logoRestaurante);
    }  

    @Override
    public String toString() {
        return "ParametroDto{" + "idParametro=" + idParametro + ", nombreRestaurante=" + nombreRestaurante + ", correoRestaurante=" + correoRestaurante + ", impuestoServicio=" + impuestoServicio + ", impuestoVenta=" + impuestoVenta + ", descuentoMaximo=" + descuentoMaximo + ", logoRestaurante=" + logoRestaurante + '}';
    }

//  

//    public Long getIdParametro() {
//        if (idParametro.get() != null && !idParametro.get().isBlank()) {
//            return Long.valueOf(idParametro.get());
//        } else {
//            return null;
//        }
//    }
//
//    public byte[] getImagen() {
//        return (byte[]) imagen.getValue();
//    }
//
//    public void setImagen(byte[] imagen) {
//        this.imagen.set(imagen);
//    }
//
//    public void setIdParametro(Long idParametro) {
//        this.idParametro.set(idParametro.toString());
//    }
//
//    public String getNombre() {
//        return nombre.get();
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre.set(nombre);
//    }
//
//    public Double getValorNumerico() {
//        if (valorNumerico.get() != null && !valorNumerico.get().isBlank()) {
//            return Double.valueOf(valorNumerico.get());
//        } else {
//            return null;
//        }
//    }
//
//    public void setValorNumerico(Double valorNumerico) {
//        this.valorNumerico.set(valorNumerico.toString());
//    }
//
//    public String getValorTexto() {
//        return valorTexto.get();
//    }
//
//    public void setValorTexto(String valorTexto) {
//        this.valorTexto.set(valorTexto);
//    }
//
//    public String getDescripcion() {
//        return descripcion.get();
//    }
//
//    public void setDescripcion(String descripcion) {
//        this.descripcion.set(descripcion);
//    }

}
