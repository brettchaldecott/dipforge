/*
 * MessageQueueClient: The message queue client library
 * Copyright (C) 2006  2015 Burntjam
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
 * RPCMessage.java
 */

package com.rift.coad.daemon.messageservice;

/**
 * The definition of the RPC message.
 *
 * @author Brett Chaldecott
 */
public interface RPCMessage extends Message {
    /**
     * This method sets the XML body for the message.
     *
     * @param xml The string containing the formatted xml for the request.
     * @exception MessageServiceException
     */
    public void setMethodBodyXML(String xml) throws MessageServiceException;
    
    
    /**
     * This method returns the XML body of the message.
     *
     * @return The string containing the formatted xml for the request.
     * @exception MessageServiceException
     */
    public String getMethodBodyXML() throws MessageServiceException;
    
    
    /**
     * This method sets the method information for 
     *
     * @param returnType The return type for this message.
     * @param name The name of this method.
     * @param types The types that are arguments to this method.
     * @exception MessageServiceException
     */
    public void defineMethod(Class returnType, String name, Class[] types) 
    throws MessageServiceException;
    
    
    /**
     * This method retrieves the return type of a method.
     *
     * @return The object containing the return type information.
     * @exception MessageServiceException
     */
    public Object getReturnType() throws MessageServiceException;
    
    
    /**
     * This method returns the name of the method being wrapped.
     *
     * @return The string containing the method name.
     * @exception MessageServiceException
     */
    public String getMethodName() throws MessageServiceException;
    
    
    /**
     * This method returns the argument types for this method.
     *
     * @return The list of arguments.
     * @exception MessageServiceException
     */
    public Class[] getArgumentTypes() throws MessageServiceException;
    
    
    /**
     * This method sets the arguments for this message.
     *
     * @param args The arguments to set.
     * @exception MessageServiceException
     */
    public void setArguments(Object[] args) throws MessageServiceException;
    
    
    /**
     * This method returns the arguments for a method.
     *
     * @return The list of arguments.
     * @exception MessageServiceException.
     */
    public Object[] getArguments() throws MessageServiceException;
    
    
    /**
     * This method returns true if an exception was thrown.
     *
     * @return TRUE if and exception was generated, FALSE if not.
     * @exception MessageServiceException
     */
    public boolean generatedException() throws MessageServiceException;
    
    
    /**
     * This method returns the result of the RPC call.
     *
     * @return The object returns as a result of the asynchronis call.
     * @exception MessageServiceException
     */
    public Object getResult() throws MessageServiceException;
    
    
    /**
     * This method is responsible for setting the result of the return.
     *
     * @param result The result of the message.
     * @exception MessageServiceException
     */
    public void setResult(Object result) throws MessageServiceException;
    
    
    /**
     * This method returns the exception that got thrown while processing this
     * RPC message.
     *
     * @return The exception that got thrown while processing this message.
     * @exception MessageServiceException
     */
    public Throwable getThrowable() throws MessageServiceException;
    
    
    /**
     * This method returns the exception that got thrown while processing this
     * RPC message.
     *
     * @param throwable The throwable exception to set.
     * @exception MessageServiceException
     */
    public void setThrowable(Throwable throwable) throws MessageServiceException;
}
