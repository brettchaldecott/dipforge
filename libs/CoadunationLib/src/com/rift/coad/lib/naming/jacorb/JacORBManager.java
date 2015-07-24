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
 * JacORBManager.java
 *
 * This object is responsible for managing the JacORB instance. It start the ORB
 * , POA and the embedded name Cos Name service.
 */

// package
package com.rift.coad.lib.naming.jacorb;

// java imports
import java.util.Properties;
import java.io.File;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NameComponent;

// jac orb imports
import org.jacorb.naming.EmbeddedNameServer;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.naming.OrbManager;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.common.FileUtil;


/**
 * This object is responsible for managing the JacORB instance.
 *
 * @author Brett Chaldecott
 */
public class JacORBManager implements OrbManager {
    
    /**
     * The thread responsible for running the orb.
     */
    public class OrbRunner extends Thread {
        
        /**
         * The constructor of the orb runner class.
         */
        public OrbRunner() {
            
        }
        
        /**
         * The run method
         */
        public void run() {
            orb.run();
        }
    }
    
    // class constants
    private final static String HOST = "host";
    private final static String PORT = "port";
    private final static String DEFAULT_PORT = "2000";
    private final static String NAME_SERVER_STORE = "name_server_store";
    private final static String PURGE_NAME_STORE = "purge_name_store";
    private final static boolean PURGE_NAME_STORE_DEFAULT = true;
    
    // private member variables
    private ORB orb = null;
    private POA poa = null;
    private OrbRunner orbRunner = null;
    private EmbeddedNameServer embeddedNameServer = null;
    
    /** Creates a new instance of JacORBManager */
    public JacORBManager(CoadunationThreadGroup threadGroup) throws 
            JacORBException {
        try {
            // start the orb
            Properties properties = new Properties();
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(this.getClass());
            properties.setProperty("org.omg.CORBA.ORBClass",
                    "org.jacorb.orb.ORB");
            System.setProperty("org.omg.CORBA.ORBClass",
                    "org.jacorb.orb.ORB");
            properties.setProperty("org.omg.CORBA.ORBSingletonClass",
                    "org.jacorb.orb.ORBSingleton");
            System.setProperty("org.omg.CORBA.ORBSingletonClass",
                    "org.jacorb.orb.ORBSingleton");
            properties.setProperty("javax.rmi.CORBA.PortableRemoteObjectClass",
                    org.objectweb.carol.rmi.multi.JacORBPRODelegate.
                    class.getName());
            System.setProperty("javax.rmi.CORBA.PortableRemoteObjectClass",
                    org.objectweb.carol.rmi.multi.JacORBPRODelegate.
                    class.getName());
            System.setProperty("javax.rmi.CORBA.UtilClass",
                    org.objectweb.carol.util.delegate.UtilDelegateImpl.
                    class.getName());
            properties.setProperty("OAPort",config.getString(PORT,DEFAULT_PORT));
            properties.setProperty("OAIAddr",config.getString(HOST));
            
            // purge the contents of the name server store directory
            String nameServerStoreDir = config.getString(NAME_SERVER_STORE);
            if (config.getBoolean(PURGE_NAME_STORE,PURGE_NAME_STORE_DEFAULT))
            {
                purgeNameServerStore(nameServerStoreDir);
            }
            
            // Name server properties
            // The following properties are for the name server which runs using
            // the same orb instance
            /*
             * by setting the following property, the ORB will
             * accept client requests targeted at the object with
             * key "NameService", so more readablee corbaloc URLs
             * can be used
             */
            properties.put("jacorb.orb.objectKeyMap.NameService",
                    "StandardNS/NameServer-POA/_root");
            properties.put("jacorb.implname", "StandardNS");
            properties.put("jacorb.naming.db_dir", nameServerStoreDir);
            // end of name server properties
            orb = ORB.init(new String[0],properties);
            
            // start the poa
            poa = POAHelper.narrow(
                    orb.resolve_initial_references("RootPOA"));
            
            // init the embedded name server
            embeddedNameServer = new EmbeddedNameServer(orb,poa);
            
            // activate the poa
            poa.the_POAManager().activate();
            
            // start the orb
            orbRunner = new OrbRunner();
            orbRunner.start();
        } catch (JacORBException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new JacORBException ("Failed to start the orb : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a reference to the orb.
     *
     * @return The reference to the orb.
     */
    public ORB getORB() {
        return orb;
    }
    
    
    /**
     * The reference to the poa.
     */
    public POA getPOA() {
        return poa;
    }
    
    
    /**
     * This method is called to terminate the orb.
     */
    public void terminate() {
        orb.shutdown(true);
    }
    
    /**
     * This method is responsible for purging he contents 
     */
    private void purgeNameServerStore(String dir) throws JacORBException {
        try {
            // create the temporary directory
            File nameServerStoreDir = new File(dir);
            nameServerStoreDir.mkdirs();
            if (!nameServerStoreDir.exists()) {
                // ignore this
                return;
            } else if (nameServerStoreDir.isDirectory() == false) {
                throw new JacORBException(
                        "The name server store directory path [" + dir 
                        + "] is invalid.");
            }
            // purge the contents of the directory
            File[] files = nameServerStoreDir.listFiles();
            for (int index = 0; index < files.length; index++) {
                FileUtil.delTargetRecursive(files[index]);
            }
        } catch (JacORBException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new JacORBException("Failed to purge the name server store " +
                    "directory : " + ex.getMessage(),ex);
        }
    }
}

