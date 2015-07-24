/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * MappingManagerAsync.java
 */

// package path
package com.rift.coad.script.server.files.mapping;

// java io file
import java.io.File;

// log4j import
import org.apache.log4j.Logger;

// gwt import
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// coadunation import
import com.rift.coad.daemon.jython.JythonMappingManagerDaemon;
import com.rift.coad.datamapperbroker.client.rdf.DataMapperMethod;
import com.rift.coad.groovy.GroovyMappingManagerDaemon;
import com.rift.coad.rdf.objmapping.util.RDFCopy;
import com.rift.coad.script.client.files.FileSuffixLookup;
import com.rift.coad.script.client.files.mapping.MappingException;
import com.rift.coad.script.client.files.mapping.MappingManager;
import com.rift.coad.script.client.files.mapping.NoMappingException;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.List;

/**
 * This object is responsible for managing the mappings for the scripts.
 *
 * @author brett chaldecott
 */
public class MappingManagerImpl extends RemoteServiceServlet implements
        MappingManager {


    // class singletons
    private static Logger log = Logger.getLogger(MappingManagerImpl.class);
    

    /**
     * The constructor of the mapping manager.
     */
    public MappingManagerImpl() {
    }
    

    /**
     * This method returns the mapping for the specified scope and name.
     *
     * @param scope The scope of the file.
     * @param name The name of the file.
     * @return The reference to the data mapper.
     * @throws com.rift.coad.script.client.files.mapping.NoMappingException
     * @throws com.rift.coad.script.client.files.mapping.MappingException
     */
    public DataMapperMethod getMapping(String scope, String name) throws
            NoMappingException, MappingException {
        String suffix = FileSuffixLookup.getSuffixForName(name);
        String path = scope.replaceAll("[.]", "/") + File.separator + name;
        if (suffix.equals(com.rift.coad.script.client.files.Constants.FILE_SUFFIXES[0])) {
            return getGroovyMapping(path);
        } else if (suffix.equals(com.rift.coad.script.client.files.Constants.FILE_SUFFIXES[1])) {
            return getPythonMapping(path);
        }
        log.error("Request to execute on unsupported file type [" + scope + "][" + name + "]");
        throw new MappingException
                ("Request to execute on unsupported file type [" + scope + "][" + name + "]");
    }


    /**
     * This method returns the python data mapping
     *
     * @param path The path to the file
     * @return The reference to the python data mapping information for the file.
     * @throws com.rift.coad.script.client.files.mapping.NoMappingException
     * @throws com.rift.coad.script.client.files.mapping.MappingException
     */
    private DataMapperMethod getPythonMapping(String path) throws
            NoMappingException, MappingException {
        try {
            JythonMappingManagerDaemon mappingManager = (JythonMappingManagerDaemon)
                    ConnectionManager.getInstance().getConnection(JythonMappingManagerDaemon.class,
                    "jython/MappingManagerDaemon");
            List<com.rift.coad.datamapperbroker.rdf.DataMapperMethod> methods = mappingManager.listMethods();
            for (com.rift.coad.datamapperbroker.rdf.DataMapperMethod method : methods) {
                log.info("The method name is : " + method.getName());
                if (method.getName().equals(path)) {
                    log.info("Found method [" + method.getName() + "][" + path + "]");
                    return (DataMapperMethod)RDFCopy.copyToClient(method);
                }
            }
            log.info("There is no script with the name : " + path);
            throw new NoMappingException
                    ("There is no script with the name : " + path);
        } catch (NoMappingException ex) {
            throw ex;
        } catch (Exception ex) {
            log.info("Failed to retrieve the mapping information [" + path + "] : " + ex.getMessage(),ex);
            throw new MappingException
                    ("Failed to retrieve the mapping information [" + path + "] : " + ex.getMessage());
        }
    }


    /**
     * This method returns the mappings for the groovy identified.
     *
     * @param path The path to the file
     * @return The mapping for the object.
     * @throws com.rift.coad.script.client.files.mapping.NoMappingException
     * @throws com.rift.coad.script.client.files.mapping.MappingException
     */
    private DataMapperMethod getGroovyMapping(String path) throws
            NoMappingException, MappingException {
        try {
            GroovyMappingManagerDaemon mappingManager = (GroovyMappingManagerDaemon)
                    ConnectionManager.getInstance().getConnection(GroovyMappingManagerDaemon.class,
                    "groovy/MappingManagerDaemon");
            List<com.rift.coad.datamapperbroker.rdf.DataMapperMethod> methods = mappingManager.listMethods();
            for (com.rift.coad.datamapperbroker.rdf.DataMapperMethod method : methods) {
                log.info("The method name is : " + method.getName());
                if (method.getName().equals(path)) {
                    log.info("Found method [" + method.getName() + "][" + path + "]");
                    return (DataMapperMethod)RDFCopy.copyToClient(method);
                }
            }
            log.info("There is no script with the name : " + path);
            throw new NoMappingException
                    ("There is no script with the name : " + path);
        } catch (NoMappingException ex) {
            throw ex;
        } catch (Exception ex) {
            log.info("Failed to retrieve the mapping information [" + path + "] : " + ex.getMessage(),ex);
            throw new MappingException
                    ("Failed to retrieve the mapping information [" + path + "] : " + ex.getMessage());
        }
    }


    /**
     * This method updates the mapping information.
     *
     * @param mapping The data mapping information.
     * @throws com.rift.coad.script.client.files.mapping.MappingException
     */
    public void updateMapping(String scope, String name,
            DataMapperMethod mapping) throws MappingException {
        String suffix = FileSuffixLookup.getSuffixForName(name);
        String path = scope.replaceAll("[.]", "/") + File.separator + name;
        mapping.setName(path);
        if (suffix.equals(com.rift.coad.script.client.files.Constants.FILE_SUFFIXES[0])) {
            updateGroovyMapping(mapping);
        } else if (suffix.equals(com.rift.coad.script.client.files.Constants.FILE_SUFFIXES[1])) {
            updatePythonMapping(mapping);
        }

    }


    /**
     * This method handles the updating of a groovy mapping.
     *
     * @param mapping The groovy mapping
     * @throws com.rift.coad.script.client.files.mapping.MappingException
     */
    private void updateGroovyMapping(DataMapperMethod mapping) throws MappingException {
        try {
            GroovyMappingManagerDaemon mappingManager = (GroovyMappingManagerDaemon)
                    ConnectionManager.getInstance().getConnection(GroovyMappingManagerDaemon.class,
                    "groovy/MappingManagerDaemon");
            try {
                getGroovyMapping(mapping.getName());
                mappingManager.updateMethod(
                        (com.rift.coad.datamapperbroker.rdf.DataMapperMethod)
                        RDFCopy.copyFromClient(mapping));
            } catch (NoMappingException ex) {
                mappingManager.addMethod(
                        (com.rift.coad.datamapperbroker.rdf.DataMapperMethod)
                        RDFCopy.copyFromClient(mapping));
            }
        } catch (Exception ex) {
            log.info("Failed to update the mapping information [" +
                    mapping.getName() + "] : " + ex.getMessage(),ex);
            throw new MappingException
                    ("Failed to update the mapping information [" +
                    mapping.getName() + "] : " + ex.getMessage());
        }
    }


    /**
     * This method is responsible for updating the python mappings.
     *
     * @param mapping The python mapping
     * @throws com.rift.coad.script.client.files.mapping.MappingException
     */
    private void updatePythonMapping(DataMapperMethod mapping) throws MappingException {
        try {
            JythonMappingManagerDaemon mappingManager = (JythonMappingManagerDaemon)
                    ConnectionManager.getInstance().getConnection(JythonMappingManagerDaemon.class,
                    "jython/MappingManagerDaemon");
            try {
                getPythonMapping(mapping.getName());
                mappingManager.updateMethod(
                        (com.rift.coad.datamapperbroker.rdf.DataMapperMethod)
                        RDFCopy.copyFromClient(mapping));
            } catch (NoMappingException ex) {
                mappingManager.addMethod(
                        (com.rift.coad.datamapperbroker.rdf.DataMapperMethod)
                        RDFCopy.copyFromClient(mapping));
            }
        } catch (Exception ex) {
            log.info("Failed to update the mapping information [" +
                    mapping.getName() + "] : " + ex.getMessage(),ex);
            throw new MappingException
                    ("Failed to update the mapping information [" +
                    mapping.getName() + "] : " + ex.getMessage());
        }
    }
    
}
