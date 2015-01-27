/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * JenaStoreTypes.java
 */
package com.rift.coad.rdf.semantic.persistance.jena.xml;

import java.lang.management.ManagementFactory;
import javax.management.ObjectName;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.rift.coad.rdf.semantic.persistance.PersistanceConstants;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.jena.JenaModelWrapper;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStore;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStoreType;
import java.io.ByteArrayInputStream;
import java.util.Properties;

/**
 * The reference to the 
 *
 * @author brett chaldecott
 */
public class JenaXMLModelFactory implements JenaStore {

    // private member variables
    private Model dataStore;

    /**
     * This private constructur instanciate the default model object from the
     * jena module factory and sets the data within it.
     *
     * @param properties The properties containing the data for the store.
     * @throws PersistanceException
     */
    private JenaXMLModelFactory(Properties properties)
            throws PersistanceException {
        try {
            // This is a nasty work around to remove mbeans to prevent
            // clashes on mbean services
            try {
                ManagementFactory.getPlatformMBeanServer().
                    unregisterMBean(new ObjectName(
                    "com.hp.hpl.jena.sparql.system:type=SystemInfo"));
            } catch (Exception ex) {
                // ignore
            }
            try {
                ManagementFactory.getPlatformMBeanServer().
                    unregisterMBean(new ObjectName(
                    "com.hp.hpl.jena.sparql.system:type=Context"));
            } catch (Exception ex) {
                // ignore
            }
            try {
                ManagementFactory.getPlatformMBeanServer().
                    unregisterMBean(new ObjectName(
                    "com.hp.hpl.jena.sparql.system:type=Engine"));
            } catch (Exception ex) {
                // ignore
            }

            dataStore = ModelFactory.createDefaultModel();
            // This is a nasty work around to remove mbeans to prevent
            // clashes on mbean services
            try {
                ManagementFactory.getPlatformMBeanServer().
                    unregisterMBean(new ObjectName(
                    "com.hp.hpl.jena.sparql.system:type=SystemInfo"));
            } catch (Exception ex) {
                // ignore
            }
            try {
                ManagementFactory.getPlatformMBeanServer().
                    unregisterMBean(new ObjectName(
                    "com.hp.hpl.jena.sparql.system:type=Context"));
            } catch (Exception ex) {
                // ignore
            }
            try {
                ManagementFactory.getPlatformMBeanServer().
                    unregisterMBean(new ObjectName(
                    "com.hp.hpl.jena.sparql.system:type=Engine"));
            } catch (Exception ex) {
                // ignore
            }
            String xmlContents =
                    properties.getProperty(PersistanceConstants.XML_RDF_CONTENTS);
            if (xmlContents != null) {
                ByteArrayInputStream in = new
                        ByteArrayInputStream(xmlContents.getBytes());
                dataStore.read(in, null);
                in.close();
            }
        } catch (Throwable ex) {
            throw new PersistanceException("Failed to instanciate the new jena "
                    + "xml store because : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method creates a new instance of the xml model factory.
     *
     * @param properties The properties.
     * @return The reference to the new store.
     * @throws PersistanceException
     */
    public static JenaStore createInstance(Properties properties)
            throws PersistanceException {
        return new JenaXMLModelFactory(properties);
    }

    /**
     * This method returns the model
     * 
     * @return
     * @throws PersistanceException
     */
    public JenaModelWrapper getModule() throws PersistanceException {
        return new JenaModelWrapperXML(dataStore);
    }

    /**
     * This method is called to close down the data store.
     * 
     * @throws PersistanceException
     */
    public void close() throws PersistanceException {
        try {
            dataStore.close();
        } catch (Exception ex) {
            // ignore
        }
    }

    
    /**
     * This method returns the store type.
     * 
     * @return The enum defining the store type.
     */
    public JenaStoreType getType() {
        return JenaStoreType.XML;
    }
    
    
    
}
