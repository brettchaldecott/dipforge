/*
 * DNSServer: The dns server implementation.
 * Copyright (C) 2008  2015 Burntjam
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
 * ServerTransferQueue.java
 */

// package path
package com.rift.coad.daemon.dns.server;

// java imports
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 * This object is responsbile for managing the list of secondary zones, and
 * making the necessary transfers for them.
 *
 * @author brett chaldecott
 */
public class ServerTransferQueue extends Thread {
    
    // The class constants
    private final static String TRANSFER_LOG_SIZE = "transfer_log_size";
    private final static int DEFAULT_TRANSFER_LOG_SIZE = 10;
    
    // static member variables
    private static Logger log = Logger.getLogger(ServerTransferQueue.class);
    
    // private member variables
    private List secondaryZones = new ArrayList();
    private ThreadStateMonitor monitor = new ThreadStateMonitor();
    private List transferErrors = new ArrayList();
    private int logSize = 0;
    private int totalErrors = 0;
    
    
    /**
     * Creates a new instance of ServerTransferQueue
     */
    public ServerTransferQueue() throws ServerException {
        try {
            Configuration centralConfig = ConfigurationFactory.getInstance().
                    getConfig(ServerTransferQueue.class);
            logSize = (int)centralConfig.getLong(TRANSFER_LOG_SIZE,
                    DEFAULT_TRANSFER_LOG_SIZE);
        } catch (Exception ex) {
            log.error("Failed to instanciate the server transfer queue : " +
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to instanciate the server transfer queue : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to performing the processing on the secondary
     * entries.
     */
    public void run() {
        while(!monitor.isTerminated()) {
            try {
                ServerTransfer zone = getNext();
                if (zone == null) {
                    // delay the processing indefinitly.
                    delayProcessing(0);
                    continue;
                } else if (!zone.requiresRefresh()) {
                    long time = zone.getTimeUntilRefresh();
                    if (time > 0) {
                        // deal with the race condition
                        delayProcessing(time);
                    }
                    continue;
                }
                try {
                    zone.performTransfer();
                } finally {
                    resort();
                }
            } catch (Throwable ex) {
                log.error("Failed to process : " + ex.getMessage(),ex);
                addTransferError(ex.getMessage());
            }
            
        }
    }
    
    
    /**
     * This method is called to terminate the processing of the server transfer
     * queue.
     */
    public void terminate() {
        monitor.terminate(true);
        synchronized(this) {
            notifyAll();
        }
    }
    
    
    /**
     * This method is called to add a zone to the queue.
     */
    public synchronized void addServerTransfer(ServerTransfer zone) throws
            ServerException {
        if (secondaryZones.contains(zone)) {
            log.error("The zone [" + zone.getZoneName() + "] is in the queue");
            throw new ServerException(
                    "The zone [" + zone.getZoneName() + "] is in the queue");
        }
        this.secondaryZones.add(zone);
        Collections.sort(secondaryZones);
        notifyAll();
        
    }
    
    
    /**
     * This method is called to remove a zone from the queue.
     */
    public synchronized void removeServerTransfer(ServerTransfer zone) throws
            ServerException {
        if (!secondaryZones.contains(zone)) {
            log.error("The zone [" + zone.getZoneName() + "] not in the queue");
            throw new ServerException(
                    "The zone [" + zone.getZoneName() + "] not in the queue");
        }
        secondaryZones.remove(zone);
        Collections.sort(secondaryZones);
        notifyAll();
    }
    
    
    /**
     * This method is called to retrieve the next entry for processing from the
     * queue.
     *
     * @return The zone to retrieve for processing.
     */
    private synchronized ServerTransfer getNext() {
        if (this.secondaryZones.size() == 0) {
            return null;
        }
        return (ServerTransfer)this.secondaryZones.iterator().next();
    }
    
    
    /**
     * This method is called to re-sort the zone entries.
     */
    private synchronized void resort() {
        Collections.sort(secondaryZones);
    }
    
    
    /**
     * This method is called to delay processing of zone tranfers.
     *
     * @param period The period to delay processing for.
     */
    private synchronized void delayProcessing(long period) {
        try {
            wait(period);
        } catch (Throwable ex) {
            log.error("Delay stopped because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds the transfer errors
     */
    private synchronized void addTransferError(String message) {
        transferErrors.add(message);
        totalErrors++;
        if (transferErrors.size() >= logSize) {
            transferErrors.remove(0);
        }
    }
    
    
    /**
     * This method returns the list of current transfer errors.
     *
     * @return The list of transfer errors.
     */
    public synchronized List getTransferErrors() {
        List listCopy = new ArrayList();
        listCopy.addAll(transferErrors);
        return listCopy;
    }
    
    
    /**
     * This method returns the number of secondary zones
     *
     * @return The number of secondary zones.
     */
    public synchronized int getNumberSecondaries() {
        return this.secondaryZones.size();
    }
    
    
    /**
     * This method returns the status of the server transfer queue.
     *
     * @return The status of the transfer queue.
     */
    public String getStatus() {
        StringBuffer buffer = new StringBuffer();
        List errors = getTransferErrors();
        buffer.append("Total Secondaries: ").append(getNumberSecondaries()).
                append("\nTotal Transfer Errors: ").
                append(this.transferErrors.size()).
                append("\nLast [").append(errors.size()).append("] errors:\n");
        for (int index = 0; index < errors.size(); index++) {
            buffer.append("\t").append(errors.get(index).toString()).
                    append("\n");
        }
        return buffer.toString();
    }
}
