/*
 * Email Server: The email server interface
 * Copyright (C) 2008  2015 Burntjam
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
 * FetchMailQueue.java
 */

// package path
package com.rift.coad.daemon.email.server.fetchmail;

// java imports
import java.util.Date;
import java.lang.Comparable;


/**
 * This object acts as an entry in a fetch mail queue.
 *
 * @author brett chaldecott
 */
public interface FetchEntry extends Comparable {
    /**
     * This method returns the email address for this entry.
     */
    public String getEmailAddress();
    
    /**
     * This method gets the retrieve time.
     */
    public Date getRetryTime();
    
    
    /**
     * This method re-calculates the retrieve time.
     */
    public void recalculateRetryTime(long retryInteval);
    
}
