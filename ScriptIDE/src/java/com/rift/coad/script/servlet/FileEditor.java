/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
 * Copyright (C) 2009  Rift IT Contracting
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
 * FileEditor.java
 */


// package path
package com.rift.coad.script.servlet;

// java import
import com.rift.coad.script.broker.ScriptManagerDaemon;
import com.rift.coad.script.client.files.FileSuffixLookup;
import com.rift.coad.util.connection.ConnectionManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// log4j imports
import org.apache.log4j.Logger;

/**
 * This object contains the file editor.
 *
 * @author brett chaldecott
 */
public class FileEditor extends HttpServlet {

    // private member variables
    private static Logger log = Logger.getLogger(FileEditor.class);

    // private member variables
    private int number = 0;

    /**
     * The default constructor
     */
    public FileEditor() {
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

        // check if there is an action parameter set
        if (request.getParameter("action") == null) {
            processGetFile(request, response);
        } else if (request.getParameter("action").equals("save")) {
            processSaveFile(request, response);
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
     * This method is called to process the retrievel of the
     *
     * @param request The request object
     * @param response The response.
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void processGetFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ScriptManagerDaemon daemon = (ScriptManagerDaemon)
                    ConnectionManager.getInstance().getConnection(ScriptManagerDaemon.class,
                    "script/ManagementDaemon");
            String scope = request.getParameter("scope");
            String fileName = request.getParameter("fileName");
            String fileContents = daemon.getScript(scope, fileName);


            request.setAttribute("scope", scope);
            request.setAttribute("fileName", fileName);
            request.setAttribute("fileContents", fileContents);
            request.setAttribute("number", "" + (++number));

            String suffix = fileName.substring(fileName.indexOf(".") + 1);
            if (suffix.equals("property")) {
                // if it is a properties file there is no syntax rule in place
                // ignore
                request.setAttribute("type","");
            } else {
                request.setAttribute("type",
                        FileSuffixLookup.getTypeForSuffix(
                        suffix));
            }

            RequestDispatcher requestDispatcher = getServletContext()
                .getRequestDispatcher("/editor/editor.jsp");

            requestDispatcher.forward(request, response);

        } catch (Exception ex) {
            log.error("Failed to retrieve the file : " + ex.getMessage(),ex);
            throw new ServletException
                ("Failed to retrieve the file : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to save the file.
     *
     * @param request The request that is used to save the file.
     * @param response The response.
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void processSaveFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ScriptManagerDaemon daemon = (ScriptManagerDaemon)
                    ConnectionManager.getInstance().getConnection(ScriptManagerDaemon.class,
                    "script/ManagementDaemon");
            String scope = request.getParameter("scope");
            String fileName = request.getParameter("fileName");
            String fileContents = request.getParameter("fileContents");
            daemon.updateScript(scope, fileName,fileContents);


            request.setAttribute("scope", scope);
            request.setAttribute("fileName", fileName);
            request.setAttribute("fileContents", fileContents);
            request.setAttribute("type", fileName.substring(fileName.indexOf(".") + 1));
            request.setAttribute("number", "" + (++number));
            

            RequestDispatcher requestDispatcher = getServletContext()
                .getRequestDispatcher("/editor/editor.jsp");

            requestDispatcher.forward(request, response);

        } catch (Exception ex) {
            log.error("Failed to update the file : " + ex.getMessage(),ex);
            throw new ServletException
                ("Failed to update the file : " + ex.getMessage(),ex);
        }
    }
}
