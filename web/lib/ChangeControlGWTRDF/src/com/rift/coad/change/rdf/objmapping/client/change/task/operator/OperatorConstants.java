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
 * OperatorConstants.java
 */

// package path
package com.rift.coad.change.rdf.objmapping.client.change.task.operator;

/**
 * The constants that are known by the operator
 * @author brett chaldecott
 */
public class OperatorConstants {
    // logical operations
    public final static String AND = "&&";
    public final static String OR = "||";
    public final static String NOT = "!";
    public final static String NOT_EQUAL = "!=";
    public final static String EQUAL = "==";
    public final static String LESS = "<";
    public final static String GREATER = ">";
    
    // maths operations
    public final static String INCREMENT = "++";
    public final static String DECREMENT = "--";
    public final static String ADD = "+";
    public final static String SUBTRACT = "-";
    public final static String MULTIPLY = "*";
    public final static String DIVIDE = "/";
    public final static String MOD = "%";
}
