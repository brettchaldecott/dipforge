/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2013  Rift IT Contracting
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
 * LsActionStackEntry.java
 */
package com.rift.coad.change.request.action.leviathan.action;

import com.rift.coad.change.request.action.sleep.SleepManager;
import com.rift.coad.datamapperbroker.DataMapperBrokerDaemon;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.dipforge.ls.engine.EngineException;
import com.rift.dipforge.ls.engine.LeviathanConstants;
import com.rift.dipforge.ls.engine.internal.ProcessStackEntry;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import com.rift.dipforge.ls.parser.obj.CallStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * This object represents the action stack information.
 *
 * @author brett chaldecott
 */
public class LsActionStackEntry extends ProcessStackEntry {

    /**
     * The method identifier.
     */
    public final static String METHOD_SLEEP = "sleep";
    
    /**
     * This default sleep time is to be used if the sleep period is not set
     * correctly.
     */
    public final static long DEFAULT_SLEEP_TIME = 30 * 1000;
    
    // class singletons
    private static Logger log = Logger.getLogger(LsActionStackEntry.class);
    
    // private member variables
    private List parameters = new ArrayList();
    private CallStatement callStatement;

    /**
     * This constructor sets up the action stack information.
     *
     * @param processorMemoryManager The reference to the process memory
     * manager.
     * @param parent The reference to the parent stack entry.
     */
    public LsActionStackEntry(ProcessStackEntry parent, CallStatement callStatement,
            Object variable, List parameters) {
        super(parent.getProcessorMemoryManager(), parent);
        this.callStatement = callStatement;
        this.parameters = parameters;
    }

    /**
     * This constructor sets up the ls action stack entries parent variables.
     *
     * @param processorMemoryManager The reference to the processor memory
     * manager.
     * @param parent The reference to the parent.
     * @param variables The reference to the list of variables.
     */
    public LsActionStackEntry(ProcessStackEntry parent, Map variables,
            CallStatement callStatement, Object variable, List parameters) {
        super(parent.getProcessorMemoryManager(), parent, variables);
        this.callStatement = callStatement;
        this.parameters = parameters;
    }

    /**
     * This method is called to execute this stack entry.
     *
     * @throws EngineException
     */
    @Override
    public void execute() throws EngineException {
        try {
            // get the name of the method being called.
            CallStatement.CallStatementEntry entry =
                    callStatement.getEntries().get(callStatement.getEntries().size() - 1);
            if (!entry.getName().equals(METHOD_SLEEP)) {
                throw new EngineException("The action has been invoked by an "
                        + "unrecognised method [" + entry.getName() + "]");
            }
            if (this.parameters.size() != 1) {
                throw new EngineException("An invalid number of parameters was passed, "
                        + "expected [1] got [" + this.parameters.size() + "]");
            }
            long sleepPeriod = getSleepPeriod(parameters.get(0));
            SleepManager daemon = (SleepManager)
                        ConnectionManager.getInstance().getConnection(
                        SleepManager.class,
                        "change/request/action/SleepManager");
            daemon.addAction(this.getParent().getProcessorMemoryManager().getGuid(), 
                    sleepPeriod);
            this.getProcessorMemoryManager().setState(LeviathanConstants.Status.SUSPENDED);
            return;
        } catch (EngineException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new EngineException("Failed to process the action stack entry :"
                    + ex.getMessage(), ex);
        }
    }

    @Override
    public void setResult(Object result) throws EngineException {
        // do nothing with the result
    }

    /**
     * This method returns the sleep period
     *
     * @param parameter The parameter to retrieve.
     * @return The 
     */
    private long getSleepPeriod(Object parameter) {
        // use a default sleep time of 30 seconds
        long period = DEFAULT_SLEEP_TIME;
        log.info("##### The sleep parameter type is : " + parameter.getClass().getName());
        if (parameter instanceof String) {
            period = Long.parseLong((String)parameter);
        } else if (parameter instanceof Long) {
            period = (Long)parameter;
        } else if (parameter.getClass().equals(long.class)) {
            period = long.class.cast(parameter);
        } else if (parameter instanceof Integer) {
            period = (Integer)parameter;
        } else if (parameter.getClass().equals(int.class)) {
            period = int.class.cast(parameter);
        } else if (parameter instanceof Double) {
            period = ((Double)parameter).longValue();
        } else if (parameter.getClass().equals(double.class)) {
            period = double.class.cast(parameter).longValue();
        }
        return period;
    }
}
