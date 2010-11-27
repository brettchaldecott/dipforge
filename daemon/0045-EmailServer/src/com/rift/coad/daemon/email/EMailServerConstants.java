/*
 * EMail: The email server
 * Copyright (C) 2008  Rift IT Contracting
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
 * EMailServerConstants.java
 */

// the package path
package com.rift.coad.daemon.email;

/**
 * This class contains all the constants for the email server.
 *
 * @author brett chaldecott
 */
public class EMailServerConstants {
    
    public final static String VERSION = "1";
    public final static String NAME = "EMail Server";
    public final static String DESCRIPTION = "Coadunation EMail Server";
    public final static String MAIL_DIR = "maildir";
    
    /**
     * Creates a new instance of EmailServerConstants
     */
    private EMailServerConstants() {
    }
    
}
