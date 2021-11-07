/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.service;

import cr.ac.una.restuna.model.ProductoDto;
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
public class ProductoService {

    public Respuesta getProducto(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ProductoController/producto", "/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            ProductoDto producto = (ProductoDto) request.readEntity(ProductoDto.class);
            return new Respuesta(true, "", "", "Producto", producto);
        } catch (Exception ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error obteniendo el producto [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el producto.", "getProducto " + ex.getMessage());
        }
    }

    public Respuesta guardarProducto(ProductoDto producto) {
        try {
            Request request = new Request("ProductoController/producto");
            request.post(producto);

            if (request.isError()) {
                return new Respuesta(false, "Error guardando el producto.", "guardarProducto " + request.getError());
            }
            ProductoDto productoDto = (ProductoDto) request.readEntity(ProductoDto.class);//
            return new Respuesta(true, "", "", "Producto", productoDto);
        } catch (Exception ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error guardando el producto.", ex);
            return new Respuesta(false, "Error guardando el producto.", "guardarProducto " + ex.getMessage() + ex.getLocalizedMessage());
        }
    }

    public Respuesta eliminarProducto(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ProductoController/producto", "/{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            ProductoDto productoDto = (ProductoDto) request.readEntity(ProductoDto.class);//
            return new Respuesta(true, "", "", "Producto", productoDto);
        } catch (Exception ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error eliminando el producto.", ex);
            return new Respuesta(false, "Error eliminando el producto.", "eliminarProducto " + ex.getMessage());
        }
    }

    public Respuesta getProductos() {
        try {
            Request request = new Request("ProductoController/productos");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");

            }
            List<ProductoDto> productos = (List<ProductoDto>) request.readEntity(new GenericType< List<ProductoDto>>() {
            });
            return new Respuesta(true, "", "", "ProductosList", productos);
        } catch (Exception ex) {
            Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, "Error obteniendo los productos.", ex);
            return new Respuesta(false, "Error obteniendo el producto.", "getProducto " + ex.getMessage());
        }
    }

}
