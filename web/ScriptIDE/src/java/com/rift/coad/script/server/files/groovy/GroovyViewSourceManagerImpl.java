/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * GroovyFileManager.java
 */


// package path
package com.rift.coad.script.server.files.groovy;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.script.client.files.groovy.GroovyViewSourceManager;
import com.rift.coad.script.client.files.groovy.ViewSourceManagerException;
import org.apache.log4j.Logger;

/**
 * The view source manager.
 *
 * @author brett chaldecott
 */
public class GroovyViewSourceManagerImpl extends RemoteServiceServlet implements
        GroovyViewSourceManager {

    // class constants
    public static final String GROOVY_WEB_PATH = "groovy_web_path";

    // class singletons
    private Logger log = Logger.getLogger(GroovyViewSourceManagerImpl.class);

    // private member variables
    private String path;

    /**
     * The default constructor for the view source manager.
     */
    public GroovyViewSourceManagerImpl() throws ViewSourceManagerException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    GroovyViewSourceManagerImpl.class);
            path = config.getString(GROOVY_WEB_PATH);
        } catch (Exception ex) {
            log.error("Failed to instantiate the php manager : " +
                    ex.getMessage(),ex);
            throw new ViewSourceManagerException
                    ("Failed to instantiate the php manager : " +
                    ex.getMessage());
        }
    }

    
    /**
     * This method is called to return the web path.
     *
     * @return The string containing the web path
     * @throws com.rift.coad.script.client.files.php.ViewSourceManagerException
     */
    public String getWebPath() throws ViewSourceManagerException {
        return path;
    }
}
