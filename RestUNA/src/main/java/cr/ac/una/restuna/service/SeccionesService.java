package cr.ac.una.restuna.service;

import cr.ac.una.restuna.model.ProductoDto;
import cr.ac.una.restuna.model.SeccionDto;
import cr.ac.una.restuna.util.Request;
import cr.ac.una.restuna.util.Respuesta;
import jakarta.ws.rs.core.GenericType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeccionesService {

//    public Respuesta getProducto(Long id) {
//        try {
//            Map<String, Object> parametros = new HashMap<>();
//            parametros.put("id", id);
//            Request request = new Request("ProductoController/producto", "/{id}", parametros);
//            request.get();
//            if (request.isError()) {
//                return new Respuesta(false, request.getError(), "");
//
//            }
//            ProductoDto producto = (ProductoDto) request.readEntity(ProductoDto.class);
//            return new Respuesta(true, "", "", "Producto", producto);
//        } catch (Exception ex) {
//            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error obteniendo el producto [" + id + "]", ex);
//            return new Respuesta(false, "Error obteniendo el producto.", "getProducto " + ex.getMessage());
//        }
//    }
//
    public Respuesta guardarSeccion(SeccionDto seccionDto) {
        try {
            Request request = new Request("SeccionController/seccion");
            request.post(seccionDto);

            if (request.isError()) {
                return new Respuesta(false, "UPS!\nError de la aplicacion guardando la seccion.", "guardarSeccion " + request.getError());
            }
            SeccionDto seccionDtoGuardado = (SeccionDto) request.readEntity(SeccionDto.class);
            return new Respuesta(true, "", "", "Producto", seccionDtoGuardado);
        } catch (Exception ex) {
            Logger.getLogger(SeccionesService.class.getName()).log(Level.SEVERE, "Severo!\nError de la aplicacion guardando la seccion.", ex);
            return new Respuesta(false, "Error de la aplicacion guardando la seccion.", "guardarSeccion " + ex.getMessage() + ex.getLocalizedMessage());
        }
    }
//
//    public Respuesta eliminarProducto(Long id) {
//        try {
//            Map<String, Object> parametros = new HashMap<>();
//            parametros.put("id", id);
//            Request request = new Request("ProductoController/producto", "/{id}", parametros);
//            request.delete();
//            if (request.isError()) {
//                return new Respuesta(false, request.getError(), "");
//
//            }
//            ProductoDto productoDto = (ProductoDto) request.readEntity(ProductoDto.class);//
//            return new Respuesta(true, "", "", "Producto", productoDto);
//        } catch (Exception ex) {
//            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error eliminando el producto.", ex);
//            return new Respuesta(false, "Error eliminando el producto.", "eliminarProducto " + ex.getMessage());
//        }
//    }

    public Respuesta getSecciones() {
        try {
            Request request = new Request("SeccionController/secciones");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<SeccionDto> seccionDtos = (List<SeccionDto>) request.readEntity(new GenericType< List<SeccionDto>>() {});
            return new Respuesta(true, "", "", "SeccionesList", seccionDtos);
        } catch (Exception ex) {
            Logger.getLogger(SeccionesService.class.getName()).log(Level.SEVERE, "Error obteniendo la seccion.", ex);
            return new Respuesta(false, "Error obteniendo la seccion.", "getSecciones " + ex.getMessage());
        }
    }
}
