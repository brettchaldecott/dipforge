/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.coad.change.request.action.sleep;

/**
 *
 * @author brett chaldecott
 */
public class SleepManagerException extends Exception {

    /**
     * Creates a new instance of
     * <code>SleepManagerException</code> without detail message.
     */
    public SleepManagerException() {
    }

    /**
     * Constructs an instance of
     * <code>SleepManagerException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public SleepManagerException(String msg) {
        super(msg);
    }

    
    /**
     * Constructs an instance of
     * <code>SleepManagerException</code> with the specified detail message.
     *
     * @param message the detail message.
     * @param cause The cause of the exception
     */
    public SleepManagerException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
