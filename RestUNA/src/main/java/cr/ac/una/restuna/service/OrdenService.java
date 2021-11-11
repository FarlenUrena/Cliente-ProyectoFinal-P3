/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.service;

import cr.ac.una.restuna.model.OrdenDto;
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
public class OrdenService {
    public Respuesta getOrden(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("OrdenController/orden", "/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            OrdenDto orden = (OrdenDto) request.readEntity(OrdenDto.class);
            return new Respuesta(true, "", "", "OrdenGuardada", orden);
        } catch (Exception ex) {
            Logger.getLogger(OrdenService.class.getName()).log(Level.SEVERE, "Error obteniendo la orden [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el orden.", "getOrden " + ex.getMessage());
        }
    }

    public Respuesta guardarOrden(OrdenDto orden) {
        try {
            Request request = new Request("OrdenController/orden");
            request.post(orden);
            
            if (request.isError()) {
                return new Respuesta(false, "Error guardando la orden.", "" + request.getError());

            }
            OrdenDto ordenDto = (OrdenDto) request.readEntity(OrdenDto.class);//
            return new Respuesta(true, "", "", "OrdenGuardada", ordenDto);
        } catch (Exception ex) {
            Logger.getLogger(OrdenService.class.getName()).log(Level.SEVERE, "Error guardando el orden.", ex);
            return new Respuesta(false, "Error guardando la orden.", "guardarOrden " + ex.getMessage() + ex.getLocalizedMessage());
        }
    }

    public Respuesta eliminarOrden(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id",id);
            Request request = new Request("OrdenController/orden", "/{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            OrdenDto ordenDto = (OrdenDto) request.readEntity(OrdenDto.class);//
            return new Respuesta(true, "", "", "Orden", ordenDto);
        } catch (Exception ex) {
            Logger.getLogger(OrdenService.class.getName()).log(Level.SEVERE, "Error eliminando la orden.", ex);
            return new Respuesta(false, "Error eliminando la orden.", "eliminarOrden " + ex.getMessage());
        }
    }

    public Respuesta getOrdenes() {
        try {
            Request request = new Request("OrdenController/ordenes");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            List<OrdenDto> ordenes = (List<OrdenDto>) request.readEntity( new GenericType< List<OrdenDto>>() {});
            return new Respuesta(true, "", "", "OrdenesList", ordenes);
        } catch (Exception ex) {
            Logger.getLogger(OrdenService.class.getName()).log(Level.SEVERE, "Error obteniendo las ordenes.", ex);
            return new Respuesta(false, "Error obteniendo las ordenes.", "getOrdenes " + ex.getMessage());
        }
    }
}
