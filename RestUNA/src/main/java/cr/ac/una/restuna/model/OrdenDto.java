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
 * @author Kendall
 */
public class OrdenDto {
    private SimpleStringProperty idOrden;
    private SimpleStringProperty nombreCliente;
    private ElementoDto idElemento;
    private List<ProductoporordenDto> productoporordenList;
    private List<FacturaDto> facturaList;  
    
   public Long getIdOrden() {
        if(idOrden.get()!=null && !idOrden.get().isBlank())
            return Long.valueOf(idOrden.get());
        else return null;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden.set(idOrden.toString());
    }

    public String getNombreCliente() {
        return nombreCliente.get();
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente.set(nombreCliente);
    }

    public ElementoDto getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(ElementoDto idElemento) {
        this.idElemento = idElemento;
    }
    
    public List<ProductoporordenDto> getProductoporordenList() {
        return productoporordenList;
    }

    public void setProductoporordenList(List<ProductoporordenDto> productoporordenList) {
        this.productoporordenList = productoporordenList;
    }

    public List<FacturaDto> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<FacturaDto> facturaList) {
        this.facturaList = facturaList;
    }
}
