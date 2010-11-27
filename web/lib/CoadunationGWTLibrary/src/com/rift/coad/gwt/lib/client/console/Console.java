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
 * Console.java
 */

// package path
package com.rift.coad.gwt.lib.client.console;

// java imports
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;
import com.smartgwt.client.widgets.tree.events.NodeContextClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeContextClickHandler;
import java.util.LinkedHashMap;

/**
 * This object represents the console.
 *
 * @author brett chaldecott
 */
public class Console {

    // private member variables
    private static Console singleton = null;
    private TabSet mainTabSet;
    private Navigation navigation;

    /**
     * The constructor of the console object
     * @param title
     */
    public Console(String title, NavigationDataHandler data) {
        setInstance(this);
        //viewport
        VLayout main = new VLayout() {

            protected void onInit() {
                super.onInit();
            }
        };

        main.setWidth100();
        main.setHeight100();
        main.setLayoutMargin(5);
        main.setStyleName("tabSetContainer");

        HLayout hLayout = new HLayout();
        hLayout.setWidth100();
        hLayout.setHeight100();

        VLayout sideNavLayout = new VLayout();
        sideNavLayout.setHeight100();
        sideNavLayout.setWidth(185);
        sideNavLayout.setShowResizeBar(true);

        navigation = new Navigation(title, this, data);

        navigation.addLeafClickHandler(new LeafClickHandler() {

            public void onLeafClick(LeafClickEvent event) {
                TreeNode node = event.getLeaf();
                showPanel(node);
            }
        });

        navigation.addNodeClickHandler(new NodeClickHandler() {

            public void onNodeClick(NodeClickEvent event) {
                TreeNode node = event.getNode();
                showPanel(node);
            }
        });

        navigation.addNodeContextClickHandler(new NodeContextClickHandler() {

            public void onNodeContextClick(NodeContextClickEvent event) {
                //SC.say("Node context click");
                NavigationTreeNode node = (NavigationTreeNode)event.getNode();
                if (node.getMenu() != null) {
                    node.getMenu().showContextMenu();
                }
                event.cancel();
            }

        });

        sideNavLayout.addMember(navigation);
        hLayout.addMember(sideNavLayout);

        mainTabSet = new TabSet();

        //default is 22. required to increase to that select tab contol dispalys well
        mainTabSet.setTabBarThickness(23);
        mainTabSet.setWidth100();
        mainTabSet.setHeight100();

        LayoutSpacer layoutSpacer = new LayoutSpacer();
        layoutSpacer.setWidth(5);

        mainTabSet.setTabBarControls(TabBarControls.TAB_SCROLLER, TabBarControls.TAB_PICKER);

        HLayout mainPanel = new HLayout();
        mainPanel.setHeight100();
        mainPanel.setWidth100();

        if (SC.hasFirebug()) {
            Label label = new Label();
            label.setContents("<p>Firebug can make the Showcase run slow.</p><p>For the best performance, we suggest disabling Firebug for this site.</p>");
            label.setWidth100();
            label.setHeight("auto");
            label.setMargin(20);
            Window fbWindow = new Window();
            fbWindow.setShowHeader(false);
            fbWindow.addItem(label);
            fbWindow.setWidth(220);
            fbWindow.setHeight(130);

            LayoutSpacer spacer = new LayoutSpacer();
            spacer.setWidth100();
            mainPanel.addMember(spacer);
            mainPanel.addMember(fbWindow);
        }
        Canvas canvas = new Canvas();
        canvas.setBackgroundImage("[SKIN]/shared/background.gif");
        canvas.setWidth100();
        canvas.setHeight100();
        canvas.addChild(mainTabSet);

        hLayout.addMember(canvas);

        main.addMember(hLayout);
        main.draw();

        RootPanel.getBodyElement().removeChild(RootPanel.get("loadingWrapper").getElement());


    }

    private void showPanel(TreeNode node) {


        boolean isNavigationTreeNode = node instanceof NavigationTreeNode;
        if (isNavigationTreeNode) {
            NavigationTreeNode navigationTreeNode = (NavigationTreeNode) node;
            PanelFactory factory = navigationTreeNode.getFactory();
            if (factory != null) {
                String panelID = factory.getID();
                Tab tab = null;
                if (panelID != null) {
                    String tabID = panelID + "_tab";
                    tab = mainTabSet.getTab(tabID);
                }
                if (tab == null) {
                    ConsolePanel panel = (ConsolePanel) factory.create();
                    panel.setConsole(this);
                    addTab(panel,navigationTreeNode);
                    History.newItem(navigationTreeNode.getNodeID(), false);
                } else {
                    mainTabSet.selectTab(tab);
                }
            }
        }
    }


    /**
     * This method returns an instance of the console
     *
     * @return The reference toe the console
     */
    public static Console getInstance() {
        return singleton;
    }


    /**
     * This method is used to set the instance.
     *
     * @param instance The singleton instance;
     */
    public static void setInstance(Console instance) {
        singleton = instance;
    }

    /**
     * This method is used to identify the tab based on its id so that it can be removed.
     *
     * @param id The id of the panel to remove.
     */
    public void close(String id) {
        for (Tab tab : mainTabSet.getTabs()) {
            if (tab.getPane().getID().equals(id)) {
                mainTabSet.removeTab(tab);
                break;
            }
        }
    }


    /**
     * This method is called to close a panel.
     * 
     * @param panel The panel to close.
     */
    public void close(ConsolePanel panel) {
        for (Tab tab : mainTabSet.getTabs()) {
            if (tab.getPane() == panel) {
                mainTabSet.removeTab(tab);
                break;
            }
        }
    }

    
    /**
     * This method is called to add a new panel.
     *
     * @param panel The panel to add
     */
    public void addPanel(ConsolePanel panel) {
        addTab(panel,null);
    }


    public void addPanel(NavigationTreeNode node, ConsolePanel panel) {
        
        addTab(panel,node);

        addTreeNode(node);
    }


    /**
     * This method is called to add a tree node.
     *
     * @param node The tree node to add.
     */
    public void addTreeNode(NavigationTreeNode node) {
        if (navigation.getTree().findById(node.getParentNodeId().toLowerCase()) == null) {
            //SC.say("No node matches the id : " + node.getParentNodeId());
            // Ignore this as the parent node might not have been expanded yet.
        } else {
            if (navigation.getTree().hasChildren(navigation.getTree().findById(node.getParentNodeId().toLowerCase()))) {
                navigation.getTree().add(node,
                        navigation.getTree().findById(node.getParentNodeId().toLowerCase()));
            }
        }
    }


    /**
     * This method is called to remove the tree node identified by the id.
     *
     * @param nodeId The node id for the tree node.
     */
    public void removeTreeNode(String nodeId) {
        if (navigation.getTree().findById(nodeId) != null) {
            navigation.getTree().remove(navigation.getTree().findById(nodeId));
        }
    }


    /**
     * The node to remove.
     *
     * @param node This method is called to remove the tree node.
     */
    public void removeTreeNode(TreeNode node) {
        navigation.getTree().remove(node);
    }


    private void addTab(ConsolePanel panel, NavigationTreeNode node) {
        panel.setConsole(this);

        String tabId = panel.getID() + "_tab";

        Tab tab = mainTabSet.getTab(tabId);
        if (tab != null) {
            return;
        }

        tab = new Tab();
        tab.setID(tabId);

        String sampleName = panel.getName();

        String icon = panel.getIcon();
        if (icon == null && node != null) {
            icon = node.getIcon();
        }
        if (icon == null) {
            icon = "silk/plugin.png";
        }
        String imgHTML = "<img src='images/" + icon + "' style='vertical-align:middle' border=0 width=16 height=16 />";//Canvas.imgHTML(icon);
        tab.setTitle("<span>" + imgHTML + "&nbsp;" + sampleName + "</span>");
        tab.setPane(panel);
        tab.setCanClose(true);
        mainTabSet.addTab(tab);
        mainTabSet.selectTab(tab);
        if (!SC.isIE()) {
            if (mainTabSet.getNumTabs() == 10) {
                mainTabSet.removeTabs(new int[]{1});
            }
        }
    }
}
