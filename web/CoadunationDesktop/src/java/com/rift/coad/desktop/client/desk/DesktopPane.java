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
 * DesktopPane.java
 */

/**
 * 
 */
package com.rift.coad.desktop.client.desk;

import com.google.gwt.user.client.DOM;
import java.util.ArrayList;
import java.util.List;

import org.gwm.client.GDesktopPane;
import org.gwm.client.GFrame;
import org.gwm.client.GInternalFrame;
import org.gwm.client.util.Gwm;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import org.gwm.client.impl.CoadFrame;
import org.gwm.client.impl.CoadInternalFrame;
import org.gwm.client.impl.CoadSelectBoxManagerImpl;
import org.gwm.client.impl.CoadSelectBoxManagerImplIE6;

/**
 * This is the re-implementation of the GWM desktop to work around some problems
 */
public class DesktopPane extends Composite implements GDesktopPane {

    private AbsolutePanel frameContainer;

    private FlexTable desktopWidget;

    private List frames;
    
    private GInternalFrame activeFrame;

    private String theme = Gwm.getDefaultTheme();
    
    private IconBar iconBar = null;

    public DesktopPane() {
        initialize();
        setupUI();
        setupListeners();
        
        //CoadFrame frame = new CoadFrame("test",true); 
        //frameContainer.add(frame);
        //frameContainer.add(DesktopFeed.getInstance());
        //DesktopFeed.getInstance().setTitle("Testing this out.");
        //DesktopFeed.getInstance().setWidth("100%");
        //DOM.setIntStyleAttribute(DesktopFeed.getInstance().getElement(), "zIndex", 1);
        //DOM.setIntStyleAttribute(frame.getElement(), "zIndex", 1);
        /*CoadInternalFrame test = new CoadInternalFrame("test","test");
        test.setUrl("http://www.space.com");
        this.addGFrame(test);
        test.setVisible(true);
        test.setSize(100,200);*/
        
        
    }

    private void initialize() {
        this.frames = new ArrayList();
    }

    private void setupUI() {
        desktopWidget = new FlexTable();
        desktopWidget.setBorderWidth(0);
        desktopWidget.setCellPadding(0);
        desktopWidget.setCellSpacing(0);
        desktopWidget.setSize("100%", "100%");
        frameContainer = new AbsolutePanel();
        frameContainer.setWidth("100%");
        frameContainer.setHeight("100%");
        desktopWidget.getFlexCellFormatter().setHeight(0, 0, "100%");
        desktopWidget.getFlexCellFormatter().setWidth(0, 0, "100%");
        desktopWidget.setWidget(0, 0, frameContainer);
        
        frameContainer.setStyleName("gwm-"+ theme + "-GDesktopPane-FrameContainer");
        
        // force the absolute panel z axis to 0. This is necessary as of firefox 3.
        DOM.setIntStyleAttribute(frameContainer.getElement(), "zIndex", 0);
        
        initWidget(desktopWidget);
        setStyleName("gwm-"+ theme + "-GDesktopPane");
        theme = Gwm.getDefaultTheme();
    }

    private void setupListeners() {

    }
    
    /**
     * This method sets the icon bar.
     * 
     * @param iconBar
     */
    public void setIconBar(IconBar iconBar) {
        this.iconBar = iconBar;
    }
    
    public void iconify(GFrame frame) {
        frame.setVisible(false);
        iconBar.iconify(frame);
    }

    public void deIconify(GFrame frame) {
        frame.restore();
    }
    
    /**
     * Add a GFrame to this GDesktopPane.
     * 
     * @param internalFrame
     */
    public void addGFrame(Widget internalFrame) {
        //internalFrame.setDesktopPane(this);
        int spos = (frames.size() + 1) * 30;
        int left = frameContainer.getAbsoluteLeft() + spos;
        int top = frameContainer.getAbsoluteTop() + spos;
        frameContainer.add(internalFrame);
    }

    /**
     * Add a GFrame to this GDesktopPane.
     * 
     * @param internalFrame
     */
    public void addFrame(GInternalFrame internalFrame) {
        internalFrame.setDesktopPane(this);
        int spos = (frames.size() + 1) * 30;
        int left = frameContainer.getAbsoluteLeft() + spos;
        int top = frameContainer.getAbsoluteTop() + spos;
        CoadSelectBoxManagerImpl selectBoxManager = ((CoadFrame) internalFrame)
                .getSelectBoxManager();
        if (selectBoxManager instanceof CoadSelectBoxManagerImplIE6) {
            frameContainer.add(selectBoxManager.getBlockerWidget(), left, top);
        }
        frameContainer.add((Widget)internalFrame);
        internalFrame.setLocation(left, top);
        frames.add(internalFrame);
        internalFrame.setTheme(theme);
        iconBar.addWindow(internalFrame);
    }
    
    public void removeFrame(GInternalFrame internalFrame) {
        frameContainer.remove((Widget)internalFrame);
        CoadSelectBoxManagerImpl selectBoxManager = ((CoadFrame) internalFrame)
        .getSelectBoxManager();
        if (selectBoxManager instanceof CoadSelectBoxManagerImplIE6) {
            frameContainer.remove(selectBoxManager.getBlockerWidget());
        }
        frames.remove(internalFrame);
        iconBar.removeWindow(internalFrame);
    }

    /**
     * Closes all GInternalFrames contained in this GDesktopPane.
     */
    public void closeAllFrames() {
        for (int i = 0; i < frames.size(); i++) {
            ((GFrame) frames.get(i)).close();
        }
    }

    public List getAllFrames() {
        return frames;
    }

    public void addWidget(Widget widget, int left, int top) {
        frameContainer.remove(widget);
        frameContainer.add(widget);
        frameContainer.setWidgetPosition(widget, left, top);
    }
    
    public void setWidgetPosition(Widget widget, int left, int top) {
        frameContainer.setWidgetPosition(widget, left, top);
    }

    public Widget getFramesContainer() {
        return frameContainer;
    }

    public void setActivateFrame(GInternalFrame internalFrame) {
        activeFrame = internalFrame;
    }

    public GInternalFrame getActiveFrame() {
        return activeFrame;
    }

    public void setTheme(String theme) {
       this.theme = theme;
       for (int x = 0; x < frames.size(); x++) {
           GInternalFrame theFrame = (GInternalFrame) frames.get(x);
           theFrame.setTheme(theme);
       }
       frameContainer.setStyleName("gwm-"+ theme + "-GDesktopPane-FrameContainer");
       setStyleName("gwm-"+ theme + "-GDesktopPane");
    }
    
    /**
     * This method is responsible for setting the background image on the desktop.
     * @param url The string url
     */
    public void setBackgroundImage(DesktopInfo info) {
        //DOM.setIntStyleAttribute(frameContainer.getElement(), "zIndex", 0);
        this.getElement().getStyle().setProperty("backgroundImage", "url(" + info.getBackgroundImage() + ")");
        if (!info.isRepeat()) {
            this.getElement().getStyle().setProperty("backgroundRepeat","no-repeat");
            this.getElement().getStyle().setProperty("backgroundAttachment","fixed");
            this.getElement().getStyle().setProperty("backgroundPosition","center");
        } else {
            this.getElement().getStyle().setProperty("backgroundRepeat","repeat");
            this.getElement().getStyle().setProperty("backgroundAttachment","fixed");
            this.getElement().getStyle().setProperty("backgroundPosition","top left");
        }
    }
    
    
}
