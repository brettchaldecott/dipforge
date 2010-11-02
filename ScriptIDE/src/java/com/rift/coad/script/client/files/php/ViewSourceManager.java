/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * ViewSourceManager.java
 */

// package path
package com.rift.coad.script.client.files.php;

// gwt imports
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * This object is responsible for
 *
 * @author brett chaldecott
 */
public interface ViewSourceManager extends RemoteService{

    /**
     * This method returns the path that the PHP web content falls under.
     *
     * @return The string containing the php web path.
     * @throws com.rift.coad.script.client.files.php.ViewSourceManagerException
     */
    public String getWebPath() throws ViewSourceManagerException;
}
