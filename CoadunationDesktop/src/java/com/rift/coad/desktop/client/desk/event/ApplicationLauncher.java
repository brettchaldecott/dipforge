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
 * EventPanel.java
 */

package com.rift.coad.desktop.client.desk.event;

import com.rift.coad.desktop.client.desk.MimeType;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.rift.coad.desktop.client.desk.DesktopTabManager;
import org.gwm.client.GInternalFrame;
import org.gwm.client.impl.CoadInternalFrame;

/**
 * This object represents the event launcher.
 * 
 * @author brett
 */
public class ApplicationLauncher  extends Composite implements MouseListener, ClickListener {
    
    // private member variables
    private MimeType type = null;
    private int eventNum = 0;
    private VerticalPanel panel = null;
    private HTML content = null;
    
    /**
     * The constructor of the application launcher.
     *
     * @param type The mime type for this application.
     * @param eventNum The number of events that have occurred for the application.
     */
    public ApplicationLauncher(MimeType type) {
        this.type = type;
        this.eventNum = 0;
        
        // master panel
        panel = new VerticalPanel();
        panel.setWidth("100%");
        panel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        
        // html
        content = null;
        if (type.getIcon().length() == 0) {
            content = new HTML(type.getName());
        } else {
            content = new HTML(
                "<img src=\""  + type.getIcon() + "\" border=0/>" + type.getName());
        }
        content.addMouseListener(this);
        content.addClickListener(this);
        content.setStyleName("Application-Event-MouseOut");
        panel.add(content);
        
        this.initWidget(panel);

    }
    
    
    /**
     * The constructor of the application launcher.
     *
     * @param type The mime type for this application.
     * @param eventNum The number of events that have occurred for the application.
     */
    public ApplicationLauncher(MimeType type, int eventNum) {
        this.type = type;
        this.eventNum = eventNum;
        
        // master panel
        panel = new VerticalPanel();
        panel.setWidth("100%");
        panel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        
        // html
        content = null;
        if (type.getIcon().length() == 0) {
            content = new HTML(
                "(" + eventNum + ")" + type.getName());
        } else {
            content = new HTML(
                "<img src=\""  + type.getIcon() + "\" border=0/>(" + eventNum + ")" + type.getName());
        }
        content.addMouseListener(this);
        content.addClickListener(this);
        content.setStyleName("Application-Event-MouseOut");
        panel.add(content);
        
        this.initWidget(panel);

    }

    
    /**
     * This method returns the number of events.
     * @return The integer value.
     */
    public int getEventNum() {
        return eventNum;
    }
    
    
    /**
     * The setter for the event number.
     * @param eventNum The new event number value.
     */
    public void setEventNum(int eventNum) {
        this.eventNum = eventNum;
        content = null;
        if (type.getIcon().length() == 0) {
            content = new HTML(
                "(" + eventNum + ")" + type.getName());
        } else {
            content = new HTML(
                "<img src=\""  + type.getIcon() + "\" border=0/>(" + eventNum + ")" + type.getName());
        }
        panel.clear();
        content.addMouseListener(this);
        content.addClickListener(this);
        content.setStyleName("Application-Event-MouseOut");
        panel.add(content);
        
    }
    
    
    public void onMouseDown(Widget arg0, int arg1, int arg2) {
        // ignore
    }

    public void onMouseEnter(Widget arg0) {
        content.setStyleName("Application-Event-MouseOver");
    }

    public void onMouseLeave(Widget arg0) {
        content.setStyleName("Application-Event-MouseOut");
    }

    public void onMouseMove(Widget arg0, int arg1, int arg2) {
        // ignore
    }

    public void onMouseUp(Widget arg0, int arg1, int arg2) {
        // ignore
    }
    
    public void onClick(Widget arg0) {
        GInternalFrame window = new CoadInternalFrame(this.type.getName(),null);
        window.setUrl(this.type.getUrl());
        DesktopTabManager.getInstance().getCurrentDesktop().addWindow(window);
        window.setVisible(true);
        window.setSize(this.type.getWidth(),this.type.getHeight());
    }

}
