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
package com.rift.coad.datamapperbroker.rdf;


// the semantic import
import com.rift.coad.lib.common.RandomGuid;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;


// data type imports
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.resource.ResourceBase;
import thewebsemantic.Identifier;


/**
 * This object represents a data mapper method.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/datamapper#")
@RdfType("DataMapperMethod")
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
        setIdForDataType();
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
        setIdForDataType();
        this.result = result;
        setAssociatedObject();
    }




    /**
     * This method returns the id of the object.
     *
     * @return The string containing the object id.
     */
    @Override
    public String getObjId() {
        return service + "." + name;
    }


    /**
     * This method returns the service information.
     *
     * @return The string containing the service information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethodServiceId")
    @Identifier()
    public String getService() {
        return service;
    }


    /**
     * This method sets the service information.
     *
     * @param service The new service id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethodServiceId")
    public void setService(String service) {
        this.service = service;
    }


    /**
     * This method retrieves the name information.
     *
     * @return The string containing the name information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethodName")
    @Identifier()
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the data mapper.
     *
     * @param name The string containing the name of the data mapper method.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperMethodName")
    public void setName(String name) {
        this.name = name;
    }


    /**
     * This method returns the result of the data mapper query.
     *
     * @return The data type that is a return result.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperResult")
    public DataType getResult() {
        return result;
    }
    

    /**
     * This method sets the result of the data mapper.
     *
     * @param result The data mapper reference.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/datamapper#DataMapperResult")
    public void setResult(DataType result) {
        this.result = result;
        setAssociatedObject();
    }


    /**
     * This method is called to set the data type id.
     */
    private void setIdForDataType() {
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
     * This method returns true if the objects are equal.
     *
     * @param obj The object to perform the comparision on.
     * @return TRUE if equal, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataMapperMethod other = (DataMapperMethod) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code value to perform the comparision on.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        String result = "[Service: " + this.service + "\n" +
                "Name: " + this.name + "\n" +
                "Result: " + this.result.toString() + "\n" +
                "Arguments: " + super.toString() + "]";
        return result;
    }





}
