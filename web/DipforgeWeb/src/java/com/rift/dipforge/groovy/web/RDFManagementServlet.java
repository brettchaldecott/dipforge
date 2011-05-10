/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.dipforge.groovy.web;

import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author brettc
 */
public class RDFManagementServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(RDFManagementServlet.class);

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RDFManagementServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RDFManagementServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            out.close();
        }
    }

    /**
     * This method is called to shut down the rdf configurtion object.
     */
    @Override
    public void destroy() {
        super.destroy();
        //ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            //Thread.currentThread().setContextClassLoader(
            //        com.hp.hpl.jena.sdb.store.Feature.class.getClassLoader());
            SemanticUtil.closeInstance(RDFManagementServlet.class);
        } catch (Exception ex) {
            log.error("Failed to destroy the groovy mapping manager : " + ex.getMessage(),ex);

        } finally {
            //Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }

    /**
     * This method is called to initialize the RDF configuration.
     *
     * @throws javax.servlet.ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
//        try {
//            SemanticUtil.getInstance(RDFConfigure.class);
//        } catch (Exception ex) {
//            log.error("Failed to instanciate the groovy mapping manager : " + ex.getMessage(),ex);
//
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
