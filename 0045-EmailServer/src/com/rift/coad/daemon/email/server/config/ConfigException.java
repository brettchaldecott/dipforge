/*
 * ConfigException.java
 *
 * Created on February 11, 2008, 5:58 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.rift.coad.daemon.email.server.config;

/**
 *
 * @author brett
 */
public class ConfigException extends java.lang.Exception implements 
        java.io.Serializable{
    
    /**
     * Creates a new instance of <code>ConfigException</code> without detail 
     * message.
     */
    public ConfigException() {
    }
    
    
    /**
     * Constructs an instance of <code>ConfigException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public ConfigException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>ConfigException</code> with the specified
     * detail message.
     * @param msg the detail message.
     * @param ex The cause of the exception.
     */
    public ConfigException(String msg, Exception ex) {
        super(msg,ex);
    }
}
