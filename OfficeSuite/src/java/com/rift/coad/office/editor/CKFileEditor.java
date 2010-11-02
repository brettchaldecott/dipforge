/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.office.editor;

import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.office.client.documents.DocumentFileSuffixLookup;
import com.rift.coad.office.server.documents.DocumentManagerImpl;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author brett
 */
public class CKFileEditor extends HttpServlet {

    // class singletons
    private static Logger log = Logger.getLogger(CKFileEditor.class);

    // private member variables
    private File base = null;
    private int number = 0;

    /**
     * The default constructor fo the php file editor
     */
    public CKFileEditor() throws Exception {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    DocumentManagerImpl.class);
            base = new File(config.getString(DocumentManagerImpl.INSTALLATION_BASE),
                    config.getString(DocumentManagerImpl.DOCUMENT_BASE));
        } catch (Exception ex) {
            log.error("Failed to instantiate the ck document editor : " +
                    ex.getMessage(),ex);
            throw new Exception
                    ("Failed to instantiate the ck document editor : " +
                    ex.getMessage(),ex);
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
            String scope = request.getParameter("scope");
            String fileName = request.getParameter("fileName");

            File path = new File(base,scope);

            File source = new File(path,fileName);
            java.io.FileInputStream in = new java.io.FileInputStream(source);
            byte[] buffer = new byte[(int)source.length()];
            in.read(buffer);
            in.close();
            String fileContents = new String(buffer);

            request.setAttribute("fileName", scope + "/" + fileName);
            request.setAttribute("fileContents", fileContents.replaceAll("[<]", "&lt;").
                    replaceAll("[>]", "&gt;"));
            String suffix = fileName.substring(fileName.indexOf(".") + 1);
            request.setAttribute("number", "" + (++number));

            request.setAttribute("type",
                        DocumentFileSuffixLookup.getTypeForSuffix(
                        suffix));
            
            RequestDispatcher requestDispatcher = getServletContext()
                .getRequestDispatcher("/editor/ckeditor.jsp");

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
            String fileName = request.getParameter("fileName");
            String fileContents = request.getParameter("fileContents");

            File source = new File(base,fileName);
            java.io.FileOutputStream out = new java.io.FileOutputStream(source);
            out.write(fileContents.getBytes());
            out.close();

            request.setAttribute("fileName", fileName);
            request.setAttribute("fileContents", fileContents);
            request.setAttribute("type", fileName.substring(fileName.indexOf(".") + 1));
            request.setAttribute("number", "" + (++number));



            RequestDispatcher requestDispatcher = getServletContext()
                .getRequestDispatcher("/editor/ckeditor.jsp");

            requestDispatcher.forward(request, response);

        } catch (Exception ex) {
            log.error("Failed to update the file : " + ex.getMessage(),ex);
            throw new ServletException
                ("Failed to update the file : " + ex.getMessage(),ex);
        }
    }
}
