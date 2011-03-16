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
 * BasicJDOSPARQLQuery.java
 */

package com.rift.coad.rdf.semantic.jdo.basic.sparql;

import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.jdo.basic.BasicJDOException;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceResultRow;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import java.util.ArrayList;
import java.util.List;

/**
 * The basic JDO SPARQL result list
 *
 * @author brettc
 */
public class BasicJDOSparqlResultList extends ArrayList<SPARQLResultRow> {

    // private member variables
    private PersistanceSession persistanceSession;
    private OntologySession ontologySession;


    
    /**
     * This constructor creates a basic jdo sparql result list.
     *
     * @param persistanceSession The reference to the persistance session.
     * @param ontologySession The ontology session.
     * @param query The query.
     */
    public BasicJDOSparqlResultList(PersistanceSession persistanceSession,
            OntologySession ontologySession, String query) throws BasicJDOException {
        this.persistanceSession = persistanceSession;
        this.ontologySession = ontologySession;
        try {
            List<PersistanceResultRow> sparqlresult = persistanceSession.
                    createQuery(query).execute();
            for (PersistanceResultRow row : sparqlresult) {
                add(new BasicJDOSPARQLResultRow(persistanceSession,
                        ontologySession, row));
            }
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to insanciate the basic JDO value. : " +
                    ex.getMessage(),ex);
        }
        
    }


    
    


}
