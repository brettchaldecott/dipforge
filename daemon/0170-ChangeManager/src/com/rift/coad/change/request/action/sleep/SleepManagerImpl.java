/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2013  Rift IT Contracting
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
 * SleepManager.java
 */
package com.rift.coad.change.request.action.sleep;

// java imports
import com.rift.coad.change.request.action.ActionHandler;
import com.rift.coad.change.request.action.ActionHandlerAsync;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.lib.interceptor.credentials.Session;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

// dipforge imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.interceptor.InterceptorWrapper;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.util.change.ChangeLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;


/**
 * This bean is responsible for managing the sleeping actions.
 * 
 * @author brett chaldecott
 */
public class SleepManagerImpl extends InterceptorWrapper implements 
        SleepManager, BeanRunnable {

    /**
     * The dump directory configuration key
     */
    private final static String DUMP_DIR = "dump_dir";
    private final static String DUMP_FILE = "sleepingactions.dmp";
    
    // private class singletons
    private static Logger log = Logger.getLogger(SleepManagerImpl.class);
    
    // private member variables
    private SortedSet<SleepActionInfo> sleepSet = new 
            TreeSet<SleepActionInfo>(new SleepActionInfoComparator());
    private Map<String,SleepActionInfo> sleepingActions = 
            new ConcurrentHashMap<String,SleepActionInfo>();
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private String dumpDir;
    
    /**
     * The default constructor
     */
    public SleepManagerImpl() throws SleepManagerException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(SleepManagerImpl.class);
            dumpDir = config.getString(DUMP_DIR);
        } catch (Exception ex) {
            log.error("Failed to initialize the sleep manager because : " + 
                    ex.getMessage(),ex);
            throw new SleepManagerException(
                    "Failed to initialize the sleep manager because : " + 
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is called to add a new action.
     * 
     * @param actionInstanceId The id of the action that is sleeping.
     * @param period The period length.
     * @throws SleepManagerException 
     */
    public synchronized void addAction(String actionInstanceId, long period) throws SleepManagerException {
        try {
            SleepActionInfo sleepInfo = new SleepActionInfo(actionInstanceId,period);
            sleepSet.add(sleepInfo);
            sleepingActions.put(actionInstanceId, sleepInfo);
            state.notifyThread();
        } catch (Exception ex) {
            log.error("Failed to add the action : " + ex.getMessage(),ex);
            throw new SleepManagerException
                    ("Failed to add the action : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * The list of actions that is sleeping.
     * 
     * @return This method is used to return a list of actions that are sleeping.
     * @throws SleepManagerException 
     */
    public List<String> listSleepingActions() throws SleepManagerException {
        List<String> entries = new ArrayList<String>();
        entries.addAll(this.sleepingActions.keySet());
        return entries;
    }

    
    /**
     * This method returns the sleeping period for the given action instance.
     * 
     * @param actionInstanceId The identifier of the action.
     * @return The period that the action instance is sleeping for.
     * @throws SleepManagerException 
     */
    public synchronized long getActionInstanceSleepPeriod(
            String actionInstanceId) throws SleepManagerException {
        if (sleepingActions.containsKey(actionInstanceId)) {
            return sleepingActions.get(actionInstanceId).getPeriod();
        }
        return -1;
    }

    
    /**
     * This method is called by the container and is the equivalent of run
     */
    public void process() {
        
        // wait for the deployment process to stop.
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        
        loadDump();
        
        try {
            while(!state.isTerminated()) {
                SleepActionInfo info = null;
                synchronized(this) {
                    if (!this.sleepSet.isEmpty()) {
                        info = this.sleepSet.first();
                    }
                }
                if (info == null) {
                    state.monitor(0);
                } else {
                    Date current = new Date();
                    long sleepPeriod = (info.getStart().getTime() + info.getPeriod()) - 
                            current.getTime();
                    if (sleepPeriod <= 0) {
                        resumeAction(info);
                    } else {
                        state.monitor(sleepPeriod);
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Failed to process the actions : " + ex.getMessage(),ex);
        }
        
        createDumpFile();
    }

    
    /**
     * This method is called to terminate the processing of the sleep manager.
     */
    public void terminate() {
        this.state.terminate(true);
    }
    
    
    /**
     * This method is called to resume the action.
     * @param info 
     */
    private void resumeAction(SleepActionInfo info) {
        try {
            initUserSession(info);
            ActionHandlerAsync handler = (ActionHandlerAsync)RPCMessageClient.
                        createOneWay("change/request/RequestFactoryDaemon", ActionHandler.class,
                    ActionHandlerAsync.class, "change/request/action/ActionHandler");
            handler.resumeAction(info.getActionInstanceId());
            // remove the entries from the set and sleep action map.
            synchronized(this) {
                this.sleepSet.remove(info);
                this.sleepingActions.remove(info.getActionInstanceId());
            }
            
        } catch (Exception ex) {
            log.error("Failed to resume the action : " + ex.getMessage(),ex);
            log.error("Rescheduling action [" + info.getActionInstanceId() + "]");
            synchronized(this) {
                this.sleepSet.remove(info);
                info.setStart(new Date());
                this.sleepSet.add(info);
            }
        } finally {
            releaseUserSession();
        }
    }
    
    /**
     * This method is called to load the dump file.
     */
    private void loadDump() {
        try {
            File dataFile = new File(this.dumpDir,DUMP_FILE);
            if (!dataFile.exists()) {
                return;
            }
            FileInputStream in = new FileInputStream(dataFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            sleepSet = (SortedSet<SleepActionInfo>)objectInputStream.readObject();
            objectInputStream.close();
            in.close();
            for (SleepActionInfo info : sleepSet) {
                this.sleepingActions.put(info.getActionInstanceId(), info);
            }
            dataFile.delete();
        } catch (Exception ex) {
            log.error("Failed to load the dump file : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to create the dump file
     */
    private void createDumpFile() {
        try {
            if (this.sleepSet.size() == 0) {
                return;
            }
            File dataFile = new File(this.dumpDir,DUMP_FILE);
            if (dataFile.exists()) {
                dataFile.delete();
            }
            FileOutputStream out = new FileOutputStream(dataFile);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(this.sleepSet);
            oos.close();
            out.close();
        } catch (Exception ex) {
            log.error("Failed to create the dump file : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for initializing the user session.
     *
     * @param info The info about the sleeping session.
     * @exception SleepManagerException
     */
    private void initUserSession(SleepActionInfo info) throws SleepManagerException {
        try {
            Session session = new Session(info.getCreator(),
                    info.getSessionId(),
                    new HashSet(info.getPrincipals()));
            getServerInterceptor().createSession(session);
        } catch (Exception ex) {
            log.error("Failed to setup the user session : " +
                    ex.getMessage(),ex);
            throw new SleepManagerException(
                    "Failed to setup the user session :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for initializing the user session.
     */
    private void releaseUserSession() {
        try {
            getServerInterceptor().release();
        } catch (Exception ex) {
            log.error("Failed to release the user session : " +
                    ex.getMessage(),ex);
        }
    }
}
