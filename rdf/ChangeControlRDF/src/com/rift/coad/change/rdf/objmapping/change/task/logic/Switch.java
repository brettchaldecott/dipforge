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
 * Switch.java
 */

// package path
package com.rift.coad.change.rdf.objmapping.change.task.logic;

// action task import
import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;

// the semantic imports
import com.rift.coad.rdf.objmapping.base.DataType;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This object represents a swith statement.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("Switch")
public class Switch extends ActionTaskDefinition {


    private DataType switchData;
    private CaseBlock[] caseStatements;

    /**
     * The default constructor
     */
    public Switch() {
    }


    /**
     * The constructor that ignores the next statement.
     *
     * @param name The name of string.
     * @param description The description of the switch statement.
     * @param switchData The data to perform the case statement evaluation on.
     * @param caseStatements The list of case statements.
     */
    public Switch(String name, String description, DataType switchData, CaseBlock[] caseStatements) {
        super(name, description);
        this.switchData = switchData;
        this.caseStatements = caseStatements;
    }


    /**
     * This constructor sets up the task to follow this one.
     *
     * @param name The name of the switch statement.
     * @param description The description of this switch statement.
     * @param next The task that follows this one.
     * @param switchData The data to perform the swith con.
     * @param caseStatements
     */
    public Switch(String name, String description, ActionTaskDefinition next, DataType switchData, CaseBlock[] caseStatements) {
        super(name, description, next);
        this.switchData = switchData;
        this.caseStatements = caseStatements;
    }


    /**
     * This method returns the case statements that will be evaluated by this object.
     *
     * @return The list of case statements to be evaluated.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#CaseStatements")
    public CaseBlock[] getCaseStatements() {
        return caseStatements;
    }


    /**
     * This method sets the case statements to be evaluated.
     *
     * @param caseStatements The list of case statements.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#CaseStatements")
    public void setCaseStatements(CaseBlock[] caseStatements) {
        this.caseStatements = caseStatements;
    }


    /**
     * This method returns the switch data.
     *
     * @return This method returns the switch data.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#SwitchData")
    public DataType getSwitchData() {
        return switchData;
    }


    /**
     * This method sets the switch data.
     *
     * @param switchData The data to perform the switch on.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#SwitchData")
    public void setSwitchData(DataType switchData) {
        this.switchData = switchData;
    }



}
