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
 * LogicalExpression.java
 */
package com.rift.coad.change.rdf.objmapping.client.change.task.logic;

// the stack to evaluate against.
import com.rift.coad.change.rdf.objmapping.client.change.action.StackEntry;
import com.rift.coad.change.rdf.objmapping.client.change.task.operator.LogicalException;
import com.rift.coad.change.rdf.objmapping.client.change.task.operator.Operator;
import com.rift.coad.change.rdf.objmapping.client.change.task.operator.OperatorConstants;
import com.rift.coad.rdf.objmapping.client.base.ComparisonOperators;
import com.rift.coad.rdf.objmapping.client.base.DataType;

/**
 * This object is used to evaluate a logical expression against a stack.
 *
 * @author brett chaldecott
 */
public class LogicalAlgorithm {

    private LogicalExpression expression;
    private StackEntry stack;

    /**
     * The constructor that sets up all the variables.
     *
     * @param expression The expression to perform the evaluation against.
     * @param stack The stack containing the variables.
     */
    public LogicalAlgorithm(LogicalExpression expression, StackEntry stack) {
        this.expression = expression;
        this.stack = stack;
    }

    /**
     * This method is called to evaluate the expression against the stack.
     *
     * @return TRUE if the expression evaluates true, FALSE if not.
     * @throws LogicalException
     */
    public boolean evaluate() throws LogicalException {
        return evaluate(this.expression);
    }

    /**
     * This method is used to evaluate the expression.
     *
     * @param expression The expression to evaluate
     * @return TRUE or FALSE depending on the logical expression supplied.
     * @throws LogicalException
     */
    private boolean evaluate(LogicalExpression expression) throws LogicalException {
        // do nothing
        return false;
    }

    
    /**
     * This method evaluates the lhs and the rhs.
     *
     * @param lhs
     * @param operation
     * @param rhs
     * @return
     * @throws LogicalException
     */
    private boolean evaluate(DataType lhs, Operator operation, DataType rhs) throws LogicalException {
        // ignore
        return false;
    }
}
