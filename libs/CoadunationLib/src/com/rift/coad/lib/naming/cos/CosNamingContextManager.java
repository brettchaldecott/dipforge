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
 * CosNamingContextManager.java
 *
 * This class is responsible for managing the naming naming context.
 */

// package path
package com.rift.coad.lib.naming.cos;

// java imports
import java.util.Hashtable;

// coadunation imports
import com.rift.coad.lib.naming.NamingContextManager;
import com.rift.coad.lib.naming.NamingException;
import com.rift.coad.lib.naming.OrbManager;
import com.rift.coad.lib.thread.CoadunationThreadGroup;

/**
 * This class is responsible for managing the naming naming context.
 *
 * @author Brett Chaldecott
 */
public class CosNamingContextManager implements NamingContextManager {
    
    // the class singleton, this is not a traditionally clean way of doing this
    // but since this object is started from another singleton it will not
    // cause any problems
    private static CosNamingContextManager singleton = null;
    
    // classes private member variables
    private MasterContext masterContext = null;
    
    /** 
     * Creates a new instance of CosNamingContextManager
     *
     * @param threadGroup The coadunation thread group.
     * @param orbManager The reference to the orb manager.
     * @param instanceId The reference to the instance id of this Coadunation 
     *          Server.
     * @exception CosNamingException
     */
    public CosNamingContextManager(CoadunationThreadGroup threadGroup,
            OrbManager orbManager, String instanceId) throws
            CosNamingException {
        try {
            singleton = this;
            masterContext = new MasterContext(new Hashtable(),threadGroup,
                    orbManager,instanceId);
        } catch (Exception ex) {
            throw new CosNamingException("Failed to instanicate the cos naming " +
                    "context manager : " + ex.getMessage());
        }
    }
    
    
    /**
     * The singleton method to retrieve a reference to the cos naming context
     * manager.
     *
     * @return A reference to 
     */
    public static CosNamingContextManager getInstance() throws 
            CosNamingException {
        if (singleton == null) {
            throw new CosNamingException("The COS naming context manager has " +
                    "not been initialized.");
        }
        return singleton;
    }
    
    
    /**
     * This method returns the string identifying the context factory.
     *
     * @return The string containing the class name of the initial context
     *      factory.
     */
    public String getInitialContextFactory() {
        return "com.rift.coad.lib.naming.cos.URLInitialContextFactory";
    }
    
    
    /**
     * The url packages.
     *
     * @return Returns the string name for the class responsible for the URL
     *      packages.
     */
    public String getURLContextFactory() {
        return  "com.rift.coad.lib.naming.cos.javaURLContextFactory";
    }
    
    /**
     * This method is called to init the context for a class loader.
     *
     * @exception NamingException
     */
    public void initContext() throws NamingException {
        masterContext.initContext();
    }
    
    
    /**
     * This method is called to release the context for class loader.
     */
    public void releaseContext() {
        masterContext.releaseContext();
    }
    
    
    /**
     * The method to shut down the naming context.
     */
    public void shutdown() {
        masterContext.terminate();
    }
    
    
    /**
     * This method returns the current master context
     */
    public MasterContext getMasterContext() {
        return masterContext;
    }
}
