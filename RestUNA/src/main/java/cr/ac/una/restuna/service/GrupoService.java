/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.service;

//import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.GrupoDto;
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
 * @author jeez
 */
public class GrupoService {
    public Respuesta getGrupos() {
        try {
//            Map<String, Object> parametros = new HashMap<>();
//            parametros.put("id", id);
            Request request = new Request("GrupoController/getGrupos");
            request.get();
            
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
//            GrupoDto gruposDto = List<(GrupoDto)> request.readEntity(GrupoDto.class);
            List<GrupoDto> gruposDto = (List<GrupoDto>) request.readEntity( new GenericType<List<GrupoDto>>() {});
            return new Respuesta(true, "", "", "GruposList", gruposDto);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el listado de grupos", ex);
            return new Respuesta(false, "Error obteniendo listado de grupos.", "getGrupos " + ex.getMessage());
        }
    }
    
}
