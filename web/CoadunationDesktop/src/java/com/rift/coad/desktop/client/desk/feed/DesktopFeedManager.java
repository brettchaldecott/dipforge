/*
 * CoadunationDesktop: The desktop interface to the Coadunation Server.
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
 * DesktopManager.java
 */// package path
package com.rift.coad.desktop.client.desk.feed;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This object is responsible for managing the feed information on a desktop.
 * 
 * @author brett chaldecott
 */
public class DesktopFeedManager {

    /**
     * The feed cache entry.
     */
    private static class FeedCacheEntry {

        private String tab = null;
        private Date touchTime = new Date();
        private List<FeedEvent> events = null;

        /**
         * The constructor of the feed cache entry.
         * 
         * @param tab The string containing the tab name.
         */
        public FeedCacheEntry(String tab) {
            this.tab = tab;
        }

        /**
         * The string containing the tab name.
         * 
         * @return The string containing the tab name.
         */
        public String getTab() {
            return tab;
        }

        /**
         * The list of events.
         * 
         * @return The list of events.
         */
        public List<FeedEvent> getEvents() {
            if (touchTime.getTime() > (new Date().getTime() - (10 * 60 * 1000))) {
                return events;
            }
            return null;
        }

        /**
         * The list of events for this feed.
         * 
         * @param events The list of events
         */
        public void setEvents(List<FeedEvent> events) {
            touchTime = new Date();
            this.events = events;
        }
    }

    /**
     * This object 
     */
    public static class FeedCache {
        // class singleton 
        private static FeedCache singleton = null;        // private member variables
        private Map<String, FeedCacheEntry> entries = new HashMap<String, FeedCacheEntry>();

        private FeedCache() {
        }

        /**
         * The method returns the reference to the feed cache singleton.
         * 
         * @return A reference to the feed cache.
         */
        public synchronized static FeedCache getInstance() {
            if (singleton == null) {
                singleton = new FeedCache();
            }
            return singleton;
        }

        /**
         * This method returns the list of events.
         */
        public synchronized List<FeedEvent> getEvents(String tab) {
            FeedCacheEntry entry = entries.get(tab);
            if (entry == null) {
                return null;
            }
            return entry.getEvents();
        }

        /**
         * this method updates the events
         */
        public synchronized void setEvents(String tab, List<FeedEvent> events) {
            FeedCacheEntry entry = entries.get(tab);
            if (entry == null) {
                entry = new FeedCacheEntry(tab);
                entries.put(tab, entry);
            }
            entry.setEvents(events);
        }
    }


    /**
     * This class is responsible for representing a feed entry.
     */
    public class DesktopFeedEntry extends Timer {
        // the pivate member variables
        private Timer timer = null;
        private DesktopFeed feed = null;
        private boolean initialized = false;
        private String[] tabs = null;

        /**
         * The constructor of the feed.
         * 
         * @param feed The feed entry to store.
         */
        public DesktopFeedEntry(DesktopFeed feed) {
            this.feed = feed;

        }

        /**
         * This method returns the feed.
         */
        public DesktopFeed getFeed() {
            return feed;
        }

        /**
         * This method is run to retrieve the feed information for the given desktop.
         */
        @Override
        public void run() {
            if (initialized == false) {
                // call the async method to retrieve the tab information

                FeedRPCServiceHelper.getService().getTabs(feed.getDesktopName(),
                        new AsyncCallback() {

                            public void onSuccess(Object result) {
                                tabs = (String[]) result;
                                feed.setTabs(tabs);
                                processTabUpdate(0);
                            }

                            public void onFailure(Throwable caught) {
                                //Window.alert("Failed to retrieve the tab information :" + caught.getMessage());
                            }
                        });
                this.scheduleRepeating(10 * 60 * 1000);
                initialized = true;
            } else {
                // call the async method to retrieve tab feed information.
                processTabUpdate(0);
            }
        }

        /**
         * This method is called to process the tab updates
         */
        private void processTabUpdate(final int index) {
            List<FeedEvent> listEvents = FeedCache.getInstance().getEvents(tabs[index]);
            if (listEvents != null) {
                FeedEvent[] events = new FeedEvent[listEvents.size()];
                events = listEvents.toArray(new FeedEvent[0]);
                feed.addContent(tabs[index], events);
                if (tabs.length > (index + 1)) {
                    processTabUpdate(index + 1);
                }
            } else {
                FeedRPCServiceHelper.getService().getEvents(tabs[index],
                        new AsyncCallback() {

                            public void onSuccess(Object result) {
                                List<FeedEvent> listEvents = (List<FeedEvent>) result;
                                FeedEvent[] events = new FeedEvent[listEvents.size()];
                                events = listEvents.toArray(new FeedEvent[0]);
                                feed.addContent(tabs[index], events);
                                FeedCache.getInstance().setEvents(tabs[index], listEvents);
                                if (tabs.length > (index + 1)) {
                                    processTabUpdate(index + 1);
                                }
                            }

                            public void onFailure(Throwable caught) {
                                //Window.alert("Failed to retrieve the tab information :" + caught.getMessage());
                                //processTabUpdate(index);
                                }
                        });
            }
        }
    }    // private member variables
    private static DesktopFeedManager singleton = null;
    private Map<String, DesktopFeedEntry> feeds = new HashMap<String, DesktopFeedEntry>();

    /**
     * The default constructor of the desktop feed manager.
     */
    private DesktopFeedManager() {
    }

    /**
     * Returns the instance of thedesktop feed manager.
     * 
     * @return This method returns the instance of the feed.
     */
    public static synchronized DesktopFeedManager getInstance() {
        if (singleton == null) {
            singleton = new DesktopFeedManager();
        }
        return singleton;
    }

    /**
     * This method registers a desktop feed with the manager.
     */
    public void registerDesktopFeed(DesktopFeed feed) {
        DesktopFeedEntry entry = new DesktopFeedEntry(feed);
        feeds.put(feed.getDesktopName(), entry);
        entry.schedule(10);
    }
}
