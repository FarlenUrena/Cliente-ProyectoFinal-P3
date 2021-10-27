/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

/**
 *
 * @author Farlen
 */
public class ParametroDto {

    private Long idParametro;
    private String nombre;
    private float valorNumerico;
    private String valorTexto;
    private String descripcion;
    
    public ParametroDto() {
        this.idParametro = new Long(0);
        this.nombre = new String();
        this.valorNumerico=new Float(0);
        this.valorTexto = new String();
        this.descripcion=new String();
    }

    public ParametroDto(Long idParametro, String nombre, float valorNumerico, String valorTexto, String descripcion) {
        this.idParametro = idParametro;
        this.nombre = nombre;
        this.valorNumerico = valorNumerico;
        this.valorTexto = valorTexto;
        this.descripcion = descripcion;
    }
    
    
    public Long getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Long idParametro) {
        this.idParametro = idParametro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getValorNumerico() {
        return valorNumerico;
    }

    public void setValorNumerico(float valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    public String getValorTexto() {
        return valorTexto;
    }

    public void setValorTexto(String valorTexto) {
        this.valorTexto = valorTexto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
