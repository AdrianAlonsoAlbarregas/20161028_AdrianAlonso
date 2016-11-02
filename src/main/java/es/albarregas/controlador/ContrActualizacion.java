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
public class ContrActualizacion extends HttpServlet {

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
                /*Se comprueba si se desea volver al menu */
                if ("Menu".equals(request.getParameter("volver"))) {
                    url = "index.html";
                } else if ("".equals(request.getParameter("especie")) || "".equals(request.getParameter("lugar")) || "".equals(request.getParameter("fecha"))) {
                    /*Si algun dato esta vacio, se manda al usuario a la pagina de errores*/
                    url = "jsp/errores.jsp";
                    request.setAttribute("error", "actNulo");
                } else {
                    conexion = datasource.getConnection();
                    sql = "update aves set especie = ?, lugar = ?, fecha = ? where anilla = ?";
                    try {
                        /*Si todo es correcto, se procede a cargar una sentencia de sql con los datos introducidos.
                        La anilla no se actualiza porque no es modificable, pero se utiliza para identificar la entrada
                        que se desea modificar*/
                        sentenciaPrep = conexion.prepareStatement(sql);
                        sentenciaPrep.setString(1, request.getParameter("especie"));
                        sentenciaPrep.setString(2, request.getParameter("lugar"));
                        sentenciaPrep.setString(3, request.getParameter("fecha"));
                        sentenciaPrep.setString(4, request.getParameter("anilla"));
                        sentenciaPrep.executeUpdate();
                        request.setAttribute("actualizacion", "correcta");
                        url = "jsp/datosModificados.jsp";
                    } catch (SQLException e) {
                        /*Se comprueba que todos los campos cumplan las restricciones de la base de datos */
                        url = "jsp/errores.jsp";
                        request.setAttribute("error", "datos");
                    }
                }
            } catch (SQLException e) {
                /*Se comprueba que no se produzcan errores en la conexion y que los datos no sean nulos.
                Despues se procede a cerrar la conexion y a cargar la siguiente vista*/
                url = "jsp/errores.jsp";
                request.setAttribute("error", "conexion");
            } catch (NullPointerException e) {
                url = "jsp/errores.jsp";
                request.setAttribute("error", "datos");
            } finally {
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
