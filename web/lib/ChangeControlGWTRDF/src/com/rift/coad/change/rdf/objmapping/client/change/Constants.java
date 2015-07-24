/*
 * ChangeControlRDF: The rdf information for the change control system.
 * Copyright (C) 2010  2015 Burntjam
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
 * ActionInfo.java
 */

package com.rift.coad.change.rdf.objmapping.client.change;

/**
 * The class constants.
 *
 * @author brett chaldecott
 */
public class Constants {


    /**
     * The task defintion.
     */
    public final static String ACTION_TASK_DEFINITION = "com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition";

    
    /**
     * The action information.
     */
    public final static String ACTION_INFO = "com.rift.coad.change.rdf.objmapping.change.ActionInfo";


    /**
     * This is the class path for a block object.
     */
    public final static String BLOCK = "com.rift.coad.change.rdf.objmapping.change.task.Block";


    /**
     * This is the calls path for the assign object.
     */
    public final static String ASSIGN = "com.rift.coad.change.rdf.objmapping.change.task.Assign";

    /**
     * This is the class path for the call object.
     */
    public final static String CALL = "com.rift.coad.change.rdf.objmapping.change.task.Call";


    /**
     * This is the class path for the concurrent block.
     */
    public final static String CONCURRENT_BLOCK = "com.rift.coad.change.rdf.objmapping.change.task.ConcurrentBlock";


    /**
     * This is the class path for the embedded block.
     */
    public final static String EMBEDDED_BLOCK = "com.rift.coad.change.rdf.objmapping.change.task.EmbeddedBlock";


    // logical operations
    /**
     * IF Block.
     */
    public final static String IF_BLOCK = "com.rift.coad.change.rdf.objmapping.change.task.logic.If";

    /**
     * ELSE IF Block.
     */
    public final static String ELSE_IF_BLOCK = "com.rift.coad.change.rdf.objmapping.change.task.logic.ElseIf";

    /**
     * ELSE Block.
     */
    public final static String ELSE_BLOCK = "com.rift.coad.change.rdf.objmapping.change.task.logic.Else";


    /**
     * Logical Expression Object.
     */
    public final static String LOGICAL_EXPRESSION = "com.rift.coad.change.rdf.objmapping.change.task.logic.LogicalExpression";


    // loop operations
    /**
     * The static variable that provides the class path for the while loop
     */
    public final static String WHILE_LOOP = "com.rift.coad.change.rdf.objmapping.change.task.loop.WhileLoop";

    /**
     * The for loop class path
     */
    public final static String FOR_LOOP = "com.rift.coad.change.rdf.objmapping.change.task.loop.ForLoop";


    /**
     * The for each loop class path
     */
    public final static String FOR_EACH_LOOP = "com.rift.coad.change.rdf.objmapping.change.task.loop.ForEach";

}
