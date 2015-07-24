/*
 * AuditTrail: The audit trail log object.
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
 * AuditTrailException.java
 */

// package path
package com.rift.coad.audit;

/**
 * The audit trail exception. This exception is thrown when there is a problem with
 * audit trail.
 * 
 * @author brett chaldecott
 */
public class AuditTrailException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>AuditTrailException</code> without detail message.
     */
    public AuditTrailException() {
    }


    /**
     * Constructs an instance of <code>AuditTrailException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public AuditTrailException(String msg) {
        super(msg);
    }

    /**
     * This exception is thrown when there is a problem with the audit trail.
     *
     * @param message The message to describing the cause.
     * @param cause The cause of this problem.
     */
    public AuditTrailException(String message, Throwable cause) {
        super(message, cause);
    }



}
