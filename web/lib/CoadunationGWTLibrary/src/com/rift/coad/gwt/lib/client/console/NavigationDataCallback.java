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
 * NavigationDataCallback.java
 */

package com.rift.coad.gwt.lib.client.console;

/**
 *
 * @author brett chaldecott
 */
public interface NavigationDataCallback {

    /**
     * This method adds the child node.
     *
     * @param nodes The list of nodes to add
     */
    public void addChildren(NavigationTreeNode[] nodes);


    /**
     * This method is called to add a tree node.
     *
     * @param node The node to add.
     */
    public void addChild(NavigationTreeNode node);


    /**
     * This method is called to add a new panel
     */
    public void addPanel(ConsolePanel panel);


    /**
     * This method is responsible for adding the new panel.
     *
     * @param node The new node.
     * @param panel The panel.
     */
    public void addPanel(NavigationTreeNode node, ConsolePanel panel);
}
