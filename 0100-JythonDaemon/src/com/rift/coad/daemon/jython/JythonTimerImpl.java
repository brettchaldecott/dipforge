/*
 * Timer: The timer class
 * Copyright (C) 2006-2007  Rift IT Contracting
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
 * JythonTimerImpl.java
 */

package com.rift.coad.daemon.jython;

import com.rift.coad.daemon.timer.TimerEventHandler;
import com.rift.coad.daemon.timer.TimerException;
import com.rift.coad.lib.configuration.ConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;
import org.python.util.PythonInterpreter;

/**
 *
 * @author Glynn Chaldecott
 */
public class JythonTimerImpl implements TimerEventHandler {
    
    protected Logger log =
            Logger.getLogger(JythonDaemonImpl.class.getName());
    
    public String scriptLocal = "";
    
    /** Creates a new instance of JythonTimerImpl */
    public JythonTimerImpl() throws Exception {
        try {
            com.rift.coad.lib.configuration.Configuration coadConfig =
                    com.rift.coad.lib.configuration.ConfigurationFactory.
                    getInstance().getConfig(com.rift.coad.daemon.jython.
                    JythonDaemonImpl.class);
            System.setProperty("python.home",
                    coadConfig.getString("python_home"));
            scriptLocal = coadConfig.getString("script_location");
        } catch (ConfigurationException ex) {
            log.error("Failed to set jython properties :" + ex.getMessage(),
                            ex);
            throw new Exception("Failed to set jython properties :" + ex);
        }
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
            String name = (String) serializable;
            File scriptFile = new File(scriptLocal,name);
            FileInputStream fis = new FileInputStream(scriptFile);
            PythonInterpreter inter = new PythonInterpreter();
            inter.execfile(fis);
        } catch (Throwable ex) {
            log.error("Failed to retrieve and run script:" + ex, ex);
        }
    }
    
}
