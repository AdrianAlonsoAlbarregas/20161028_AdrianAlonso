/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.*;

/**
 *
 * @author Adrian
 */
public class ContrInsertar extends HttpServlet {

    DataSource datasource;

    @Override
    public void init() {
        try {
            Context initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("java:comp/env/jdbc/bdaves");
        } catch (NamingException e) {
            System.out.println("error de acceso");
            e.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Connection conexion = null;
            PreparedStatement sentenciaPrep = null;
            String sql = null;
            String url = null;
            try {
                /*Si el usuario desea volver al menu, el controlador lo manda de vuelta*/
                if ("Menu".equals(request.getParameter("volver"))) {
                    url = "index.html";
                }
                /*Se comprueba que no se haya pasado ningun dato vacio. Si se ha hecho, el controlador manda al usuario
                a la pagina de errores. si no, se procede a insertar los datos en la base de datos*/
                else if ("".equals(request.getParameter("especie")) || "".equals(request.getParameter("lugar")) || "".equals(request.getParameter("fecha"))) {
                    url = "jsp/errores.jsp";
                    request.setAttribute("error", "actNulo");
                } else {
                    conexion = datasource.getConnection();
                    String anilla = request.getParameter("anilla");
                    String especie = request.getParameter("especie");
                    String lugar = request.getParameter("lugar");
                    String fecha = request.getParameter("fecha");
                    sql = "INSERT INTO aves (anilla, especie, lugar, fecha) VALUES (?, ?, ?, ?);";
                    sentenciaPrep = conexion.prepareStatement(sql);
                    sentenciaPrep.setString(1, anilla);
                    sentenciaPrep.setString(2, especie);
                    sentenciaPrep.setString(3, lugar);
                    sentenciaPrep.setString(4, fecha);
                    try {
                        sentenciaPrep.executeUpdate();
                        url = "jsp/datosIntroducidos.jsp";

                    } catch (SQLException e) {
                        /*Se comprueba que los datos no produzcan nngun error en la base de datos (que la anilla este
                        repetida o que la longitud de uno de los campos sea mayor que la de la base de datos)*/
                        url = "jsp/errores.jsp";
                        request.setAttribute("error", "datos");
                    }
                }
            } catch (SQLException e) {
                /*Se comprueba si se han producido errores en la conexion */
                url = "jsp/errores.jsp";
                request.setAttribute("error", "conexion");
            } finally {
                /*Se cierra la conexion y se avanza a la siguiente vista */
                try {
                    if (conexion != null) {
                        conexion.close();
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
