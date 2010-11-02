/*
 * CoadunationGWTLibrary: The default console for the coadunation applications.
 * Copyright (C) 2009  Rift IT Contracting
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
 * Factory.java
 */

// the package path
package com.rift.coad.audit.gwt.console.client.query;

// gwt and smart gwt imports
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.audit.LogEntry;

/**
 * This object represents the source audit trail.
 *
 * @author brett chaldecott
 */
public class Factory implements PanelFactory {

    public class SourceAuditTrailPanel extends ConsolePanel {

        /**
         * The constructor that sets the host name and source for the query.
         */
        private SourceAuditTrailPanel() {
        }

        /**
         * This method returns the panel contents.
         *
         * @return The canvas to display.
         */
        @Override
        public Canvas getViewPanel() {
            final ListGrid auditTrailGrid = new ListGrid();
            auditTrailGrid.setHeight(300);
            auditTrailGrid.setAlternateRecordStyles(true);

            if ((hostname.equals("")) && (source.equals(""))) {
                auditTrailGrid.setWidth(800);
                ListGridField hostname = new ListGridField("hostname", "Hostname", 70);
                ListGridField source = new ListGridField("source", "Source", 70);
                ListGridField user = new ListGridField("user", "User", 70);
                ListGridField time = new ListGridField("time", "Time", 180);
                ListGridField status = new ListGridField("status", "Status", 70);
                ListGridField correlationId = new ListGridField("correlationId", "Correlation Id", 70);
                ListGridField externalId = new ListGridField("externalId", "External Id", 70);
                ListGridField request = new ListGridField("request", "Request", 220);
                auditTrailGrid.setFields(hostname,source,user, time, status, correlationId, externalId, request);
            } else {
                auditTrailGrid.setWidth(700);
                ListGridField user = new ListGridField("user", "User", 70);
                ListGridField time = new ListGridField("time", "Time", 180);
                ListGridField status = new ListGridField("status", "Status", 70);
                ListGridField correlationId = new ListGridField("correlationId", "Correlation Id", 70);
                ListGridField externalId = new ListGridField("externalId", "External Id", 70);
                ListGridField request = new ListGridField("request", "Request", 220);
                auditTrailGrid.setFields(user, time, status, correlationId, externalId, request);
            }
            auditTrailGrid.setAutoFetchData(false);
            auditTrailGrid.setShowFilterEditor(false);

            // Create an asynchronous callback to handle the result.
            LogQueryLookup.getService().myMethod(hostname, source, "", "", "", "", 200, new AsyncCallback() {

                public void onSuccess(Object result) {
                    LogEntry[] entries = (LogEntry[]) result;
                    ListGridRecord[] recordGrid = new ListGridRecord[entries.length];
                    for (int index = 0; index < entries.length; ++index) {
                        recordGrid[index] = new ListGridRecord();
                        if ((hostname.equals("")) && (source.equals(""))) {
                            recordGrid[index].setAttribute("hostname", entries[index].getHostname());
                            recordGrid[index].setAttribute("source", entries[index].getSource());
                        }
                        recordGrid[index].setAttribute("user", entries[index].getUser());
                        recordGrid[index].setAttribute("time", entries[index].getTime());
                        recordGrid[index].setAttribute("status", entries[index].getStatus());
                        recordGrid[index].setAttribute("correlationId", entries[index].getCorrelationId());
                        recordGrid[index].setAttribute("externalId", entries[index].getExternalId());
                        recordGrid[index].setAttribute("request", entries[index].getRequest());
                    }
                    auditTrailGrid.setData(recordGrid);
                }

                public void onFailure(Throwable caught) {
                    SC.say("Failed to retrieve the list of sources for a host : " + caught.getMessage());
                }
            });

            return auditTrailGrid;
        }


        /**
         * This method returns the name of the panel
         * 
         * @return The name of the panel
         */
        @Override
        public String getName() {
            return source.substring(source.lastIndexOf("."));
        }
    }

    private String id;
    private String hostname;
    private String source;

    /**
     * The constructor for the factory object that initializes the appropritate information.
     */
    public Factory() {
        this.hostname = "";
        this.source = "";
    }


    /**
     * The constructor for the factory object that initializes the appropritate information.
     *
     * @param hostname The host for the query.
     * @param source The source for the query.
     */
    public Factory(String hostname, String source) {
        this.hostname = hostname;
        this.source = source;
    }


    /**
     * This method returns the canvas reference.
     * 
     * @return This method returns the canvas instance.
     */
    public Canvas create() {
        SourceAuditTrailPanel panel = new SourceAuditTrailPanel();
        id = panel.getID();
        return panel;
    }


    /**
     * This method returns the id of the factory.
     *
     * @return The id of this factory.
     */
    public String getID() {
        return id;
    }

    public String getDescription() {
        return "Audit Trail";
    }
}
