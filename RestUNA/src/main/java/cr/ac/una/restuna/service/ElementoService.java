package cr.ac.una.restuna.service;

import cr.ac.una.restuna.model.ElementoDto;
import cr.ac.una.restuna.util.Request;
import cr.ac.una.restuna.util.Respuesta;
import jakarta.ws.rs.core.GenericType;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ElementoService {

    public Respuesta guardarElemento(ElementoDto elementoDto) {
        try {
            Request request = new Request("ElementodeseccionController/elementoDeSeccion");
            request.post(elementoDto);

            if (request.isError()) {
                return new Respuesta(false, "Error guardando el elemento.", "guardarElemento " + request.getError());
            }
            ElementoDto elemento = (ElementoDto) request.readEntity(ElementoDto.class);//
            return new Respuesta(true, "", "", "Elemento", elemento);
        } catch (Exception ex) {
            Logger.getLogger(ElementoService.class.getName()).log(Level.SEVERE, "Error guardando el elemento.", ex);
            return new Respuesta(false, "Error guardando el elemento.", "guardarElemento " + ex.getMessage() + ex.getLocalizedMessage());
        }
    }

    public Respuesta getElementos() {
        try {
            Request request = new Request("ElementodeseccionController/elementoDeSeccion");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            List<ElementoDto> elementosDto = (List<ElementoDto>) request.readEntity(new GenericType< List<ElementoDto>>() {});
            return new Respuesta(true, "", "", "ElemetosList", elementosDto);
        } catch (Exception ex) {
            Logger.getLogger(ElementoService.class.getName()).log(Level.SEVERE, "Error obteniendo los productos.", ex);
            return new Respuesta(false, "Error obteniendo el producto.", "getProducto " + ex.getMessage());
        }
    }
}
