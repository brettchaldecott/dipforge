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
 * RPCMessageImpl.java
 */

// package path
package com.rift.coad.daemon.messageservice.message;

// java imports
import java.util.Date;
import java.util.List;

// coadunation imports
import com.rift.coad.lib.common.ObjectSerializer;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.RPCMessage;
import com.rift.coad.daemon.messageservice.message.rpc.RPCXMLParser;

/**
 * The implementation of the RPC message object.
 *
 * @author Brett Chaldecott
 */
public class RPCMessageImpl extends MessageImpl implements RPCMessage {
    
    // the classes private member variables
    private String xmlBody = null;
    private byte[] result = null;
    private byte[] exception = null;
    private RPCXMLParser parser = null;
    
    /**
     * Creates a new instance of RPCMessageImpl
     *
     * @param messageId The unique identifier for this message.
     * @param user The user of this message.
     * @param sessionId The id of this user session.
     * @param principals The list of principals assigned to this message.
     * @param status The status of this message.
     */
    public RPCMessageImpl(String messageId, String user, String sessionId, 
            List principals, int status) {
        super(messageId,user,sessionId,principals,status);
    }
    
    
    /**
     * Creates a new instance of RPCMessageImpl
     *
     * @param messageId The id of the message that was created.
     * @param create The created time stamp.
     * @param retries The number of retries of this message.
     * @param processedDate The last time this message was processed.
     * @param user The name of the user.
     * @param sessionId The id of this user session.
     * @param principals The list of principals.
     * @param from The from address of the message.
     * @param messageType The type of message being used.
     * @param status The status of this message.
     */
    public RPCMessageImpl(String messageId, Date created, int retries, 
            Date processedDate,String user, String sessionId, List principals, 
            String from, int messageType, int status) {
        super(messageId,created,retries,processedDate,user,sessionId,principals,
                from,messageType, status);
    }
    
    
    /**
     * This method clears the body of the rpc message.
     *
     * @exception MessageServiceException
     */
    public void clearBody() throws MessageServiceException {
        xmlBody = null;
    }
    
    
    /**
     * This method sets the XML body for the message.
     *
     * @param xml The string containing the formatted xml for the request.
     * @exception MessageServiceException
     */
    public void setMethodBodyXML(String xml) throws MessageServiceException {
        this.xmlBody = xml;
    }
    
    
    /**
     * This method returns the XML body of the message.
     *
     * @return The string containing the formatted xml for the request.
     * @exception MessageServiceException
     */
    public String getMethodBodyXML() throws MessageServiceException {
        return xmlBody;
    }
    
    
    /**
     * This method sets the method information for 
     *
     * @param returnType The return type for this message.
     * @param name The name of this method.
     * @param types The types that are arguments to this method.
     * @exception MessageServiceException
     */
    public void defineMethod(Class returnType, String name, Class[] types) 
    throws MessageServiceException {
        String returnTypeName = "void";
        if (returnType != null) {
            returnTypeName = returnType.getName();
        }
        
        String typesString = "";
        for (int index = 0; index < types.length; index++) {
            typesString += String.format(
                    "   <argument name=\"p%d\" type=\"%s\"/>\n",index + 1,
                    types[index].getName());
        }
        xmlBody = String.format(
                "<method name=\"%s\" type=\"%s\">\n" +
                "%s" +
                "</method>",name,returnTypeName,typesString);
    }
    
    
    /**
     * This method retrieves the return type of a method.
     *
     * @return The object containing the return type information.
     * @exception MessageServiceException
     */
    public Object getReturnType() throws MessageServiceException {
        parseXML();
        return parser.getReturnType();
    }
    
    
    /**
     * This method returns the name of the method being wrapped.
     *
     * @return The string containing the method name.
     * @exception MessageServiceException
     */
    public String getMethodName() throws MessageServiceException {
        parseXML();
        return parser.getMethodName();
    }
    
    
    /**
     * This method returns the argument types for this method.
     *
     * @return The list of arguments.
     * @exception MessageServiceException
     */
    public Class[] getArgumentTypes() throws MessageServiceException {
        parseXML();
        return parser.getArgumentTypes();
    }
    
    
    /**
     * This method sets the arguments for this message.
     *
     * @param args The arguments to set.
     * @exception MessageServiceException
     */
    public void setArguments(Object[] args) throws MessageServiceException {
        for (int index = 0; args != null && index < args.length; index++) {
            String name = "p" + (index + 1);
            this.setObjectProperty(name,args[index]);
        }
    }
    
    
    /**
     * This method returns the arguments for a method.
     *
     * @return The list of arguments.
     * @exception MessageServiceException.
     */
    public Object[] getArguments() throws MessageServiceException {
        parseXML();
        Object[] argumentTypes = parser.getArgumentTypes();
        Object[] args = new Object[argumentTypes.length];
        for (int index = 0; index < args.length; index++) {
            String name = "p" + (index + 1);
            args[index] = this.getObjectProperty(name);
        }
        return args;
    }
    
    
    /**
     * This method returns true if an exception was thrown.
     *
     * @return TRUE if and exception was generated, FALSE if not.
     * @exception MessageServiceException
     */
    public boolean generatedException() throws MessageServiceException {
        return (exception != null);
    }
    
    
    /**
     * This method returns the result of the RPC call.
     *
     * @return The object returns as a result of the asynchronis call.
     * @exception MessageServiceException
     */
    public Object getResult() throws MessageServiceException {
        if (result == null) {
            return null;
        }
        try {
            return ObjectSerializer.deserialize(result);
        } catch (Exception ex) {
            throw new MessageServiceException(
                    "Failed to deserialize the result [" + ex.getMessage() + 
                    "]");
        }
    }
    
    
    /**
     * This method returns the result of the RPC call.
     *
     * @return The object returns as a result of the asynchronis call.
     * @exception MessageServiceException
     */
    public byte[] getResultBytes() throws MessageServiceException {
        return result;
    }
    
    
    /**
     * This method is responsible for setting the result of the return.
     *
     * @param result The result of the message.
     * @exception MessageServiceException
     */
    public void setResult(Object result) throws MessageServiceException {
        try {
            if (result != null) {
                this.result = ObjectSerializer.serialize(result);
            } else {
                this.result = null;
            }
        } catch (Exception ex) {
            throw new MessageServiceException("Failed to set the result [" +
                    ex.getMessage() + "]");
        }
    }
    
    
    /**
     * This method is responsible for setting the result of the return.
     *
     * @param result The bytes containing the result
     * @exception MessageServiceException
     */
    public void setResultBytes(byte[] result) throws MessageServiceException {
        this.result = result;
    }
    
    
    /**
     * This method returns the exception that got thrown while processing this
     * RPC message.
     *
     * @return The exception that got thrown while processing this message.
     * @exception MessageServiceException
     */
    public Throwable getThrowable() throws MessageServiceException {
        if (exception == null) {
            return null;
        }
        try {
            return (Throwable)ObjectSerializer.deserialize(exception);
        } catch (Exception ex) {
            throw new MessageServiceException(
                    "Failed to deserialize the exception [" + ex.getMessage() + 
                    "]",ex);
        }
    }
    
    /**
     * This method returns the exception that got thrown while processing this
     * RPC message.
     *
     * @return The bytes representing the serialized exception
     * @exception MessageServiceException
     */
    public byte[] getThrowableBytes() throws MessageServiceException {
        return exception;
    }
    
    
    /**
     * This method returns the exception that got thrown while processing this
     * RPC message.
     *
     * @param throwable The throwable exception to set.
     * @exception MessageServiceException
     */
    public void setThrowable(Throwable throwable) throws 
            MessageServiceException {
        try {
            this.exception = ObjectSerializer.serialize(throwable);
        } catch (Exception ex) {
            throw new MessageServiceException("Failed to set the exception [" +
                    ex.getMessage() + "]");
        }
    }
    
    
    /**
     * This method sets the throwable bytes of the exception.
     *
     * @param exception The exception bytes.
     * @exception MessageServiceException
     */
    public void setThrowableBytes(byte[] exception) throws 
            MessageServiceException {
        this.exception = exception;
    }
    
    
    /**
     * This method is responsible for parsing XML.
     *
     * @exception MessageServiceException
     */
    private void parseXML() throws MessageServiceException {
        if (parser != null) {
            return;
        }
        if (xmlBody == null) {
            throw new MessageServiceException(
                    "The xml body has not been setup.");
        }
        try {
            parser = new RPCXMLParser(xmlBody);
        } catch (Exception ex) {
            throw new MessageServiceException("Failed parse the xml : " + 
                    ex.getMessage(),ex);
        }
    }
}
