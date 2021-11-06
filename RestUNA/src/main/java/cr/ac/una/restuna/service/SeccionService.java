package cr.ac.una.restuna.service;

import cr.ac.una.restuna.model.SeccionDto;
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

public class SeccionService {

 public Respuesta getSeccion(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("SeccionController/seccion", "/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            SeccionDto seccion = (SeccionDto) request.readEntity(SeccionDto.class);
            return new Respuesta(true, "", "", "Seccion", seccion);
        } catch (Exception ex) {
            Logger.getLogger(SeccionService.class.getName()).log(Level.SEVERE, "Error obteniendo la seccion [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el seccion.", "getSeccion " + ex.getMessage());
        }
    }

    public Respuesta guardarSeccion(SeccionDto seccion) {
        try {
            Request request = new Request("SeccionController/seccion");
            request.post(seccion);
            
            if (request.isError()) {
                return new Respuesta(false, "Error guardando la seccion.", "guardarSeccion " + request.getError());

            }
            SeccionDto seccionDto = (SeccionDto) request.readEntity(SeccionDto.class);//
            
            return new Respuesta(true, "", "", "Seccion", seccionDto);
        } catch (Exception ex) {
            Logger.getLogger(SeccionService.class.getName()).log(Level.SEVERE, "Error guardando el seccion.", ex);
            return new Respuesta(false, "Error guardando la seccion.", "guardarSeccion " + ex.getMessage() + ex.getLocalizedMessage());
        }
    }

    public Respuesta eliminarSeccion(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id",id);
            Request request = new Request("SeccionController/seccion", "/{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            SeccionDto seccionDto = (SeccionDto) request.readEntity(SeccionDto.class);//
            return new Respuesta(true, "", "", "Seccion", seccionDto);
        } catch (Exception ex) {
            Logger.getLogger(SeccionService.class.getName()).log(Level.SEVERE, "Error eliminando la seccion.", ex);
            return new Respuesta(false, "Error eliminando la seccion.", "eliminarSeccion " + ex.getMessage());
        }
    }

    public Respuesta getSecciones() {
        try {
            Request request = new Request("SeccionController/secciones");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            List<SeccionDto> secciones = (List<SeccionDto>) request.readEntity( new GenericType< List<SeccionDto>>() {});
            return new Respuesta(true, "", "", "SeccionesList", secciones);
        } catch (Exception ex) {
            Logger.getLogger(SeccionService.class.getName()).log(Level.SEVERE, "Error obteniendo las secciones.", ex);
            return new Respuesta(false, "Error obteniendo las secciones.", "getSecciones " + ex.getMessage());
        }
    }
}
