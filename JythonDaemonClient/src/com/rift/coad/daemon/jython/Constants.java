/*
 * JythonDaemonClient: The client libraries for the jython data mapper.
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
 * Constants.java
 */

// package path
package com.rift.coad.daemon.jython;

/**
 * The class containing the constants.
 *
 * @author brett chaldecott
 */
public class Constants {

    /**
     * The name of the service.
     */
    public final static String SERVICE_ID = "jython_script";


    /**
     * The constant containing the data mapper jndi reference.
     */
    public final static String DATA_MAPPER = "jython/DataMapperManager";
}
