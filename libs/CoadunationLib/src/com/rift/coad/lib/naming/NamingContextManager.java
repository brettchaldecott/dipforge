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
 * NamingContextManager.java
 *
 * This interface is used to manager the naming context.
 */

package com.rift.coad.lib.naming;

/**
 * This interface is used to manager the naming context.
 *
 * @author Brett Chaldecott
 */
public interface NamingContextManager {
    
    /**
     * This method returns the string identifying the context factory.
     *
     * @return The string containing the class name of the initial context
     *      factory.
     */
    public String getInitialContextFactory();
    
    
    /**
     * The url packages.
     *
     * @return Returns the string name for the class responsible for the URL
     *      packages.
     */
    public String getURLContextFactory();
    
    
    /**
     * This method is called to init the context for a class loader.
     */
    public void initContext() throws NamingException;
    
    
    /**
     * This method is called to release the context for class loader.
     */
    public void releaseContext();
    
    
    /**
     * The method to shut down the naming context.
     */
    public void shutdown();
    
    
}
