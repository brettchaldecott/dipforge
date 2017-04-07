/*
 * Semantics: The semantic library for dipforge
 * Copyright (C) 2015  2015 Burntjam
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
 * JenaHTTPModelFactory.java
 */

package com.rift.coad.rdf.semantic.persistance.jena.http;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import com.rift.coad.rdf.semantic.persistance.PersistanceConstants;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.jena.JenaModelWrapper;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStore;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStoreType;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.RDFS;
import org.apache.log4j.Logger;

/**
 *
 * @author brett
 */
public class JenaHTTPModelFactory implements JenaStore{
    
    // class constants
    private final static String HTTP_AUTH = "http_auth";
    private final static boolean HTTP_AUTH_DEFAULT = false;
    private final static String HTTP_AUTH_USERNAME = "http_auth_username";
    private final static String HTTP_AUTH_PASSWORD = "http_auth_password";
    
    // logger
    private static Logger log = Logger.getLogger(JenaHTTPModelFactory.class);
    
    // class singleton
    private static Map<String,JenaHTTPModelFactory> singletonMap =  
            new ConcurrentHashMap<String,JenaHTTPModelFactory>();
    
    // 
    //private DatasetAccessor datasetAccessor;
    private HttpModel model;
    
    
    /**
     * This constructor 
     * 
     * @param properties
     * @throws PersistanceException 
     */
    private JenaHTTPModelFactory(Properties properties) throws PersistanceException {
        try {
            log.info("######################### The jena http model factory is being started");
            /*if (properties.get(HTTP_AUTH) == null || 
                    (boolean)properties.get(HTTP_AUTH) == false) {
                datasetAccessor = DatasetAccessorFactory.createHTTP(
                        properties.getProperty(PersistanceConstants.STORE_URL));
            } else {
                String username = properties.getProperty(HTTP_AUTH_USERNAME);
                String password = properties.getProperty(HTTP_AUTH_PASSWORD);
                
                datasetAccessor = DatasetAccessorFactory.createHTTP(
                        properties.getProperty(PersistanceConstants.STORE_URL));
                
            }*/
            String url = properties.getProperty(PersistanceConstants.STORE_URL);
            log.info("######################### instanciate the http model [" + url + "]");
            // check if the url is initialized and ready to use. This has to be done
            // as if the url is not up the Inheritience hierachy can cause an 
            // ClassDefNotFound exception to occurr if the HTTP model does not innize
            // completely
            // remove this check and just assume as this loads the entire store
            //URL serverUrl = new URL(url);
            //serverUrl.getContent();
            
            // instanciate the memory model first, using a blank model this setups
            // necessary requirements to instanciate a model if this is not done
            // the ClassDefNotFound exception can occurr
            ModelFactory.createDefaultModel();
            model = new HttpModel(url);
            log.info("######################### The jena http model factory has been started");
        } catch (Exception ex) {
            log.error("Failed to instanciate the http model factor : " + ex.getMessage(),ex);
            throw new PersistanceException("Failed to : " + ex.getMessage(),ex);
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
            String storeUrl = properties.getProperty(PersistanceConstants.STORE_URL);
            if (storeUrl == null) {
                throw new PersistanceException("The configuration file ["
                        + PersistanceConstants.STORE_URL
                        + "] must be set for the SDB store.");
            }
            JenaHTTPModelFactory result = singletonMap.get(storeUrl);
            if (result == null) {
                result = new JenaHTTPModelFactory(properties);
                singletonMap.put(storeUrl, result);
            }
        
            return result;
        } catch (PersistanceException ex) {
            throw ex;
        }
    }
    
    /**
     * This method returns a reference to the model.
     * 
     * @return This method is used to get a reference to the model.
     * @throws PersistanceException 
     */
    @Override
    public JenaModelWrapper getModule() throws PersistanceException {
        return new JenaHTTPModelWrapper(model);
    }

    /**
     * This method is called to close the connection to the store.
     * 
     * @throws PersistanceException 
     */
    @Override
    public void close() throws PersistanceException {
        // do nothing as this model does not need to be closed
        
    }

    
    /**
     * This method returns the type information.
     * 
     * @return This method returns the type information
     */
    @Override
    public JenaStoreType getType() {
        return JenaStoreType.HTTP;
    }
    
}
