/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * CoadunationThread.java
 *
 * The coadunation thread class.
 */

// package path
package com.rift.coad.lib.thread;

import com.rift.coad.lib.security.UserSession;


/**
 * The coadunation thread class.
 *
 * @author Brett Chaldecott
 */
public class CoadunationThread extends BasicThread {
    
    /**
     * 
     * Creates a new instance of CoadunationThread
     */
    public CoadunationThread() throws Exception {
    }
    
    
    /**
     * 
     * Creates a new instance of CoadunationThread setting the runnable reference.
     * 
     * 
     * @param runnable The reference to the runnable object.
     */
    public CoadunationThread(BasicRunnable runnable) throws Exception {
        super(runnable);
    }
    
    
    /**
     * This method cannot be used on a coadunation thread.
     */
    public void start() throws IllegalThreadStateException {
        throw new IllegalThreadStateException("Cannot user this method use" +
                " start(Username) instead");
    }
    
    
    /**
     * This method overrides the start method to bind this thread to the
     * thread group.
     *
     * @exception IllegalThreadStateException
     */
    public void start(String username) throws IllegalThreadStateException {
        try {
            ThreadGroupManager.getInstance().addThreadToGroup(this,username);
            super.start();
        } catch (Exception ex) {
            throw new IllegalThreadStateException(
                    "Failed to start the thread : " + ex.getMessage());
        }
    }
}
