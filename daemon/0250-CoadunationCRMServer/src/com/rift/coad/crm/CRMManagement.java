/*
 * 0047-CoadunationCRMServer: The CRM server.
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
 * CRMManagement.java
 */

package com.rift.coad.crm;



// java imports
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.thread.ThreadStateMonitor;

// crm imports
import com.rift.coad.crm.rdf.ModelManager;
import com.rift.coad.crm.result.CRMResultSet;

// jena imports
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.GraphStoreFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.apache.jena.update.UpdateProcessor;
import thewebsemantic.binding.Jenabean;

// coadunation imports
import com.rift.coad.lib.id.IDGenerator;

/**
 * The class responsible for managing the CRM database.
 * 
 * @author brett chaldecott
 */
public class CRMManagement implements CRMManagementMBean, BeanRunnable {

    // private member variables
    private static Logger log = Logger.getLogger(CRMManagement.class);
    private ThreadStateMonitor monitor = new ThreadStateMonitor();
    
    /**
     * The default constructor
     * 
     * @throws com.rift.coad.crm.CRMException
     */
    public CRMManagement() throws CRMException {
        try {
            IDGenerator.init(CRMManagement.class);
            ModelManager.getInstance();
            Jenabean.instance().bind(ModelManager.getInstance().getModel());
        } catch (Throwable ex) {
            log.error("Failed to init the CRM Manager : " + ex.getMessage(),ex);
            throw new CRMException
                    ("Failed to init the CRM Manager : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the version information for this object.
     * 
     * @return The string containing the version information for this object.
     */
    public String getVersion() {
        return "1.0";
    }
    
    
    /**
     * This method returns the name of this object.
     * 
     * @return The string containing the name of this object.
     */
    public String getName() {
        return this.getClass().getName();
    }
    
    
    /**
     * This method returns the name of the storage type being used.
     * 
     * @return The string containing the name of the storage being used.
     * @throws com.rift.coad.crm.CRMException
     * @throws java.rmi.RemoteException
     */
    public String getStorageType() throws CRMException {
        try {
            return ModelManager.getInstance().getStorageType();
        } catch (Throwable ex) {
            log.error("Failed to retrieve the storage status : " + 
                    ex.getMessage(),ex);
            throw new CRMException("Failed to retrieve the storage status : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the status of the storage being used.
     * 
     * @return The string containing the status of the storage being used.
     * @throws com.rift.coad.crm.CRMException
     * @throws java.rmi.RemoteException
     */
    public String getStorageStatus() throws CRMException {
        try {
            return ModelManager.getInstance().getStorageStatus();
        } catch (Throwable ex) {
            log.error("Failed to get the status of the storage : " + 
                    ex.getMessage(),ex);
            throw new CRMException("Failed to get the status of the storage : " + 
                    ex.getMessage(),ex);
        }
    }
    
    

    /**
     * This method returns a description of this class.
     * 
     * @return The string containing the description of this class.
     */
    public String getDescription() {
        return "CRM Management";
    }
    
    
    /**
     * This method returns the search result in RDF format.
     * 
     * @param statement The statement to perform the search with in SPARQL format.
     * @return The statement to base the search on.
     * @throws com.rift.coad.crm.CRMException
     */
    public String search(String statement) throws CRMException {
        QueryExecution qe = null;
        try {
            Query query = QueryFactory.create(statement);
            qe = QueryExecutionFactory.create(query, 
                    ModelManager.getInstance().getModel());
            ResultSet results = qe.execSelect();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ResultSetFormatter.out(out, results, query);
            return out.toString();
        } catch (Exception ex) {
            log.error("Failed to perform the query : " + ex.getMessage(),ex);
            throw new CRMException(
                    "Failed to perform the query : " + ex.getMessage(),ex);
        } finally {
            if (qe != null) {
                qe.close();
            }
        }
    }
    
    
    /**
     * This method inserts the entity into the database.
     * 
     * @param statement The statement to insert into the db in RDF format.
     * @throws com.rift.coad.crm.CRMException
     */
    public void insertEntry(String statement) throws CRMException {
        QueryExecution qe = null;
        Model model = null;
        try {
            model = ModelManager.getInstance().getModel();
            model.begin();
            ByteArrayInputStream in = new ByteArrayInputStream(statement.getBytes());
            model.read(in,null);
            model.commit();
            model = null;
            in.close();
        } catch (Exception ex) {
            log.error("Failed to perform the insert : " + ex.getMessage(),ex);
            throw new CRMException(
                    "Failed to perform the insert : " + ex.getMessage(),ex);
        } finally {
            if (qe != null) {
                qe.close();
            }
            if (model != null) {
                model.abort();
            }
        }
    }
    
    
    /**
     * This method updates the entry.
     * 
     * @param statement The statement to use to update entries in SPARQL format.
     * @throws com.rift.coad.crm.CRMException
     */
    public void updateEntry(String statement) throws CRMException {
        QueryExecution qe = null;
        try {
            UpdateProcessor processor = 
                    UpdateFactory.create(UpdateFactory.create(statement),
                    GraphStoreFactory.create(
                    ModelManager.getInstance().getModel()));
            processor.execute();
        } catch (Exception ex) {
            log.error("Failed to perform the query : " + ex.getMessage(),ex);
            throw new CRMException(
                    "Failed to perform the query : " + ex.getMessage(),ex);
        } finally {
            if (qe != null) {
                qe.close();
            }
        }
    }
    
    
    /**
     * This method deletes entries from the RDF database based on the statement
     * passed in.
     * 
     * @param statement The statement used to delete the entries.
     * @throws com.rift.coad.crm.CRMException
     */
    public void deleteEntry(String statement) throws CRMException {
        QueryExecution qe = null;
        try {
            UpdateProcessor processor = 
                    UpdateFactory.create(UpdateFactory.create(statement),
                    GraphStoreFactory.create(
                    ModelManager.getInstance().getModel()));
            processor.execute();
        } catch (Exception ex) {
            log.error("Failed to perform the query : " + ex.getMessage(),ex);
            throw new CRMException(
                    "Failed to perform the query : " + ex.getMessage(),ex);
        } finally {
            if (qe != null) {
                qe.close();
            }
        }
    }
    
    
    /**
     * This method is called to process.
     */
    public void process() {
        while (!monitor.isTerminated()) {
            monitor.monitor();
        }
        try {
            log.info("Shutting down the model manager.");
            ModelManager.getInstance().closeModel();
        } catch (Throwable ex) {
            log.error("Failed to shut down the CRM model manager : " + ex.getMessage(),ex);
        }
        try {
            IDGenerator.fin();
        } catch (Exception ex) {
            log.error("Failed to finalize the id generation information");
        }
    }
    
    
    /**
     * This method is called to terminate the crm engine
     */
    public void terminate() {
        monitor.terminate(true);
    }

}
