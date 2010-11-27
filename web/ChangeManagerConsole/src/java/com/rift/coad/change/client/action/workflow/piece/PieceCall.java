/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2009  Rift IT Contracting
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
 * PieceCall.java
 */


// package path
package com.rift.coad.change.client.action.workflow.piece;

// smart gwt imports
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.smartgwt.client.widgets.Canvas;

// coadunation imports
import com.rift.coad.change.client.action.workflow.DroppedPiece;
import com.rift.coad.change.client.action.workflow.Icons;
import com.rift.coad.change.client.action.workflow.ParamInfo;
import com.rift.coad.change.client.action.workflow.ParamInfoFilter;
import com.rift.coad.change.client.action.workflow.PropertyFactory;
import com.rift.coad.change.client.action.workflow.TaskDefinitionManager;
import com.rift.coad.change.client.action.workflow.piece.call.DataMapperManagerConnector;
import com.rift.coad.change.client.action.workflow.piece.call.JNDICallbackHandler;
import com.rift.coad.change.client.action.workflow.piece.call.MethodCallbackHandler;
import com.rift.coad.change.client.action.workflow.piece.call.ParameterMappingCanvas;
import com.rift.coad.change.client.action.workflow.piece.call.ServiceCache;
import com.rift.coad.change.client.action.workflow.piece.resource.ResourceCache;
import com.rift.coad.change.rdf.objmapping.client.change.task.Call;
import com.rift.coad.datamapperbroker.client.rdf.DataMapperMethod;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SectionItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import java.util.ArrayList;
import java.util.List;

/**
 * This object represents a call statement.
 *
 * @author brett chaldecott
 */
public class PieceCall  extends DroppedPiece implements PropertyFactory, TaskDefinitionManager {

    public class ServiceChangedHandler implements ChangedHandler {

        private PieceCall call;
        /**
         * The default constructor
         */
        public ServiceChangedHandler(PieceCall call) {
            this.call = call;
        }
        

        /**
         * The on change method.
         *
         * @param event The events to deal with.
         */
        public void onChanged(ChangedEvent event) {
            Object value = event.getValue();
            if (value == null) {
                jndiReference.clearValue();
                jndiReference.setDisabled(true);
                methodName.clearValue();
                methodName.setDisabled(true);
                clearMethod();
            } else {
                jndiReference.clearValue();
                jndiReference.setDisabled(false);
                DataMapperManagerConnector.getService().
                        listJNDIForService(value.toString(), new JNDICallbackHandler(call));
                methodName.clearValue();
                methodName.setDisabled(false);
                DataMapperManagerConnector.getService().listMethods(value.toString(),
                        new MethodCallbackHandler(call));
                clearMethod();
            }
        }

    }


    /**
     * This object handles the changing of the method name.
     */
    public class MethodNameChangedHandler implements ChangedHandler {

        /**
         * The method that is called on change event.
         *
         * @param event The on changed event to handle
         */
        public void onChanged(ChangedEvent event) {
            if (event.getValue() == null) {
                clearMethod();
            } else {
                for (DataMapperMethod currentMethod : methods) {
                    if (currentMethod.getName().equals(event.getValue().toString())) {
                        setMethod(currentMethod);
                        return;
                    }
                }
            }
        }

    }

    // variable
    private Call call;

    // private member variables
    private DynamicForm form;
    private TextItem name;
    private TextItem description;
    private SelectItem serviceName;
    private SelectItem jndiReference;
    private SelectItem methodName;
    private DataMapperMethod[] methods;

    // the data mapper method
    private DataMapperMethod method;
    
    // result parameter
    private TextItem resultHeader;
    private SelectItem results;

    // parameter mapping
    private ParameterMappingCanvas parameterMapping;



    /**
     * The default constructor
     */
    public PieceCall() {
        super(Icons.CALL);

        // get the services
        name = new TextItem();
        name.setName("Name");
        name.setValue(IDGenerator.getId());
        description = new TextItem();
        description.setName("Description");
        description.setValue(IDGenerator.getId());
    }


    /**
     * The call task.
     *
     * @param call
     */
    public PieceCall(Call call) throws Exception {
        super(Icons.CALL);
        try {
            this.call = call;

            // get the services
            name = new TextItem();
            name.setName("Name");
            name.setValue(call.getName());
            description = new TextItem();
            description.setName("Description");
            description.setValue(call.getDescription());

            this.method = call.getDataMapperMethod();
        } catch (Exception ex) {
            throw new Exception("Failed to instanciate a piece call because : "
                    + ex.getMessage());
        }
    }


    /**
     * This method returns the property panel information.
     *
     * @return The link to the property panel information.
     */
    public Canvas getPropertyPanel() {
        if (form != null) {
            if (method != null) {
                results.setValueMap(ParamInfoFilter.convertParameterToArray(
                        ParamInfoFilter.filterParameters(this.getBin().listParameters(),
                        method.getResult().getIdForDataType())));
            }
            return form;
        }
        form = new DynamicForm();

        

        // return type
        serviceName = new SelectItem();
        serviceName.setName("Service");
        serviceName.setValueMap(ServiceCache.getInstance().getServices());
        serviceName.addChangedHandler(new ServiceChangedHandler(this));

        if (call != null) {
            serviceName.setValue(call.getDataMapperMethod().getService());
        }

        // jndi reference
        jndiReference = new SelectItem();
        jndiReference.setName("JNDI");
        if (call != null) {
            DataMapperManagerConnector.getService().
                        listJNDIForService(call.getDataMapperMethod().getService(),
                        new JNDICallbackHandler(this,call.getJndi()));
        }

        // method
        methodName = new SelectItem();
        methodName.setName("Method");
        methodName.addChangedHandler(new MethodNameChangedHandler());
        if (call != null) {
            DataMapperManagerConnector.getService().listMethods(
                    call.getDataMapperMethod().getService(),
                    new MethodCallbackHandler(this,call.getDataMapperMethod().getName()));
        }

        resultHeader = new TextItem();
        resultHeader.setName("Result");
        resultHeader.setWidth(300);
        resultHeader.setDisabled(true);
        if (call != null && call.getDataMapperMethod().getResult() != null) {
            resultHeader.setValue(call.getDataMapperMethod().getResult().getIdForDataType());
        }


        results = new SelectItem();
        results.setName("Variables");
        results.setWidth(300);
        results.setDisabled(true);
        if ((call != null) && 
                !ResourceCache.getInstance().isSystemResource(
                call.getDataMapperMethod().getResult().getIdForDataType())) {
            results.setDisabled(false);
            results.setValueMap(ParamInfoFilter.convertParameterToArray(
                        ParamInfoFilter.filterParameters(this.getBin().listParameters(),
                        method.getResult().getIdForDataType())));
            if (call.getResult() != null) {
                results.setValue(call.getResult());
            }
        }

        // the parameter fields
        // parameters
        // the form canvas
        parameterMapping = new ParameterMappingCanvas(this.getBin(),call);
        CanvasItem formCanvas = new CanvasItem();
        formCanvas.setName("Parameters");
        formCanvas.setCanvas(parameterMapping);


        form.setFields(new FormItem[]{name,description, new SpacerItem(),
            serviceName,jndiReference,methodName,resultHeader,
            results,formCanvas});


        return form;
    }


    /**
     * This method returns the task definition.
     *
     * @return The task definition.
     */
    public ActionTaskDefinition getTaskDefinition() throws Exception {
        if (form == null) {
            if (call != null) {
                return call;
            }
            throw new Exception("The call has not been configured.");
        }
        Call result = Call.createInstance();
        if (call != null) {
            result.setTaskDefinitionId(call.getTaskDefinitionId());
        }
        result.setName(name.getValue().toString());
        result.setDescription(description.getValue().toString());
        if (this.method == null) {
            throw new Exception("The method name has not been set to create call.");
        }
        result.setDataMapperMethod(this.method);
        if (this.jndiReference.getValue() != null) {
            result.setJndi(this.jndiReference.getValue().toString());
        }
        if ((this.method.getResult() != null) && 
                ResourceCache.getInstance().isSystemResource(this.method.getResult().getIdForDataType())) {
            result.setResult(this.method.getResult().getDataName());
        } else if ((this.method.getResult() != null) &&
                !ResourceCache.getInstance().isSystemResource(this.method.getResult().getIdForDataType())) {
            Object resultObj = results.getValue();
            if (resultObj == null) {
                throw new Exception("Must provide a return variable.");
            }
            result.setResult(resultObj.toString());
        }
        result.setParameters(this.parameterMapping.getParameters());
        this.call = result;
        return result;
    }


    /**
     * This method is called to set the list of services.
     *
     * @param services The list of services.
     */
    public void setServices(String[] services) {
        this.serviceName.setValueMap(services);
    }


    /**
     * This method returns the jndi reference.
     *
     * @return The jndi reference.
     */
    public SelectItem getJndiReference() {
        return jndiReference;
    }


    /**
     * This method is called to set all the method names.
     * @param methods The list of methods.
     */
    public void setMethods(DataMapperMethod[] methods) {
        setMethods(methods,null);
    }

    
    /**
     * This method sets the method information for the call.
     * 
     * @param methods The list of methods.
     * @param methodName The name of the currently selected method.
     */
    public void setMethods(DataMapperMethod[] methods, String methodName) {
        this.methods = methods;
        this.methodName.clearValue();
        String[] methodNames = new String[methods.length];
        for (int index = 0; index < methods.length; index++) {
            methodNames[index] = methods[index].getName();
        }
        this.methodName.setValueMap(methodNames);
        if (methodName != null) {
            this.methodName.setValue(methodName);
        }

    }

    /**
     * This method is called to set the method information.
     *
     * @param method The method to set.
     */
    private void setMethod(DataMapperMethod method) {

        this.method = method;

        setResult(method);


        this.parameterMapping.setMethod(method);
    }


    /**
     * The method to clear.
     */
    public void clearMethod() {
        resultHeader.clearValue();
        resultHeader.setDisabled(true);

        results.clearValue();
        results.setDisabled(true);

        this.parameterMapping.clearMethod();
    }


    /**
     * This method is called to setup the result information
     *
     * @param method The result information.
     */
    private void setResult(DataMapperMethod method) {
        // set the return result.
        if (method.getResult() == null) {
            resultHeader.setDisabled(true);
            resultHeader.clearValue();
            results.setDisabled(true);
            results.clearValue();
            return;
        }

        if (ResourceCache.getInstance().isSystemResource(method.getResult().getIdForDataType())) {
            resultHeader.setDisabled(true);
            resultHeader.setValueMap(method.getResult().getIdForDataType());
            resultHeader.setValue(method.getResult().getDataName());
            results.setDisabled(true);
            return;
        }

        List<ParamInfo> flowParameters = this.getBin().listParameters();

        resultHeader.setDisabled(true);
        resultHeader.setValue(method.getResult().getIdForDataType());

        results.setDisabled(false);
        results.setValueMap(ParamInfoFilter.convertParameterToArray(ParamInfoFilter.filterParameters(flowParameters, method.getResult().getIdForDataType())));
    }


    
}
