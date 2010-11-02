/*
 * ChangeControlConsole: The console for the change manager
 * Copyright (C) 2009  Rift IT Contracting
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
 * QueryAsync.java
 */


// package path
package com.rift.coad.change.client.resources;

// gwt imports
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * This interface is responsible for async handling.
 *
 * @author brett chaldecott
 */
public interface QueryAsync {
    /**
     * This asynchronious version of the sychronious list types method.
     *
     * @param baseTypes The base types.
     * @param callback The call back method.
     */
    public void listTypes(String[] baseTypes, AsyncCallback callback);
}
