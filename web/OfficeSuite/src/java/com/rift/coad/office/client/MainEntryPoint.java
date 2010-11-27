/*
 * OfficeSuite: The Office Suite for Coadunation OS.
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
 * MainEntryPoint.java
 */

package com.rift.coad.office.client;

import com.google.gwt.core.client.EntryPoint;
import com.rift.coad.gwt.lib.client.console.Console;
import com.rift.coad.office.client.documents.ScopeCache;
import com.rift.coad.office.client.tree.TreeDataHandler;

/**
 *
 * @author brett
 */
public class MainEntryPoint implements EntryPoint {
    
    /** Creates a new instance of MainEntryPoint */
    public MainEntryPoint() {
    }
    
    /**
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        ScopeCache.getInstance();
        Console console = new Console("Office", new TreeDataHandler());
    }
    
}
