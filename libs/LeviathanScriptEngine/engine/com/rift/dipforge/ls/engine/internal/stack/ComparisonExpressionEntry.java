/*
 * LeviathanScriptEngine: The implementation of the Leviathan script engin.
 * Copyright (C) 2012  Rift IT Contracting
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
 * ExpressionStatementComponent.java
 */
// package path
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.Comparison;
import com.rift.dipforge.ls.parser.obj.Expression;
import com.rift.dipforge.ls.parser.obj.Relation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author brett chaldecott
 */
public class ComparisonExpressionEntry extends ExpressionEntryStatementComponent {

    // private member variables
    private Comparison comparison;
    private Object value;
    private Object result;
    private String operation;
    private Relation currentRelation;
    private List<Comparison.ComparisonBlock> blocks =
            new ArrayList<Comparison.ComparisonBlock>();

    /**
     * This is the constructor of the comparison expression entry.
     *
     * @param processorMemoryManager The reference to the processor memory
     * manager.
     * @param parent The parent of this object.
     */
    public ComparisonExpressionEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Comparison comparison) {
        super(processorMemoryManager, parent);
        this.comparison = comparison;
        currentRelation = comparison.getInitialValue();
        blocks.addAll(comparison.getBlocks());
    }

    /**
     * The comparison expression entry manager.
     *
     * @param processorMemoryManager The reference to the processor memory
     * manager.
     * @param parent The parent object reference.
     * @param variables The list of variables.
     */
    public ComparisonExpressionEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables,
            Comparison comparison) {
        super(processorMemoryManager, parent, variables);
        this.comparison = comparison;
        currentRelation = comparison.getInitialValue();
        blocks.addAll(comparison.getBlocks());
    }

    /**
     * This method is called to execute.
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        if (currentRelation != null) {
            RelationExpressionEntry entry = new RelationExpressionEntry(
                    this.getProcessorMemoryManager(), this, currentRelation);
            // set the current comparison = null
            currentRelation = null;
        } else {
            if (value == null && operation == null) {
                value = result;
            } else {
                
                if (operation.equals("==")) {
                    if ((null == result) && (null == value)) {
                        value = new Boolean(true);
                    } else if ((null == result) || (null == value)) {
                        value = new Boolean(false);
                    } else if (value == result) {
                        value = new Boolean(true);
                    } else if (value instanceof Comparable
                            && ((Comparable) value).compareTo(result) == 0) {
                        value = new Boolean(true);
                    } else {
                        value = new Boolean(false);
                    }
                } else {
                    if (((null != result) && (null == value))
                            || ((null == result) && (null != value))) {
                        value = new Boolean(true);
                    } else if ((null == result) && (null == value)) {
                        value = new Boolean(false);
                    } else if (value == result) {
                        value = new Boolean(false);
                    } else if (value instanceof Comparable
                            && ((Comparable) value).compareTo(result) == 0) {
                        value = new Boolean(false);
                    } else {
                        value = new Boolean(true);
                    }
                }
            }
            result = null;
            if (blocks.size() == 0) {
                ProcessStackEntry assignment =
                        (ProcessStackEntry) this.getParent();
                assignment.setResult(value);
                pop();
            } else {
                Comparison.ComparisonBlock comparison = this.blocks.remove(0);
                operation = comparison.getOperation();
                currentRelation = comparison.getStatement();
            }
        }
    }

    /**
     * This method sets the result value.
     * 
     * @param result The result value
     */
    public void setResult(Object result) {
        this.result = result;
    }
    
    
    
}
