/*
 * CoadunationDesktop: The desktop interface to the Coadunation Server.
 * Copyright (C) 2008  2015 Burntjam
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
 * EventPanel.java
 */
package com.rift.coad.desktop.client.desk.event;

// gwt imports
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.Iterator;
import java.util.List;

/**
 * The event panel is responsible displaying the events information for
 * various applications.
 * 
 * @author brett chaldecott
 */
public class EventPanel extends Composite {

    /**
     * This class is responsible for representing a feed entry.
     */
    public class EventTimer extends Timer {
        // private member variable
        private boolean initialized = false;

        /**
         * The default constructor of the event timer.
         */
        public EventTimer() {
        }

        /**
         * This method implements the run functionality.
         */
        @Override
        public void run() {
            EventRPCServiceHelper.getService().getMimeEvents(
                    new AsyncCallback() {

                        public void onSuccess(Object result) {
                            List<EventInfo> mimeEvents = (List<EventInfo>) result;
                            content.clear();
                            int count = 0;
                            if (mimeEvents.size() > 0) {
                                eventPanelHeading.setHTML("<b>Active Applications</br>");
                                for (Iterator<EventInfo> iter = mimeEvents.iterator(); iter.hasNext() && count < 10; count++) {
                                    EventInfo type = iter.next();
                                    ApplicationLauncher launcher = new ApplicationLauncher(type.getMime(), type.getEventNum());
                                    content.add(launcher);
                                }
                            } else {
                                HTML noEvents = new HTML("No Events");
                                noEvents.setStyleName("Application-Event-MouseOut");
                                content.add(noEvents);
                            }
                        }

                        public void onFailure(Throwable caught) {
                            //Window.alert("Failed to retrieve the tab information :" + caught.getMessage());
                            }
                    });

            if (!initialized) {
                this.scheduleRepeating(2 * 60 * 1000);
                initialized = true;
            }
        }

    }    // private member variables
    private HTML eventPanelHeading = null;
    private VerticalPanel panel = null;
    private VerticalPanel content = null;
    private EventTimer timer = null;

    /**
     * The event panel
     */
    public EventPanel() {
        panel = new VerticalPanel();
        panel.setWidth("100%");
        eventPanelHeading = new HTML("<b>Active Applications</br>");
        eventPanelHeading.setStyleName("Event-Panel-Heading");
        panel.add(eventPanelHeading);
        content = new VerticalPanel();
        content.setWidth("100%");
        panel.add(content);
        this.initWidget(panel);
        this.setStyleName("Event-Panel");

        timer = new EventTimer();
        timer.schedule(10);
    }
    /**
     * 
     */
}
