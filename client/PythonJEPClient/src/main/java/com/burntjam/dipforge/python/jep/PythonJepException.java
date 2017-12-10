/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep;

/**
 *
 * @author ubuntu
 */
public class PythonJepException extends Exception implements java.io.Serializable {

    private String stringStack;
    
    /**
     * Creates a new instance of <code>PythonJepException</code> without detail
     * message.
     */
    public PythonJepException() {
    }

    /**
     * Constructs an instance of <code>PythonJepException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PythonJepException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>PythonJepException</code> with the specified detail message.
     * @param msg the detail message.
     * @param ex The cause of this exception.
     */
    public PythonJepException(String msg, Throwable ex) {
        super(msg,ex);
    }

    
    /**
     * Constructs an instance of <code>PythonJepException</code> with the specified detail message.
     * @param msg the detail message.
     * @param ex The cause of this exception.
     */
    public PythonJepException(String msg, String stringStack) {
        super(msg);
        this.stringStack = stringStack;
    }
    
    
    /**
     * The getter for the string stack method.
     * 
     * @return The string stack
     */
    public String getStringStack() {
        return stringStack;
    }

    
    /**
     * The setter for the string stack.
     * 
     * @param stringStack The reference to the new string stack
     */
    public void setStringStack(String stringStack) {
        this.stringStack = stringStack;
    }
}
