/*
 * Semantics: The semantic library for coadunation os
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
 * OntologyManager.java
 */

package com.rift.coad.rdf.semantic.ontology.jena;

import com.hp.hpl.jena.ontology.OntModel;
import com.rift.coad.rdf.semantic.ontology.OntologyException;
import com.rift.coad.rdf.semantic.ontology.OntologyTransaction;
import org.apache.log4j.Logger;

/**
 * This interface defines the methods for managing the transaction.
 *
 * @author brett chaldecott
 */
public class JenaOntologyTransaction implements OntologyTransaction {

    // class static member variables
    private static Logger log = Logger.getLogger(JenaOntologyTransaction.class);
    
    // private member variables
    private OntModel jenaOntModel;
    private boolean inTransaction = false;

    /**
     * This method generates the ontology transaction.
     * 
     * @param jenaOntModel The jena ontology model.
     */
    public JenaOntologyTransaction(OntModel jenaOntModel) {
        this.jenaOntModel = jenaOntModel;
    }


    /**
     * This method is called to being the transaction scope.
     *
     * @throws OntologyException
     */
    public void begin() throws OntologyException {
        if (inTransaction) {
            throw new OntologyException("A transaction has already been started");
        }
        try {
            jenaOntModel.begin();
            inTransaction = true;
        } catch (Exception ex) {
            log.error("Failed to start the transaction : " + ex.getMessage(),ex);
            throw new OntologyException
                    ("Failed to start the transaction : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for commiting the transaction.
     *
     * @throws PersistanceException
     */
    public void commit() throws OntologyException {
        try {
            jenaOntModel.commit();
        } catch (Exception ex) {
            log.error("Failed to commit : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for rolling back the transactional changes.
     *
     * @throws OntologyException
     */
    public void rollback() throws OntologyException {
        try {
            jenaOntModel.abort();
        } catch (Exception ex) {
            log.error("Failed to commit : " + ex.getMessage(),ex);
        }
    }


    /**
     * Returns true if a transaction has been started for this object.
     *
     * @return Returns true if a transaction has been started for this object.
     * @throws OntologyException
     */
    public boolean isInTransaction() throws OntologyException {
        return inTransaction;
    }
}
