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
 * NamingConstants.java
 *
 * This class contains the naming constants.
 */

package com.rift.coad.lib.naming;

/**
 * This class contains the naming constants.
 *
 * @author Brett Chaldecott
 */
public class NamingConstants {
    
    /**
     * The prefix of the java jndi.
     */
    public final static String SIMPLE_JAVA_JNDI_PREFIX = "java:";


    /**
     * The prefix of the java jndi.
     */
    public final static String JAVA_JNDI_PREFIX = "java:comp";
    
    /**
     * The base of a coadunation JNDI network url.
     */
    public final static String JNDI_NETWORK_PREFIX = "java:network";
    
    /**
     * The base of a coadunation JNDI network url.
     */
    public final static String JNDI_NETWORK_URL_BASE = "java:network/env";
    
    /**
     * The sub nameing contexts
     */
    public final static String SUBCONTEXT = "subcontext";
    
    /** Creates a new instance of NamingConstants */
    private NamingConstants() {
    }
    
}
