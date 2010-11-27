/*
 * CoadunationTypeManage: The client library for the type manager
 * Copyright (C) 2009  Rift IT Contracting
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
 * TypeManager.java
 */

// package path
package com.rift.coad.type;

// import
import java.rmi.RemoteException;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.util.connection.ConnectionManager;


/**
 * This is the implementation of the type manager.
 *
 * @author brett chaldecott
 */
public class TypeManager implements TypeManagerMBean {
    
    // static member variables
    private static Logger log = Logger.getLogger(TypeManager.class);

    /**
     * The default constructor for the type manager.
     */
    public TypeManager() {

    }

    /**
     * This method returns the version information.
     *
     * @return The version information for type manager.
     * @throws java.rmi.RemoteException
     */
    public String getVersion() throws RemoteException {
        return "1.0";
    }


    /**
     * This method returns the name of the type manager.
     *
     * @return The name of type manager
     * @throws java.rmi.RemoteException
     */
    public String getName() throws RemoteException {
        return this.getClass().getName();
    }


    /**
     * This method returns the description of the type manager.
     *
     * @return The description of the type manager.
     * @throws java.rmi.RemoteException
     */
    public String getDescription() throws RemoteException {
        return "Type Manager";
    }


    /**
     * This method adds the type to the type manager store.
     *
     * @param xml The xml containing the type information to add.
     * @throws com.rift.coad.type.TypeManagerException
     */
    public void addType(String xml) throws TypeManagerException {
        try {
            ((TypeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(TypeManagerDaemon.class, "type/ManagementDaemon")).addType(xml);
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to add the type : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method updates the type information
     *
     * @param xml The xml to update the type information for.
     * @throws com.rift.coad.type.TypeManagerException
     */
    public void updateType(String xml) throws TypeManagerException {
        try {
            ((TypeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(TypeManagerDaemon.class, "type/ManagementDaemon")).updateType(xml);
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to update the type : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method delete the type information
     *
     * @param xml The xml type information to delete.
     * @throws com.rift.coad.type.TypeManagerException
     */
    public void deleteType(String xml) throws TypeManagerException {
        try {
            ((TypeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(TypeManagerDaemon.class, "type/ManagementDaemon")).deleteType(xml);
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to delete the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to delete the type : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the type information.
     *
     * @return The string containing the type information.
     * @throws com.rift.coad.type.TypeManagerException
     */
    public String getTypes() throws TypeManagerException {
        try {
            return ((TypeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(TypeManagerDaemon.class, "type/ManagementDaemon")).getTypes();
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the types : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to get the types : " + ex.getMessage(),ex);
        }
    }

}
