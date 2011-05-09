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
 * ContextInfo.java
 */

// the package path
package com.rift.dipforge.groovy.lib;

import javax.servlet.http.HttpServletRequest;

/**
 * The object that manages the context
 * @author brettc
 */
public class ContextInfo {

    // private static variables
    private static String j2eeContext = null;

    // private member variables
    public String path;


    /**
     * The context info object.
     *
     * @param path The path
     */
    public ContextInfo(String path) {
        this.path = ContextUtils.stripUri(path);
    }


    /**
     * The context info object.
     *
     * @param path The path
     */
    public ContextInfo(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri.startsWith(request.getContextPath())) {
            uri = uri.substring(request.getContextPath().length());
        }
        this.path = ContextUtils.stripUri(uri);
    }



    /**
     * This method returns the context path.
     *
     * @return The string containing the context path.
     */
    public String getPath() {
        return path;
    }


    /**
     * This equals operator.
     *
     * @param obj The object reference.
     * @return The reference to the object.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContextInfo other = (ContextInfo) obj;
        if ((this.path == null) ? (other.path != null) : !this.path.equals(other.path)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code value.
     *
     * @return The return code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.path != null ? this.path.hashCode() : 0);
        return hash;
    }


    /**
     * The to string operator.
     *
     * @return The string.
     */
    @Override
    public String toString() {
        return "ContextInfo{" + "path=" + path + '}';
    }






}
