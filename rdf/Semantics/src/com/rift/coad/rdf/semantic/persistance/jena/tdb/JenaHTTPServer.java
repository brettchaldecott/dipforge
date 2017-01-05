/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.coad.rdf.semantic.persistance.jena.tdb;

import org.apache.jena.fuseki.embedded.FusekiEmbeddedServer;
import org.apache.jena.fuseki.server.DataService;
import org.apache.jena.query.Dataset;

/**
 *
 * @author brett chaldecott
 */
public class JenaHTTPServer {
    
    private FusekiEmbeddedServer server;
    private DataService service;
   
    /**
     * This constructor 
     * @param dataset 
     */
    public JenaHTTPServer(Dataset dataset) {
        if (dataset == null) {
            System.out.println("The data set is null and has not been instantiated");
        }
        
        server = FusekiEmbeddedServer.create().add("/rdf", dataset).build();
        
    }
    
    /**
     * The start method for the jena http server
     */
    public void start() {
        server.start();
    }
    
    /**
     * Stop the server
     */
    public void stop() {
        server.stop();
    }
}
