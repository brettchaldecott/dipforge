/*
 * EMailServer: The email server implementation.
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
 * SMTPUtils.java
 */

// package path
package com.rift.coad.daemon.email.server.smtp;

// java imports
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * This class includes common util methods that are used by the SMTP Server
 * implementation.
 *
 * @author brett chaldecott
 */
public class SMTPUtils {
    
    /**
     * Creates a new instance of SMTPUtils
     */
    private SMTPUtils() {
    }
    
    
    /**
     * This method returns the date string.
     *
     * @return The string containing the current date.
     */
    public static String getDateString() {
        return new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(
                new Date());
    }
    
    
    
}
