/*
 * Tomcat: The deployer for the tomcat daemon
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
 * WarUtils.java
 */

package com.rift.coad.daemon.tomcat.war;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import org.apache.log4j.Logger;

/**
 * The war utils.
 *
 * @author brett chaldecott
 */
public class WarUtils {

    // class singleton
    private static Logger log = Logger.getLogger(WarUtils.class);

    /**
     * This method returns the context for the war path
     * @param war
     * @return
     */
    public static String getWarContext(File war) {
        try {
            TomcatXMLContext xmlParser = new TomcatXMLContext(
                    new URLClassLoader(new URL[] {war.toURL()}));
            return xmlParser.getContext();
        } catch (Exception ex) {
            log.info("Failed to retrieve the Tomcat context from the war ["
                    + ex.getMessage() + "] defaulting to file name.",ex);
        }
        String context = war.getName().replaceAll(" ","_");
        return "/" + context.substring(0,context.lastIndexOf("."));
    }
}
