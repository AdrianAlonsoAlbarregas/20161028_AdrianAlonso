<%-- 
    Document   : datosModificados
    Created on : 31-oct-2016, 16:45:34
    Author     : Adrian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href=<%request.getContextPath();%>"estilo.css">
        <title>Datos Modificados</title>
    </head>
    <body>
        <% if("correcta".equals(request.getAttribute("actualizacion"))){ %>
        <h2>Los datos se han modificado correctamente</h2>
        <% }else{ %>
        <h2>Los datos se han eliminado exitosamente</h2>
        <% } %>
        <a href=<%request.getContextPath();%>"ContrModifDatos?volver=Menu"><button type="button">Menu</button></a>
    </body>
</html>
