/*
 * DipforgeWeb: Dipforge web environment
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
 * WebGroovyServlet.java
 */

package com.rift.dipforge.groovy.web.template;

import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.dipforge.groovy.lib.ContextInfo;
import com.rift.dipforge.groovy.lib.GroovyEnvironmentException;
import com.rift.dipforge.groovy.lib.servlet.DipforgeServlet;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The dipforge gsp servlet.
 *
 * @author brett chaldecott
 */
public class DipforgeGSPServlet extends DipforgeServlet {

    // private member variables
    private static Logger log = Logger.getLogger(DipforgeGSPServlet.class);


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
                    DipforgeGSPServlet.class);

            GSPEnvironmentManager.init(configuration.getString(
                    GSPEnvironmentConstants.DIPFORGE_LIB_DIR),
                    configuration.getString(
                    GSPEnvironmentConstants.ENVIRONMENT_BASE),
                    configuration.getString(
                    GSPEnvironmentConstants.WEB_DIRECTORY),
                    configuration.getString(
                    GSPEnvironmentConstants.LIB_DIRECTORY),
                    configuration.getString(
                    GSPEnvironmentConstants.ENVIRONMENT_SUB_DIRECTORIES).split(","),
                    configuration.getString(
                    GSPEnvironmentConstants.ENVIRONMENT_LIBS_DIR).split(","));
            

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
            GSPExecuter executer = GSPEnvironmentManager.getInstance().getExecuter(new ContextInfo(request));
            executer.executeGSP(request, response, this);
        } catch (GSPEnvironmentException ex) {
            log.error("Failed to execute the call because : " + ex.getMessage(),ex);
        }
    }
}
