package com.webonline.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webonline.core.model.AccessToken;
import java.lang.reflect.Type;
import com.webonline.core.model.Categoria;
import javax.servlet.http.HttpSession;

public class ServletCategorias extends HttpServlet {

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        RequestDispatcher despachador = null;
        HttpSession session = peticion.getSession();
        AccessToken token = (AccessToken)session.getAttribute("token");
        URL url = new URL("http://localhost:9200/api/v1/categorias");
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");
        conexion.setRequestProperty("Accept", "application/json; charset=UTF-8");
        conexion.setRequestProperty("Authorization", "Bearer ".concat(token.getAccessToken()));
        respuesta.setCharacterEncoding("UTF-8");
        peticion.setCharacterEncoding("UTF-8");
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
            List<Categoria> listaCategoria = null;
            while ((salida = br.readLine()) != null) {
                listaCategoria = gson.fromJson(salida, tipoCategorias);
                //System.out.println("salida: " + salida);
                //System.out.println("listaCategoria: " + listaCategoria);
            }
            conexion.disconnect();
            peticion.setAttribute("categorias", listaCategoria);
            peticion.setAttribute("token", token);
            despachador = peticion.getRequestDispatcher("categorias.jsp");
        }
        despachador.forward(peticion, respuesta);
    }

    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        doPost(peticion, respuesta);
    }

}
