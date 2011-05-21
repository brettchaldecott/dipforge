/*
 * DipforgeWeb: Dipforge web environment
 * Copyright (C) 2011  Rift IT Contracting
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
 * MediaServlet.java
 */

package com.rift.dipforge.groovy.web.media;

import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.dipforge.groovy.lib.ContextInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The media servlet.
 *
 * @author brett chaldecott
 */
public class MediaServlet extends HttpServlet {

    // private member variables
    private static Logger log = Logger.getLogger(MediaServlet.class);

    // private member variables
    private String dipforgeLibDir;
    private String baseDir;
    private String webDir;



    /**
     * Initialize the GroovyServlet.
     *
     * @throws ServletException
     *  if this method encountered difficulties
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            Configuration configuration = ConfigurationFactory.getInstance().getConfig(
                    MediaServlet.class);
            dipforgeLibDir = configuration.getString(
                    MediaConstants.DIPFORGE_LIB_DIR);
            baseDir = configuration.getString(
                    MediaConstants.ENVIRONMENT_BASE);
            webDir = configuration.getString(
                    MediaConstants.WEB_DIRECTORY);

        } catch (Exception ex) {
            log.error("Failed to setup the groovy environment :" + ex.getMessage(),ex);
            throw new ServletException
                    ("Failed to setup the groovy environment :" + ex.getMessage(),ex);
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

        // workout the real path
        log.info("Request on [" + request.getContextPath() + "][" + request.getRequestURI() + "]");
        String servletSubPath = request.getContextPath();
        ContextInfo context = new ContextInfo(request);
        String subPath = context.stripContext(request.getRequestURI());

        File f = new File(this.baseDir + File.separator + context.getPath() +
                File.separator + this.webDir + File.separator + subPath);
        // fall back
        if (!f.isFile()) {
            f = new File(this.dipforgeLibDir +
                File.separator + this.webDir + File.separator + subPath);
        }
        String name = f.getName();

        String mimeType = getServletContext().getMimeType(name);

        response.setContentType(mimeType);

        response.setHeader(MediaConstants.CONTENT_DISPOSITION, "inline; filename=\"" + name
                + "\"");

        OutputStream out = response.getOutputStream();

        FileInputStream in = new FileInputStream(f);

        byte[] buf = new byte[512];
        int l;

        try
        {
            while ((l = in.read(buf)) > 0)
            {
                out.write(buf, 0, l);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            in.close();
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
