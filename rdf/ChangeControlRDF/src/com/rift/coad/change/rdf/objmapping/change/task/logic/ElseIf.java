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
 * ElseIf.java
 */

package com.rift.coad.change.rdf.objmapping.change.task.logic;

import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("ElseIf")
public class ElseIf extends Else {

    // private member variables
    private LogicalExpression expression;
    private Else elseBlock;


    /**
     * The default constructor.
     */
    public ElseIf() {

    }

    /**
     * This constructor setups all member variables except the next task value.
     *
     * @param name The name of this task.
     * @param description The description of this task.
     * @param child The child value of this task.
     * @param expression The expression within the task.
     * @param elseBlock The else block.
     */
    public ElseIf(String name, String description, ActionTaskDefinition child,
            LogicalExpression expression, Else elseBlock) {
        super(name, description, child);
        this.expression = expression;
        this.elseBlock = elseBlock;
    }


    /**
     * This else if block setup all internal parameters.
     *
     * @param name The name of this task.
     * @param description The description of this task.
     * @param next The next task.
     * @param child The child task.
     * @param expression The expression.
     * @param elseBlock The else block.
     */
    public ElseIf(String name, String description, ActionTaskDefinition next,
            ActionTaskDefinition child, LogicalExpression expression, Else elseBlock) {
        super(name, description, next, child);
        this.expression = expression;
        this.elseBlock = elseBlock;
    }

    
    /**
     * This method returns a reference to the following else block.
     * 
     * @return The reference to the else block
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ElseIfElseBlock")
    public Else getElseBlock() {
        return elseBlock;
    }


    /**
     * This method sets the else block.
     *
     * @param elseBlock The else block.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ElseIfElseBlock")
    public void setElseBlock(Else elseBlock) {
        this.elseBlock = elseBlock;
    }


    /**
     * This method returns the logick expression used to evaluate this object.
     *
     * @return The logical expression represented by this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ElseIfLogicalExpression")
    public LogicalExpression getExpression() {
        return expression;
    }

    
    /**
     * This method sets the expression for evaluation.
     * 
     * @param expression The expression to evaluate.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ElseIfLogicalExpression")
    public void setExpression(LogicalExpression expression) {
        this.expression = expression;
    }




}
