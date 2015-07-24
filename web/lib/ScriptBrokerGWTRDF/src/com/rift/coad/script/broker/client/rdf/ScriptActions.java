/*
 * ScriptBrokerRDF: The rdf information for the script broker
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
 * ScriptActions.java
 */

package com.rift.coad.script.broker.client.rdf;

/**
 * This file contains the actions that can be performed on scripts
 *
 * @author brett chaldecott
 */
public class ScriptActions {

    /**
     * when a script is added
     */
    public final static String ADD = "add";
    /**
     * when a script is updated.
     */
    public final static String UPDATE = "update";
    /**
     * when a script is removed.
     */
    public final static String REMOVE = "remove";
}
