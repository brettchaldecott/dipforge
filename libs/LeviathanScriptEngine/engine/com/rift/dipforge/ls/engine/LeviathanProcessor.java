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
 * LeviathanProcessor.java
 */
package com.rift.dipforge.ls.engine;

import com.rift.coad.lib.common.ObjectSerializer;
import com.rift.dip.leviathan.LeviathanLexer;
import com.rift.dip.leviathan.LeviathanParser;
import com.rift.dipforge.ls.engine.internal.Constants;
import com.rift.dipforge.ls.engine.internal.InvalidTypeException;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.engine.internal.ProcessorStack;
import com.rift.dipforge.ls.parser.obj.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

/**
 * This class is responsible for managing the processing of a script.
 *
 * @author brett chaldecott
 */
public class LeviathanProcessor {

    // private member variables
    private ProcessorMemoryManager memory;

    /**
     * This constructor loads the process into memory using the file path as the
     * source for the information.
     *
     * @param filePath The path to the file.
     * @exception EngineException
     */
    public LeviathanProcessor(String filePath) throws EngineException {
        try {
            File file = new File(filePath);
            FileInputStream in = new FileInputStream(file);
            byte[] input = new byte[(int) file.length()];
            in.read(input);
            memory = (ProcessorMemoryManager) ObjectSerializer.deserialize(input);
            in.close();
        } catch (Exception ex) {
            throw new EngineException("Failed to load the object because : "
                    + ex.getMessage());
        }
    }

    /**
     * The constructor that sets the code space.
     *
     * @param codeSpace The code space
     * @exception EngineException
     */
    public LeviathanProcessor(String sourceFile, Map variables)
            throws EngineException {
        try {
            LeviathanLexer lex = new LeviathanLexer(new ANTLRFileStream(sourceFile));
            CommonTokenStream tokens = new CommonTokenStream(lex);

            LeviathanParser parser = new LeviathanParser(tokens);
            Workflow flow = parser.workflow();

            // walk the code space and find the initial heap variables
            Block mainBlock = null;
            for (Statement statement : flow.getStatements()) {
                if (statement instanceof Variable) {
                    Variable var = (Variable) statement;
                    if (variables.containsKey(var.getName())) {
                        continue;
                    }

                    // retrieve the inital value
                    Object initialValue = null;
                    if (var.getInitialValue() != null) {
                        initialValue = executeAssignment(
                                (Assignment) var.getInitialValue());
                    }
                    variables.put(var.getName(), initialValue);
                } else if ((statement instanceof Block)
                        && !(statement instanceof MethodDefinition)) {
                    mainBlock = (Block) statement;
                }

            }
            memory = new ProcessorMemoryManager(flow, variables);
            Map mainBlockVars = new HashMap();
            mainBlockVars.put(Constants.BLOCK, mainBlock);
            memory.pushStack(mainBlockVars);
            memory.setState(LeviathanConstants.Status.RUNNING);
        } catch (Exception ex) {
            throw new EngineException("Failed to initialize the processor : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method executes the
     */
    public void execute() throws EngineException {
        execute(null);
    }

    /**
     * This method is called to resume the processing of this object.
     *
     * @param value The value to resume on.
     */
    public void resume(Object value) throws EngineException {
        execute(value);
    }

    /**
     * This method is called to execute through the flow code
     *
     * @param value The value pass in.
     * @throws EngineException
     */
    private void execute(Object value) throws EngineException {
        try {
            ProcessorStack stack = null;
            while ((stack = memory.peekStack()) != null) {

                ProcessorStack.ProcessStackEntry entry;
                while ((entry = stack.getCurrentEntry()) != null) {
                    Statement statement = null;
                    if (null == (statement = getNextStatement(entry))) {
                        stack.popStackEntry();
                        continue;
                    }
                    executeStatement(entry, statement);

                }
                // pop this entry off the stack
                memory.popStack();
            }
            if (memory.peekStack() == null) {
                memory.setState(LeviathanConstants.Status.COMPLETED);
            }
        } catch (Exception ex) {
            throw new EngineException("Failed to execute the script : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the status information for this object.
     *
     * @return The status information for the leviathan processor
     */
    public LeviathanConstants.Status getStatus() {
        return memory.getState();
    }

    /**
     * This method persists the memory information.
     *
     * @param dirPath The directory path that the information will be persisted
     * to
     * @throws EngineException
     */
    public void persist(String dirPath) throws EngineException {
        try {
            File file = new File(dirPath, memory.getGuid());
            FileOutputStream out = new FileOutputStream(file);
            out.write(ObjectSerializer.serialize(memory));
            out.close();
        } catch (Exception ex) {
            throw new EngineException("This method persists the object : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the GUID for the object.
     *
     * @return The string containing the GUID.
     */
    public String getGUID() {
        return memory.getGuid();
    }

    /**
     * This method retrieves the next item in the list
     *
     * @param blk The block that this object is attached to.
     * @param statement The current statement
     * @return The reference to the statement
     */
    private Statement getNextStatement(ProcessorStack.ProcessStackEntry entry)
            throws EngineException {
        Block blk = (Block) entry.getVariable(Constants.BLOCK);
        Statement statement = null;
        if (entry.containsVariable(Constants.STATEMENT_POS)) {
            statement = (Statement) entry.getVariable(Constants.STATEMENT_POS);
        }

        if ((statement == null)
                || !(blk.getStatements().contains(statement))) {
            if (blk.getStatements().size() > 0) {
                Statement result = blk.getStatements().get(0);
                entry.addVariable(Constants.STATEMENT_POS, result);
                return result;
            }
        } else {
            int index = blk.getStatements().indexOf(statement) + 1;
            if (blk.getStatements().size() > index) {
                Statement result = blk.getStatements().get(index);
                entry.setVariable(Constants.STATEMENT_POS, result);
                return result;
            }
        }
        return null;
    }

    /**
     * This method executes the statement
     *
     * @param statement The statement to execute
     */
    private void executeStatement(ProcessorStack.ProcessStackEntry entry,
            Statement statement) throws EngineException {
        try {
            if (statement instanceof Variable) {
                // retrieve the inital value
                Variable var = (Variable) statement;
                if (entry.containsVariable(var.getName(), false)) {
                    throw new EngineException("Duplicate variable ["
                            + var.getName() + "]");
                }
                Object initialValue = null;
                if (var.getInitialValue() != null) {
                    initialValue = executeAssignment(
                            (Assignment) var.getInitialValue());
                }
                entry.addVariable(var.getName(), initialValue);

            } else if (statement instanceof CallStatement) {
            } else if (statement instanceof IfStatement) {
            } else if (statement instanceof WhileStatement) {
            } else if (statement instanceof ForStatement) {
            } else if (statement instanceof CaseStatement) {
            } else if (statement instanceof Block) {
            } else if (statement instanceof ContinueStatement) {
            } else if (statement instanceof BreakStatement) {
            } else if (statement instanceof ReturnStatement) {
            }
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException("Failed to execute the statement : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method executes an assignment
     *
     * @param value The value containing the assignment information.
     * @return The value for the assignment
     */
    private Object executeAssignment(Object value) throws EngineException {
        Assignment ass = (Assignment) value;
        return executeExpression((Expression) ass.getValue());
    }

    
    /**
     * This method executes the expression
     *
     * @param exp The expression to execute the expression on
     * @return The reference to the result of the expression.
     */
    private Object executeExpression(Expression exp) throws EngineException {
        Object value = executeComparison(exp.getInitialValue());
        for (Expression.ExpressionBlock block : exp.getBlocks()) {
            if (!(value instanceof Boolean)) {
                throw new EngineException(
                        "Performing boolean operation on none bool types");
            }
            Boolean lhs = (Boolean) value;
            if (block.getOperation().equals("&&") && !lhs.booleanValue()) {
                continue;
            }

            Object rhsValue = executeComparison(block.getStatement());
            if (!(rhsValue instanceof Boolean)) {
                throw new EngineException(
                        "Performing boolean operation on none bool types");
            }
            Boolean rhs = (Boolean) value;
            if (block.getOperation().equals("&&")) {
                value = new Boolean(lhs.booleanValue() && rhs.booleanValue());
            } else {
                value = new Boolean(lhs.booleanValue() || rhs.booleanValue());
            }
        }
        return value;
    }

    
    /**
     * This method performs the comparison.
     * 
     * @param comp The object to perform the comparison on.
     * @return The object containing the result.
     * @throws EngineException 
     */
    private Object executeComparison(Comparison comp) throws EngineException {
        Object value = executeRelation(comp.getInitialValue());
        for (Comparison.ComparisonBlock block : comp.getBlocks()) {
            Object rhsValue = executeRelation(block.getStatement());
            if (block.getOperation().equals("==")) {
                if ((null == rhsValue) && (null == value)) {
                    value = new Boolean(true);
                } else if ((null == rhsValue) || (null == value)) {
                    value = new Boolean(false);
                } else if (value == rhsValue) {
                    value = new Boolean(true);
                } else if (value instanceof Comparable && 
                        ((Comparable)value).compareTo(rhsValue) == 0) {
                    value = new Boolean(true);
                } else {
                    value = new Boolean(false);
                }
            } else {
                if (((null != rhsValue) && (null == value)) || 
                        ((null == rhsValue) && (null != value))) {
                    value = new Boolean(true);
                } else if ((null == rhsValue) && (null == value)) {
                    value = new Boolean(false);
                } else if (value == rhsValue) {
                    value = new Boolean(false);
                } else if (value instanceof Comparable && 
                        ((Comparable)value).compareTo(rhsValue) == 0) {
                    value = new Boolean(false);
                } else {
                    value = new Boolean(true);
                }
            }
        }
        return value;
    }
    
    
    /**
     * This method executes the relation operation.
     * 
     * @param rela The 
     * @return
     */
    private Object executeRelation(Relation rela) throws EngineException {
        Object value = executeAdd(rela.getInitialValue());
        try {
            // there will only ever be one in a relationship expression for now
            // I have left the code like this so that if need be it can be used
            // in a loop at a later date
            for (Relation.RelationStatementBlock block : rela.getBlocks()) {
                Object rhsValue = executeAdd(block.getStatement());
                if (!((value instanceof Number) && (rhsValue instanceof Number))) {
                    throw new EngineException(
                            "Performing a relation operation on non numeric type");
                }
                Comparable compar = (Comparable)value;
                // operations
                if (block.getOperation().equals("<")) {
                    value = new Boolean((compar.compareTo(rhsValue)) < 0);
                } else if (block.getOperation().equals("<=")) {
                    value = new Boolean((compar.compareTo(rhsValue)) <= 0);
                } else if (block.getOperation().equals(">=")) {
                    value = new Boolean((compar.compareTo(rhsValue)) >= 0);
                } else if (block.getOperation().equals(">")) {
                    value = new Boolean((compar.compareTo(rhsValue)) > 0);
                }
            }
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException(ex.getMessage(),ex);
        }
        return value;
    }
    
    
    /**
     * This method executes the add method.
     * 
     * @param addStat The add statement.
     * @return The reference to the add statement value.
     */
    private Object executeAdd(AddStatement addStat) throws EngineException {
        Object value = executeMultStatement(addStat.getInitialValue());
        for (AddStatement.AddStatementBlock block : addStat.getBlockStatement()) {
            Object rhsValue = executeMultStatement(block.getStatement());
            if (value == null) {
                throw new NullPointerException(
                        "Initial value of add statement cannot be null.");
            }
            if (value instanceof String) {
                if (block.getOperation().equals("+")) {
                    value = value.toString() + rhsValue.toString();
                } else {
                    throw new InvalidTypeException("Cannot subtract strings.");
                }
            } else if (value instanceof Number) {
                Number lhs = (Number)value;
                if (rhsValue instanceof String) {
                    if (block.getOperation().equals("+")) {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() + 
                                    Byte.parseByte(rhsValue.toString())));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() + 
                                    Double.parseDouble(rhsValue.toString())));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() + 
                                    Float.parseFloat(rhsValue.toString())));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() + 
                                    Integer.parseInt(rhsValue.toString())));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() + 
                                    Long.parseLong(rhsValue.toString())));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() + 
                                    Short.parseShort(rhsValue.toString())));
                        }
                    } else {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() - 
                                    Byte.parseByte(rhsValue.toString())));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() - 
                                    Double.parseDouble(rhsValue.toString())));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() - 
                                    Float.parseFloat(rhsValue.toString())));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() - 
                                    Integer.parseInt(rhsValue.toString())));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() - 
                                    Long.parseLong(rhsValue.toString())));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() - 
                                    Short.parseShort(rhsValue.toString())));
                        }
                    }
                } else if (rhsValue instanceof Number) {
                    Number rhs = (Number)rhsValue;
                    if (block.getOperation().equals("+")) {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() + 
                                    rhs.byteValue()));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() + 
                                    rhs.doubleValue()));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() + 
                                    rhs.floatValue()));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() + 
                                    rhs.intValue()));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() + 
                                    rhs.longValue()));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() + 
                                    rhs.shortValue()));
                        }
                    } else {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() -
                                    rhs.byteValue()));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() - 
                                    rhs.doubleValue()));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() - 
                                    rhs.floatValue()));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() - 
                                    rhs.intValue()));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() -
                                    rhs.longValue()));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() -
                                    rhs.shortValue()));
                        }
                    }
                } else {
                    throw new InvalidTypeException(
                            "Invalid type used in arithematic");
                }
            }
        }
        return value;
    }
    
    
    /**
     * Execute the multi statement.
     * 
     * @param multStat The multi statement
     * @return This method returns the statement.
     * @throws EngineException 
     */
    private Object executeMultStatement(MultStatement multStat)
            throws EngineException {
        Object value = executeUnary(multStat.getInitialValue());
        for (MultStatement.MultStatementBlock block : multStat.getBlocks()) {
            Object rhsValue = executeUnary(block.getBlockValue());
            if (value == null) {
                throw new NullPointerException(
                        "Initial value of add statement cannot be null.");
            }
            if (!(value instanceof Number)) {
                throw new InvalidTypeException("None numeric value as base.");
            } else if (value instanceof Number) {
                Number lhs = (Number)value;
                if (rhsValue instanceof String) {
                    if (block.getOperation().equals("*")) {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() * 
                                    Byte.parseByte(rhsValue.toString())));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() * 
                                    Double.parseDouble(rhsValue.toString())));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() *
                                    Float.parseFloat(rhsValue.toString())));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() *
                                    Integer.parseInt(rhsValue.toString())));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() *
                                    Long.parseLong(rhsValue.toString())));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() *
                                    Short.parseShort(rhsValue.toString())));
                        }
                    } else if (block.getOperation().equals("/")) {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() /
                                    Byte.parseByte(rhsValue.toString())));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() / 
                                    Double.parseDouble(rhsValue.toString())));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() /
                                    Float.parseFloat(rhsValue.toString())));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() /
                                    Integer.parseInt(rhsValue.toString())));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() /
                                    Long.parseLong(rhsValue.toString())));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() /
                                    Short.parseShort(rhsValue.toString())));
                        }
                    } else {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() %
                                    Byte.parseByte(rhsValue.toString())));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() % 
                                    Double.parseDouble(rhsValue.toString())));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() %
                                    Float.parseFloat(rhsValue.toString())));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() %
                                    Integer.parseInt(rhsValue.toString())));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() %
                                    Long.parseLong(rhsValue.toString())));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() %
                                    Short.parseShort(rhsValue.toString())));
                        }
                    }
                } else if (value instanceof Number) {
                    Number rhs = (Number)rhsValue;
                    if (block.getOperation().equals("*")) {
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
                    } else if (block.getOperation().equals("/")) {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() / 
                                    rhs.byteValue()));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() /
                                    rhs.doubleValue()));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() /
                                    rhs.floatValue()));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() /
                                    rhs.intValue()));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() /
                                    rhs.longValue()));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() /
                                    rhs.shortValue()));
                        }
                    } else {
                        if (value instanceof Byte) {
                            value = new Byte((byte)(lhs.byteValue() %
                                    rhs.byteValue()));
                        } else if (value instanceof Double) {
                            value = new Double((double)(lhs.doubleValue() %
                                    rhs.doubleValue()));
                        } else if (value instanceof Float) {
                            value = new Float((float)(lhs.floatValue() %
                                    rhs.floatValue()));
                        } else if (value instanceof Integer) {
                            value = new Integer((int)(lhs.intValue() %
                                    rhs.intValue()));
                        } else if (value instanceof Long) {
                            value = new Long((long)(lhs.longValue() %
                                    rhs.longValue()));
                        } else if (value instanceof Short) {
                            value = new Short((short)(lhs.shortValue() %
                                    rhs.shortValue()));
                        }
                    }
                } else {
                    throw new InvalidTypeException(
                            "Invalid type used in arithematic");
                }
            }
        }
        return value;
    }
    
    
    /**
     * This method is called to set the sign on a 
     * @param unaryStat The reference to the unary statement.
     * @return The object containing the unary result.
     * @throws EngineException 
     */
    private Object executeUnary(UnaryStatement unaryStat)
            throws EngineException {
        
        
        return null;
    }
    
    
}
