package com.webonline.core.servlets;

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
import java.net.URL;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletEliminarCategoria extends HttpServlet {

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        RequestDispatcher despachador = null;
        HttpSession sesion = peticion.getSession();
        AccessToken token = (AccessToken) sesion.getAttribute("token");
        String id = peticion.getParameter("codigoCategoria");
        URL url = new URL("http://localhost:9200/api/v1/categorias/".concat(id));
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("DELETE");
        conexion.setRequestProperty("Accept", "application/json; charset=UTF-8");
        conexion.setRequestProperty("Authorization", "Bearer ".concat(token.getAccessToken()));
        if (conexion.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP 1.1 Error code "
                    .concat(String.valueOf(conexion.getResponseCode())));
        } else {
            InputStreamReader in = new InputStreamReader(conexion.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(in);
            String salida = "";
            while ((salida = br.readLine()) != null) {
                DeleteResponse response = new Gson().fromJson(salida, DeleteResponse.class);
                peticion.setAttribute("response", response);
            }
            url = new URL("http://localhost:9200/api/v1/categorias");
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            respuesta.setCharacterEncoding("UTF-8");
            peticion.setCharacterEncoding("UTF-8");
            conexion.setRequestProperty("Accept", "application/json; charset=UTF-8");
            conexion.setRequestProperty("Authorization", "Bearer ".concat(token.getAccessToken()));
            if (conexion.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP 1.1 Error code "
                        .concat(String.valueOf(conexion.getResponseCode())));
            } else {
                in = new InputStreamReader(conexion.getInputStream(), "UTF-8");
                br = new BufferedReader(in);
                Type tipoCategorias = new TypeToken<List<Categoria>>() {
                }.getType();
                List<Categoria> listaCategoria = null;
                while ((salida = br.readLine()) != null) {
                    listaCategoria = new Gson().fromJson(salida, tipoCategorias);
                }
                peticion.setAttribute("categorias", listaCategoria);
            }
            conexion.disconnect();
            despachador = peticion.getRequestDispatcher("categorias.jsp");
            despachador.forward(peticion, respuesta);
        }
    }

    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        doPost(peticion, respuesta);
    }
}
