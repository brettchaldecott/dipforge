/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * SecurityInterceptorException.java
 *
 * This exception will get thrown when there is an error while dealing with the
 * IIOP interceptors.
 */

package com.rift.coad.lib.interceptor.iiop;

/**
 * This exception will get thrown when there is an error while dealing with the
 * IIOP interceptors.
 *
 * @author mincemeat
 */
public class SecurityInterceptorException extends java.lang.RuntimeException {
    
    /**
     * Creates a new instance of <code>SecurityInterceptorException</code> without 
     * detail message.
     */
    public SecurityInterceptorException() {
    }
    
    
    /**
     * Constructs an instance of <code>SecurityInterceptorException</code> with the 
     * specified detail message.
     * 
     * 
     * 
     * @param msg the detail message.
     */
    public SecurityInterceptorException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>SecurityInterceptorException</code> with the 
     * specified detail message.
     * 
     * 
     * 
     * @param msg the detail message.
     * @param ex The exception stack.
     */
    public SecurityInterceptorException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
