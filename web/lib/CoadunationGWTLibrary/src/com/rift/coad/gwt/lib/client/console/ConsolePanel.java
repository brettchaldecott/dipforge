/*
 * CoadunationGWTLibrary: The default console for the coadunation applications.
 * Copyright (C) 2009  2015 Burntjam
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
 * ConsolePanel.java
 */

// package path
package com.rift.coad.gwt.lib.client.console;

// the vlayout
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeNode;


/**
 * This is the definition of the console panel.
 *
 * @author brett chaldecott
 */
public abstract class ConsolePanel extends VLayout {
    
    // private member variables
    private Console console;

    /**
     * The default constructor for the console panel
     */
    public ConsolePanel() {
        setWidth100();
        setHeight100();

        Layout layout = new HLayout();

        Canvas panel = getViewPanel();
        HLayout wrapper = new HLayout();
        wrapper.setWidth100();
        wrapper.setHeight100();
        wrapper.addMember(panel);

        addMember(wrapper);
    }


    /**
     * This method is invoked to return the canvas to display to the user.
     * 
     * @return The canvas to display to the user.
     */
    public abstract Canvas getViewPanel();

    /**
     * This method returns the name of this panel.
     * 
     * @return The name of this panel.
     */
    public abstract String getName();

    /**
     * This method returns the icon for this panel.
     * 
     * @return The string containing the icon for this panel
     */
    public String getIcon() {
        return null;
    }
    
    /**
     * This method sets the console so that methods can be called on it.
     *
     * @param console The console to call the methods on.
     */
    protected void setConsole(Console console) {
        this.console = console;
    }

    /**
     * This method is called to close this panel down.
     */
    public void close() {
        console.close(this);
    }

    /**
     * This method is called to add a new panel
     */
    public void addPanel(ConsolePanel panel) {
        console.addPanel(panel);
    }


    /**
     * This method is responsible for adding the new panel.
     *
     * @param node The new node.
     * @param panel The panel.
     */
    public void addPanel(NavigationTreeNode node, ConsolePanel panel) {
        console.addPanel(node, panel);
    }


    /**
     * This method is called to add a tree node.
     *
     * @param node The tree node to add
     */
    public void addTreeNode(NavigationTreeNode node) {
        console.addTreeNode(node);
    }


    /**
     * This method is called to remove the tree node identified by the id.
     *
     * @param nodeId The node id for the tree node.
     */
    public void removeTreeNode(String nodeId) {
        console.removeTreeNode(nodeId);
    }


    /**
     * The node to remove.
     *
     * @param node This method is called to remove the tree node.
     */
    public void removeTreeNode(TreeNode node) {
        console.removeTreeNode(node);
    }
}
