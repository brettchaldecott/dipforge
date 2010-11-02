/*
 * ChangeControlRDF: The rdf information for the change control system.
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
 * Call.java
 */

package com.rift.coad.change.rdf.objmapping.client.change.task;

import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.Constants;
import com.rift.coad.datamapperbroker.client.rdf.DataMapperMethod;

/**
 * This object represents a call to an external system.
 *
 * @author brett chaldecott
 */
public class Call extends ActionTaskDefinition {
    private String jndi;
    private DataMapperMethod dataMapperMethod;
    private String[] parameters;
    private String result;


    /**
     * The default constructor
     */
    public Call() {
    }


    /**
     * A constructor that does not set the next task.
     * @param name The name of the next task.
     * @param description The description of the next task.
     * @param dataMapperMethod The data mapper method.
     */
    public Call(String name, String description, DataMapperMethod dataMapperMethod) {
        super(name, description);
        this.dataMapperMethod = dataMapperMethod;
        setAssociatedObjectInfo(dataMapperMethod);
    }


    /**
     * This constructor sets all the internal parameters.
     *
     * @param name The name of this task.
     * @param description The description of this task.
     * @param next The next task.
     * @param dataMapperMethod The method containing the data mapper id.
     */
    public Call(String name, String description, ActionTaskDefinition next,
            DataMapperMethod dataMapperMethod) {
        super(name, description, next);
        this.dataMapperMethod = dataMapperMethod;
        setAssociatedObjectInfo(dataMapperMethod);
    }


    /**
     * This constructor sets all internal values.
     *
     * @param name The name of the call.
     * @param description The description of the call.
     * @param next The next task.
     * @param serviceId The service Id.
     * @param jndi The jndi reference for the service id.
     * @param dataMapperMethod The data mapper method.
     */
    public Call(String name, String description, ActionTaskDefinition next,
            String jndi, DataMapperMethod dataMapperMethod) {
        super(name, description, next);
        this.jndi = jndi;
        this.dataMapperMethod = dataMapperMethod;
        setAssociatedObjectInfo(dataMapperMethod);
    }


    /**
     * This constructor sets all the member variables.
     *
     * @param id The id of this call.
     * @param name The name of this call.
     * @param description The description of this call.
     * @param next The next call reference.
     * @param jndi The jndi reference for the service.
     * @param dataMapperMethod The data mapper method to make the call onto.
     * @param parameters The names of the parameter variables.
     * @param result The names of the result variables.
     */
    public Call(String id, String name, String description, ActionTaskDefinition next,
            String jndi, DataMapperMethod dataMapperMethod, String[] parameters, String result) {
        super(id, name, description, next);
        this.jndi = jndi;
        this.dataMapperMethod = dataMapperMethod;
        this.parameters = parameters;
        this.result = result;
    }


    /**
     * This method returns the jndi url for the call.
     * 
     * @return The string containing the jndi information.
     */
    public String getJndi() {
        return jndi;
    }


    /**
     * This method sets the jndi url.
     *
     * @param jndi The string containing the new jndi url.
     */
    public void setJndi(String jndi) {
        this.jndi = jndi;
    }




    /**
     * This method returns the data mapper method name.
     *
     * @return The string containing the data mapper method name.
     */
    public DataMapperMethod getDataMapperMethod() {
        return dataMapperMethod;
    }


    /**
     * The setter of the data mapper method.
     *
     * @param dataMapperMethod The string containing the data mapper method name.
     */
    public void setDataMapperMethod(DataMapperMethod dataMapperMethod) {
        this.dataMapperMethod = dataMapperMethod;
        setAssociatedObjectInfo(dataMapperMethod);
    }


    /**
     * This method is used to guarantee the uniqueness of any parameters added to a block.
     *
     * @param parameter The parameter to guarantee the uniqueness of.
     */
    private void setAssociatedObjectInfo(DataMapperMethod dataMapperMethod) {
        if (dataMapperMethod.getAssociatedObject() == null) {
            // ignore within the 
        }
    }


    /**
     * The parameters that are getting pulled from the stack.
     *
     * @return The list of parameter names that will get pulled from the stack.
     */
    public String[] getParameters() {
        return parameters;
    }


    /**
     * This method sets the parameters.
     *
     * @param parameters The list of parameters that are getting mapped to the method parameters.
     */
    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }


    /**
     * This method returns the name of the result variable that will be populated in the stack.
     *
     * @return The name of the result variable that will be populated in the stack.
     */
    public String getResult() {
        return result;
    }


    /**
     * This method sets the result call.
     *
     * @param result The result variable.
     */
    public void setResult(String result) {
        this.result = result;
    }



    /**
     * The default constructor
     */
    public static Call createInstance() {
        Call call = new Call();
        call.setIdForDataType(Constants.CALL);
        call.setBasicType(Constants.CALL);
        return call;
    }


    /**
     * A constructor that does not set the next task.
     * @param name The name of the next task.
     * @param description The description of the next task.
     * @param dataMapperMethod The data mapper method.
     */
    public static Call createIntance(String name, String description, DataMapperMethod dataMapperMethod) {
        Call call = createInstance();
        call.setName(name);
        call.setDescription(description);
        call.setDataMapperMethod(dataMapperMethod);
        return call;
    }


    /**
     * This constructor sets all the internal parameters.
     *
     * @param name The name of this task.
     * @param description The description of this task.
     * @param next The next task.
     * @param dataMapperMethod The method containing the data mapper id.
     */
    public static Call createInstance(String name, String description, ActionTaskDefinition next,
            DataMapperMethod dataMapperMethod) {
        Call call = createInstance();
        call.setName(name);
        call.setDescription(description);
        call.setNext(next);
        call.setDataMapperMethod(dataMapperMethod);
        return call;
    }


    /**
     * This constructor sets all internal values.
     *
     * @param name The name of the call.
     * @param description The description of the call.
     * @param next The next task.
     * @param serviceId The service Id.
     * @param jndi The jndi reference for the service id.
     * @param dataMapperMethod The data mapper method.
     */
    public static Call createInstance(String name, String description, ActionTaskDefinition next,
            String jndi, DataMapperMethod dataMapperMethod) {
        Call call = createInstance();
        call.setName(name);
        call.setDescription(description);
        call.setNext(next);
        call.setJndi(jndi);
        call.setDataMapperMethod(dataMapperMethod);
        return call;
    }


    /**
     * This constructor sets all the member variables.
     *
     * @param id The id of this call.
     * @param name The name of this call.
     * @param description The description of this call.
     * @param next The next call reference.
     * @param jndi The jndi reference for the service.
     * @param dataMapperMethod The data mapper method to make the call onto.
     * @param parameters The names of the parameter variables.
     * @param result The names of the result variables.
     */
    public static Call createInstance(String id, String name, String description, ActionTaskDefinition next,
            String jndi, DataMapperMethod dataMapperMethod, String[] parameters, String result) {
        Call call = createInstance();
        call.setName(name);
        call.setDescription(description);
        call.setNext(next);
        call.setJndi(jndi);
        call.setDataMapperMethod(dataMapperMethod);
        call.setParameters(parameters);
        call.setResult(result);
        return call;
    }
}
