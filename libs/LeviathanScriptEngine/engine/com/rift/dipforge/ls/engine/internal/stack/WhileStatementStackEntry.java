/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.Constants;
import com.rift.dipforge.ls.engine.internal.InvalidTypeException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.WhileStatement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author brett
 */
public class WhileStatementStackEntry extends StatementStackEntry {

    // private member variables
    private WhileStatement statement;
    private Boolean expressionResult = null;

    /**
     * The constructor that sets up the while statement stack entry
     *
     * @param processorMemoryManager The reference to the processor memory
     * manager.
     * @param parent The reference to the parent object.
     */
    public WhileStatementStackEntry(
            ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent,
            WhileStatement statement) throws EngineException {
        super(processorMemoryManager, parent);
        this.statement = statement;
        this.addVariable(Constants.CONTINUE, null);
        this.addVariable(Constants.BREAK, null);
    }

    /**
     * This method is called to execute a while statement
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        if (StackUtil.loopPopStack(this)) {
            pop();
        } else if (expressionResult == null) {
            ExpressionStatementComponent expression = new ExpressionStatementComponent(
                    this.getProcessorMemoryManager(), this,
                    statement.getExpression());
        } else if (expressionResult.booleanValue()) {
            this.setVariable(Constants.CONTINUE, null);
            Map parameters = new HashMap();
            parameters.put(Constants.BLOCK, statement.getBlock());
            BlockStackEntry stackEntry = new BlockStackEntry(
                    this.getProcessorMemoryManager(), this, parameters);
            expressionResult = null;
        } else {
            pop();
        }
    }

    /**
     * This method sets the result.
     *
     * @param result The reference to the result
     * @throws EngineException
     */
    @Override
    public void setResult(Object result) throws EngineException {
        if (!(result instanceof Boolean)) {
            throw new InvalidTypeException("Expected a boolean got a : "
                    + result.getClass().getName());
        }
        this.expressionResult = (Boolean) result;
    }
}
