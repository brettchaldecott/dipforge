/*
 * GroovyLib: The goovy environment manager
 * Copyright (C) 2011  Rift IT Contracting
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
 * GroovyEnvironmentManager.java
 */

// package path
package com.rift.dipforge.groovy.lib;

/**
 * The context utils for the groovy environment
 *
 * @author brett chaldecott
 */
public class ContextUtils {


    /**
     * This method strips the uri.
     *
     * @param uri The uri to strip back.
     * @return The stripped uri.
     */
    public static String stripUri(String uri) {
        String result = uri;
        if (result.startsWith("/")) {
            result = result.substring(1);
        }
        
        int endpos = result.indexOf("/");
        if (endpos != -1) {
            result = result.substring(0, endpos);
        }
        return result;
    }

}
