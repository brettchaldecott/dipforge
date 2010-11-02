/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * WebServiceException.java
 *
 * The web service exception thrown when there is an error working with the
 * web services.
 */

// package path
package com.rift.coad.lib.webservice;

// imports
import com.rift.coad.lib.httpd.MimeTypes;

/**
 * The web service exception thrown when there is an error working with the web
 * services.
 *
 * @author Brett Chaldecott
 */
public class WebServiceException extends java.lang.Exception {
    
    private String encoding = MimeTypes.PLAIN;
    
    /**
     * Creates a new instance of <code>WebServiceException</code> without detail message.
     *
     * @param msg the detail message.
     */
    public WebServiceException(String msg) {
        super(msg);
    }
    
    
    /**
     * Creates a new instance of <code>WebServiceException</code> without detail message.
     *
     * @param msg the detail message.
     * @param encoding The encoding of the string
     */
    public WebServiceException(String msg, String encoding) {
        super(msg);
        this.encoding = encoding;
    }
    
    
    /**
     * Constructs an instance of <code>WebServiceException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception stack trace.
     */
    public WebServiceException(String msg,Throwable ex) {
        super(msg,ex);
    }
    
    
    /**
     * Constructs an instance of <code>WebServiceException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param encoding The encoding of the string
     * @param ex The exception stack trace.
     */
    public WebServiceException(String msg,String encoding,Throwable ex) {
        super(msg,ex);
        this.encoding = encoding;
    }
    
    
    /**
     * This method returns the encoding of the message.
     *
     * @return Encoding of the string message.
     */
    public String getEncoding() {
        return encoding;
    }
}
