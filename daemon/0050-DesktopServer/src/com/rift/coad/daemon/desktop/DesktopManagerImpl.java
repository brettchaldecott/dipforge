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
 * DesktopManagerImpl.java
 */

// package path
package com.rift.coad.daemon.desktop;

// java list
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

// log4j imports
import org.apache.log4j.Logger;

// configuration
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.Date;
import java.util.Map;

/**
 * This object implements the desktop manager interface. It provides the ability
 * 
 * @author brett chaldecott
 */
public class DesktopManagerImpl implements DesktopManager {
    
    // class constants
    private final static String DESKTOP_FILE = "desktop_file";
    private final static String DESKTOP_APP_FILE = "desktop_app_file";
    private final static String MIME_EXPIRY = "mime_expiry";
    private final static long MIME_EXPIRY_DEFAULT = 2 * 60 * 60 * 1000;
    
    // private member variables
    private Logger log = Logger.getLogger(DesktopManagerImpl.class);
    private Map<String, MimeType> mimeTypes = null;
    private Date mimeTypesTouch = null;
    private long mimeExpiry = 0;
    private DesktopAppParser parser = null;
    
    /**
     * The desktop manager default constructor.
     */
    public DesktopManagerImpl() throws DesktopException {
        try {
            Configuration conf = ConfigurationFactory.getInstance().
                    getConfig(DesktopManagerImpl.class);
            mimeExpiry = conf.getLong(MIME_EXPIRY, MIME_EXPIRY_DEFAULT);
            parser = new DesktopAppParser(conf.getString(DESKTOP_APP_FILE));
        } catch (Exception ex) {
            log.error("Failed to retrieve the desktop configuration  : " + ex.getMessage(),ex);
            throw new DesktopException(
                    "Failed to retrieve the desktop configuration : " + ex.getMessage(),ex);
        }
    }
    
    /**
     * This method returns the list of desktops
     * @return The list of desktops.
     * @throws com.rift.coad.daemon.desktop.DesktopException
     */
    public List<DesktopInfo> listDesktops() throws 
            DesktopException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(DesktopManagerImpl.class);
            DesktopParser parser = new DesktopParser(config.getString(DESKTOP_FILE));
            List<DesktopInfo> desktopList = new ArrayList<DesktopInfo>();
            List<DesktopParser.Desktop> desktops = parser.getDesktops();
            for (DesktopParser.Desktop desktop : desktops) {
                desktopList.add(new DesktopInfo(desktop.getName(),desktop.getTheme(),
                        desktop.getBackgroundImage(), desktop.isRepeat()));
            }
            return desktopList;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of desktops : " + ex.getMessage(),ex);
            throw new DesktopException(
                    "Failed to retrieve the list of desktops : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds the desktop to the list of desktops of a user.
     * @param name The name of the desktop.
     * @throws com.rift.coad.daemon.desktop.DuplicateEntryException
     * @throws com.rift.coad.daemon.desktop.DesktopException
     */
    public void addDesktop(String name) throws 
            DuplicateEntryException, DesktopException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    /**
     * This method returns the desktop information for the specified name.
     * 
     * @param name The name of the desktop to retrieve.
     * @return The reference to the specified desktop.
     * @throws com.rift.coad.daemon.desktop.EntryNotFoundException
     * @throws com.rift.coad.daemon.desktop.DesktopException
     */
    public DesktopInfo getDesktopInfo(String name) throws EntryNotFoundException, DesktopException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    /**
     * This method renames the desktop.
     * @param oldName The name of the old desktop.
     * @param newName The name of the new desktop.
     * @throws com.rift.coad.daemon.desktop.DuplicateEntryException
     * @throws com.rift.coad.daemon.desktop.EntryNotFoundException
     * @throws com.rift.coad.daemon.desktop.DesktopException
     */
    public void renameDesktop(String oldName, String newName) throws 
            DuplicateEntryException, EntryNotFoundException, DesktopException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    /**
     * The name of the desktop to remove.
     * 
     * @param name This method removes the list of desktops.
     * @throws com.rift.coad.daemon.desktop.DesktopException
     */
    public void removeDesktop(String name) throws 
            DesktopException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * This method returns a list of applications attached to a desktop.
     * @param desktop The name of the desktop to return the application list for.
     * @return This method returns the list of desktop mime types.
     * @throws com.rift.coad.daemon.desktop.DesktopException
     * @throws java.rmi.RemoteException
     */
    public List<MimeType> listDesktopApplications(String desktopName) throws DesktopException {
        Map<String, MimeType> mimeTypes =  getMimeTypes();
        DesktopAppParser.Desktop desktop = parser.getDesktops().get(desktopName);
        if (desktop == null) {
            log.error("Failed to retrieve the list of apps for desktop : " + desktopName);
            throw new DesktopException("Failed to retrieve the list of apps for desktop : " + desktopName);
        }
        List<MimeType> result = new ArrayList<MimeType>();
        for (Iterator<String> iter = desktop.getApps().iterator(); iter.hasNext();) {
            MimeType type = mimeTypes.get(iter.next());
            if (type == null) {
                continue;
            }
            result.add(type);
        }
        return result;
    }
    
    
    /**
     * This method retrieves the mime types from cache or from the mime manager.
     * 
     * @return The mime types.
     * @exception EventException
     */
     private Map<String, MimeType> getMimeTypes() throws DesktopException {
         try {
             if (this.mimeTypesTouch == null || 
                     (this.mimeTypesTouch.getTime() < new Date(new Date().getTime() - mimeExpiry).getTime())) {
                MimeManager manager = 
                    (MimeManager)ConnectionManager.getInstance().getConnection(MimeManager.class, 
                    "desktop/MimeManager");
                this.mimeTypes = manager.getMimeTypes();
                this.mimeTypesTouch = new Date();
             }
            return this.mimeTypes;
         } catch (Throwable ex) {
             log.error("Failed to retrieve the mime types : " + ex.getMessage(),ex);
             throw new DesktopException("Failed to retrieve the mime types : " + ex.getMessage(),ex);
         }
     }
    
}
