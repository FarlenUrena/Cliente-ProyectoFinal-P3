/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.service;

import cr.ac.una.restuna.model.CajaDto;
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
public class CajaService {
    public Respuesta getCaja(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("CajaController/caja", "/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            CajaDto caja = (CajaDto) request.readEntity(CajaDto.class);
            return new Respuesta(true, "", "", "Caja", caja);
        } catch (Exception ex) {
            Logger.getLogger(CajaService.class.getName()).log(Level.SEVERE, "Error obteniendo la caja [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el caja.", "getCaja " + ex.getMessage());
        }
    }

    public Respuesta guardarCaja(CajaDto caja) {
        try {
            Request request = new Request("CajaController/caja");
            request.post(caja);
            
            if (request.isError()) {
                return new Respuesta(false, "Error guardando la caja.", "guardarCaja " + request.getError());

            }
            CajaDto cajaDto = (CajaDto) request.readEntity(CajaDto.class);//
            
            return new Respuesta(true, "", "", "Caja", cajaDto);
        } catch (Exception ex) {
            Logger.getLogger(CajaService.class.getName()).log(Level.SEVERE, "Error guardando el caja.", ex);
            return new Respuesta(false, "Error guardando la caja.", "guardarCaja " + ex.getMessage() + ex.getLocalizedMessage());
        }
    }

    public Respuesta eliminarCaja(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id",id);
            Request request = new Request("CajaController/caja", "/{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            CajaDto cajaDto = (CajaDto) request.readEntity(CajaDto.class);//
            return new Respuesta(true, "", "", "Caja", cajaDto);
        } catch (Exception ex) {
            Logger.getLogger(CajaService.class.getName()).log(Level.SEVERE, "Error eliminando la caja.", ex);
            return new Respuesta(false, "Error eliminando la caja.", "eliminarCaja " + ex.getMessage());
        }
    }

    public Respuesta getCajas() {
        try {
            Request request = new Request("CajaController/cajas");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            List<CajaDto> cajas = (List<CajaDto>) request.readEntity( new GenericType< List<CajaDto>>() {});
            return new Respuesta(true, "", "", "CajasList", cajas);
        } catch (Exception ex) {
            Logger.getLogger(CajaService.class.getName()).log(Level.SEVERE, "Error obteniendo las cajas.", ex);
            return new Respuesta(false, "Error obteniendo las cajas.", "getCajaes " + ex.getMessage());
        }
    }
}
