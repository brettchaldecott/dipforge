/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.dipforge.ls.engine.internal.stack;

import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.Assignment;
import com.rift.dipforge.ls.parser.obj.Variable;
import java.util.Map;

/**
 *
 * @author brett
 */
public class VariableStackEntry extends StatementStackEntry {

    // private variable information
    private Variable var;
    private Object value;
    private boolean set = false;

    /**
     * This constructor sets up the processor memory manager.
     *
     * @param processorMemoryManager The reference to the processor memory
     * manager.
     * @param parent The parent reference.
     * @param variables The list of variables.
     */
    public VariableStackEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Map variables, Variable var) {
        super(processorMemoryManager, parent, variables);
        this.var = var;
    }

    /**
     * This constructor sets up the variable information.
     *
     * @param processorMemoryManager The processor memory manager.
     * @param parent The reference to the parent.
     */
    public VariableStackEntry(ProcessorMemoryManager processorMemoryManager,
            ProcessStackEntry parent, Variable var) {
        super(processorMemoryManager, parent);
        this.var = var;
    }

    /**
     * This method is called to execute the variable stack entry.
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        if (this.var.getInitialValue() == null || set) {
            if (this.getParent() != null) {
                this.getParent().addVariable(this.var.getName(), value);
            } else {
                this.getProcessorMemoryManager().getHeap().
                        addVariable(this.var.getName(), value);
            }
            this.pop();

        } else {
            new AssignmentStatementComponent(this.getProcessorMemoryManager(),
                    this, this.var.getInitialValue());
        }
    }

    /**
     * This method sets the result.
     *
     * @param result
     * @throws EngineException
     */
    @Override
    public void setResult(Object result) throws EngineException {
        this.set = true;
        this.value = value;
    }
}
