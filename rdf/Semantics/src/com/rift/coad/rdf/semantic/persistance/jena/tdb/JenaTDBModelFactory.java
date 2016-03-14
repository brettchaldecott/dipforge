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
//                // This is a nasty work around to remove mbeans to prevent
//                // clashes on mbean services
//                try {
//                    ManagementFactory.getPlatformMBeanServer().
//                            unregisterMBean(new ObjectName(
//                            "org.apache.jena.sparql.system:type=SystemInfo"));
//                } catch (Exception ex) {
//                    // ignore
//                    log.error("Failed to remove reference : " + ex.getMessage());
//                }
//                try {
//                    ManagementFactory.getPlatformMBeanServer().
//                            unregisterMBean(new ObjectName(
//                            "org.apache.jena.sparql.system:type=Context"));
//                } catch (Exception ex) {
//                    // ignore
//                    log.error("Failed to remove reference : " + ex.getMessage());
//                }
//                try {
//                    ManagementFactory.getPlatformMBeanServer().
//                            unregisterMBean(new ObjectName(
//                            "org.apache.jena.sparql.system:type=Engine"));
//                } catch (Exception ex) {
//                    // ignore
//                    log.error("Failed to remove reference : " + ex.getMessage());
//                }

                String assemblerFile = prop.getProperty(PersistanceConstants.STORE_CONFIGURATION_FILE);
                if (assemblerFile == null) {
                    throw new PersistanceException("The configuration file ["
                            + PersistanceConstants.STORE_CONFIGURATION_FILE
                            + "] must be set for the SDB store.");
                }
                
                // read in the SDB data information
                dataset = TDBFactory.assembleDataset(assemblerFile);
                
//                // This is a nasty work around to remove mbeans to prevent
//                // clashes on mbean services
//                try {
//                    ManagementFactory.getPlatformMBeanServer().
//                            unregisterMBean(new ObjectName(
//                            "org.apache.jena.sparql.system:type=SystemInfo"));
//                } catch (Exception ex) {
//                    // ignore
//                    log.error("Failed to remove reference : " + ex.getMessage());
//                }
//                try {
//                    ManagementFactory.getPlatformMBeanServer().
//                            unregisterMBean(new ObjectName(
//                            "org.apache.jena.sparql.system:type=Context"));
//                } catch (Exception ex) {
//                    // ignore
//                    log.error("Failed to remove reference : " + ex.getMessage());
//                }
//                try {
//                    ManagementFactory.getPlatformMBeanServer().
//                            unregisterMBean(new ObjectName(
//                            "org.apache.jena.sparql.system:type=Engine"));
//                } catch (Exception ex) {
//                    // ignore
//                    log.error("Failed to remove reference : " + ex.getMessage());
//                }
//
//                // This is a nasty work around to remove mbeans to prevent
//                // clashes on mbean services
//                try {
//                    ManagementFactory.getPlatformMBeanServer().
//                            unregisterMBean(new ObjectName(
//                            "org.apache.jena.sparql.system:type=SystemInfo"));
//                } catch (Exception ex) {
//                    // ignore
//                    log.error("Failed to remove reference : " + ex.getMessage());
//                }
//                try {
//                    ManagementFactory.getPlatformMBeanServer().
//                            unregisterMBean(new ObjectName(
//                            "org.apache.jena.sparql.system:type=Context"));
//                } catch (Exception ex) {
//                    // ignore
//                    log.error("Failed to remove reference : " + ex.getMessage());
//                }
//                try {
//                    ManagementFactory.getPlatformMBeanServer().
//                            unregisterMBean(new ObjectName(
//                            "org.apache.jena.sparql.system:type=Engine"));
//                } catch (Exception ex) {
//                    // ignore
//                    log.error("Failed to remove reference : " + ex.getMessage());
//                }
            }
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
