/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.service;

import cr.ac.una.restuna.model.ProductoporordenDto;
import cr.ac.una.restuna.util.Request;
import cr.ac.una.restuna.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Farlen
 */
public class ProductoporordenService {
   public Respuesta getProductopororden(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ProductoporordenController/productopororden", "/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            ProductoporordenDto productopororden = (ProductoporordenDto) request.readEntity(ProductoporordenDto.class);
            return new Respuesta(true, "", "", "Productopororden", productopororden);
        } catch (Exception ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error obteniendo el productopororden [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el productopororden.", "getProductopororden " + ex.getMessage());
        }
    }
    public Respuesta guardarProductopororden(ProductoporordenDto productopororden) {
        try {
            Request request = new Request("ProductoporordenController/productopororden");
            request.post(productopororden);
            
            if (request.isError()) {
                return new Respuesta(false, "Error guardando el productopororden.", "guardarProducto " + request.getError());

            }
            ProductoporordenDto productoporordenDto = (ProductoporordenDto) request.readEntity(ProductoporordenDto.class);//
            return new Respuesta(true, "Productopororden guardado correctamente", "guardarProducto", "Productopororden", productoporordenDto);
        } catch (Exception ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error guardando el productopororden.", ex);
            return new Respuesta(false, "Error guardando el productopororden.", "guardarProductopororden " + ex.getMessage() + ex.getLocalizedMessage());
        }
    }

    public Respuesta eliminarProductopororden(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ProductoporordenController/productopororden", "/{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            ProductoporordenDto productoporordenDto = (ProductoporordenDto) request.readEntity(ProductoporordenDto.class);//
            return new Respuesta(true, "", "", "Productopororden", productoporordenDto);
        } catch (Exception ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error eliminando el productopororden.", ex);
            return new Respuesta(false, "Error eliminando el productopororden.", "eliminarProductopororden " + ex.getMessage());
        }
    }    
    public Respuesta getProductospororden() {
        try {
            Request request = new Request("ProductoporordenController/productospororden");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            List<ProductoporordenDto> productoporordensDto = (List<ProductoporordenDto>) request.readEntity( new GenericType<List<ProductoporordenDto>>() {});
            return new Respuesta(true, "", "", "ProductoporordensList", productoporordensDto);
        } catch (Exception ex) {
            Logger.getLogger(ProductoporordenService.class.getName()).log(Level.SEVERE, "Error obteniendo el listado de productoporordens", ex);
            return new Respuesta(false, "Error obteniendo listado de productoporordens.", "getProductoporordens " + ex.getMessage());
        }
    }
}