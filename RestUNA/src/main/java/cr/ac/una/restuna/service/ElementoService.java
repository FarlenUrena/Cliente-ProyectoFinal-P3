package cr.ac.una.restuna.service;

import cr.ac.una.restuna.model.ElementodeseccionDto;
import cr.ac.una.restuna.util.Request;
import cr.ac.una.restuna.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ElementoService {

    public Respuesta getElemento(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ElementodeseccionController/elementoDeSeccion", "/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            ElementodeseccionDto elemento = (ElementodeseccionDto) request.readEntity(ElementodeseccionDto.class);
            return new Respuesta(true, "", "", "Elemento", elemento);
        } catch (Exception ex) {
            Logger.getLogger(ElementoService.class.getName()).log(Level.SEVERE, "Error obteniendo el elemento [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el elemento.", "getElemento " + ex.getMessage());
        }
    }

    public Respuesta guardarElemento(ElementodeseccionDto elemento) {
        try {
            Request request = new Request("ElementodeseccionController/elementoDeSeccion");
            request.post(elemento);
            
            if (request.isError()) {
                return new Respuesta(false, "Error guardando el elemento.", "guardarElemento " + request.getError());

            }
            ElementodeseccionDto elementoDto = (ElementodeseccionDto) request.readEntity(ElementodeseccionDto.class);//
            return new Respuesta(true, "", "", "Elemento", elementoDto);
        } catch (Exception ex) {
            Logger.getLogger(ElementoService.class.getName()).log(Level.SEVERE, "Error guardando el elemento.", ex);
            return new Respuesta(false, "Error guardando el elemento.", "guardarElemento " + ex.getMessage() + ex.getLocalizedMessage());
        }
    }
    public Respuesta guardarElementos(List<ElementodeseccionDto> elementos) {
        try {
            Request request = new Request("ElementodeseccionController/elementosDeSeccion");
            request.post(elementos);
            
            if (request.isError()) {
                return new Respuesta(false, "Error guardando el elemento.", "guardarElemento " + request.getError());

            }
            List<ElementodeseccionDto> elementosDto = (List<ElementodeseccionDto>) request.readEntity(new GenericType< List<ElementodeseccionDto>>() {});//
            return new Respuesta(true, "", "", "ElementosActualizados", elementosDto);
        } catch (Exception ex) {
            Logger.getLogger(ElementoService.class.getName()).log(Level.SEVERE, "Error guardando el elemento.", ex);
            return new Respuesta(false, "Error guardando el elemento.", "guardarElemento " + ex.getMessage() + ex.getLocalizedMessage());
        }
    }

    public Respuesta eliminarElemento(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id",id);
            Request request = new Request("ElementodeseccionController/elementoDeSeccion", "/{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            ElementodeseccionDto elementoDto = (ElementodeseccionDto) request.readEntity(ElementodeseccionDto.class);//
            return new Respuesta(true, "", "", "Elemento", elementoDto);
        } catch (Exception ex) {
            Logger.getLogger(ElementoService.class.getName()).log(Level.SEVERE, "Error eliminando el elemento.", ex);
            return new Respuesta(false, "Error eliminando el elemento.", "eliminarElemento " + ex.getMessage());
        }
    }

    public Respuesta getElementos() {
        try {
            Request request = new Request("ElementodeseccionController/elementosDeSeccion");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            List<ElementodeseccionDto> elementos = (List<ElementodeseccionDto>) request.readEntity(new GenericType< List<ElementodeseccionDto>>() {});
            return new Respuesta(true, "", "", "ElementosList", elementos);
        } catch (Exception ex) {
            Logger.getLogger(ElementoService.class.getName()).log(Level.SEVERE, "Error obteniendo los elementos.", ex);
            return new Respuesta(false, "Error obteniendo el elemento.", "getElemento " + ex.getMessage());
        }
    }
}
