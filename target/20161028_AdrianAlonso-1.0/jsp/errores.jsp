<%-- 
    Document   : errores
    Created on : 29-oct-2016, 12:49:40
    Author     : Adrian
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href=<%request.getContextPath();%>"estilo.css">
        <title>Placeholder</title>
    </head>
    <body>
        <div id="mensaje">
            <h3>Se ha producido un error:</h3>
            <%--Se comprueba que tipo de error se ha detectado y se muestra un mensaje en consecuencia --%>
            <% if ("conexion".equals(request.getAttribute("error"))) {
            %><p>La conexion no se ha establecido correctamente</p><%
                }
                if ("datos".equals(request.getAttribute("error"))) {
            %><p>Los datos introducidos no son validos</p><%
                }
                if ("nulo".equals(request.getAttribute("error"))) {
            %><p>No se ha seleccionado ningun dato</p><%
                }
                if ("borrado".equals(request.getAttribute("error"))) {
            %><p>No se han podido borrar los datos</p><%
                }
                if ("actNulo".equals(request.getAttribute("error"))) {
            %><p>No se pueden dejar los campos en blanco</p><%
                }%>
            <a href=<%request.getContextPath();%>"ContrModifDatos?volver=Menu"><button type="button">Menu</button></a>
        </div>
    </body>
</html>
