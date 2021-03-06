package com.rift.coad.daemon.messageservice.db;
// Generated Feb 19, 2007 7:55:38 AM by Hibernate Tools 3.2.0.beta6a


import java.sql.Timestamp;

/**
 * MessageError generated by hbm2java
 */
public class MessageError  implements java.io.Serializable {

    // Fields    

     /**
      * 		       auto_increment
 * 		    
     */
     private Integer id;
     private int errorLevel;
     private Timestamp errorDate;
     private String msg;
     private Message message;

     // Constructors

    /** default constructor */
    public MessageError() {
    }

	/** minimal constructor */
    public MessageError(int errorLevel, String msg) {
        this.errorLevel = errorLevel;
        this.msg = msg;
    }
    /** full constructor */
    public MessageError(int errorLevel, Timestamp errorDate, String msg, Message message) {
       this.errorLevel = errorLevel;
       this.errorDate = errorDate;
       this.msg = msg;
       this.message = message;
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
    public int getErrorLevel() {
        return this.errorLevel;
    }
    
    public void setErrorLevel(int errorLevel) {
        this.errorLevel = errorLevel;
    }
    public Timestamp getErrorDate() {
        return this.errorDate;
    }
    
    public void setErrorDate(Timestamp errorDate) {
        this.errorDate = errorDate;
    }
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Message getMessage() {
        return this.message;
    }
    
    public void setMessage(Message message) {
        this.message = message;
    }




}


