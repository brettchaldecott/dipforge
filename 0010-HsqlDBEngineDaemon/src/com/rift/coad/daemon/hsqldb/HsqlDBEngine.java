/*
 * HsqlDbEngineDaemon: The hsql db engine daemon
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
 * HsqlDBEngineImpl.java
 *
 * The implementation of the HsqlDBEngine daemon.
 *
 * $Revision: 1.1.2.3 $
 */

// package path
package com.rift.coad.daemon.hsqldb;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.net.InetAddress;

// logging import
import org.apache.log4j.Logger;


// coadunation imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import java.io.File;
import java.util.StringTokenizer;
import org.hsqldb.DatabaseManager;

/**
 * The implementation of the HsqlDBEngine daemon.
 *
 * @author Brett Chaldecott
 */
public class HsqlDBEngine implements HsqlDBEngineMBean, BeanRunnable {
    
    // index value
    private final static String DB_NAME = "db_name";
    private final static String DEFAULT_DB_NAME = "coadunation";
    private final static String DB_LIST = "db_list";

    
    // private member variables
    private Logger log = Logger.getLogger(HsqlDBEngine.class.getName());
    private HsqlServerWrapper wrapper = null;
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private int index = 1;
    
    /** 
     * Creates a new instance of HsqlDBEngineImpl
     *
     * @exception HsqlDBEngineException
     */
    public HsqlDBEngine() throws HsqlDBEngineException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(HsqlDBEngine.class);
            wrapper = new HsqlServerWrapper(config);
            wrapper.addDB(config.getString(DB_NAME,DEFAULT_DB_NAME));

            // create other databases
            if (config.containsKey(DB_LIST)) {
                StringTokenizer tokens = new StringTokenizer(config.getString(DB_LIST),",");
                for (index = 1; tokens.hasMoreTokens(); index++) {
                    String dbName = tokens.nextToken();
                    log.info("Adding db : " + dbName);
                    wrapper.addDB(dbName);
                }
            }
        } catch (Exception ex) {
            throw new HsqlDBEngineException(
                    "Failed to init the HsqlDB Engine : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the number of databases
     * 
     * @return
     * @throws java.rmi.RemoteException
     * @throws com.rift.coad.daemon.hsqldb.HsqlDBEngineException
     */
    public int getNumDBs() throws HsqlDBEngineException {
        return index;
    }
    
    
    /**
     * Retrieves, in string form, this server's host address.
     *
     * @return this server's host address
     *
     * @jmx.managed-attribute
     *  access="read-write"
     *  description="Host InetAddress"
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public String getAddress() throws RemoteException, HsqlDBEngineException {
        return "localhost";
    }
    
    
    
    /**
     * Retrieves the url alias (network name) of the i'th database
     * that this Server hosts.
     *
     * @param index the index of the url alias upon which to report
     * @param asconfigured if true, report the configured value, else
     *      the live value
     * @return the url alias component of the i'th database
     *      that this Server hosts, or null if no such name exists.
     *
     * @jmx.managed-operation
     *  impact="INFO"
     *  description="url alias component of the i'th hosted Database"
     *
     * @jmx.managed-operation-parameter
     *      name="index"
     *      type="int"
     *      position="0"
     *      description="This Server's index for the hosted Database"
     *
     * @jmx.managed-operation-parameter
     *      name="asconfigured"
     *      type="boolean"
     *      position="1"
     *      description="if true, the configured value, else the live value"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public String getDatabaseName(int index, boolean asconfigured) throws 
            RemoteException, HsqlDBEngineException {
        return wrapper.getDatabaseName(index, asconfigured);
    }
    
    
    /**
     * Retrieves the HSQLDB path descriptor (uri) of the i'th
     * Database that this Server hosts.
     *
     * @param index the index of the uri upon which to report
     * @param asconfigured if true, report the configured value, else
     *      the live value
     * @return the HSQLDB database path descriptor of the i'th database
     *      that this Server hosts, or null if no such path descriptor
     *      exists
     *
     * @jmx.managed-operation
     *  impact="INFO"
     *  description="For i'th hosted database"
     *
     * @jmx.managed-operation-parameter
     *      name="index"
     *      type="int"
     *      position="0"
     *      description="This Server's index for the hosted Database"
     *
     * @jmx.managed-operation-parameter
     *      name="asconfigured"
     *      type="boolean"
     *      position="1"
     *      description="if true, the configured value, else the live value"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public String getDatabasePath(int index, boolean asconfigured)  throws 
            RemoteException, HsqlDBEngineException {
        return wrapper.getDatabasePath(index, asconfigured);
    }
    
    
    /**
     * This method returns the HsqlDB type
     *
     * @return A string containing the type of this db.
     * @param index The index of this type.
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public String getDatabaseType(int index) throws RemoteException, 
            HsqlDBEngineException {
        return wrapper.getDatabaseType(index);
    }
    
    
    /**
     * Retrieves this server's host port.
     *
     * @return this server's host port
     *
     * @jmx.managed-attribute
     *  access="read-write"
     *  description="At which ServerSocket listens for connections"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public int getPort() throws RemoteException, HsqlDBEngineException {
        return 9001;
    }
    
    
    /**
     * Retrieves this server's product name.  <p>
     *
     * Typically, this will be something like: "HSQLDB xxx server".
     *
     * @return the product name of this server
     *
     * @jmx.managed-attribute
     *  access="read-only"
     *  description="Of Server"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public String getProductName() throws RemoteException, 
            HsqlDBEngineException {
        return "HsqlDB";
    }
    
    
    /**
     * Retrieves the server's product version, as a String.  <p>
     *
     * Typically, this will be something like: "1.x.x" or "2.x.x" and so on.
     *
     * @return the product version of the server
     *
     * @jmx.managed-attribute
     *  access="read-only"
     *  description="Of Server"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public String getProductVersion() throws RemoteException, 
            HsqlDBEngineException {
        return "HsqlDB Daemon V2";
    }
    
    
    /**
     * Retrieves a string respresentaion of the network protocol
     * this server offers, typically one of 'HTTP', HTTPS', 'HSQL' or 'HSQLS'.
     *
     * @return string respresentation of this server's protocol
     *
     * @jmx.managed-attribute
     *  access="read-only"
     *  description="Used to handle connections"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public String getProtocol() throws RemoteException, HsqlDBEngineException {
        return "Unknown";
    }
    
    
    /**
     * Retrieves a String identifying this Server object.
     *
     * @return a String identifying this Server object
     *
     * @jmx.managed-attribute
     *  access="read-only"
     *  description="Identifying Server"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public String getServerId() throws RemoteException, HsqlDBEngineException {
        return "Unknown";
    }
    
    
    /**
     * Retrieves current state of this server in numerically coded form. <p>
     *
     * Typically, this will be one of: <p>
     *
     * <ol>
     * <li>ServerProperties.SERVER_STATE_ONLINE (1)
     * <li>ServerProperties.SERVER_STATE_OPENING (4)
     * <li>ServerProperties.SERVER_STATE_CLOSING (8)
     * <li>ServerProperties.SERVER_STATE_SHUTDOWN (16)
     * </ol>
     *
     * @return this server's state code.
     *
     * @jmx.managed-attribute
     *  access="read-only"
     *  description="1:ONLINE 4:OPENING 8:CLOSING, 16:SHUTDOWN"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public int getState() throws RemoteException, HsqlDBEngineException {
        throw new HsqlDBEngineException("Not implemented");
    }
    
    
    /**
     * Retrieves a character sequence describing this server's current state,
     * including the message of the last exception, if there is one and it
     * is still in context.
     *
     * @return this server's state represented as a character sequence.
     *
     * @jmx.managed-attribute
     *  access="read-only"
     *  description="State as string"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public String getStateDescriptor() throws RemoteException,
            HsqlDBEngineException {
        return "Servers are up";
    }
    
    
    /**
     * Retrieves whether the use of secure sockets was requested in the
     * server properties.
     *
     * @return if true, secure sockets are requested, else not
     *
     * @jmx.managed-attribute
     *  access="read-write"
     *  description="Use TLS/SSL sockets?"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public boolean isTls() throws RemoteException, HsqlDBEngineException {
        throw new HsqlDBEngineException("Cannot return the TLS flag for multiple servers.");
    }
    
    /**
     * Attempts to put properties from the file
     * with the specified path. The file
     * extension '.properties' is implicit and should not
     * be included in the path specification.
     *
     * @param path the path of the desired properties file, without the
     *      '.properties' file extension
     * @throws RuntimeException if this server is running
     * @return true if the indicated file was read sucessfully, else false
     *
     * @jmx.managed-operation
     *  impact="ACTION"
     *  description="Reads in properties"
     *
     * @jmx.managed-operation-parameter
     *   name="path"
     *   type="java.lang.String"
     *   position="0"
     *   description="(optional) returns false if path is empty"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public boolean putPropertiesFromFile(String path) throws RemoteException,
            HsqlDBEngineException{
        // not implemented
        throw new HsqlDBEngineException("Not implemented");
    }
    
    
    /**
     * Puts properties from the supplied string argument.  The relevant
     * key value pairs are the same as those for the (web)server.properties
     * file format, except that the 'server.' prefix should not be specified.
     *
     * @param s semicolon-delimited key=value pair string,
     *      e.g. k1=v1;k2=v2;k3=v3...
     * @throws RuntimeException if this server is running
     *
     * @jmx.managed-operation
     *   impact="ACTION"
     *   description="'server.' key prefix automatically supplied"
     *
     * @jmx.managed-operation-parameter
     *   name="s"
     *   type="java.lang.String"
     *   position="0"
     *   description="semicolon-delimited key=value pairs"
     *
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    public void putPropertiesFromString(String s) throws RemoteException,
            HsqlDBEngineException {
        throw new HsqlDBEngineException("Not implemented");
    }
    
    
    /**
     * This method will be called to perform the processing. This method
     * replaces the traditional run method.
     */
    public void process() {
        try {
            wrapper.start();
            while(!state.isTerminated()) {
                state.monitor();
            }
            log.info("process method exiting");
        } catch (Exception ex) {
            log.error("Failed to start the server : " + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * This method is called to soft terminate the processing thread.
     */
    public void terminate() {
        try {
            wrapper.stop();
            DatabaseManager.closeDatabases(0);
            state.terminate(true);
            log.info("Hsql has been shut down exiting");
        } catch (Exception ex) {
            log.error("Failed to stop the logger : " + ex.getMessage(), ex);
        }
    }
}
