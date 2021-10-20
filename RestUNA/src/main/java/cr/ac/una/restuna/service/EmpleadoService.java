/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.service;

import cr.ac.una.restuna.model.EmpleadoDto;
import cr.ac.una.restuna.util.Request;
import cr.ac.una.restuna.util.Respuesta;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Farlen
 */
public class EmpleadoService {
    
    
        public Respuesta getUsuario(String usuario , String clave)
    {
        try
        {
            Map<String,Object> parametros = new HashMap<>();
            parametros.put("usuario", usuario);
            parametros.put("clave", clave);
            Request request = new Request("EmpleadoController/usuario","/{usuario}/{clave}",parametros);
            request.get/*Token*/();

            if(request.isError()){
                return new Respuesta(false,request.getError(),"");
            }
            
            EmpleadoDto empleado = (EmpleadoDto) request.readEntity(EmpleadoDto.class);
            return new Respuesta(true , "" , "" , "Empleado" , empleado);
        }
        catch(Exception ex)
        {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE , "Error obteniendo el usuario [" + usuario + "]" , ex);
            return new Respuesta(false , "Error obteniendo el usuario." , "getUsuario " + ex.getMessage());
        }
    }

    public Respuesta getEmpleado(Long id)
    {
        try
        {
            Map<String , Object> parametros = new HashMap<>();
            parametros.put("id" , id);
            Request request = new Request("EmpleadoController/empleado" , "/{id}" , parametros);
            request.get();
            if(request.isError())
            {
                return new Respuesta(false , request.getError() , "");

            }
            EmpleadoDto empleado = (EmpleadoDto) request.readEntity(EmpleadoDto.class);
            return new Respuesta(true , "" , "" , "Empleado" , empleado);
        }
        catch(Exception ex)
        {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE , "Error obteniendo el empleado [" + id + "]" , ex);
            return new Respuesta(false , "Error obteniendo el empleado." , "getEmpleado " + ex.getMessage());
        }
    }
    
        public Respuesta guardarEmpleado(EmpleadoDto empleado)
    {
        try
        {
            Request request = new Request("EmpleadoController/empleado");
            request.post(empleado);
            if(request.isError())
            {
                   return new Respuesta(false , "Error guardando el empleado." , "guardarEmpleado " + request.getError());
        

            }
            EmpleadoDto empleadoDto = (EmpleadoDto) request.readEntity(EmpleadoDto.class);//
            return new Respuesta(true , "" , "" , "Empleado" , empleadoDto);
        }
        catch(Exception ex)
        {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE , "Error guardando el empleado." , ex);
            return new Respuesta(false , "Error guardando el empleado." , "guardarEmpleado " + ex.getMessage()+ex.getLocalizedMessage());
        }
    }
        
            public Respuesta eliminarEmpleado(Long id){
        try{
            Map<String , Object> parametros = new HashMap<>();
            parametros.put("id" , id);
            Request request = new Request("EmpleadoController/empleado" , "/{id}" , parametros);
            request.delete();
            if(request.isError()){
                return new Respuesta(false , request.getError() , "");

            }
            EmpleadoDto empleadoDto = (EmpleadoDto) request.readEntity(EmpleadoDto.class);//
            return new Respuesta(true , "" , "" , "Empleado" , empleadoDto);
        }
        catch(Exception ex){
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE , "Error eliminando el empleado." , ex);
            return new Respuesta(false , "Error eliminando el empleado." , "eliminarEmpleado " + ex.getMessage());
        }
    }
}
