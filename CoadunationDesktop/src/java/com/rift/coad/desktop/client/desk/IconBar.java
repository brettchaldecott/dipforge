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
 * DesktopManager.java
 */

// the package path
package com.rift.coad.desktop.client.desk;

// java imports
import com.google.gwt.user.client.Window;
import java.util.Map;
import java.util.HashMap;

// gwt imports
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.Iterator;
import org.gwm.client.GFrame;
import org.gwm.client.GInternalFrame;

// coadunation imports
import org.gwm.client.impl.CoadInternalFrame;



/**
 * This object is responsible for representing the icon bar.
 * 
 * @author brett
 */
public class IconBar  extends Composite {
    
    /**
     * This object represents a window on the desktop.
     */
    public class Icon extends Composite implements MouseListener, ClickListener {
        
        // private member variables
        private GInternalFrame window = null;
        private HorizontalPanel iconPanel = new HorizontalPanel();
        private HTML content = null;
        private boolean iconified = false;
        
        /**
         * The constructor of the icon bar
         * 
         * @param The window being managed by this icon.
         */
        public Icon(GInternalFrame window) {
            this.window = window;
            
            // add the label
            content = new HTML(window.getCaption());
            content.addMouseListener(this);
            content.addClickListener(this);
            content.setStyleName("Icon-Maximized");
            iconPanel.add(content);
            iconPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
            
            // setup the icon panel
            iconPanel.setStyleName("Icon-MouseOut");
            
            this.initWidget(iconPanel);
        }
        
        /**
         * This method listens for the mouse button to be pressed down.
         * @param arg0
         * @param arg1
         * @param arg2
         */
        public void onMouseDown(Widget arg0, int arg1, int arg2) {
            // do nothing
        }
        
        
        /**
         * This method is called when the widget is entered.
         * 
         * @param arg0
         */
        public void onMouseEnter(Widget arg0) {
            iconPanel.setStyleName("Icon-MouseOver");
        }

        /**
         * This method is called on leaving the widget
         * 
         * @param arg0
         */
        public void onMouseLeave(Widget arg0) {
            iconPanel.setStyleName("Icon-MouseOut");
        }
        
        
        /**
         * This method is called when the mouse moves.
         * @param arg0
         * @param arg1
         * @param arg2
         */
        public void onMouseMove(Widget arg0, int arg1, int arg2) {
            
        }
        
        /**
         * This button is called when 
         * @param arg0
         * @param arg1
         * @param arg2
         */
        public void onMouseUp(Widget arg0, int arg1, int arg2) {
            
        }

        public void onClick(Widget arg0) {
            if (iconified) {
                restore();
            } else {
                ((CoadInternalFrame)window).raise();
            }
        }
        
        /**
         * This method is called to nofify the icon of the fact that the window 
         * has been minimized
         */
        public void iconify() {
            iconified = true;
            content.setStyleName("Icon-Minimized");
        }
        
        /**
         * This method is called to restore the window
         */
        public void restore() {
            iconified = false;
            desktop.deIconify(window);
            content.setStyleName("Icon-Maximized");
        }
    }
    
     /**
     * This object represents a window on the desktop.
     */
    public class DesktopWidget extends Composite implements MouseListener, ClickListener {
        
        // private member variables
        private HorizontalPanel iconPanel = new HorizontalPanel();
        private HTML content = null;
        
        /**
         * The constructor of the icon bar
         * 
         * @param The window being managed by this icon.
         */
        public DesktopWidget() {
            // add the label
            content = new HTML("<img src='/FileManager/dlf/var/desktop/icons/user-desktop.png' " +
                    "style='vertical-align:middle' border=0 width=20 height=20/>");
            content.addMouseListener(this);
            content.addClickListener(this);
            content.setStyleName("Icon-Maximized");
            content.setHeight("20px");
            
            iconPanel.add(content);
            iconPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
            iconPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
            // setup the icon panel
            iconPanel.setStyleName("Icon-MouseOut");
            
            this.initWidget(iconPanel);
        }
        
        /**
         * This method listens for the mouse button to be pressed down.
         * @param arg0
         * @param arg1
         * @param arg2
         */
        public void onMouseDown(Widget arg0, int arg1, int arg2) {
            // do nothing
        }
        
        
        /**
         * This method is called when the widget is entered.
         * 
         * @param arg0
         */
        public void onMouseEnter(Widget arg0) {
            iconPanel.setStyleName("Icon-MouseOver");
        }

        /**
         * This method is called on leaving the widget
         * 
         * @param arg0
         */
        public void onMouseLeave(Widget arg0) {
            iconPanel.setStyleName("Icon-MouseOut");
        }
        
        
        /**
         * This method is called when the mouse moves.
         * @param arg0
         * @param arg1
         * @param arg2
         */
        public void onMouseMove(Widget arg0, int arg1, int arg2) {
            
        }
        
        /**
         * This button is called when 
         * @param arg0
         * @param arg1
         * @param arg2
         */
        public void onMouseUp(Widget arg0, int arg1, int arg2) {
            
        }

        public void onClick(Widget arg0) {
                iconify();
        }
        
        /**
         * This method is called to nofify the icon of the fact that the window 
         * has been minimized
         */
        public void iconify() {
            for (Iterator iter = windows.keySet().iterator(); iter.hasNext();) {
                GInternalFrame frame = (GInternalFrame)iter.next();
                if (!frame.isMinimized()) {
                    frame.minimize();
                }
            }
        }
    }
    
    
    // private member variables
    private HorizontalPanel bottomPanel = new HorizontalPanel();
    private DesktopPane desktop = null;
    private Map windows = new HashMap(); 
    
    /**
     * The constructor of the icon bar. 
     */
    public IconBar(DesktopPane desktop) {
        this.desktop = desktop;
        
        // setup the bottom panel
        //bottomPanel.setStyleName("BottomPanel");
        //bottomPanel.setWidth("100%");
        bottomPanel.add(new Label(""));
        HorizontalPanel wrapperPanel = new HorizontalPanel();
        wrapperPanel.setWidth("100%");
        wrapperPanel.setStyleName("BottomPanel");
        wrapperPanel.add(bottomPanel);
        
        // setup the panel border
        VerticalPanel panel = new VerticalPanel();
        
        // this must be enabled at a later date so that proper borders can be put in place
        // but due to complications with IE I am current using the CSS border of the bottom panel
        // and icon bar to make the divide. This is nasty but effective.
        //HorizontalPanel panelBorder = new HorizontalPanel();
        //Label borderLabel = new Label("");
        //borderLabel.setStyleName("BottomPanel-Border");
        //panelBorder.setStyleName("BottomPanel-Border");
        //panelBorder.add(borderLabel);
        //panelBorder.setWidth("100%");
        //panel.add(panelBorder);
        panel.add(wrapperPanel);
        panel.setWidth("100%");
        panel.setStyleName("IconPanel");
        
        initWidget(panel);
        
        this.bottomPanel.add(new DesktopWidget());
    }
    
    
    /**
     * This method is responsible for adding a new icon window.
     */
    public void addWindow(GInternalFrame window) {
        Icon icon = new Icon(window);
        windows.put(window, icon);
        this.bottomPanel.add(icon);
    }
    
    
    /**
     * This method is responsible for setting the iconify state of an icon iconfied.
     * 
     * @param window The window being iconfified.
     */
    public void iconify(GFrame frame) {
        Icon icon = (Icon)windows.get(frame);
        icon.iconify();
    }
    
    
    /**
     * This method is called to remove the entry from the tool bar.
     * 
     * @param internalFrame This method is called to remove the window.
     */
    public void removeWindow(GInternalFrame internalFrame) {
        Icon icon = (Icon)windows.remove(internalFrame);
        this.bottomPanel.remove(icon);
        
    }
}
