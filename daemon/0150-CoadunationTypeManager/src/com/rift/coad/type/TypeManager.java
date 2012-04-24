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
     * This method adds the adds XML defined type to the store.
     *
     * @param xml The string containing the XML information.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void importTypes(String project,String xml) throws TypeManagerException, RemoteException {
        try {
            ((TypeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(TypeManagerDaemon.class, "type/ManagementDaemon")).
                    importTypes(project, xml);
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to import the types : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to import the types : " + ex.getMessage(),ex);
        }
    }


    /**
     * The export method for the types
     *
     * @return The string containing the export.
     * @param The namespace to export.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public String exportTypes(String project) throws TypeManagerException, RemoteException {
        try {
            return ((TypeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(TypeManagerDaemon.class, "type/ManagementDaemon")).
                    exportTypes(project);
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to export the types : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to export the types : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to drop the given name space.
     *
     * @param namespace The name space to drop.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void dropTypes(String project) throws TypeManagerException, RemoteException {
        try {
            ((TypeManagerDaemon)ConnectionManager.getInstance().
                    getConnection(TypeManagerDaemon.class, "type/ManagementDaemon")).
                    dropTypes(project);
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to drop the types : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to drop the types : " + ex.getMessage(),ex);
        }
    }
    
}
