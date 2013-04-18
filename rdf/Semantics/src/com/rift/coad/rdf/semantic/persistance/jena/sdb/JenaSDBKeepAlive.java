/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2013  Rift IT Contracting
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

package com.rift.coad.rdf.semantic.persistance.jena.sdb;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import org.apache.log4j.Logger;

/**
 * This class is responsible for keeping the connection to the database alive.
 * 
 * @author brett chaldecott
 */
public class JenaSDBKeepAlive extends Thread {
    
    // class constants
    private final static long TIMEOUT = 1000 * 60 * 10;
    
    // class logger
    private static Logger log = Logger.getLogger(JenaSDBKeepAlive.class);
    
    // pivate member variables
    private Model model; 
    private boolean terminated = false;
    
    
    /**
     * This constructor is responsible for setting up the model.
     * 
     * @param model The model to call.
     */
    public JenaSDBKeepAlive(Model model) {
        this.model = model;
    }

    
    /**
     * This method is called to process
     */
    @Override
    public void run() {
        while(!isTerminated()) {
            this.testConnection();
        }
    }
    
    
    /**
     * This method is called to terminate the processing of this thread.
     */
    public synchronized void terminate() {
        terminated = true;
        this.notifyAll();
    }
    
    
    /**
     * This method is called to determine if this object should stop processing
     * @return 
     */
    private synchronized boolean isTerminated() {
        try {
            this.wait(TIMEOUT);
        } catch (Exception ex) {
            // ignore this exception as it is expected
            log.info("Stopped waiting");
        }
        return terminated;
    }
    
    
    /**
     * This method is called to test the connection.
     */
    private void testConnection() {
        try {
            Query query = QueryFactory.create(
                    "SELECT ?s WHERE { ?s a " + 
                    "<http://www.coadunation.net/schema/rdf/1.0/test#Test> .}");
            QueryExecution executioner = 
                    QueryExecutionFactory.create(query, model);
            ResultSet resultSet = executioner.execSelect();
            executioner.close();
        } catch (Exception ex) {
            log.error("Failed to test the connection : " + ex.getMessage());
        }
    }
}
