/*
 * GroovyLib: The goovy environment manager
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
 * GroovyEnvironmentManager.java
 */

package com.rift.dipforge.groovy.lib.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


/**
 * This is a re-imlementation of the groovy.servlet.AbstractHttpServlet. To work
 * around a library dependancy problem. It is a very crude approach to the
 * problem.
 *
 * @author brett chaldecott
 */
public abstract class DipforgeServlet extends HttpServlet {


    /**
     * Content type of the HTTP response.
     */
    public static final String CONTENT_TYPE_TEXT_HTML = "text/html";

    /**
     * Servlet API include key name: path_info
     */
    public static final String INC_PATH_INFO = "javax.servlet.include.path_info";

    /* *** Not used, yet. See comments in getScriptUri(HttpServletRequest). ***
     * Servlet API include key name: request_uri
     */
    public static final String INC_REQUEST_URI = "javax.servlet.include.request_uri";

    /**
     * Servlet API include key name: servlet_path
     */
    public static final String INC_SERVLET_PATH = "javax.servlet.include.servlet_path";

    /**
     * Servlet (or the web application) context.
     */
    protected ServletContext servletContext;

    /**
     * Either <code>null</code> or a compiled pattern matcher read from "{@code resource.name.regex}"
     * and used in {@link AbstractHttpServlet#getScriptUri(HttpServletRequest)}.
     */
    protected Matcher resourceNameMatcher;

    /**
     * The replacement used by the resource name matcher.
     */
    protected String resourceNameReplacement;

    /**
     * The replace method to use on the matcher.
     * <pre>
     * true - replaceAll(resourceNameReplacement); (default)
     * false - replaceFirst(resourceNameReplacement);
     * </pre>
     */
    protected boolean resourceNameReplaceAll;

    /**
     * Controls almost all log output.
     */
    protected boolean verbose;

    /**
     * Encoding to use, becomes charset part of contentType.
     */
    protected String encoding = "UTF-8";

    /**
     * Mirrors the static value of the reflection flag in MetaClass.
     * See AbstractHttpServlet#logGROOVY861
     */
    protected boolean reflection;

    /**
     * Debug flag logging the class the class loader of the request.
     */
    private boolean logGROOVY861;

    /**
     * Initializes all fields with default values.
     */
    public DipforgeServlet() {
        this.servletContext = null;
        this.resourceNameMatcher = null;
        this.resourceNameReplacement = null;
        this.resourceNameReplaceAll = true;
        this.verbose = false;
        this.reflection = false;
        this.logGROOVY861 = false;
    }

    private boolean isFile(URL ret) {
        return ret != null && ret.getProtocol().equals("file");
    }

    /**
     * Returns the include-aware uri of the script or template file.
     *
     * @param request the http request to analyze
     * @return the include-aware uri either parsed from request attributes or
     *         hints provided by the servlet container
     */
    public String getScriptUri(HttpServletRequest request) {
        /*
         * Log some debug information for http://jira.codehaus.org/browse/GROOVY-861
         */
        if (logGROOVY861) {
            log("Logging request class and its class loader:");
            log(" c = request.getClass() :\"" + request.getClass() + "\"");
            log(" l = c.getClassLoader() :\"" + request.getClass().getClassLoader() + "\"");
            log(" l.getClass()           :\"" + request.getClass().getClassLoader().getClass() + "\"");
            /*
             * Keep logging, if we're verbose. Else turn it off.
             */
            logGROOVY861 = verbose;
        }

        //
        // NOTE: This piece of code is heavily inspired by Apaches Jasper2!
        //
        // http://cvs.apache.org/viewcvs.cgi/jakarta-tomcat-jasper/jasper2/ \
        //        src/share/org/apache/jasper/servlet/JspServlet.java?view=markup
        //
        // Why doesn't it use request.getRequestURI() or INC_REQUEST_URI?
        //

        String uri = null;
        String info = null;

        //
        // Check to see if the requested script/template source file has been the
        // target of a RequestDispatcher.include().
        //
        uri = (String) request.getAttribute(INC_SERVLET_PATH);
        if (uri != null) {
            //
            // Requested script/template file has been target of
            // RequestDispatcher.include(). Its path is assembled from the relevant
            // javax.servlet.include.* request attributes and returned!
            //
            info = (String) request.getAttribute(INC_PATH_INFO);
            if (info != null) {
                uri += info;
            }
            return applyResourceNameMatcher(uri);
        }

        //
        // Requested script/template file has not been the target of a
        // RequestDispatcher.include(). Reconstruct its path from the request's
        // getServletPath() and getPathInfo() results.
        //
        uri = request.getServletPath();
        info = request.getPathInfo();
        if (info != null) {
            uri += info;
        }

        /*
         * TODO : Enable auto ".groovy" extension replacing here!
         * http://cvs.groovy.codehaus.org/viewrep/groovy/groovy/groovy-core/src/main/groovy/servlet/GroovyServlet.java?r=1.10#l259
         */

        return applyResourceNameMatcher(uri);
    }

    private String applyResourceNameMatcher(final String aUri) {
        /*
         * mangle resource name with the compiled pattern.
         */
        String uri = aUri;
        Matcher matcher = resourceNameMatcher;
        if (matcher != null) {
            matcher.reset(uri);
            String replaced;
            if (resourceNameReplaceAll) {
                replaced = matcher.replaceAll(resourceNameReplacement);
            } else {
                replaced = matcher.replaceFirst(resourceNameReplacement);
            }
            if (!uri.equals(replaced)) {
                if (verbose) {
                    log("Replaced resource name \"" + uri + "\" with \"" + replaced + "\".");
                }
                uri = replaced;
            }
        }
        return uri;
    }

    /**
     * Parses the http request for the real script or template source file.
     *
     * @param request the http request to analyze
     * @return a file object using an absolute file path name
     */
    protected File getScriptUriAsFile(HttpServletRequest request) {
        String uri = getScriptUri(request);
        String real = servletContext.getRealPath(uri);
        return new File(real).getAbsoluteFile();
    }

    /**
     * Overrides the generic init method to set some debug flags.
     *
     * @param config the servlet configuration provided by the container
     * @throws ServletException if init() method defined in super class
     *                          javax.servlet.GenericServlet throws it
     */
    public void init(ServletConfig config) throws ServletException {
        /*
         * Never forget super.init()!
         */
        super.init(config);

        /*
         * Grab the servlet context.
         */
        this.servletContext = config.getServletContext();

        // Get verbosity hint.
        String value = config.getInitParameter("verbose");
        if (value != null) {
            this.verbose = Boolean.valueOf(value);
        }

        // get encoding
        value = config.getInitParameter("encoding");
        if (value != null) {
            this.encoding = value;
        }

        // And now the real init work...
        if (verbose) {
            log("Parsing init parameters...");
        }

        String regex = config.getInitParameter("resource.name.regex");
        if (regex != null) {
            String replacement = config.getInitParameter("resource.name.replacement");
            if (replacement == null) {
                Exception npex = new NullPointerException("resource.name.replacement");
                String message = "Init-param 'resource.name.replacement' not specified!";
                log(message, npex);
                throw new ServletException(message, npex);
            }
            int flags = 0; // TODO : Parse pattern compile flags.
            this.resourceNameMatcher = Pattern.compile(regex, flags).matcher("");
            this.resourceNameReplacement = replacement;
            String all = config.getInitParameter("resource.name.replace.all");
            if (all != null) {
                this.resourceNameReplaceAll = Boolean.valueOf(all);
            }
        }

        value = config.getInitParameter("logGROOVY861");
        if (value != null) {
            this.logGROOVY861 = Boolean.valueOf(value);
            // nothing else to do here
        }

        /*
         * If verbose, log the parameter values.
         */
        if (verbose) {
            log("(Abstract) init done. Listing some parameter name/value pairs:");
            log("verbose = " + verbose); // this *is* verbose! ;)
            log("reflection = " + reflection);
            log("logGROOVY861 = " + logGROOVY861);
            if (resourceNameMatcher != null) {
                log("resource.name.regex = " + resourceNameMatcher.pattern().pattern());
            } else {
                log("resource.name.regex = null");
            }
            log("resource.name.replacement = " + resourceNameReplacement);
        }
    }


    /**
     * This method returns the encoding 
     * @return
     */
    public String getEncoding() {
        return this.encoding;
    }
}