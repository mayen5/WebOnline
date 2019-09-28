package com.webonline.core.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webonline.core.model.AccessToken;
import com.webonline.core.model.Categoria;
import com.webonline.core.model.DeleteResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CategoriaController {

    private String endPoint;
    private String token;

    public CategoriaController(String endPoint, String token) {
        this.endPoint = endPoint;
        this.token = token;
    }

    public List<Categoria> getCategorias() throws MalformedURLException, IOException {
        List<Categoria> listaCategoria = null;
        URL url = new URL(this.endPoint);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");
        conexion.setRequestProperty("Accept", "application/json; charset=UTF-8");
        conexion.setRequestProperty("Authorization", "Bearer ".concat(this.token));
        if (conexion.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP 1.1 Error code "
                    .concat(String.valueOf(conexion.getResponseCode())));
        } else {
            InputStreamReader in = new InputStreamReader(conexion.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(in);
            String salida;
            Gson gson = new Gson();
            Type tipoCategorias = new TypeToken<List<Categoria>>() {
            }.getType();
            while ((salida = br.readLine()) != null) {
                listaCategoria = gson.fromJson(salida, tipoCategorias);
            }
            conexion.disconnect();

        }
        return listaCategoria;
    }

    public DeleteResponse eliminarCategoria() throws MalformedURLException, IOException {

        DeleteResponse response = null;
        URL url = new URL(this.endPoint);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("DELETE");
        conexion.setRequestProperty("Accept", "application/json; charset=UTF-8");
        conexion.setRequestProperty("Authorization", "Bearer ".concat(this.token));
        if (conexion.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP 1.1 Error code "
                    .concat(String.valueOf(conexion.getResponseCode())));
        } else {
            InputStreamReader in = new InputStreamReader(conexion.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(in);
            String salida = ""; 
            while ((salida = br.readLine()) != null) {
                response = new Gson().fromJson(salida, DeleteResponse.class);
            }            
        }
        return response;
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
