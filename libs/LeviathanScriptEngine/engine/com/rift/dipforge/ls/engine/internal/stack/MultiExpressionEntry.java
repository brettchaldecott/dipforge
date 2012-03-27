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
 * MultiExpressionEntry.java
 */


package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.InvalidTypeException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.AddStatement;
import com.rift.dipforge.ls.parser.obj.MultStatement;
import com.rift.dipforge.ls.parser.obj.UnaryStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The multi expression entry
 * 
 * @author brett chaldecott
 */
public class MultiExpressionEntry extends ExpressionEntryStatementComponent {
    
    // private member variables
    private MultStatement multStatement;
    private Object value;
    private Object result;
    private String operation;
    private UnaryStatement currentUnary;
    private List<MultStatement.MultStatementBlock> blocks =
            new ArrayList<MultStatement.MultStatementBlock>();

    /**
     * The multi expression entry
     * 
     * @param processorMemoryManager
     * @param parent 
     * @param currentMulti The multiply.
     */
    public MultiExpressionEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, MultStatement multStatement) {
        super(processorMemoryManager, parent);
        this.multStatement = multStatement;
        currentUnary = multStatement.getInitialValue();
        blocks.addAll(multStatement.getBlocks());
        
    }

    
    /**
     * The multi expression entry
     * 
     * @param processorMemoryManager The processor memory manager.
     * @param parent The parent variable.
     * @param variables The variables.
     * @param currentMulti The multiply.
     */
    public MultiExpressionEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables, MultStatement currentMulti) {
        super(processorMemoryManager, parent, variables);
        this.multStatement = multStatement;
        currentUnary = multStatement.getInitialValue();
        blocks.addAll(multStatement.getBlocks());
    }

    
    /**
     * This method is called to multiply the results.
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        if (currentUnary != null) {
            UnaryExpressionEntry entry = new UnaryExpressionEntry(
                    this.getProcessorMemoryManager(), this, currentUnary);
            // set the current comparison = null
            currentUnary = null;
        } else if (result != null) {
            if (value == null && operation == null) {
                value = result;
            } else {
                if (!(value instanceof Number)) {
                throw new InvalidTypeException("None numeric value as base.");
            } else if (value instanceof Number) {
                Number lhs = (Number)value;
                if (result instanceof String) {
                    if (operation.equals("*")) {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() * 
                                    Byte.parseByte(result.toString())));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() * 
                                    Double.parseDouble(result.toString())));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() *
                                    Float.parseFloat(result.toString())));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() *
                                    Integer.parseInt(result.toString())));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() *
                                    Long.parseLong(result.toString())));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() *
                                    Short.parseShort(result.toString())));
                        }
                    } else if (operation.equals("/")) {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() /
                                    Byte.parseByte(result.toString())));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() / 
                                    Double.parseDouble(result.toString())));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() /
                                    Float.parseFloat(result.toString())));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() /
                                    Integer.parseInt(result.toString())));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() /
                                    Long.parseLong(result.toString())));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() /
                                    Short.parseShort(result.toString())));
                        }
                    } else {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() %
                                    Byte.parseByte(result.toString())));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() % 
                                    Double.parseDouble(result.toString())));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() %
                                    Float.parseFloat(result.toString())));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() %
                                    Integer.parseInt(result.toString())));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() %
                                    Long.parseLong(result.toString())));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() %
                                    Short.parseShort(result.toString())));
                        }
                    }
                } else if (result instanceof Number) {
                    Number rhs = (Number)result;
                    if (operation.equals("*")) {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() * 
                                    rhs.byteValue()));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() * 
                                    rhs.doubleValue()));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() *
                                    rhs.floatValue()));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() *
                                    rhs.intValue()));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() *
                                    rhs.longValue()));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() *
                                    rhs.shortValue()));
                            }
                        } else if (operation.equals("/")) {
                            if (value instanceof Byte) {
                                value = new Byte((byte) (lhs.byteValue()
                                        / rhs.byteValue()));
                            } else if (value instanceof Double) {
                                value = new Double((double) (lhs.doubleValue()
                                        / rhs.doubleValue()));
                            } else if (value instanceof Float) {
                                value = new Float((float) (lhs.floatValue()
                                        / rhs.floatValue()));
                            } else if (value instanceof Integer) {
                                value = new Integer((int) (lhs.intValue()
                                        / rhs.intValue()));
                            } else if (value instanceof Long) {
                                value = new Long((long) (lhs.longValue()
                                        / rhs.longValue()));
                            } else if (value instanceof Short) {
                                value = new Short((short) (lhs.shortValue()
                                        / rhs.shortValue()));
                            }
                        } else {
                            if (value instanceof Byte) {
                                value = new Byte((byte) (lhs.byteValue()
                                        % rhs.byteValue()));
                            } else if (value instanceof Double) {
                                value = new Double((double) (lhs.doubleValue()
                                        % rhs.doubleValue()));
                            } else if (value instanceof Float) {
                                value = new Float((float) (lhs.floatValue()
                                        % rhs.floatValue()));
                            } else if (value instanceof Integer) {
                                value = new Integer((int) (lhs.intValue()
                                        % rhs.intValue()));
                            } else if (value instanceof Long) {
                                value = new Long((long) (lhs.longValue()
                                        % rhs.longValue()));
                            } else if (value instanceof Short) {
                                value = new Short((short) (lhs.shortValue()
                                        % rhs.shortValue()));
                            }
                        }
                    } else {
                        throw new InvalidTypeException(
                                "Invalid type used in arithematic");
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
                MultStatement.MultStatementBlock mult = this.blocks.remove(0);
                operation = mult.getOperation();
                currentUnary = mult.getBlockValue();
            }
        }
    }

    /**
     * This method sets the result of this object.
     * 
     * @param result This 
     */
    public void setResult(Object result) {
        this.result = result;
    }
    
    
    
}
