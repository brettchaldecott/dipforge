/*
 * ChangeControlRDF: The rdf information for the change control system.
 * Copyright (C) 2009  Rift IT Contracting
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
 * Request.java
 */

// package path
package com.rift.coad.change.rdf.objmapping.change;

/**
 * The constants associated with a request.
 *
 * @author brett chaldecott
 */
public class RequestConstants {
    /**
     * This constants stats that a request is unprocesed
     */
    public final static String UNPROCESSED = "UNPROCESSED";

    /**
     * This status means that the action instance is currently running.
     */
    public final static String RUNNING = "RUNNING";

    /**
     * This means that the action instance has completed processing.
     */
    public final static String COMPLETE  = "COMPLETE";

    /**
     * This means that there was an error.
     */
    public final static String ERROR = "ERROR";
}
