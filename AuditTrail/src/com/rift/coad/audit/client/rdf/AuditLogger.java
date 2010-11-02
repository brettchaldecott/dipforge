/*
 * AuditTrail: The audit trail log object.
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
 * AuditLogger.java
 */


// package name space.
package com.rift.coad.audit.client.rdf;

// java imports
import com.rift.coad.audit.AuditTrailLogger;
import java.util.ArrayList;
import java.util.List;

// log4j object
import org.apache.log4j.Logger;

// coadunation message service
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;

// audit trail imports
import com.rift.coad.audit.AuditTrailLoggerAsync;
import com.rift.coad.rdf.objmapping.audit.LogEntry;
import com.rift.coad.audit.Constants;
import com.rift.coad.lib.security.SessionManager;

/**
 * This object is responsible for managing the audit trail log.
 *
 * @author brett chaldecott
 */
public class AuditLogger {

    // class singletons
    private static Logger log = Logger.getLogger(AuditLogger.class);

    // private member variables
    private String source;


    /**
     * This constructor is responsible for
     *
     * @param source The source of the audit trail log.
     */
    private AuditLogger(String source) {
        this.source = source;
    }

    /**
     * This method returns a reference to the logger object.
     *
     * @param source The source the log events are tied to.
     * @return The logger instance.
     */
    public static AuditLogger getLogger(String source) {
        return new AuditLogger(source);
    }

    /**
     * This method returns an instance of the audit trail logger object.
     *
     * @param source The source object.
     * @return The reference to the audit trail logger object.
     */
    public static AuditLogger getLogger(Class source) {
        return new AuditLogger(source.getName());
    }


    /**
     * This method returns a new audit trail object using the format mask and arguments.
     *
     * @param format The format mask for the request information.
     * @param args The arguments to replace in the format mask.
     * @return The reference to the newly created audit trail.
     */
    public AuditTrail create(String format, Object... args) {
        return new AuditTrail(source,String.format(format,args));
    }

}
