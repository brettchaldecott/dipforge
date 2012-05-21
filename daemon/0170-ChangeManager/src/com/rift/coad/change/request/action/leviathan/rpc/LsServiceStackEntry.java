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
 * LsServiceStackEntry.java
 */
package com.rift.coad.change.request.action.leviathan.rpc;

import com.rift.coad.change.request.RequestData;
import com.rift.coad.change.request.action.leviathan.requestdata.LsActionRDFData;
import com.rift.coad.change.request.action.leviathan.requestdata.LsActionRDFProperty;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.datamapper.DataMapper;
import com.rift.coad.datamapper.DataMapperAsync;
import com.rift.coad.datamapperbroker.DataMapperBrokerConstants;
import com.rift.coad.datamapperbroker.DataMapperBrokerDaemon;
import com.rift.coad.datamapperbroker.DataMapperBrokerDaemonAsync;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.XMLSemanticUtil;
import com.rift.coad.rdf.types.mapping.MethodMapping;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.LeviathanConstants;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This object manages the service stack information.
 * 
 * @author brett chaldecott
 */
public class LsServiceStackEntry extends ProcessStackEntry {
    
    // private member variables
    private CallStatement callStatement;
    private ServiceRPCCall call;
    private List parameters = new ArrayList();
    private boolean executed = false;
    private Object result;
    
    /**
     * The constructor of the ls store method
     *
     * @param parent The reference to the parent stack entry.
     * @param variables The variables
     * @param callStatement The call statement
     * @param target The target
     * @param parameters The parameters
     */
    public LsServiceStackEntry(ProcessStackEntry parent,
            Map variables, CallStatement callStatement, Object target,
            List parameters) {
        super(parent.getProcessorMemoryManager(), parent, variables);
        this.callStatement = callStatement;
        this.call = (ServiceRPCCall) target;
        this.parameters.addAll(parameters);
    }
    
    
    /**
     * This method is used to execute the service call.
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        try {
            if (!this.executed) {
                DataMapperBrokerDaemon daemon = (DataMapperBrokerDaemon)
                        ConnectionManager.getInstance().getConnection(
                        DataMapperBrokerDaemon.class,
                        "datamapper/BrokerDaemon");
                List<MethodMapping> methods = daemon.listMethodsByService(this.call.getService(), 
                        this.call.getProject(), this.call.getClassName());
                CallStatement.CallStatementEntry entry = 
                    callStatement.getEntries().get(
                    callStatement.getEntries().size() -1);
                
                for (MethodMapping method : methods) {
                    if (!method.getMethodName().equals(entry.getName())) {
                        continue;
                    }
                    if (method.getParameters().size() != parameters.size()) {
                        continue;
                    }
                    List parameters = new ArrayList();
                    for (Object parameter: this.parameters) {
                        if (parameter instanceof LsActionRDFProperty) {
                            LsActionRDFProperty property = 
                                    (LsActionRDFProperty)parameter;
                            RequestData data = new RequestData(
                                    property.getId(), property.getDataTypeUri(),
                                    property.getData().getData(), property.getName());
                            parameters.add(data);
                        } else {
                            parameters.add(parameter);
                        }
                    }
                    
                    List<String> services = new ArrayList<String>();
                    services.add(this.call.getService());
                    DataMapperAsync dataMapperBroker = (DataMapperAsync)RPCMessageClient.create(
                            "change/request/action/ActionHandler", DataMapper.class, 
                            DataMapperAsync.class, services, false,
                            this.getParent().getProcessorMemoryManager().getGuid());
                    dataMapperBroker.execute(method, parameters);
                    executed = true;
                    this.getProcessorMemoryManager().setState(LeviathanConstants.Status.SUSPENDED);
                    return;
                }
                throw new EngineException("No method found matching the "
                        + "configuration [" + this.call.getService() + "][" +
                        this.call.getProject() + "][" + this.call.getClassName() 
                        + "][" + entry.getName() + "] to execute got [" + 
                        methods.toString() + "]");
            } else {
                if (this.result instanceof RequestData) {
                    RequestData data = (RequestData)result;
                    LsActionRDFData rdfData = 
                            (LsActionRDFData)this.getParent().getVariable(
                            LsActionRDFProperty.RDF_GLOBAL_PROPERTY);
                    Session session = XMLSemanticUtil.getSession();
                    session.persist(rdfData.getData());
                    session.persist(data.getData());
                    rdfData.setData(session.dumpXML());
                    this.getParent().setResult(new LsActionRDFProperty(
                                data.getName(), data.getDataType() + "/" + 
                                data.getId(), data.getDataType(),rdfData));
                } else {
                    this.getParent().setResult(result);
                }
                pop();
            }
        } catch (Exception ex) {
            throw new EngineException("Failed to execute service stack because :"
                    + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method sets the result.
     * 
     * *@param result This method sets the result
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        this.result = result;
        this.getProcessorMemoryManager().setState(LeviathanConstants.Status.RUNNING);
    }
    
}
