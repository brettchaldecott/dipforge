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

// package path
package com.rift.coad.change.rdf.objmapping.client.change.task.logic;

import com.rift.coad.change.rdf.objmapping.client.change.Constants;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;

/**
 * The logical expresion.
 *
 * @author brett chaldecott
 */
public class LogicalExpression extends DataType {

    private String id;
    private DataType[] expressions;

    /**
     * The default constructor.
     */
    public LogicalExpression() {
        id = IDGenerator.getId();
    }


    /**
     * This constructor sets the expresions.
     *
     * @param expresions The array  off expressions.
     */
    public LogicalExpression(DataType[] expressions) {
        id = IDGenerator.getId();
        this.expressions = expressions;
        if (this.expressions == null || this.id == null) {
            return;
        }
        for (DataType value : expressions) {
            value.setAssociatedObject(Constants.LOGICAL_EXPRESSION + id);
        }
    }

    /**
     * This constructor sets the id and the expression
     *
     * @param id
     * @param expresions
     */
    public LogicalExpression(String id, DataType[] expressions) {
        this.id = id;
        this.expressions = expressions;
        if (this.expressions == null || this.id == null) {
            return;
        }
        for (DataType value : expressions) {
            value.setAssociatedObject(Constants.LOGICAL_EXPRESSION + id);
        }
    }


    /**
     * This method returns the list of expressions.
     *
     * @return The list of expressions.
     */
    public DataType[] getExpressions() {
        return expressions;
    }


    /**
     * This method sets the list expressions.
     *
     * @param expressions The list of expressions.
     */
    public void setExpressions(DataType[] expressions) {
        this.expressions = expressions;
        if (this.expressions == null || this.id == null) {
            return;
        }
        for (DataType value : expressions) {
            value.setAssociatedObject(Constants.LOGICAL_EXPRESSION + id);
        }
    }

    /**
     * This method adds an expression to the array of expression held within.
     *
     * @param value The value to add
     */
    public void addExpression(DataType value) {
        if (expressions == null) {
            expressions = new DataType[1];
        } else {
            DataType[] expressions = new DataType[this.expressions.length + 1];
            for (int index = 0; index < this.expressions.length;index++) {
                expressions[index] = this.expressions[index];
            }
            this.expressions = expressions;
        }
        value.setAssociatedObject(Constants.LOGICAL_EXPRESSION + id);
        expressions[expressions.length - 1] = value;
    }


    /**
     * This method returns the id of the logical expression.
     *
     * @return The string containing the logical expression id.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the logical expression id.
     *
     * @param id The id of the logical expression.
     */
    public void setId(String id) {
        this.id = id;
        if (this.expressions == null || this.id == null) {
            return;
        }
        for (DataType value : expressions) {
            value.setAssociatedObject(Constants.LOGICAL_EXPRESSION + id);
        }
    }


    /**
     * This method checks to see if the entries are equal.
     *
     * @param obj The object to perform the comparison on.
     * @return TRUE if equals, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogicalExpression other = (LogicalExpression) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for this object.
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    /**
     * The default constructor.
     */
    public static LogicalExpression createInstance() {
        LogicalExpression expression = new LogicalExpression();
        expression.setIdForDataType(Constants.LOGICAL_EXPRESSION);
        expression.setBasicType(Constants.LOGICAL_EXPRESSION);
        return expression;
    }


    /**
     * This constructor sets the expresions.
     *
     * @param expresions The array  off expressions.
     */
    public static LogicalExpression createInstances(DataType[] expressions) {
        LogicalExpression expression = createInstance();
        expression.setExpressions(expressions);
        return expression;
    }

    /**
     * This constructor sets the id and the expression
     *
     * @param id
     * @param expresions
     */
    public static LogicalExpression createInstance(String id, DataType[] expressions) {
        LogicalExpression expression = createInstance();
        expression.setId(id);
        expression.setExpressions(expressions);
        return expression;
    }
}
