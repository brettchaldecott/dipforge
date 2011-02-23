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
import com.rift.coad.rdf.semantic.jdo.obj.MethodInfo;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.DefaultPersistanceManagerFactory;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceManager;
import com.rift.coad.rdf.semantic.persistance.PersistanceProperty;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.util.ClassTypeInfo;
import java.net.URI;
import java.util.Collection;
import java.util.List;

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
    public PersistanceResource persist() throws BasicJDOException {
        PersistanceManager persistance = null;
        try {
            persistance = DefaultPersistanceManagerFactory.init();
            PersistanceSession transientSession = persistance.getSession();
            PersistanceResource resource = persist(dataSource, transientSession);
            session.persist(transientSession.dumpXML());
            return resource;
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the data source : "
                    + ex.getMessage(), ex);
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
    public PersistanceResource persist(Object dataSource, PersistanceSession transientSession)
            throws BasicJDOException {
        try {
            ClassInfo classInfo = ClassInfo.interrogateClass(dataSource.getClass());
            URI resourceUri = new URI(String.format(Constants.RESOURCE_URI_FORMAT,
                    classInfo.getNamespace(), classInfo.getLocalName(),
                    classInfo.getIdMethod().getMethodRef().invoke(dataSource).toString()));
            PersistanceResource resource = transientSession.createResource(
                    resourceUri);
            for (MethodInfo methodInfo : classInfo.getGetters()) {
                PersistanceIdentifier identifier = PersistanceIdentifier.getInstance(
                        methodInfo.getNamespace(), methodInfo.getLocalName());
                Class returnType =
                        methodInfo.getMethodRef().getReturnType();
                if (ClassTypeInfo.isBasicType(returnType)) {
                    persistBasicType(methodInfo, resource, identifier);
                } else if (returnType.isArray()) {
                    throw new BasicJDOException("The array type is not supported");
                } else if (ClassTypeInfo.isCollection(returnType)) {
                    persistCollection(transientSession, methodInfo, resource, identifier);
                } else {
                    this.persistObject(transientSession, methodInfo, resource, identifier);
                }
            }
            return resource;
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the data source ["
                    + dataSource.getClass().getName() + "]: " + ex.getMessage(), ex);
        }
    }

    /**
     * This method is called to persist the basic type
     *
     * @param methodInfo The method to persist the basic type.
     * @param resource The resource reference
     * @throws BasicJDOException
     */
    private void persistBasicType(MethodInfo methodInfo,
            PersistanceResource resource, PersistanceIdentifier identifier)
            throws BasicJDOException {
        try {
            resource.removeProperty(identifier);
            PersistanceProperty property =
                    resource.createProperty(identifier);
            property.setValue(
                    methodInfo.getMethodRef().invoke(dataSource).
                    toString());
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the basic type : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method persists the collection information
     *
     * @param transientSession The transient session information.
     * @param methodInfo The method information.
     * @param resource The resource information.
     * @param identifier The identifier.
     * @throws BasicJDOException
     */
    private void persistCollection(PersistanceSession transientSession,
            MethodInfo methodInfo, PersistanceResource resource,
            PersistanceIdentifier identifier) throws BasicJDOException {
        try {
            resource.removeProperty(identifier);
            Collection collection =
                    (Collection) methodInfo.getMethodRef().invoke(dataSource);
            for (Object obj : collection) {
                resource.createProperty(identifier).setValue(persist(obj,
                        transientSession));
            }
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the collection : "
                    + ex.getMessage(), ex);
        }
    }

    private void persistObject(PersistanceSession transientSession,
            MethodInfo methodInfo, PersistanceResource resource,
            PersistanceIdentifier identifier) throws BasicJDOException {
        try {
            PersistanceProperty property =
                    resource.createProperty(identifier);
            property.setValue(
                    persist(methodInfo.getMethodRef().invoke(dataSource),
                    transientSession));
        } catch (BasicJDOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BasicJDOException("Failed to persist the object : "
                    + ex.getMessage(), ex);
        }
    }
}
