/*
 * CoadunationHibernate: The hibernate configuration.
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
 * HibernateUtilException.java
 */

package com.rift.coad.hibernate.util;

/**
 * This exception will get thrown when an error is generated by the Hibernate
 * Utility libraries.
 *
 * @author Brett Chaldecott
 */
public class HibernateUtilException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>HibernateException</code> without detail
     * message.
     */
    public HibernateUtilException() {
    }
    
    
    /**
     * Constructs an instance of <code>HibernateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public HibernateUtilException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>HibernateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception.
     */
    public HibernateUtilException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
