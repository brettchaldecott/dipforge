/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.auth;

/**
 *
 * @author brett
 */
public class RDBException extends Exception {

    /**
     * Creates a new instance of <code>RDBException</code> without detail message.
     */
    public RDBException() {
    }


    /**
     * Constructs an instance of <code>RDBException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RDBException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>RDBException</code> with the specified detail message.
     * @param msg the detail message.
     * @param ex The exception stack
     */
    public RDBException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
