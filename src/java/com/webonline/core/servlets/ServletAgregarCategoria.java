package com.webonline.core.servlets;

import com.webonline.core.controller.CategoriaController;
import com.webonline.core.model.AccessToken;
import com.webonline.core.model.Categoria;
import com.webonline.core.model.CreatedResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletAgregarCategoria extends HttpServlet {

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {

        RequestDispatcher despachador = null;
        HttpSession sesion = peticion.getSession();
        AccessToken token = (AccessToken) sesion.getAttribute("token");
        String url = this.getServletContext().getInitParameter("endPoint")
                .concat("/categorias");
        System.out.println("url post agregar: " + url);
        CategoriaController categoriaController = new CategoriaController(url, token.getAccessToken());
        Categoria categoria = new Categoria();
        categoria.setDescripcion(peticion.getParameter("descripcion"));
        System.out.println("descripcion agregar: " + categoria.getDescripcion());
        CreatedResponse response = categoriaController.post(categoria);
        if (response != null) {
            peticion.setAttribute("categorias", categoriaController.getCategorias());
            despachador = peticion.getRequestDispatcher("categorias.jsp");
            despachador.forward(peticion, respuesta);
        }else{
            despachador = peticion.getRequestDispatcher("createCategoria.jsp");
            despachador.forward(peticion, respuesta);
        }

    }

    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        doPost(peticion, respuesta);
    }
}
