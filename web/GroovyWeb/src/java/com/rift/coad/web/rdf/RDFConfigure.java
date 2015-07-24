/*
 * GroovyDaemon: The groovy daemon
 * Copyright (C) 2009  2015 Burntjam
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * RDFConfigure.java
 */

// package path
package com.rift.coad.web.rdf;

// java imports
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// log4j imports
import org.apache.log4j.Logger;

// coadunation import
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;

/**
 * The implementation of the RDF 
 *
 * @author brett chaldecott
 */
public class RDFConfigure extends HttpServlet {

    // private member variables
    private static Logger log = Logger.getLogger(RDFConfigure.class);

    /**
     * The default constructor for the rdf configuration object.
     */
    public RDFConfigure() {
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
            log.error("The class could be : " + com.hp.hpl.jena.sdb.SDB.class.getName());
            SemanticUtil.closeInstance(RDFConfigure.class);
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
            out.println("<title>Servlet RDFConfigure</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RDFConfigure at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            out.close();
        }
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
        return "RDF Configuration";
    }// </editor-fold>

}
