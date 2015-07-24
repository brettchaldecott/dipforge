/*
 * AuditTrailServer: The audit trail server.
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

package com.rift.coad.audit.webservice;

/**
 * This exception is thrown when there is an error with the audit trail server.
 *
 * @author brett chaldecott
 */
public class AuditTrailException extends Exception implements java.io.Serializable {
    /**
     * The message for the caller.
     */
    public String message = null;

    /**
     * The cause of the exception.
     */
    public String cause = null;

}
