/*
 * CoadunationCRMClient: The CRM client library
 * Copyright (C) 2009  2015 Burntjam
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
 * CRMManagementMBean.java
 */

package com.rift.coad.crm;

// import path
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// coadunation annotations
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.ExceptionInfo;


// crm imports
import com.rift.coad.crm.result.CRMResultSet;

/**
 * This interface defines the CRM management functionality.
 * 
 * @author brett chaldecott
 */
public interface CRMManagementMBean extends Remote {
    /**
     * This method returns the version information of the CRM management system.
     * 
     * @return The String containing the version information.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns the version of this entity.")
    @Version(number="1.0")
    @Result(description="This method returns the version of this entity.")
    public String getVersion() throws RemoteException;
    
    
    /**
     * This method returns the name of this management bean.
     * 
     * @return The string containing the name of this management bean.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns the name of this entity.")
    @Version(number="1.0")
    @Result(description="This method returns the name of this entity.")
    public String getName() throws RemoteException;
    
    
    /**
     * This method returns the description of this management bean.
     * 
     * @return The string description of this management bean.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns the description of this entity.")
    @Version(number="1.0")
    @Result(description="This method returns the description of this entity.")
    public String getDescription() throws RemoteException;
    
    
    /**
     * This method returns the name of the storage type being used.
     * 
     * @return The string containing the name of the storage type being used.
     * @throws com.rift.coad.crm.CRMException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns the name of the storage type being used.")
    @Version(number="1.0")
    @Result(description="The string containing the name of the storage type being used.")
    public String getStorageType() throws CRMException, RemoteException;
    
    
    /**
     * This method returns the status of the storage being used.
     * 
     * @return The string containing the status of the storage being used.
     * @throws com.rift.coad.crm.CRMException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="The string containing the status of the storage being used.")
    @Version(number="1.0")
    @Result(description="The string containing status of the storage.")
    public String getStorageStatus() throws CRMException, RemoteException;
    
    
    /**
     * This method performs a search and returns the results in string.
     * @param statement 
     * @return The string result.
     * @throws com.rift.coad.crm.CRMException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns a result set.")
    @Version(number="1.0")
    @Result(description="The string containing the Model in XML RDF format.")
    public String search(
            @ParamInfo(name="statement",description="The search statement to execute on the RDF data.")String statement) 
            throws CRMException, RemoteException;
    
    
    /**
     * This method inserts a new entry using RDF format.
     * @param statement The statement to use for the insert.
     * @throws com.rift.coad.crm.CRMException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method is responsible for inserting a new entry.")
    @Version(number="1.0")
    @Result(description="The XML RDF to insert.")
    public void insertEntry(
            @ParamInfo(name="statement",description="The XML RDF to insert.")String statement)
            throws CRMException, RemoteException;
    
    
    /**
     * This method is called to update an entry.
     * @param statement The statement is passed in RDF format.
     * @throws com.rift.coad.crm.CRMException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method is responsible for updating an entry.")
    @Version(number="1.0")
    @Result(description="The SPARQL update.")
    public void updateEntry(
            @ParamInfo(name="statement",description="The SPARQL update statemen.")String statement)
            throws CRMException, RemoteException;
    
    
    /**
     * This method is responsible for deleting an entry based on the statement
     * @param statement The statement used to identify the information to remove.
     * @throws com.rift.coad.crm.CRMException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method is responsible for updating an entry.")
    @Version(number="1.0")
    @Result(description="The SPARQL update.")
    public void deleteEntry(String statement) throws CRMException, RemoteException;
    
    
}
