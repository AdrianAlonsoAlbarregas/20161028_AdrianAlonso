
<%--
    Document   : operaciones
    Created on : 29-oct-2016, 12:38:08
    Author     : Adrian
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="es.albarregas.modelo.Ave" %>
<%@page import="java.util.Iterator" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href=<%request.getContextPath();%>"estilo.css">
        <title>Operaciones</title>
    </head>
<%-- TIENES QUE MAQUETAR MEJOR EL CONTENIDO DE LA PÁGINA --%>
    <body>
        <div class="lista">
            <h2>Lista de entradas en la base de datos:</h2>
            <form action="ContrModifDatos" method="post">
                <%--Se crea un ArrayList con todas las entradas que haya en la base de datos --%>
                <%  ArrayList<Ave> lista = (ArrayList<Ave>) request.getAttribute("datos");
                    Iterator<Ave> it = lista.iterator();
                    /*Se comprueba si se quieren mostrar, actualizar o eliminar los datos*/
                    if ("mostrar".equals(request.getAttribute("operacion"))) {
                %>
                <ul>
                    <% while (it.hasNext()) {
                            Ave ave = it.next();
                    %><li><b>Anilla</b>: <%=ave.getAnilla()%>; <b>Especie</b>: <%=ave.getEspecie()%>; <b>Lugar</b>: <%=ave.getLugar()%>; <b>Fecha</b>: <%=ave.getFecha()%></li>
                    <% } %></ul><%
                    } else {
                        /*Si se quieren actualizar los datos, estos se muestran y se permite elegir uno y solo uno para
                        actualizarlo*/
                        if ("actualizar".equals(request.getAttribute("operacion"))) {
                    %><h3>Seleccione el registro que desea actualizar:</h3>
                <ul><%
// ESTAS REPITIENDO EL MISMO CÓDIGO DONDE LO ÚNICO QUE CAMBIA ES EL INPUT
                    while (it.hasNext()) {
                        Ave ave = it.next();
                    %><li><input type="radio" name="seleccion" value="<%=ave.getAnilla()%>">Anilla: <%=ave.getAnilla()%>; Especie: <%=ave.getEspecie()%></li>
                    <% } %></ul><%
                %><input type="submit" name="continuar" value="Continuar"><%
                    }
                    /*Si se desean eliminar datos, se muestran y se permiten elegir varios para hacer un borrado
                    multiple */
                    if ("eliminar".equals(request.getAttribute("operacion"))) {
                %><h3>Selecciones los registros que desea eliminar</h3>
                <ul><%
                    while (it.hasNext()) {
                        Ave ave = it.next();
                    %><li><input type="checkbox" name="selecciones" value="<%=ave.getAnilla()%>">Anilla: <%=ave.getAnilla()%>; Especie: <%=ave.getEspecie()%></li>
                    <% } %></ul><%
                %><input type="submit" name="eliminar" value="Eliminar seleccion"><%
                        }
                    }
                %><input type="submit" name="volver" value="Menu">
            </form>

        </div>
    </body>
</html>
