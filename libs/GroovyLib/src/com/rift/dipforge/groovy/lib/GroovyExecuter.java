/*
 * GroovyLib: The goovy environment manager
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
 * GroovyEnvironmentManager.java
 */


package com.rift.dipforge.groovy.lib;

import com.rift.dipforge.groovy.lib.reflect.GroovyReflectionUtil;
import com.rift.dipforge.groovy.lib.servlet.DipforgeServlet;
import groovy.lang.Closure;
import groovy.servlet.AbstractHttpServlet;
import groovy.servlet.ServletBinding;
import groovy.util.ResourceException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The implementation of the groovy executer.
 *
 * @author brett chaldecott
 */
public class GroovyExecuter {

    // class singletons
    private Logger log = Logger.getLogger(GroovyExecuter.class);

    // private member variables
    private Object groovyScriptEngine;
    private GroovyClassLoader classLoader;

    /**
     * The constructor of the groovy script engine.
     *
     * @param groovyScriptEngine The script engine.
     * @param classLoader The class loader.
     */
    protected GroovyExecuter(Object groovyScriptEngine, GroovyClassLoader classLoader) {
        this.groovyScriptEngine = groovyScriptEngine;
        this.classLoader = classLoader;
    }


    /**
     * This method invokes a script in the groovy environment and returns the result.
     *
     * @param script The script path.
     * @param parameters The parameters for the script.
     * @return The result.
     * @throws com.rift.coad.groovy.BridgeException
     */
    public Object executeScript(String script, String[] parameterNames, Object[] parameters)
            throws GroovyEnvironmentException {
        if (parameterNames.length != parameters.length) {
            throw new GroovyEnvironmentException(
                    "Must supply the same number of parameters as parameter names.");
        }
        ClassLoader current = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object binding = classLoader.loadClass("groovy.lang.Binding").newInstance();
            // bind the parameters
            Method bindMethod = GroovyReflectionUtil.getMethod(binding,"setProperty", String.class,Object.class);
            for (int index = 0; index < parameters.length; index++) {
                String parameterName = parameterNames[index];
                Object parameter = parameters[index];
                log.info("Adding the bind parameter named : " + parameterName);
                bindMethod.invoke(binding, parameterName,parameter);
            }

            Method run = groovyScriptEngine.getClass().getMethod("run",
                    String.class,binding.getClass());
            return run.invoke(groovyScriptEngine, script,binding);
        } catch (InvocationTargetException invocationException) {
            Throwable ex = invocationException.getTargetException();
            log.error("Failed to execute the groovy script : " + ex.getMessage(),ex);
            throw new GroovyEnvironmentException
                    ("Failed to execute the groovy script : " + ex.getMessage());

        } catch (Throwable ex) {
            log.error("Failed to execute the groovy script : " + ex.getMessage(),ex);
            throw new GroovyEnvironmentException
                    ("Failed to execute the groovy script : " + ex.getMessage());
        } finally {
            // reset the class loader
            Thread.currentThread().setContextClassLoader(current);
        }
    }


    /**
     * This method is called to execute a script on behalf of a servlet.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @param servlet The servlet.
     * @throws GroovyEnvironmentException
     */
    public void executeServletScript(HttpServletRequest request,
            HttpServletResponse response, DipforgeServlet servlet)
            throws GroovyEnvironmentException, java.io.IOException {

        // Run the script
        ServletContext servletContext = servlet.getServletContext();
        ClassLoader current = Thread.currentThread().getContextClassLoader();

        // Get the script path from the request - include aware (GROOVY-815)
        String scriptUri = servlet.getScriptUri(request);

        try {
            Thread.currentThread().setContextClassLoader(classLoader);

            // Set it to HTML by default
            response.setContentType("text/html; charset="+servlet.getEncoding());

            // Set up the script context
            Class bindingClass = classLoader.loadClass("groovy.servlet.ServletBinding");
            Constructor bindingConstructor = bindingClass.getConstructor(
                    HttpServletRequest.class,HttpServletResponse.class,
                    ServletContext.class);
            Object binding = bindingConstructor.newInstance(request, response, servletContext);

            // setup the servlet boot strap environment
            Class bootstrapClass = classLoader.loadClass(
                    "com.rift.dipforge.groovy.bootstrap.BoostrapClosureWrapper");
            Constructor bootstrapConstructor = bootstrapClass.getConstructor(
                    groovyScriptEngine.getClass());
            Object bootstrap = bindingConstructor.newInstance(groovyScriptEngine);
            Method run = bootstrap.getClass().getMethod("run",
                        String.class,binding.getClass());
            run.invoke(scriptUri,binding);
            
            /*
             * Set reponse code 200.
             */
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (InvocationTargetException exception) {
            Throwable runtimeException = exception.getTargetException();
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
        }  catch (Throwable runtimeException) {
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

            // reset the class loader
            Thread.currentThread().setContextClassLoader(current);
        }
    }

}
