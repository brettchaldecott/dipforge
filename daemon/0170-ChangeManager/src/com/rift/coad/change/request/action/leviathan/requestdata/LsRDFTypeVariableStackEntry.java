/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2012  2015 Burntjam
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
 * RequestTypeMethodStackEntry.java
 */

// package path
package com.rift.coad.change.request.action.leviathan.requestdata;

import com.rift.coad.change.request.RequestData;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.XMLSemanticUtil;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The request type variable stack entry
 * 
 * @author brett chaldecott
 */
public class LsRDFTypeVariableStackEntry extends ProcessStackEntry {

    private CallStatement callStatement;
    private LsActionRDFProperty target;
    private Object assignmentValue;
    private boolean hasAssignment;
    
    
    /**
     * The constructor of the request type variables stack entry.
     * 
     * @param processorMemoryManager The reference to the processor memory manager.
     * @param parent The parent reference.
     * @param variables The list of variables
     */
    public LsRDFTypeVariableStackEntry(
            ProcessStackEntry parent, Map variables, CallStatement callStatement,
            Object target, Object assignmentValue, boolean hasAssignment) {
        super(parent.getProcessorMemoryManager(), parent, variables);
        this.callStatement = callStatement;
        this.target = (LsActionRDFProperty)target;
        this.assignmentValue = assignmentValue;
        this.hasAssignment = hasAssignment;
    }

    
    
    
    /**
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        try {
            LsActionRDFProperty data = (LsActionRDFProperty)target;
            Session session = XMLSemanticUtil.getSession();
            session.persist(data.getData().getData());
            Resource resource = session.get(Resource.class,data.getUri());
            
            // walk the resources
            resource = LsRDFTypeUtil.getResource(resource, 
                    callStatement.getEntries().subList(1,
                    callStatement.getEntries().size() - 1));
            
            // get the last entry
            CallStatement.CallStatementEntry entry = 
                    callStatement.getEntries().get(
                    callStatement.getEntries().size() -1);
            
            if (this.assignmentValue != null) {
                executeSet(session, entry, resource);
            } 
            
            executeGet(entry, resource);
            pop();
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException("Failed to process the method call : " + 
                    ex.getMessage(),ex);
        }
    }

    /**
     * This method is called to handle the process getter.
     * 
     * @param entry The reference to the resource.
     * @param resource The resource the call on.
     * @throws EngineException
     */
    private void executeGet(CallStatement.CallStatementEntry entry, 
            Resource resource) throws EngineException {
        try {
            OntologyClass ontologyClass = LsRDFTypeUtil.getOntologyClass(resource);
            List<OntologyProperty> properties = ontologyClass.listProperties();
            boolean found = false;
            
            String propertyName = entry.getName();
            for (OntologyProperty property : properties) {
                if (property.getLocalname().equalsIgnoreCase(propertyName)) {
                    
                    String propertyType = property.getType().getURI().toString();
                    if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_STRING).getURI().toString())) {
                        this.getParent().setResult(
                                resource.getProperty(String.class, property.getURI().toString()));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                        this.getParent().setResult(
                                resource.getProperty(Boolean.class, property.getURI().toString()));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                        this.getParent().setResult(
                                resource.getProperty(Float.class, property.getURI().toString()));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                        this.getParent().setResult(
                                resource.getProperty(Double.class, property.getURI().toString()));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                        this.getParent().setResult(
                                resource.getProperty(Integer.class, property.getURI().toString()));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_LONG).getURI().toString())) {
                        this.getParent().setResult(
                                resource.getProperty(Long.class, property.getURI().toString()));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_INT).getURI().toString())) {
                        this.getParent().setResult(
                                resource.getProperty(Integer.class, property.getURI().toString()));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                        this.getParent().setResult(
                                resource.getProperty(Short.class, property.getURI().toString()));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                        this.getParent().setResult(
                                resource.getProperty(Byte.class, property.getURI().toString()));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_DATE).getURI().toString())) {
                        this.getParent().setResult(
                                resource.getProperty(Date.class, property.getURI().toString()));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                        this.getParent().setResult(
                                resource.getProperty(Date.class, property.getURI().toString()));
                    } else {
                        String stringUri = resource.getURI().toString();
                        this.getParent().setResult(new LsActionRDFProperty(
                                propertyName, stringUri, ontologyClass.getURI().toString(),
                                this.target.getData()));
                    }
                    return;
                }
            }

        } catch (Exception ex) {
            throw new EngineException("Failed to execute the getter because : " 
                    + ex.getMessage(),ex);
        }
        throw new EngineException("Failed to retrieve the resource ");
    }
    
    
    /**
     * This method is called to set the value.
     * 
     * @param entry The reference to the entry.
     * @param resource The resource entry
     * @throws EngineException 
     */
    private void executeSet(Session session, CallStatement.CallStatementEntry entry, 
            Resource resource) throws EngineException {
        try {
            OntologyClass ontologyClass = LsRDFTypeUtil.getOntologyClass(resource);
            List<OntologyProperty> properties = ontologyClass.listProperties();
            Object parameter = this.assignmentValue;
            String propertyName = entry.getName();
            for (OntologyProperty property : properties) {
                if (property.getLocalname().equalsIgnoreCase(propertyName)) {
                    String propertyType = property.getType().getURI().toString();
                                        if (XSDDataDictionary.isBasicTypeByURI(propertyType)) {
                        resource.setProperty(property.getURI().toString(),parameter);
                    } else {
                        RequestData dataParameter = (RequestData)parameter;
                        session.persist(dataParameter.getData());
                        resource.setProperty(property.getURI().toString(), 
                                session.get(Resource.class, 
                                new URI(dataParameter.getDataType() + "/" + 
                                dataParameter.getId())));
                    }
                    this.target.getData().setData(session.dumpXML());
                    return;
                }
            }

        } catch (Exception ex) {
            throw new EngineException("Failed to execute the getter because : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    
    
    
    /**
     * This method is called to set the result of the child call on the parent.
     * 
     * @param result
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        
    }
    
}
