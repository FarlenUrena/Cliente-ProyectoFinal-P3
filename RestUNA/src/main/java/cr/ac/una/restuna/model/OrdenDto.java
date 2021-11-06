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

    public SimpleStringProperty idOrden;
    public SimpleStringProperty nombreCliente;
    public ElementodeseccionDto idElemento;
    public List<ProductoporordenDto> productoporordenList;

    public List<FacturaDto> facturaList;  
    


  public Long getIdOrden() {
        if (idOrden.get() != null && !idOrden.get().isBlank()) {

            return Long.valueOf(idOrden.get());
        } else {
            return null;
        }
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

 

    public void setIdElemento(ElementodeseccionDto idElemento) {
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
