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
 * BasicJDOPersistanceHandler.java
 */

// package path
package com.rift.coad.rdf.semantic.jdo.basic;

import com.rift.coad.rdf.semantic.Constants;
import com.rift.coad.rdf.semantic.jdo.obj.ClassInfo;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.DefaultPersistanceManagerFactory;
import com.rift.coad.rdf.semantic.persistance.PersistanceManager;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;

/**
 * This object is responsible for
 * @author brett chaldecott
 */
public class BasicJDOPersistanceHandler {

    private Object dataSource;
    private PersistanceSession session;
    private OntologySession ontologySession;

    public BasicJDOPersistanceHandler(Object dataSource, 
            PersistanceSession session, OntologySession ontologySession) {
        this.dataSource = dataSource;
        this.session = session;
        this.ontologySession = ontologySession;
    }


    /**
     * This method is called to persist the object to the session
     * @return
     * @throws BasicJDOException
     */
    public String persist() throws BasicJDOException {
        PersistanceManager persistance = null;
        try {
            persistance = DefaultPersistanceManagerFactory.init();
            PersistanceSession transientSession = persistance.getSession();
            String referenceURI = persist(dataSource,transientSession);
            session.persist(transientSession.dumpXML());
            return referenceURI;
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the data source : "
                    + ex.getMessage(),ex);
        } finally {
            try {
                persistance.close();
            } catch (Exception ex) {
                // ignore
            }
        }
    }


    /**
     * This method is responsible for the persistence session.
     *
     * @param dataSource The data source that has to be persisted.
     * @return The uri for the object being persisted.
     * @throws BasicJDOException
     */
    public String persist(Object dataSource,PersistanceSession transientSession)
            throws BasicJDOException {
        try {
            ClassInfo classInfo = ClassInfo.interrogateClass(dataSource.getClass());
            String resourceUri = String.format(Constants.RESOURCE_URI_FORMAT,
                    classInfo.getNamespace(),classInfo.getLocalName(),
                    classInfo.getIdMethod().getMethodRef().invoke(dataSource).toString());
            // TODO: complete the persist method
            return resourceUri;
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the data source [" +
                    dataSource.getClass().getName() + "]: " + ex.getMessage(),ex);
        }
    }
}
