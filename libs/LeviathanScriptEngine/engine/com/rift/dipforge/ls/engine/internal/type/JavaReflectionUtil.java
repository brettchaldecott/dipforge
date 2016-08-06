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
 * JavaReflectionTypeManager.java
 */
// package information
package com.rift.dipforge.ls.engine.internal.type;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import java.lang.reflect.Field;
import java.util.List;

/**
 * The java reflection util.
 *
 * @author brett chaldecott
 */
public class JavaReflectionUtil {

    /**
     * This method returns the object as defined by the variable and the call
     * statement arguments.
     *
     * @param variable The variable
     * @param entries The list of entries
     * @return The object reference
     * @throws EngineException The engin exception that can get thrown if the object was not found
     */
    public static Object getObject(Object variable, 
            List<CallStatement.CallStatementEntry> entries)
            throws EngineException {
        try {
            Object result = variable;
            for (int index = 0;
                    index < entries.size(); index++) {
                CallStatement.CallStatementEntry entry =
                        entries.get(index);
                Class def = variable.getClass();
                Field field = def.getDeclaredField(entry.getName());
                result = field.get(result);
            }
            return result;
        } catch (Exception ex) {
            throw new EngineException(
                    "Failed to retrieve the field information because " +
                    ex.getMessage(),ex);
        }
    }
}
