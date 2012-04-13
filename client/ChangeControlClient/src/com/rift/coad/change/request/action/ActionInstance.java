/*
 * ChangeControlClient: The client library for the change control client.
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
 * ActionInstance.java
 */

package com.rift.coad.change.request.action;

// java import
import com.rift.coad.change.request.RequestEvent;
import com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager;
import java.rmi.Remote;
import java.rmi.RemoteException;


// coadunation imports

/**
 * This interfaces supplies access to a running action definition.
 *
 * @author brett chaldecott
 */
public interface ActionInstance extends Remote {

    /**
     * This method returns the id ofthe action instance.
     *
     * @return The string containing the id of this action instance which should match the request id.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public String getId() throws ActionException, RemoteException;


    /**
     * This metbhod returns the request id attached to this action instance.
     *
     * @return The string containing the request id attached to this object.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public String getRequestId() throws ActionException, RemoteException;


    /**
     * This method returns the master request id.
     *
     * @return The string containing the master request id.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public String getMasterRequestId() throws ActionException, RemoteException;


    /**
     * This method returns the action
     *
     * @return The string containing the action.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public String getAction() throws ActionException, RemoteException;


    /**
     * This method returns the data type that this action is being performed on.
     *
     * @return This method returns the id of the data type.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public String getDataType() throws ActionException, RemoteException;


    /**
     * This method returns the status of this action instance.
     *
     * @return The status of this action instance.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public String getStatus() throws ActionException, RemoteException;


    /**
     * This method returns the last event that occurred on this intance.
     *
     * @return The last action event.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public RequestEvent getLastEvent() throws ActionException, RemoteException;

    
    /**
     * This method returns the information about the status of this action stack.
     *
     * @return The action stack information.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public ProcessorMemoryManager getStack() throws ActionException, RemoteException;


    /**
     * This method sets the action stack information.
     *
     * @param stack The stack containing the status information.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public void setStack(ProcessorMemoryManager stack) throws ActionException, RemoteException;


    /**
     * This method is responsible for executing the action
     *
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public void execute() throws ActionException, RemoteException;


    /**
     * This method executes the action instance using the result.
     *
     * @param result The result for the execution.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public void execute(String result) throws ActionException, RemoteException;


    /**
     * This method executes the action instance.
     *
     * @param ex The exeception.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public void execute(Exception ex) throws ActionException, RemoteException;
}
