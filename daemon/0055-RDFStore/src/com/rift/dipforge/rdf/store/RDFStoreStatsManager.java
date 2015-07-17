/*
 * RDFStoreClient: The rdf store daemon client.
 * Copyright (C) 2011  Rift IT Contracting
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
 * RDFStoreStatsManager.java
 */


// package path
package com.rift.dipforge.rdf.store;

import com.rift.coad.util.change.ChangeLog;
import java.util.Date;

/**
 * The store status manager.
 *
 * @author brett chaldecott
 */
public class RDFStoreStatsManager {

    // class singleton
    private static RDFStoreStatsManager singleton = null;

    // private member variables
    private Date startTime;
    private int numberUpdates = 0;
    private Date lastUpdate = new Date();
    private int numberDeletes = 0;
    private Date lastDelete = new Date();


    /**
     * The private member variables
     */
    private RDFStoreStatsManager() {
        startTime = new Date();
    }


    /**
     * This method returns an instance of the stats manager.
     *
     * @return This method returns a reference to the stats manager.
     */
    public synchronized static RDFStoreStatsManager getInstance() {
        if (singleton == null) {
            singleton = new RDFStoreStatsManager();
        }
        return singleton;
    }


    /**
     * This method increments the update count
     */
    public synchronized void incrementUpdate() {
        numberUpdates++;
        lastUpdate = new Date();
    }


    /**
     * This method increments the delete count.
     */
    public synchronized void incrementDelete() {
        numberDeletes++;
        lastDelete = new Date();
    }


    /**
     * This method returns the stats
     *
     * @return The string containing the stats information.
     */
    public synchronized String getStats() {
        int size  = -1;
        try {
             size = ChangeLog.getInstance().getLogSize();
        } catch (Exception ex) {
            // ignore exception
        }
        return "Start Time:" + startTime.toString() + "\nNumber Updates: " +
                numberUpdates + "\nLast Update: " + lastUpdate.toString() +
                "\nNumber Deletes: " + numberDeletes +
                "\nLast Delete: " + lastDelete.toString() +
                "\nQueue Size: " + size;
    }
}
