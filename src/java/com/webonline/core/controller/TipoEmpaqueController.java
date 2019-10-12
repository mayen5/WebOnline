package com.webonline.core.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webonline.core.model.CreatedResponse;
import com.webonline.core.model.DeleteResponse;
import com.webonline.core.model.TipoEmpaque;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TipoEmpaqueController {
private final String CONTENT_TYPE = "application/json; charset=UTF-8";
    private final String AUTHORIZATION = "Bearer ";
    private final String ERROR_EXCEPTION = "Failed: HTTP 1.1 Error code ";
    private String endPoint;
    private String token;

    public TipoEmpaqueController(String endPoint, String token) {
        this.endPoint = endPoint;
        this.token = token;
    }

    public List<TipoEmpaque> getTiposEmpaque() throws MalformedURLException, IOException {
        List<TipoEmpaque> listaTipoEmpaque = null;
        URL url = new URL(this.endPoint);
        HttpURLConnection conexion = this.getConexion(url, "GET");
        if (conexion.getResponseCode() != 200) {
            throw new RuntimeException(ERROR_EXCEPTION
                    .concat(String.valueOf(conexion.getResponseCode())));
        } else {
            InputStreamReader in = new InputStreamReader(conexion.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(in);
            String salida;
            Gson gson = new Gson();
            Type tipoTipoEmpaque = new TypeToken<List<TipoEmpaque>>() {
            }.getType();
            while ((salida = br.readLine()) != null) {
                listaTipoEmpaque = gson.fromJson(salida, tipoTipoEmpaque);
            }
            conexion.disconnect();

        }
        return listaTipoEmpaque;
    }

    public TipoEmpaque getTipoEmpaque(int id) throws MalformedURLException, IOException {
        TipoEmpaque tipoEmpaque = null;
        URL url = new URL(this.endPoint.concat("/").concat(String.valueOf(id)));
        HttpURLConnection conexion = this.getConexion(url, "GET");
        if (conexion.getResponseCode() != 200) {
            throw new RuntimeException(ERROR_EXCEPTION
                    .concat(String.valueOf(conexion.getResponseCode())));
        } else {
            tipoEmpaque = (TipoEmpaque) this.unmarshalling(conexion, TipoEmpaque.class);
            conexion.disconnect();

        }
        return tipoEmpaque;
    }

    public DeleteResponse eliminarTipoEmpaque() throws MalformedURLException, IOException {

        DeleteResponse response = null;
        URL url = new URL(this.endPoint);
        HttpURLConnection conexion = this.getConexion(url, "DELETE");
        if (conexion.getResponseCode() != 200) {
            throw new RuntimeException(ERROR_EXCEPTION
                    .concat(String.valueOf(conexion.getResponseCode())));
        } else {
            response = (DeleteResponse) this.unmarshalling(conexion, DeleteResponse.class);
            conexion.disconnect();
        }
        return response;
    }

    public CreatedResponse post(TipoEmpaque tipoEmpaque) throws MalformedURLException, IOException {
        CreatedResponse response = null;
        URL url = new URL(this.endPoint);
        HttpURLConnection conexion = this.getConexion(url, "POST");
        conexion.setRequestProperty("Body", new Gson().toJson(tipoEmpaque));
        if (conexion.getResponseCode() != 201) {
            throw new RuntimeException(ERROR_EXCEPTION
                    .concat(String.valueOf(conexion.getResponseCode())));
        } else {
            response = (CreatedResponse) this.unmarshalling(conexion, CreatedResponse.class);
            conexion.disconnect();
        }

        return response;
    }

    public CreatedResponse put(TipoEmpaque tipoEmpaque)
            throws MalformedURLException, IOException {
        CreatedResponse response = null;
        URL url = new URL(this.endPoint);
        HttpURLConnection conexion = this.getConexion(url, "PUT");
        conexion.setRequestProperty("Body", new Gson().toJson(tipoEmpaque));
        if (conexion.getResponseCode() != 201) {
            throw new RuntimeException(ERROR_EXCEPTION
                    .concat(String.valueOf(conexion.getResponseCode())));
        } else {
            response = (CreatedResponse) this.unmarshalling(conexion, CreatedResponse.class);
            conexion.disconnect();
        }

        return response;
    }

    public Object unmarshalling(HttpURLConnection conexion, Class clase)
            throws MalformedURLException, IOException {

        Object respuesta = null;
        InputStreamReader in = new InputStreamReader(conexion.getInputStream(), "UTF-8");
        BufferedReader br = new BufferedReader(in);
        String salida;
        while ((salida = br.readLine()) != null) {
            switch (clase.getName()) {
                case "TipoEmpaque":
                    respuesta = new Gson().fromJson(salida, TipoEmpaque.class);
                    break;
                case "CreatedResponse":
                    respuesta = new Gson().fromJson(salida, CreatedResponse.class);
                    break;
                case "DeletedResponse":
                    respuesta = new Gson().fromJson(salida, DeleteResponse.class);
                    break;
                default:
                    break;
            }
        }
        return respuesta;
    }

    public HttpURLConnection getConexion(URL url, String method)
            throws MalformedURLException, IOException {

        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod(method);
        conexion.setRequestProperty("Accept", this.CONTENT_TYPE);
        conexion.setRequestProperty("Authorization", this.AUTHORIZATION.concat(this.token));

        return conexion;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
