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
 * WebGroovyServlet.java
 */


package com.rift.dipforge.groovy.web;

import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.dipforge.groovy.lib.ContextInfo;
import com.rift.dipforge.groovy.lib.GroovyEnvironmentConstants;
import com.rift.dipforge.groovy.lib.GroovyEnvironmentException;
import com.rift.dipforge.groovy.lib.GroovyEnvironmentManager;
import com.rift.dipforge.groovy.lib.GroovyExecuter;
import com.rift.dipforge.groovy.lib.servlet.DipforgeServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import org.apache.log4j.Logger;
import org.codehaus.groovy.runtime.GroovyCategorySupport;



/**
 * The web groovy servlet.
 *
 * @author brett chaldecott
 */
public class WebGroovyServlet extends DipforgeServlet {

    // private member variables
    private static Logger log = Logger.getLogger(WebGroovyServlet.class);
    
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
                    WebGroovyServlet.class);

            GroovyEnvironmentManager.init(configuration.getString(
                    GroovyEnvironmentConstants.DIPFORGE_LIB_DIR),
                    configuration.getString(
                    GroovyEnvironmentConstants.ENVIRONMENT_BASE),
                    configuration.getString(
                    GroovyEnvironmentConstants.LIB_DIR),
                    configuration.getString(
                    GroovyEnvironmentConstants.ENVIRONMENT_SUB_DIRECTORIES).split(","),
                    configuration.getString(
                    GroovyEnvironmentConstants.ENVIRONMENT_LIBS_DIR).split(","));

        } catch (Exception ex) {
            log.error("Failed to setup the groovy environment :" + ex.getMessage(),ex);
            throw new ServletException
                    ("Failed to setup the groovy environment :" + ex.getMessage(),ex);
        }
    }


    /**
     * Handle web requests to the GroovyServlet
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Set it to HTML by default
        response.setContentType("text/html; charset="+encoding);

        // Run the script
        try {
            GroovyExecuter executer = GroovyEnvironmentManager.getInstance().getExecuter(new ContextInfo(request));
            executer.executeServletScript(request, response, this);
        } catch (GroovyEnvironmentException ex) {
            log.error("Failed to execute the call because : " + ex.getMessage(),ex);
        }
    }

}
