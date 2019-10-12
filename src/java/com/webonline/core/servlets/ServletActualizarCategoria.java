package com.webonline.core.servlets;

import com.webonline.core.controller.CategoriaController;
import com.webonline.core.model.AccessToken;
import com.webonline.core.model.Categoria;
import com.webonline.core.model.CreatedResponse;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletActualizarCategoria extends HttpServlet {

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {

        RequestDispatcher despachador = null;
        String codigoCategoria = peticion.getParameter("codigoCategoria");
        String descripcion = peticion.getParameter("descripcion");
        HttpSession sesion = peticion.getSession();
        AccessToken token = (AccessToken) sesion.getAttribute("token");
        String url = this.getServletContext().getInitParameter("endPoint").concat("/categorias/").concat(codigoCategoria);
        CategoriaController categoriaController = new CategoriaController(url, token.getAccessToken());
        //Categoria categoria = categoriaController.search(Integer.parseInt(codigoCategoria));
        Categoria categoria = new Categoria();
        System.out.println("url act: "+url);
        System.out.println("cod cat act: " + codigoCategoria);
        categoria.setCodigoCategoria(Integer.parseInt(codigoCategoria));
        categoria.setDescripcion(descripcion);
        categoriaController.put(categoria);
        categoriaController.setEndPoint(this.getServletContext().getInitParameter("endPoint").concat("/categorias"));
        List<Categoria> lista = categoriaController.getCategorias();
        peticion.setAttribute("categorias", lista);
        despachador = peticion.getRequestDispatcher("categorias.jsp");
        despachador.forward(peticion, respuesta);

    }

    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        doPost(peticion, respuesta);
    }
}
