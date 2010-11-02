/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * GroovyScopeCache.java
 */

package com.rift.coad.script.client;

import com.google.gwt.core.client.EntryPoint;
import com.rift.coad.gwt.lib.client.console.Console;
import com.rift.coad.script.client.scope.GroovyScopeCache;
import com.rift.coad.script.client.scope.PHPScopeCache;
import com.rift.coad.script.client.scope.ScopeCache;
import com.rift.coad.script.client.tree.ResourceDataHandler;
import com.rift.coad.script.client.type.TypeCache;

/**
 *
 *
 * @author brett chaldecott
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
        PHPScopeCache.getInstance();
        GroovyScopeCache.getInstance();
        com.rift.coad.script.client.files.php.ViewSourceCache.getInstance();
        com.rift.coad.script.client.files.groovy.GroovyViewSourceCache.getInstance();
        TypeCache.init();
        Console console = new Console("IDE", new ResourceDataHandler());
        
    }
    
}
