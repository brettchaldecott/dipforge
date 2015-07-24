/*
 * ChangeControlRDF: The rdf information for the change control system.
 * Copyright (C) 2009  2015 Burntjam
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * ChangeException.java
 */

package com.rift.coad.change.rdf.objmapping.client.change.task.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * This object represents a general change exception error.
 *
 * @author brett chaldecott
 */
public class ChangeException extends Exception implements java.io.Serializable {

    private String exceptionType;
    private String exceptionMessage;
    private String exceptionStack;


    /**
     * Creates a new instance of <code>ChangeException</code> without detail message.
     */
    public ChangeException() {
    }

    /**
     * This method returns the change exception.
     *
     * @param ex The exception that sets the information for this object.
     */
    public ChangeException(Throwable ex) {
        super(ex);
        this.exceptionType = ex.getClass().getName();
        this.exceptionMessage = ex.getMessage();
        this.exceptionStack = stackToString(ex);
    }


    /**
     * This constructor sets the
     * @param exceptionType
     * @param exceptionMessage
     * @param exceptionStack
     */
    public ChangeException(String exceptionType, String exceptionMessage, String exceptionStack) {
        super(exceptionMessage);
        this.exceptionType = exceptionType;
        this.exceptionMessage = exceptionMessage;
        this.exceptionStack = exceptionStack;
    }


    /**
     * This constructor sets the cause of the exception.
     *
     * @param exceptionType The type of exception
     * @param message The message that caused the exception
     * @param cause The cause of the exception.
     */
    public ChangeException(String exceptionType, String message, Throwable cause) {
        super(message, cause);
        this.exceptionType = exceptionType;
        this.exceptionMessage = message;
        this.exceptionStack = stackToString(cause);
    }


    /**
     * This method returns the exception message.
     *
     * @return The string containing the message
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }


    /**
     * This method sets the exception message.
     *
     * @param exceptionMessage The string containing the exception message.
     */
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }


    /**
     * This method returns the exception stack.
     *
     * @return The string containing the exception stack.
     */
    public String getExceptionStack() {
        return exceptionStack;
    }


    /**
     * This method sets the exception stack.
     *
     * @param exceptionStack The string containing the exception stack.
     */
    public void setExceptionStack(String exceptionStack) {
        this.exceptionStack = exceptionStack;
    }


    /**
     * This method sets the stack exception reference
     * @param cause
     */
    public void setExceptionStack(Throwable cause) {
        this.exceptionStack = stackToString(cause);
    }


    /**
     * This method returns the exception type information.
     *
     * @return The string containing the exception type information.
     */
    public String getExceptionType() {
        return exceptionType;
    }
    

    /**
     * This method sets the exception type information.
     *
     * @param exceptionType The string containing the exception type information.
     */
    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }


    /**
     * This method converts the cause to a string.
     * @param cause The cause of the exception.
     * @return The result string.
     */
    private String stackToString(Throwable cause) {
        return cause.getMessage();
    }

}
