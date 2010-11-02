/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.script.broker;

/**
 *
 * @author brett
 */
public class UnknownEntryException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>UnknownEntryException</code> without detail message.
     */
    public UnknownEntryException() {
    }


    /**
     * Constructs an instance of <code>UnknownEntryException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public UnknownEntryException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>UnknownEntryException</code> with the 
     * specified detail message.
     * 
     * @param msg the detail message.
     * @param cause The cause of the exception.
     */
    public UnknownEntryException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
