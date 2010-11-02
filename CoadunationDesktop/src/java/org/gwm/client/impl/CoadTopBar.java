/*
 * Copyright (c) 2006-2007 Luciano Broussal <luciano.broussal AT gmail.com>
 * (http://www.gwtwindowmanager.org)
 * 
 * Main Contributors :
 *      Johan Vos,Andy Scholz,Marcelo Emanoel  
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gwm.client.impl;

import org.gwm.client.GInternalFrame;
import org.gwm.client.event.GFrameAdapter;
import org.gwm.client.event.GFrameEvent;
import org.gwm.client.util.Gwm;
import org.gwm.client.util.GwmUtilities;
import org.gwm.client.util.widget.OverlayLayer;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class CoadTopBar extends FlexTable implements MouseListener, ClickListener {

    protected OverlayLayer fixPanelForFrameWithURL;

    private HTML caption;

    private String currentTheme;

    private String title;

    protected CoadFrame parent;

    protected Label closeArea;

    protected Label maximizeArea;

    protected Label minimizeArea;

    protected int dragStartX, dragStartY;

    protected boolean dragging;

    protected boolean draggable;

    protected Label restoreButton;

    protected boolean dragStarted;

    protected boolean minimized = false;

    protected OutlinePanel outline;
    
    CoadTopBar() {
        super();
    }

    public void init(CoadFrame frame) {
        this.parent = frame;
        this.draggable = true;
        buildGui();
        initListeners();
        setMovingGuard();
    }

    private void buildGui() {
        this.currentTheme = parent.getTheme();
        this.title = parent.getCaption();
        caption = new HTML(title);
        DOM.setStyleAttribute(caption.getElement(), "whiteSpace", "nowrap");
        closeArea = new Label();
        closeArea.addClickListener(this);
        closeArea.addMouseListener(new FocusAction("CloseButton"));
        minimizeArea = new Label();
        minimizeArea.addClickListener(this);
        minimizeArea.addMouseListener(new FocusAction("MinimizeButton"));
        maximizeArea = new Label();
        maximizeArea.addClickListener(this);
        maximizeArea.addMouseListener(new FocusAction("MaximizeButton"));
        restoreButton = new Label("");
        restoreButton.setStyleName("gwm-" + this.currentTheme
                + "-TopBar-RestoreButton");

        setWidget(0, 0, caption);
        getCellFormatter().setWidth(0, 0, "100%");
        setWidget(0, 1, minimizeArea);
        setWidget(0, 2, maximizeArea);
        setWidget(0, 3, closeArea);
        setTheme(currentTheme);
        setCellPadding(0);
        setCellSpacing(0);
        setStyleName(getItemTheme("Frame-TopBar"));
        addStyleName("topBar");
        updateTopBar();
    }

    public void onClick(Widget w) {
        if (w.equals(closeArea)) {
            parent.close();
        }
        if (w.equals(maximizeArea)) {
            if (parent.isMaximized()) {
                parent.restore();
                updateTopBar();
            } else {
                parent.maximize();
                if (parent.isMinimizable()) {
                    minimizeArea.setVisible(false);
                }

            }
        }
        if (w.equals(minimizeArea)) {
            if (parent.isMinimized()) {
                minimized = false;
                parent.restore();
            } else {
                minimized = true;
                parent.minimize();
            }
        }
    }

    public void onMouseDown(Widget sender, int x, int y) {
        if (draggable) {
            dragStarted = true;
            DOM.addEventPreview(parent);
            DOM.setCapture(caption.getElement());
            dragStartX = x;
            dragStartY = y;
            if (Gwm.isOverlayLayerDisplayOnDragAction()) {
                fixPanelForFrameWithURL = new OverlayLayer();
                fixPanelForFrameWithURL.show(parent.getTheme());
            }
            if (parent.isDragOutline()) {
                outline = new OutlinePanel();
                outline.setVisible(false);
                if (parent instanceof GInternalFrame) {
                    ((GInternalFrame) parent).getDesktopPane()
                            .addWidget(outline, 0, 0);
                } else {
                    outline = new OutlinePanel();
                    RootPanel.get().add(outline);
                }
                outline.setSize(parent.getWidth() + "px", parent.getHeight()
                        + "px");
                outline.setTop(parent.getTop());
                outline.setLeft(parent.getLeft());
                outline.setDeep(DefaultGFrame.getLayerOfTheTopWindow() + 50);
                outline.setVisible(true);
            } else {
                // DOM.setStyleAttribute(parent.getElement(), "filter",
                // "progid:DXImageTransform.Microsoft.Alpha(opacity=80)");
                // DOM
                // .setStyleAttribute(parent.getElement(), "-mozOpacity",
                // "0.9");
                // DOM.setStyleAttribute(parent.getElement(), "opacity", "0.8");
                parent.getSelectBoxManager().onParentDragStart(parent);
            }
        }
    }

    public void onMouseEnter(Widget sender) {
    }

    public void onMouseLeave(Widget sender) {
    }

    public void onMouseMove(Widget sender, int x, int y) {
        if (dragStartX == x && dragStartY == y) {
            return;
        } else if (dragStarted && !dragging) {
            dragging = true;
            if (!parent.isDragOutline()) {
                GwmUtilities.hideSelect();
            }
        }
        if (dragging) {
            int absX = x + parent.getLeft();
            int absY = y + parent.getTop();
            
            if (parent.isDragOutline()) {
                outline.setTop(absY - dragStartY);
                outline.setLeft(absX - dragStartX);
                parent.fireGhostMoving(absY - dragStartY, absX - dragStartX);
            } else {
                parent.setLocation(absY - dragStartY, absX - dragStartX);
                parent.fireFrameMoving();
            }

        }
    }

    public void onMouseUp(Widget sender, int x, int y) {
        if (dragging || dragStarted) {
            if (Gwm.isOverlayLayerDisplayOnDragAction()) {
                fixPanelForFrameWithURL.hide();
            }
            DOM.releaseCapture(caption.getElement());
            DOM.removeEventPreview(parent);
            int absX = x + parent.getLeft();
            int absY = y + parent.getTop();

            parent.setLocation(absY - dragStartY, absX - dragStartX);
            if (parent.isDragOutline()) {
                if(parent instanceof GInternalFrame){
                    outline.removeFromParent();
                }else{
                    RootPanel.get().remove(outline);
                }
                parent.fireGhostMoved(absY - dragStartY, absX - dragStartX);
            } else {
                // TODO BLOCKER
                // parent.getSelectBoxManager().setBlockerVisible(true);
                // DOM.setStyleAttribute(parent.getElement(), "filter",
                // "progid:DXImageTransform.Microsoft.Alpha(opacity=100)");
                // DOM.setStyleAttribute(parent.getElement(), "-mozOpacity",
                // "1");
                // DOM.setStyleAttribute(parent.getElement(), "opacity", "1");
                parent.getSelectBoxManager().onParentDragEnd(parent);
            }
            if (dragging)
                parent.fireFrameMoved();

            dragging = false;
            dragStarted = false;
            if (!parent.isDragOutline()) {
                GwmUtilities.showSelect();
            }

        }
    }

    public void setCaption(String caption) {
        this.title = caption;
        this.caption.setText(caption);
    }

    public void setIconified() {
        clear();
        if (parent instanceof GInternalFrame) {
            caption.setStyleName(getItemTheme("Frame-TopBar-minimized"));
            restoreButton
                    .setStyleName(getItemTheme("Frame-TopBar-RestoreButton"));
        } else {
            caption.setStyleName(getItemTheme("Frame-TopBar"));
            restoreButton
                    .setStyleName(getItemTheme("Frame-TopBar-MaximizeButton"));
            addStyleName("topBar");
        }
        setWidget(0, 0, caption);
        getFlexCellFormatter().setWidth(0, 0, "100%");
        setWidget(0, 1, restoreButton);
        // OPtion to ajust to parent width :)
        // setWidth(parent.getWidth() +"px");
        parent.getSelectBoxManager().setBlockerSize(getOffsetWidth() + 5,
                getOffsetHeight() + 3);
    }

    public void setRestored() {
        minimized = false;
        if (parent instanceof GInternalFrame)
            return;
        clear();
        buildGui();
        initListeners();
        parent.restore();
    }

    public void updateTopBar() {
        maximizeArea.setVisible(parent.isMaximizable());
        minimizeArea.setVisible(parent.isMinimizable());
        closeArea.setVisible(parent.isCloseable());

    }

    public void setCaption(HTML caption) {
        this.caption = caption;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public void setDragStartX(int dragStartX) {
        this.dragStartX = dragStartX;
    }

    public void setDragStartY(int dragStartY) {
        this.dragStartY = dragStartY;
    }

    public void setParent(CoadFrame parent) {
        this.parent = parent;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setTheme(String theme) {
        currentTheme = theme;
        if (!minimized) {
            caption.setStyleName(getItemTheme("FrameCaption"));
            caption.addStyleName("topBar");
            closeArea.setStyleName(getItemTheme("Frame-TopBar-CloseButton"));
            minimizeArea
                    .setStyleName(getItemTheme("Frame-TopBar-MinimizeButton"));
            maximizeArea
                    .setStyleName(getItemTheme("Frame-TopBar-MaximizeButton"));
            setStyleName(getItemTheme("Frame-TopBar"));
        } else {
            if (parent instanceof GInternalFrame) {
                caption.setStyleName(getItemTheme("Frame-TopBar-minimized"));
                restoreButton
                        .setStyleName(getItemTheme("Frame-TopBar-RestoreButton"));
            } else {
                caption.setStyleName(getItemTheme("Frame-TopBar"));
                restoreButton
                        .setStyleName(getItemTheme("Frame-TopBar-MaximizeButton"));
            }
        }
    }

    private void setMovingGuard() {
        parent.addFrameListener(new GFrameAdapter() {

            public void frameMoved(GFrameEvent evt) {

                if (evt.getGFrame() instanceof GInternalFrame) {
                    GInternalFrame internalFrame = (GInternalFrame) evt
                            .getGFrame();
                    Widget desktopPane = (Widget) internalFrame
                            .getDesktopPane();
                    if (internalFrame.getTop() < 0) {
                        internalFrame.setTop(0);
                    }
                    if (internalFrame.getTop() > desktopPane.getOffsetHeight() - 40) {
                        internalFrame
                                .setTop(desktopPane.getOffsetHeight() - 100);
                    }
                    if (internalFrame.getLeft() + internalFrame.getWidth() < 0) {
                        internalFrame.setLeft(-internalFrame.getWidth() + 100);
                    }
                    if (internalFrame.getLeft() > desktopPane.getOffsetWidth()) {
                        internalFrame
                                .setLeft(desktopPane.getOffsetWidth() - 20);
                    }

                } else {
                    if (evt.getGFrame().getTop() < 0) {
                        evt.getGFrame().setTop(0);
                    }
                    if (evt.getGFrame().getTop() > Window.getClientHeight()) {
                        evt.getGFrame().setTop(Window.getClientHeight() - 20);
                    }
                    if (evt.getGFrame().getLeft() + evt.getGFrame().getWidth() < 0) {
                        evt.getGFrame().setLeft(
                                -evt.getGFrame().getWidth() + 100);
                    }
                    if (evt.getGFrame().getLeft() > Window.getClientWidth()) {
                        evt.getGFrame().setLeft(Window.getClientWidth() - 20);
                    }
                }
            }

        });
    }

    protected void initListeners() {
        caption.addMouseListener(this);
        restoreButton.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                setRestored();
            }
        });
    }

    class FocusAction extends MouseListenerAdapter {
        private String action;

        FocusAction(String action) {
            this.action = action;
        }

        public void onMouseEnter(Widget target) {
            target.setStyleName("gwm-" + currentTheme + "-Frame-TopBar-"
                    + action + "-active");
        }

        public void onMouseLeave(Widget target) {
            target.setStyleName("gwm-" + currentTheme + "-Frame-TopBar-"
                    + action);
        }

    }

    private String getItemTheme(String item) {
        return "gwm-" + currentTheme + "-" + item;
    }
    
   
}

