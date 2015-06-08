/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * CoadunationThreadGroup.java
 *
 * This object is responsible for loading the JMX Bean into memory from the 
 * deployment loader passed to it.
 */

package com.rift.coad.lib.thread;

// the import paths
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.security.user.UserStoreManager;


/**
 * This object is responsible for controlling the creation and administration of
 * a grouping of threads.
 *
 * @author Brett Chaldecott
 */
public class CoadunationThreadGroup {
    
    /**
     * This object contains the list of threads in memory for a grouping. It 
     */
    public class ThreadList {
        // the class log variable
        protected Logger log =
            Logger.getLogger(ThreadList.class.getName());
        
        // member variables
        private boolean terminated = false;
        private Vector threads = null;
        
        /**
         * The default constructor of the thread list.
         */
        public ThreadList() {
            threads = new Vector();
        }
        
        
        /**
         * This method adds a new thread to the list of threads.
         *
         * @param thread The reference to the thread to add.
         */
        public synchronized boolean addThread(BasicThread thread) {
            if (terminated == false) {
                threads.add(thread);
                return true;
            }
            return false;
        }
        
        
        /**
         * Returns a copy of the current threads.
         *
         * @return The list of the current threads.
         */
        public synchronized Vector getThreads() {
           Vector threads = new Vector() ;
           threads.addAll(this.threads);
           return threads;
        }
        
        
        /**
         * This method removes an object from the list that matches.
         *
         * @param thread The reference to the thread to remove.
         */
        public synchronized void remove(BasicThread thread) {
            for (int index = 0; index < threads.size(); index++) {
                if (threads.get(index) == thread) {
                    log.info("Object equal removing [" 
                            + thread.getId() + "] id [" + 
                            ((BasicThread)threads.get(index)).getId() 
                            + "] index [" + index + "]");
                    threads.remove(index);
                    break;
                }
            }
        }
        
        
        /**
         * This method sets the terminated flag for this object.
         */
        public synchronized void terminate() {
            terminated = true;
        }
        
        
        /**
         * This method will return true if this object is terminated
         */
        public synchronized boolean isTerminated() {
            return terminated;
        }
    }
    
    // the classes constant static variables
    private final static String THREAD_TERMINATE_TIMEOUT = "Thread_Terminate_Timeout";
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(CoadunationThreadGroup.class.getName());
    
    // the classes private member variables
    private UserSessionManager sessionManager = null;
    private UserStoreManager userStoreManager = null;
    private ThreadList threadList = null;
    private long threadTerminateTimeout = 0;
    private CoadunationThreadGroup parent = null;
    
    
    /**
     * 
     * Creates a new instance of CoadunationThreadGroup 
     * 
     * 
     * @param sessionManager A reference to the user session manager.
     * @param userStoreManager The user store object.
     */
    public CoadunationThreadGroup(UserSessionManager sessionManager,
            UserStoreManager userStoreManager) throws ThreadException {
        this.sessionManager = sessionManager;
        this.userStoreManager = userStoreManager;
        this.threadList = new ThreadList();
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            threadTerminateTimeout = config.getLong(THREAD_TERMINATE_TIMEOUT);
            
        } catch (Exception ex) {
            throw new ThreadException(
                    "Failed to retrieve default thread terminate timeout.",ex);
        }
    }
    
    
    /**
     * 
     * Creates a new instance of CoadunationThreadGroup 
     * 
     * 
     * @param sessionManager A reference to the user session manager.
     * @param userStoreManager The user store object.
     */
    private CoadunationThreadGroup(CoadunationThreadGroup parent, UserSessionManager sessionManager,
            UserStoreManager userStoreManager) throws ThreadException {
        if (parent == null) {
            throw new ThreadException("The parent thread group is invalid");
        }
        this.parent = parent;
        this.sessionManager = sessionManager;
        this.userStoreManager = userStoreManager;
        this.threadList = new ThreadList();
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            threadTerminateTimeout = config.getLong(THREAD_TERMINATE_TIMEOUT);
            
        } catch (Exception ex) {
            throw new ThreadException(
                    "Failed to retrieve default thread terminate timeout.",ex);
        }
    }
    
    
    /**
     * This method will start the required number of threads of the given class
     * type.
     *
     * @param classRef The reference to the class type.
     * @param username The name of the user.
     * @param number The number of threads to start.
     * @exception ThreadException
     */
    public void startThreads(Class classRef, String username, int number) 
    throws ThreadException {
        try{
            validateThreadClass(classRef);
            for (int count = 0; count < number; count++) {
                BasicThread threadRef = (BasicThread)classRef.newInstance();
                if (threadRef instanceof CoadunationThread) {
                    throw new ThreadException(
                            "Must inherit from Basic Thread and not Coad Thread.");
                }
                addThread(threadRef,username);
                // make sure the context loader is set corretly for all
                // newly created threads
                threadRef.setContextClassLoader(Thread.currentThread().
                        getContextClassLoader());
                
                // start the thread
                threadRef.start();
            }
        } catch (Exception ex) {
            throw new ThreadException("Failed to add threads for [" + 
                    classRef.getName() + "] because :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will start the required number of threads of the given class
     * type.
     *
     * @param threadRef The reference to the class type.
     * @param username The name of the user.
     * @exception ThreadException
     */
    public void addThread(BasicThread threadRef, String username) 
    throws ThreadException {
        try {
            UserSession user = userStoreManager.getUserInfo(username);
            threadRef.setSessionManager(sessionManager);
            threadRef.setUser(user);
            threadRef.setCoadThreadGroup(this);
            
            // make sure the context loader is set corretly for all
            // newly created threads
            threadRef.setContextClassLoader(Thread.currentThread().
                    getContextClassLoader());
        } catch (Exception ex) {
            throw new ThreadException(
                    "Failed to add a thread to this group : " + ex.getMessage()
                    ,ex);
        }
    }
    
    
    /**
     * This method will add a thread to this thread grouping.
     *
     * @return TRUE if the thread has been added false if it could not be added.
     * @param threadRef The reference to the thread object to add.
     * @exception ThreadException
     */
    protected boolean addThread(BasicThread threadRef) throws ThreadException {
        return threadList.addThread(threadRef);
    }
    
    
    /**
     * This method will remove the thread reference from the object.
     *
     * @param threadRef The reference to removed.
     * @exception ThreadException
     */
    protected void removeThread(BasicThread threadRef) {
        threadList.remove(threadRef);
    }
    
    
    /**
     * This method returns the parent object referenced by this object.
     *
     * @return The parent of this object.
     */
    public CoadunationThreadGroup getParent() {
        return parent;
    }
    
    
    /**
     * This method returns the thread information for all the threads controlled
     * by this object.
     *
     * @return The list containing the thread information.
     */
    public List getThreadInfo() throws ThreadException {
        List threadInfoList = new ArrayList();
        Vector threads = threadList.getThreads();
        for (int i = 0; i < threads.size(); i++) {
            BasicThread thread = (BasicThread)threads.get(i);
            ThreadInfo threadInfo = new ThreadInfo(thread.getId(), 
                    thread.getClass(), thread.getUser(), thread.getState(),
                    thread.getInfo());
            threadInfoList.add(threadInfo);
        }
        return threadInfoList;
    }
    
    
    /**
     * This method will return the terminated flag value.
     */
    public boolean  isTerminated() {
        return threadList.isTerminated();
    }
    
    /**
     * This method terminates the threads being maintained by this thread
     * grouping.
     */
    public synchronized void terminate() {
        
        // the object has already been terminated
        if (threadList.isTerminated()) {
            return;
        }
        
        // call soft terminate on running threads.
        threadList.terminate();
        
        Vector threads = threadList.getThreads();
        for (int i = 0; i < threads.size(); i++) {
            BasicThread thread = (BasicThread)threads.get(i);
            try {
                thread.terminate();
            } catch(Exception ex) {
                log.error("Failed to terminate thread [" + thread.getId() + 
                        "] class [" + thread.getClass().getName() + 
                        "] because : " + ex.getMessage(),ex);
            }
        }
        
        // call join on the thread
        for (int i = 0; i < threads.size(); i++) {
            BasicThread thread = (BasicThread)threads.get(i);
            try {
                thread.join(getThreadTimeout(thread));
            } catch(Exception ex) {
                log.error("Failed to wait for thread [" + thread.getId() + 
                        "] class [" + thread.getClass().getName() + 
                        "] because : " + ex.getMessage(),ex);
            }
        }
        
        
        // call the depricated stop method on the threads that have not stopped
        // within the designated time
        for (int i = 0; i < threads.size(); i++) {
            BasicThread thread = (BasicThread)threads.get(i);
            try {
                if (thread.getState() != Thread.State.TERMINATED) {
                    log.error("The thread [" + thread.getId() + 
                        "] class [" + thread.getClass().getName() + 
                        "] has not been terminated forcing it to stop.");
                    thread.stop();
                }
            } catch(Exception ex) {
                log.error("Failed to wait for thread [" + thread.getId() + 
                        "] class [" + thread.getClass().getName() + 
                        "] because : " + ex.getMessage(),ex);
            }
        }
    }
    
    
    /**
     * This method retrieve the thread timout period for a given object.
     *
     * @return The long containing the thread time out.
     * @param obj The object to retrieve the thread timeout for.
     */
    private long getThreadTimeout(Object obj) {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    obj.getClass());
            return config.getLong(THREAD_TERMINATE_TIMEOUT);
        } catch (Exception ex) {
            return threadTerminateTimeout;
        }
    }
    
    
    /**
     * This method creates a new child thread group.
     *
     * @return The newly created thread group.
     * @exception ThreadException
     */
    public CoadunationThreadGroup createThreadGroup() throws ThreadException {
        return new CoadunationThreadGroup(this,sessionManager,userStoreManager);
    }
    
    
    /**
     * This method validates the class references is valid.
     *
     * @param classRef The class reference that is passed in for validation.
     */
    private void validateThreadClass(Class classRef) throws ThreadException {
        Class tempClass = classRef.getSuperclass();
        while (tempClass != null) {
            System.out.println("Class : " + tempClass.getName());
            if (tempClass.getName().
                    equals("com.rift.coad.lib.thread.BasicThread")) {
                return;
            }
            tempClass = classRef.getSuperclass();
        }
        throw new ThreadException(
                "This object does not inherit from BasicThread [" +
                classRef.getName() + "]");
    }
}
