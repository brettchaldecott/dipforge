/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  Rift IT Contracting
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
 * ServiceConstants.java
 */

package com.rift.coad.change.request;

/**
 * The list of constants that are required by the change manager.
 *
 * @author brett chaldecott
 */
public class ServiceConstants {
    /**
     * The string containing the
     */
    public final static String SERVICE = "ChangeRequestManager";


    /**
     * The string containing the call back information.
     */
    public final static String CALLBACK_SERVICE = "RequestHandlerCallback";


    /**
     * The string containing the create request handler service
     */
    public final static String CREATE_REQUEST_HANDLER_SERVICE = "CreateRequestHandler";
}
