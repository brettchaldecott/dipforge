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
 * CaseBlock.java
 */

// package path
package com.rift.coad.change.rdf.objmapping.change.task.logic;

// coadunation rdf
import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import com.rift.coad.rdf.objmapping.base.DataType;

// block statement.
import com.rift.coad.change.rdf.objmapping.change.task.Block;

// semantics
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 *
 * @author brett
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("CaseBlock")
public class CaseBlock extends Block {
    // private member variables
    private DataType comparisonData;

    /**
     * The default constructor
     */
    public CaseBlock() {
    }


    /**
     * Set all relevant parameters.
     * @param name The name of the case.
     * @param description The description.
     * @param child The child block
     * @param comparisonData The comparison data.
     */
    public CaseBlock(String name, String description, ActionTaskDefinition child, DataType comparisonData) {
        super(name, description, child);
        this.comparisonData = comparisonData;
    }


    /**
     * This method returns the data that the switch comparison must be performed on.
     *
     * @return The object to perform the swith comparison on.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ComparisonData")
    public DataType getComparisonData() {
        return comparisonData;
    }


    /**
     * The method that sets the data that the switch comparison must be performed on.
     *
     * @param comparisonData The data to perform the comparison on.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ComparisonData")
    public void setComparisonData(DataType comparisonData) {
        this.comparisonData = comparisonData;
    }


    


}
