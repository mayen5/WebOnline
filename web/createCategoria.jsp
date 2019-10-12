<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <jsp:include page="menu.jsp"></jsp:include>
        <body>
            <br>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="card border-primary text-center col-md-6">
                        <div class="card-header">
                        <c:if test="${empty categoria}">
                            <h3>Nueva Categoria</h3>
                        </c:if>
                            <c:if test="${not empty categoria}">
                            <h3>Modificar Categoria</h3>
                        </c:if>
                        </div>
                        <div class="card-body">
                            <c:if test="${empty categoria}">
                                <form method="post" action="ServletAgregarCategoria.do">
                                    <div class="form-group input-group flex-nowrap">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text bg-primary text-light" id="addon-wrapping">Descripción</span>
                                        </div>
                                        <input id="descripcion" name="descripcion" type="text" class="form-control" placeholder="Escriba la descripción" aria-label="descripcion" aria-describedby="addon-wrapping">
                                    </div>
                                    <br>
                                    <div class="text-center">
                                        <button type="submit" class="btn btn-success" >Guardar</button>
                                    </div>
                                    <br>
                                </form>
                            </c:if>
                            <c:if test="${not empty categoria}">
                                <form method="post" action="ServletActualizarCategoria.do?codigoCategoria=${categoria.codigoCategoria}">
                                    <div class="form-group input-group flex-nowrap">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text bg-primary text-light" id="addon-wrapping">Descripción</span>
                                        </div>
                                        <input id="descripcion" name="descripcion" value="${categoria.descripcion}" type="text" class="form-control" placeholder="Escriba la descripción" aria-label="descripcion" aria-describedby="addon-wrapping">
                                    </div>
                                    <br>
                                    <div class="text-center">
                                        <button type="submit" class="btn btn-success" >Guardar</button>
                                    </div>
                                    <br>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

        </body>
    </html>
