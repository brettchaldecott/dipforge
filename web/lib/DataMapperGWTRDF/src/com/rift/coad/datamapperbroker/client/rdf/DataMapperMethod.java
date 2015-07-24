/*
 * DataMapperBrokerMBean: The data mapper broker client interface.
 * Copyright (C) 2009  2015 Burntjam
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
 * DataMapperMethod.java
 */

// package path
package com.rift.coad.datamapperbroker.client.rdf;


// the semantic import


// data type imports
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;


/**
 * This object represents a data mapper method.
 *
 * @author brett chaldecott
 */
public class DataMapperMethod extends ResourceBase {

    // private member variables
    private String service;
    private String name;
    private DataType result;

    /**
     * The required default constructor.
     */
    public DataMapperMethod() {
    }



    /**
     * This method sets the data mapper method.
     *
     * @param service The service name.
     * @param name The name of the method attached to this service.
     */
    public DataMapperMethod(String service, String name) {
        this.service = service;
        this.name = name;
        setDataTypeId();
    }


    /**
     * This constructor sets up all private member variables.
     *
     * @param service The service name.
     * @param name The name of the method.
     * @param result The result
     */
    public DataMapperMethod(String service, String name, DataType result) {
        this.service = name;
        this.name = name;
        setDataTypeId();
        this.result = result;
        setAssociatedObject();
    }




    /**
     * This method returns the service information.
     *
     * @return The string containing the service information.
     */
    public String getService() {
        return service;
    }


    /**
     * This method sets the service information.
     *
     * @param service The new service id.
     */
    public void setService(String service) {
        this.service = service;
    }


    /**
     * This method retrieves the name information.
     *
     * @return The string containing the name information.
     */
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the data mapper.
     *
     * @param name The string containing the name of the data mapper method.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * This method returns the result of the data mapper query.
     *
     * @return The data type that is a return result.
     */
    public DataType getResult() {
        return result;
    }
    

    /**
     * This method sets the result of the data mapper.
     *
     * @param result The data mapper reference.
     */
    public void setResult(DataType result) {
        this.result = result;
        setAssociatedObject();
    }


    /**
     * This method is called to set the data type id.
     */
    private void setDataTypeId() {
        super.setIdForDataType(this.getClass() + "." + service + "." + name);
    }


    /**
     * This method is called to set the associated object reference.
     */
    private void setAssociatedObject() {
        if ((result != null) && (result.getAssociatedObject() == null)) {
            result.setAssociatedObject(this.getIdForDataType());
        }
    }


    /**
     * The required default constructor.
     */
    public static DataMapperMethod createInstance() {
        DataMapperMethod method = new DataMapperMethod();
        method.setIdForDataType(Constants.DATA_MAPPER_METHOD);
        method.setBasicType(Constants.DATA_MAPPER_METHOD);
        return method;

    }



    /**
     * This method sets the data mapper method.
     *
     * @param service The service name.
     * @param name The name of the method attached to this service.
     */
    public static DataMapperMethod createInstance(String service, String name) {
        DataMapperMethod method = createInstance();
        method.setService(service);
        method.setName(name);
        method.setDataTypeId();
        return method;
    }


    /**
     * This constructor sets up all private member variables.
     *
     * @param service The service name.
     * @param name The name of the method.
     * @param result The result
     */
    public static DataMapperMethod createInstance(String service, String name, DataType result) {
        DataMapperMethod method = createInstance();
        method.setService(service);
        method.setName(name);
        method.setDataTypeId();
        method.setResult(result);
        method.setAssociatedObject();
        return method;
    }
}
