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
 * LogicalExpression.java
 */
package com.rift.coad.change.rdf.objmapping.change.task.logic;

// the stack to evaluate against.
import com.rift.coad.change.rdf.objmapping.change.action.StackEntry;
import com.rift.coad.change.rdf.objmapping.change.task.operator.LogicalException;
import com.rift.coad.change.rdf.objmapping.change.task.operator.Operator;
import com.rift.coad.change.rdf.objmapping.change.task.operator.OperatorConstants;
import com.rift.coad.rdf.objmapping.base.ComparisonOperators;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.VariableNameHolder;

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
        try {
            boolean result = false;
            boolean currentValue = false;
            Operator currentOperator = null;
            for (int index = 0; index < expression.getExpressions().length; index++) {
                if (expression.getExpressions()[index] instanceof LogicalExpression) {
                    currentValue = evaluate((LogicalExpression) expression.getExpressions()[index]);
                } else if (expression.getExpressions()[index] instanceof Operator) {
                    currentOperator = (Operator) expression.getExpressions()[index];
                } else {
                    currentValue = evaluate(expression.getExpressions()[index],
                            (Operator) expression.getExpressions()[++index],
                            expression.getExpressions()[++index]);
                }


                if (currentOperator == null) {
                    result = currentValue;
                } else {
                    if (currentOperator.getName().equals(OperatorConstants.NOT)) {
                        result = !currentValue;
                    } else if (currentOperator.getName().equals(OperatorConstants.AND)) {
                        result = result && currentValue;
                    } else if (currentOperator.getName().equals(OperatorConstants.EQUAL)) {
                        result = result == currentValue;
                    } else if (currentOperator.getName().equals(OperatorConstants.NOT_EQUAL)) {
                        result = result != currentValue;
                    } else if (currentOperator.getName().equals(OperatorConstants.OR)) {
                        result = result || currentValue;
                    }
                }
            }

            return result;
        } catch (java.lang.IndexOutOfBoundsException ex) {
            throw new LogicalException(
                    "The expression has not been constructed correctly : " + ex.getMessage(), ex);
        } catch (java.lang.ClassCastException ex) {
            throw new LogicalException("Attempt to compare incompatible types : " + ex.getMessage(), ex);
        } catch (LogicalException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new LogicalException(
                    "Failed to evaluate the locial expression : " + ex.getMessage(), ex);
        }
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
        try {
            DataType lhsValue = stack.getStackVariable(lhs.getDataName());
            DataType rhsValue = rhs;
            if (rhs instanceof VariableNameHolder) {
                rhsValue = stack.getStackVariable(rhs.getDataName());
            }
            if ((lhsValue == null) && (rhsValue == null)) {
                return true;
            } else if ((lhsValue == null) || (rhsValue == null)) {
                return false;
            }
            if (operation.getName().equals(OperatorConstants.EQUAL)) {
                // this is a hack to work around the fact that the equals operator must
                // perform a comparison on name as well as type, where as the
                // greater and less than operators are only used for the value.
                return ComparisonOperators.class.cast(lhsValue).equalValue(rhsValue);
            } else if (operation.getName().equals(OperatorConstants.NOT_EQUAL)) {
                // this is a hack to work around the fact that the equals operator must
                // perform a comparison on name as well as type, where as the
                // greater and less than operators are only used for the value.
                return !ComparisonOperators.class.cast(lhsValue).equalValue(rhsValue);
            } else if (operation.getName().equals(OperatorConstants.LESS)) {
                return ComparisonOperators.class.cast(lhsValue).lessThan(rhsValue);
            } else if (operation.getName().equals(OperatorConstants.GREATER)) {
                return ComparisonOperators.class.cast(lhsValue).greaterThan(rhsValue);
            }

        } catch (java.lang.ClassCastException ex) {
            throw new LogicalException("Attempt to compare incompatible types : " + ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new LogicalException("Failed to evaluate the arguments : " + ex.getMessage(), ex);
        }
        throw new LogicalException("Expression is incorrectly cannot [" + operation.getName() + "] on [" +
                lhs.getIdForDataType() + "][" + rhs.getIdForDataType() + "]");
    }
}
