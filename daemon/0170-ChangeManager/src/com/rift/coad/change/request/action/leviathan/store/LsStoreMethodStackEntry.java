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
 * LsStoreMethodStackEntry.java
 */

package com.rift.coad.change.request.action.leviathan.store;

import com.rift.coad.change.request.action.leviathan.requestdata.LsActionRDFProperty;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.rdf.semantic.Property;
import com.rift.coad.rdf.semantic.RDFConstants;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.XMLSemanticUtil;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.rdf.store.master.Constants;
import com.rift.dipforge.rdf.store.master.MasterRDFStoreDaemon;
import com.rift.dipforge.rdf.store.master.MasterRDFStoreDaemonAsync;
import com.rift.dipforge.rdf.store.master.StoreActions;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * The implementation of the LsStoreMethod stack entry
 * 
 * @author brett chaldecott
 */
public class LsStoreMethodStackEntry extends ProcessStackEntry {
    
    private static Logger log = Logger.getLogger(LsStoreMethodStackEntry.class);
    
    // private memember variables
    private CallStatement callStatement;
    private LsStoreProperty target;
    private List parameters = new ArrayList();

    /**
     * The constructor of the ls store method
     *
     * @param parent The reference to the parent stack entry.
     * @param variables The variables
     * @param callStatement The call statement
     * @param target The target
     * @param parameters The parameters
     */
    public LsStoreMethodStackEntry(ProcessStackEntry parent,
            Map variables, CallStatement callStatement, Object target,
            List parameters) {
        super(parent.getProcessorMemoryManager(), parent, variables);
        this.callStatement = callStatement;
        this.target = (LsStoreProperty) target;
        this.parameters.addAll(parameters);
    }

    /**
     * This method is called to execute the store method.
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        try {
            if (callStatement.getEntries().size() != 2) {
                throw new EngineException("Invalid request on the Store object. "
                        + "Expected either persist or remove got : " + callStatement.toString());
            }
            String method = callStatement.getEntries().get(1).getName();
            if (method.equals("persist")) {
                persist(StoreActions.PERSIST);
            } else if (method.equals("remove")) {
                persist(StoreActions.REMOVE);
            } else {
                throw new EngineException(
                        "Can either call persist or remove on this object not : "
                        + method);
            }
            pop();
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException("Failed to execute the call because : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method is called to set the result
     *
     * @param result
     * @throws EngineException
     */
    @Override
    public void setResult(Object result) throws EngineException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * This method is called to persist the information.
     *
     * @throws EngineException
     */
    private void persist(String action) throws EngineException {
        try {
            List<String> services = new ArrayList<String>();
            services.add(Constants.SERVICE);

            MasterRDFStoreDaemonAsync daemon = (MasterRDFStoreDaemonAsync)
                    RPCMessageClient.createOneWay(
                    "change/request/action/ActionFactoryManager", 
                    MasterRDFStoreDaemon.class,
                    MasterRDFStoreDaemonAsync.class, services, false);
            for (Object parameter : this.parameters) {
                String xml = getXML(parameter);
                daemon.persist(action, xml);
            }
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException("Failed to persist the action : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the XML defining the data information.
     *
     * @param parameter The parameter.
     * @return The xml.
     * @throws EngineException
     */
    private String getXML(Object parameter) throws EngineException {
        try {
            LsActionRDFProperty data = (LsActionRDFProperty) parameter;
            Session sourceSession = XMLSemanticUtil.getSession();
            sourceSession.persist(data.getData().getData());
            Resource sourceResource = sourceSession.get(Resource.class, data.getUri());

            Session resultSession = XMLSemanticUtil.getSession();
            copyResource(sourceSession,resultSession, sourceResource);
            
            return resultSession.dumpXML();
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException(
                    "This method returns the xml value for the object : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method is called to copy the resource
     *
     * @param resultSession
     * @param sourceResource
     * @return
     * @throws EngineException
     */
    public Resource copyResource(Session sourceSession,
            Session resultSession, Resource sourceResource)
            throws EngineException {
        try {
            PersistanceIdentifier typeIdentifier =
                    PersistanceIdentifier.getInstance(RDFConstants.SYNTAX_NAMESPACE,
                    RDFConstants.TYPE_LOCALNAME);
            OntologyClass ontologyClass =
                    sourceResource.getProperty(OntologyClass.class,
                    typeIdentifier.toURI().toString());
            Resource resultResource = resultSession.createResource(
                    ontologyClass.getURI(), sourceResource.getURI());
            for (OntologyProperty property : ontologyClass.listProperties()) {
                
                
                // if this does not have a range just add the property.
                String propertyURI = property.getURI().toString();
                List propertyValues = sourceResource.listProperties(propertyURI);
                for (Object objPropertyValue : propertyValues) {
                    Property propertyValue = (Property)objPropertyValue;
                    if (!sourceResource.hasProperty(propertyURI)) {
                        continue;
                    }

                    if (!property.hasRange()) {
                        Resource sourceSubResource = propertyValue.get(Resource.class);
                        if (resultSession.contains(Resource.class,
                                sourceSubResource.getURI())) {
                            resultResource.addProperty(propertyURI,
                                    resultSession.get(Resource.class,
                                    sourceSubResource.getURI()));
                        } else {
                            String targetUri = sourceSubResource.get(String.class);
                            log.info("##########################");
                            log.info("The property URI is : " + propertyURI);
                            log.info("The property URI is : " + property.getType().getURI());
                            log.info("The target uri is : " + targetUri);
                            log.info("##########################");
                            resultResource.addProperty(propertyURI,
                                    resultSession.createResource(
                                    property.getType().getURI(),
                                    new URI(targetUri)));
                        }
                        continue;
                    }
                    String propertyType = property.getType().getURI().toString();
                    if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_STRING).getURI().toString())) {
                        resultResource.addProperty(propertyURI,
                                    propertyValue.get(String.class));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                        resultResource.addProperty(propertyURI,
                                    propertyValue.get(Boolean.class));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                        resultResource.addProperty(propertyURI,
                                    propertyValue.get(Float.class));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                        resultResource.addProperty(propertyURI,
                                    propertyValue.get(Double.class));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                        resultResource.addProperty(propertyURI,
                                    propertyValue.get(Integer.class));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_LONG).getURI().toString())) {
                        resultResource.addProperty(propertyURI,
                                    propertyValue.get(Long.class));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_INT).getURI().toString())) {
                        resultResource.addProperty(propertyURI,
                                    propertyValue.get(Boolean.class));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                        resultResource.addProperty(propertyURI,
                                    propertyValue.get(Short.class));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                        resultResource.addProperty(propertyURI,
                                    propertyValue.get(Byte.class));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_DATE).getURI().toString())) {
                        resultResource.addProperty(propertyURI,
                                    propertyValue.get(Date.class));
                    } else if (propertyType.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                        resultResource.addProperty(propertyURI,
                                    propertyValue.get(Date.class));
                    } else {
                        log.info("#### add new resource " + 
                                propertyValue.get(Resource.class).getURI().toString());
                        resultResource.addProperty(propertyURI,
                                    copyResource(sourceSession,resultSession,
                                    propertyValue.get(Resource.class)));
                    }
                }
            }
            return resultResource;
        } catch (Exception ex) {
            throw new EngineException("Failed to copy the resource : "
                    + ex.getMessage(), ex);
        }
    }
}
