/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2012  Rift IT Contracting
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
 * LsRDFTypeMethodStackEntry.java
 */
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
 * The implementation of the Leviathan 
 * 
 * @author brett chaldecott
 */
public class LsRDFTypeMethodStackEntry extends ProcessStackEntry {

    // private memember variables
    private CallStatement callStatement;
    private RequestData target;
    private List parameters = new ArrayList();
    
    
    /**
     * 
     * @param parent
     * @param variables
     * @param callStatement
     * @param target
     * @param parameters 
     */
    public LsRDFTypeMethodStackEntry(ProcessStackEntry parent,
            Map variables, CallStatement callStatement, Object target,
            List parameters) {
        super(parent.getProcessorMemoryManager(), parent, variables);
        this.callStatement = callStatement;
        this.target = (RequestData)target;
        this.parameters.addAll(parameters);
    }

    
    /**
     * The execute method.
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        try {
            RequestData data = (RequestData)target;
            Session session = XMLSemanticUtil.getSession();
            session.persist(data.getData());
            Resource resource = session.get(Resource.class, 
                    data.getDataType() + "/" + data.getId());
            
            // walk the resources
            resource = LsRDFTypeUtil.getResource(resource, 
                    callStatement.getEntries().subList(1,
                    callStatement.getEntries().size() - 1));
            
            // get the last entry
            CallStatement.CallStatementEntry entry = 
                    callStatement.getEntries().get(
                    callStatement.getEntries().size() -1);
            // if getter
            if (entry.getName().matches("get[A-Za-z0-9-]*")) {
                executeGetter(entry, resource);
            } else {
                executeSetter(session, entry, resource);
            }
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
    private void executeGetter(CallStatement.CallStatementEntry entry, 
            Resource resource) throws EngineException {
        try {
            OntologyClass ontologyClass = LsRDFTypeUtil.getOntologyClass(resource);
            List<OntologyProperty> properties = ontologyClass.listProperties();
            boolean found = false;
            
            String propertyName = stripMethodName(entry.getName());
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
                    } else {
                        String stringUri = resource.getURI().toString();
                        String id = stringUri.substring(stringUri.lastIndexOf("/") + 1);
                        this.getParent().setResult(new RequestData(
                                id, ontologyClass.getURI().toString(),
                                this.target.getData(), ontologyClass.getLocalName()));
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
    private void executeSetter(Session session, CallStatement.CallStatementEntry entry, 
            Resource resource) throws EngineException {
        if (parameters.size() != 1) {
            throw new EngineException("Must provide one parameter");
        }
        try {
            OntologyClass ontologyClass = LsRDFTypeUtil.getOntologyClass(resource);
            List<OntologyProperty> properties = ontologyClass.listProperties();
            Object parameter = this.parameters.get(0);
            boolean found = false;
            String propertyName = stripMethodName(entry.getName());
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
                    this.target.setData(session.dumpXML());
                    return;
                }
            }

        } catch (Exception ex) {
            throw new EngineException("Failed to execute the getter because : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method sets the result
     * 
     * @param result
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        
    }
    
    
    /**
     * This method assumes either [get] or [set] as a prefix and strips it.
     * 
     * @param name The name of the method
     * @return The reference to the stripped string.
     */
    public String stripMethodName(String name) {
        return name.substring(3);
    }
    
}
