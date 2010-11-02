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

import java.util.ArrayList;
import java.util.List;

import org.gwm.client.GDialog;
import org.gwm.client.GFrame;
import org.gwm.client.GInternalFrame;
import org.gwm.client.event.GFrameEvent;
import org.gwm.client.event.GFrameListener;
import org.gwm.client.util.Gwm;
import org.gwm.client.util.GwmUtilities;
import org.gwm.client.util.widget.OverlayLayer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.components.client.Effects;
import com.gwt.components.client.Effects.Effect;

/**
 * The GWT default implementation of {@link GDialog}
 * 
 */
public class CoadFrame extends SimplePanel implements GFrame, EventPreview {

    protected CoadSelectBoxManagerImpl selectBoxManager;

    protected static int layerOfTheTopWindow;

    protected static GFrame topFrame;

    private String title;

    protected CoadTopBar topBar;

    private CoadResizeImage resizeImage;

    private Widget myContent;

    private String url;

    private boolean visible;

    private static final int DEFAULT_WIDTH = 200;

    private static final int DEFAULT_HEIGHT = 40;

    private static final String DEFAULT_TITLE = "GFrame";

    protected int maxWidth, maxHeight;

    private int minWidth, minHeight;

    protected int width = -1;

    protected int height = -1;

    protected String currentTheme;

    protected String theme;

    private boolean closable, maximizable, minimizable, resizable;

    protected boolean maximized, minimized;

    private Label imgTopLeft;

    private Label imgTopRight;

    private HTML imgBot;

    protected int previousWidth;

    protected int previousHeight;

    protected int previousTop;

    protected int previousLeft;

    private List listeners;

    protected int left;

    protected int top;

    private Widget frame;

    private FlexTable ui;

    private FlexTable topRow;

    protected FlexTable centerRow;

    private FlexTable bottomRow;

    private boolean freeminimized;

    private boolean modalMode;

    private boolean outlineDragMode;

    private static OverlayLayer overlayLayer = new OverlayLayer();

    private boolean closed = false;

    private Label centerLeftLabel;

    private Label centerRightLabel;

    private OutlinePanel outLine;

    private boolean minimizing = false;

    protected boolean containsApplet;

    public CoadFrame() {
        this(DEFAULT_TITLE);
    }

  public CoadFrame(String caption, boolean containsApplet) {
        selectBoxManager = (CoadSelectBoxManagerImpl) GWT
                .create(CoadSelectBoxManagerImpl.class);
        // selectBoxManager = new CoadunationSelectBoxManagerImpl();
        this.containsApplet = containsApplet;

        if (GwmUtilities.isFFBrowser()) {
            if (Gwm.isAppletCompliant() && !containsApplet) {
                selectBoxManager = new CoadSelectBoxManagerImplIE6();
            } else {
                selectBoxManager = (CoadSelectBoxManagerImpl) GWT
                        .create(CoadSelectBoxManagerImpl.class);
            }
        }else{
        	selectBoxManager = (CoadSelectBoxManagerImpl) GWT
                .create(CoadSelectBoxManagerImpl.class);
        }

        this.theme = Gwm.getDefaultTheme();
        this.currentTheme = theme;
        this.title = caption;
        this.myContent = new HTML("");
        this.closable = true;
        this.minimizable = true;
        this.maximizable = true;
        this.resizable = true;
        this.outlineDragMode = true;
        initializeFrame();
        buildGui();
        sinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
        super.setVisible(false);
        RootPanel.get().add(this);
        addFrameListener(selectBoxManager.getFrameListener());

    }

    public CoadFrame(String caption) {
        this(caption, false);
    }

    private void initializeFrame() {
        outLine = new OutlinePanel();
        outLine.setVisible(false);
        RootPanel.get().add(outLine);
        ui = new FlexTable();
        topRow = new FlexTable();
        centerRow = new FlexTable();
        bottomRow = new FlexTable();
        listeners = new ArrayList();
        resizeImage = new CoadResizeImage(this);
        topBar = (CoadTopBar) GWT.create(CoadTopBar.class);
        topBar.init(this);
        imgTopLeft = new Label();
        imgTopRight = new Label();
        imgBot = new HTML("&nbsp;");
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        DOM.setStyleAttribute(getElement(), "position", "absolute");

    }

    protected void buildGui() {
        this.ui = new FlexTable();
        if (freeminimized) {
            this.ui.setWidget(0, 0, topBar);
            super.setWidget(ui);
            return;
        }
        if (this.width < this.minWidth) {
            this.width = this.minWidth;
        }
        if (this.height < this.minHeight) {
            this.height = this.minHeight;
        }
        setSize(this.width, this.height);
        topRow.setWidget(0, 0, imgTopLeft);
        topRow.setWidget(0, 1, topBar);
        topRow.setWidget(0, 2, imgTopRight);
        bottomRow.setHTML(0, 0, "&nbsp;");
        bottomRow.setWidget(0, 1, imgBot);
        bottomRow.setHTML(0, 2, "&nbsp;");

        centerLeftLabel = new Label();
        if (url != null) {
            setUrl(url);
        }
        centerRow.setWidget(0, 0, centerLeftLabel);
        centerRow.setWidget(0, 1, myContent);
        centerRow.getFlexCellFormatter().setHorizontalAlignment(0, 1,
                HasHorizontalAlignment.ALIGN_CENTER);
        centerRightLabel = new Label();
        centerRow.setWidget(0, 2, centerRightLabel);
        setResizable(resizable);

        ui.getCellFormatter().setHeight(1, 0, "100%");
        ui.getCellFormatter().setWidth(1, 0, "100%");
        ui.getCellFormatter().setAlignment(1, 0,
                HasHorizontalAlignment.ALIGN_CENTER,
                HasVerticalAlignment.ALIGN_MIDDLE);
        ui.setCellPadding(0);
        ui.setCellSpacing(0);
        ui.setWidget(0, 0, topRow);
        ui.setWidget(1, 0, centerRow);
        ui.setWidget(2, 0, bottomRow);
        super.setWidget(ui);
        setTheme(currentTheme);

        topRow.setCellPadding(0);
        topRow.setCellSpacing(0);
        topRow.setHeight("100%");
        topRow.getCellFormatter().setWidth(0, 1, "100%");
        centerRow.setCellPadding(0);
        centerRow.setCellSpacing(0);
        centerRow.setWidth("100%");
        centerRow.setHeight("100%");
        centerRow.setBorderWidth(0);

        bottomRow.setCellPadding(0);
        bottomRow.setCellSpacing(0);
        bottomRow.setWidth("100%");
        // bottomRow.getCellFormatter().setWidth(0, 1, "100%");
        if (visible) {
            setSize(getOffsetWidth(), getOffsetHeight());
        }
    }

    public void setTheme(String theme) {
        this.theme = theme;
        this.currentTheme = theme;
        applyTheme();
    }

    protected void applyTheme() {
        topBar.setTheme(currentTheme);
        resizeImage.setTheme(currentTheme);
        imgTopLeft.setStyleName(getItemTheme("FrameBorder-tl"));
        imgTopRight.setStyleName(getItemTheme("FrameBorder-tr"));
        bottomRow.getCellFormatter().setStyleName(0, 0,
                getItemTheme("FrameBorder-bl"));
        imgBot.setStyleName(getItemTheme("FrameBorder-b"));
        bottomRow.getCellFormatter().setStyleName(0, 1,
                getItemTheme("FrameBorder-b"));
        topRow.getCellFormatter().setStyleName(0, 1,
                getItemTheme("FrameBorder-t"));
        centerRow.getCellFormatter().setStyleName(0, 1,
                getItemTheme("FrameContent"));
        myContent.setStyleName(getItemTheme("FrameContent"));
        centerRow.getCellFormatter().setStyleName(0, 0,
                getItemTheme("FrameBorder-l"));
        centerRow.getCellFormatter().setStyleName(0, 2,
                getItemTheme("FrameBorder-r"));
        centerLeftLabel.setStyleName(getItemTheme("FrameBorder-l"));
        centerRightLabel.setStyleName(getItemTheme("FrameBorder-r"));
        topRow.getCellFormatter().setVerticalAlignment(0, 0,
                HasVerticalAlignment.ALIGN_BOTTOM);
        topRow.getCellFormatter().setVerticalAlignment(0, 2,
                HasVerticalAlignment.ALIGN_BOTTOM);

        bottomRow.getCellFormatter().setVerticalAlignment(0, 0,
                HasVerticalAlignment.ALIGN_TOP);
        bottomRow.getCellFormatter().setVerticalAlignment(0, 2,
                HasVerticalAlignment.ALIGN_TOP);
        if (resizable) {
            resizeImage.setTheme(currentTheme);
            bottomRow.setWidget(0, 2, resizeImage);
            bottomRow.getCellFormatter().setStyleName(0, 2,
                    getItemTheme("FrameBorder-br"));
        } else {
            bottomRow.getCellFormatter().setStyleName(0, 2,
                    getItemTheme("FrameBorder-br"));
        }

    }

    public String getTheme() {
        return this.currentTheme;
    }

    public void setContent(Widget widget) {
        myContent = widget;
        this.url = null;
        buildGui();
    }

    public void setContent(String content) {
        myContent = new HTML(content);
        this.url = null;
        buildGui();
    }

    public void minimize() {
        minimizing = true;
        this.previousTop = getAbsoluteTop();
        this.previousLeft = getAbsoluteLeft();
        this.previousWidth = getWidth();
        this.previousHeight = getHeight();

        outLine.setSize(width + "px", height + "px");
        outLine.setTop(top);
        outLine.setLeft(left);
        //outLine.setDeep(layerOfTheTopWindow + 50);
        outLine.setVisible(true);
        setVisible(false);
        Effects.Effect("Fade", outLine, "{duration:0.3}").addEffectListener(
                new Effects.EffectListenerAdapter() {
                    public void onAfterFinish(Effect sender) {
                        setVisible(true);
                        topBar.setIconified();
                        freeminimized = true;
                        centerRow.setVisible(false);
                        bottomRow.setVisible(false);
                        myContent.setVisible(false);
                        // buildGui();
                        setSize(topBar.getOffsetWidth() + 5, topBar
                                .getOffsetHeight() + 3);
                        minimized = true;
                        fireFrameMinimized();
                    }
                });

    }

    public void maximize() {

        if (maxWidth == 0) {
            this.width = Window.getClientWidth();
        } else {
            this.width = maxWidth;
        }
        if (maxHeight == 0) {
            this.height = Window.getClientHeight();
        } else {
            this.height = maxHeight;
        }
        this.previousTop = getAbsoluteTop();
        this.previousLeft = getAbsoluteLeft();
        this.previousWidth = getWidth();
        this.previousHeight = getHeight();

        setLocation(0, 0);
        setSize(width, height);
        this.maximized = true;
        fireFrameMaximized();
    }

    public void restore() {
        this.width = previousWidth;
        this.height = previousHeight;
        this.maximized = false;
        this.minimized = false;
        this.freeminimized = false;
        setLocation(this.previousTop, this.previousLeft);
        setSize(width, height);
        centerRow.setVisible(true);
        bottomRow.setVisible(true);
        if (!getContent().isVisible()) {
            getContent().setVisible(true);

        }
        selectBoxManager.setBlockerVisible(true);
        selectBoxManager.setBlockerSize(width, height);
        fireFrameRestored();
    }

    public void close() {
        selectBoxManager.setBlockerVisible(false);
        outLine.setSize(getWidth() + "px", getHeight() + "px");
        //outLine.setDeep(layerOfTheTopWindow + 50);
        if (this instanceof GInternalFrame) {
            ((GInternalFrame) this).getDesktopPane().addWidget(outLine,
                    0, 0);
        } else {
            RootPanel.get().add(outLine);
        }
        outLine.setTop(top);
        outLine.setLeft(left);
        outLine.setVisible(true);
        setVisible(false);
        Effects.Effect("BlindUp", outLine,
                "{duration : 0.6, scaleFromCenter: true}").addEffectListener(
                new Effects.EffectListenerAdapter() {
                    public void onAfterFinish(Effect sender) {
                        removeFromParent();
                        outLine.removeFromParent();
                        selectBoxManager.removeBlocker();
                        closed = true;
                        if (modalMode) {
                            overlayLayer.hide();
                        }
                        fireFrameClosed();
                    }
                });
    }

    public boolean isMinimized() {
        return this.minimized;
    }

    public boolean isDraggable() {
        return topBar.isDraggable();
    }

    public boolean isMaximized() {
        return this.maximized;
    }

    public void setLocation(int top, int left) {

        Element elem = getElement();
        DOM.setStyleAttribute(elem, "left", left + "px");
        DOM.setStyleAttribute(elem, "top", top + "px");

        // selectBoxManager.setLocation(top, left, this);

        this.left = left;
        this.top = top;

    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        ui.setSize(width + "px", height + "px");
        selectBoxManager.setBlockerSize(width, height);
    }

    public void setWidth(int width) {
        setSize(width, height);
    }

    public int getWidth() {
        int widthResult = 0;
        if (getOffsetWidth() > 0) {
            widthResult = getOffsetWidth();
            return widthResult;
        }
        try {
            String widthStr = DOM.getStyleAttribute(ui.getElement(), "width");
            widthStr = widthStr.replaceAll("px", "");
            int width = Integer.parseInt(widthStr);
            return width;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setHeight(int height) {
        setSize(width, height);
    }

    public int getHeight() {
        if (getOffsetHeight() > 0)
            return getOffsetHeight();
        try {
            String heightStr = DOM.getStyleAttribute(ui.getElement(), "height");
            heightStr = heightStr.replaceAll("px", "");
            int height = Integer.parseInt(heightStr);
            return height;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setMinimumWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public int getMinimumWidth() {
        return this.minWidth;
    }

    public void setMinimumHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getMinimumHeight() {
        return this.minHeight;
    }

    public void setMaximumWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMaximumHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setTop(int top) {
        setLocation(top, left);
    }

    public void setLeft(int left) {
        setLocation(top, left);
    }

    public void setCaption(String title) {
        this.title = title;
        this.topBar.setCaption(title);
    }

    public String getCaption() {
        return this.title;
    }

    public void setUrl(String url) {
        this.url = url;
        myContent = getFrame();
    }
    
    public String getUrl() {
        return url;
    }

    private Widget getFrame() {
        if (frame == null) {
            frame = new HTML("<iframe src='" + url
                    + "' width=100% height=100% frameborder=0></iframe>");
            frame.setWidth("100%");
            frame.setHeight("100%");
            centerRow.setWidget(0, 1, frame);
        }
        return frame;

    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
        if (resizable) {
            bottomRow.setWidget(0, 2, resizeImage);
            bottomRow.getCellFormatter().setStyleName(0, 2,
                    getItemTheme("FrameBorder-br"));
            resizeImage.setTheme(currentTheme);
        } else {
            bottomRow.setHTML(0, 2, "&nbsp;");
            bottomRow.getCellFormatter().setStyleName(0, 2,
                    getItemTheme("FrameBorder-br"));
        }
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setClosable(boolean closable) {
        this.closable = closable;
        topBar.updateTopBar();
    }

    public boolean isClosable() {
        return closable;
    }

    public void setMinimizable(boolean minimizable) {
        this.minimizable = minimizable;
        topBar.updateTopBar();
    }

    public void setMaximizable(boolean maximizable) {
        this.maximizable = maximizable;
        topBar.updateTopBar();
    }

    public void setDraggable(boolean draggable) {
        topBar.setDraggable(draggable);
    }

    public void onBrowserEvent(Event event) {
        int type = DOM.eventGetType(event);
        if (type == Event.ONMOUSEDOWN) {
            if (topFrame != this) {
                _show();
            }
        }
    }

    /**
     * Fires the event of the resizing of this frame to its listeners.
     */
    public void fireFrameResized() {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener listener = (GFrameListener) listeners.get(i);
            listener.frameResized(new GFrameEvent(this));
        }
    }

    /**
     * Fires the event of the moving of this frame to its listeners.
     */
    void fireFrameMoved() {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener listener = (GFrameListener) listeners.get(i);
            listener.frameMoved(new GFrameEvent(this));
        }
    }

    /**
     * Fires the closed event of this frame to its listeners.
     */
    private void fireFrameClosed() {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener listener = (GFrameListener) listeners.get(i);
            listener.frameClosed(new GFrameEvent(this));
        }
    }

    /**
     * Fires the frameMaximized event of this frame to its listeners.
     */
    protected void fireFrameOpened() {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener listener = (GFrameListener) listeners.get(i);
            listener.frameOpened(new GFrameEvent(this));
        }
    }

    /**
     * Fires the frameMaximized event of this frame to its listeners.
     */
    public void fireFrameMaximized() {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener listener = (GFrameListener) listeners.get(i);
            listener.frameMaximized(new GFrameEvent(this));
        }
    }

    /**
     * Fires the frameMinimized event of this frame to its listeners.
     */
    public void fireFrameMinimized() {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener listener = (GFrameListener) listeners.get(i);
            listener.frameMinimized(new GFrameEvent(this));
        }
    }

    /**
     * Fires the frameMinimized event of this frame to its listeners.
     */
    public void fireFrameIconified() {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener listener = (GFrameListener) listeners.get(i);
            listener.frameIconified(new GFrameEvent(this));
        }
    }

    /**
     * Fires the frameRestored event of this frame to its listeners.
     */
    public void fireFrameRestored() {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener listener = (GFrameListener) listeners.get(i);
            listener.frameRestored(new GFrameEvent(this));
        }
    }

    /**
     * Fires the frameSelected event of this frame to its listeners.
     */
    public void fireFrameSelected() {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener listener = (GFrameListener) listeners.get(i);
            listener.frameSelected(new GFrameEvent(this));
        }
    }

    public void fireGhostMoving(int top, int left) {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener listener = (GFrameListener) listeners.get(i);
            listener.frameGhostMoving(top, left, new GFrameEvent(this));
        }
    }

    public void fireGhostMoved(int top, int left) {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener listener = (GFrameListener) listeners.get(i);
            listener.frameGhostMoved(top, left, new GFrameEvent(this));
        }
    }

    public boolean isMaximizable() {
        return maximizable;
    }

    public boolean isMinimizable() {
        return minimizable;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getMaximumWidth() {
        return this.maxWidth;
    }

    public int getMaximumHeight() {
        return this.maxHeight;
    }

    public Widget getContent() {
        return myContent;
    }

    public void addFrameListener(GFrameListener l) {
        listeners.add(l);
    }

    public void removeGFrameListener(GFrameListener l) {
        listeners.remove(l);
    }

    public boolean isVisible() {
        return visible;
    }

    public void showModal() {
        overlayLayer.show(currentTheme);
        setMaximizable(false);
        setMinimizable(false);
        setResizable(false);
        GwmUtilities.diplayAtScreenCenter(this);
        modalMode = true;
    }

    public void setVisible(boolean visible) {
        if (closed) {
            throw new IllegalStateException(
                    "This is window has been closed. You can work anymore with a closed window. the garbage collector as released the allocated resources.");
        }
        if (this.visible == visible)
            return;
        if (visible) {
            this.minimized = false;
            super.setVisible(true);
            if (minimizing) {
                minimizing = false;
            }
            setSize(getOffsetWidth(), getOffsetHeight());
            selectBoxManager.setBlockerVisible(true);
            _show();
            fireFrameOpened();
        } else {
            selectBoxManager.setBlockerVisible(false);
            super.setVisible(false);
            if (modalMode) {
                modalMode = false;
                overlayLayer.hide();
            }
        }
        this.visible = visible;
    }

    public boolean isCloseable() {
        return closable;
    }

    public void startResizing() {
        if (topBar instanceof CoadTopBarFF) {
            overlayLayer.show(10);
        }
    }

    public void stopResizing() {
        if (topBar instanceof CoadTopBarFF) {
            overlayLayer.hide();
        }
        fireFrameResized();
    }

    protected void _show() {
        //selectBoxManager.setBlockerDeepLayer(++layerOfTheTopWindow);
        //DOM.setIntStyleAttribute(getElement(), "zIndex", ++layerOfTheTopWindow);
        fireFrameSelected();
    }

    public static int getLayerOfTheTopWindow() {
        return layerOfTheTopWindow;
    }

    public String toString() {
        return this.title;
    }

    public boolean onEventPreview(Event event) {
        int type = DOM.eventGetType(event);
        switch (type) {

        case Event.ONMOUSEDOWN:
        case Event.ONMOUSEUP:
        case Event.ONMOUSEMOVE:
        case Event.ONCLICK:
        case Event.ONDBLCLICK: {
            if (DOM.getCaptureElement() == null) {
                Element target = DOM.eventGetTarget(event);
                if (!DOM.isOrHasChild(getElement(), target)) {
                    return false;
                }
            }
            break;
        }
        }
        return true;
    }

    public CoadSelectBoxManagerImpl getSelectBoxManager() {
        return selectBoxManager;
    }

    public boolean isDragOutline() {
        return outlineDragMode;
    }

    public void setOutlineDragMode(boolean outline) {
        this.outlineDragMode = outline;
    }

    public void fireFrameMoving() {
        for (int i = 0; i < listeners.size(); i++) {
            GFrameListener frameListener = (GFrameListener) listeners.get(i);
            frameListener.frameMoving(new GFrameEvent(this));
        }
    }

    public void updateSize() {
        setSize(ui.getOffsetWidth(), ui.getOffsetHeight());
    }

    public Image getTitleIcon() {
        return null;
    }

    public void setTitleIcon(Image icon) {
    }

    private String getItemTheme(String item) {
        return "gwm-" + currentTheme + "-" + item;
    }

}
