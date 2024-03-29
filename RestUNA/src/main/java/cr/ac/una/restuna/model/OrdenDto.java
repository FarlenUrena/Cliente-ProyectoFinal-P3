/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Kendall
 */
public class OrdenDto {

    private SimpleStringProperty idOrden;
    private SimpleObjectProperty<Date> fechaCreacion;
    private SimpleStringProperty nombreCliente;
    private SimpleStringProperty esEstado;
    private ElementodeseccionDto idElementodeseccionDto;
    private EmpleadoDto idEmpleadoDto;
    private Boolean modificado;
    private List<ProductoporordenDto> productosporordenDto;
    private List<ProductoporordenDto> productosporordenElimindosDto;
    private List<FacturaDto> facturasDto;
    private List<FacturaDto> facturasEliminadasDto;
    
    
    public OrdenDto() {
        this.idOrden = new SimpleStringProperty();
        this.fechaCreacion = new SimpleObjectProperty<Date>();
        this.nombreCliente = new SimpleStringProperty();
        this.esEstado = new SimpleStringProperty();
        this.idElementodeseccionDto = new ElementodeseccionDto();
        this.idEmpleadoDto = new EmpleadoDto();
        this.modificado = false;
        this.productosporordenDto = new ArrayList<>();
        this.productosporordenElimindosDto = new ArrayList<>();
        this.facturasDto = new ArrayList<>();
        this.facturasEliminadasDto = new ArrayList<>();
    }
    

    public Long getIdOrden() {
         if (idOrden.get() != null && !idOrden.get().isEmpty()) {
            return Long.valueOf(idOrden.get());
        } else {
            return null;
        }
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden.set(idOrden.toString());
    }

    public Date getFechaCreacion() {
        return fechaCreacion.get();
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion.set(fechaCreacion);
    }

    public String getNombreCliente() {
        return nombreCliente.get();
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente.set(nombreCliente);
    }

    public Long getEsEstado() {
        if (esEstado.get() != null && !esEstado.get().isEmpty()) {
            return Long.valueOf(esEstado.get());
        } else {
            return null;
        }
    }

    public void setEsEstado(Long esEstado) {
        this.esEstado.set(esEstado.toString());
    }

    public ElementodeseccionDto getIdElementodeseccionDto() {
        return idElementodeseccionDto;
    }

    public void setIdElementodeseccionDto(ElementodeseccionDto idElementodeseccionDto) {
        this.idElementodeseccionDto = idElementodeseccionDto;
    }

    public EmpleadoDto getIdEmpleadoDto() {
        return idEmpleadoDto;
    }

    public void setIdEmpleadoDto(EmpleadoDto idEmpleadoDto) {
        this.idEmpleadoDto = idEmpleadoDto;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public List<ProductoporordenDto> getProductosporordenDto() {
        return productosporordenDto;
    }

    public void setProductosporordenDto(List<ProductoporordenDto> productosporordenDto) {
        this.productosporordenDto = productosporordenDto;
    }

    public List<ProductoporordenDto> getProductosporordenElimindosDto() {
        return productosporordenElimindosDto;
    }

    public void setProductosporordenElimindosDto(List<ProductoporordenDto> productosporordenElimindosDto) {
        this.productosporordenElimindosDto = productosporordenElimindosDto;
    }

    public List<FacturaDto> getFacturasDto() {
        return facturasDto;
    }

    public void setFacturasDto(List<FacturaDto> facturasDto) {
        this.facturasDto = facturasDto;
    }

    public List<FacturaDto> getFacturasEliminadasDto() {
        return facturasEliminadasDto;
    }

    public void setFacturasEliminadasDto(List<FacturaDto> facturasEliminadasDto) {
        this.facturasEliminadasDto = facturasEliminadasDto;
    }

    @Override
    public String toString() {
        return "OrdenDto{" + "idOrden=" + idOrden + ", fechaCreacion=" + fechaCreacion + ", nombreCliente=" + nombreCliente + ", esEstado=" + esEstado + ", idElementodeseccionDto=" + idElementodeseccionDto + ", idEmpleadoDto=" + idEmpleadoDto + ", modificado=" + modificado + ", productosporordenDto=" + productosporordenDto + ", productosporordenElimindosDto=" + productosporordenElimindosDto + ", facturasDto=" + facturasDto + ", facturasEliminadasDto=" + facturasEliminadasDto + '}';
    }
    
    

}

