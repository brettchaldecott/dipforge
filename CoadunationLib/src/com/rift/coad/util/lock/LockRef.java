/*
 * CoadunationUtil: The coaduntion utility library.
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
 * LockRef.java
 */

// the package path
package com.rift.coad.util.lock;

/**
 * This interface provides access to the lock.
 *
 * @author Brett Chaldecott
 */
public interface LockRef {
    // class constants
    public final static int READ = 1;
    public final static int WRITE = 2;
    
    /**
     * This method returns the key that the lock has been created for.
     *
     * @return The key the lock has been created for.
     */
    public Object getKey();
    
    
    /**
     * This method returns the id of the thread locking this object.
     *
     * @return The id of the thread that has the lock.
     */
    public long getThreadId() throws LockException;
    
    
    /**
     * This method sets the thread id for the lock.
     *
     * @param id The new thread id.
     */
    public void setThreadId(long id) throws LockException;
    
    
    /**
     * This method returns the name of the lock for the key.
     *
     * @return The string containing the name of the lock.
     */
    public Object getLockName() throws LockException;
    
    
    /**
     * This method sets the name associated with the key lock.
     *
     * @param name The name of the lock.
     */
    public void setLockName(Object name) throws LockException;
    
    
    /**
     * This method returns the lock type for this object.
     *
     * @return The lock type for this object.
     * @exception LockException
     */
    public int getLockType() throws LockException;
    
    
    /**
     * This method releases the lock.
     */
    public void release() throws LockException;
}
