/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2011  2015 Burntjam
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
 * PersistanceManager.java
 */


package com.rift.coad.rdf.semantic.persistance;

import com.rift.coad.rdf.semantic.SessionManager;

/**
 * The interface definition for the persistence manager.
 * 
 * @author brett chaldecott
 */
public interface PersistanceManager {
    /**
     * The name of the persitance manager.
     *
     * @return The name of the persistance manager.
     */
    public String getName();


    /**
     * The type of rdf store managed through this implementation.
     *
     * @return The string containing the rdf store information.
     */
    public String getRDFStore();


    /**
     * This method returns the version of the persistance manager.
     *
     * @return The string containing the version of the persistance manager.
     */
    public String getVersion();


    /**
     * This method returns the persistance session.
     *
     * @return The reference to the persistance session.
     * @throws PersistanceException
     */
    public PersistanceSession getSession() throws PersistanceException;
    
    
    /**
     * This method returns the persistance session.
     *
     * @param lock The type of lock to utilize for this session.
     * @return The reference to the persistance session.
     * @throws PersistanceException
     */
    public PersistanceSession getSession(SessionManager.SessionLock lock) 
            throws PersistanceException;
    
    
    /**
     * This method is called to close down the persistance manager, releaseing
     * all consumed resources.
     * 
     * @throws PersistanceException
     */
    public void close() throws PersistanceException;
}
