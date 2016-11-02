<%-- 
    Document   : insertar
    Created on : 29-oct-2016, 12:36:32
    Author     : Adrian
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href=<%request.getContextPath();%>"estilo.css">
        <title>Insertar</title>
    </head>
    <body>
        <h3>Introduzca los datos que desea insertar en la base de datos:</h3>
        <form action="ContrInsertar" method="post">
            <p>Anilla: <input type="text" name="anilla">  
                Especie: <input type="text" name="especie">  
                Lugar de avistamiento: <input type="text" name="lugar"> 
                Fecha de avistamiento: <input type="text" name="fecha"></p>
            <input type="submit" name="insertar" value="Insertar">
            <input type="submit" name="volver" value="Menu">
        </form>
    </body>
</html>
