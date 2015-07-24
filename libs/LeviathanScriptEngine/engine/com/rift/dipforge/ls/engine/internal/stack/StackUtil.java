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
 * ContinueStatementStackEntry.java
 */
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.Constants;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;

/**
 * This contains stack information.
 * 
 * @author brett chaldecott
 */
public class StackUtil {
    /**
     * This method is called to pop the stack.
     * @param entry The entry that is being evaluated.
     * @return TRUE This method returns true
     * @throws EngineException 
     */
    public static boolean popStack(ProcessStackEntry entry) throws EngineException {
        if (entry.containsVariable(Constants.BREAK)) {
            Boolean breakValue = (Boolean)entry.getVariable(Constants.BREAK);
            if (breakValue != null) {
                return true;
            }
        }
        if (entry.containsVariable(Constants.CONTINUE)) {
            Boolean continueValue = (Boolean)entry.getVariable(Constants.CONTINUE);
            if (continueValue != null) {
                return true;
            }
        }
        if (entry.containsVariable(Constants.RETURN)) {
            Boolean returnValue = (Boolean)entry.getVariable(Constants.RETURN);
            if (returnValue != null) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * This method is called to pop the stack.
     * @param entry The entry that is being evaluated.
     * @return TRUE This method returns true
     * @throws EngineException 
     */
    public static boolean loopPopStack(ProcessStackEntry entry) throws EngineException {
        if (entry.containsVariable(Constants.BREAK)) {
            Boolean breakValue = (Boolean)entry.getVariable(Constants.BREAK);
            if (breakValue != null) {
                return true;
            }
        }
        if (entry.containsVariable(Constants.RETURN)) {
            Boolean returnValue = (Boolean)entry.getVariable(Constants.RETURN);
            if (returnValue != null) {
                return true;
            }
        }
        return false;
    }
    
}
