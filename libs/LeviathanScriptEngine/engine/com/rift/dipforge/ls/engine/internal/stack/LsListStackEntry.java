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
 * LsListStackEntry.java
 */

// package
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.Expression;
import com.rift.dipforge.ls.parser.obj.LsList;
import java.util.*;


/**
 * This implementation
 * 
 * @author brett chaldecott
 */
public class LsListStackEntry extends StatementStackEntry {

    // private member variables
    private LsList list;
    private List listValues = null;
    private Map mapValues = null;
    private Expression key = null;
    private Expression value = null;
    private Object resultKey;
    private List resultList;
    private Map resultMap;
    
    /**
     * This constructor sets up all the Ls parameters.
     * 
     * @param processorMemoryManager The list of parameters.
     * @param parent The parent
     */
    public LsListStackEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, LsList list) throws EngineException {
        super(processorMemoryManager, parent);
        this.list = list;
        initLists(list);
    }

    
    /**
     * This constructor sets up all the list processing information.
     * 
     * @param processorMemoryManager The list of information.
     * @param parent The list of parameters.
     * @param variables The variables.
     */
    public LsListStackEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables, LsList list)
            throws EngineException {
        super(processorMemoryManager, parent, variables);
        this.list = list;
        initLists(list);
    }

    
    
    /**
     * This method executes the list stack.
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        if (listValues != null && listValues.size() > 0) {
            ExpressionStatementComponent component = new 
                    ExpressionStatementComponent(
                    this.getProcessorMemoryManager(),this,
                    (Expression)listValues.get(0));
        } else if (mapValues != null && mapValues.size() > 0) {
            Set keySet = mapValues.keySet();
            if (resultKey == null) {
                this.key = (Expression)keySet.toArray()[0];
                ExpressionStatementComponent component = new 
                    ExpressionStatementComponent(
                    this.getProcessorMemoryManager(),this,
                    this.key);
            } else {
                ExpressionStatementComponent component = new 
                    ExpressionStatementComponent(
                    this.getProcessorMemoryManager(),this,
                    (Expression)mapValues.get(this.key));
            }
        } else {
            if (this.resultList != null) {
                ProcessStackEntry assignment =
                    (ProcessStackEntry) this.getParent();
                assignment.setResult(resultList);
            } else if (this.resultMap != null) {
                ProcessStackEntry assignment =
                    (ProcessStackEntry) this.getParent();
                assignment.setResult(resultMap);
            }
            pop();
        }
    }

    
    /**
     * This sets the result.
     * 
     * @param result The results
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        if (listValues != null) {
            resultList.add(result);
            listValues.remove(0);
        } else if (mapValues != null) {
            if (resultKey == null) {
                resultKey = result;
            } else {
                resultMap.put(resultKey, result);
                resultKey = null;
                mapValues.remove(this.key);
            }
        }
    }
    
    
    /**
     * This method inits the list
     * 
     * @param list The list of arguments
     * @throws EngineException 
     */
    private void initLists(LsList list) throws EngineException {
        if (list.getValueList() != null) {
            listValues = new ArrayList();
            listValues.addAll(list.getValueList());
            resultList = new ArrayList();
            
        } else if (list.getValueMap() != null) {
            mapValues = new HashMap();
            mapValues.putAll(list.getValueMap());
            resultMap = new HashMap();
        }
    }
}
