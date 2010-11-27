/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2008  Rift IT Contracting
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
 * RequestInterface.java
 *
 * This interface supplies the request method. This is here for testing purposes.
 */

// package path
package com.rift.coad.lib.httpd;

// package path
import org.apache.http.protocol.HttpService;
import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.BasicHttpContext;

// logging import
import org.apache.log4j.Logger;


/**
 * This class is responsible for handling a request.
 *
 * @author Brett Chaldecott
 */
public class Request {
    
    // class static member variables
    private static Logger log = Logger.getLogger(Request.class);
    
    // private member variables
    private HttpService httpservice = null;
    private HttpServerConnection conn = null;
    
    
    /**
     * This method is called to process the connection request
     */
    public Request(HttpService httpservice, HttpServerConnection conn) {
        this.httpservice = httpservice;
        this.conn = conn;
    }
    
    
    /**
     * The handle request method placed in this interface for test reasons.
     */
    public void handleRequest() {
        HttpContext context = new BasicHttpContext(null);
        try {
            while (!Thread.interrupted() && this.conn.isOpen()) {
                this.httpservice.handleRequest(this.conn, context);
            }
        } catch (org.apache.http.ConnectionClosedException ex) {
            log.debug("The client connection is close : " +
                    ex.getMessage());
        } catch (java.net.SocketTimeoutException ex) {
            log.debug("The connection has timed out : " +
                    ex.getMessage());
        } catch (Throwable ex) {
            log.warn("Failed to process the request : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to destory the http request when finished.
     */
    public void destroy() {
        try {
            conn.shutdown();
        } catch (Exception ex) {
            log.error("Failed to shut down the connection : " + ex.getMessage(),
                    ex);
        }
    }
}
