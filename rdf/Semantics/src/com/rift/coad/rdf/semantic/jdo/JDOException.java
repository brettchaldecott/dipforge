/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.jdo;

/**
 *
 * @author brettc
 */
public class JDOException extends Exception {

    /**
     * Creates a new instance of <code>JDOException</code> without detail message.
     */
    public JDOException() {
    }


    /**
     * Constructs an instance of <code>JDOException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public JDOException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>JDOException</code> with the specified detail message.
     * 
     * @param msg the detail message.
     * @param cause The cause of the exception
     */
    public JDOException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
