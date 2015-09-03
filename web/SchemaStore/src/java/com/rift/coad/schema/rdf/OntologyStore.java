/*
 * SchemaStore: The schema store implementation.
 * Copyright (C) 2011  2015 Burntjam
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
 * OntologyStore.java
 */

package com.rift.coad.schema.rdf;

import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.schema.util.DirectoryUtil;
import com.rift.coad.schema.util.XMLListGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The implementation of the ontology store
 *
 * @author brett chaldecott
 */
public class OntologyStore extends HttpServlet {


    // class constants
    private static final String SCHEMA_DIR = "schema_dir";

    // class singletons
    private static Logger log = Logger.getLogger(OntologyStore.class);

    // clas private member variables
    private String schemaDir;

    /**
     * The default constructor of the ontology store.
     */
    public OntologyStore() throws OntologyException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    OntologyStore.class);
            schemaDir = config.getString(SCHEMA_DIR);
        } catch (Exception ex) {
            log.error("Failed to retrieve the type server configuration information : " + ex.getMessage(),ex);
            throw new OntologyException
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
            String subPath = request.getPathInfo();
            if (request.getParameter("list") != null) {
                out.write(generateListResponse(request));
            } else {
                File rdfFile = new File(schemaDir,subPath + ".xml");
                FileInputStream in = new FileInputStream(rdfFile);
                byte[] buffer = new byte[(int)rdfFile.length()];
                in.read(buffer);
                in.close();
                out.print(new String(buffer));
            }
        } catch (Exception ex) {
            response.setStatus(response.SC_EXPECTATION_FAILED);
            response.setContentType("text/text;charset=UTF-8");
            out.println("Failed to retrieve the schema because : " + ex.getMessage());
            ex.printStackTrace(out);
            
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


    /**
     * This method is called to generate a list response.
     *
     * @param request The request information.
     * @return The return result.
     */
    private String generateListResponse(HttpServletRequest request) throws OntologyException {
        try {

            File schemaDirPath = new File(schemaDir);
            List<File> files =DirectoryUtil.recurseDirectory(schemaDirPath, ".xml");
            XMLListGenerator generator = new XMLListGenerator(
                    request.getRequestURL().toString().toLowerCase(),
                    schemaDirPath.getPath(), files,".xml");
            return generator.generateXMLList();
        } catch (Exception ex) {
            log.error("Failed generate the list of reponses : " + ex.getMessage());
            throw new OntologyException
                    ("Failed generate the list of reponses : " + ex.getMessage());
        }
    }
    
}
