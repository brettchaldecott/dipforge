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
 * BasicThread.java
 *
 * This object is the basic Coadunation thread.
 */

// package path
package com.rift.coad.lib.thread;

// logging import
import org.apache.log4j.Logger;

// imports
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserSessionManager;


/**
 * This object is the basic Coadunation thread.
 *
 * @author Brett Chaldecott
 */
public class BasicThread extends Thread {
    
    // The classes member variables
    private UserSessionManager sessionManager = null;
    private UserSession user = null;
    private BasicRunnable runnable = null;
    private CoadunationThreadGroup threadCoadGroup = null;
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(BasicThread.class.getName());
    
    
    /** 
     * Creates a new instance of BasicThread
     */
    public BasicThread() throws Exception {
        super(ThreadGroupManager.getInstance().getThreadGroup(),
                RandomGuid.getInstance().getGuid());
    }
    
    
    /** 
     * Creates a new instance of BasicThread setting the runnable reference.
     *
     * @param runnable The reference to the runnable object.
     */
    public BasicThread(BasicRunnable runnable) throws Exception  {
        super(ThreadGroupManager.getInstance().getThreadGroup(),
                RandomGuid.getInstance().getGuid());
        this.runnable = runnable;
    }
    
    
    /**
     * This method will set the session for this thread.
     *
     * @param sessionManager The reference to the session manager.
     * @exception ThreadException
     */
    protected void setSessionManager(UserSessionManager sessionManager) 
    throws ThreadException {
        if (null == sessionManager) {
            throw new ThreadException(
                    "Cannot set the session manager reference to null.");
        }
        this.sessionManager = sessionManager;
    }
    
    
    /**
     * This method returns the user object reference.
     *
     * @return The reference to the user object.
     */
    public UserSession getUser() {
        return user;
    }
    
    
    /**
     * This method will set the user information for this object.
     *
     * @param user The users that this thread will run as.
     */
    public void setUser(UserSession user) throws ThreadException {
        if (user == null) {
            throw new ThreadException(
                    "Cannot set the User to null.");
        }
        this.user = user;
    }
    
    
    /**
     * This method returns the coadanation thread group.
     *
     * @return The reference to the coadunation thread group.
     */
    protected CoadunationThreadGroup getCoadThreadGroup() {
        return threadCoadGroup;
    }
    
    
    /**
     * This method sets the coadunation thread group.
     *
     * @param threadCoadGroup The reference to the coad thread group.
     */
    protected void setCoadThreadGroup(CoadunationThreadGroup threadCoadGroup) {
        this.threadCoadGroup = threadCoadGroup;
    }
    
    
    /**
     * The run method
     */
    public final void run () {
       try {
           if ((user == null) || (sessionManager == null)) {
               log.error(
                       "Cannot run this thread as it has not been correctly initialized.");
               return ;
           }
           if (threadCoadGroup == null) {
               log.error(
                       "The thread group has not been set thread cannot run.");
               return;
           }
           else if (false == threadCoadGroup.addThread(this))
           {
               log.error(
                       "The thread group has been terminated will exit now.");
               return;
           }
           sessionManager.initSessionForUser(user);
           process();
       } catch (Exception ex) {
           log.error("Thread failed to processes :" + ex.getMessage(),ex);
       } finally {
           if (threadCoadGroup != null) {
               threadCoadGroup.removeThread(this);
           }
       }
       
    }
    
    
    /**
     * This method replaces the run method in the BasicThread.
     *
     * @exception Exception
     */
    public void process() throws Exception {
        if (runnable == null) {
            throw new ThreadException(
                    "This object has not been setup correctly. Either the process " +
                    "method must be over ridden or a BasicRunnable object must be used.");
        }
        System.out.println("Call the runnable object");
        runnable.process();
        System.out.println("After calling the runnable object");
    }
    
    
    /**
     * This method will be implemented by child objects to terminate the
     * processing of this thread.
     */
    public void terminate() {
        if (runnable != null) {
            runnable.terminate();
        }
    }
    
    
    /**
     * This method returns the information about what this thread is processing.
     *
     * @return String containing a description of this thread
     */
    public String getInfo() {
        if (runnable != null) {
            return "Class : " + runnable.getClass().getName();
        }
        return "Class : " + this.getClass().getName();
    }
}
