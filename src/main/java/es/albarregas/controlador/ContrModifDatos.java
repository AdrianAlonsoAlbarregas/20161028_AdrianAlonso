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
import java.util.ArrayList;
import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Adrian
 */
@WebServlet(name = "ContrModifDatos", urlPatterns = {"/ContrModifDatos"})
public class ContrModifDatos extends HttpServlet {

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
// SE TE HA OLVIDADO ELIMINAR ESTA LÍNEA AL CREAR EL SERVLET
        try (PrintWriter out = response.getWriter()) {
            Connection conexion = null;
            PreparedStatement sentenciaPrep = null;
            Statement sentencia = null;
            ResultSet resultado = null;

            String sql = null;
// NO ENTIENDO PARA QUÉ OBTIENES LA LISTA ENTERA Y ADEMÁS SERÁ NULL
            ArrayList<Ave> lista = (ArrayList<Ave>) request.getAttribute("datos");
            String url = null;
            try {
                /*Si el usuario desea volver al menu el controlador lo manda de vuelta */
                if ("Menu".equals(request.getParameter("volver"))) {
                    url = "index.html";
                } else {
                    conexion = datasource.getConnection();
                    /*Si se desean actualizar datos, se recogen y se introducen en una sentencia sql para ejecutar
                    la actualizacion en la base de datos*/
                    if ("Continuar".equals(request.getParameter("continuar"))) {
                        try {
                            sql = "select * from aves where anilla = " + request.getParameter("seleccion");
                            sentencia = conexion.createStatement();
                            resultado = sentencia.executeQuery(sql);
                            resultado.next();
                            Ave ave = new Ave();
                            ave.setAnilla(resultado.getString("anilla"));
                            ave.setEspecie(resultado.getString("especie"));
                            ave.setLugar(resultado.getString("lugar"));
                            ave.setFecha(resultado.getString("fecha"));
                            request.setAttribute("actualizar", ave);
                            url = "jsp/actualizacion.jsp";
                        } catch (SQLException e) {
                            /*Se comprueba que ningun dato produzca error en la base de datos*/
                            url = "jsp/errores.jsp";
                            request.setAttribute("error", "datos");
                        } finally {
                            request.getRequestDispatcher(url).forward(request, response);
                        }
                    } else {
                        /*Si se desean eliminar datos, se recogen todas las entradas seleccionadas y se procede a
                        eliminarlas de la base de datos*/
// TIENES QUE PREGUNTAR SI ES CORRECTO ANTES DE ELIMINAR DEFININITIVAMENTE
                        String[] anillas = request.getParameterValues("selecciones");
                        sql = "delete from aves where anilla = ?";
                        try {
// PODIAS HABER CONSTRUIDO LA CLAUSULA WHERE
                            for (String i : anillas) {
                                sentenciaPrep = conexion.prepareStatement(sql);
                                sentenciaPrep.setString(1, i);
                                sentenciaPrep.executeUpdate();
                            }
                            url = "jsp/datosModificados.jsp";
                        } catch (SQLException e) {
                            /*Se comprueba que no se produzca ningun error durante el borrado de datos */
                            url = "jsp/errores.jsp";
                            request.setAttribute("error", "borrado");
                        }

                    }
                }
                /*Se comprueba que se haya seleccionado al menos una entrada para actualizar/eliminar, se comprueba que
                no se produzca ningun error en la conexion y se procede a cerrar la conexion y cargar la siguiente vista*/
            } catch (NullPointerException e) {
                url = "jsp/errores.jsp";
                request.setAttribute("error", "nulo");
            } catch (SQLException e) {
                url = "jsp/errores.jsp";
                request.setAttribute("error", "conexion");
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
