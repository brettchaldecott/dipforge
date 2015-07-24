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
 * NamingDirector.java
 *
 * This object is responsible for instanciating the naming server.
 */

// package path
package com.rift.coad.lib.naming;

// java imports
import java.lang.reflect.Constructor;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import javax.naming.Context;

// logging import
import org.apache.log4j.Logger;

// carol imports
import org.objectweb.carol.util.configuration.ConfigurationRepository;

// coadunation imports
import com.rift.coad.lib.naming.*;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.CoadunationThreadGroup;

/**
 * This object is responsible for instanciating the naming server.
 *
 * @author Brett Chaldecott
 */
public class NamingDirector {
    
    // the class constants
    private final static String ORB_MANAGER = "orb_manager";
    private final static String NAMING_CONTEXT_MANAGER = 
            "naming_context_manager";
    private final static String INSTANCE_IDENTIFIER = 
            "instance_identifier";
    public final static String PRIMARY_URL = "primary_jndi_url";
    public final static String PRIMARY = "primary";
    
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(NamingDirector.class.getName());
    
    // The singleton reference
    private static NamingDirector singleton = null;
    
    // private member variables
    private String instanceId = null;
    private String jndiBase = null;
    private String primaryJNDIUrl = null;
    private boolean primary = false;
    private OrbManager orbManager = null;
    private NamingContextManager namingContextManager = null;
    private CoadunationThreadGroup threadGroup = null;
    
    /** Creates a new instance of NamingDirector */
    private NamingDirector(CoadunationThreadGroup threadGroup) throws 
            NamingException {
        try {
            // init carol
            ConfigurationRepository.init();
            ConfigurationRepository.addInterceptors("iiop",
                    "com.rift.coad.lib.interceptor.iiop.InterceptorIntializer");
            
            // the reference to the configuration class
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            
            // retrieve the instance identifier
            instanceId = config.getString(INSTANCE_IDENTIFIER);
            primary = config.getBoolean(PRIMARY);
            primaryJNDIUrl = config.getString(PRIMARY_URL);
            
            if (primary){
                jndiBase = primaryJNDIUrl;
            } else {
                jndiBase = primaryJNDIUrl + "/" + NamingConstants.SUBCONTEXT
                        + "/" + instanceId;
            }
            
            // init the orb manager
            Class ref = Class.forName(config.getString(ORB_MANAGER));
            Constructor orgManagerConstructor = ref.getConstructor(
                    threadGroup.getClass());
            orbManager = (OrbManager)orgManagerConstructor.newInstance(
                    threadGroup);
            
            // init the naming context manager
            ref = Class.forName(config.getString(NAMING_CONTEXT_MANAGER));
            Constructor namingConstructor = ref.getConstructor(
                    threadGroup.getClass(),OrbManager.class,String.class);
            namingContextManager = (NamingContextManager)namingConstructor.
                    newInstance(threadGroup,orbManager,instanceId);
            
            // set the initial context factory.
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    namingContextManager.getInitialContextFactory());
            // set the initial context factory.
            System.setProperty(Context.URL_PKG_PREFIXES,
                    namingContextManager.getURLContextFactory());
        } catch (Exception ex) {
            log.error("Failed to start the naming director : " + ex.getMessage(),ex);
            throw new NamingException("Failed to init carol :" + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method inits the naming director and starts carol.
     */
    public synchronized static NamingDirector init(CoadunationThreadGroup 
            threadGroup) throws NamingException {
        if (singleton == null) {
            singleton = new NamingDirector(threadGroup);
        }
        return singleton;
    }
    
    
    /**
     * This method returns a reference to the current instancance in memory.
     *
     * @exception Exception
     */
    public synchronized static NamingDirector getInstance() throws 
            NamingException {
        if (singleton == null) {
            throw new NamingException(
                    "The naming director has not been initialized.");
        }
        return singleton;
    }
    
    
    /**
     * This method returns the id for this coadunation instance.
     *
     * @return The string containing the id of this coadunation instance.
     */
    public String getInstanceId() {
        return instanceId;
    }
    
    
    /**
     * This method returns true if this is a primary.
     *
     * @return TRUE if primary, FALSE if not.
     */
    public boolean isPrimary() {
        return primary;
    }
    
    
    /**
     * This method returns the JNDI base for this instance.
     *
     * @return The string containing the jndi base for this instance.
     */
    public String getJNDIBase() {
        return jndiBase;
    }
    
    
    /**
     * This method returns the primary JNDI url.
     *
     * @return The string containing the jndi base for this instance.
     */
    public String getPrimaryJNDIUrl() {
        return primaryJNDIUrl;
    }
    
    
    /**
     * Retrieve a reference to the ORB.
     *
     * @return The reference to the orb.
     */
    public ORB getORB() {
        return orbManager.getORB();
    }
    
    
    /**
     * Retrieve a reference to the POA.
     *
     * @return The reference to the POA.
     */
    public POA getPOA() {
        return orbManager.getPOA();
    }
    
    
    /**
     * This method is called to init the context for a class loader.
     */
    public void initContext() throws NamingException {
        namingContextManager.initContext();
    }
    
    
    /**
     * This method is called to release the context for class loader.
     */
    public void releaseContext() {
        namingContextManager.releaseContext();
    }
    
    
    /**
     * This method is responsible for 
     */
    public synchronized void shutdown() {
        namingContextManager.shutdown();
        orbManager.terminate();
    }
}
