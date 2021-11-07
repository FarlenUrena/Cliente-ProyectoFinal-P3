/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Farlen
 */
public class EmpleadoDto {

    public SimpleStringProperty idEmpleado;
    public SimpleStringProperty nombre;
    public SimpleStringProperty apellido;
    public SimpleStringProperty cedula;
    public SimpleStringProperty nombreUsuario;
    public SimpleStringProperty password;
    public SimpleStringProperty rol;
    private String token;
    private Boolean modificado;
    //DTOs
    public List<OrdenDto> ordenesDto;
    public List<OrdenDto> ordenesElimindasDto;
    public List<CajaDto> cajasDto;
    public List<CajaDto> cajasEliminadasDto;

    public EmpleadoDto() {
        this.modificado = false;
        this.idEmpleado = new SimpleStringProperty();
        this.nombre = new SimpleStringProperty();
        this.apellido = new SimpleStringProperty();
        this.cedula = new SimpleStringProperty();
        this.nombreUsuario = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.rol = new SimpleStringProperty();
         this.modificado = false;
        this.ordenesDto = new ArrayList<>();
        this.ordenesElimindasDto = new ArrayList<>();
        this.cajasDto = new ArrayList<>();
        this.cajasEliminadasDto = new ArrayList<>();
    }

    public Long getIdEmpleado() {
        if (idEmpleado.get() != null && !idEmpleado.get().isBlank()) {
            return Long.valueOf(idEmpleado.get());
        } else {
            return null;
        }
    }

    public void setIdEmpleado(Long empId) {
        this.idEmpleado.set(empId.toString());
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getApellido() {
        return apellido.get();
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public String getCedula() {
        return cedula.get();
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public String getNombreUsuario() {
        return nombreUsuario.get();
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario.set(nombreUsuario);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Long getRol() {
        if (rol.get() != null && !rol.get().isEmpty()) {
            return Long.valueOf(rol.get());
        } else {
            return null;
        }
    }

    public void setRol(Long rol) {
        this.rol.setValue(rol.toString());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public List<OrdenDto> getOrdenesDto() {
        return ordenesDto;
    }

    public void setOrdenesDto(List<OrdenDto> ordenesDto) {
        this.ordenesDto = ordenesDto;
    }

    public List<OrdenDto> getOrdenesElimindasDto() {
        return ordenesElimindasDto;
    }

    public void setOrdenesElimindasDto(List<OrdenDto> ordenesElimindasDto) {
        this.ordenesElimindasDto = ordenesElimindasDto;
    }

    public List<CajaDto> getCajasDto() {
        return cajasDto;
    }

    public void setCajasDto(List<CajaDto> cajasDto) {
        this.cajasDto = cajasDto;
    }

    public List<CajaDto> getCajasEliminadasDto() {
        return cajasEliminadasDto;
    }

    public void setCajasEliminadasDto(List<CajaDto> cajasEliminadasDto) {
        this.cajasEliminadasDto = cajasEliminadasDto;
    }
}
