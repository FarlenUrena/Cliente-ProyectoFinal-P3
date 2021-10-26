/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.restuna.util;

import cr.ac.una.restuna.service.EmpleadoService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import java.io.StringReader;
import java.util.Base64;

/**
 *
 * @author ccarranza
 */
public class Request {

    private Client client;
    private Invocation.Builder builder;
    private WebTarget webTarget;
    private Response response;
    private static final String AUTHENTICATION_SCHEME = "Bearer ";

    public Request() {
        this.client = ClientBuilder.newClient();
    }

    public Request(String target) {
        this();
        setTarget(target);
    }

    public Request(String target, String parametros, Map<String, Object> valores) {
        this();
        this.webTarget = client.target(AppContext.getInstance().get("resturl") + target).path(parametros).resolveTemplates(valores);
        this.builder = webTarget.request(MediaType.APPLICATION_JSON);
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        if (AppContext.getInstance().get("Token") != null) {
            headers.add("Authorization", AppContext.getInstance().get("Token").toString());
        }
        builder.headers(headers);
    }

    /**
     * Ingresa el objetivo de la petición
     *
     * @param target Objetivo de la petición
     */
    public void setTarget(String target) {
        this.webTarget = client.target(AppContext.getInstance().get("resturl") + target);
        this.builder = webTarget.request(MediaType.APPLICATION_JSON);
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        if (AppContext.getInstance().get("Token") != null) {
            headers.add("Authorization", AppContext.getInstance().get("Token").toString());
        }
        builder.headers(headers);
    }

    public void setHeader(String nombre, Object valor) {
        builder.header(nombre, valor);
    }

    public void setHeader(MultivaluedMap<String, Object> valores) {
        valores.add("Content-Type", "application/json; charset=UTF-8");
        builder.headers(valores);
    }

    public void get() {
        if (verifyTokenExp()) {
            response = builder.get();
        }
    }

    public void post(Object clazz) {
        if (verifyTokenExp()) {
            Entity<?> entity = Entity.entity(clazz, "application/json; charset=UTF-8");
            response = builder.post(entity);

        }

    }

    public void put(Object clazz) {
        if (verifyTokenExp()) {
            Entity<?> entity = Entity.entity(clazz, "application/json; charset=UTF-8");
            response = builder.put(entity);

        }

    }

    public void delete() {
        if (verifyTokenExp()) {
            response = builder.delete();

        }
    }

    public int getStatus() {
        return response.getStatus();
    }

        public Boolean isError() {
        if (getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(4000);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                FlowController.getInstance().goLogInWindowModal(true);
                            }
                        });
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
        }
        return getStatus() != Response.Status.OK.getStatusCode();
    }

    public String getError() {
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            String mensaje;
            if (response.hasEntity()) {
                if (response.getMediaType().equals(MediaType.TEXT_PLAIN_TYPE)) {
                    mensaje = response.readEntity(String.class);
                } else if (response.getMediaType().getType().equals(MediaType.TEXT_HTML_TYPE.getType())
                        && response.getMediaType().getSubtype()
                                .equals(MediaType.TEXT_HTML_TYPE.getSubtype())) {
                    mensaje = response.readEntity(String.class);
                    mensaje = mensaje.substring(mensaje.indexOf("<b>message</b>") + ("<b>message</b>").length());
                    mensaje = mensaje.substring(0, mensaje.indexOf("</p>"));
                } else if (response.getMediaType().equals(MediaType.APPLICATION_JSON_TYPE)) {
                    mensaje = response.readEntity(String.class);
                } else {
                    mensaje = response.getStatusInfo().getReasonPhrase();
                }
            } else {
                mensaje = response.getStatusInfo().getReasonPhrase();
            }
            return mensaje;
        }
        return null;
    }

    private String readError() {
        String mensaje;
        if (response.hasEntity()) {
            if (response.getMediaType().equals(MediaType.TEXT_PLAIN_TYPE)) {
                mensaje = response.readEntity(String.class);
            } else if (response.getMediaType().getType().equals(MediaType.TEXT_HTML_TYPE.getType())
                    && response.getMediaType().getSubtype()
                            .equals(MediaType.TEXT_HTML_TYPE.getSubtype())) {
                mensaje = response.readEntity(String.class);
                mensaje = mensaje.substring(mensaje.indexOf("<b>message</b>") + ("<b>message</b>").length());
                mensaje = mensaje.substring(0, mensaje.indexOf("</p>"));
            } else if (response.getMediaType().equals(MediaType.APPLICATION_JSON_TYPE)) {
                mensaje = response.readEntity(String.class);
            } else {
                mensaje = response.getStatusInfo().getReasonPhrase();
            }
        } else {
            mensaje = response.getStatusInfo().getReasonPhrase();
        }
        return mensaje;
    }

    public Object readEntity(Class<?> clazz) {
        return response.readEntity(clazz);
    }

    public Object readEntity(GenericType<?> genericType) {
        return response.readEntity(genericType);
    }

    private boolean verifyTokenExp() {
        if (AppContext.getInstance().get("Token") == null || webTarget.getUri().getPath().contains("renovar") || webTarget.getUri().getPath().contains("usuario")) {
            return true; //no se ha logueado
        }
        JsonObject payload = getPayLoadToken(AppContext.getInstance().get("Token").toString());
        if (payload.getJsonNumber("exp").longValue() > System.currentTimeMillis() / 1000) {
            return true;
        } else {
            String tokenRenovacion = payload.getString("rnw");
            payload = getPayLoadToken(tokenRenovacion);
            if (payload != null && payload.getJsonNumber("exp").longValue() > System.currentTimeMillis() / 1000) {
                AppContext.getInstance().set("Token", tokenRenovacion);
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.renovarToken();
                if (respuesta.getEstado()) {
                    AppContext.getInstance().set("Token", respuesta.getResultado("Token").toString());
                    MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
                    headers.add("Authorization", respuesta.getResultado("Token").toString());
                    setHeader(headers);
                    return true;
                }
            }
            response = Response.status(401, "El token ha expirado.").build();
            return false;
        }

    }

    private JsonObject getPayLoadToken(String token) {
        if (token != null && !token.isBlank()) {
            token = token.substring(AUTHENTICATION_SCHEME.length()).trim();
            String[] part = token.split("\\.");
            String decodeToken = new String(Base64.getUrlDecoder().decode(part[1]));
            JsonObject object;
            JsonReader jsonReader = Json.createReader(new StringReader(decodeToken));
            object = jsonReader.readObject();
            return object;
        } else {
            return null;
        }
    }
}
