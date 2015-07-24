/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2007  2015 Burntjam
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
 * BasicFormatter.java
 */

// package path
package com.rift.coad.lib.audit.basic;

// java imports
import java.text.SimpleDateFormat;

// log4j import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.audit.AuditTrailFormatter;
import com.rift.coad.lib.audit.AuditTrailEntry;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 * This object is reponsible for logging an audit trail entry using the standard
 * log4j objects.
 *
 * @author brett chaldecott
 */
public class BasicFormatter implements AuditTrailFormatter {
    
    // class constants
    private final static String DATE_FORMAT = "DateFormat";
    private final static String DEFAULT_DATE_FORMAT = 
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    
    // private member variables
    private Logger log = Logger.getLogger(BasicFormatter.class.getName());
    private SimpleDateFormat format = null;
    
    /**
     * Creates a new instance of BasicFormatter
     */
    public BasicFormatter() {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    BasicFormatter.class);
            format = new SimpleDateFormat(config.getString(DATE_FORMAT,
                    DEFAULT_DATE_FORMAT));
        } catch (Exception ex) {
            log.warn("Failed to retrieve the simple date format from the " +
                    "configuration defaulting to [" + DEFAULT_DATE_FORMAT + "]",
                    ex);
            format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        }
    }

    /**
     * This method is responsible for formatting an audit trail entry.
     *
     * @param entry The entry to add to the audit trail.
     */
    public void format(AuditTrailEntry entry) {
        if (entry.getCause() == null)
        {
            log.info(String.format("[%s][Success][%s][%s][%s]",
                    format.format(entry.getRecordTime()),entry.getUser(),
                    entry.getTarget(),entry.getMethod()));
        }
        else
        {
            log.info(String.format("[%s][Failure][%s][%s][%s][%s]",
                    format.format(entry.getRecordTime()),entry.getUser(),
                    entry.getTarget(),entry.getMethod(),
                    entry.getCause().getMessage()));
        }
    }
    
    
}
