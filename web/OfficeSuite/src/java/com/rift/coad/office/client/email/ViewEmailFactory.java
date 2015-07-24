/*
 * OfficeSuite: The office suite.
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
 * ViewEmailFactory.java
 */


// package path
package com.rift.coad.office.client.email;

import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.rift.coad.office.client.IconConstants;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * This object is responsible for providing a view of the emails.
 *
 * @author brett chaldecott
 */
public class ViewEmailFactory implements PanelFactory {

    public class EmailPanel extends ConsolePanel {


        private Window messageWindow = null;

        /**
         * The default constructor
         */
        public EmailPanel() {
        }


        /**
         * This method returns the panel view.
         *
         * @return The reference to the panel.
         */
        @Override
        public Canvas getViewPanel() {
            VLayout layout = new VLayout();
            layout.setWidth100();
            layout.setHeight100();

            Window window = new Window();
            window.setTitle("Email");
            window.setWidth100();
            window.setHeight100();
            window.setCanDragReposition(false);
            window.setCanDragResize(true);
            window.setHeaderControls();

            window.setSrc("/WebMail");
            layout.addMember(window);

            return layout;
        }


        /**
         * This method returns the name of this panel
         *
         * @return The string containing the name of this panel
         */
        @Override
        public String getName() {
            return "Mail";
        }


        /**
         * This method returns the icon path for this console
         *
         * @return The icon path.
         */
        @Override
        public String getIcon() {
            return IconConstants.EMAIL;
        }

    }

    // class singletons
    private static ViewEmailFactory singleton = null;

    // private member variables
    private String id;
    private EmailPanel panel;


    /**
     * This constructor is responsible for
     * @param scriptInfo
     */
    private ViewEmailFactory() {
    }


    /**
     * The reference to the instance singleton.
     * @param scriptInfo The script informaton.
     * @return The reference to the file editor
     */
    public synchronized static ViewEmailFactory getInstance() {
        if (singleton == null) {
            singleton = new ViewEmailFactory();
        }
        return singleton;
    }


    /**
     * This method is responsible for creating a new panel.
     *
     * @return The reference to the newly created canvas.
     */
    public Canvas create() {
        if (panel == null) {
            panel = new EmailPanel();
            id = panel.getID();
        } else if (!panel.isDrawn()) {
            panel = new EmailPanel();
            panel.draw();
            id = panel.getID();
        }

        return panel;
    }


    /**
     * This method returns the reference to the panel.
     *
     * @return The reference to the panel.
     */
    public EmailPanel getPanel() {
       return panel;
    }


    /**
     * This method returns the id of the panel instance.
     *
     * @return The reference to the panel id.
     */
    public String getID() {
        return id;
    }


    /**
     * The description of this factory.
     *
     * @return The string containing the description.
     */
    public String getDescription() {
        return "Email Factory";
    }


}
