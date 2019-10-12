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

public class ServletBuscarCategoria extends HttpServlet {

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        RequestDispatcher despachador = null;
        String codigoCategoria = peticion.getParameter("codigoCategoria");
        HttpSession session = peticion.getSession();
        AccessToken token = (AccessToken) session.getAttribute("token");
        String url = this.getServletContext().getInitParameter("endPoint")
                .concat("/categorias/").concat(codigoCategoria);
        CategoriaController categoriaController = new CategoriaController(url, token.getAccessToken());
        System.out.println("codigo buscar:" + codigoCategoria);
        Categoria categoria = categoriaController.search(Integer.parseInt(codigoCategoria));
        //Categoria categoria = categoriaController.search(codigoCategoria);
        peticion.setAttribute("categoria", categoria);
        despachador = peticion.getRequestDispatcher("createCategoria.jsp");
        despachador.forward(peticion, respuesta);

    }

    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        doPost(peticion, respuesta);
    }
}
