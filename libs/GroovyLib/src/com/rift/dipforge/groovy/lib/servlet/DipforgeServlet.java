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

package com.rift.dipforge.groovy.lib.servlet;

import groovy.servlet.AbstractHttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * The dip forge servlet that exposes the get script uri method
 *
 * @author brett chaldecott
 */
public abstract class DipforgeServlet extends AbstractHttpServlet {


    /**
     * The script uri method.
     *
     * @param request The request method
     * @return The http servlet request.
     */
    public String getScriptUri(HttpServletRequest request) {
        return super.getScriptUri(request);
    }


    /**
     * This method returns the character encoding used by this servlet
     *
     * @return The character encoding.
     */
    public String getEncoding() {
        return encoding;
    }
}
