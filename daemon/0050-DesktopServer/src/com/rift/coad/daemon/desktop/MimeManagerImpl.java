/*
 * DesktopServerClient: The client interface to the desktop server.
 * Copyright (C) 2008  Rift IT Contracting
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
 * MimeManagerImpl.java
 */

// package path
package com.rift.coad.daemon.desktop;

// java imports
import java.rmi.RemoteException;
import java.util.Map;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 * The implementation of the mime management.
 * 
 * @author brett chaldecott
 */
public class MimeManagerImpl implements MimeManager {
    
    // private member variables.
    private static Logger log = Logger.getLogger(MimeManagerImpl.class); 
    private MimeParser parser = null;
    
    /**
     * The constructor of the mime manager.
     */
    public MimeManagerImpl() throws DesktopException {
        try {
            Configuration conf = ConfigurationFactory.getInstance().getConfig(MimeManagerImpl.class);
            parser = new MimeParser(conf.getString("mime_file"));
        } catch (Exception ex) {
            log.error("Failed to init the mime manager : " + ex.getMessage(), ex);
            throw new DesktopException("Failed to init the mime manager : " + ex.getMessage(), ex);
        }
    }
    
    /**
     * This object returns the mime types
     * @return The list of mime types.
     * @throws java.rmi.RemoteException
     * @throws com.rift.coad.daemon.desktop.DesktopException
     */
    public Map<String, MimeType> getMimeTypes() throws RemoteException, DesktopException {
        return parser.getTypes();
    }

}
