/*
 * CoadunationDesktop: The desktop interface to the Coadunation Server.
 * Copyright (C) 2007  Rift IT Contracting
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
 * DesktopRPCImpl.java
 */

// package path
package com.rift.coad.desktop.server.desk;

// java import
import com.rift.coad.desktop.client.desk.DesktopInfo;
import com.rift.coad.desktop.client.desk.MimeType;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import java.util.Date;

// gwt import
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// log4j imports
import org.apache.log4j.Logger;

// coadunation import
import com.rift.coad.desktop.client.desk.DesktopInfo;
import com.rift.coad.desktop.client.desk.DesktopRPC;
import com.rift.coad.desktop.client.desk.DesktopRPCException;

// coadunation desktop server imports
import com.rift.coad.daemon.desktop.DesktopManager;
import com.rift.coad.daemon.desktop.MimeManager;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * This object is responsible for processing the desktop RPC based requests.
 * 
 * @author brett chaldecott
 */
public class DesktopRPCImpl extends RemoteServiceServlet implements
    DesktopRPC {
    
    // class constants
    private final static String MIME_CACHE_EXPIRY = "mime_cache_expiry";
    private final static long MIME_CACHE_EXPIRY_DEFAULT = 2 * 60 * 60 * 1000;
        
    // private member variables
    private Logger log = Logger.getLogger(DesktopRPCImpl.class);
    private List<MimeType> mimeTypes = null;
    private Date mimeTouchTime = null;
    private long mimeCacheExpiry = 0;

    public DesktopRPCImpl() throws DesktopRPCException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(this.getClass());
            mimeCacheExpiry = config.getLong(MIME_CACHE_EXPIRY, MIME_CACHE_EXPIRY_DEFAULT);
        } catch (Exception ex) {
            log.error("Failed to instantiate the event rpc service : " + ex.getMessage(),ex);
            throw new DesktopRPCException("Failed to instantiate the event rpc service : " + ex.getMessage(),ex);
        }
    }

    
    
    /**
     * This method returns the list of available desktops.
     * @return The list of desktops.
     * @throws com.rift.coad.desktop.client.desk.DesktopRPCException
     */
    public List<DesktopInfo> listDesktops() throws DesktopRPCException {
        try {
            List<DesktopInfo> target = new ArrayList<DesktopInfo>();
            List<com.rift.coad.daemon.desktop.DesktopInfo> source = 
                    getDesktopManager().listDesktops();
            for (com.rift.coad.daemon.desktop.DesktopInfo desktop : source) {
                target.add(new DesktopInfo(desktop.getName(),
                        desktop.getTheme(),desktop.getBackgroundImage(),desktop.isRepeat()));
            }
            return target;
        } catch (DesktopRPCException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the desktop list : " + ex.getMessage(),ex);
            throw new DesktopRPCException(
                    "Failed to retrieve the desktop list : " + ex.getMessage(),ex);
        }
    }
    
    /**
     * This method attempts to make a connection to the master bar to request the menu structure.
     * 
     * @return The reference to the desktop manager
     */
    private DesktopManager getDesktopManager() throws  DesktopRPCException {
        try {
            Context ctx = new InitialContext();
            Object ref = ctx.lookup("desktop/DesktopManager");
            if (ref != null) {
                return (DesktopManager) PortableRemoteObject.narrow(ref, DesktopManager.class);
            }
            log.error("Failed to narrow the desktop manager");
            throw new DesktopRPCException("Failed to narrow the desktop manager ");
        } catch (DesktopRPCException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Fail to narrow the desktop manager : " + ex.getMessage(), ex);
            throw new DesktopRPCException(
                    "Fail to narrow the desktop manager : " + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * This method returns a list of the desktop applications.
     * 
     * @return The list of desktop applications.
     * @throws com.rift.coad.desktop.client.desk.DesktopRPCException
     */
    public List<MimeType> listDesktopApplications(String desktop) throws DesktopRPCException {
        try {
            List<com.rift.coad.daemon.desktop.MimeType> serverApps = 
                    getDesktopManager().listDesktopApplications(desktop);
            List<MimeType> apps = new ArrayList<MimeType>();
            for (Iterator<com.rift.coad.daemon.desktop.MimeType> iter = serverApps.iterator(); iter.hasNext();) {
                com.rift.coad.daemon.desktop.MimeType mimeType = iter.next();
                apps.add(new MimeType(mimeType.getName(), 
                        mimeType.getUrl(), mimeType.getIcon(), mimeType.getWidth(), mimeType.getHeight()));
            }
            return apps;
        } catch (DesktopRPCException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of desktop applications : " + ex.getMessage(),ex);
            throw new DesktopRPCException(
                    "Failed to retrieve the list of desktop applications : " + ex.getMessage(),ex);
        }
    }
    
    
    
    
    /**
     * This method is responsible for returning the list of mime types
     * @return This method returns a list of mime types
     * @throws com.rift.coad.desktop.client.desk.event.EventException
     */
    public List<MimeType> listMimeTypes() throws DesktopRPCException {
        try {
            if ((this.mimeTouchTime != null) && (this.mimeTouchTime.getTime() > 
                    new Date(new Date().getTime() - this.mimeCacheExpiry).getTime())) {
                return this.mimeTypes;
            }
            MimeManager server = 
                    (MimeManager)ConnectionManager.getInstance().getConnection(MimeManager.class, "desktop/MimeManager");
            Map<String,com.rift.coad.daemon.desktop.MimeType> mimeMap = server.getMimeTypes();
            SortedSet<String> mimeNames = new TreeSet<String>();
            for (Iterator<String> iter = mimeMap.keySet().iterator(); iter.hasNext();) {
                mimeNames.add(iter.next());
            }
            mimeTypes = new ArrayList<MimeType>();
            for (Iterator<String> iter = mimeNames.iterator(); iter.hasNext();) {
                com.rift.coad.daemon.desktop.MimeType mimeType = mimeMap.get(iter.next());
                mimeTypes.add(new MimeType(mimeType.getName(), 
                        mimeType.getUrl(), mimeType.getIcon(), mimeType.getWidth(), mimeType.getHeight()));
            }
            mimeTouchTime = new Date();
            return mimeTypes;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the applications : " + ex.getMessage(),ex);
            throw new DesktopRPCException(
                    "Failed to retrieve the applications : " + ex.getMessage());
        }
    }
}
