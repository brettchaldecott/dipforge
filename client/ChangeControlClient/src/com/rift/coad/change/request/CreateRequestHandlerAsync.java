/*
 * ChangeControlManager: The manager for the change events.
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
 * CreateRequestHandlerAsync.java
 */

// package path
package com.rift.coad.change.request;

/**
 * This method is the async version of the create request handler.
 *
 * @author brett chaldecott
 */
public interface CreateRequestHandlerAsync {
    /**
     * This method initiates a request.
     *
     * @param request The request to init.
     * @return The string containing the message id.
     */
    public String createRequest(Request request);
    
}
