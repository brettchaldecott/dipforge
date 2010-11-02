/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.base;

// semantic imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;
import thewebsemantic.Id;

// coadunation imports
import com.rift.coad.rdf.objmapping.base.Name;


/**
 * This object a unique identifier.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base/")
@RdfType("UniqueId")
public abstract class UniqueId extends DataType {

    /**
     * The getter and setter for the id number value.
     *
     * @return The value contained within.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#IdValue")
    public abstract String getValue();


    /**
     * This method sets the value of the id number.
     *
     * @param value The new value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#IdValue")
    public abstract void setValue(String value);
}
