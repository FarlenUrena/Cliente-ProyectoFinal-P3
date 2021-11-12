/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.service;

import cr.ac.una.restuna.model.FacturaDto;
import cr.ac.una.restuna.util.Request;
import cr.ac.una.restuna.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Farlen
 */
public class FacturaService {
    
  public Respuesta getFactura(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("FacturaController/factura", "/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            FacturaDto factura = (FacturaDto) request.readEntity(FacturaDto.class);
            return new Respuesta(true, "", "", "Factura", factura);
        } catch (Exception ex) {
            Logger.getLogger(FacturaService.class.getName()).log(Level.SEVERE, "Error obteniendo la factura [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el factura.", "getFactura " + ex.getMessage());
        }
    }

    public Respuesta guardarFactura(FacturaDto factura) {
        try {
            Request request = new Request("FacturaController/factura");
            request.post(factura);
            
            if (request.isError()) {
                return new Respuesta(false, "Error guardando la factura.", "guardarFactura " + request.getError());

            }
            FacturaDto facturaDto = (FacturaDto) request.readEntity(FacturaDto.class);//
            
            return new Respuesta(true, "", "", "Factura", facturaDto);
        } catch (Exception ex) {
            Logger.getLogger(FacturaService.class.getName()).log(Level.SEVERE, "Error guardando el factura.", ex);
            return new Respuesta(false, "Error guardando la factura.", "guardarFactura " + ex.getMessage() + ex.getLocalizedMessage());
        }
    }

    public Respuesta eliminarFactura(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id",id);
            Request request = new Request("FacturaController/factura", "/{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            FacturaDto facturaDto = (FacturaDto) request.readEntity(FacturaDto.class);//
            return new Respuesta(true, "", "", "Factura", facturaDto);
        } catch (Exception ex) {
            Logger.getLogger(FacturaService.class.getName()).log(Level.SEVERE, "Error eliminando la factura.", ex);
            return new Respuesta(false, "Error eliminando la factura.", "eliminarFactura " + ex.getMessage());
        }
    }

    public Respuesta getFacturaes() {
        try {
            Request request = new Request("FacturaController/facturas");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            List<FacturaDto> facturas = (List<FacturaDto>) request.readEntity( new GenericType< List<FacturaDto>>() {});
            return new Respuesta(true, "", "", "FacturaesList", facturas);
        } catch (Exception ex) {
            Logger.getLogger(FacturaService.class.getName()).log(Level.SEVERE, "Error obteniendo las facturas.", ex);
            return new Respuesta(false, "Error obteniendo las facturas.", "getFacturaes " + ex.getMessage());
        }
    }  
}
