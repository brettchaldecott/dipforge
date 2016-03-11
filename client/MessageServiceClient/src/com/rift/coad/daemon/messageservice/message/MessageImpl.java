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
 * MessageImpl.java
 */

// package path
package com.rift.coad.daemon.messageservice.message;

// java imports
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

// coadunation imports
import com.rift.coad.daemon.messageservice.InvalidMessageType;
import com.rift.coad.daemon.messageservice.InvalidProperty;
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageError;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.lib.common.ObjectSerializer;

/**
 * The implementation of the message object.
 *
 * @author Brett Chaldecott
 */
public abstract class MessageImpl implements Message, Cloneable {
    
    // private member variables
    private String messageId = null;
    private Date created = null;
    private int retries = 0;
    private Date processedDate = null;
    private Date nextProcessDate = null;
    private String sessionId = null;
    private String user = null;
    private List principals = null;
    private String from = null;
    private int messageType = 0;
    private String target = null;
    private String[] services = null;
    private String replyTo = null;
    private String targetNamedQueue = null;
    private String replyNamedQueue = null;
    private boolean reply = false;
    private int priority = 0;
    private String correlationId = null;
    private Properties properties = new Properties();
    private boolean acknowledged = false;
    private int status = 0;
    private List errors = new ArrayList();
    
    /**
     * Creates a new instance of MessageImpl.
     *
     * @param messageId The unique id of this message.
     * @param user The name of the user.
     * @param sessionId The id of the session.
     * @param principals The list of principals.
     * @param status The status of the message.
     */
    public MessageImpl(String messageId, String user, String sessionId, 
            List principals, int status) {
        this.messageId = messageId;
        this.created = new Date();
        this.processedDate = new Date();
        this.user = user;
        this.sessionId = sessionId;
        this.principals = principals;
        this.status = status;
    }
    
    
    /**
     * Creates a new instance of MessageImpl.
     *
     * @param messageId The unique id of this message.
     * @param created The date the message was created.
     * @param retries The number of retries of this message.
     * @param processedDate The processed date.
     * @param user The user responsible for this message.
     * @param sessionId The session id
     * @param principals The list of principals.
     * @param from The from url of the message.
     * @param messageType The message type.
     * @param status The status of the message.
     */
    public MessageImpl(String messageId, Date created, int retries,
            Date processedDate,String user,String sessionId, List principals,
            String from, int messageType, int status) {
        this.messageId = messageId;
        this.created = created;
        this.retries = retries;
        this.processedDate = processedDate;
        this.user = user;
        this.sessionId = sessionId;
        this.principals = principals;
        this.from = from;
        this.messageType = messageType;
        this.status = status;
    }
    
    
    /**
     * This method returns the id of this message object.
     *
     * @return The id of the message object.
     */
    public String getMessageId() {
        return messageId;
    }
    
    /**
     * This method sets the new id for this message
     *
     * @param messageId The new id for this message.
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    
    /**
     * This method returns the date this message was created.
     *
     * @return The date the message was created
     */
    public Date getCreated() {
        return created;
    }
    
    
    /**
     * This is the number of retries this message has had.
     *
     * @return The number of retries performed by this message.
     */
    public int getRetries() {
        return retries;
    }
    
    
    /**
     * This method increments the retry count on this object.
     */
    public void incrementRetries() {
        retries++;
    }
    
    
    /**
     * This method will return the last processed date.
     */
    public Date getProcessedDate() {
        return processedDate;
    }
    
    
    /**
     * This method sets the processed date.
     *
     * @param processedDate The processed date of the message.
     */
    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }
    
    
    /**
     * This method returns the next process date of the object.
     */
    public Date getNextProcessDate() {
        return this.nextProcessDate;
    }
    
    
    /**
     * This method returns the next process date of the object.
     *
     * @param nextProcessDate The next processed date of the message.
     */
    public void setNextProcessDate(Date nextProcessDate) {
        this.nextProcessDate = nextProcessDate;
    }
    
    
    /**
     * This method returns the creator of this message.
     */
    public String getMessageCreater() {
        return user;
    }
    
    
    /**
     * This method returns the ID of the session that created this message.
     *
     * @return The string containing the id of the session that created this
     *      message.
     */
    public String getSessionId() {
        return sessionId;
    }
    
    
    /**
     * This method returns the list of user principals assigned to this message.
     *
     * @return The list of user principals assigned to this message.
     */
    public List getMessagePrincipals() {
        return principals;
    }
    
    
    /**
     * This method sets the list of user principals assigned to this message.
     *
     * @param principals The new list of principals
     */
    public void setMessagePrincipals(List principals) {
        this.principals = principals;
    }
    
    
    /**
     * This method returns the message type for this object.
     *
     * @return The int containing the message type.
     */
    public int getMessageType() {
        return messageType;
    }
    
    
    /**
     * This method sets the type of message that this object represents.
     *
     * @param messageType The type of object being wrapped.
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
    
    /**
     * This method returns the target of this message.
     *
     * @return The string containing the target information.
     * @throws MessageServiceException
     * @throws InvalidMessageType
     */
    public String getTarget() throws MessageServiceException,
            InvalidMessageType {
        return target;
    }
    
    /**
     * This method returns the target of this message.
     *
     * @param target The string containing the Target URL.
     * @throws MessageServiceException
     * @throws InvalidMessageType
     */
    public void setTarget(String target) throws MessageServiceException,
            InvalidMessageType {
        this.target = target;
    }
    
    
    /**
     * This method returns the list of services this message targeted at.
     *
     * @return This method returns the list of services.
     * @throws MessageServiceException
     * @throws InvalidMessageType
     */
    public String[] getServices() throws MessageServiceException,
            InvalidMessageType {
        return services;
    }
    
    
    /**
     * This method returns the list of services this message targeted at.
     *
     * @param services The list of services.
     * @throws MessageServiceException
     * @throws InvalidMessageType
     */
    public void setServices(String[] services) throws MessageServiceException,
            InvalidMessageType {
        this.services = services;
    }
    
    
    /**
     * This method returns the from address of the message.
     *
     * @return The from address of the message.
     * @throws MessageServiceException
     */
    public String getFrom() throws MessageServiceException {
        return from;
    }
    
    
    /**
     * This method returns the from address of the message.
     *
     * @throws MessageServiceException
     */
    public void setFrom(String from) throws MessageServiceException {
        this.from = from;
    }
    
    
    /**
     * This method returns the reply to URL, it can be different from the from
     * URL.
     *
     * @return The string containing the from URL.
     * @throws MessageServiceException
     */
    public String getReplyTo() throws MessageServiceException {
        return replyTo;
    }
    
    
    /**
     * This method returns the reply to URL, it can be different from the from
     * URL.
     *
     * @throws MessageServiceException
     */
    public void setReplyTo(String replyTo) throws MessageServiceException {
        this.replyTo = replyTo;
    }
    
    
    /**
     * This will only be set if the reply service is not a daemon, but an
     * external process.
     *
     * @return The string containing the Queue name.
     * @throws MessageServiceException
     */
    public String getTargetNamedQueue() throws MessageServiceException {
        return targetNamedQueue;
    }
    
    
    /**
     * This will only be set if the reply service is not a daemon, but an
     * external process.
     *
     * @param name The string containing the Queue name.
     * @throws MessageServiceException
     */
    public void setTargetNamedQueue(String name) throws MessageServiceException {
        this.targetNamedQueue = name;
    }
    
    
    /**
     * This will only be set if the reply service is not a daemon, but an
     * external process.
     *
     * @return The string containing the Queue name.
     * @throws MessageServiceException
     */
    public String getReplyNamedQueue() throws MessageServiceException {
        return replyNamedQueue;
    }
    
    
    /**
     * This will only be set if the reply service is not a daemon, but an
     * external process.
     *
     * @param name The string containing the Queue name.
     * @throws MessageServiceException
     */
    public void setReplyNamedQueue(String name) throws MessageServiceException {
        this.replyNamedQueue = name;
    }
    
    
    /**
     * This method returns the value of the reply flag. TRUE if it should reply
     * FALSE if it should not.
     *
     * @return TRUE if this message must reply, FALSE if not.
     * @throws MessageServiceException
     */
    public boolean getReply() throws MessageServiceException {
        return reply;
    }
    
    
    /**
     * This method sets the reply flag.
     *
     * @param value TRUE if a reply is required, FALSE if not.
     * @throws MessageServiceException
     */
    public void setReply(boolean value) throws MessageServiceException {
        this.reply = value;
    }
    
    
    /**
     * This message returns the priority of this message.
     *
     * @return The int indicating the priority of this message.
     */
    public int getPriority() {
        return priority;
    }
    
    
    /**
     * This method sets the priority of the message.
     *
     * @param priority The priority of the message.
     * @throws MessageServiceException
     */
    public void setPriority(int priority) throws MessageServiceException {
        this.priority = priority;
    }
    
    
    /**
     * This method sets the correlation id for this message. It is the external
     * identifier for this message.
     *
     * @param id The id that will be used as the correlation id.
     * @throws MessageServiceException
     */
    public void setCorrelationId(String id) throws MessageServiceException {
        this.correlationId = id;
    }
    
    
    /**
     * The external correlation id for this message.
     *
     * @return The string containing the correllation ID.
     * @throws MessageServiceException
     */
    public String getCorrelationId() throws MessageServiceException {
        return correlationId;
    }
    
    
    /**
     * This clears the body of the message.
     *
     * @throws MessageServiceException
     */
    public abstract void clearBody() throws MessageServiceException;
    
    
    /**
     * This method clears the properties assigned to this message.
     *
     * @throws MessageServiceException
     */
    public void clearProperties() throws MessageServiceException {
        properties.clear();
    }
    
    
    /**
     * This method returns true if the property is found.
     *
     * @return TRUE if the property is found, FALSE if not.
     * @param name The name of the property.
     * @throws MessageServiceException
     */
    public boolean containsProperty(String name) throws MessageServiceException {
        return properties.containsKey(name);
    }
    
    
    /**
     * This method returns the boolean property value for the requested name.
     *
     * @return The value of the boolean property.
     * @param name The name of the property.
     * @throws MessageServiceException
     * @throws InvalidProperty
     */
    public boolean getBooleanProperty(String name) throws 
            MessageServiceException, InvalidProperty {
        if (!properties.containsKey(name)) {
            throw new InvalidProperty("The property [" + name + "] could not" +
                    "be found.");
        }
        Boolean bool = (Boolean)properties.get(name);
        return bool.booleanValue();
    }
    
    
    /**
     * This method returns the byte property value for the requested name.
     *
     * @return The value of the byte property.
     * @param name The name of the property.
     * @throws MessageServiceException
     * @throws InvalidProperty
     */
    public byte getByteProperty(String name) throws 
            MessageServiceException, InvalidProperty {
        if (!properties.containsKey(name)) {
            throw new InvalidProperty("The property [" + name + "] could not" +
                    "be found.");
        }
        Byte byteValue = (Byte)properties.get(name);
        return byteValue.byteValue();
    }
    
    
    /**
     * This method returns the double property value for the requested name.
     *
     * @return The value of the byte property.
     * @param name The name of the property.
     * @throws MessageServiceException
     * @throws InvalidProperty
     */
    public double getDoubleProperty(String name) throws 
            MessageServiceException, InvalidProperty {
        if (!properties.containsKey(name)) {
            throw new InvalidProperty("The property [" + name + "] could not" +
                    "be found.");
        }
        Double doubleValue = (Double)properties.get(name);
        return doubleValue.doubleValue();
    }
    
    
    /**
     * This method returns the float property value for the requested name.
     *
     * @return The value of the float property.
     * @param name The name of the property.
     * @throws MessageServiceException
     * @throws InvalidProperty
     */
    public float getFloatProperty(String name) throws 
            MessageServiceException, InvalidProperty {
        if (!properties.containsKey(name)) {
            throw new InvalidProperty("The property [" + name + "] could not" +
                    "be found.");
        }
        Float floatValue = (Float)properties.get(name);
        return floatValue.floatValue();
    }
    
    
    /**
     * This method returns the int property value for the requested name.
     *
     * @return The value of the int property.
     * @param name The name of the property.
     * @throws MessageServiceException
     * @throws InvalidProperty
     */
    public int getIntProperty(String name) throws 
            MessageServiceException, InvalidProperty {
        if (!properties.containsKey(name)) {
            throw new InvalidProperty("The property [" + name + "] could not" +
                    "be found.");
        }
        Integer intValue = (Integer)properties.get(name);
        return intValue.intValue();
    }
    
    
    /**
     * This method returns the long property value for the requested name.
     *
     * @return The value of the long property.
     * @param name The name of the property.
     * @throws MessageServiceException
     * @throws InvalidProperty
     */
    public long getLongProperty(String name) throws 
            MessageServiceException, InvalidProperty {
        if (!properties.containsKey(name)) {
            throw new InvalidProperty("The property [" + name + "] could not" +
                    "be found.");
        }
        Long longValue = (Long)properties.get(name);
        return longValue.longValue();
    }
    
    
    /**
     * This method returns the object property value for the requested name.
     *
     * @return The value of the object property.
     * @param name The name of the property.
     * @throws MessageServiceException
     * @throws InvalidProperty
     */
    public Object getObjectProperty(String name) throws 
            MessageServiceException, InvalidProperty {
        if (!properties.containsKey(name)) {
            throw new InvalidProperty("The property [" + name + "] could not" +
                    "be found.");
        }
        try {
            return ObjectSerializer.deserialize((byte[])properties.get(name));
        } catch (Exception ex) {
            throw new MessageServiceException("Failed to retrieve the object [" 
                    + name + "] value :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the string property value for the requested name.
     *
     * @return The value of the string property.
     * @param name The name of the property.
     * @throws MessageServiceException
     * @throws InvalidProperty
     */
    public String getStringProperty(String name) throws 
            MessageServiceException, InvalidProperty {
        if (!properties.containsKey(name)) {
            throw new InvalidProperty("The property [" + name + "] could not" +
                    "be found.");
        }
        return (String)properties.get(name);
    }
    
    
    /**
     * This method returns the value of the property.
     *
     * @return The value of the property.
     * @param name The name of the property.
     * @throws MessageServiceException
     * @throws InvalidProperty
     */
    public Object getPropertyValue(String name) throws 
            MessageServiceException, InvalidProperty {
        if (!properties.containsKey(name)) {
            throw new InvalidProperty("The property [" + name + "] could not" +
                    "be found.");
        }
        return properties.get(name);
    }
    
    
    /**
     * This method returns the string property value for the requested name.
     *
     * @return The list of property names
     * @throws MessageServiceException
     */
    public Enumeration getPropertyNames() throws MessageServiceException {
        return properties.propertyNames();
    }
    
    
    /**
     * This method returns true if the specified property exits.
     *
     * @return TRUE if the property exists, FALSE if not.
     * @param name The name of the property to look for.
     * @throws MessageServiceException
     */
    public boolean propertyExists(String name) throws MessageServiceException {
        return properties.containsKey(name);
    }
    
    
    /**
     * This method sets the boolean property value for the name.
     *
     * @param name The name of the property.
     * @param value The value of the boolean property.
     * @throws MessageServiceException
     */
    public void setBooleanProperty(String name, boolean value) throws 
            MessageServiceException {
        properties.put(name, new Boolean(value));
    }
    
    
    /**
     * This method sets the byte property value for the name.
     *
     * @param name The name of the property.
     * @param value The value of the byte property.
     * @throws MessageServiceException
     */
    public void setByteProperty(String name, byte value) throws 
            MessageServiceException {
        properties.put(name, new Byte(value));
    }
    
    
    /**
     * This method sets the double property value for the name.
     *
     * @param name The name of the property.
     * @param value The value of the double property.
     * @throws MessageServiceException
     */
    public void setDoubleProperty(String name, double value) throws 
            MessageServiceException{
        properties.put(name, new Double(value));
    }
    
    
    /**
     * This method sets the float property value for the name.
     *
     * @param name The name of the property.
     * @param value The value of the float property.
     * @throws MessageServiceException
     */
    public void setFloatProperty(String name, float value) throws 
            MessageServiceException {
        properties.put(name, new Float(value));
    }
    
    
    /**
     * This method set the int property value for the name.
     *
     * @param name The name of the property.
     * @param value The value of the int property.
     * @throws MessageServiceException
     */
    public void setIntProperty(String name, int value) throws 
            MessageServiceException {
        properties.put(name, new Integer(value));
    }
    
    
    /**
     * This method sets the long property value for the name.
     *
     * @param name The name of the property.
     * @throws MessageServiceException
     */
    public void setLongProperty(String name, long value) throws 
            MessageServiceException {
        properties.put(name, new Long(value));
    }
    
    
    /**
     * This method returns the object property value for the name.
     *
     * @param name The name of the property.
     * @param value The new object value to set.
     * @throws MessageServiceException
     * @throws InvalidProperty
     */
    public void setObjectProperty(String name, Object value) throws 
            MessageServiceException {
        try {
            properties.put(name, ObjectSerializer.serialize(value));
        } catch (Exception ex) {
            throw new MessageServiceException("Failed to store the object value :" +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method sets the string property value for the name.
     *
     * @param name The name of the property.
     * @throws MessageServiceException
     */
    public void setStringProperty(String name, String value) throws 
            MessageServiceException {
        properties.put(name, value);
    }
    
    
    /**
     * This method sets the value of the property.
     *
     * @param name The name of the property.
     * @param value The property value.
     * @throws MessageServiceException
     * @throws InvalidProperty
     */
    public void setPropertyValue(String name,Object value) throws 
            MessageServiceException, InvalidProperty {
        properties.put(name, value);
    }
    
    /**
     * This method acknowledges that this message has been successfully
     * processed by a target.
     *
     * @throws MessageServiceException
     */
    public void acknowledge() throws MessageServiceException {
        acknowledged = true;
    }
    
    
    /**
     * This method returns the value of the acknowledged flag for this message.
     *
     * @return TRUE if acknowleded, FALSE if not.
     * @throws MessageServiceException
     */
    public boolean isAcknowledged() throws MessageServiceException {
        return acknowledged;
    }
    
    
    /**
     * This method returns the current state of this message.
     *
     * @return The current state of this message.
     * @throws MessageServiceException
     */
    public int getState() throws MessageServiceException {
        return status;
    }
    
    
    /**
     * This method sets the current state of this message.
     *
     * @param status The new status of this message.
     * @throws MessageServiceException
     */
    public void setState(int status) throws MessageServiceException {
        this.status = status;
    }
    
    
    /**
     * This method returns list of errors
     */
    public List getErrors() throws MessageServiceException {
        return errors;
    }
    
    
    /**
     * This method adds an error to the list of errors for this message.
     *
     * @param level The level of the error.
     * @param msg The message associated with the error.
     */
    public void addError(int level, String msg) throws MessageServiceException {
        errors.add(new MessageError(new Date(),level,msg));
    }
    
    
    /**
     * This method adds the error to the list of errors.
     *
     * @param error The error to add to the list.
     */
    public void addError(MessageError error) {
        errors.add(error);
    }
    
    
    /**
     * This method returns a clone of this object.
     *
     * @return A cloned instance of this object.
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
