/*
 * RDFTypeSource: The type configuration source.
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
 * RDFTypeServer.java
 */

// package path
package com.rift.coad.rdf.web;

// java imports
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// log4j imports
import org.apache.log4j.Logger;

// coadunation inmports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 * This object is responsible for serving up the rdf type information.
 *
 * @author brett chaldecott
 */
public class RDFTypeServer extends HttpServlet {
    
    // class constants
    private static final String TYPE_FILE = "type_file";
    
    // class singletons
    private static Logger log = Logger.getLogger(RDFTypeServer.class);

    // private member variables
    private String file;

    /**
     * The default constructor
     */
    public RDFTypeServer() throws RDFTypeException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    RDFTypeServer.class);
            file = config.getString(TYPE_FILE);
        } catch (Exception ex) {
            log.error("Failed to retrieve the type server configuration information : " + ex.getMessage(),ex);
            throw new RDFTypeException
                    ("Failed to retrieve the type server configuration information : " + ex.getMessage(),ex);
        }
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
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            File sourceFile = new File(this.file);
            if (sourceFile.isFile()) {
                FileInputStream in = new FileInputStream(sourceFile);
                byte[] buffer = new byte[(int)sourceFile.length()];
                in.read(buffer,0,buffer.length);
                String xml = new String(buffer,0,buffer.length);
                out.write(xml);
                in.close();
            } else {
                // if there is no file assume it is yet to be created and generate\
                // blank types file.
                out.write("<rdf:RDF\n" +
                    "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"+
                    "xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n"+
                    "<owl:AnnotationProperty rdf:about=\"http://thewebsemantic.com/javaclass\"/>\n"+
                    "</rdf:RDF>\n");
            }
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
        return "Short description";
    }// </editor-fold>

}
