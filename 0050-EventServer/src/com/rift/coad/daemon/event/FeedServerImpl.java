/*
 * News Feed Server: This is the implementation of the news feed server.
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
 * FeedServerImpl.java
 */// the package path
package com.rift.coad.daemon.event;

// package path
import com.rift.coad.daemon.desktop.MimeManager;
import com.rift.coad.daemon.desktop.MimeType;
import com.rift.coad.daemon.event.db.DBFeedEvent;
import com.rift.coad.daemon.event.db.DBFeedEventMeta;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * The implementation of the feed server.
 * 
 * @author brett chaldecott
 */
public class FeedServerImpl implements FeedServer {
    // class constants
    private final static String EVENT_CONFIG = "event_config";
    private final static String MIME_EXPIRY = "mime_expiry";
    private final static long MIME_EXPIRY_DEFAULT = 2 * 60 * 60 * 1000;    // private member variables
    private static Logger log = Logger.getLogger(FeedServerImpl.class);
    private FeedXMLConfigHelper configHelper = null;
    private Map<String, MimeType> mimeTypes = null;
    private Date mimeTypesTouch = null;
    private long mimeExpiry = 0;

    /**
     * The constructor of the feed server implementation.
     * 
     * @throws EventException
     */
    public FeedServerImpl() throws EventException {
        try {
            Configuration conf = ConfigurationFactory.getInstance().
                    getConfig(FeedServerImpl.class);
            configHelper = new FeedXMLConfigHelper(conf.getString(EVENT_CONFIG));
            mimeExpiry = conf.getLong(MIME_EXPIRY, MIME_EXPIRY_DEFAULT);
            HibernateUtil.getInstance(FeedManagerImpl.class);
        } catch (Exception ex) {
            log.error("Failed to init the feed server implementation : " + ex.getMessage(), ex);
            throw new EventException(
                    "Failed to init the feed server implementation : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the list of feed identifiers.
     * 
     * @return The list of feed identifiers.
     * @throws java.rmi.RemoteException
     * @throws com.rift.coad.daemon.event.EventException
     */
    public List<String> listFeedIdentifiers(String desktop) throws EventException {
        return configHelper.listFeedIdentifiers(desktop);
    }

    /**
     * This method returns the list of events.
     * 
     * @param feedIdentifier
     * @return
     * @throws java.rmi.RemoteException
     * @throws com.rift.coad.daemon.event.EventException
     */
    public synchronized List<FeedEvent> getEvents(String feedIdentifier) throws RemoteException, EventException {
        try {
            List<String[]> filters = configHelper.getFilters(feedIdentifier);
            Session session = HibernateUtil.getInstance(FeedManagerImpl.class).getSession();
            StringBuffer buffer = new StringBuffer();
            String sep = "";
            for (Iterator<String[]> iter = filters.iterator(); iter.hasNext(); iter.next()) {
                buffer.append(sep).append("(DBFeedEventMeta.metaType = ? and DBFeedEventMeta.metaValue = ?)");
                sep = " OR ";
            }

            Query query = null;
            if (sep.length() == 0) {
                query = session.createSQLQuery("SELECT DISTINCT DBFeedEvent.id FROM DBFeedEvent, DBFeedEventMeta " +
                        "WHERE DBFeedEvent.id = DBFeedEventMeta.eventId  ORDER BY DBFeedEvent.id DESC");
            } else {
                query = session.createSQLQuery(String.format("SELECT DISTINCT DBFeedEvent.id FROM DBFeedEvent, DBFeedEventMeta " +
                        "WHERE DBFeedEvent.id = DBFeedEventMeta.eventId AND (%s)  ORDER BY DBFeedEvent.id DESC",
                        buffer.toString()));
            }

            int index = 0;
            for (Iterator<String[]> iter = filters.iterator(); iter.hasNext();) {
                String[] filter = iter.next();
                query.setString(index++, filter[0]);
                query.setString(index++, filter[1]);
            }

            List result = query.list();
            List<FeedEvent> listFeeds = new ArrayList<FeedEvent>();
            for (Iterator iter = result.iterator(); iter.hasNext();) {
                DBFeedEvent event = (DBFeedEvent) session.get(DBFeedEvent.class, (Integer) iter.next());
                try {
                    Validator.validate(this.getClass(), event.getRole());
                    listFeeds.add(new FeedEvent(event.getName(),
                            event.getExternalId(), event.getUsername(),
                            event.getApplication(), event.getDescription(),
                            event.getUrl(), event.getRole()));
                } catch (AuthorizationException ex) {
                    // ignore
                } catch (SecurityException ex) {
                    // ignore
                }
            }
            return listFeeds;
        } catch (EventException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the feed events : " + ex.getMessage(), ex);
            throw new EventException("Failed to retrieve the feed events : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the list of events.
     * 
     * @return This method returns the mime events.
     * @throws java.rmi.RemoteException
     * @throws com.rift.coad.daemon.event.EventException
     */
    public synchronized List<EventInfo> getMimeEvents() throws RemoteException, EventException {
        try {
            Map<String, MimeType> mimeTypes = this.getMimeTypes();
            List<EventInfo> info = new ArrayList<EventInfo>();
            Session session = HibernateUtil.getInstance(FeedManagerImpl.class).getSession();
            List applications = session.createSQLQuery(
                    "SELECT application, COUNT(*) FROM DBFeedEvent GROUP BY application").list();
            for (Iterator iter = applications.iterator(); iter.hasNext();) {
                Object[] result = (Object[]) iter.next();
                if (!mimeTypes.containsKey(result[0])) {
                    log.info("The application [" + result[0] + "] has no matching mime type.");
                    continue;
                }
                info.add(new EventInfo(mimeTypes.get(result[0]), (Integer) result[1]));
            }
            return info;
        } catch (EventException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the mime events : " + ex.getMessage(), ex);
            throw new EventException("Failed to retrieve the mime events : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the required attribute value
     * 
     * @return the requested attribute value or null.
     * @param attribute  The attribute value.
     * @param meta The meta data value.
     * @exception EventException
     */
    private String getAttributeValue(String attribute,
            Set<DBFeedEventMeta> meta) throws EventException {
        try {
            for (Iterator<DBFeedEventMeta> iter = meta.iterator(); iter.hasNext();) {
                DBFeedEventMeta metaValue = iter.next();
                if (metaValue.getMetaType().equalsIgnoreCase(attribute)) {
                    return metaValue.getMetaValue();
                }
            }
        } catch (Throwable ex) {
            log.error("Failed to retrieve the specified value : " + ex.getMessage(), ex);
            throw new EventException("Failed to retrieve the specified value : " +
                    ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * This method retrieves the mime types from cache or from the mime manager.
     * 
     * @return The mime types.
     * @exception EventException
     */
    private Map<String, MimeType> getMimeTypes() throws EventException {
        try {
            if (this.mimeTypesTouch == null ||
                    (this.mimeTypesTouch.getTime() < new Date(new Date().getTime() - mimeExpiry).getTime())) {
                MimeManager manager =
                        (MimeManager) ConnectionManager.getInstance().getConnection(MimeManager.class,
                        "desktop/MimeManager");
                this.mimeTypes = manager.getMimeTypes();
                this.mimeTypesTouch = new Date();
            }
            return this.mimeTypes;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the mime types : " + ex.getMessage(), ex);
            throw new EventException("Failed to retrieve the mime types : " + ex.getMessage(), ex);
        }
    }
}
