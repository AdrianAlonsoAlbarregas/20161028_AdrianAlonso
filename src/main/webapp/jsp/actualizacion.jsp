<%-- 
    Document   : actualizacion
    Created on : 31-oct-2016, 16:45:50
    Author     : Adrian
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="es.albarregas.modelo.Ave" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href=<%request.getContextPath();%>"estilo.css">
        <title>Actualizar Datos</title>
    </head>
    <body>
        <% Ave ave = (Ave)request.getAttribute("actualizar"); %>
        <h3>Introduzca los nuevos datos:</h3>
        <form action="ContrActualizacion" method="post">
            <%--La anilla no es modificable--%>
            <p><b>Anilla: </b><%=ave.getAnilla()%>; <b>Especie: </b> <input type="text" name="especie" value="<%=ave.getEspecie()%>">;
                <b>Lugar de avistamiento:</b> <input type="text" name="lugar" value="<%=ave.getLugar()%>">; <b>Fecha de avistamiento:</b> <input type="text" name="fecha" value="<%=ave.getFecha()%>"></p>
            <input type="hidden" name="anilla" value="<%=ave.getAnilla()%>">
            <input type="submit" name="confirmar" value="Confirmar">
            <input type="submit" name="volver" value="Menu">
        </form>
    </body>
</html>
