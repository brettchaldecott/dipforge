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
 * ActionInstanceImpl.java
 */

// package path
package com.rift.coad.change.request.action;

// java import
import java.util.Date;

// log4j import
import com.rift.coad.change.ChangeManagerDaemon;
import com.rift.coad.util.change.ChangeException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import org.apache.log4j.Logger;


// action import
import com.rift.coad.change.rdf.objmapping.change.action.ActionStack;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.util.change.Change;
import com.rift.coad.util.change.ChangeLog;
import com.rift.coad.change.ChangeManagerDaemonImpl;
import com.rift.coad.change.rdf.objmapping.change.ActionDefinition;
import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.change.Request;
import com.rift.coad.change.rdf.objmapping.change.action.ActionConstants;
import com.rift.coad.change.rdf.objmapping.change.action.StackEntry;
import com.rift.coad.change.rdf.objmapping.change.action.StackEvent;
import com.rift.coad.change.rdf.objmapping.change.task.Assign;
import com.rift.coad.change.rdf.objmapping.change.task.Block;
import com.rift.coad.change.rdf.objmapping.change.task.Call;
import com.rift.coad.change.rdf.objmapping.change.task.ConcurrentBlock;
import com.rift.coad.change.rdf.objmapping.change.task.EmbeddedBlock;
import com.rift.coad.change.rdf.objmapping.change.task.exception.Catch;
import com.rift.coad.change.rdf.objmapping.change.task.exception.Try;
import com.rift.coad.change.rdf.objmapping.change.task.logic.ElseIf;
import com.rift.coad.change.rdf.objmapping.change.task.logic.If;
import com.rift.coad.change.rdf.objmapping.change.task.logic.Switch;
import com.rift.coad.change.rdf.objmapping.change.task.loop.ForEach;
import com.rift.coad.change.rdf.objmapping.change.task.loop.ForLoop;
import com.rift.coad.change.rdf.objmapping.change.task.loop.WhileLoop;
import com.rift.coad.change.request.rdf.TestTask;
import com.rift.coad.lib.Resource;
import com.rift.coad.lib.ResourceIndex;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.ArrayList;
import java.util.List;


/**
 * This object is the implementation of the action instance.
 *
 * @author brett chaldecott
 */
public class ActionInstanceImpl implements ActionInstance,ResourceIndex,Resource {

    /**
     * The enum defining the type of action to perform on the object.
     */
    public enum TYPE {

        ADD,
        UPDATE,
        DELETE
    };


    /**
     * The action enumerated values
     */
    public enum ACTION_STATUS {
        ACTION_RUNNING,
        ACTION_COMPLETE,
        ACTION_ERROR,
        ACTION_FINISHED
    }

    /**
     * This object represents a change.
     */
    public static class ActionChange implements Change {

        private TYPE changeType;
        private ActionStack stack;

        /**
         * This constructor creates a new change using the supplied parameters.
         * 
         * @param changeType The type of change that is being applied.
         * @param stack The stack containing the current state of this action.
         * @throws com.rift.coad.change.request.action.ActionException
         */
        public ActionChange(TYPE changeType, ActionStack stack) throws ActionException {
            try {
                
                this.stack = (ActionStack) stack.clone();
                this.changeType = changeType;
            } catch (Exception ex) {
                log.error("Failed to instanciate the change : " + ex.getMessage(), ex);
                throw new ActionException("Failed to instanciate the change : " + ex.getMessage(), ex);
            }
        }

        /**
         * This method is responsible for applying the changes.
         *
         * @throws ChangeException
         */
        public void applyChanges() throws com.rift.coad.util.change.ChangeException {
            try {
                Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
                log.debug("###### apply the stack changes : " + this.stack.getEvents().length);
                for (StackEvent entry : this.stack.getEvents()) {
                    log.debug("########### event : " + entry);
                }
                if ((changeType == TYPE.ADD) || (changeType == TYPE.UPDATE)) {
                    log.debug("###### persist the stack : " + stack.getId());
                    session.persist(this.stack);
                } else {
                    log.debug("###### remove the stack : " + stack.getId());
                    session.remove(this.stack);
                }
            } catch (Exception ex) {
                log.error("Failed to apply the changes : " + ex.getMessage(), ex);
            }
        }
    }

    // class singletons
    private static Logger log = Logger.getLogger(ActionInstanceImpl.class);

    // private member variables
    private com.rift.coad.change.rdf.objmapping.change.task.exception.ChangeException currentException;
    private DataType currentResult;
    private ActionStack action = null;

    /**
     * The constructor sets the action information.
     */
    public ActionInstanceImpl(ActionStack action) {
        this.action = action;
    }

    /**
     *
     * @param request
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public ActionInstanceImpl(String masterRequestId, Request request) throws ActionException {
        try {
            ChangeManagerDaemon daemon = (ChangeManagerDaemon) ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class, "java:comp/env/bean/change/ChangeManagerDaemon");
            ActionDefinition definition = daemon.getActionDefinition(request.getData().getIdForDataType(), request.getAction());
            if (definition == null) {
                log.error("The action [" + request.getAction() + "] on object [" + request.getData().getIdForDataType()
                        + "] does not exist");
                throw new ActionException("The action [" + request.getAction() + "] on object [" + request.getData().getIdForDataType()
                        + "] does not exist");
            }
            List<DataType> variables = new ArrayList<DataType>();
            if (request.getDependancies() != null) {
                for (int index = 0; index < request.getDependancies().length; index++) {
                    variables.add((DataType) request.getDependancies()[index].clone());
                }
            }
            variables.add(request.getData());
            // copy the local variables for the parent of this flow.
            if (definition.getParent() instanceof Block) {
                DataType[] scopeVariables = copyData(Block.class.cast(definition.getParent()).getParameters());
                for (DataType scopeVariable : scopeVariables) {
                    variables.add(scopeVariable);
                }
            }
            this.action = new ActionStack(masterRequestId, definition, request.getId(),
                    new StackEntry(variables.toArray(new DataType[0]), definition.getParent()));
            addStackEvent(this.action.getStack().getBlock(), ACTION_STATUS.ACTION_RUNNING);
            ChangeLog.getInstance().addChange(new ActionChange(TYPE.ADD, this.action));
        } catch (ActionException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to instanciate the action instance : " + ex.getMessage(), ex);
            throw new ActionException("Failed to instanciate the action instance : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the id of the action instance.
     *
     * @return The string containing the id of the action instance.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public String getId() throws ActionException {
        return action.getId();
    }

    /**
     * This method returns the id of the request.
     *
     * @return The id of the request.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public String getRequestId() throws ActionException {
        return action.getRequestId();
    }


    /**
     * This method returns the master request id.
     *
     * @return The string containing the master request id.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public String getMasterRequestId() throws ActionException {
        return action.getMasterRequestId();
    }

    /**
     * This method returns the name of the action being managed by this instance.
     *
     * @return The string containing the action to be managed by this object.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public String getAction() throws ActionException {
        return action.getAction().getActionInfo().getName();
    }

    /**
     * This method returns the data type that this action is being performed on.
     *
     * @return This method returns the id of the data type.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public String getDataTypeId() throws ActionException {
        return action.getAction().getDataTypeId();
    }

    /**
     * This method returns the current status of this action stack
     * @return
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public String getStatus() throws ActionException {
        log.debug("Current status length is : " + action.getEvents().length);
        if (action.getEvents().length == 0) {
            throw new ActionException("The action instance is not properly configured " +
                    "and does not contain any stack events");
        }
        
        return action.getEvents()[action.getEvents().length - 1].getStatus();
    }

    /**
     * This method returns the last event that occurred on this intance.
     *
     * @return The last action event.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public StackEvent getLastEvent() throws ActionException {
        if (action.getEvents().length == 0) {
            throw new ActionException("The action instance is not properly configured " +
                    "and does not contain any stack events");
        }
        return action.getEvents()[action.getEvents().length - 1];
    }

    /**
     * This method returns the action stack.
     *
     * @return The action stack
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public ActionStack getStack() throws ActionException {
        return action;
    }


    /**
     * This method is responsible for updating the stack
     * @param stack
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public void setStack(ActionStack stack) throws ActionException {
        try {
            this.action = stack;
            ChangeLog.getInstance().addChange(new ActionChange(TYPE.UPDATE, this.action));
        } catch (Exception ex) {
            log.error("Failed to set the action stack for this instance : " + ex.getMessage(), ex);
            throw new ActionException("Failed to set the action stack for this instance : " + ex.getMessage(), ex);
        }
    }


    /**
     * This method is responsible for removing this action.
     * 
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public void remove() throws ActionException {
        try {
            ChangeLog.getInstance().addChange(new ActionChange(TYPE.DELETE, this.action));
        } catch (Exception ex) {
            log.error("Failed to delete the action stack for this instance : " + ex.getMessage(), ex);
            throw new ActionException("Failed to delete the action stack for this instance : " + ex.getMessage(), ex);
        }
    }


    /**
     * This method is called to execute the action instance.
     *
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public synchronized void execute() throws ActionException {
        try {
            // this method executies the current stack.
            execute(action.getStack());
        } catch (Exception ex) {
            try {
                addStackEvent(action.getStack().getBlock(), ACTION_STATUS.ACTION_ERROR, ex.getMessage());
            } catch (Exception ex2) {
                // ignore
            }
            log.error("Failed to excute : " + ex.getMessage(), ex);
        } finally {
            try {
                ChangeLog.getInstance().addChange(new ActionChange(TYPE.UPDATE, this.action));
            } catch (ChangeException ex) {
                log.error("Failed to update the change log because : " + ex.getMessage(),ex);
            }
        }
    }

    /**
     * This method is called to execute the action instance updating the data type passed in.
     *
     * @param result The result of the external call.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public synchronized void execute(DataType result) throws ActionException {
        try {
            currentResult = result;

            // this method executies the current stack.
            execute(action.getStack());
        } catch (Exception ex) {
            try {
                addStackEvent(action.getStack().getBlock(), ACTION_STATUS.ACTION_ERROR, ex.getMessage());
            } catch (Exception ex2) {
                // ignore
            }
            log.error("Failed to excute : " + ex.getMessage(), ex);
        } finally {
            try {
                ChangeLog.getInstance().addChange(new ActionChange(TYPE.UPDATE, this.action));
            } catch (ChangeException ex) {
                log.error("Failed to update the change log because : " + ex.getMessage(),ex);
            }
        }
    }

    /**
     * This method is used to execute the action after an exception has occurred.
     *
     * @param ex The exception that has occurred.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public synchronized void execute(Exception ex) throws ActionException {
        try {
            if (ex instanceof com.rift.coad.change.rdf.objmapping.change.task.exception.ChangeException) {
                currentException =
                        (com.rift.coad.change.rdf.objmapping.change.task.exception.ChangeException) ex;
            } else {
                currentException = new com.rift.coad.change.rdf.objmapping.change.task.exception.ChangeException(ex);
            }

            // this method executies the current stack.
            execute(action.getStack());
        } catch (Exception e) {
            log.error("Failed to excute : " + ex.getMessage(), ex);
        } finally {
            try {
                ChangeLog.getInstance().addChange(new ActionChange(TYPE.UPDATE, this.action));
            } catch (ChangeException e) {
                log.error("Failed to update the change log because : " + e.getMessage(),e);
            }
        }
    }

    
    /**
     * This executes the current action instance requests, and is synchronized
     */
    protected synchronized boolean execute(StackEntry stack) throws Exception {
        if (stack == null) {
            return true;
        }

        while (true) {
            System.out.println("Inside the while loop");
            if (stack.getChild() != null) {
                try {
                    if (!execute(stack.getChild())) {
                        return false;
                    }
                } catch (com.rift.coad.change.rdf.objmapping.change.task.exception.ChangeException ex) {
                    this.currentException = ex;
                    log.error("Sub block threw an exception : " + ex.getMessage(), ex);
                } catch (Exception ex) {
                    log.error("Sub block threw an exception : " + ex.getMessage(), ex);
                    this.currentException = new com.rift.coad.change.rdf.objmapping.change.task.exception.ChangeException(ex);
                }
                stack.setChild(null);
            }

            // deal with exceptions here
            if (handleException(stack)) {
                continue;
            }

            // deal with results here
            handleResult(stack);
            
            if (stack.getCurrentTask() == null) {
                break;
            }

            // TODO: evaluate using a spring object factory for this section, as it would
            // enable configuration of operator rather than a hard coded if statement.
            
            // operations
            if (stack.getCurrentTask() instanceof Assign) {
                new AssignHandler(this, stack, (Assign) stack.getCurrentTask()).execute();
            } else if (stack.getCurrentTask() instanceof Switch) {
                new SwitchHandler(this, stack, (Switch) stack.getCurrentTask()).execute();
                stack.setCurrentTask(stack.getCurrentTask().getNext());
                continue;
            } else if (stack.getCurrentTask() instanceof If) {
                new IfHandler(this, stack, (If) stack.getCurrentTask()).execute();
                continue;
            } else if (stack.getCurrentTask() instanceof ForEach) {
                // check if there are any more task to process under the for each.
                if (null == new ForEachHandler(this, stack, (ForEach) stack.getCurrentTask()).execute()) {
                    addComplete(stack);
                    return true;
                }
                continue;
            } else if (stack.getCurrentTask() instanceof ForLoop) {
                if (null == new ForHandler(this,stack,(ForLoop)stack.getCurrentTask()).execute()) {
                    addComplete(stack);
                    return true;
                }
                continue;
            } else if (stack.getCurrentTask() instanceof WhileLoop) {
                if (null == new WhileLoopHandler(this,stack,(WhileLoop)stack.getCurrentTask()).execute()) {
                    addComplete(stack);
                    return true;
                }
                continue;
            } else if (stack.getCurrentTask() instanceof ConcurrentBlock) {
                // TODO: Implement the
            } else if (stack.getCurrentTask() instanceof Call) {
                CallHandler handler = new CallHandler(this,stack,(Call)stack.getCurrentTask());
                if (!handler.handleCallEnd(action)) {
                    handler.execute();
                    return false;
                }
            } else if (isBlock(stack.getCurrentTask())) {
                // create the block for the current entry
                StackEntry child = createStack(stack, stack.getCurrentTask());
                child.setCurrentTask(Block.class.cast(stack.getCurrentTask()).getChild());
                stack.setChild(child);
                stack.setCurrentTask(stack.getCurrentTask().getNext());
                continue;
            } else if (stack.getCurrentTask() instanceof TestTask) {
                // this section is for testing
                TestTask task = (TestTask)stack.getCurrentTask();
                if (!task.getTest().execute(stack)) {
                    return false;
                }
            }


            stack.setCurrentTask(stack.getCurrentTask().getNext());
            
        }
        addComplete(stack);
        return true;
    }
    

    /**
     * This method is called to handle the exception
     */
    protected boolean handleException(StackEntry stack) throws Exception {
        if (this.currentException == null) {
            return false;
        }
        if (!(stack.getBlock() instanceof Try)) {
            System.out.println("This is not a try block");
            addStackEvent(stack.getBlock(), ACTION_STATUS.ACTION_ERROR, currentException.getExceptionMessage());
            throw this.currentException;
        }
        addStackEvent(stack.getBlock(), ACTION_STATUS.ACTION_ERROR, currentException.getExceptionMessage());
        for (Catch catchBlock : ((Try) stack.getBlock()).getCatches()) {
            System.out.println("Loop through the catches [" +
                    catchBlock.getExceptionName() + "][" +
                    this.currentException.getExceptionType() + "]");
            if (catchBlock.getExceptionName().equals(this.currentException.getExceptionType())) {
                System.out.println("Handle the exception");
                currentException = null;
                createStack(stack, catchBlock);
                return true;
            }
        }
        System.out.println("Re-throw the exception from bottom");
        throw currentException;
    }

    
    /**
     * This method handles a result.
     *
     * @param stack The stack result.
     * @throws java.lang.Exception
     */
    protected boolean handleResult(StackEntry stack) throws Exception {
        if (this.currentResult == null) {
            log.info("No result to be handled");
            return true;
        }

        log.info("The stack variables : " + stack.getVariables().length);
        for (int index = 0; index < stack.getVariables().length; index++) {
            if (stack.getCurrentTask() instanceof Call && 
                    (((Call)stack.getCurrentTask()).getResult() != null)) {
                Call call = (Call)stack.getCurrentTask();
                if (stack.getVariables()[index].getDataName().equals(call.getResult())) {
                    log.info("Set the result based on a call [" + call.getResult() +
                            "]for a returned variable : " + this.currentResult.toString());
                    this.currentResult.setDataName(call.getResult());
                    stack.getVariables()[index] = this.currentResult;
                    this.currentResult = null;
                    return true;
                }
            } else {
                if (stack.getVariables()[index].getDataName().equals(this.currentResult.getDataName())) {
                    log.info("Set the result based on equal data names [" + this.currentResult.getDataName() +
                            "]for a returned variable : " + this.currentResult.toString());
                    stack.getVariables()[index] = this.currentResult;
                    this.currentResult = null;
                    return true;
                }
            }
        }

        if ((stack.getParent() != null) && handleResult(stack.getParent())) {
            return true;
        }
        log.info("Variables not found to set");
        return false;
    }

    
    /**
     * This method sets the
     * @param parent
     * @param task
     * @return
     * @throws java.lang.Exception
     */
    protected StackEntry createStack(StackEntry parent, ActionTaskDefinition task) throws Exception {
        addStackEvent(task, ACTION_STATUS.ACTION_RUNNING);
        StackEntry child = new StackEntry(new DataType[]{}, task, parent);
        parent.setChild(child);
        setScopeVariables(child, task);
        return child;
    }

    
    /**
     * This method returns true if this task is a block requiring a new scope.
     *
     * @param task The task to execute.
     * @return TRUE if this is a block, FALSE if not.
     */
    protected boolean isBlock(ActionTaskDefinition task) {
        if (task instanceof Block) {
            return true;
        } else if (task instanceof ConcurrentBlock) {
            return true;
        } else if (task instanceof Try) {
            return true;
        } else if (task instanceof Catch) {
            return true;
        }
        return false;
    }


    /**
     * This method is responsible for setting the scope of the variables.
     *
     * @param child The child stack.
     * @param task The task.
     * @throws java.lang.Exception
     */
    protected void setScopeVariables(StackEntry child, ActionTaskDefinition task) throws Exception {
        if (task instanceof Block) {
            child.setVariables(copyData(Block.class.cast(task).getParameters()));
        } else if (task instanceof ConcurrentBlock) {
            child.setVariables(copyData(ConcurrentBlock.class.cast(task).getParameters()));
        }
    }


    /**
     * This method is responsible for 
     * @param data The data to copy
     * @return The resultant data
     * @throws java.lang.Exception
     */
    protected DataType[] copyData(DataType[] data) throws Exception {
        if (data == null) {
            return null;
        }
        DataType[] result = new DataType[data.length];
        for (int index = 0; index < data.length; index++) {
            result[index] = (DataType) data[index].clone();
        }
        return result;
    }


    /**
     * This method returns the primary key attached to this object.
     *
     * @return The object that contains the primary key information.
     */
    public Object getPrimaryKey() {
        return action.getId();
    }


    /**
     * This method returns the name of this resource for grouping in the cache.
     *
     * @return The string containing the resource name.
     */
    public String getResourceName() {
        return this.getClass().getName();
    }


    /**
     * This method is responsible for releasing the resource information.
     */
    public void releaseResource() {
        
    }


    /**
     *
     * @param task The task that this stack event is tied to.
     * @param statusFlag The status of this event.
     * @throws Exception The exception.
     */
    protected void addStackEvent(ActionTaskDefinition task, ACTION_STATUS statusFlag)
        throws Exception {
        addStackEvent(task,statusFlag,null);
    }


    /**
     * This method is called to add a new stack event tied to a specific task.
     *
     * @param task The name of the task event.
     * @param status The status string for the task event.
     */
    protected void addStackEvent(ActionTaskDefinition task, ACTION_STATUS statusFlag, String message)
        throws Exception {
        String user = "Unknown";
        try {
            user = com.rift.coad.lib.security.SessionManager.getInstance().getSession().getUser().getName();
        } catch (Exception ex) {
            log.info("Failed to get the user information : " + ex.getMessage(),ex);
        }
        Date start = null;
        Date complete = null;
        String status = null;
        switch (statusFlag) {
            case ACTION_RUNNING:
                start = new Date();
                status = ActionConstants.RUNNING;
                break;
            case ACTION_ERROR:
                start = new Date();
                status = ActionConstants.ERROR;
                break;
            case ACTION_COMPLETE:
                complete = new Date();
                status = ActionConstants.COMPLETE;
                break;
            case ACTION_FINISHED:
                complete = new Date();
                status = ActionConstants.FINISHED;
                break;
            default:
                log.error("Unrecognised action :" + statusFlag.toString());
                throw new ActionException("Unrecognised action :" + statusFlag.toString());
        }

        StackEvent event = new StackEvent(start, complete, task, status, user);
        if (message != null) {
            event.setMessage(message);
        }
        action.addEvent(event);

    }


    /**
     * This method is called to add the stack entry
     *
     * @param stack The current stack value.
     */
    protected void addComplete(StackEntry stack) throws Exception {
        addStackEvent(stack.getBlock(), ACTION_STATUS.ACTION_COMPLETE);
        if (stack.getParent() == null) {
            addStackEvent(stack.getBlock(), ACTION_STATUS.ACTION_FINISHED);
        }
    }
}
