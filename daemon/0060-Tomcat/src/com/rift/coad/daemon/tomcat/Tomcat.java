/*
 * Tomcat: The interface for the tomcat daemon.
 * Copyright (C) 2007  Rift IT Contracting
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
 * TomcatImpl.java
 */


package com.rift.coad.daemon.tomcat;

/**
 * The implementation of the tomcat management interface.
 *
 * @author Brett Chaldecott
 */
public class Tomcat implements TomcatMBean {
    
    /**
     * Creates a new instance of TomcatImpl
     */
    public Tomcat() {
    }

    
    /**
     * This method returns the name of the tomcat daemon.
     */
    public String getName() {
        return "Apache Tomcat 6.0";
    }

    
    /**
     * This method returns the description of the tom cat daemon.
     */
    public String getDescription() {
        return "Apache Tomcat 6.0";
    }
    
    
    /**
     * This method returns the version of this daemon.
     */
    public String getVersion() {
        return "Version 1.1";
    }
        
}
