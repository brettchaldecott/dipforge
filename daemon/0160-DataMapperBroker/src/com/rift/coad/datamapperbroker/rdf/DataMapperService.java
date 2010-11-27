/*
 * DataMapperBroker: The implementation of the data mapper broker
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
 * DataMapperBrokerDaemonImpl.java
 */


// package path
package com.rift.coad.datamapperbroker.rdf;

// java imports
import com.rift.coad.rdf.objmapping.base.DataType;
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This object responsible for representing a data mapper service.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/datamapperinternal#")
@RdfType("DataMapperService")
public class DataMapperService extends DataType {
    // private member variables
    private String serviceId;
    private DataMapperMethod[] methods;

    /**
     * The default constructor
     */
    public DataMapperService() {
    }

    
    /**
     * This constructor sets the service id.
     * 
     * @param serviceId The service id.
     */
    public DataMapperService(String serviceId) {
        this.serviceId = serviceId;
    }


    /**
     * This constructor sets the service id and methods.
     *
     * @param serviceId The service id to set.
     * @param methods The methods.
     */
    public DataMapperService(String serviceId, DataMapperMethod[] methods) {
        this.serviceId = serviceId;
        this.methods = methods;
    }


    /**
     * This method returns the object id.
     *
     * @return The object id.
     */
    @Override
    public String getObjId() {
        return serviceId;
    }


    /**
     * This method returns the 
     * @return
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/datamapperinternal#DataMapperServiceMethods")
    public DataMapperMethod[] getMethods() {
        return methods;
    }


    /**
     * This method sets the methods attached to a service.
     *
     * @param methods The methods attached to a service
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/datamapperinternal#DataMapperServiceMethods")
    public void setMethods(DataMapperMethod[] methods) {
        this.methods = methods;
    }


    /**
     * This method returns the name the service id.
     *
     * @return The service id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/datamapperinternal#DataMapperServiceId")
    @Identifier()
    public String getServiceId() {
        return serviceId;
    }


    /**
     * This method sets the service id.
     *
     * @param serviceId The string containing the new service id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/datamapperinternal#DataMapperServiceId")
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }



    
}
