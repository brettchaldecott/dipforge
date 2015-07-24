/*
 * LeviathanScriptEngine: The implementation of the Leviathan script engin.
 * Copyright (C) 2012  2015 Burntjam
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
 * Constants.java
 */

// package path
package com.rift.dipforge.ls.engine.internal;

/**
 * The constants for the engine.
 * 
 * @author brett chaldecott
 */
public class Constants {
    
    /**
     * The blocks the call is part of
     */
    public final static String BLOCK = "*block";
    
    
    /**
     * The last statement executed in the flow.
     */
    public final static String STATEMENT_POS = "*statement";
    
    
    /**
     * The constant that identifies the line number value.
     */
    public final static String LINE_NUMBER = "*line_number";
    
    
    /**
     * The constant that defines the result store variable.
     */
    public final static String RESULT = "*result";
    
    /**
     * This constant identifies the variable flag that will contain the continue
     * value.
     */
    public final static String CONTINUE = "*continue";
    
    
    /**
     * The string that identifies the break flag.
     */
    public final static String BREAK = "*break";
    
    
    /**
     * The string that identifies the return flag.
     */
    public final static String RETURN = "*return";
}
