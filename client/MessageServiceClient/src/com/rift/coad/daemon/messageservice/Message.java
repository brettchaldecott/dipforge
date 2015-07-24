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
 * Message.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * The definition of the message object.
 *
 * @author Brett Chaldecott
 */
public interface Message extends Serializable {
    
    /**
     * A message will get delivered to a point defined by a JNDI URL.
     */
    public final static int POINT_TO_POINT = 1;
    
    /**
     * A message will get delivered to the first available service with a
     * matching service name.
     */
    public final static int POINT_TO_SERVICE = 2;
    
    /**
     * A message will get delivered to all services within a coadunation cluster
     * that match the service name.
     */
    public final static int POINT_TO_MULTI_SERVICE = 3;
    
    /**
     * This state flag indicates that a message is undelivered.
     */
    public final static int UNDELIVERED = 1;
    
    /**
     * The message has been delivered to its destination.
     */
    public final static int DELIVERED = 2;
    
    /**
     * The message could not be delivered to its destination.
     */
    public final static int UNDELIVERABLE = 3;
    
    
    /**
     * A warning
     */
    public final static int WARN = 1;
    
    
    /**
     * An error.
     */
    public final static int ERROR = 2;
    
    
    /**
     * A critical error.
     */
    public final static int CRITICAL = 3;
    /**
     * This method returns the id of this message object.
     *
     * @return The id of the message object.
     */
    public String getMessageId();
    
    
    /**
     * This method returns the date this message was created.
     *
     * @return The date message was created.
     */
    public Date getCreated();
    
    
    /**
     * This is the number of retries this message has had.
     *
     * @return The number of retries performed by this message.
     */
    public int getRetries();
    
    
    /**
     * This method increments the retry count on this object.
     */
    public void incrementRetries();
    
    
    /**
     * This method will return the last processed date.
     */
    public Date getProcessedDate();
    
    
    /**
     * This method sets the processed date.
     *
     * @param processedDate The processed date of the message.
     */
    public void setProcessedDate(Date processedDate);
    
    
    /**
     * This method returns the creator of this message.
     */
    public String getMessageCreater();
    
    
    /**
     * This method returns the ID of the session that created this message.
     *
     * @return The string containing the id of the session that created this
     *      message.
     */
    public String getSessionId();
    
    
    /**
     * This method returns the list of user principals assigned to this message.
     *
     * @return The list of user principals assigned to this message.
     */
    public List getMessagePrincipals();
    
    
    /**
     * This method returns the type of message that this object represents.
     *
     * @return The type of object being wrapped.
     */
    public int getMessageType();
    
    
    /**
     * This method sets the type of message that this object represents.
     *
     * @param messageType The type of object being wrapped.
     */
    public void setMessageType(int messageType);
    
    /**
     * This method will return the URL string for this message if this is a
     * point to point message.
     *
     * @return The string containing the Target URL.
     * @exception MessageServiceException
     * @exception InvalidMessageType
     */
    public String getTarget() throws MessageServiceException, 
            InvalidMessageType;
    
    
    /**
     * This method sets the target of the message.
     *
     * @param target The string containing the Target URL.
     * @exception MessageServiceException
     * @exception InvalidMessageType
     */
    public void setTarget(String  target) throws MessageServiceException, 
            InvalidMessageType;
    
    
    /**
     * This method returns the list of services.
     *
     * @return The string array list of services.
     * @exception MessageServiceException
     * @exception InvalidMessageType
     */
    public String[] getServices() throws MessageServiceException, 
            InvalidMessageType;
    
    
    /**
     * This method returns the list of services this message targeted at.
     *
     * @param services The list of services.
     * @exception MessageServiceException
     * @exception InvalidMessageType
     */
    public void setServices(String[] services) throws MessageServiceException,
            InvalidMessageType;
    
    
    /**
     * This method returns the from URL of a message.
     *
     * @return The string containing the from URL.
     * @exception MessageServiceException
     */
    public String getFrom() throws MessageServiceException;
    
    
    /**
     * This method returns the from address of the message.
     *
     * @return The from address of the message.
     * @exception MessageServiceException
     */
    public void setFrom(String from) throws MessageServiceException;
    
    
    /**
     * This method returns the reply to URL, it can be different from the from
     * URL.
     *
     * @return The string containing the from URL.
     * @exception MessageServiceException
     */
    public String getReplyTo() throws MessageServiceException;
    
    
    /**
     * This method returns the reply to URL, it can be different from the from
     * URL.
     *
     * @return The string containing the from URL.
     * @exception MessageServiceException
     */
    public void setReplyTo(String replyTo) throws MessageServiceException;
    
    
    /**
     * This method gets the target named queue.
     *
     * @return The string containing the Queue name.
     * @exception MessageServiceException
     */
    public String getTargetNamedQueue() throws MessageServiceException;
    
    
    /**
     * This method sets the target named queue.
     *
     * @param name The string containing the Queue name.
     * @exception MessageServiceException
     */
    public void setTargetNamedQueue(String name) throws MessageServiceException;
    
    
    /**
     * This will only be set if the reply service is not a daemon, but an
     * external process.
     *
     * @return The string containing the Queue name.
     * @exception MessageServiceException
     */
    public String getReplyNamedQueue() throws MessageServiceException;
    
    
    /**
     * This will only be set if the reply service is not a daemon, but an
     * external process.
     *
     * @param name The string containing the Queue name.
     * @exception MessageServiceException
     */
    public void setReplyNamedQueue(String name) throws MessageServiceException;
    
    
    /**
     * This method returns the value of the reply flag. TRUE if it should reply
     * FALSE if it should not.
     *
     * @return TRUE if this message must reply, FALSE if not.
     * @exception MessageServiceException
     */
    public boolean getReply() throws MessageServiceException;
    
    
    /**
     * This method sets the reply flag.
     *
     * @param value TRUE if a reply is required, FALSE if not.
     * @exception MessageServiceException
     */
    public void setReply(boolean value) throws MessageServiceException;
    
    
    /**
     * This message returns the priority of this message.
     *
     * @return The int indicating the priority of this message.
     * @exception MessageServiceException
     */
    public int getPriority();
    
    
    /**
     * This method sets the priority of the message.
     *
     * @param priority The priority of the message.
     * @exception MessageServiceException
     */
    public void setPriority(int priority) throws MessageServiceException;
    
    
    /**
     * This method sets the correlation id for this message. It is the external
     * identifier for this message.
     *
     * @param id The id that will be used as the correlation id.
     * @exception MessageServiceException
     */
    public void setCorrelationId(String id) throws MessageServiceException;
    
    
    /**
     * The external correlation id for this message.
     *
     * @return The string containing the correllation ID.
     * @exception MessageServiceException
     */
    public String getCorrelationId() throws MessageServiceException;
    
    
    /**
     * This clears the body of the message.
     *
     * @exception MessageServiceException
     */
    public void clearBody() throws MessageServiceException;
    
    
    /**
     * This method clears the properties assigned to this message.
     *
     * @exception MessageServiceException
     */
    public void clearProperties() throws MessageServiceException;
    
    
    /**
     * This method returns true if the property is found.
     *
     * @return TRUE if the property is found, FALSE if not.
     * @param name The name of the property.
     * @exception MessageServiceException
     */
    public boolean containsProperty(String name) throws MessageServiceException;
    
    
    /**
     * This method returns the boolean property value for the requested name.
     *
     * @return The value of the boolean property.
     * @param name The name of the property.
     * @exception MessageServiceException
     * @exception InvalidProperty
     */
    public boolean getBooleanProperty(String name) throws 
            MessageServiceException, InvalidProperty;
    
    
    /**
     * This method returns the byte property value for the requested name.
     *
     * @return The value of the byte property.
     * @param name The name of the property.
     * @exception MessageServiceException
     * @exception InvalidProperty
     */
    public byte getByteProperty(String name) throws 
            MessageServiceException, InvalidProperty;
    
    
    /**
     * This method returns the double property value for the requested name.
     *
     * @return The value of the byte property.
     * @param name The name of the property.
     * @exception MessageServiceException
     * @exception InvalidProperty
     */
    public double getDoubleProperty(String name) throws 
            MessageServiceException, InvalidProperty;
    
    
    /**
     * This method returns the float property value for the requested name.
     *
     * @return The value of the float property.
     * @param name The name of the property.
     * @exception MessageServiceException
     * @exception InvalidProperty
     */
    public float getFloatProperty(String name) throws 
            MessageServiceException, InvalidProperty;
    
    
    /**
     * This method returns the int property value for the requested name.
     *
     * @return The value of the int property.
     * @param name The name of the property.
     * @exception MessageServiceException
     * @exception InvalidProperty
     */
    public int getIntProperty(String name) throws 
            MessageServiceException, InvalidProperty;
    
    
    /**
     * This method returns the long property value for the requested name.
     *
     * @return The value of the long property.
     * @param name The name of the property.
     * @exception MessageServiceException
     * @exception InvalidProperty
     */
    public long getLongProperty(String name) throws 
            MessageServiceException, InvalidProperty;
    
    
    /**
     * This method returns the object property value for the requested name.
     *
     * @return The value of the object property.
     * @param name The name of the property.
     * @exception MessageServiceException
     * @exception InvalidProperty
     */
    public Object getObjectProperty(String name) throws 
            MessageServiceException, InvalidProperty;
    
    
    /**
     * This method returns the string property value for the requested name.
     *
     * @return The value of the string property.
     * @param name The name of the property.
     * @exception MessageServiceException
     * @exception InvalidProperty
     */
    public String getStringProperty(String name) throws 
            MessageServiceException, InvalidProperty;
    
    
    /**
     * This method returns the value of the property.
     *
     * @return The value of the property.
     * @param name The name of the property.
     * @exception MessageServiceException
     * @exception InvalidProperty
     */
    public Object getPropertyValue(String name) throws 
            MessageServiceException, InvalidProperty;
    
    
    /**
     * This method returns the string property value for the requested name.
     *
     * @return The list of property names
     * @exception MessageServiceException
     */
    public Enumeration getPropertyNames() throws MessageServiceException;
    
    
    /**
     * This method returns true if the specified property exits.
     *
     * @return TRUE if the property exists, FALSE if not.
     * @param name The name of the property to look for.
     * @exception MessageServiceException
     */
    public boolean propertyExists(String name) throws MessageServiceException;
    
    
    /**
     * This method sets the boolean property value for the name.
     *
     * @param name The name of the property.
     * @param value The value of the boolean property.
     * @exception MessageServiceException
     */
    public void setBooleanProperty(String name, boolean value) throws 
            MessageServiceException;
    
    
    /**
     * This method sets the byte property value for the name.
     *
     * @param name The name of the property.
     * @param value The value of the byte property.
     * @exception MessageServiceException
     */
    public void setByteProperty(String name, byte value) throws 
            MessageServiceException;
    
    
    /**
     * This method sets the double property value for the name.
     *
     * @param name The name of the property.
     * @param value The value of the double property.
     * @exception MessageServiceException
     */
    public void setDoubleProperty(String name, double value) throws 
            MessageServiceException;
    
    
    /**
     * This method sets the float property value for the name.
     *
     * @param name The name of the property.
     * @param value The value of the float property.
     * @exception MessageServiceException
     */
    public void setFloatProperty(String name, float value) throws 
            MessageServiceException;
    
    
    /**
     * This method set the int property value for the name.
     *
     * @param name The name of the property.
     * @param value The value of the int property.
     * @exception MessageServiceException
     */
    public void setIntProperty(String name, int value) throws 
            MessageServiceException;
    
    
    /**
     * This method sets the long property value for the name.
     *
     * @param name The name of the property.
     * @return value The new long value.
     * @exception MessageServiceException
     */
    public void setLongProperty(String name, long value) throws 
            MessageServiceException;
    
    
    /**
     * This method returns the object property value for the name.
     *
     * @param name The name of the property.
     * @param value The new object value to set.
     * @exception MessageServiceException
     * @exception InvalidProperty
     */
    public void setObjectProperty(String name, Object value) throws 
            MessageServiceException;
    
    
    /**
     * This method sets the string property value for the name.
     *
     * @param name The name of the property.
     * @return value The new string value to set.
     * @exception MessageServiceException
     */
    public void setStringProperty(String name, String value) throws 
            MessageServiceException;
    
    
    /**
     * This method sets the value of the property.
     *
     * @param name The name of the property.
     * @param value The property value.
     * @exception MessageServiceException
     * @exception InvalidProperty
     */
    public void setPropertyValue(String name,Object value) throws 
            MessageServiceException, InvalidProperty;
    
    
    /**
     * This method acknowledges that this message has been successfully
     * processed by a target.
     *
     * @exception MessageServiceException
     */
    public void acknowledge() throws MessageServiceException;
    
    
    /**
     * This method returns the value of the acknowledged flag for this message.
     *
     * @return TRUE if acknowleded, FALSE if not.
     * @exception MessageServiceException
     */
    public boolean isAcknowledged() throws MessageServiceException;
    
    
    /**
     * This method returns the current state of this message.
     *
     * @return The current state of this message.
     * @exception MessageServiceException
     */
    public int getState() throws MessageServiceException;
    
    
    /**
     * This method returns list of errors
     */
    public List getErrors() throws MessageServiceException;
    
    
    /**
     * This method adds an error to the list of errors for this message.
     *
     * @param level The level of the error.
     * @param msg The message associated with the error.
     */
    public void addError(int level, String msg) throws MessageServiceException;
}
