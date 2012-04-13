/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2009  Rift IT Contracting
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
 * CallHandler.java
 */

// package path
package com.rift.coad.change.request.action;

// java imports
import java.util.ArrayList;
import java.util.List;

// coadunation imports
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.datamapper.DataMapper;
import com.rift.coad.datamapper.DataMapperAsync;

/**
 * The implementation of the call handler
 *
 * @author brett chaldecott
 */
public class CallHandler {
    // private member variables
    private ActionInstanceImpl instance;
    //private StackEntry stack;
    //private Call task;

    
    /**
     * This constructor sets all the member variables.
     * 
     * @param instance The instance value.
     * @param stack The stack value.
     * @param task The task.
     */
    public CallHandler(ActionInstanceImpl instance) {
        this.instance = instance;
        //this.stack = stack;
        //this.task = task;
    }

    /**
     * This method returns true if the current action has been handled by the stack.
     *
     * @param actionStack The stack
     * @return TRUE if handled, FALSE if not.
     */
    /*public boolean handleCallEnd(ActionStack actionStack) throws Exception {
        StackEvent[] events = actionStack.getEvents();
        if ((events == null) || (events.length == 0)) {
            return false;
        }
        StackEvent lastEvent = events[events.length -1];
        ActionTaskDefinition task = lastEvent.getTask();
        if (task.getTaskDefinitionId().equals(this.task.getTaskDefinitionId()) &&
                lastEvent.getStatus().equals(ActionConstants.RUNNING)) {
            instance.addStackEvent(task, ActionInstanceImpl.ACTION_STATUS.ACTION_COMPLETE);
            return true;
        }
        return false;
    }*/

    /**
     * This method executes the operatorion.
     * 
     * @return The reference to the updated stack.
     * @throws java.lang.Exception
     */
    /*public StackEntry execute() throws Exception {
        DataMapperAsync handler = null;
        if (task.getJndi() != null) {
            handler = (DataMapperAsync)RPCMessageClient.create(
                    "change/request/action/ActionHandler",DataMapper.class,
                    DataMapperAsync.class,task.getJndi(),instance.getId());
        } else {
            List<String> services = new ArrayList<String>();
            services.add(task.getDataMapperMethod().getService());
            handler = (DataMapperAsync)RPCMessageClient.create(
                    "change/request/action/ActionHandler", DataMapper.class,
                    DataMapperAsync.class, services, false,instance.getId());
        }
        DataType[] attributes = task.getDataMapperMethod().getAttributes();
        String[] parameterList = task.getParameters();
        DataType[] parameters = new DataType[0];
        if (attributes != null) {
            if (parameterList.length != attributes.length) {
                throw new Exception("Runtime Exception: the parameter list does not match the target list.");
            }
            parameters = new DataType[attributes.length];
            for (int index = 0; index < attributes.length; index++) {
                DataType value = this.stack.getStackVariable(task.getParameters()[index]);
                value.setDataName(attributes[index].getDataName());
                parameters[index] = value;
            }
        }
        handler.execute(task.getDataMapperMethod().getService(),
                task.getDataMapperMethod().getName(), parameters);
        instance.addStackEvent(task, ActionInstanceImpl.ACTION_STATUS.ACTION_RUNNING);
        return null;
    }*/



}
