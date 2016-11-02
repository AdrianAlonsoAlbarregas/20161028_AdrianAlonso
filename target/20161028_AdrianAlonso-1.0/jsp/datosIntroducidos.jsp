<%-- 
    Document   : datosIntroducidos
    Created on : 30-oct-2016, 13:20:12
    Author     : Adrian
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href=<%request.getContextPath();%>"estilo.css">
        <title>Insertar exitoso</title>
    </head>
    <body>
        <h2>Los datos se han introducido en la base de datos.</h2>
        <a href=<%request.getContextPath();%>"ContrInsertar?volver=Menu"><button type="button">Menu</button></a>
    </body>
</html>
