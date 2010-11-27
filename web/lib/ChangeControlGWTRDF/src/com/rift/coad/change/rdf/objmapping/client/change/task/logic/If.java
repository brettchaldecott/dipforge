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
 * If.java
 */

// package path
package com.rift.coad.change.rdf.objmapping.client.change.task.logic;

// change imports
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.Constants;
import com.rift.coad.change.rdf.objmapping.client.change.task.Block;

// the semantic imports

/**
 * The if statement.
 *
 * @author brett chaldecott
 */
public class If extends Block {

    // private member variables
    private LogicalExpression expression;
    private Else elseBlock;
    
    /**
     * The default constructor
     */
    public If() {
    }


    /**
     * This constructor sets up all the internal values.
     *
     * @param name The name of the if statement.
     * @param description The description of the if statement.
     * @param child The child block
     * @param expression The expression.
     * @param elseBlock The else block.
     */
    public If(String name, String description, ActionTaskDefinition child,
            LogicalExpression expression, Else elseBlock) {
        super(name, description, child);
        this.expression = expression;
        this.elseBlock = elseBlock;
    }


    /**
     * This method returns the else block that follows this if statement.
     *
     * @return The reference to else block.
     */
    public Else getElseBlock() {
        return elseBlock;
    }


    /**
     * This method sets the else block that follows this statement.
     *
     * @param elseBlock The else block that follows this statement.
     */
    public void setElseBlock(Else elseBlock) {
        this.elseBlock = elseBlock;
    }


    /**
     * This method returns the logical expression information.
     *
     * @return The reference to the logical expression.
     */
    public LogicalExpression getExpression() {
        return expression;
    }


    /**
     * This method sets the logical expression information.
     *
     * @param expression The logical expression information.
     */
    public void setExpression(LogicalExpression expression) {
        this.expression = expression;
    }



    /**
     * The default constructor
     */
    public static If createInstance() {
        If ifBlock = new If();
        ifBlock.setIdForDataType(Constants.IF_BLOCK);
        ifBlock.setBasicType(Constants.IF_BLOCK);
        return ifBlock;
    }


    /**
     * This constructor sets up all the internal values.
     *
     * @param name The name of the if statement.
     * @param description The description of the if statement.
     * @param child The child block
     * @param expression The expression.
     * @param elseBlock The else block.
     */
    public static If createInstance(String name, String description, ActionTaskDefinition child,
            LogicalExpression expression, Else elseBlock) {
        If ifBlock = createInstance();
        ifBlock.setName(name);
        ifBlock.setDescription(description);
        ifBlock.setChild(child);
        ifBlock.setExpression(expression);
        ifBlock.setElseBlock(elseBlock);
        return ifBlock;
    }

    

}
