/*
 * HsqlDBEngineClient: The hsql database engine wrapper.
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
 * HsqlDBEngine.java
 */

// package path
package com.rift.coad.daemon.hsqldb;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

// coadunation annotation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.ExceptionInfo;


/**
 * This interface supplies access to the Hsql server methods.
 *
 * @author Brett Chaldecott
 */
public interface HsqlDBEngineMBean extends Remote {
    
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
    @MethodInfo(description="Retrieves string containing the host address.")
    @Version(number="1.0")
    @Result(description="The string containing the host address.")
    public String getAddress() throws RemoteException, HsqlDBEngineException;
    
    
    /**
     * This method returns the number of databases
     * 
     * @return
     * @throws java.rmi.RemoteException
     * @throws com.rift.coad.daemon.hsqldb.HsqlDBEngineException
     */
    @MethodInfo(description="Retrieve the number of database entires.")
    @Version(number="1.0")
    @Result(description="The number of database entries.")
    public int getNumDBs() throws RemoteException, HsqlDBEngineException;
    
    
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
    @MethodInfo(description="Retrieves string containing the database name.")
    @Version(number="1.0")
    @Result(description="The string containing the database name.")
    public String getDatabaseName(
            @ParamInfo(name="index",
            description="The server for the host database")int index, 
            @ParamInfo(name="asconfigured",
            description="if true, the configured value, else the live value")
            boolean asconfigured) throws 
            RemoteException, HsqlDBEngineException;
    
    
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
    @MethodInfo(description="The path to the database.")
    @Version(number="1.0")
    @Result(description="The string containing the database path.")
    public String getDatabasePath(@ParamInfo(name="index",
            description="The server for the host database")int index, 
            @ParamInfo(name="asconfigured",
            description="if true, the configured value, else the live value")
            boolean asconfigured)  throws 
            RemoteException, HsqlDBEngineException;
    
    
    /**
     * This method returns the HsqlDB type
     *
     * @return A string containing the type of this db.
     * @param index The index of this type.
     * @exception RemoteException
     * @exception HsqlDBEngineException
     */
    @MethodInfo(description="Returns the HsqlDB type")
    @Version(number="1.0")
    @Result(description="The string containing the HsqlDB type.")
    public String getDatabaseType(@ParamInfo(name="index",
            description="The server for the host database")int index) 
            throws RemoteException, 
            HsqlDBEngineException;
    
    
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
    @MethodInfo(description="The port the database is running on.")
    @Version(number="1.0")
    @Result(description="The integer value containing the database port value")
    public int getPort() throws RemoteException, HsqlDBEngineException;
    
    
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
    @MethodInfo(description="Retrieve the server product name")
    @Version(number="1.0")
    @Result(description="The string containing the server product name")
    public String getProductName() throws RemoteException, HsqlDBEngineException;
    
    
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
    @MethodInfo(description="Retrieve the product version")
    @Version(number="1.0")
    @Result(description="The string containing the product version")
    public String getProductVersion() throws RemoteException, 
            HsqlDBEngineException;
    
    
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
    @MethodInfo(description="The protocol used by this server.")
    @Version(number="1.0")
    @Result(description="The string containing the protocol used by this server")
    public String getProtocol() throws RemoteException, HsqlDBEngineException;
    
    
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
    @MethodInfo(description="Retrieves containing the server identifier")
    @Version(number="1.0")
    @Result(description="The string containing the server identifier")
    public String getServerId() throws RemoteException, HsqlDBEngineException;
    
    
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
    @MethodInfo(description="Retrieves containing the state of the database")
    @Version(number="1.0")
    @Result(description="The string containing the state of the database")
    public int getState() throws RemoteException, HsqlDBEngineException;
    
    
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
    @MethodInfo(description="Retrieves a character sequence describing this server's current state," +
            "including the message of the last exception, if there is one and it" +
            "is still in context.")
    @Version(number="1.0")
    @Result(description="The string containing the state of the database")
    public String getStateDescriptor() throws RemoteException,
            HsqlDBEngineException;
    
    
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
    @MethodInfo(description="Retrieves whether the use of secure sockets was requested in the" +
        "server properties.")
    @Version(number="1.0")
    @Result(description="if true, secure sockets are requested, else not")
    public boolean isTls() throws RemoteException, HsqlDBEngineException;
    
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
    @MethodInfo(description="Attempts to put properties from the file " +
            "with the specified path. The file " +
            "extension '.properties' is implicit and should not " +
            "be included in the path specification.")
    @Version(number="1.0")
    @Result(description="true if the indicated file was read sucessfully, " +
            "else false")
    public boolean putPropertiesFromFile(@ParamInfo(name="path",
            description="The path to the file.")String path) throws RemoteException,
            HsqlDBEngineException;
    
    
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
    @MethodInfo(description="Puts properties from the supplied string argument. " +
            " The relevant key value pairs are the same as those for the (web)server.properties " +
            "file format, except that the 'server.' prefix should not be specified.")
    @Version(number="1.0")
    public void putPropertiesFromString(@ParamInfo(name="properties",
            description="The string containing properties.")String s) throws RemoteException,
            HsqlDBEngineException;
    
    
}
