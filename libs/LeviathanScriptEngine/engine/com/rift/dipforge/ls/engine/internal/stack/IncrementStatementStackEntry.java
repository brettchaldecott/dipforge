/*
 * LeviathanScriptEngine: The implementation of the Leviathan script engin.
 * Copyright (C) 2012  2015 Burntjam
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
 * IncrementExpressionEntry.java
 */

// package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.InvalidTypeException;
import com.rift.dipforge.ls.engine.internal.NoSuchVariable;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.IncrementStatement;
import java.util.Map;

/**
 * This method is used to increment the expression information.
 *
 * @author brett chaldecott
 */
public class IncrementStatementStackEntry extends StatementStackEntry {

    // private member variables
    private IncrementStatement incrementStatement;
    
    /**
     * This method increments the expression entry information.
     *
     * @param processorMemoryManager The processor memory manager
     * @param parent
     * @param currentIncrement The current increment method.
     */
    public IncrementStatementStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, IncrementStatement incrementStatement) {
        super(processorMemoryManager, parent);
        this.incrementStatement = incrementStatement;
    }

    /**
     * This method increments the expression entry information.
     *
     * @param processorMemoryManager The processor memory manager
     * @param parent
     * @param currentIncrement The current increment method.
     */
    public IncrementStatementStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables,
            IncrementStatement incrementStatement) {
        super(processorMemoryManager, parent, variables);
        this.incrementStatement = incrementStatement;
    }
    

    /**
     * This method is called to execute the increment expression.
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        if (!containsVariable(this.incrementStatement.getVariable())) {
            throw new NoSuchVariable("The variable [" + 
                    this.incrementStatement.getVariable() + "] not found");
        }
        Object value = getVariable(this.incrementStatement.getVariable());
        if (value == null) {
            throw new java.lang.NullPointerException("Increment called on [" +
                    this.incrementStatement.getVariable() + "] when value is null");
        }
        String operation = this.incrementStatement.getOperation();
        if (value instanceof Number) {
            Number lhs = (Number) value;
            if (operation.equals("++")) {
                if (value instanceof Byte) {
                    value = new Byte((byte) (lhs.byteValue() + 1));
                } else if (value instanceof Double) {
                    value = new Double((double) (lhs.doubleValue() + 1));
                } else if (value instanceof Float) {
                    value = new Float((float) (lhs.floatValue() + 1));
                } else if (value instanceof Integer) {
                    value = new Integer((int) (lhs.intValue() + 1));
                } else if (value instanceof Long) {
                    value = new Long((long) (lhs.longValue() + 1));
                } else if (value instanceof Short) {
                    value = new Short((short) (lhs.shortValue() + 1));
                }
            } else {
                if (value instanceof Byte) {
                    value = new Byte((byte) (lhs.byteValue() - 1));
                } else if (value instanceof Double) {
                    value = new Double((double) (lhs.doubleValue() - 1));
                } else if (value instanceof Float) {
                    value = new Float((float) (lhs.floatValue() - 1));
                } else if (value instanceof Integer) {
                    value = new Integer((int) (lhs.intValue() - 1));
                } else if (value instanceof Long) {
                    value = new Long((long) (lhs.longValue() - 1));
                } else if (value instanceof Short) {
                    value = new Short((short) (lhs.shortValue() - 1));
                }
            }
        } else if (value instanceof String){
            Number lhs = new Integer((String)value);
            if (operation.equals("++")) {
                value = "" + new Integer((int) (lhs.intValue() + 1));
            } else {
                value = "" + new Integer((int) (lhs.intValue() - 1));
            }
        } else {
            throw new InvalidTypeException("Cannot increment this type [" +
                    value.getClass().getName() + "]");
        }
        setVariable(this.incrementStatement.getVariable(),value);
        pop();
    }

    /**
     * This method sets the result.
     *
     * @param result The result to set
     * @throws EngineException
     */
    @Override
    public void setResult(Object result) throws EngineException {
        
    }
}
