/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep.engine;

/**
 *
 * @author ubuntu
 */
public class PythonWrapperException extends Exception {

    /**
     * Creates a new instance of <code>PythonWrapperException</code> without
     * detail message.
     */
    public PythonWrapperException() {
    }

    /**
     * Constructs an instance of <code>PythonWrapperException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PythonWrapperException(String msg) {
        super(msg);
    }

    
    /**
     * The string containing the message, the cause of the exception.
     * @param string The string containing the message.
     * @param thrwbl The cause of the exception
     */
    public PythonWrapperException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
    
    
    
}
