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
 * DesktopEventLauncher.java
 */

// package path
package com.rift.coad.desktop.client.desk.feed;

// The gwt composite import
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
 * This object is responsible for launching the appropriate application to deal with the
 * desktop events.
 * 
 * @author brett chaldecott
 */
public class DesktopEventLauncher extends Composite implements MouseListener, ClickListener {
    // private member variables
    private FeedEvent event = null;
    private VerticalPanel content  = null;
    private HTML eventContent = null;
    
    /**
     * This is the constructor of the desktop event launcher.
     * 
     * @param event The feed event.
     */
    public DesktopEventLauncher(FeedEvent event) {
        this.event = event;
        
        content = new VerticalPanel();
        content.setWidth("600px");
        
        eventContent = new HTML(
                event.getUsername() + "&nbsp;<b>-</b>&nbsp;<b>" 
                + event.getApplication() + "</b>&nbsp;<b>-</b>&nbsp;" + event.getName() 
                + "<br>" +
                event.getDescription());
        eventContent.addClickListener(this);
        eventContent.addMouseListener(this);
        eventContent.setSize("100%", "100%");
        eventContent.setStyleName("Event-MouseOut");
        content.add(eventContent);
        
        this.initWidget(content);
    }
    
    
    /**
     * This method is called when the mouse button is pressed down.
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public void onMouseDown(Widget arg0, int arg1, int arg2) {
        
    }
    
    
    /**
     * This method is called when a mouse enters a block
     * @param arg0
     */
    public void onMouseEnter(Widget arg0) {
        eventContent.setStyleName("Event-MouseOver");
    }

    /**
     * This method is called when the mouse leaves the block
     * @param arg0
     */
    public void onMouseLeave(Widget arg0) {
        eventContent.setStyleName("Event-MouseOut");
    }
    
    /**
     * 
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public void onMouseMove(Widget arg0, int arg1, int arg2) {
        
    }
    
    /**
     * 
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public void onMouseUp(Widget arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * 
     * @param arg0
     */
    public void onClick(Widget arg0) {
        GInternalFrame window = new CoadInternalFrame(event.getApplication(),event.getName());
        window.setUrl(event.getUrl());
        DesktopTabManager.getInstance().getCurrentDesktop().addWindow(window);
        window.setVisible(true);
        window.setSize(event.getWidth(),event.getHeight() );
    }
}
