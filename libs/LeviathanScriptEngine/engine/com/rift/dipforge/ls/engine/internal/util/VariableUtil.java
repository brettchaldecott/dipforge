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
 * ParameterArgument.java
 */
package com.rift.dipforge.ls.engine.internal.util;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.InvalidParameterException;
import com.rift.dipforge.ls.parser.obj.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This util is used to manipulate variables.
 *
 * @author brett chaldecott
 */
public class VariableUtil {

    /**
     * This method is called to clone the variables.
     *
     * @param callStatement The call statement.
     * @param method The method
     * @return The list of variables.
     * @throws EngineException
     */
    public static List<Variable> cloneVariables(CallStatement callStatement,
            MethodDefinition method) throws EngineException {
        if (!(callStatement.getEntries().get(
                callStatement.getEntries().size() - 1).getArgument() instanceof ParameterArgument)) {
            throw new IncompatibleClassChangeError(
                    "Arguments parameters are not compatible");
        }
        ParameterArgument parameterArgument =
                (ParameterArgument) callStatement.getEntries().get(
                callStatement.getEntries().size() - 1).getArgument();
        List<Expression> expressions = parameterArgument.getExpressions();
        List<Variable> parameters = method.getParameters();
        if (expressions.size() != parameters.size()) {
            throw new InvalidParameterException("Parameter number mismatch");
        }
        List<Variable> result = new ArrayList<Variable>();
        for (int index = 0; index < parameters.size(); index++) {
            Expression exp = expressions.get(index);
            Variable var = parameters.get(index);
            result.add(new Variable(Types.DEF, var.getName(), new Assignment(exp)));
        }
        return result;
    }

    /**
     * This method returns true if the flow contains a specified heap variable.
     * 
     * @param flow The flow to perform the check against
     * @param name The name of the variable to perform the check for.
     * @return TRUE if the variable is found.
     */
    public static boolean containsHeapVariable(Workflow flow, String name) {
        for (Statement statement : flow.getStatements()) {
            if (statement instanceof Variable) {
                Variable var = (Variable)statement;
                if (var.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
}
