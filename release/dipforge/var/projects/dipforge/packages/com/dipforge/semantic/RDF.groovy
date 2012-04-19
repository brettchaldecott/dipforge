/*
 * Dipforge: Common libraries.
 * Copyright (C) Thu Nov 03 14:05:39 SAST 2011 Rift IT Contracting
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
 * RDF.groovy 
 * @author brett chaldecott
 */

package com.dipforge.semantic


import groovy.json.*;
import org.apache.log4j.Logger;
import java.net.URI;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.dipforge.rdf.store.RDFConfig;


/**
 * This object provides the access to the RDF layer.
 * 
 * @author brett chaldecott
 */
class RDF {
    // private member variables    
    static def log = Logger.getLogger("com.dipforge.semantic.RDF");
    
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param type The URI of the type.
     */
    static def create(String type) {
        try {
            Session session = SemanticUtil.getInstance(RDFConfig.class).getSession();
            def ontology = session.getOntologySession()
            def classDef = ontology.getClass(new URI(type))
            def typeBuilder = new RDFTypeBuilder(classDef)
            
            return typeBuilder.getTypeInstance()
        } catch (Exception ex) {
            log.error("Failed to create the type because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method executes a query and returns the results of that query.
     * 
     * @return The results of the query.
     * @param The query string.
     */
    static def query(String query) {
        
        
    }
    
    
    /**
     * This method executes the statement.
     * 
     * @param query The query to execute.
     */
    static void execute(String query) {
        
    }
}
