/*
 * GroovyWeb: The groovy web application
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
 * CoadunationGroovyServlet.java
 */

package com.rift.coad.web.groovy;

import groovy.lang.Closure;
import groovy.servlet.AbstractHttpServlet;
import groovy.servlet.ServletBinding;
import groovy.servlet.ServletCategory;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.groovy.runtime.GroovyCategorySupport;

/**
 * NOTE: This is a custom implementation of the GroovyServlet provided by the
 * groovy libraries to force the recompile of dependant classes.
 *
 * This servlet will run Groovy scripts as Groovlets.  Groovlets are scripts
 * with these objects implicit in their scope:
 *
 * <ul>
 *  <li>request - the HttpServletRequest</li>
 *  <li>response - the HttpServletResponse</li>
 *  <li>application - the ServletContext associated with the servlet</li>
 *  <li>session - the HttpSession associated with the HttpServletRequest</li>
 *  <li>out - the PrintWriter associated with the ServletRequest</li>
 * </ul>
 *
 * <p>Your script sources can be placed either in your web application's normal
 * web root (allows for subdirectories) or in /WEB-INF/groovy/* (also allows
 * subdirectories).
 *
 * <p>To make your web application more groovy, you must add the GroovyServlet
 * to your application's web.xml configuration using any mapping you like, so
 * long as it follows the pattern *.* (more on this below).  Here is the
 * web.xml entry:
 *
 * <pre>
 *    &lt;servlet>
 *      &lt;servlet-name>Groovy&lt;/servlet-name>
 *      &lt;servlet-class>groovy.servlet.GroovyServlet&lt;/servlet-class>
 *    &lt;/servlet>
 *
 *    &lt;servlet-mapping>
 *      &lt;servlet-name>Groovy&lt;/servlet-name>
 *      &lt;url-pattern>*.groovy&lt;/url-pattern>
 *      &lt;url-pattern>*.gdo&lt;/url-pattern>
 *    &lt;/servlet-mapping>
 * </pre>
 *
 * <p>The URL pattern does not require the "*.groovy" mapping.  You can, for
 * example, make it more Struts-like but groovy by making your mapping "*.gdo".
 *
 * @author Sam Pullara
 * @author Mark Turansky (markturansky at hotmail.com)
 * @author Guillaume Laforge
 * @author Christian Stein
 * @author Marcel Overdijk
 *
 * @see groovy.servlet.ServletBinding
 */
public class CoadunationGroovyServlet extends AbstractHttpServlet {

    /**
     * The script engine executing the Groovy scripts for this servlet
     */
    private GroovyScriptEngine gse;

    /**
     * Initialize the GroovyServlet.
     *
     * @throws ServletException
     *  if this method encountered difficulties
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Set up the scripting engine
        gse = createGroovyScriptEngine();
        // force the recompile of dependancy classes
        gse.getGroovyClassLoader().setShouldRecompile(true);

        
        servletContext.log("Groovy servlet initialized on " + gse + ".");
    }

    /**
     * Handle web requests to the GroovyServlet
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Get the script path from the request - include aware (GROOVY-815)
        final String scriptUri = getScriptUri(request);

        // Set it to HTML by default
        response.setContentType("text/html; charset="+encoding);

        // Set up the script context
        final ServletBinding binding = new ServletBinding(request, response, servletContext);
        setVariables(binding);

        // Run the script
        try {
            Closure closure = new Closure(gse) {

                public Object call() {
                    try {
                        return ((GroovyScriptEngine) getDelegate()).run(scriptUri, binding);
                    } catch (ResourceException e) {
                        throw new RuntimeException(e);
                    } catch (ScriptException e) {
                        throw new RuntimeException(e);
                    }
                }

            };
            GroovyCategorySupport.use(ServletCategory.class, closure);
            /*
             * Set reponse code 200.
             */
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (RuntimeException runtimeException) {
            StringBuffer error = new StringBuffer("GroovyServlet Error: ");
            error.append(" script: '");
            error.append(scriptUri);
            error.append("': ");
            Throwable e = runtimeException.getCause();
            /*
             * Null cause?!
             */
            if (e == null) {
                error.append(" Script processing failed.");
                error.append(runtimeException.getMessage());
                if (runtimeException.getStackTrace().length > 0)
                    error.append(runtimeException.getStackTrace()[0].toString());
                servletContext.log(error.toString());
                System.err.println(error.toString());
                runtimeException.printStackTrace(System.err);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, error.toString());
                return;
            }
            /*
             * Resource not found.
             */
            if (e instanceof ResourceException) {
                error.append(" Script not found, sending 404.");
                servletContext.log(error.toString());
                System.err.println(error.toString());
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            /*
             * Other internal error. Perhaps syntax?!
             */
            servletContext.log("An error occurred processing the request", runtimeException);
            error.append(e.getMessage());
            if (e.getStackTrace().length > 0)
                error.append(e.getStackTrace()[0].toString());
            servletContext.log(e.toString());
            System.err.println(e.toString());
            runtimeException.printStackTrace(System.err);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        } finally {
            /*
             * Finally, flush the response buffer.
             */
            response.flushBuffer();
            // servletContext.log("Flushed response buffer.");
        }
    }

    /**
     * Hook method to setup the GroovyScriptEngine to use.<br/>
     * Subclasses may override this method to provide a custom
     * engine.
     */
    protected GroovyScriptEngine createGroovyScriptEngine(){
        return new GroovyScriptEngine(this);
    }
}