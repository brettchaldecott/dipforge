/*
 * OntologyRepository: The ontology repository.
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
 * RepositoryManagerDaemonImpl.java
 */

package com.rift.coad.ontology;

// java imports
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;

/**
 * The implementation of the ontology repository.
 * 
 * @author brett chaldecott
 */
public class RepositoryManagerDaemonImpl implements RepositoryManagerDaemon {
    
    // class constants
    private final static String BASE_PATH = "base_path";
    private final static String JNDI_URL = "ontology/RepositoryManagerDaemon";
    
    // class singletons
    private static Logger log = Logger.getLogger(RepositoryManagerDaemonImpl.class);
    
    // private member variable
    private String basePath;
    private String tmpLocation;
    
    /**
     * The default constructor.
     */
    public RepositoryManagerDaemonImpl() throws RepositoryException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    RepositoryManagerDaemonImpl.class);
            basePath = config.getString(BASE_PATH);
            tmpLocation = config.getString("coadunation_temp");
        } catch (Exception ex) {
            log.error("Failed to instanciate the repository manager : " + ex.getMessage(),ex);
            throw new RepositoryException
                    ("Failed to instanciate the repository manager : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method lists the ontologies managed by this repository.
     * 
     * @return The list of ontologies in the repository.
     * @throws com.rift.coad.ontology.RepositoryException
     */
    public List<String> listOntologies() throws RepositoryException {
        try {
            File scriptDir = new File(basePath);
            File[] files = scriptDir.listFiles();
            List<String> entries = new ArrayList<String>();
            for (int index = 0; index < files.length; index++) {
                File file = files[index];
                if (!file.isFile()) {
                    continue;
                }
                entries.add(file.getName());
            }
            return entries;
        } catch (Exception ex) {
            log.error("Failed to list the ontologies : " + ex.getMessage(),ex);
            throw new RepositoryException(
                    "Failed to list the ontologies :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds the ontology.
     * @param name
     * @param contents
     * @throws com.rift.coad.ontology.RepositoryException
     */
    public void addOntology(String name, String contents) throws RepositoryException {
        try {
            if (new File(basePath,name).exists()) {
                log.info("Attempt to add an ontology that already exists : " + name);
                throw new RepositoryException
                        ("Attempt to add an ontology that already exists : " + name);
            }
            File temp = File.createTempFile(name, null,new File(tmpLocation));
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write(contents.getBytes());
            fos.close();
            File supFile = new File(basePath,name);
            temp.renameTo(supFile);
            
            // call all the services
            List services = new ArrayList();
            services.add(Services.ADD_ONTOLOGY);
            RepositoryEventListenerAsync listener = (RepositoryEventListenerAsync)
                    RPCMessageClient.create(
                    JNDI_URL,RepositoryEventListener.class,
                    RepositoryEventListenerAsync.class,services,false);
            listener.load(name);
            
        } catch (RepositoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the ontology : " + ex.getMessage(),ex);
            throw new RepositoryException(
                    "Failed to add the ontology :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method updates the ontology.
     * 
     * @param name The name of the ontology to update.
     * @param contents The contents of the ontology.
     * @throws com.rift.coad.ontology.RepositoryException
     */
    public void updateOntology(String name, String contents) throws RepositoryException {
        try {
            if (!new File(basePath,name).exists()) {
                log.info("Attempt to update an ontology that does not exists : " + name);
                throw new RepositoryException
                        ("Attempt to update a ontology that does not exists : " + name);
            }
            File temp = File.createTempFile(name, null,new File(tmpLocation));
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write(contents.getBytes());
            fos.close();
            File supFile = new File(basePath,name);
            temp.renameTo(supFile);
            // call all the services
            List services = new ArrayList();
            services.add(Services.ADD_ONTOLOGY);
            RepositoryEventListenerAsync listener = (RepositoryEventListenerAsync)
                    RPCMessageClient.create(
                    JNDI_URL,RepositoryEventListener.class,
                    RepositoryEventListenerAsync.class,services,false);
            listener.reload(name);
        } catch (RepositoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the ontology : " + ex.getMessage(),ex);
            throw new RepositoryException(
                    "Failed to update the ontology :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method deletes an ontology.
     * @param name
     * @throws com.rift.coad.ontology.RepositoryException
     */
    public void deleteOntology(String name) throws RepositoryException {
        try {
            File ontology = new File(basePath,name);
            if (!ontology.exists()) {
                log.info("The ontology does not exists : " + name);
                throw new RepositoryException
                        ("The ontology does not exists : " + name);
            }
            ontology.delete();
            
            // call all the services
            List services = new ArrayList();
            services.add(Services.ADD_ONTOLOGY);
            RepositoryEventListenerAsync listener = (RepositoryEventListenerAsync)
                    RPCMessageClient.create(
                    JNDI_URL,RepositoryEventListener.class,
                    RepositoryEventListenerAsync.class,services,false);
            listener.unload(name);
        } catch (RepositoryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to delete the ontology : " + ex.getMessage(),ex);
            throw new RepositoryException(
                    "Failed to delete the ontology :" + ex.getMessage(),ex);
        }
    }

}
