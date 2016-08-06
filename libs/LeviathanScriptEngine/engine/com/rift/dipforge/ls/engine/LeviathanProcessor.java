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
 * LeviathanProcessor.java
 */
package com.rift.dipforge.ls.engine;

import com.rift.coad.lib.common.ObjectSerializer;
import com.rift.dip.leviathan.LeviathanLexer;
import com.rift.dip.leviathan.LeviathanParser;
import com.rift.dipforge.ls.engine.internal.*;
import com.rift.dipforge.ls.engine.internal.stack.BlockStackEntry;
import com.rift.dipforge.ls.engine.internal.stack.VariableStackEntry;
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
     * @param file The file to parse
     * @exception EngineException
     */
    public LeviathanProcessor(File file) throws EngineException {
        try {
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
     * @param flow The flow to process
     * @param variables The variables
     * @exception EngineException
     */
    public LeviathanProcessor(Workflow flow, Map variables)
            throws EngineException {
        try {
            memory = new ProcessorMemoryManager(flow, variables);
            memory.setState(LeviathanConstants.Status.INIT);
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
        memory.peekStack().setResult(value);
        execute(value);
    }

    
    /**
     * This method is called to execute through the flow code
     *
     * @param value The value pass in.
     * @throws EngineException
     */
    private synchronized void execute(Object value) throws EngineException {
        try {
            // walk the code space and find the initial heap variables
            if (memory.getCurrentStatement() == null || 
                    memory.getCurrentStatement() instanceof Variable) {
                Block mainBlock = null;
                Workflow flow = memory.getCodeSpace();
                while (memory.getState() == LeviathanConstants.Status.INIT && 
                        checkForShutdown()) {
                    ProcessStackEntry stack = null;
                    if ((stack = memory.peekStack()) == null) {
                        Statement statement = null;
                        int pos = 0;
                        if (memory.getCurrentStatement() != null) {
                            pos = flow.getStatements().indexOf(
                                    memory.getCurrentStatement()) + 1;
                        }
                        for (; pos < flow.getStatements().size(); pos++) {
                            if (statement instanceof Variable) {
                                Variable var = (Variable)statement;
                                if (memory.getHeap().containsVariable(var.getName())) {
                                    continue;
                                }
                                Map mainBlockVars = new HashMap();
                                // create a new stack entry it will add it self
                                // to the stack
                                VariableStackEntry entry = 
                                        new VariableStackEntry(memory,null, var);
                                memory.setState(LeviathanConstants.Status.RUNNING);
                                memory.setCurrentStatement(statement);
                            }
                        }
                        if (memory.peekStack() == null) {
                            break;
                        }
                    } else {
                        stack.execute();
                    }
                }
                if (memory.peekStack() == null) {
                    for (Statement statement : flow.getStatements()) {
                        if ((statement instanceof Block)
                                && !(statement instanceof MethodDefinition)) {
                            mainBlock = (Block) statement;
                        }
                    }
                    memory.setCurrentStatement(mainBlock);
                    Map mainBlockVars = new HashMap();
                    mainBlockVars.put(Constants.BLOCK, mainBlock);
                    BlockStackEntry entry = new 
                            BlockStackEntry(memory,null, mainBlockVars);
                    memory.setState(LeviathanConstants.Status.RUNNING);
                }
            }
            ProcessStackEntry stack = null;
            while (memory.getState() == LeviathanConstants.Status.RUNNING &&
                    checkForShutdown() &&
                    (stack = memory.peekStack()) != null && 
                    memory.getCurrentStatement() instanceof Block) {
                stack.execute();
            }
            if (memory.getCurrentStatement() instanceof Block && 
                    memory.peekStack() == null &&
                    memory.getState() == LeviathanConstants.Status.RUNNING) {
                memory.setState(LeviathanConstants.Status.COMPLETED);
            }
        } catch (AbortException ex) {
            // processing has been borted for now
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
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
    public synchronized void persist(File dirPath) throws EngineException {
        try {
            if (this.memory.getState() != LeviathanConstants.Status.COMPLETED) {
                File file = new File(dirPath, memory.getGuid());
                FileOutputStream out = new FileOutputStream(file);
                out.write(ObjectSerializer.serialize(memory));
                out.close();
            }
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
     * This method is called to see if the environment has been shut down.
     * 
     * @throws AbortException 
     */
    private boolean checkForShutdown() throws AbortException {
       if (LeviathanEngine.getInstance().getStatus() != 
               LeviathanConstants.Status.RUNNING) {
           throw new AbortException("The engine is not running");
       }
       return true;
    }
}
