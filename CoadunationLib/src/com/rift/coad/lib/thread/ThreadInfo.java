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
 * ThreadInfo.java
 *
 * The information about a single thread running in coadunation.
 */

package com.rift.coad.lib.thread;

// coadunation imports
import com.rift.coad.lib.security.UserSession;

/**
 * The information about a single thread running in coadunation.
 *
 * @author Brett Chaldecott
 */
public class ThreadInfo {
    
    // the classes member variables
    private long threadId = 0;
    private Class threadClass = null;
    private UserSession user = null;
    private Thread.State state = null;
    private String info = null;
    
    /** 
     * Creates a new instance of ThreadInfo 
     *
     * @param threadId The id of the thread this object represents.
     * @param threadClass The reference to the thread class.
     * @param user The user object.
     * @param state The state of this thread.
     */
    public ThreadInfo(long threadId, Class threadClass, UserSession user, 
            Thread.State state, String info) {
        this.threadId = threadId;
        this.threadClass = threadClass;
        this.user = user;
        this.state = state;
        this.info = info;
    }
    
    
    /**
     * The getter method for the thread id.
     *
     * @return The id of the thread contained in a long value.
     */
    public long getThreadId() {
        return threadId;
    }
    
    
    /**
     * The getter method for the thread class object.
     *
     * @return The reference to the thread class.
     */
    public Class getThreadClass() {
        return threadClass;
    }
    
    
    /**
     * This method returns the user object reference for this thread.
     *
     * @return The reference to the user object.
     */
    public UserSession getUser() {
        return user;
    }
    
    
    /**
     * This method returns the state of the thread that this object contains
     * information for.
     *
     * @return The reference to the current state.
     */
    public Thread.State getState() {
        return state;
    }
    
    
    /**
     * The information about this thread as provided by the thread.
     *
     * @return A string containing the information about this thread.
     */
    public String getInfo() {
        return info;
    }
}
