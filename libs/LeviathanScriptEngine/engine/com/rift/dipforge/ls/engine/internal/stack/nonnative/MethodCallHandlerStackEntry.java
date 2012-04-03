/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// package path
package com.rift.dipforge.ls.engine.internal.stack.nonnative;

// java imports
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.TypeManager;
import com.rift.dipforge.ls.engine.internal.Constants;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.engine.internal.TypeManagerLookup;
import com.rift.dipforge.ls.engine.internal.stack.BlockStackEntry;
import com.rift.dipforge.ls.engine.internal.stack.ExpressionStatementComponent;
import com.rift.dipforge.ls.engine.internal.stack.StatementStackEntry;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import com.rift.dipforge.ls.parser.obj.Expression;
import com.rift.dipforge.ls.parser.obj.ParameterArgument;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The method call handler stack entry
 * 
 * @author brett chaldecott
 */
public class MethodCallHandlerStackEntry extends StatementStackEntry {

    // private member variables
    private ProcessStackEntry caller;
    private Object variable;
    private CallStatement callStatement;
    private List<Expression> expressions;
    private List parameters = new ArrayList();
    private boolean executed = false;
    private Object result;
    
    /**
     * This constructor sets up the call handler information
     * 
     * @param processorMemoryManager The reference to the call handler
     * @param parent The parent information
     * @param callStatement The call statement
     */
    public MethodCallHandlerStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent,
            CallStatement callStatement,
            Object variable) throws EngineException {
        super(processorMemoryManager, parent);
        this.caller = parent;
        this.callStatement = callStatement;
        expressions = getParameters(callStatement);
        this.variable = variable;
    }
    
    
    /**
     * The constructor of the method call handler.
     * 
     * @param processorMemoryManager The processor memory manager.
     * @param parent The parent
     * @param variables The variables
     * @param callStatement The call statement
     * @throws EngineException 
     */
    public MethodCallHandlerStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables,
            CallStatement callStatement, Object variable) throws EngineException {
        super(processorMemoryManager, parent, variables);
        this.caller = parent;
        this.callStatement = callStatement;
        expressions = getParameters(callStatement);
        this.variable = variable;
    }
    
    
    /**
     * This method returns the parameters.
     * 
     * @param callStatement The call statement that the expression list will be retrieved from.
     * @return The list of expressions
     * @throws EngineException 
     */
    private List<Expression> getParameters(CallStatement callStatement)
            throws EngineException {
        ParameterArgument parameterArgument = 
                (ParameterArgument)callStatement.getEntries().get(
                callStatement.getEntries().size() -1).getArgument();
        List<Expression> expressions = new ArrayList<Expression>();
        expressions.addAll(parameterArgument.getExpressions());
        return expressions;
    }
    
    
    /**
     * This method executes the method.
     * 
     * @throws EngineException 
     */
    @Override
    public void execute() throws EngineException {
        if (expressions.size() > 0) {
            ExpressionStatementComponent expression = new ExpressionStatementComponent(
                    this.getProcessorMemoryManager(), this,
                    expressions.get(0));
        } else if (!executed) {
            // set the parent to null so the method will not recurse
            // into it variables for a look up.
            this.setParent(null);
            TypeManager manager = TypeManagerLookup.getInstance().
                    getManager(variable);
            ProcessStackEntry entry = manager.
                    createStackEntryForMethod(this, null, callStatement,
                    variable, parameters);
            executed = true;
        } else if (executed) {
            ProcessStackEntry assignment =
                    (ProcessStackEntry) caller;
            assignment.setResult(result);
            pop();
        }
    }
    
    
    /**
     * This method sets the results
     * 
     * @param result The result string
     * @throws EngineException 
     */
    @Override
    public void setResult(Object result) throws EngineException {
        if (expressions.size() > 0) {
            parameters.add(result);
            expressions.remove(0);
        } else {
            this.result = result;
        }
    }
    
}
