/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.audit;

// the rmi remove interface.
import com.rift.coad.rdf.objmapping.audit.LogEntry;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface accepts audit trail log requests.
 *
 * @author brett chaldecott
 */
public interface AuditTrailLogger extends Remote {

    /**
     * This method takes a log object.
     *
     * @param entry The entry to store in the logs.
     * @throws RemoteException
     */
    public void logEvent(LogEntry entry) throws RemoteException;
}
