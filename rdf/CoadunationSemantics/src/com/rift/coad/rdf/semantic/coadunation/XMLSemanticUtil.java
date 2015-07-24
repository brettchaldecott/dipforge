/*
 * CoaduntionSemantics: The semantic library
 * Copyright (C) 2011  2015 Burntjam
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
 * XMLSemanticUtil.java
 */
package com.rift.coad.rdf.semantic.coadunation;

import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.SessionManagerBuilder;
import com.rift.coad.rdf.semantic.ontology.OntologyConstants;
import com.rift.coad.schema.util.XMLListParser;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * This object is responsible for providing the default XML semantic session.
 * 
 * @author brett chaldecott
 */
public class XMLSemanticUtil {
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(XMLSemanticUtil.class.getName());
    
    
    /**
     * This method returns a reference to the newly created session.
     * 
     * @return The reference to the session.
     */
    public static Session getSession() throws SemanticUtilException {
        try {
            // Retrieve the configuration for the message service implementation
            com.rift.coad.lib.configuration.Configuration coadConfig =
                    com.rift.coad.lib.configuration.ConfigurationFactory.
                    getInstance()
                    .getConfig(XMLSemanticUtil.class);

            // retrieve the default transaction timeout
            int timeout = (int)coadConfig.getLong(
                    SemanticUtil.TRANSACTION_TIMEOUT,
                    SemanticUtil.DEFAULT_TRANSACTION_TIMEOUT);

            // setup the basic uri property list
            Properties properties = new Properties();
            StringBuffer ontologyListBuffer = new StringBuffer();
            String sep = "";
            if (coadConfig.containsKey(SemanticUtil.RDF_QUERY_URL)) {
                List<URL> urls = 
                        new XMLListParser(new URL(
                        coadConfig.getString(SemanticUtil.RDF_QUERY_URL))).
                        getURLs();
                for (URL url : urls) {
                    ontologyListBuffer.append(sep).append(url.toString());
                    sep = ",";
                }
            }
            if (coadConfig.containsKey(OntologyConstants.ONTOLOGY_LOCATION_URIS)) {
                ontologyListBuffer.append(sep).append(
                        coadConfig.getString(OntologyConstants.ONTOLOGY_LOCATION_URIS));
            }
            


            // ket the key set
            Set keys = coadConfig.getKeys();
            for (Object key : keys) {
                if (key.toString().equals(OntologyConstants.ONTOLOGY_LOCATION_URIS)) {
                    continue;
                }
                if (coadConfig.isString((String)key)) {
                    properties.put(key, coadConfig.getString((String)key));
                }

            }
            if (ontologyListBuffer.length() > 0) {
                properties.put(OntologyConstants.ONTOLOGY_LOCATION_URIS, ontologyListBuffer.toString());
            }
            
            // instanciate the session manager
            return SessionManagerBuilder.createManager(properties).getSession();
        } catch (Throwable ex) {
            log.error("Initial SessionManager " +
                    "creation failed: " + ex.getMessage(),ex);
            throw new SemanticUtilException("Initial SessionManager " +
                    "creation failed: " + ex.getMessage(),ex);
        }
    }
}
