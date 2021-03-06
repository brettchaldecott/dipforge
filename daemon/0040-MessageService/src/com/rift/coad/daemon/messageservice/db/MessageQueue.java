package com.rift.coad.daemon.messageservice.db;
// Generated Feb 19, 2007 7:55:38 AM by Hibernate Tools 3.2.0.beta6a


import java.util.HashSet;
import java.util.Set;

/**
 * MessageQueue generated by hbm2java
 */
public class MessageQueue  implements java.io.Serializable {

    // Fields    

     /**
      * 		       auto_increment
 * 		    
     */
     private Integer id;
     private String messageQueueName;
     private Integer named;
     private Set<Message> messages = new HashSet<Message>(0);
     private Set<MessageQueueService> messageQueueServices = new HashSet<MessageQueueService>(0);

     // Constructors

    /** default constructor */
    public MessageQueue() {
    }

	/** minimal constructor */
    public MessageQueue(String messageQueueName) {
        this.messageQueueName = messageQueueName;
    }
    /** full constructor */
    public MessageQueue(String messageQueueName, Integer named, Set<Message> messages, Set<MessageQueueService> messageQueueServices) {
       this.messageQueueName = messageQueueName;
       this.named = named;
       this.messages = messages;
       this.messageQueueServices = messageQueueServices;
    }
    
   
    // Property accessors
    /**       
     *      * 		       auto_increment
     * 		    
     */
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getMessageQueueName() {
        return this.messageQueueName;
    }
    
    public void setMessageQueueName(String messageQueueName) {
        this.messageQueueName = messageQueueName;
    }
    public Integer getNamed() {
        return this.named;
    }
    
    public void setNamed(Integer named) {
        this.named = named;
    }
    public Set<Message> getMessages() {
        return this.messages;
    }
    
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
    public Set<MessageQueueService> getMessageQueueServices() {
        return this.messageQueueServices;
    }
    
    public void setMessageQueueServices(Set<MessageQueueService> messageQueueServices) {
        this.messageQueueServices = messageQueueServices;
    }




}


