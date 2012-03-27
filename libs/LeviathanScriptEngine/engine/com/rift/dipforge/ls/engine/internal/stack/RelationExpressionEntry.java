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
import com.rift.dipforge.ls.parser.obj.AddStatement;
import com.rift.dipforge.ls.parser.obj.Comparison;
import com.rift.dipforge.ls.parser.obj.Relation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The implementation of the relation expression
 * 
 * @author brett chaldecott
 */
public class RelationExpressionEntry extends ExpressionEntryStatementComponent {

    // private member variables
    private Relation relation;
    private Object value;
    private Object result;
    private String operation;
    private AddStatement currentAdd;
    private List<Relation.RelationStatementBlock> blocks =
            new ArrayList<Relation.RelationStatementBlock>();
    
    
    /**
     * The constructor for the relation expression entry.
     * 
     * @param processorMemoryManager The process memory manager.
     * @param parent The parent reference.
     */
    public RelationExpressionEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent,
            Relation relation) {
        super(processorMemoryManager, parent);
        this.relation = relation;
        currentAdd = relation.getInitialValue();
        blocks.addAll(relation.getBlocks());
        
    }

    
    /**
     * The constructor for the relation expression entry.
     * 
     * @param processorMemoryManager The processor memory manager.
     * @param parent The parent.
     * @param variables The variables
     * @param relation The relationship object
     */
    public RelationExpressionEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables,
            Relation relation) {
        super(processorMemoryManager, parent, variables);
        this.relation = relation;
        currentAdd = relation.getInitialValue();
        blocks.addAll(relation.getBlocks());
    }

    
    
    /**
     * This method executes the relation expression.
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        if (currentAdd != null) {
            AddExpressionEntry entry = new AddExpressionEntry(
                    this.getProcessorMemoryManager(), this, currentAdd);
            // set the current comparison = null
            currentAdd = null;
        } else if (result != null) {
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
                Relation.RelationStatementBlock comparison = this.blocks.remove(0);
                operation = comparison.getOperation();
                currentAdd = comparison.getStatement();
            }
        }
    }

    
    /**
     * The method that sets the result of the relation expression
     * 
     * @param result
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        this.result = result;
    }
    
    
    
}
