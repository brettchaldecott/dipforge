/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.groovy;

/**
 * This exception is thrown if there is a problem with bridge exception.
 *
 * @author brett chaldecott
 */
public class BridgeException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>BridgeException</code> without detail message.
     */
    public BridgeException() {
    }


    /**
     * Constructs an instance of <code>BridgeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public BridgeException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>BridgeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public BridgeException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
