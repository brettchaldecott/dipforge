/*
 * CoadunationTypeManagerConsole: The type management console.
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
 * RDFViewFactory.java
 */


package com.rift.coad.type.manager.client;

import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * This object displays the RDF xml information.
 *
 * @author brett chaldecott
 */
public class RDFViewFactory implements PanelFactory {

    /**
     * The panel with rdf information.
     */
    public class RDFPanel extends ConsolePanel {

        /**
         * The default constructor for the RDF panel
         */
        public RDFPanel() {
        }


        /**
         * This method returns the panel reference.
         *
         * @return The reference to the panel.
         */
        @Override
        public Canvas getViewPanel() {
            VLayout layout = new VLayout();
            layout.setWidth100();
            layout.setHeight100();

            Window window = new Window();
            window.setTitle("http://[hostname]:[port]" + URI);
            window.setWidth100();
            window.setHeight100();
            window.setCanDragReposition(false);
            window.setCanDragResize(true);
            window.setHeaderControls(HeaderControls.HEADER_LABEL);
            window.setSrc(URI);

            layout.addMember(window);
            return layout;
        }


        /**
         * The name of the panel
         *
         * @return The string containing the name of the panel.
         */
        @Override
        public String getName() {
            return "RDF View";
        }

    }

    // class constants
    private final static String URI = "/RDFTypeSource/Query?";

    // class singleton
    private static RDFViewFactory singleton = null;

    // private member variables
    private String id;
    private RDFPanel panel;

    /**
     * The default constructor for the RDFView factory.
     */
    private RDFViewFactory() {
    }


    /**
     * This method returns an instance of the RDFViewFactory.
     *
     * @return The reference to the factory.
     */
    public static RDFViewFactory getInstance() {
        if (singleton == null) {
            singleton = new RDFViewFactory();
        }
        return singleton;
    }


    /**
     * This method returns a reference to a new panel.
     *
     * @return The reference to the new panel.
     */
    public Canvas create() {
        if (panel == null) {
            panel = new RDFPanel();
            id = panel.getID();
        } else if (!panel.isDrawn()) {
            panel = new RDFPanel();
            panel.draw();
            id = panel.getID();
        }

        return panel;
    }


    /**
     * This method returns the id for the panel
     *
     * @return The id for the panel
     */
    public String getID() {
        return id;
    }


    /**
     * This method returns the description of the view factory.
     *
     * @return The description of the view factory.
     */
    public String getDescription() {
        return URI;
    }

}
