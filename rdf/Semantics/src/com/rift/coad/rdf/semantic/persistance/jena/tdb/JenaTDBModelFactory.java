/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.persistance.jena.tdb;

// jena imports
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;

// dipforge imports
import org.apache.jena.tdb.TDBFactory;
import com.rift.coad.rdf.semantic.persistance.PersistanceConstants;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.jena.JenaEscaperFactory;
import com.rift.coad.rdf.semantic.persistance.jena.JenaModelWrapper;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStore;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStoreType;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.management.ObjectName;
import org.apache.jena.sparql.core.assembler.AssemblerUtils;
import org.apache.jena.tdb.assembler.VocabTDB;
import org.apache.log4j.Logger;

/**
 * The tdb jena model factory
 * 
 * @author brett chaldecott
 */
public class JenaTDBModelFactory implements JenaStore {
    
    // logger
    private static Logger log = Logger.getLogger(JenaTDBModelFactory.class);
    
    // class singleton
    private static Map<String,JenaTDBModelFactory> singletonMap =  
            new ConcurrentHashMap<String,JenaTDBModelFactory>();
    
    // private member variables
    private Dataset dataset;
    private JenaHTTPServer server = null;
    //private JenaModelWrapperTDB model;
    
    
    /**
     * The private constructor responsible for creating the tdb model factory
     * 
     * @param properties 
     */
    private JenaTDBModelFactory(Properties prop) throws PersistanceException {
        try {
            // synchronize on the MBeanServer to solve the race condition
            // between the various threads
            synchronized (ManagementFactory.getPlatformMBeanServer()) {
                String assemblerFile = prop.getProperty(PersistanceConstants.STORE_CONFIGURATION_FILE);
                if (assemblerFile == null) {
                    throw new PersistanceException("The configuration file ["
                            + PersistanceConstants.STORE_CONFIGURATION_FILE
                            + "] must be set for the SDB store.");
                }
                
                // attempt to assemble a default data set
                dataset = TDBFactory.assembleDataset(assemblerFile);
                if (dataset == null) {
                    
                    // 
                    dataset = (Dataset)AssemblerUtils.build(assemblerFile, "http://jena.hpl.hp.com/2005/11/Assembler#RDFDataset");
                    
                    if (dataset == null) {
                        log.info("##################### The assembler : " + AssemblerUtils.readAssemblerFile(assemblerFile).toString());
                        throw new PersistanceException("Failed to instantiate the tdb persistance model");
                    }
                }
                
                String startStoreHttpServer = prop.getProperty(PersistanceConstants.START_STORE_HTTP_SERVER);
                if (startStoreHttpServer != null && 
                        (startStoreHttpServer.equals("true") || startStoreHttpServer.equals("yes"))) {
                    server = new JenaHTTPServer(dataset);
                    server.start();
                }
                
            }
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(
                    "Failed to init the tdb file : " + ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to init the tdb file : " + ex.getMessage(),ex);
        }
    }

    /**
     * This method creates a new instance of the xml model factory.
     *
     * @param properties The properties.
     * @return The reference to the new store.
     * @throws PersistanceException
     */
    public synchronized static JenaStore createInstance(Properties properties)
            throws PersistanceException {
        try {
            String assemblerFile = properties.getProperty(PersistanceConstants.STORE_CONFIGURATION_FILE);
            if (assemblerFile == null) {
                throw new PersistanceException("The configuration file ["
                        + PersistanceConstants.STORE_CONFIGURATION_FILE
                        + "] must be set for the SDB store.");
            }
            JenaTDBModelFactory result = singletonMap.get(assemblerFile);
            if (result == null) {
                result = new JenaTDBModelFactory(properties);
                singletonMap.put(assemblerFile, result);
            }
        
            return result;
        } catch (PersistanceException ex) {
            throw ex;
        }
    }
    
    
    /**
     * This method returns the store matching the path to the assember file
     * @param path The path to the assembler file.
     * @return The reference to the jena store object.
     * @throws PersistanceException 
     */
    public synchronized static JenaTDBModelFactory getInstance(String path) throws PersistanceException {
        try {
            return singletonMap.get(path);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    /**
     * This method is called to retrieve the model
     * 
     * @return The reference to the TDB model
     * @throws PersistanceException 
     */
    public synchronized JenaModelWrapper getModule() throws PersistanceException {
        try {
            return new JenaModelWrapperTDB(dataset);
        } catch (Exception ex) {
            log.error("Failed to retrieve the model : " + ex.getMessage(),ex);
            throw new PersistanceException
                    ("Failed to retrieve the model : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is called to close the store
     * @throws PersistanceException 
     */
    public void close() throws PersistanceException {
        try {
            if (server != null) {
                server.stop();
            }
            dataset.close();
        } catch (Exception ex) {
            log.error("Failed to close the store : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is called to determine the type of store type being utilized.
     * 
     * @return The enum definingthe type of store.
     */
    public JenaStoreType getType() {
        return JenaStoreType.TDB;
    }
    
    
    

}
