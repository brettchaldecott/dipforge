/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep;

import com.rift.coad.daemon.timer.TimerEventHandler;
import com.rift.coad.daemon.timer.TimerException;
import com.rift.coad.util.connection.ConnectionManager;
import java.io.Serializable;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 *
 * @author ubuntu
 */
public class PythonJepTimerImpl implements TimerEventHandler {
    
    protected Logger log =
            Logger.getLogger(PythonJepTimerImpl.class.getName());
    
    
    @Override
    public void processEvent(Serializable event) throws RemoteException, TimerException {
        try {
            PythonJepDaemon server = (PythonJepDaemon)ConnectionManager.getInstance().
                    getConnection(PythonJepDaemon.class, "python/Daemon");
            String info = event.toString();
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
