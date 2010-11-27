/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2010  Rift IT Contracting
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
 * VariableNameHolder.java
 */

// package path
package com.rift.coad.rdf.objmapping.base;

import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

/**
 * This is a name holder object.
 *
 * @author brett
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("VariableNameHolder")
public class VariableNameHolder extends DataType {

    /**
     * The default constructor for the name holder.
     */
    public VariableNameHolder() {

    }


    /**
     * This constructor sets the name for the name holder.
     *
     * @param name
     */
    public VariableNameHolder(String name) {
        super.setDataName(name);
    }



    /**
     * This method returns the id that identifies this object.
     *
     * @return The string containing the object id.
     */
    @Override
    public String getObjId() {
        return getDataName();
    }

}
