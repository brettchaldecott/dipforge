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
 * CoadInternalFrame.java
 */
package org.gwm.client.impl;

import org.gwm.client.GDesktopPane;
import org.gwm.client.GDialog;
import org.gwm.client.GInternalFrame;
import org.gwm.client.util.Gwm;
import org.gwm.client.util.GwmUtilities;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * The implementation of a GWM internal frame.
 */
public class CoadInternalFrame extends CoadFrame implements
        GInternalFrame {

    private static final String DEFAULT_TITLE = "GInternalFrame";
    private String inactiveTheme;
    boolean active = false;
    private GDesktopPane desktopPane;
    private String name;
    private String header;
    
    static {
        layerOfTheTopWindow = 1;
    }
    
    public CoadInternalFrame() {
        this(DEFAULT_TITLE);
    }

    public CoadInternalFrame(String header) {
        this(header, false);
        this.inactiveTheme = Gwm.getDefaultTheme() + "-off";
    }

    public CoadInternalFrame(String header, boolean containsApplet) {
        this(null,header,containsApplet);
    }
    
    public CoadInternalFrame(String name, String header) {
        this(name , header, false);
        this.inactiveTheme = Gwm.getDefaultTheme() + "-off";
    }

    public CoadInternalFrame(String name, String header, boolean containsApplet) {
        super(name, containsApplet);
        if (header != null) {
            this.setCaption(name + " - " + header);
        }
        this.name =name;
        this.header = header;
        this.inactiveTheme = Gwm.getDefaultTheme() + "-off";
    }

    
    public void setDesktopPane(GDesktopPane pane) {
        this.desktopPane = pane;
    }

    public GDesktopPane getDesktopPane() {
        return this.desktopPane;
    }

    public void minimize() {
        if (desktopPane == null) {
            throw new IllegalStateException(
                    "This method can be used only if the GInternalFrame has been already attached to the parent Desktop.");
        }
        if (desktopPane != null) {
            desktopPane.iconify(this);
            minimized = true;
            fireFrameMinimized();
        }
    }

    public void maximize() {
        if (desktopPane == null) {
            throw new IllegalStateException(
                    "This method can be used only if the GInternalFrame has been already attached to the parent Desktop.");
        }

        if (maxWidth == 0) {
            this.width = desktopPane.getFramesContainer().getOffsetWidth();
        } else {
            this.width = maxWidth;
        }
        if (maxHeight == 0) {
            this.height = desktopPane.getFramesContainer().getOffsetHeight();
        } else {
            this.height = maxHeight;
        }
        this.previousTop = getAbsoluteTop() - ((Widget) desktopPane).getAbsoluteTop();
        this.previousLeft = getAbsoluteLeft() - ((Widget) desktopPane).getAbsoluteLeft();
        this.previousWidth = getWidth();
        this.previousHeight = getHeight();
        setLocation(0, 0);
        setSize(width, height);
        this.maximized = true;
        fireFrameMaximized();
    }

    public void setLocation(int top, int left) {
        if (desktopPane != null) {
            desktopPane.setWidgetPosition(this, left, top);
            if (selectBoxManager instanceof CoadSelectBoxManagerImplIE6) {
                desktopPane.addWidget(selectBoxManager.getBlockerWidget(), left, top);
            }
            this.top = top;
            this.left = left;
        }
    }

    public void setVisible(boolean visible) {
        if (desktopPane == null) {
            throw new IllegalStateException(
                    "This method can be used only if the GInternalFrame has been already attached to the parent Desktop.");
        }
        if (minimized) {
            throw new IllegalStateException(
                    "The Frame is minimized : use the getDesktopPane().deIconify() intead to restore the frame.");
        }
        super.setVisible(visible);
    }

    public void restore() {
        if (!minimized) {
            super.restore();
            return;
        }
        minimized = false;
        topBar.setRestored();
        setVisible(true);
        fireFrameRestored();
    }

    public void setInactiveTheme(String theme) {
        this.inactiveTheme = theme;
        if (desktopPane != null) {
            if (desktopPane.getActiveFrame() != this) {
                this.currentTheme = theme;
                applyTheme();
            }
        }
    }
    
    /**
     * This method is called to raise the window to the front.
     */
    public void raise() {
        _show();
    }
    
    
    /**
     * This method will make this window the active window on a desktop.
     */
    protected void _show() {
        if (desktopPane.getActiveFrame() != null) {
            GInternalFrame oldActiveFrame = desktopPane.getActiveFrame();
            desktopPane.setActivateFrame(null);
            oldActiveFrame.setTheme(oldActiveFrame.getTheme());
        }
        getDesktopPane().setActivateFrame(this);
        this.setTheme(currentTheme);
        topFrame = this;
        if (containsApplet && GwmUtilities.isFFBrowser()) {
            centerRow.setWidget(0, 1, new HTML(""));
            centerRow.setWidget(0, 1, getContent());

        }
        //selectBoxManager.setBlockerDeepLayer(++layerOfTheTopWindow);
        DOM.setIntStyleAttribute(getElement(), "zIndex", ++layerOfTheTopWindow);
        
        fireFrameSelected();
    }

    public void setTheme(String theme) {
        if (!theme.endsWith("-off")) {
            this.inactiveTheme = theme + "-off";
        }
        if (desktopPane != null) {
            if (getDesktopPane().getActiveFrame() == this) {
                if (theme.endsWith("-off")) {
                    String activeTheme = theme.substring(0, theme.length() - 4);
                    super.setTheme(activeTheme);
                } else {
                    super.setTheme(theme);
                }
            } else {
                super.setTheme(inactiveTheme);
            }
        } else {
            super.setTheme(theme);
        }
    }

    public void close() {
        if (!minimized) {
            super.close();
        }
        if (desktopPane != null) {
            desktopPane.removeFrame(this);
        }

    }
    
    /**
     * This method returns the header for the frame.
     * 
     * @return The string containing the header value.
     */
    public String getHeader() {
        return header;
    }
    
    
    /**
     * This method sets the header value.
     * 
     * @param header The new header value
     */
    public void setHeader(String header) {
        this.header = header;
        if (this.name == null) {
            this.setCaption(header);
        } else {
            this.setCaption(name + "-" + header);
        }
    }
    
    
    /**
     * This method returns the name of the frame.
     * 
     * @return The string containing the name of the frame.
     */
    public String getName() {
        return name;
    }

    
    /**
     * This method sets the name of the frame
     * @param name
     */
    public void setName(String name) {
        this.name = name;
        if (this.name == null) {
            this.setCaption(header);
        } else {
            this.setCaption(name + "-" + header);
        }
    }
    
    
    
}
