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
 * SudoCallbackHandler.java
 *
 * The interface that should be implemented by classes that need to run as
 * another user.
 */

package com.rift.coad.lib.security.sudo;

/**
 * The interface that should be implemented by classes that need to run as
 * another user.
 *
 * @author Brett Chaldecott
 */
public interface SudoCallbackHandler {
    
    /**
     * This method will get called bh the Sudo object once a thread is running
     * as another user.
     *
     * @exception Exception
     */
    public void process() throws Exception;
}
