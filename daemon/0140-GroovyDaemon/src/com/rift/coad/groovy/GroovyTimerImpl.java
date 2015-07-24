/*
 * GroovyDaemon: The groovy daemon.
 * Copyright (C) 2010  2015 Burntjam
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
 * GroovyTimerImpl.java
 */

package com.rift.coad.groovy;

import com.rift.coad.daemon.timer.TimerEventHandler;
import com.rift.coad.daemon.timer.TimerException;
import com.rift.coad.util.connection.ConnectionManager;
import java.io.Serializable;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 * This is the implementation of the groovy timer object.
 * 
 * @author Brett Chaldecott
 */
public class GroovyTimerImpl implements TimerEventHandler {
    
    protected Logger log =
            Logger.getLogger(GroovyTimerImpl.class.getName());
    
    /** Creates a new instance of JythonTimerImpl */
    public GroovyTimerImpl() throws Exception {
    }

    
    /**
     * This method implements the TimerEventHandler thus allowing a user to run
     * scripts using the Coadunation Timer Daemon.
     *
     * @param serializable This is the serializable event supplied to the Timer 
     *          Daemon and is used as the name of the script.
     */
    public void processEvent(Serializable serializable) throws RemoteException, 
            TimerException {
        try {
            GroovyDaemon server = (GroovyDaemon)ConnectionManager.getInstance().
                    getConnection(GroovyDaemon.class, "groovy/Daemon");
            String info = serializable.toString();
            String[] arguments = info.split(":");
            if (arguments.length == 2) {
                String result = server.execute(arguments[0], arguments[1]);
                log.info("Executed Script [" + info + "] result : " + result);
            } else {
                log.error("The requests is mell formed. Should be [project:path] got [" + info + "]");
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve and run the script:" + ex, ex);
        }
    }
    
}
