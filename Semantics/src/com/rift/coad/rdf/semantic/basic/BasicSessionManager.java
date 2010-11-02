/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * BasicSessionManager.java
 */

// package path
package com.rift.coad.rdf.semantic.basic;

// url imports
import java.net.URL;
import java.io.ByteArrayInputStream;

// log4j imports
import org.apache.log4j.Logger;

// jena bean imports
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

// coadunation imports
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.SessionException;
import com.rift.coad.rdf.semantic.SessionManager;
import com.rift.coad.rdf.semantic.session.BasicSession;

/**
 * The implementation of the basic manager.
 *
 * @author brett chaldecott
 */
public class BasicSessionManager implements SessionManager {

    // class static member variables
    private static Logger log = Logger.getLogger(BasicSessionManager.class);
    
    // private member variables
    private Model config = null;
    private Model store = null;
    private URL configUrl = null;

    /**
     * The default configuration it does not setup the entity configuration.
     */
    public BasicSessionManager() {
        store = ModelFactory.createDefaultModel();
    }

    /**
     * The constructor that sets up the entity configuration using the contents string.
     *
     * @param contents The contents string.
     */
    public BasicSessionManager(String contents) {
        config = ModelFactory.createDefaultModel();
        store = ModelFactory.createDefaultModel();
        ByteArrayInputStream in = new ByteArrayInputStream(contents.getBytes());
        config.read(in,null);
    }


    /**
     * The constructor that instantiates an object using the configuration url as the configuration source.
     *
     * @param configUrl The source of the bean configuration.
     */
    public BasicSessionManager(URL configUrl)  throws BasicException {
        try {
            config = ModelFactory.createDefaultModel();
            store = ModelFactory.createDefaultModel();
            this.configUrl = configUrl;
            config.read(configUrl.toString());
        } catch (Exception ex) {
            log.error("Failed to initialize the basic session manager : " + ex.getMessage(),ex);
            throw new BasicException(
                    "Failed to initialize the basic session manager : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the version information.
     *
     * @return The string containing the version information.
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of the session manager.
     *
     * @return The string containing the name of the session manager.
     */
    public String getName() {
        return this.getClass().getName();
    }

    /**
     * This method returns the description of this object.
     *
     * @return The string containing the description of the session manager.
     */
    public String getDescription() {
        return "A basic session manager";
    }


    /**
     * This method returns a new session attached to the model.
     *
     * @return The reference to the session.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public Session getSession() throws SessionException {
        try {
            // re-read the configuration.
            if (configUrl != null) {
                config = ModelFactory.createDefaultModel();
                config.read(configUrl.toString());
            }
            return new BasicSession(config,store);
        } catch (Exception ex) {
            log.error("Failed to return the session object reference : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to return the session object reference : " + ex.getMessage(),ex);
        }
    }

    public void shutdown() throws SessionException {
        // do nothing
    }

}
