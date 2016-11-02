/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controlador;

import es.albarregas.modelo.Ave;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Adrian
 */
public class ContrEnlaces extends HttpServlet {

    DataSource datasource;

    @Override
    public void init() {
        try {
            Context initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("java:comp/env/jdbc/bdaves");
        } catch (NamingException e) {
            System.out.println("Error de acceso");
            e.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Connection conexion = null;
            Statement sentencia = null;
            ResultSet resultado = null;
            Ave ave = null;
            ArrayList<Ave> lista = null;
            String sql = null;
            String url = null;
            try {
                conexion = datasource.getConnection();
                /*Se controla que opcion se ha escogido en el menu. si se ha elegido insertar, se muestra la pagina
                para insertar nuevos datos*/
                if ("Insertar".equals(request.getParameter("insertar"))) {
                    url = "jsp/insertar.jsp";
                } else {
                    /*Si se elige cualquier otra opcion que no sea insertar, se cargan todas las entradas de la base
                    de datos en una variable para mostrarlas en la siguiente vista*/
                    if ("Mostrar".equals(request.getParameter("mostrar"))) {
                        request.setAttribute("operacion", "mostrar");
                    }
                    if ("Actualizar".equals(request.getParameter("actualizar"))) {
                        request.setAttribute("operacion", "actualizar");
                    }
                    if ("Eliminar".equals(request.getParameter("eliminar"))) {
                        request.setAttribute("operacion", "eliminar");
                    }
                    sql = "select * from aves";
                    sentencia = conexion.createStatement();
                    resultado = sentencia.executeQuery(sql);
                    lista = new ArrayList();
                    url = "jsp/operaciones.jsp";
                    while (resultado.next()) {
                        ave = new Ave();
                        ave.setAnilla(resultado.getString("anilla"));
                        ave.setEspecie(resultado.getString("especie"));
                        ave.setLugar(resultado.getString("lugar"));
                        ave.setFecha(resultado.getString("fecha"));
                        lista.add(ave);
                    }
                    request.setAttribute("datos", lista);
                }
            } catch (SQLException e) {
                /*Se controla que no se produzca ningun error de conexion */
                url = "jsp/errores.jsp";
                request.setAttribute("error", "conexion");
            } finally {
                /*Se cierran las conexiones y se ordena al controlador cargar la vista que corresponda */
                try {
                    if (conexion != null) {
                        conexion.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    if (resultado != null) {
                        resultado.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
