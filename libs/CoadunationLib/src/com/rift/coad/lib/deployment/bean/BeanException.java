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
 * XMLConfigurationException.java
 *
 * BeanException.java
 *
 * This exception will get thrown when there is an error dealing with the bean
 * loading.
 */

package com.rift.coad.lib.deployment.bean;

/**
 * This exception will get thrown when there is an error dealing with the bean
 * loading.
 *
 * @author Brett Chaldecott
 */
public class BeanException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>BeanException</code> without detail message.
     *
     * @param msg The message for the bean exception.
     */
    public BeanException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>BeanException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception stack
     */
    public BeanException(String msg,Throwable ex) {
        super(msg,ex);
    }
}
