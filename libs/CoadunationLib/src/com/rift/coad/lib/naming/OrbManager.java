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
 * OrbManager.java
 *
 * An interface responsible for supplying the basic methods to manage an orb
 * reference.
 */

// package path
package com.rift.coad.lib.naming;

// java imports
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

/**
 * An interface responsible for supplying the basic methods to manage an orb
 * reference.
 *
 * @author Brett Chaldecott
 */
public interface OrbManager {
    /**
     * This method returns a reference to the orb.
     *
     * @return The reference to the orb.
     */
    public ORB getORB();
    
    
    /**
     * The reference to the poa.
     */
    public POA getPOA();
    
    
    /**
     * This method is called to terminate the orb.
     */
    public void terminate();
}
