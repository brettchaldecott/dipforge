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
 * AuditTrailLoggerAsync.java
 */

package com.rift.coad.audit;

// the rmi remove interface.
import com.rift.coad.audit.dto.LogEntry;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface accepts audit trail log requests.
 *
 * @author brett chaldecott
 */
public interface AuditTrailLoggerAsync extends Remote {

    /**
     * This method takes a log object.
     *
     * @return The string containing the message service id.
     * @param entry The entry to store in the logs.
     * @throws RemoteException
     */
    public String logEvent(LogEntry entry) throws RemoteException;
}
