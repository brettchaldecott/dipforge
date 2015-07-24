/*
 * ChangeControlRDF: The rdf information for the change control system.
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
 * ElseIf.java
 */

package com.rift.coad.change.rdf.objmapping.client.change.task.logic;

// imports
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.Constants;

/**
 * This
 *
 * @author brett chaldecott
 */
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
    public Else getElseBlock() {
        return elseBlock;
    }


    /**
     * This method sets the else block.
     *
     * @param elseBlock The else block.
     */
    public void setElseBlock(Else elseBlock) {
        this.elseBlock = elseBlock;
    }


    /**
     * This method returns the logick expression used to evaluate this object.
     *
     * @return The logical expression represented by this object.
     */
    public LogicalExpression getExpression() {
        return expression;
    }

    
    /**
     * This method sets the expression for evaluation.
     * 
     * @param expression The expression to evaluate.
     */
    public void setExpression(LogicalExpression expression) {
        this.expression = expression;
    }


    /**
     * The default constructor.
     */
    public static ElseIf createInstance() {
        ElseIf elseIf = new ElseIf();
        elseIf.setIdForDataType(Constants.ELSE_IF_BLOCK);
        elseIf.setBasicType(Constants.ELSE_IF_BLOCK);
        return elseIf;

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
    public static ElseIf createInstance(String name, String description, ActionTaskDefinition child,
            LogicalExpression expression, Else elseBlock) {
        ElseIf elseIf = createInstance();
        elseIf.setName(name);
        elseIf.setDescription(description);
        elseIf.setChild(child);
        elseIf.setExpression(expression);
        elseIf.setElseBlock(elseBlock);
        return elseIf;
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
    public static ElseIf createInstance(String name, String description, ActionTaskDefinition next,
            ActionTaskDefinition child, LogicalExpression expression, Else elseBlock) {
        ElseIf elseIf = createInstance();
        elseIf.setName(name);
        elseIf.setDescription(description);
        elseIf.setNext(next);
        elseIf.setChild(child);
        elseIf.setExpression(expression);
        elseIf.setElseBlock(elseBlock);
        return elseIf;
    }

}
