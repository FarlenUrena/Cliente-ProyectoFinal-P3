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
    
    public Respuesta getGrupo(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("GrupoController/getGrupoId", "/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            GrupoDto grupo = (GrupoDto) request.readEntity(GrupoDto.class);
            return new Respuesta(true, "", "", "Grupo", grupo);
        } catch (Exception ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error obteniendo el grupo [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el grupo.", "getGrupo " + ex.getMessage());
        }
    }
    public Respuesta guardarGrupo(GrupoDto grupo) {
        try {
            Request request = new Request("GrupoController/grupo");
            request.post(grupo);
            
            if (request.isError()) {
                return new Respuesta(false, "Error guardando el grupo.", "guardarProducto " + request.getError());

            }
            GrupoDto grupoDto = (GrupoDto) request.readEntity(GrupoDto.class);//
            return new Respuesta(true, "", "", "Grupo", grupoDto);
        } catch (Exception ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error guardando el grupo.", ex);
            return new Respuesta(false, "Error guardando el grupo.", "guardarGrupo " + ex.getMessage() + ex.getLocalizedMessage());
        }
    }

    public Respuesta eliminarGrupo(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("GrupoController/grupo", "/{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            GrupoDto grupoDto = (GrupoDto) request.readEntity(GrupoDto.class);//
            return new Respuesta(true, "", "", "Grupo", grupoDto);
        } catch (Exception ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error eliminando el grupo.", ex);
            return new Respuesta(false, "Error eliminando el grupo.", "eliminarGrupo " + ex.getMessage());
        }
    }    
    public Respuesta getGrupos() {
        try {
            Request request = new Request("GrupoController/getGrupos");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            List<GrupoDto> gruposDto = (List<GrupoDto>) request.readEntity( new GenericType<List<GrupoDto>>() {});
            return new Respuesta(true, "", "", "GruposList", gruposDto);
        } catch (Exception ex) {
            Logger.getLogger(GrupoService.class.getName()).log(Level.SEVERE, "Error obteniendo el listado de grupos", ex);
            return new Respuesta(false, "Error obteniendo listado de grupos.", "getGrupos " + ex.getMessage());
        }
    }
    
}
