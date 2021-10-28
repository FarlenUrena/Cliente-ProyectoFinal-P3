/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.service;

import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.model.ParametroDto;
import cr.ac.una.restuna.util.Request;
import cr.ac.una.restuna.util.Respuesta;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kendall
 */
public class ParametroService {   
    
      public Respuesta getParametro(Long id){
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id",id);
            Request request = new Request("/ParametroController/", "/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            ParametroDto par = (ParametroDto) request.readEntity(ParametroDto.class);
             return new Respuesta(true, "", "", "Parametro",par);
        }
        catch(Exception ex){
              Logger.getLogger(ParametroService.class.getName()).log(Level.SEVERE, "Error obteniendo el Parametro [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el parametro.", "getParametro " + ex.getMessage());
        }
}
      
public Respuesta guardarParametro(ParametroDto parametro) {
  try {
      Request request = new Request("ParametroController/parametro");
      request.post(parametro);
      if (request.isError()) {return new Respuesta(false, "Error guardando el parametro.", "guardarParametro " + request.getError());}
      ParametroDto parametroDto = (ParametroDto) request.readEntity(ParametroDto.class);
      return new Respuesta(true, "", "", "Parametro", parametroDto);
  } catch (Exception ex) {
      Logger.getLogger(ParametroService.class.getName()).log(Level.SEVERE, "Error guardando el parametro.", ex);
      return new Respuesta(false, "Error guardando el parametro.", "guardarParametro " + ex.getMessage() + ex.getLocalizedMessage());
  }
}

 public Respuesta eliminarParametro(Long id) {
      try {
        Request request = new Request("ParametroController/parametro");
        request.delete();
      if (request.isError()) {return new Respuesta(false, "Error eliminando el parametro.", "EliminarParametro " + request.getError());}
      ParametroDto parametroDto = (ParametroDto) request.readEntity(ParametroDto.class);
      return new Respuesta(true, "", "", "Parametro", parametroDto);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error eliminando el empleado.", ex);
            return new Respuesta(false, "Error eliminando el parametro.", "eliminarParametro" + ex.getMessage());
        }
    }


    
}
